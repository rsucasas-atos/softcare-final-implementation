using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace Aladdin.RiskDetection
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            GetMeasurements();
        }

        const int Weight = 2;
        const int SystolicBloodPressure = 11;
        const int DiastolicBloodPressure = 12;
        

        private void GetMeasurements()
        {
            List<aladdinSC.Measurement> measurements = GetMeasurementsForPatient("11", new DateTime(2010, 11, 11), new DateTime(2010, 12, 31), "42");
            foreach (aladdinSC.Measurement m in measurements.OrderBy(m=>m.DateTime))
                System.Diagnostics.Trace.WriteLine(string.Format("{0}, {1}", m.Type.Code, m.Value));
        }

        private static List<aladdinSC.Measurement> GetMeasurementsForPatient(string patientID, DateTime dateFrom, DateTime dateTo, string userID)
        {
            using (aladdinSC.StorageComponent sc = new aladdinSC.StorageComponent())
            {

                aladdinSC.QuestionnaireAnswers[] answers = sc.GetQuestionnaireAnswers("43", new DateTime(2010, 11, 1), new DateTime(2010, 12, 1), "41");
                List<aladdinSC.Measurement> measurements = new List<aladdinSC.Measurement>();
                // get Weight measurements for patient
                List<aladdinSC.Measurement> weightMeasurements = sc.GetPatientMeasurement(patientID, Weight, dateFrom, dateTo, userID).ToList();
                measurements.AddRange(weightMeasurements);
                // get Systolic Pressure measurements for patient
                List<aladdinSC.Measurement> systolicMeasurements = sc.GetPatientMeasurement(patientID, SystolicBloodPressure, dateFrom, dateTo, userID).ToList();
                measurements.AddRange(systolicMeasurements);
                // get Diastolic Pressure measurements for patient
                List<aladdinSC.Measurement> diastolicMeasurements = sc.GetPatientMeasurement(patientID, DiastolicBloodPressure, dateFrom, dateTo, userID).ToList();
                measurements.AddRange(diastolicMeasurements);
                return measurements;
            }
        }
    }
}
