using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;

namespace Aladdin.DataModel
{
    /// <summary>
    /// Patient
    /// </summary>
    public class Patient
    {

        /// <summary>
        /// Basic demographic information e.g. Name, Surname, Address, Phone numbers etc.
        /// </summary>
        public PersonData PersonData { get; set; }

        /// <summary>
        ///	Social and demographic data
        /// </summary>
        public SocioDemographicData SD_Data { get; set; }


        /// <summary>
        /// The clinician responsible for the patient
        /// </summary>
        public Clinician ResponsibleClinician { get; set; }

        /// <summary>
        /// List of patient caregivers
        /// </summary>
        public Caregivers PatientCaregiverList { get; set; }

        ///// <summary>
        ///// List of patient assessment
        ///// </summary>
        //public Assessments Assessments { get; set; }


        public Patient()
        {
            this.PatientCaregiverList = new Caregivers();
            //this.Assessments = new Assessments();
        }

    }

    public class Caregivers
    {
        [XmlElement("PatientCaregiver", typeof(PatientCaregiver))]
        public List<PatientCaregiver> ListOfCaregivers { get; set; }

        public Caregivers()
        {
            this.ListOfCaregivers = new List<PatientCaregiver>();
        }
    }

    public class Assessments
    {
        [XmlElement("PatientAssessment", typeof(PatientAssessment))]
        public List<PatientAssessment> ListOfAssessments { get; set; }

        public Assessments()
        {
            this.ListOfAssessments = new List<PatientAssessment>();
        }
    }



}
