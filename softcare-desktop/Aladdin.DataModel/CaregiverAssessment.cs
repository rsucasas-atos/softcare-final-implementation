using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Aladdin.DataModel
{
    public class CarerAssessment
    {
        public Carer Caregiver { get; set; }

        public DateTime DateOfAssessment { get; set; }

        public string RelevantHealthProblem  { get; set; }

        public int SeverityOfBurden { get; set; }

        public string EmotionalOrMentalDisorders { get; set; }

        public string PsychoactiveDrugs { get; set; }

        /// <summary>
        /// Quality of Life
        /// </summary>
        public int QualityOfLife { get; set; }


        public CarerAssessment()
        {

            this.Caregiver = new Carer();
        }
    }
}
