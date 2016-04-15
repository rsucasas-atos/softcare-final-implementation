using SoftCare.ClientApplication.ViewModels;
using System;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;


namespace SoftCare.ClientApplication.Controls
{


    /// <summary>
    /// Interaction logic for MeasureActivityPage.xaml
    /// </summary>
    public partial class MeasureActivityPage : UserControl
    {


        MeasureActivityViewModel ViewModel { get; set; }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="activeTask"></param>
        public MeasureActivityPage(SoftCare.ClientApplication.aladdinService.Task activeTask)
        {
            this.ViewModel = new MeasureActivityViewModel(activeTask);
            this.DataContext = this.ViewModel; 
            
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
            if (AppCommands.ReadActivityDataCommand.Equals(e.Command))
            {
                this.AcquireText.Foreground = Brushes.Blue;
                
                if (this.ViewModel.OmronConnect())
                {
                    Cursor = Cursors.Wait;

                    if (this.ViewModel.OmronReadData())
                    {
                        this.AcquireText.Foreground = Brushes.Green;
                        this.AcquireText.Text = String.Format("{0} ({1} {2}). \n{3}", App.DataReadMsg, this.ViewModel.GetTotaleSteps(), "steps", App.PressTheSendButtonMsg);
                    }
                    else
                    {
                        this.AcquireText.Foreground = Brushes.Red;
                        this.AcquireText.Text = App.ErrorReadingMsg;
                    }

                    Cursor = Cursors.Arrow;
                }
                else
                {
                    this.AcquireText.Foreground = Brushes.Red;
                    this.AcquireText.Text = App.DeviceNotFoundMsg;
                }
            }
            else if (AppCommands.SendMeasurementCommand.Equals(e.Command))
            {
                Cursor = Cursors.Wait;
                this.ViewModel.SendMeasurements();
                Cursor = Cursors.Arrow;
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CommandBinding_CanExecute(object sender, CanExecuteRoutedEventArgs e)
        {
            e.Handled = true;
            if (AppCommands.SendMeasurementCommand.Equals(e.Command))
                e.CanExecute = this.ViewModel.CanSendMeasurements();
            else
                e.CanExecute = true;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ManualActivity_GotFocus(object sender, System.Windows.RoutedEventArgs e)
        {
            NumberPad pad = new NumberPad();
            pad.DisplayText = this.ManualActivity.Text;
            pad.NumberPadPressed += new NumberPadPressedEventHandler(pad_NumberPadPressed);
            pad.NumberPadClosed += new NumberPadClosedEventHandler(pad_NumberPadClosed);
            pad.ShowDialog();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        void pad_NumberPadClosed(object sender)
        {
            if (this.ManualActivity.Text.Length > 0)
            {
                this.ViewModel.manuallyDataAvailable(true, Convert.ToInt32(this.ManualActivity.Text));
            }
            else
            {
                this.ViewModel.manuallyDataAvailable(false, 0);
            }

            this.NoButton.Focus();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="character"></param>
        void pad_NumberPadPressed(object sender, string character)
        {
            bool insert = true;
            if (character.Equals("BACKSPACE"))
            {
                if (this.ManualActivity.Text.Length > 0)
                {
                    this.ManualActivity.Text = this.ManualActivity.Text.Substring(0, this.ManualActivity.Text.Length - 1);
                }
                return;
            }
            else if (character.Equals(System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator))
                if (this.ManualActivity.Text.Contains(character))
                    insert = false;
            if (insert)
            {
                if (character.Equals(System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator))
                    this.ManualActivity.Text += System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator;
                else
                    this.ManualActivity.Text += character;
            }
        }


    }


}
