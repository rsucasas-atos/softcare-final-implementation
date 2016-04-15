using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Aladdin.DataModel
{
    public enum QuestionnaireQuestionAnswerType
    {
        OneAnswer,
        ManyAnswers,
        FreeText
    }


    public enum CommunicationType
    {
        Phone =1,
        Mobile,
        Email,
        Fax,
        SMS
    }


    public enum IdentifierType
    {
        PoliceID,
        PassportID,
        VATNumber,
    }

    public enum MeasurementType
    {
        BP_Systolic,
        BP_Diastolic,
        HR,
        Weight
    }
}
