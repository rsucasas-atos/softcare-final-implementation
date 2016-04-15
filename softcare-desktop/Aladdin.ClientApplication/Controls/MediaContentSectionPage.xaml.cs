using System.Collections.Generic;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Linq;


namespace SoftCare.ClientApplication.Controls
{


    /// <summary>
    /// Interaction logic for MediaContentSectionPage.xaml
    /// </summary>
    public partial class MediaContentSectionPage : UserControl
    {


        public string Category { get; set; }
        List<MediaContent> SectionMediaContent = new List<MediaContent>();


        /// <summary>
        /// 
        /// </summary>
        /// <param name="category"></param>
        public MediaContentSectionPage(string category)
        {
            InitializeComponent();
            this.Category = category;
        }

        
        /// <summary>
        /// 
        /// </summary>
        private void LoadContent()
        {
            Cursor = Cursors.Wait;
            this.MediaContentPanel.Children.Clear();

            switch (Category)
            {
                case Config.CATEGORY_MUSIC:
                    SectionMediaContent = App.MusicContent.ToList();
                    break;
                case Config.CATEGORY_VIDEOSWEB:
                    SectionMediaContent = App.VideosWebContent.ToList();
                    break;
                case Config.CATEGORY_VIDEOS:
                    SectionMediaContent = App.VideosContent.ToList();
                    break;
                case Config.CATEGORY_BOOKS:
                    SectionMediaContent = App.BooksContent.ToList();
                    break;
                case Config.CATEGORY_GAMES:
                    SectionMediaContent = App.GamesContent.ToList();
                    break;
                default:
                    break;
            }

            foreach (SoftCare.ClientApplication.Controls.MediaContent content in SectionMediaContent)
            {
                MediaContentControl mediaContentControl = new MediaContentControl();
                mediaContentControl.Title = content.title;
                mediaContentControl.MediaContent = content;
                this.MediaContentPanel.Children.Add(mediaContentControl);
            }

            Cursor = Cursors.Arrow;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            LoadContent();
        }


    }


}
