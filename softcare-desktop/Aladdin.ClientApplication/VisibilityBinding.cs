using System;
using System.Collections.Generic;
using System.Windows.Data;
using System.Text;


namespace SoftCare.ClientApplication
{


    /// <summary>
    /// 
    /// </summary>
    public class VisibilityBinding : Binding, IValueConverter
    {


        public VisibilityBinding()
        {
            NullVisible = true;
            Converter = this;
        }


        public VisibilityBinding(string path) : base(path)
        {
            NullVisible = true;
            Converter = this;
        }


        public bool NullVisible { get; set; }


        #region IValueConverter Members

        public object Convert(object value, Type targetType, object parameter, System.Globalization.CultureInfo culture)
        {
            if (value == null) return NullVisible ? System.Windows.Visibility.Visible : System.Windows.Visibility.Collapsed;
            if (targetType == typeof(System.Windows.Visibility) &&
                (value.GetType() == typeof(bool) || value.GetType() == typeof(bool?)))
            {
                if ((bool?)value == false) return System.Windows.Visibility.Collapsed;
                return System.Windows.Visibility.Visible;
            }
            return value;
        }

        public object ConvertBack(object value, Type targetType, object parameter, System.Globalization.CultureInfo culture)
        {
            if (value == null) return null;
            if ((targetType == typeof(bool?) || targetType == typeof(bool))
                && value.GetType() == typeof(System.Windows.Visibility))
            {
                if ((System.Windows.Visibility)value == System.Windows.Visibility.Collapsed) return false;
                return true;
            }
            return value;
        }

        #endregion
   
    
    }


}
