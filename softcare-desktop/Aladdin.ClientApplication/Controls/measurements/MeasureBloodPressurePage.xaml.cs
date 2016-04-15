using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using SoftCare.ClientApplication.Windows;
using SoftCare.ClientApplication.ViewModels;


namespace SoftCare.ClientApplication.Controls
{


    /// <summary>
    /// Interaction logic for MeasureBloodPressure.xaml
    /// </summary>
    public partial class MeasureBloodPressurePage : UserControl
    {


        MeasureBloodPressureViewModel ViewModel { get; set; }



        public MeasureBloodPressurePage(SoftCare.ClientApplication.aladdinService.Task activeTask)
        {
            this.ViewModel = new MeasureBloodPressureViewModel(activeTask);
            this.DataContext = this.ViewModel;

            InitializeComponent();

            this.lbl1.Content = "systolic:  " + Config.PROPERTIES_DICTIONARY[Config.PROPERTY_BLOOD_SISTOLIC_MIN] + " - " +
                    Config.PROPERTIES_DICTIONARY[Config.PROPERTY_BLOOD_SISTOLIC_MAX] + " (mmHg)";
            this.lbl2.Content = "diastolic: " + Config.PROPERTIES_DICTIONARY[Config.PROPERTY_BLOOD_DIASTOLIC_MIN] + " - " +
                    Config.PROPERTIES_DICTIONARY[Config.PROPERTY_BLOOD_DIASTOLIC_MAX] + " (mmHg)";
        }



        private void CommandBinding_Executed(object sender, ExecutedRoutedEventArgs e)
        {
            e.Handled = true;
            if (AppCommands.SendMeasurementCommand.Equals(e.Command))
                this.ViewModel.SendMeasurements();
        }



        private void CommandBinding_CanExecute(object sender, CanExecuteRoutedEventArgs e)
        {
            e.Handled = true;
            e.CanExecute = this.ViewModel.CanSendMeasurements();
        }



        private void DiastolicBloodPressure_GotFocus(object sender, RoutedEventArgs e)
        {
            NumberPad pad = new NumberPad();
            pad.DisplayText = this.ViewModel.DiastolicBloodPressureText;
            pad.NumberPadPressed += new NumberPadPressedEventHandler(DiastolicBloodPressure_NumberPadPressed);
            pad.NumberPadClosed += new NumberPadClosedEventHandler(pad_NumberPadClosed);
            pad.ShowDialog();
        }



        private void SystolicBloodPressure_GotFocus(object sender, RoutedEventArgs e)
        {
            NumberPad pad = new NumberPad();
            pad.DisplayText = this.ViewModel.SystolicBloodPressureText;
            pad.NumberPadPressed += new NumberPadPressedEventHandler(SystolicBloodPressure_NumberPadPressed);
            pad.NumberPadClosed += new NumberPadClosedEventHandler(pad_NumberPadClosed);
            pad.ShowDialog();
        }



        /// <summary>
        /// Handles the close event from Numberpad.
        /// Enables the Next Button if introduced values are valid
        /// </summary>
        /// <param name="sender"></param>
        private void pad_NumberPadClosed(object sender)
        {
            this.NoButton.Focus();
        }



        private void DiastolicBloodPressure_NumberPadPressed(object sender, string character)
        {
            bool insert = true;
            if (character.Equals("BACKSPACE"))
            {
                if (this.ViewModel.DiastolicBloodPressureText.Length > 0)
                {
                    this.ViewModel.DiastolicBloodPressureText = this.ViewModel.DiastolicBloodPressureText.Substring(0, this.ViewModel.DiastolicBloodPressureText.Length - 1);
                }
                return;
            }
            else if (character.Equals(System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator))
                if (this.ViewModel.DiastolicBloodPressureText.Contains(character))
                    insert = false;
            if (insert)
            {
                if (character.Equals(System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator))
                    this.ViewModel.DiastolicBloodPressureText += System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator;
                else
                    this.ViewModel.DiastolicBloodPressureText += character;
            }
        }



        private void SystolicBloodPressure_NumberPadPressed(object sender, string character)
        {
            bool insert = true;
            if (character.Equals("BACKSPACE"))
            {
                if (this.ViewModel.SystolicBloodPressureText.Length > 0)
                {
                    this.ViewModel.SystolicBloodPressureText = this.ViewModel.SystolicBloodPressureText.Substring(0, this.ViewModel.SystolicBloodPressureText.Length - 1);
                }
                return;
            }
            else if (character.Equals(System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator))
                if (this.ViewModel.SystolicBloodPressureText.Contains(character))
                    insert = false;
            if (insert)
            {
                if (character.Equals(System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator))
                    this.ViewModel.SystolicBloodPressureText += System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator;
                else
                    this.ViewModel.SystolicBloodPressureText += character;
            }
        }


    }


}
