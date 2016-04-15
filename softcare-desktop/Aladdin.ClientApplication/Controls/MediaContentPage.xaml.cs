using System.Windows.Controls;
using System.Windows.Input;


namespace SoftCare.ClientApplication.Controls
{


    /// <summary>
    /// Interaction logic for VideoPage.xaml
    /// </summary>
    public partial class MediaContentPage : UserControl
    {


        /// <summary>
        /// 
        /// </summary>
        public MediaContentPage()
        {
            InitializeComponent();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CommandBinding_Executed(object sender, ExecutedRoutedEventArgs e)
        {
            e.Handled = true;
            if (AppCommands.OpenMediaContentSectionCommand.Equals(e.Command))
            {
                if (Config.CATEGORY_VIDEOS.Equals(e.Parameter))
                    AppCommands.MoveToPageCommand.Execute(Config.CATEGORY_VIDEOS, null);
                else if (Config.CATEGORY_VIDEOSWEB.Equals(e.Parameter))
                    AppCommands.MoveToPageCommand.Execute(Config.CATEGORY_VIDEOSWEB, null);
                else if (Config.CATEGORY_MUSIC.Equals(e.Parameter))
                    AppCommands.MoveToPageCommand.Execute(Config.CATEGORY_MUSIC, null);
                else if (Config.CATEGORY_BOOKS.Equals(e.Parameter))
                    AppCommands.MoveToPageCommand.Execute(Config.CATEGORY_BOOKS, null);
                else if (Config.CATEGORY_GAMES.Equals(e.Parameter))
                    AppCommands.MoveToPageCommand.Execute(Config.CATEGORY_GAMES, null);
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CommandBinding_CanExecute(object sender, CanExecuteRoutedEventArgs e)
        {
            if (AppCommands.OpenMediaContentSectionCommand.Equals(e.Command))
            {
                e.Handled = true;
                if (Config.CATEGORY_VIDEOS.Equals(e.Parameter))
                {
                    e.CanExecute = App.VideosContent != null && App.VideosContent.Length > 0;
                }
                else if (Config.CATEGORY_VIDEOSWEB.Equals(e.Parameter))
                {
                    e.CanExecute = App.VideosWebContent != null && App.VideosWebContent.Length > 0;
                }
                else if (Config.CATEGORY_MUSIC.Equals(e.Parameter))
                {
                    e.CanExecute = App.MusicContent != null && App.MusicContent.Length > 0;
                }
                else if (Config.CATEGORY_BOOKS.Equals(e.Parameter))
                {
                    e.CanExecute = App.BooksContent != null && App.BooksContent.Length > 0;
                }
                else if (Config.CATEGORY_GAMES.Equals(e.Parameter))
                {
                    e.CanExecute = App.GamesContent != null && App.GamesContent.Length > 0;
                }
            }
        }


    }


}
