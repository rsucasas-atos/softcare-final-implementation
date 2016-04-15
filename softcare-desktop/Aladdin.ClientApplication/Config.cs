using SoftCare.ClientApplication.aladdinService;
using SoftCare.ClientApplication.Properties;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Windows;


namespace SoftCare.ClientApplication
{


    /// <summary>
    /// 
    /// </summary>
    public class Config
    {


        // GLOBALS - CONSTANTS
        // multimedia categories
        public const string CATEGORY_VIDEOS = "videos";         // old: education 
        public const string CATEGORY_VIDEOSWEB = "videos_web";  // old: games
        public const string CATEGORY_MUSIC = "music";           // old: entertainment
        public const string CATEGORY_BOOKS = "books";
        public const string CATEGORY_GAMES = "games";
        public const string CATEGORY_IMGS = "images";
        public const string CATEGORY_OTHER = "other";


        // communication types:
        public static string COMMUNICATION_PHONE = "phone";
        public static string COMMUNICATION_EMAIL = "email";
        public static string COMMUNICATION_OTHER = "other";


        // TaskType: 1 = Patient Questionnaire, 2 = Carer Questionnaire, 3 = Blood pressure measurement, 
        //           4 = weight measurement, 5 = Cognitive game, 6 = Activity Monitor, 7 = Exercise.
        public enum TaskTypesEnum
        {
            PatientQuestionnaire = 1,
            CarerQuestionnaire = 2,
            BloodPressureMeasurement = 3,
            WeightMeasurement = 4,
            CognitiveGame = 5,
            ActivityMonitor = 6,
            Exercise = 7,
            Message = 8
        }


        public enum TaskStatusEnum
        {
            Pending = 1,
            Canceled = 2,
            Completed = 3
        }


        public enum MeasurementTypeEnum
        {
            SystolicBloodPressure = 11,
            DiastolicBloodPressure = 12,
            Weight = 2,
            Activity = 3
        }


        public enum SystemParameterEnum
        {
            Gender = 1,
            MaritalStatus = 2,
            LivingWith = 3,
            MeasurementType = 4,
            TaskType = 5,
            TaskStatus = 6,
            WarningType = 7,
            WarningEffect = 8,
            WarningIndicator = 9,
            WarningRiskLevel = 10,
            WarningEmergencyLevel = 11,
            ContactReason = 12
        }

        // global proerties map - values from database
        public static Dictionary<string, string> ROLES_DICTIONARY;
        // user types:
        public static string USERTYPE_ADMIN = "1";
        public static string USERTYPE_CLINICIAN = "2";
        public static string USERTYPE_CARER = "3";
        public static string USERTYPE_PATIENT = "4";

        // global proerties map - values from database
        public static Dictionary<string, string> PROPERTIES_DICTIONARY;
	    // Keys values
	    public static string PROPERTY_WEIGHT_MIN = "WEIGHT_MIN";
	    public static string PROPERTY_WEIGHT_MAX = "WEIGHT_MAX";
	    public static string PROPERTY_BLOOD_SISTOLIC_MIN = "BLOOD_SISTOLIC_MIN";
	    public static string PROPERTY_BLOOD_SISTOLIC_MAX = "BLOOD_SISTOLIC_MAX";
	    public static string PROPERTY_BLOOD_DIASTOLIC_MIN = "BLOOD_DIASTOLIC_MIN";
	    public static string PROPERTY_BLOOD_DIASTOLIC_MAX = "BLOOD_DIASTOLIC_MAX";
	    public static string PROPERTY_LOGIN_ATTEMPTS = "LOGIN_ATTEMPTS";

        public static string TASK_STATUS_PENDING = "TASK_STATUS_PENDING";
        public static string TASK_STATUS_CANCELED = "TASK_STATUS_CANCELED";
        public static string TASK_STATUS_COMPLETED = "TASK_STATUS_COMPLETED";


        public static string APP_NAME = "E-Health Application";


        /// <summary>
        /// static constructor
        /// </summary>
        static Config()
        {
            // Get VALUES / PROPERTIES
            try
            {
                APP_NAME = ConfigurationManager.AppSettings["AppName"];

                // load database properties values
                SoftCare.ClientApplication.aladdinService.StorageComponentImplService sc = new SoftCare.ClientApplication.aladdinService.StorageComponentImplService();
                stringArray[] res1 = sc.getEhealthParameters();

                if ((res1 != null) && (res1.Length > 0))
                {
                    PROPERTIES_DICTIONARY = new Dictionary<string, string>();

                    for (int i = 0; i < res1.Length; i++)
                    {
                        PROPERTIES_DICTIONARY.Add(res1[i].item[0], res1[i].item[1]);
                    }
                }

                // roles
                stringArray[] res2 = sc.getEhealthRoles();

                if ((res2 != null) && (res2.Length > 0))
                {
                    ROLES_DICTIONARY = new Dictionary<string, string>();

                    for (int i = 0; i < res2.Length; i++)
                    {
                        ROLES_DICTIONARY.Add(res2[i].item[0], res2[i].item[1]);
                    }
                }
            }
            catch (Exception ex) 
            {
                MessageBox.Show("Error : " + ex.Message, Config.APP_NAME, MessageBoxButton.OK, MessageBoxImage.Error);
            }

            // Set VALUES / PROPERTIES from Database
            try
            {
                USERTYPE_ADMIN = ROLES_DICTIONARY["ADMIN"];
                USERTYPE_CLINICIAN = ROLES_DICTIONARY["CLINICIAN"];
                USERTYPE_CARER = ROLES_DICTIONARY["CARER"];
                USERTYPE_PATIENT = ROLES_DICTIONARY["PATIENT"];
            }
             catch (Exception ex) 
            {
                MessageBox.Show("Error : " + ex.Message, Config.APP_NAME, MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

    }


}
