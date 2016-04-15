using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Aladdin.DataModel
{
    /// <summary>
    /// Patient Assessment face to face at baseline, after three months, after six months and at the end point (after nine months)
    /// </summary>
    public class PatientAssessment
    {


        public Patient Patient { get; set; }

        /// <summary>
        /// Date of Assessment
        /// </summary>
        public DateTime? DateOfAssessment { get; set; }

        /// <summary>
        /// Aetiology: 
        //1 Alzheimer D,
        //2 vascular D, 
        //3 Mixed D, 
        //4 Parkinson , 
        //5 others
        /// </summary>
        public SystemParameter Aetiology { get; set; }

        /// <summary>
        /// Time elapsed since diagnosed ( months) 
        /// </summary>
        public int TimeElapsedSinceDiagnosed { get; set; }

        /// <summary>
        /// Severity: Clinical Dementia Rating (CDR) numeric, score from 0 to 3, possibilities (0; 0,5; 1; 2; and 3)
        /// </summary>
        public decimal Severity { get; set; }

        /// <summary>
        /// Relevant Pathology antecedents (i.e. AVC)
        /// </summary>
        public string RelevantPathologyAntecedents { get; set; }

        /// <summary>
        /// Comorbidity (HBP, diabetes ,cardiopathy, etc)
        /// </summary>
        public string Comorbidity { get; set; }

        /// <summary>
        /// Charlson Comorbidity Index, score from 0 to 37 
        /// </summary>
        public int CharlsonComorbidityIndex { get; set; }

        /// <summary>
        /// Barthel Index score
        /// </summary>
        public int BarthelIndex { get; set; }

        /// <summary>
        /// Lawton index score
        /// </summary>
        public int LawtonIndex { get; set; }

        /// <summary>
        /// Mini Mental Status Examination (MMSE) score
        /// </summary>
        public int MMSE { get; set; }

        /// <summary>
        /// Mattis Dementia Rating Scale (MDRS) score
        /// </summary>
        public int MDRS { get; set; }

        /// <summary>
        /// Blessed Scale Part 1 Score
        /// </summary>
        public decimal BlessedScalePart1 { get; set; }

        /// <summary>
        /// Blessed Scale Part 2 Score
        /// </summary>
        public int BlessedScalePart2 { get; set; }

        /// <summary>
        /// Blessed Scale Part 3 Score
        /// </summary>
        public decimal BlessedScalePart3 { get; set; }

        /// <summary>
        /// Checklist MBPC
        /// </summary>
        public int ChecklistMBPC { get; set; }

        /// <summary>
        /// Neuropsychatric Inventory (NPI-Q): Severity
        /// </summary>
        public int NPQI_Severity { get; set; }

        /// <summary>
        ///  Neuropsychatric Inventory (NPI-Q): Stress
        /// </summary>
        public int NPQI_Stress { get; set; }

        /// <summary>
        /// Yesavage Geriatric Depression Scale (GDS)
        /// </summary>
        public int GDS { get; set; }


        public bool Falls {get; set; }
        public bool Incontinence {get; set; }
        public bool Delirium {get; set; }
        public bool Immobility {get; set; }
        public bool SensorialDeficits {get; set; }


        public string PharmacologicalTreatment  { get; set; }

        public List<Measurement> ClinicalData { get; set; }        
        

        


    }
}
