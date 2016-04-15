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
using System.Windows.Shapes;
using SoftCare.ClientApplication.Properties;

namespace SoftCare.ClientApplication
{
    /// <summary>
    /// Interaction logic for PilotSelectionWindow.xaml
    /// </summary>
    public partial class PilotSelectionWindow : Window
    {
        public PilotSelectionWindow()
        {
            InitializeComponent();
        }

        private void BSA_Click(object sender, RoutedEventArgs e)
        {
            /////////////////////////////
            // PARCHE TEMPORAL
            // TODO
            //Settings.Default.Aladdin_ClientApplication_aladdinService_StorageComponent = "https://193.174.152.114/axis2/services/SCBSA?wsdl";
            /////////////////////////////
            Settings.Default.DefaultLanguage = "ca_ES";
            Settings.Default.ForumPage = "http://dafnis.atosorigin.es/BSA/Aladdin/home.php";
            Settings.Default.FirstInstallation = false;
            Settings.Default.Save();
            this.DialogResult = true;           
        }

        private void DAFNI_Click(object sender, RoutedEventArgs e)
        {
            /////////////////////////////
            // PARCHE TEMPORAL
            // TODO
            //Settings.Default.Aladdin_ClientApplication_aladdinService_StorageComponent = "https://193.174.152.114/axis2/services/SCDAFNI?wsdl";
            /////////////////////////////
            Settings.Default.DefaultLanguage = "el_GR";
            Settings.Default.ForumPage = "http://dafnis.atosorigin.es/DAFNI/Aladdin/home.php";
            Settings.Default.FirstInstallation = false;
            Settings.Default.Save();
            this.DialogResult = true;
        }

        private void NHNN_Click(object sender, RoutedEventArgs e)
        {
            /////////////////////////////
            // PARCHE TEMPORAL
            // TODO
            //Settings.Default.Aladdin_ClientApplication_aladdinService_StorageComponent = "https://193.174.152.114/axis2/services/SCNHNN?wsdl";
            /////////////////////////////
            Settings.Default.DefaultLanguage = "en_UK";
            Settings.Default.ForumPage = "http://dafnis.atosorigin.es/NHNN/Aladdin/home.php";
            Settings.Default.FirstInstallation = false;
            Settings.Default.Save();
            this.DialogResult = true;
        }

    }
}
