using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Aladdin.DataModel
{
    public class PatientCaregiver
    {

        public Carer  Caregiver { get; set; }

        /// <summary>
        /// Is Primary Caregiver
        /// </summary>
        public bool IsPrimary { get; set; }


        public PatientCaregiver()
        {
            this.Caregiver = new Carer();

        }
    }
}
