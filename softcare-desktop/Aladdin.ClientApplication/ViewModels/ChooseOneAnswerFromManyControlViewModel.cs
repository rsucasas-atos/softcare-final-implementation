using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SoftCare.ClientApplication.ViewModels
{
    class ChooseOneAnswerFromManyControlViewModel
    {
        public QuestionnaireWizard Wizard { get; set; }

        public ChooseOneAnswerFromManyControlViewModel(QuestionnaireWizard wizard)
        {
            this.Wizard = wizard;
        }

        internal void SetAnswer(string answer)
        {
            this.Wizard.Answers.SetAnswer(this.Wizard.ActiveQuestion.ID, answer, this.Wizard.ActiveQuestion.GlobalID);
            this.Wizard.OnQuestionAnswered();
        }
    }
}
