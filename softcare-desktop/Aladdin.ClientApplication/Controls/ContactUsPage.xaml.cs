using System;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Controls;
using System.Linq;


namespace SoftCare.ClientApplication.Controls
{


    /// <summary>
    /// Interaction logic for ContactUsPage.xaml
    /// </summary>
    public partial class ContactUsPage : UserControl
    {


        /// <summary>
        /// 
        /// </summary>
        public List<SoftCare.ClientApplication.aladdinService.SystemParameter> ContactSituations
        {
            get { return (List<SoftCare.ClientApplication.aladdinService.SystemParameter>)GetValue(ContactSituationsProperty); }
            set { SetValue(ContactSituationsProperty, value); }
        }


        // Using a DependencyProperty as the backing store for ContactSituations.  This enables animation, styling, binding, etc...
        public static readonly DependencyProperty ContactSituationsProperty =
            DependencyProperty.Register("ContactSituations", typeof(List<SoftCare.ClientApplication.aladdinService.SystemParameter>), typeof(ContactUsPage));

        

        public ContactUsPage()
        {
            SoftCare.ClientApplication.aladdinService.StorageComponentImplService sc = new SoftCare.ClientApplication.aladdinService.StorageComponentImplService();
            SoftCare.ClientApplication.aladdinService.SystemParameter[] _contactSituations = sc.GetSystemParameterList((int)Config.SystemParameterEnum.ContactReason, App.DefaultLocale);
            if (_contactSituations != null)
                this.ContactSituations = _contactSituations.ToList();
            else
            {
                _contactSituations = sc.GetSystemParameterList((int)Config.SystemParameterEnum.ContactReason, App.DefaultLocale);
                if (_contactSituations != null)
                    this.ContactSituations = _contactSituations.ToList();
            }
            this.DataContext = this;
            InitializeComponent();
        }


        /// <summary>
        /// SEND button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            // WARNING
            try
            {
                using (SoftCare.ClientApplication.aladdinService.StorageComponentImplService sc = new SoftCare.ClientApplication.aladdinService.StorageComponentImplService())
                {
                    SoftCare.ClientApplication.aladdinService.Warning userWarning = new SoftCare.ClientApplication.aladdinService.Warning();
                    userWarning.DateTimeOfWarning = System.DateTime.Now;
                    SoftCare.ClientApplication.aladdinService.SystemParameter typeOfWarning = new SoftCare.ClientApplication.aladdinService.SystemParameter();
                    typeOfWarning.Code = "1";
                    typeOfWarning.Description = "Manual";
                    userWarning.TypeOfWarning = typeOfWarning;
                    SoftCare.ClientApplication.aladdinService.SystemParameter situation = this.SituationComboBox.SelectedItem as SoftCare.ClientApplication.aladdinService.SystemParameter;
                    string situationStr = "";
                    if (situation != null)
                        situationStr = situation.Description;

                    userWarning.Delivered = false;
                    userWarning.JustificationText = string.Format("Situation:{0}, Description:{1}", situationStr, this.DescriptionBox.Text);
                    SoftCare.ClientApplication.aladdinService.Patient patient = new SoftCare.ClientApplication.aladdinService.Patient();
                    patient.ID = App.PatientID;
                    userWarning.Patient = patient;
                    SoftCare.ClientApplication.aladdinService.OperationResult res = sc.SaveWarning(userWarning, App.CurrentUserID);
                    AppCommands.MoveToPageCommand.Execute("StartPage", null);
                }
            }
            catch (Exception ex) {
                MessageBox.Show("Error : " + ex.Message, Config.APP_NAME, MessageBoxButton.OK, MessageBoxImage.Error);
            }

            // SEND EMAIL
            try
            {
                SoftCare.ClientApplication.thirdpartyService.ServicesComponentImplService tps = new SoftCare.ClientApplication.thirdpartyService.ServicesComponentImplService();
                // String subject, String txt, String sendTo
                tps.sendEmail("Healthcare desktop client application", this.DescriptionBox.Text, App.PatientID);
            }
            catch (Exception ex) {
                MessageBox.Show("Error : " + ex.Message, Config.APP_NAME, MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }


    }


}
