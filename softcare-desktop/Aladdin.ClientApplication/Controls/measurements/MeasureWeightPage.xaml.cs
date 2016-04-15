using SoftCare.ClientApplication.ViewModels;
using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;


namespace SoftCare.ClientApplication.Controls
{


    /// <summary>
    /// Interaction logic for MeasureWeightBloodPage.xaml
    /// </summary>
    public partial class MeasureWeightPage : UserControl
    {


        MeasureWeightViewModel ViewModel { get; set; }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="activeTask"></param>
        public MeasureWeightPage(SoftCare.ClientApplication.aladdinService.Task activeTask)
        {
            this.ViewModel = new MeasureWeightViewModel(activeTask);
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
            if (AppCommands.SendMeasurementCommand.Equals(e.Command))
                this.ViewModel.SendMeasurements();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CommandBinding_CanExecute(object sender, CanExecuteRoutedEventArgs e)
        {
            e.Handled = true;
            e.CanExecute = this.ViewModel.CanSendMeasurements();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void TextBox_GotFocus(object sender, RoutedEventArgs e)
        {
            NumberPad pad = new NumberPad();
            pad.DisplayText = this.ViewModel.WeightText;
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
                if (this.ViewModel.WeightText.Length > 0)
                {
                    this.ViewModel.WeightText = this.ViewModel.WeightText.Substring(0, this.ViewModel.WeightText.Length - 1);
                }
                return;
            }
            else if (character.Equals(System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator))
                if (this.ViewModel.WeightText.Contains(character))
                    insert = false;
            if (insert)
            {
                if (character.Equals(System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator))
                    this.ViewModel.WeightText += System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator;
                else
                    this.ViewModel.WeightText += character;
            }
        }


    }


}
