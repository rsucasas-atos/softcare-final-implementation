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
using System.Windows.Shapes;


namespace SoftCare.ClientApplication.Controls
{


    public delegate void NumberPadPressedEventHandler(object sender, string character);
    public delegate void NumberPadClosedEventHandler(object sender);


    /// <summary>
    /// Interaction logic for NumberPad.xaml
    /// </summary>
    public partial class NumberPad : Window
    {


        public event NumberPadPressedEventHandler NumberPadPressed;
        public event NumberPadClosedEventHandler NumberPadClosed;



        public string DisplayText
        {
            get { return (string)GetValue(DisplayTextProperty); }
            set { SetValue(DisplayTextProperty, value); }
        }


        // Using a DependencyProperty as the backing store for DisplayText.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty DisplayTextProperty =
            DependencyProperty.Register("DisplayText", typeof(string), typeof(NumberPad));


        public string DecimalSeparator { get; set; }



        public NumberPad()
        {
            InitializeComponent();
            this.DecimalSeparator = System.Threading.Thread.CurrentThread.CurrentCulture.NumberFormat.NumberDecimalSeparator;
            this.DataContext = this;
            // set window title
            this.Title = Config.APP_NAME;
        }



        private void CommandBinding_Executed(object sender, ExecutedRoutedEventArgs e)
        {
            e.Handled = true;
        }



        private void CommandBinding_CanExecute(object sender, CanExecuteRoutedEventArgs e)
        {
            e.Handled = true;
            e.CanExecute = true;
        }



        private void SetDisplayText(string character)
        {
            bool insert = true;
            if (character.Equals("BACKSPACE"))
            {
                if (this.DisplayText.Length > 0)
                {
                    this.DisplayText = this.DisplayText.Substring(0, this.DisplayText.Length - 1);
                }
                return;
            }
            else if (character.Equals(this.DecimalSeparator))
                if (this.DisplayText.Contains(character))
                    insert = false;
            if (insert)
            {
                if (character.Equals(this.DecimalSeparator))
                    this.DisplayText += this.DecimalSeparator;
                else
                    this.DisplayText += character;
            }
        }



        private void Button_Click(object sender, RoutedEventArgs e)
        {
            if (this.NumberPadPressed != null)
            {
                string text = ((e.OriginalSource as Button).Content as Label).Content as string;
                this.NumberPadPressed(this, text);
                SetDisplayText(text);
            }
        }



        private void Exit_Button_Click(object sender, RoutedEventArgs e)
        {
            this.Close();
            if (this.NumberPadClosed != null)
                this.NumberPadClosed(this);
        }



        private void Undo_Click(object sender, RoutedEventArgs e)
        {
            if (this.NumberPadPressed != null)
            {
                this.NumberPadPressed(this, "BACKSPACE");
                SetDisplayText("BACKSPACE");
            }
        }



        private void Window_Closed(object sender, EventArgs e)
        {
            if (this.NumberPadClosed != null)
                this.NumberPadClosed(this);
        }


    }


}
