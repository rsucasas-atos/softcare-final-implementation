using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Aladdin.DataModel
{
    public class Clinician
    {

        public PersonData PersonData { get; set; }

        public Clinician()
        {
            this.PersonData = new PersonData();
        }

    }
}
