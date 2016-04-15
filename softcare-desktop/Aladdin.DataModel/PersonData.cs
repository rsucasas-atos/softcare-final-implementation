using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Aladdin.DataModel
{
    /// <summary>
    /// Contact information (Contact = any person involved in the ALADDIN platform, e.g. Patient, Caregiver, Clinician etc)
    /// </summary>
    public class PersonData
    {

        /// <summary>
        /// Unique Identifier (for ALADDIN)
        /// </summary>
        public Guid ID { get; set; }

        /// <summary>
        /// Contact Surname
        /// </summary>
        public string Surname { get; set; }

        /// <summary>
        /// Contact Name
        /// </summary>
        public string Name { get; set; }

        /// <summary>
        /// List of Contact Identification Numbers e.g. Passport ID, Police ID etc.
        /// </summary>
        public List<Identifier> IdentifierList { get; set; }

        /// <summary>
        /// List of Contact Addresses
        /// </summary>
        public List<Address> AddressList { get; set; }

        /// <summary>
        /// List of Contact Communication Means e.g. phone, mobile, e-mail etc.
        /// </summary>
        public List<Communication> CommunicationList { get; set; }
        

    }


    public class Identifier
    {

        public IdentifierType Type { get; set; }

        public string Number { get; set; }

        public string IssueDate { get; set; }

        public string IssueAuthority { get; set; }

    }



    public class Address
    {
        public string Street { get; set; }

        public string StreetNo { get; set; }

        public string City { get; set; }

        public string County { get; set; }

        public string Country { get; set; }

        public string ZipCode { get; set; }

        public string Notes { get; set; }

        public bool IsPrimary { get; set; }

    }


    public class Communication
    {
        /// <summary>
        /// Type of communication e.g. phone, mobile, email etc
        /// </summary>
        public CommunicationType Type { get; set; }

        /// <summary>
        /// The actual communication number or text, e.g. +302107722453, androu@biomed.ntua.gr, etc
        /// </summary>
        public string Value { get; set; }

        /// <summary>
        /// Notes about the communication, e.g. call only 9:00 am - 12:00 am
        /// </summary>
        public string Notes { get; set; }

        /// <summary>
        /// Is the primary means of communication (used when contact should be made)
        /// </summary>
        public bool IsPrimary { get; set; }

    }


    

}
