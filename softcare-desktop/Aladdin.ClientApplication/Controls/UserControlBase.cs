using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Controls;
using System.Windows;
using System.Windows.Input;

namespace SoftCare.ClientApplication
{
    public class UserControlBase : UserControl
    {
        public IInputElement FocusedElement
        {
            get { return (IInputElement)GetValue(FocusedElementProperty); }
            set { SetValue(FocusedElementProperty, value); }
        }

        // Using a DependencyProperty as the backing store for FocusedElement.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty FocusedElementProperty =
            DependencyProperty.Register("FocusedElement", typeof(IInputElement), typeof(UserControl));

        public UserControlBase()
        {
            this.Loaded += new RoutedEventHandler(UserControlBase_Loaded);
        }

        void UserControlBase_Loaded(object sender, RoutedEventArgs e)
        {
            if (FocusedElement != null)
                FocusedElement.Focus();
        }
    }
}
