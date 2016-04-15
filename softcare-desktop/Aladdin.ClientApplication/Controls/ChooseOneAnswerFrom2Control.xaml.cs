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
using Aladdin.DataModel;
using SoftCare.ClientApplication.ViewModels;

namespace SoftCare.ClientApplication.Controls
{
    /// <summary>
    /// Interaction logic for ChooseOneAnswerFrom2Control.xaml
    /// </summary>
    public partial class ChooseOneAnswerFrom2Control : UserControl
    {
        ChooseOneAnswerFrom2ControlViewModel ViewModel { get; set; }

        public ChooseOneAnswerFrom2Control()
        {
            InitializeComponent();
        }

        internal ChooseOneAnswerFrom2Control(QuestionnaireWizard wizard)
        {
            this.ViewModel = new ChooseOneAnswerFrom2ControlViewModel(wizard);
            this.DataContext = this.ViewModel;

            InitializeComponent();
        }

        public void FillAnswers()
        {
            this.ViewModel.FillAnswers();
        }

        private void Answer1Button_Click(object sender, RoutedEventArgs e)
        {
            this.ViewModel.SelectAnswer1();
        }

        private void Answer2Button_Click(object sender, RoutedEventArgs e)
        {
            this.ViewModel.SelectAnswer2();            
        }
    }
}
