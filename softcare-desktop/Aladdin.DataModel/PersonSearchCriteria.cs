using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Aladdin.DataModel
{
    public class PersonSearchCriteria
    {
        public string FieldName { get; set; }
        public SystemParameter CompareOp { get; set; }
        public string FieldValue1 { get; set; }
        public string FieldValue2 { get; set; }
    }
}
