using SoftCare.ClientApplication.Controls;
using SoftCare.ClientApplication.Properties;
using SoftCare.ClientApplication.ViewModels;
using System;
using System.Configuration;
using System.Windows;
using System.Windows.Input;


namespace SoftCare.ClientApplication.Windows
{


    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {


        MainWindowViewModel ViewModel { get; set; }


        /// <summary>
        /// 
        /// </summary>
        public MainWindow()
        {
            this.ViewModel = new MainWindowViewModel();
            this.DataContext = this.ViewModel;
            InitializeComponent();
            SetLanguageDictionary();
            App.LoginErrorMessage = (string)this.Resources.MergedDictionaries[0]["WrongUsernameOrPassword"];
            App.WellcomeMessage = (string)this.Resources.MergedDictionaries[0]["Wellcome"];
            App.WellcomeALADDINMessage = (string)this.Resources.MergedDictionaries[0]["WellcomeALADDIN"];
            App.DataReadMsg = (string)this.Resources.MergedDictionaries[0]["DataReadMsg"];
            App.StepsMsg = (string)this.Resources.MergedDictionaries[0]["StepsMsg"];
            App.PressTheSendButtonMsg = (string)this.Resources.MergedDictionaries[0]["PressTheSendButtonMsg"];
            App.ErrorReadingMsg = (string)this.Resources.MergedDictionaries[0]["ErrorReadingMsg"];
            App.DeviceNotFoundMsg = (string)this.Resources.MergedDictionaries[0]["DeviceNotFoundMsg"];
            App.DisclaimerText = (string)this.Resources.MergedDictionaries[0]["Disclaimer"];
            App.HasDisclaimer = !App.DisclaimerText.Equals("EMPTY");
            App.ForumPage = ConfigurationManager.AppSettings["URL.Forum"];  //Settings.Default.ForumPage;
            this.ViewModel.CurrentUserInfo = null;
            App.LoggedIn += new LoggedInEventHandler(App_LoggedIn);
            // set window title
            this.Title = Config.APP_NAME;
            // replace welcome message
            App.WellcomeALADDINMessage = App.WellcomeALADDINMessage.Replace("ALADDIN", Config.APP_NAME);
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        void App_LoggedIn(object sender)
        {
            this.LoginMenu.Items.Refresh();
        }


        /// <summary>
        /// 
        /// </summary>
        private void SetLanguageDictionary()
        {
            ResourceDictionary dict = new ResourceDictionary();
            SoftCare.ClientApplication.aladdinService.SystemParameter defaultLanguage = new SoftCare.ClientApplication.aladdinService.SystemParameter();
            SoftCare.ClientApplication.aladdinService.SystemParameter locale = new SoftCare.ClientApplication.aladdinService.SystemParameter();
            /////////////////////////////
            // PARCHE TEMPORAL
            // TODO
            defaultLanguage.Code = "en_UK"; // Settings.Default.DefaultLanguage;
            /////////////////////////////
            App.DefaultLanguage = defaultLanguage;
            App.ServerAddress = Settings.Default.Aladdin_ClientApplication_aladdinService_StorageComponentImplService;
            App.OptionsLocked = Settings.Default.OptionsLocked;
            
            switch (App.DefaultLanguage.Code)
            {
                case "en_US":
                case "en_UK":
                    dict.Source = new Uri("..\\Resources\\StringResources.xaml", UriKind.Relative);
                    locale.Code = "en_UK";
                    break;
                case "es_ES":
                    dict.Source = new Uri("..\\Resources\\StringResources.es-ES.xaml", UriKind.Relative);
                    locale.Code = "es_ES";
                    break;
                case "el_GR":
                    dict.Source = new Uri("..\\Resources\\StringResources.el-GR.xaml", UriKind.Relative);
                    locale.Code = "el_GR";
                    break;
                case "it_IT":
                    dict.Source = new Uri("..\\Resources\\StringResources.it-IT.xaml", UriKind.Relative);
                    locale.Code = "it_IT";
                    break;
                case "de_DE":
                    dict.Source = new Uri("..\\Resources\\StringResources.de-DE.xaml", UriKind.Relative);
                    locale.Code = "de_DE";
                    break;
                case "ca_ES":
                    dict.Source = new Uri("..\\Resources\\StringResources.ca-ES.xaml", UriKind.Relative);
                    locale.Code = "ca_ES";
                    break;
                default:
                    dict.Source = new Uri("..\\Resources\\StringResources.xaml", UriKind.Relative);
                    locale.Code = "en_UK";
                    break;
            }
            App.DefaultLocale = locale;
            this.Resources.MergedDictionaries.Add(dict);
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MoveToPage_Executed(object sender, ExecutedRoutedEventArgs e)
        {
            e.Handled = true;

            if (e.Parameter is MediaContent)
            {
                MediaContent content = e.Parameter as MediaContent;
                if (content != null)
                    this.ViewModel.MoveToContentPage(content);
            }
            else if (e.Parameter is string)
            {
                this.ViewModel.MoveToPage(e.Parameter as string, null);
            }
            else if (e.Parameter is SoftCare.ClientApplication.aladdinService.Task)
            {
                SoftCare.ClientApplication.aladdinService.Task task = e.Parameter as SoftCare.ClientApplication.aladdinService.Task;
                int taskType = Convert.ToInt32(task.TaskType.Code);
                switch (taskType)
                {
                    case (int)Config.TaskTypesEnum.PatientQuestionnaire:
                        this.ViewModel.MoveToPage("WizardPage", task);
                        break;
                    case (int)Config.TaskTypesEnum.CarerQuestionnaire:
                        this.ViewModel.MoveToPage("WizardPage", task); ;
                        break;
                    case (int)Config.TaskTypesEnum.BloodPressureMeasurement:
                        this.ViewModel.MoveToPage("MeasureBloodPressurePage", task);
                        break;
                    case (int)Config.TaskTypesEnum.WeightMeasurement:
                        this.ViewModel.MoveToPage("MeasureWeightPage", task);
                        break;
                    case (int)Config.TaskTypesEnum.ActivityMonitor:
                        this.ViewModel.MoveToPage("MeasureActivityPage", task);
                        break;
                    case (int)Config.TaskTypesEnum.CognitiveGame:
                        this.ViewModel.MoveToPage("PlayGame", task);
                        break;
                    case (int)Config.TaskTypesEnum.Exercise:
                        this.ViewModel.MoveToPage("Exercise", task);
                        break;
                    case (int)Config.TaskTypesEnum.Message:
                        this.ViewModel.MoveToPage("MessagePage", task);
                        break;
                    default:
                        break;
                }
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void MoveToPageWithoutAuthenticationCommand(object sender, ExecutedRoutedEventArgs e)
        {
            e.Handled = true;
            if (e.Parameter is string)
                this.ViewModel.MoveToPage(e.Parameter as string, null);
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CommandBinding_Executed(object sender, ExecutedRoutedEventArgs e)
        {
            e.Handled = true;
            try
            {
                if (AppCommands.ExitApplicationCommand.Equals(e.Command))
                    this.ViewModel.ExitApplication();
            }
            catch (Exception) { }  
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void CommandBinding_CanExecute(object sender, CanExecuteRoutedEventArgs e)
        {
            e.Handled = true;
            e.CanExecute = App.IsUserAuthenticated;
        }


        //
        DateTime clickTime;

        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Menu_MouseRightButtonDown(object sender, MouseButtonEventArgs e)
        {
            clickTime = System.DateTime.Now;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Menu_MouseRightButtonUp(object sender, MouseButtonEventArgs e)
        {
            if (DateTime.Now.Subtract(clickTime).TotalSeconds > 3)
                this.ViewModel.MoveToPage("OptionsPage", null);
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Window_Closed_2(object sender, EventArgs e)
        {
            try
            {
                this.ViewModel.ExitApplication();
                this.Close();
            }
            catch (Exception) { }
        }


    }


}
