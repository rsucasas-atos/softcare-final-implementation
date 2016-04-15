using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;
using SoftCare.ClientApplication.ViewModels;
using System.Windows;
using System;
using SoftCare.ClientApplication.Windows;


namespace SoftCare.ClientApplication.Controls
{


    /// <summary>
    /// Interaction logic for ExerciseControl.xaml
    /// </summary>
    public partial class ExercisePage : UserControl
    {

        ExercisePageViewModel ViewModel { get; set; }

        public string Url { get; set; }

        public string Text { get; set; }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="activeTask"></param>
        public ExercisePage(SoftCare.ClientApplication.aladdinService.Task activeTask)
        {
            this.ViewModel = new ExercisePageViewModel(activeTask);
            this.Url = this.ViewModel.Url;
            this.Text = this.ViewModel.Text;
            this.DataContext = this.ViewModel;
            InitializeComponent();

            if (string.IsNullOrEmpty(this.Url))
                this.WebPageExpander.IsExpanded = false;
            if (string.IsNullOrEmpty(this.Text))
                this.TextExpander.IsExpanded = false;
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
                Uri uri = new Uri(Url);
                if (uri != null)
                    this.WebBrowser.Source = uri;
            }
            catch (Exception) { }

            Cursor = Cursors.Arrow;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void FullView_Click(object sender, RoutedEventArgs e)
        {
            FullviewWindow fullviewWindow = new FullviewWindow(this.Url);
            this.WebBrowser.Navigate("");
            if (fullviewWindow.ShowDialog() == false)
                if (!string.IsNullOrEmpty(this.Url))
                    this.WebBrowser.Navigate(new Uri(Url));
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


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void UserControl_Unloaded(object sender, RoutedEventArgs e)
        {
            this.WebBrowser.Navigate("");
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            AppCommands.MoveToPageCommand.Execute("MyTasksPage", null);
        }
    }


}
