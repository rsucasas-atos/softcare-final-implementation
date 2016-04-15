using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Aladdin.DataModel
{
    /// <summary>
    /// System Parameters, like Gender, Marital Status etc
    /// </summary>
    public class SystemParameter
    {
        public int Code { get; set; }
        public string Description { get; set; }

        public SystemParameter(int _code, string _description)
        {
            this.Code = _code;
            this.Description = _description;
        }

        public SystemParameter()
        {

        }
    }
}
