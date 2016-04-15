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
using SoftCare.ClientApplication.ViewModels;
using System.ComponentModel;
using System.Configuration;
using SoftCare.ClientApplication.Properties;
using System.Reflection;

namespace SoftCare.ClientApplication.Controls
{
    /// <summary>
    /// Interaction logic for OptionsPage.xaml
    /// </summary>
    public partial class OptionsPage : UserControl, INotifyPropertyChanged
    {

        #region INotifyPropertyChanged Members

        public event PropertyChangedEventHandler PropertyChanged;

        protected void SendPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
        }

        #endregion
        public SoftCare.ClientApplication.aladdinService.SystemParameter DefaultLanguage { get; set; }

        public List<SoftCare.ClientApplication.aladdinService.SystemParameter> Languages { get; set; }

        public string Version { get; set; }

        public bool IsReadOnly
        {
            get
            {
                return App.OptionsLocked;
            }
        }

        public OptionsPage()
        {
            InitializeComponent();

            this.Version = AutoUpdater.GetAladdinClientVersion();

            this.DefaultLanguage = App.DefaultLanguage;
            this.Languages = new List<SoftCare.ClientApplication.aladdinService.SystemParameter>();
            SoftCare.ClientApplication.aladdinService.SystemParameter p1 = new SoftCare.ClientApplication.aladdinService.SystemParameter();
            p1.Code = "en_UK";
            p1.Description = "English";
            Languages.Add(p1);

            SoftCare.ClientApplication.aladdinService.SystemParameter p2 = new SoftCare.ClientApplication.aladdinService.SystemParameter();
            p2.Code = "es_ES";
            p2.Description = "Εspañol";
            Languages.Add(p2);

            SoftCare.ClientApplication.aladdinService.SystemParameter p3 = new SoftCare.ClientApplication.aladdinService.SystemParameter();

            p3.Code = "el_GR";
            p3.Description = "Ελληνικά";
            Languages.Add(p3);

            SoftCare.ClientApplication.aladdinService.SystemParameter p4 = new SoftCare.ClientApplication.aladdinService.SystemParameter();

            p4.Code = "it_IT";
            p4.Description = "Italiano";
            Languages.Add(p4);

            SoftCare.ClientApplication.aladdinService.SystemParameter p5 = new SoftCare.ClientApplication.aladdinService.SystemParameter();

            p5.Code = "de_DE";
            p5.Description = "Deutsch";
            Languages.Add(p5);

            SoftCare.ClientApplication.aladdinService.SystemParameter p6 = new SoftCare.ClientApplication.aladdinService.SystemParameter();

            p6.Code = "ca_ES";
            p6.Description = "Catalan";
            Languages.Add(p6);

            this.serverAddress.Text = App.ServerAddress;
            this.updatesAddress.Text = App.UpdatesAddress;
            this.forumAddress.Text = App.ForumPage;
            this.DataContext = this;
        }


        private void CommandBinding_Executed(object sender, ExecutedRoutedEventArgs e)
        {
            e.Handled = true;

            if (AppCommands.SaveCommand.Equals(e.Command))
            {

                App.DefaultLanguage = this.DefaultLanguage;
                App.ServerAddress = this.serverAddress.Text;
                App.UpdatesAddress = this.updatesAddress.Text;
                App.ForumPage = this.forumAddress.Text;

                /////////////////////////////
                // PARCHE TEMPORAL
                // TODO
                //Settings.Default.Aladdin_ClientApplication_aladdinService_StorageComponentImplService = App.ServerAddress;
                /////////////////////////////
                Settings.Default.UpdatesUri = App.UpdatesAddress;
                Settings.Default.DefaultLanguage = App.DefaultLanguage.Code;
                Settings.Default.ForumPage = App.ForumPage;
                Settings.Default.Save();

                System.Windows.Forms.Application.Restart();
                App.ThisApp.Shutdown();
            }

            if (AppCommands.LockCommand.Equals(e.Command))
            {
                Settings.Default.OptionsLocked = (!Settings.Default.OptionsLocked);
                Settings.Default.Save();

                System.Windows.Forms.Application.Restart();
                App.ThisApp.Shutdown();
            }
        }

        private void CommandBinding_CanExecute(object sender, CanExecuteRoutedEventArgs e)
        {
            if (AppCommands.SaveCommand.Equals(e.Command))
                e.CanExecute = (this.languageCombo.SelectedValue != null && this.serverAddress.Text.Length > 0);
            else
                e.CanExecute = true;
        }
    }
}
