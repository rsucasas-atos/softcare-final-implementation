using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using SoftCare.ClientApplication.Windows;
using SoftCare.ClientApplication.ViewModels;

namespace SoftCare.ClientApplication.Controls
{
    /// <summary>
    /// Interaction logic for WizardPage.xaml
    /// </summary>
    public partial class WizardPage : UserControl
    {
        WizardPageViewModel ViewModel { get; set; }

        public WizardPage(SoftCare.ClientApplication.aladdinService.Task activeTask)
        {
            this.ViewModel = new WizardPageViewModel(activeTask);
            this.DataContext = this.ViewModel;

            InitializeComponent();
            //if (MainWindow.Questionnaire != null)
            //    this.ChooseOneAnswerFromManyControl.FillAnswers(MainWindow.Questionnaire.Questions[4].Questions[0]);
        }

        private void CommandBinding_Executed(object sender, ExecutedRoutedEventArgs e)
        {
            e.Handled = true;
            if (AppCommands.QuetionnaireMoveBackCommand.Equals(e.Command))
                this.ViewModel.MoveBack();
            else if (AppCommands.QuetionnaireMoveNextCommand.Equals(e.Command))
                this.ViewModel.MoveNext();
            else if (AppCommands.UploadQuestionnaireCommand.Equals(e.Command))
                this.ViewModel.UploadQuestionnaire();
        }

        private void CommandBinding_CanExecute(object sender, CanExecuteRoutedEventArgs e)
        {
            e.Handled = true;
            if (AppCommands.QuetionnaireMoveBackCommand.Equals(e.Command))
                e.CanExecute = this.ViewModel.CanMoveBack();
            else if (AppCommands.QuetionnaireMoveNextCommand.Equals(e.Command))
                e.CanExecute = this.ViewModel.CanMoveNext();
            else if (AppCommands.UploadQuestionnaireCommand.Equals(e.Command))
                e.CanExecute = true;
        }
    }
}
