using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Aladdin.DataModel
{
    public class Carer
    {

        /// <summary>
        /// Basic demographic information e.g. Name, Surname, Address, Phone numbers etc.
        /// </summary>
        public PersonData PersonData { get; set; }

        /// <summary>
        ///	Social and demographic data
        /// </summary>
        public SocioDemographicData SD_Data { get; set; }

        public Carer()
        {
            this.SD_Data = new SocioDemographicData();
            this.PersonData = new PersonData();
        }
    }
}
