using System;
using System.Windows;
using System.Windows.Input;


namespace SoftCare.ClientApplication.Windows
{


    /// <summary>
    /// Interaction logic for FullviewWindow.xaml
    /// </summary>
    public partial class FullviewVideosWindow : Window
    {


        public string Url { get; set; }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="url"></param>
        public FullviewVideosWindow(string url)
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
                        this.WebBrowserFull.Source = uri;

                    this.WebBrowserFull.Play();
                    InitializePropertyValues();
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
        /*private void WebBrowser_Navigated(object sender, System.Windows.Navigation.NavigationEventArgs e)
        {
            Utils.HideScriptErrors(this.WebBrowser, true);
        }*/

        // Play the media.
        void OnMouseDownPlayMedia(object sender, MouseButtonEventArgs args)
        {
            this.WebBrowserFull.Play();
        }

        // Pause the media.
        void OnMouseDownPauseMedia(object sender, MouseButtonEventArgs args)
        {
            this.WebBrowserFull.Pause();
        }

        // Stop the media.
        void OnMouseDownStopMedia(object sender, MouseButtonEventArgs args)
        {
            this.WebBrowserFull.Stop();
        }


        // When the media opens, initialize the "Seek To" slider maximum value
        // to the total number of miliseconds in the length of the media clip.
        private void Element_MediaOpened(object sender, EventArgs e)
        {
            this.timelineSlider.Maximum = this.WebBrowserFull.NaturalDuration.TimeSpan.TotalMilliseconds;
            //this.WebBrowser.Play();
        }

        // When the media playback is finished. Stop() the media to seek to media start.
        private void Element_MediaEnded(object sender, EventArgs e)
        {
            this.WebBrowserFull.Stop();
        }


        // Change the volume of the media.
        private void ChangeMediaVolume(object sender, RoutedPropertyChangedEventArgs<double> args)
        {
            if (this.WebBrowserFull != null)
                this.WebBrowserFull.Volume = (double)this.volumeSlider.Value;
        }

        // Change the speed of the media.
        private void ChangeMediaSpeedRatio(object sender, RoutedPropertyChangedEventArgs<double> args)
        {
            if (this.WebBrowserFull != null)
                this.WebBrowserFull.SpeedRatio = (double)this.speedRatioSlider.Value;
        }

        // Jump to different parts of the media (seek to). 
        private void SeekToMediaPosition(object sender, RoutedPropertyChangedEventArgs<double> args)
        {
            if (this.WebBrowserFull != null)
            {
                int SliderValue = (int)this.timelineSlider.Value;

                // Overloaded constructor takes the arguments days, hours, minutes, seconds, miniseconds.
                // Create a TimeSpan with miliseconds equal to the slider value.
                TimeSpan ts = new TimeSpan(0, 0, 0, 0, SliderValue);
                this.WebBrowserFull.Position = ts;
            }
        }

        void InitializePropertyValues()
        {
            // Set the media's starting Volume and SpeedRatio to the current value of the
            // their respective slider controls.
            this.WebBrowserFull.Volume = (double)this.volumeSlider.Value;
            this.WebBrowserFull.SpeedRatio = (double)this.speedRatioSlider.Value;
        }

    }


}
