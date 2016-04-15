using SoftCare.ClientApplication.Controls;
using SoftCare.ClientApplication.Properties;
using SoftCare.ClientApplication.Windows;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Net.Security;
using System.Security.Cryptography.X509Certificates;
using System.Windows;


namespace SoftCare.ClientApplication
{


    public delegate void LoggedInEventHandler(object sender);


    /// <summary>
    /// Interaction logic for App.xaml
    /// </summary>
    public partial class App : Application
    {


        // properties
        public static App ThisApp { get; private set; }
        private static bool _IsUserAuthenticated;
        public static bool IsUserAuthenticated
        {
            get
            {
                return _IsUserAuthenticated;
            }

            set
            {
                _IsUserAuthenticated = value;
                if (App.LoggedIn != null)
                    App.LoggedIn(null);
            }
        }
        public static string DisclaimerText;
        public static bool HasDisclaimer { get; set; }
        public static string CurrentUserID;
        public static string CurrentUserName;
        public static string PatientID;
        public static SoftCare.ClientApplication.aladdinService.SystemParameter DefaultLanguage;
        public static SoftCare.ClientApplication.aladdinService.SystemParameter DefaultLocale;
        public static bool OptionsLocked { get; set; }
        public static string ServerAddress;
        public static string UpdatesAddress;
        public static string LoginErrorMessage { get; set; }
        public static string WellcomeMessage { get; set; }
        public static string WellcomeALADDINMessage { get; set; }
        public static List<SoftCare.ClientApplication.aladdinService.Task> ActiveTasks { get; set; }
        public static string PatientQuestionnaireTempPath { get; set; }
        public static event LoggedInEventHandler LoggedIn;
        public static string DataReadMsg;
        public static string StepsMsg;
        public static string PressTheSendButtonMsg;
        public static string ErrorReadingMsg;
        public static string DeviceNotFoundMsg;
        public static string ForumPage;

        // user type
        public static string UserType;

        // reference to multimedia repository
        public static NetConfigGlobals MultimediaREST_API;
        // multimedia content
        public static MediaContent[] VideosContent;     // "videos"
        public static MediaContent[] MusicContent;      // "music"
        public static MediaContent[] VideosWebContent;  // "videos_web"
        public static MediaContent[] BooksContent;      // "books"
        public static MediaContent[] GamesContent;      // "games"


        /// <summary>
        /// 
        /// </summary>
        public App()
        {
            System.Net.ServicePointManager.ServerCertificateValidationCallback +=
                delegate(object sender, X509Certificate cert, X509Chain chain, SslPolicyErrors sslError)
                {
                    bool validationResult = true;
                    return validationResult;
                };

            this.DispatcherUnhandledException += 
                new System.Windows.Threading.DispatcherUnhandledExceptionEventHandler(OnDispatcherUnhandledException);

            // add reference
            App.ThisApp = this;

            // Initialize components
            this.InitializeComponent();

            // avialable media content
            LoadContent();

            // load main window
            MainWindow main = new MainWindow();
        }


        /// <summary>
        /// 
        /// </summary>
        private void LoadContent()
        {
            MultimediaREST_API = new NetConfigGlobals();

            VideosContent = MultimediaREST_API.MultimediaRepoVideos;
            VideosWebContent = MultimediaREST_API.MultimediaRepoVideosWeb;
            MusicContent = MultimediaREST_API.MultimediarepoMusic;
            BooksContent = MultimediaREST_API.MultimediarepoBooks;
            GamesContent = null;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected virtual void OnDispatcherUnhandledException(object sender, System.Windows.Threading.DispatcherUnhandledExceptionEventArgs e)
        {
            e.Handled = true;
            MessageBox.Show(e.Exception.Message, Config.APP_NAME, MessageBoxButton.OKCancel, MessageBoxImage.Error);
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Application_Exit(object sender, ExitEventArgs e)
        {
            Settings.Default.Save();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="e"></param>
        protected override void OnStartup(StartupEventArgs e)
        {
            // Get Reference to the current Process
            Process thisProc = Process.GetCurrentProcess();
            // Check how many total processes have the same name as the current one
            if (Process.GetProcessesByName(thisProc.ProcessName).Length > 1)
            {
                // If ther is more than one, than it is already running.
                MessageBox.Show("Softcare Client Application is already running.");
                Application.Current.Shutdown();
                return;
            }

            base.OnStartup(e);
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        public static SoftCare.ClientApplication.aladdinService.Task getTaskById(string id)
        {
            try
            {
                SoftCare.ClientApplication.aladdinService.Task t = new SoftCare.ClientApplication.aladdinService.Task();

                if (App.ActiveTasks != null)
                {
                    for (int i=0; i<App.ActiveTasks.Count; i++) 
                    {
                        if (App.ActiveTasks[i].ID.Equals(id))
                        {
                            return App.ActiveTasks[i];
                        }
                    }
                }

                return t;
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error : " + ex.Message, Config.APP_NAME, MessageBoxButton.OK, MessageBoxImage.Error);
                return null;
            }
        }
       

    }


}
