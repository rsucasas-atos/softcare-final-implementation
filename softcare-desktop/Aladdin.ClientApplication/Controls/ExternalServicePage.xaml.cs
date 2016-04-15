using SoftCare.ClientApplication.ViewModels;
using System;
using System.Configuration;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;



namespace SoftCare.ClientApplication.Controls
{


    /// <summary>
    /// Interaction logic for ExternalServicePage.xaml
    /// </summary>
    public partial class ExternalServicePage : UserControl
    {


        ExternalServicePageViewModel ViewModel { get; set; }
        public string Url { get; set; }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="activeTask"></param>
        public ExternalServicePage(SoftCare.ClientApplication.aladdinService.Task activeTask)
        {
            this.ViewModel = new ExternalServicePageViewModel(activeTask);
            this.Url = this.ViewModel.Url;
            InitializeComponent();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            Cursor = Cursors.Wait;

            try
            {
                Uri uri = new Uri(ConfigurationManager.AppSettings["URL.Forum"]);
                if (uri != null)
                {
                    this.WebBrowser.Source = uri;
                }
            }
            catch (Exception) { }

            Cursor = Cursors.Arrow;
        }
    }


}
