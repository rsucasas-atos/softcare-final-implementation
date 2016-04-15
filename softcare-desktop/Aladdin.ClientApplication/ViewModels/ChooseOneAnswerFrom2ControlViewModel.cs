using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;
using Aladdin.DataModel;

namespace SoftCare.ClientApplication.ViewModels
{
    class ChooseOneAnswerFrom2ControlViewModel : INotifyPropertyChanged
    {
        #region INotifyPropertyChanged Members

        public event PropertyChangedEventHandler PropertyChanged;

        protected void SendPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
        }

        #endregion

        string _Answer1Title;
        public string Answer1Title
        {
            get { return _Answer1Title; }
            set
            {
                _Answer1Title = value;
                this.SendPropertyChanged("Answer1Title");
            }
        }

        string _Answer2Title;
        public string Answer2Title
        {
            get { return _Answer2Title; }
            set
            {
                _Answer2Title = value;
                this.SendPropertyChanged("Answer2Title");
            }
        }

        public QuestionnaireWizard Wizard { get; set; }

        public ChooseOneAnswerFrom2ControlViewModel(QuestionnaireWizard wizard)
        {
            this.Wizard = wizard;
        }

        internal void FillAnswers()
        {
            this.Answer1Title = this.Wizard.ActiveQuestion.Answers[0].Answer;
            this.Answer2Title = this.Wizard.ActiveQuestion.Answers[1].Answer;
        }

        internal void SelectAnswer1()
        {
            this.Wizard.Answers.SetAnswer(this.Wizard.ActiveQuestion.ID, this.Wizard.ActiveQuestion.Answers[0].Value, this.Wizard.ActiveQuestion.Answers[0].GlobalID);
            this.Wizard.OnQuestionAnswered();
            this.Wizard.MoveNext();
        }

        internal void SelectAnswer2()
        {
            this.Wizard.Answers.SetAnswer(this.Wizard.ActiveQuestion.ID, this.Wizard.ActiveQuestion.Answers[1].Value, this.Wizard.ActiveQuestion.Answers[1].GlobalID);
            this.Wizard.OnQuestionAnswered();
            this.Wizard.MoveNext();
        }
    }
}
