using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Aladdin.DataModel;

namespace SoftCare.ClientApplication
{
    public class QuestionnaireWizard
    {
        public delegate void ActivePageChangedEventHandler();

        public event ActivePageChangedEventHandler ActivePageChanged;

        Questionnaire Questionnaire { get; set; }

        internal QuestionnaireAnswers Answers { get; set; }

        List<QuestionnaireQuestion> Questions { get; set; }

        int _ActiveQuestionIndex;
        int ActiveQuestionIndex
        {
            get { return _ActiveQuestionIndex; }
            set
            {
                _ActiveQuestionIndex = value;
                if (this.ActivePageChanged != null)
                    this.ActivePageChanged();
            }
        }

        public QuestionnaireQuestion ActiveQuestion
        {
            get
            {
                if (this.Questions == null)
                    return null;
                if (this.ActiveQuestionIndex >= this.Questions.Count)
                    return null;
                return this.Questions[this.ActiveQuestionIndex];
            }
        }

        public QuestionnaireWizard(Questionnaire questionnaire, QuestionnaireAnswers answers)
        {
            this.Questionnaire = questionnaire;
            this.Answers = answers;
            this.Questions = new List<QuestionnaireQuestion>(questionnaire.Questions);
            this.ActiveQuestionIndex = 0;
        }

        public void OnQuestionAnswered()
        {
            System.Windows.Input.CommandManager.InvalidateRequerySuggested();
        }

        public void MoveNext()
        {
            if (!this.CanMoveNext())
                return;

            //add sub questions
            QuestionnaireAnswer answer = this.Answers.GetAnswer(this.ActiveQuestion.ID);
            if (answer != null)
            {
                //remove all subquestions
                this.Questions.RemoveAll(qq => this.ActiveQuestion.ID.Equals(qq.ParentQuestionnaireQuestionID));

                //and then add them if the condition is met
                
                int added = 0;
                foreach (QuestionnaireQuestion question in this.ActiveQuestion.Questions)
                {
                    added++;
                    if ((question.Condition == null) || (answer.Value.Equals(question.Condition)))
                        this.Questions.Insert(this.ActiveQuestionIndex + added, question);
                }
            }

            this.ActiveQuestionIndex++;
        }

        public void MoveBack()
        {
            this.ActiveQuestionIndex--;
        }

        public bool CanMoveNext()
        {
            if (this.ActiveQuestionIndex >= this.Questions.Count)
                return false;

            QuestionnaireAnswer answer = this.Answers.GetAnswer(this.ActiveQuestion.ID);
            if (answer == null)
                return false;
            
            return true;
        }

        public bool CanMoveBack()
        {
            return this.ActiveQuestionIndex > 0;
        }

        public bool IsQuestionnaireFinished
        {
            get { return this.ActiveQuestionIndex == this.Questions.Count; }
        }
    }
}
