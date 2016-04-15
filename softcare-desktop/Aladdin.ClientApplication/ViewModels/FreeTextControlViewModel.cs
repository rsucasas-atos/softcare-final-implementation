using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;
using Aladdin.DataModel;

namespace SoftCare.ClientApplication.ViewModels
{
    class FreeTextControlViewModel : INotifyPropertyChanged
    {
        #region INotifyPropertyChanged Members

        public event PropertyChangedEventHandler PropertyChanged;

        protected void SendPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
        }

        #endregion

        public QuestionnaireWizard Wizard { get; set; }

        string _FreeText;
        public string FreeText
        {
            get
            {
                _FreeText = this.Wizard.Answers.GetAnswer(this.Wizard.ActiveQuestion.ID).Value;
                return _FreeText;
            }
            set
            {
                _FreeText = value;
                this.SendPropertyChanged("FreeText");
                this.Wizard.Answers.SetAnswer(this.Wizard.ActiveQuestion.ID, value, this.Wizard.ActiveQuestion.GlobalID);
                this.Wizard.OnQuestionAnswered();
            }
        }

        public FreeTextControlViewModel(QuestionnaireWizard wizard)
        {
            this.Wizard = wizard;
            wizard.ActivePageChanged += new QuestionnaireWizard.ActivePageChangedEventHandler(wizard_ActivePageChanged);
        }

        void wizard_ActivePageChanged()
        {
            this.SendPropertyChanged("FreeText");
        }
    }
}
