using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Aladdin.DataModel
{
    public class Task
    {
        /// <summary>
        /// Type of Task
        /// </summary>
        public SystemParameter TaskType { get; set; }

        /// <summary>
        /// Date and time that the task should be fullfiled
        /// </summary>
        public DateTime DateTimeAssigned { get; set; }

        /// <summary>
        /// Date and time that the task was actually fulfilled
        /// </summary>
        public DateTime DateTimeFulfilled { get; set; }

        /// <summary>
        /// Task Status
        /// </summary>
        public SystemParameter TaskStatus { get; set; }

    }
}
