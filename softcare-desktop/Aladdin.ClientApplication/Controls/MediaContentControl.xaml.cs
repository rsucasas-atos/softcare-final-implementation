using System.Windows;
using System.Windows.Controls;


namespace SoftCare.ClientApplication.Controls
{


    /// <summary>
    /// Interaction logic for MediaContentControl.xaml
    /// </summary>
    public partial class MediaContentControl : UserControl
    {


        public string Title
        {
            get { return (string)GetValue(TitleProperty); }
            set { SetValue(TitleProperty, value); }
        }

        // Using a DependencyProperty as the backing store for Title.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty TitleProperty =
            DependencyProperty.Register("Title", typeof(string), typeof(MediaContentControl));


        //
        public MediaContent MediaContent
        {
            get { return (MediaContent)GetValue(MediaContentProperty); }
            set { SetValue(MediaContentProperty, value); }
        }


        // Using a DependencyProperty as the backing store for MediaContent.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty MediaContentProperty =
            DependencyProperty.Register("MediaContent", typeof(MediaContent), typeof(MediaContentControl));

        
        /// <summary>
        /// 
        /// </summary>
        public MediaContentControl()
        {
            InitializeComponent();
            this.DataContext = this;
        }


    }


}
