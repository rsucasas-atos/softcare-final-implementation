using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Input;


namespace SoftCare.ClientApplication
{


    public static class AppCommands
    {


        public static RoutedUICommand MoveToPageCommand = new RoutedUICommand();
        public static RoutedUICommand MoveToLoginPageCommand = new RoutedUICommand();
        public static RoutedUICommand MoveToPageWithoutAuthenticationCommand = new RoutedUICommand();
        public static RoutedUICommand OptionsCommand = new RoutedUICommand();
        public static RoutedUICommand LoginCommand = new RoutedUICommand();
        public static RoutedUICommand SaveCommand = new RoutedUICommand();
        public static RoutedUICommand ExitApplicationCommand = new RoutedUICommand();
        public static RoutedUICommand QuetionnaireMoveNextCommand = new RoutedUICommand();
        public static RoutedUICommand QuetionnaireMoveBackCommand = new RoutedUICommand();
        public static RoutedUICommand UploadQuestionnaireCommand = new RoutedUICommand();
        public static RoutedUICommand SendMeasurementCommand = new RoutedUICommand();
        public static RoutedUICommand ReadActivityDataCommand = new RoutedUICommand();
        public static RoutedCommand OpenMediaContentSectionCommand = new RoutedCommand();
        public static RoutedCommand LockCommand = new RoutedCommand();


    }


}
