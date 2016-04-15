using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Aladdin.DataModel
{
    public class SocioDemographicData
    {
        /// <summary>
        /// Age (years)
        /// </summary>
        public int Age { get; set; }
        /// <summary>
        /// Gender (1 male- 2 female)
        /// </summary>
        public SystemParameter Gender { get; set; }
        /// <summary>
        /// Marital status ( 1 widow, 2 married, 3 single, 4 divorced)
        /// </summary>
        public SystemParameter MaritalStatus { get; set; }
        /// <summary>
        /// Number of Living Children
        /// </summary>
        public int Children { get; set; }
        /*  Living with: 
            0 alone, 
            1 with son/daughter, 
            2 with partner, 
            3 with partner + son/daughter, 
            4 with partner + son/daughter + s/d in law, 
            5 with partner + s/d + s/d in law + grandson/d, 
            6 other options 
        */
        public SystemParameter LivingWith { get; set; }
    }
}
