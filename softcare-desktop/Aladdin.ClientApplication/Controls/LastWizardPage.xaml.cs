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
using SoftCare.ClientApplication.ViewModels;

namespace SoftCare.ClientApplication.Controls
{
    /// <summary>
    /// Interaction logic for LastWizardPage.xaml
    /// </summary>
    public partial class LastWizardPage : UserControl
    {
        LastWizardPageViewModel ViewModel { get; set; }

        public LastWizardPage()
        {
            InitializeComponent();
        }

        public LastWizardPage(QuestionnaireWizard wizard)
        {
            this.ViewModel = new LastWizardPageViewModel(wizard);
            this.DataContext = this.ViewModel;
            InitializeComponent();
        }
    }
}
