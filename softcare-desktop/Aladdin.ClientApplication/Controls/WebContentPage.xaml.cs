using SoftCare.ClientApplication.ViewModels;
using SoftCare.ClientApplication.Windows;
using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;


namespace SoftCare.ClientApplication.Controls
{


    /// <summary>
    /// Interaction logic for WebContentPage.xaml
    /// </summary>
    public partial class WebContentPage : UserControl
    {


        ContentPageViewModel ViewModel { get; set; }

        public string Url { get; set; }

        public string Text { get; set; }

        public string Title { get; set; }

        public string Category { get; set; }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="content"></param>
        public WebContentPage(SoftCare.ClientApplication.Controls.MediaContent content)
        {
            this.ViewModel = new ContentPageViewModel(content);
            this.Category = 
            this.Url = this.ViewModel.Url;
            this.Text = this.ViewModel.Text;
            this.Title = this.ViewModel.Title;
            this.Category = this.ViewModel.Category;
            this.DataContext = this.ViewModel;
            InitializeComponent();

            if (string.IsNullOrEmpty(this.Url))
            {
                this.WebPageExpander.IsExpanded = false;
                this.WebPageExpander.Visibility = Visibility.Hidden;
                this.WebPageExpander.Height = 0;
            }
            if (string.IsNullOrEmpty(this.Text))
            {
                this.TextExpander.IsExpanded = false;
                this.WebBrowser.MinHeight = 450;
                this.TextExpander.Visibility = Visibility.Hidden;
                this.TextExpander.Height = 0;
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            Cursor = Cursors.Wait;

            if (!string.IsNullOrEmpty(this.Url))
            {
                Uri uri = new Uri(Url);
                if (uri != null)
                    this.WebBrowser.Source = uri;
            }

            Cursor = Cursors.Arrow;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void UserControl_Unloaded(object sender, RoutedEventArgs e)
        {
            //this.WebBrowser.Navigate("");
        }


        /// <summary>
        /// Go Back
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            AppCommands.MoveToPageCommand.Execute(this.Category, null);
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void FullView_Click(object sender, RoutedEventArgs e)
        {
            try
            {
                FullviewWindow fullviewWindow = new FullviewWindow(this.Url);
                fullviewWindow.ShowDialog();
                this.WebBrowser.Navigate("");
                if (fullviewWindow.ShowDialog() == false)
                    if (!string.IsNullOrEmpty(this.Url))
                        this.WebBrowser.Navigate(new Uri(Url));
            }
            catch (Exception) { }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void WebBrowser_Navigated(object sender, NavigationEventArgs e)
        {
            Utils.HideScriptErrors(this.WebBrowser,true);
        }


    }


}