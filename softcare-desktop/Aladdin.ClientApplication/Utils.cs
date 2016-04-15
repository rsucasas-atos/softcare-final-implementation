using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Aladdin.DataModel;
using System.Reflection;
using System.Windows.Controls;


namespace SoftCare.ClientApplication
{


    /// <summary>
    /// 
    /// </summary>
    public class Utils
    {


        /// <summary>
        /// 
        /// </summary>
        /// <param name="wb"></param>
        /// <param name="Hide"></param>
        public static void HideScriptErrors(WebBrowser wb, bool Hide)
        {
            FieldInfo fiComWebBrowser = typeof(WebBrowser).GetField("_axIWebBrowser2", BindingFlags.Instance | BindingFlags.NonPublic);

            if (fiComWebBrowser == null) return;
            object objComWebBrowser = fiComWebBrowser.GetValue(wb);
            if (objComWebBrowser == null) return;
            objComWebBrowser.GetType().InvokeMember(
            "Silent", BindingFlags.SetProperty, null, objComWebBrowser, new object[] { Hide });
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="qFS"></param>
        /// <returns></returns>
        public static Questionnaire Convert(SoftCare.ClientApplication.aladdinService.Questionnaire qFS)
        {
            Questionnaire q = new Questionnaire();
            q.Version = qFS.versionSpecified ? qFS.version.ToString() : "";
            List<QuestionnaireQuestion> qs = GetQuestions(qFS.question);
            q.Questions = qs.ToArray();
            return q;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="answers"></param>
        /// <returns></returns>
        public static SoftCare.ClientApplication.aladdinService.QuestionnaireAnswers Convert(QuestionnaireAnswers answers)
        {
            SoftCare.ClientApplication.aladdinService.QuestionnaireAnswers res = new SoftCare.ClientApplication.aladdinService.QuestionnaireAnswers();
            List<SoftCare.ClientApplication.aladdinService.QuestionnaireAnswer> list = new List<SoftCare.ClientApplication.aladdinService.QuestionnaireAnswer>();

            foreach (QuestionnaireAnswer answer in answers.Answers)
            {
                SoftCare.ClientApplication.aladdinService.QuestionnaireAnswer sAnswer = new SoftCare.ClientApplication.aladdinService.QuestionnaireAnswer();
                sAnswer.questionID = answer.QuestionID;
                sAnswer.Value = answer.Value;
                sAnswer.globalID = answer.GlobalID;
                list.Add(sAnswer);
            }
            res.answer = list.ToArray();            
            return res;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="questions"></param>
        /// <returns></returns>
        private static List<QuestionnaireQuestion> GetQuestions(SoftCare.ClientApplication.aladdinService.QuestionnaireQuestion[] questions)
        {
            List<QuestionnaireQuestion> qs = new List<QuestionnaireQuestion>();

            if (questions != null)
            {
                foreach (var qqFS in questions.OrderBy(q => q.position))
                {
                    try
                    {
                        QuestionnaireQuestionAnswerType answerType = (QuestionnaireQuestionAnswerType)Enum.Parse(typeof(QuestionnaireQuestionAnswerType), qqFS.type);
                        QuestionnaireQuestion qq = new QuestionnaireQuestion(qqFS.id, qqFS.title, answerType);
                        List<QuestionnaireQuestionAnswer> answers = new List<QuestionnaireQuestionAnswer>();

                        List<SoftCare.ClientApplication.aladdinService.QuestionnaireQuestionAnswer> answersFS = new List<SoftCare.ClientApplication.aladdinService.QuestionnaireQuestionAnswer>();
                        answersFS = qqFS.answers.OrderBy(a => a.position).ToList();

                        foreach (var aFS in answersFS)
                        {
                            var a = new QuestionnaireQuestionAnswer();
                            a.Answer = aFS.description;
                            a.Value = aFS.value.ToString();
                            a.GlobalID = System.Convert.ToString(qqFS.GlobalID);
                            answers.Add(a);
                        }
                        qq.GlobalID = System.Convert.ToString(qqFS.GlobalID);
                        qq.Answers = answers.ToArray();
                        qq.Condition = qqFS.condition.ToString();
                        qq.Questions = GetQuestions(qqFS.questions);
                        qq.FixChildrenParentID();
                        qs.Add(qq);
                    }
                    catch (Exception)
                    {

                    }
                }
            }
            return qs;
        }


    }


}
