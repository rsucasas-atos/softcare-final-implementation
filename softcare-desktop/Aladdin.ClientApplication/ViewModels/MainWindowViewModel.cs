using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ComponentModel;
using SoftCare.ClientApplication.Controls;
using System.Windows;


namespace SoftCare.ClientApplication.ViewModels
{


    /// <summary>
    /// 
    /// </summary>
    class MainWindowViewModel : INotifyPropertyChanged
    {


        #region INotifyPropertyChanged Members

        public event PropertyChangedEventHandler PropertyChanged;

        protected void SendPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
        }

        #endregion

        // date
        public string CurrentDateInfo { get; set; }
        // user
        private string _CurrentUserInfo = "";


        /// <summary>
        /// 
        /// </summary>
        public string CurrentUserInfo
        {
            get
            {
                if (App.IsUserAuthenticated)
                    _CurrentUserInfo = string.Format("{0}, {1}", App.WellcomeALADDINMessage, App.CurrentUserName);
                else
                    _CurrentUserInfo = App.WellcomeALADDINMessage;

                if (_CurrentUserInfo == null)
                {
                    _CurrentUserInfo = "-Not logged-";
                }

                _CurrentUserInfo = _CurrentUserInfo.Replace("ALADDIN", Config.APP_NAME);

                return _CurrentUserInfo;
            }
            set
            {
                _CurrentUserInfo = value;
                SendPropertyChanged("CurrentUserInfo");
            }
        }

        //
        public SoftCare.ClientApplication.aladdinService.Task ActiveTask { get; set; }

        //
        object _ActivePage;
        public object ActivePage
        {
            get { return _ActivePage; }
            set
            {
                if (_ActivePage != value)
                {
                    _ActivePage = value;
                    this.SendPropertyChanged("ActivePage");
                }
            }
        }

        LoginPage _LoginPage;
        LoginPage LoginPage
        {
            get
            {
                if (_LoginPage == null)
                    _LoginPage = new LoginPage();
                return _LoginPage;
            }
        }

        StartPage _StartPage;
        StartPage StartPage
        {
            get
            {
                if (_StartPage == null)
                    _StartPage = new StartPage();
                return _StartPage;
            }
        }

        MyTasksPage _MyTasksPage;
        MyTasksPage MyTasksPage
        {
            get
            {
                if (_MyTasksPage == null)
                    _MyTasksPage = new MyTasksPage();
                return _MyTasksPage;
            }
        }

        WizardPage _WizardPage;
        WizardPage WizardPage
        {
            get
            {
                _WizardPage = new WizardPage(this.ActiveTask);
                return _WizardPage;
            }
        }

        MeasureWeightPage _MeasureWeightPage;
        MeasureWeightPage MeasureWeightPage
        {
            get
            {
                _MeasureWeightPage = new MeasureWeightPage(this.ActiveTask);
                return _MeasureWeightPage;
            }
        }

        MeasureActivityPage _MeasureActivityPage;
        MeasureActivityPage MeasureActivityPage
        {
            get
            {
                _MeasureActivityPage = new MeasureActivityPage(this.ActiveTask);
                return _MeasureActivityPage;
            }
        }

        MeasureBloodPressurePage _MeasureBloodPressurePage;
        MeasureBloodPressurePage MeasureBloodPressurePage
        {
            get
            {
                _MeasureBloodPressurePage = new MeasureBloodPressurePage(this.ActiveTask);
                return _MeasureBloodPressurePage;
            }
        }

        MediaContentPage _MediaContentPage;
        MediaContentPage MediaContentPage
        {
            get
            {
                if (_MediaContentPage == null)
                    _MediaContentPage = new MediaContentPage();
                return _MediaContentPage;
            }
        }

        ContactUsPage _ContactUsPage;
        ContactUsPage ContactUsPage
        {
            get
            {
                if (_ContactUsPage == null)
                    _ContactUsPage = new ContactUsPage();
                return _ContactUsPage;
            }
        }

        ExternalServicePage _ExternalServicePage;
        ExternalServicePage ExternalServicePage
        {
            get
            {
                _ExternalServicePage = new ExternalServicePage(this.ActiveTask);
                return _ExternalServicePage;
            }
        }

        ExercisePage _ExercisePage;
        ExercisePage ExercisePage
        {
            get
            {
                _ExercisePage = new ExercisePage(this.ActiveTask);
                return _ExercisePage;
            }
        }

        MessagePage _MessagePage;
        MessagePage MessagePage
        {
            get
            {
                _MessagePage = new MessagePage(this.ActiveTask);
                return _MessagePage;
            }
        }

        OptionsPage _OptionsPage;
        OptionsPage OptionsPage
        {
            get
            {
                if (_OptionsPage == null)
                    _OptionsPage = new OptionsPage();
                return _OptionsPage;
            }
        }

        MediaContentSectionPage _MediaContentSectionPage;
        MediaContentSectionPage MediaContentSectionPage
        {
            get
            {
                if (_MediaContentSectionPage == null)
                    _MediaContentSectionPage = new MediaContentSectionPage(this.ContentCategory);
                return _MediaContentSectionPage;
            }
        }


        public string ContentCategory { get; set; }

        public bool IsUserAuthenticatedFlag
        {
            get
            {
                return App.IsUserAuthenticated;
            }
        }

        public bool IsUserNotAuthenticatedFlag
        {
            get
            {
                return !App.IsUserAuthenticated;
            }
        }

        public Visibility IsUserAuthenticated
        {
            get
            {
                if (App.IsUserAuthenticated)
                    return Visibility.Visible;
                else
                    return Visibility.Collapsed;
            }
        }

        public Visibility IsNotUserAuthenticated
        {
            get
            {
                if (!App.IsUserAuthenticated)
                    return Visibility.Visible;
                else
                    return Visibility.Collapsed;
            }
        }
        

        /// <summary>
        /// 
        /// </summary>
        public MainWindowViewModel()
        {
            try
            {
                this.CurrentDateInfo = DateTime.Now.ToString("dd/MM/yyyy");
                App.LoggedIn += new LoggedInEventHandler(App_LoggedIn);
                this.ActivePage = this.StartPage;
            }
            catch (Exception) { }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        void App_LoggedIn(object sender)
        {
            this.SendPropertyChanged("CurrentUserInfo");
            this.SendPropertyChanged("IsUserAuthenticatedFlag");
            this.SendPropertyChanged("IsUserNotAuthenticatedFlag");
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="content"></param>
        internal void MoveToContentPage(MediaContent content)
        {
            if (content.category.Equals(Config.CATEGORY_VIDEOS))
            {
                ContentPage page = new ContentPage(content);
                this.ActivePage = page;
            }
            else if (content.category.Equals(Config.CATEGORY_MUSIC))
            {
                ContentPageMusic page = new ContentPageMusic(content);
                this.ActivePage = page;
            }
            else
            {
                WebContentPage page = new WebContentPage(content);
                this.ActivePage = page;
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="to"></param>
        /// <param name="activeTask"></param>
        internal void MoveToPage(string to, SoftCare.ClientApplication.aladdinService.Task activeTask)
        {
            object page = null;
            this.ActiveTask = activeTask;

            if (!string.IsNullOrEmpty(to))
            {
                switch (to)
                {
                    case Config.CATEGORY_VIDEOS:
                        page = this.MediaContentSectionPage;
                        (page as MediaContentSectionPage).Category = Config.CATEGORY_VIDEOS;
                        break;
                    case Config.CATEGORY_VIDEOSWEB:
                        page = this.MediaContentSectionPage;
                        (page as MediaContentSectionPage).Category = Config.CATEGORY_VIDEOSWEB;
                        break;
                    case Config.CATEGORY_BOOKS:
                        page = this.MediaContentSectionPage;
                        (page as MediaContentSectionPage).Category = Config.CATEGORY_BOOKS;
                        break;
                    case Config.CATEGORY_MUSIC:
                        page = this.MediaContentSectionPage;
                        (page as MediaContentSectionPage).Category = Config.CATEGORY_MUSIC;
                        break;
                    case "LoginPage":
                        if (App.IsUserAuthenticated)
                            App.IsUserAuthenticated = false;
                        page = this.LoginPage;
                        break;
                    case "MyTasksPage":
                        page = this.MyTasksPage;
                        break;
                    case "OptionsPage":
                        page = this.OptionsPage;
                        break;
                    case "WizardPage":
                        page = this.WizardPage;
                        break;
                    case "StartPage":
                        page = this.StartPage;
                        break;
                    case "MeasureWeightPage":
                        page = this.MeasureWeightPage;
                        break;
                    case "MeasureBloodPressurePage":
                        page = this.MeasureBloodPressurePage;
                        break;
                    case "MeasureActivityPage":
                        page = this.MeasureActivityPage;
                        break;
                    case "MediaContentPage":
                        page = this.MediaContentPage;
                        break;
                    case "ContactUsPage":
                        page = this.ContactUsPage;
                        break;
                    case "PlayGame":
                        page = this.ExternalServicePage;
                        break;
                    case "SocialNetwork":
                        page = this.ExternalServicePage;
                        (page as ExternalServicePage).Url = App.ForumPage;
                        break;
                    case "MessagePage":
                        page = this.MessagePage;
                        break;
                    case "Exercise":
                        page = this.ExercisePage;
                        break;
                    default:
                        break;
                }
            }

            if (page != null)
                this.ActivePage = page;
        }


        /// <summary>
        /// 
        /// </summary>
        internal void ExitApplication()
        {
            Application.Current.Shutdown();
        }


    }


}
