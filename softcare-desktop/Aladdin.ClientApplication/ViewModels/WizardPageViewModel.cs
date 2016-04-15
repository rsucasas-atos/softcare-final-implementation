using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Aladdin.DataModel;
using System.ComponentModel;
using SoftCare.ClientApplication.Controls;
using System.Windows;

namespace SoftCare.ClientApplication.ViewModels
{
    class WizardPageViewModel : INotifyPropertyChanged
    {
        #region INotifyPropertyChanged Members

        public event PropertyChangedEventHandler PropertyChanged;

        protected void SendPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
        }

        #endregion

        public Questionnaire Questionnaire { get; set; }

        public QuestionnaireAnswers QuestionnaireAnswers { get; set; }

        public QuestionnaireWizard QuestionnaireWizard { get; set; }

        string _QuestionNumber;
        public string QuestionNumber
        {
            get { return _QuestionNumber; }
            set
            {
                _QuestionNumber = value;
                this.SendPropertyChanged("QuestionNumber");
            }
        }

        string _QuestionTitle;
        public string QuestionTitle
        {
            get { return _QuestionTitle; }
            set
            {
                _QuestionTitle = value;
                this.SendPropertyChanged("QuestionTitle");
            }
        }

        object _ActiveQuestionnairePage;
        public object ActiveQuestionnairePage
        {
            get { return _ActiveQuestionnairePage; }
            set
            {
                _ActiveQuestionnairePage = value;
                this.SendPropertyChanged("ActiveQuestionnairePage");
            }
        }

        FreeTextControl _FreeTextControl;
        FreeTextControl FreeTextControl
        {
            get
            {
                if (_FreeTextControl == null)
                    _FreeTextControl = new FreeTextControl(this.QuestionnaireWizard);
                return _FreeTextControl;
            }
        }

        ChooseOneAnswerFrom2Control _ChooseOneAnswerFrom2Control;
        ChooseOneAnswerFrom2Control ChooseOneAnswerFrom2Control
        {
            get
            {
                if (_ChooseOneAnswerFrom2Control == null)
                    _ChooseOneAnswerFrom2Control = new ChooseOneAnswerFrom2Control(this.QuestionnaireWizard);
                return _ChooseOneAnswerFrom2Control;
            }
        }

        ChooseOneAnswerFromManyControl _ChooseOneAnswerFromManyControl;
        ChooseOneAnswerFromManyControl ChooseOneAnswerFromManyControl
        {
            get
            {
                if (_ChooseOneAnswerFromManyControl == null)
                    _ChooseOneAnswerFromManyControl = new ChooseOneAnswerFromManyControl(this.QuestionnaireWizard);
                return _ChooseOneAnswerFromManyControl;
            }
        }

        LastWizardPage _LastWizardPage;
        LastWizardPage LastWizardPage
        {
            get
            {
                if (_LastWizardPage == null)
                    _LastWizardPage = new LastWizardPage(this.QuestionnaireWizard);
                return _LastWizardPage;
            }
        }

        Visibility _QuestionSummaryVisibility;
        public Visibility QuestionSummaryVisibility
        {
            get { return _QuestionSummaryVisibility; }
            set
            {
                if (_QuestionSummaryVisibility != value)
                {
                    _QuestionSummaryVisibility = value;
                    this.SendPropertyChanged("QuestionSummaryVisibility");
                }
            }
        }

        SoftCare.ClientApplication.aladdinService.Task ActiveTask;

        public WizardPageViewModel(SoftCare.ClientApplication.aladdinService.Task activeTask)
        {
            this.ActiveTask = activeTask;
            if (this.ActiveTask != null)
            {
                SoftCare.ClientApplication.aladdinService.Questionnaire qFS = this.ActiveTask.Questionnaire;
                this.Questionnaire = Questionnaire.FromFile(@"DailyQuestionnaire.xml");  //Utils.Convert(qFS);// Questionnaire.FromFile(@"DailyQuestionnaire.xml");
                this.QuestionnaireAnswers = new QuestionnaireAnswers();
                this.QuestionnaireWizard = new QuestionnaireWizard(this.Questionnaire, this.QuestionnaireAnswers);
                this.QuestionnaireWizard.ActivePageChanged += new ClientApplication.QuestionnaireWizard.ActivePageChangedEventHandler(QuestionnaireWizard_ActivePageChanged);
                this.QuestionSummaryVisibility = Visibility.Visible;
                this.UpdateQuestionInfo();
            }
        }

        void QuestionnaireWizard_ActivePageChanged()
        {
            this.UpdateQuestionInfo();
        }

        private void UpdateQuestionInfo()
        {
            if (this.QuestionnaireWizard.IsQuestionnaireFinished)
            {
                this.QuestionSummaryVisibility = Visibility.Hidden;
                this.ActiveQuestionnairePage = this.LastWizardPage;
            }
            else
            {
                this.QuestionSummaryVisibility = Visibility.Visible;
                this.QuestionNumber = string.Format("Question {0}", this.QuestionnaireWizard.ActiveQuestion.ID);
                this.QuestionTitle = this.QuestionnaireWizard.ActiveQuestion.Title;
                if (this.QuestionnaireWizard.ActiveQuestion.QuestionType == QuestionnaireQuestionAnswerType.OneAnswer)
                {
                    if (this.QuestionnaireWizard.ActiveQuestion.Answers.Length == 2)
                    {
                        this.ChooseOneAnswerFrom2Control.FillAnswers();
                        this.ActiveQuestionnairePage = this.ChooseOneAnswerFrom2Control;
                    }
                    else
                    {
                        this.ChooseOneAnswerFromManyControl.FillAnswers(this.QuestionnaireWizard.ActiveQuestion);
                        this.ActiveQuestionnairePage = this.ChooseOneAnswerFromManyControl;
                    }
                }
                else
                {
                    if (this.QuestionnaireWizard.ActiveQuestion.QuestionType == QuestionnaireQuestionAnswerType.FreeText)
                        this.ActiveQuestionnairePage = this.FreeTextControl;                        
                }
            }
        }

        internal void MoveBack()
        {
            if (this.CanMoveBack())
            {
                this.QuestionnaireWizard.MoveBack();
                this.UpdateQuestionInfo();
            }
        }

        internal void MoveNext()
        {
            if (this.CanMoveNext())
            {
                this.QuestionnaireWizard.MoveNext();
                this.UpdateQuestionInfo();
            }
        }

        internal bool CanMoveBack()
        {
            return this.QuestionnaireWizard.CanMoveBack();
        }

        internal bool CanMoveNext()
        {
            return this.QuestionnaireWizard.CanMoveNext();
        }

        internal void UploadQuestionnaire()
        {
            string xml = this.QuestionnaireAnswers.ToXml();
            SoftCare.ClientApplication.aladdinService.StorageComponentImplService sc = new SoftCare.ClientApplication.aladdinService.StorageComponentImplService();
            SoftCare.ClientApplication.aladdinService.QuestionnaireAnswers answers = Utils.Convert(this.QuestionnaireAnswers);
            answers.DateTime = this.ActiveTask.DateTimeAssigned;
            answers.DateTimeSpecified = true;
            answers.UserID = App.CurrentUserID;
            answers.ObjectID = this.ActiveTask.ObjectID;
            answers.TaskID = this.ActiveTask.ID;
            //answers.ID = this.ActiveTask.Questionnaire.ID;
            SoftCare.ClientApplication.aladdinService.OperationResult res = sc.StoreQuestionnaireAnswers(answers, App.CurrentUserID);
            res = sc.ChangeTaskStatus(Convert.ToInt32(this.ActiveTask.ID), (int)Config.TaskStatusEnum.Completed, App.CurrentUserID);
            AppCommands.MoveToPageCommand.Execute("MyTasksPage", null);
        }
    }
}
