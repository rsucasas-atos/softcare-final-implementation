using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;
using System.IO;

namespace Aladdin.DataModel
{
    public partial class QuestionnaireAnswers
    {
        [XmlElementAttribute("answer")]
        public List<QuestionnaireAnswer> Answers {get; set;}

        public QuestionnaireAnswers()
        {
            this.Answers = new List<QuestionnaireAnswer>();
        }

        public QuestionnaireAnswer GetAnswer(string questionID)
        {
            return this.Answers.SingleOrDefault(a => a.QuestionID.Equals(questionID));
        }

        public void SetAnswer(string questionID, string answer, string globalID)
        {
            QuestionnaireAnswer qa = this.GetAnswer(questionID);
            if (qa != null)
                qa.Value = answer;
            else
            {
                qa = new QuestionnaireAnswer();
                qa.QuestionID = questionID;
                qa.Value = answer;
                qa.GlobalID = globalID;
                this.Answers.Add(qa);
            }
        }

        public string ToXml()
        {
            XmlSerializer ser = new XmlSerializer(typeof(QuestionnaireAnswers));
            StringBuilder sb = new StringBuilder();
            using (StringWriter wr = new StringWriter(sb))
                ser.Serialize(wr, this);
            return sb.ToString();
        }

        public static QuestionnaireAnswers FromXml(string xml)
        {
            XmlSerializer ser = new XmlSerializer(typeof(QuestionnaireAnswers));
            return ser.Deserialize(new StringReader(xml)) as QuestionnaireAnswers;
        }
    }

    [System.SerializableAttribute()]
    [XmlTypeAttribute(AnonymousType = true)]
    public partial class QuestionnaireAnswer
    {
        private string questionIDField;

        private string valueField;

        private string globalIDField;

        [XmlAttributeAttribute(AttributeName = "questionID")]
        public string QuestionID
        {
            get
            {
                return this.questionIDField;
            }
            set
            {
                this.questionIDField = value;
            }
        }

        [XmlAttributeAttribute(AttributeName = "value")]
        public string Value
        {
            get
            {
                return this.valueField;
            }
            set
            {
                this.valueField = value;
            }
        }

        [XmlAttributeAttribute(AttributeName = "globalid")]
        public string GlobalID
        {
            get
            {
                return this.globalIDField;
            }
            set
            {
                this.globalIDField = value;
            }
        }

        public QuestionnaireAnswer()
        {
        }

        public QuestionnaireAnswer(string questionID, string value, string globalID)
        {
            this.QuestionID = questionID;
            this.Value = value;
            this.GlobalID = globalID;
        }
    }
}
