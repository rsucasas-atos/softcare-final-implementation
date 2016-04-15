﻿using SoftCare.ClientApplication.ViewModels;
using SoftCare.ClientApplication.Windows;
using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;


namespace SoftCare.ClientApplication.Controls
{


    /// <summary>
    /// Interaction logic for ContentPage.xaml
    /// </summary>
    public partial class ContentPage : UserControl
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
        public ContentPage(SoftCare.ClientApplication.Controls.MediaContent content)
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

                this.WebBrowser.Play();
                InitializePropertyValues();
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
                FullviewVideosWindow fullviewWindow = new FullviewVideosWindow(this.Url);
                fullviewWindow.ShowDialog();
            }
            catch (Exception) { }
        }


        // Play the media.
        void OnMouseDownPlayMedia(object sender, MouseButtonEventArgs args)
        {
            this.WebBrowser.Play();
        }

        // Pause the media.
        void OnMouseDownPauseMedia(object sender, MouseButtonEventArgs args)
        {
            this.WebBrowser.Pause();
        }

        // Stop the media.
        void OnMouseDownStopMedia(object sender, MouseButtonEventArgs args)
        {
            this.WebBrowser.Stop();
        }


        // When the media opens, initialize the "Seek To" slider maximum value
        // to the total number of miliseconds in the length of the media clip.
        private void Element_MediaOpened(object sender, EventArgs e)
        {
            this.timelineSlider.Maximum = this.WebBrowser.NaturalDuration.TimeSpan.TotalMilliseconds;
            //this.WebBrowser.Play();
        }

        // When the media playback is finished. Stop() the media to seek to media start.
        private void Element_MediaEnded(object sender, EventArgs e)
        {
            this.WebBrowser.Stop();
        }


        // Change the volume of the media.
        private void ChangeMediaVolume(object sender, RoutedPropertyChangedEventArgs<double> args)
        {
            if (this.WebBrowser != null)
                this.WebBrowser.Volume = (double)this.volumeSlider.Value;
        }

        // Change the speed of the media.
        private void ChangeMediaSpeedRatio(object sender, RoutedPropertyChangedEventArgs<double> args)
        {
            if (this.WebBrowser != null)
                this.WebBrowser.SpeedRatio = (double)this.speedRatioSlider.Value;
        }

        // Jump to different parts of the media (seek to). 
        private void SeekToMediaPosition(object sender, RoutedPropertyChangedEventArgs<double> args)
        {
            if (this.WebBrowser != null)
            {
                int SliderValue = (int)this.timelineSlider.Value;

                // Overloaded constructor takes the arguments days, hours, minutes, seconds, miniseconds.
                // Create a TimeSpan with miliseconds equal to the slider value.
                TimeSpan ts = new TimeSpan(0, 0, 0, 0, SliderValue);
                this.WebBrowser.Position = ts;
            }
        }

        void InitializePropertyValues()
        {
            // Set the media's starting Volume and SpeedRatio to the current value of the
            // their respective slider controls.
            this.WebBrowser.Volume = (double)this.volumeSlider.Value;
            this.WebBrowser.SpeedRatio = (double)this.speedRatioSlider.Value;
        }


    }


}