using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Aladdin.DataModel
{
    public class Measurement
    {
        public MeasurementType Type { get; set; }
        public DateTime DateTime { get; set; }
        public decimal Value { get; set; }
        public string Units { get; set; }
        public decimal LowerLimit { get; set; }
        public decimal UpperLimit { get; set; }

    }



}
