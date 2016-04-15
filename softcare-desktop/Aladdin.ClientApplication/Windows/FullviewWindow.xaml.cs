using System;
using System.Windows;
using System.Windows.Input;


namespace SoftCare.ClientApplication.Windows
{


    /// <summary>
    /// Interaction logic for FullviewWindow.xaml
    /// </summary>
    public partial class FullviewWindow : Window
    {


        public string Url { get; set; }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="url"></param>
        public FullviewWindow(string url)
        {
            this.Url = url;
            InitializeComponent();
            // set window title
            this.Title = Config.APP_NAME;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            Cursor = Cursors.Wait;

            try
            {
                if (!string.IsNullOrEmpty(this.Url))
                {
                    Uri uri = new Uri(Url);
                    if (uri != null)
                        this.WebBrowser.Source = uri;
                }
            }
            catch (Exception) { }

            Cursor = Cursors.Arrow;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Back_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Window_Closed(object sender, EventArgs e)
        {
            //this.WebBrowser.Navigate("");
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void WebBrowser_Navigated(object sender, System.Windows.Navigation.NavigationEventArgs e)
        {
            Utils.HideScriptErrors(this.WebBrowser, true);
        }


    }


}
