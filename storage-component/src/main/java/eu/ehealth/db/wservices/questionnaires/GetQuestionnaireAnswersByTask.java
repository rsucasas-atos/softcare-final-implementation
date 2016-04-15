package eu.ehealth.db.wservices.questionnaires;

import java.util.ArrayList;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.QuestionnaireAnswer;
import eu.ehealth.db.xsd.QuestionnaireAnswers;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class GetQuestionnaireAnswersByTask extends DbStorageComponent<QuestionnaireAnswers, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetQuestionnaireAnswersByTask(Session session)
	{
		super(session);
	}

	
	@Override
	protected QuestionnaireAnswers dbStorageFunction(ArrayList<String> lo)
	{
		QuestionnaireAnswers rqas = new QuestionnaireAnswers();
		try
		{
			String taskIdv = (String) lo.get(0); 
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetQuestionnaireAnswersByTask");

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			taskIdv = nc.check(taskIdv, String.class);

			if (!checkUserPermissions("", userId, U_SERVICE, U_CLINICIAN, U_ADMIN))
			{
				return rqas;
			}

			try
			{
				Integer taskId = new Integer(taskIdv);
				eu.ehealth.db.db.Task task = (eu.ehealth.db.db.Task) _session.load(eu.ehealth.db.db.Task.class, taskId);

				String sql = "SELECT id FROM questionnaireanswer WHERE ";
				sql += "timestamp = '" + task.getDateTimeFulfilled().toString() + "' ";
				sql += " AND question in (select id from questionnairequestion where quest = " + task.getQuestionnaire().toString() + ")";
				sql += " AND objectId = '" + task.getObject().toString() + "'";

				StorageComponentMain.scLog("DEBUG", sql);

				Object[] lqa = _session.createSQLQuery(sql).list().toArray();

				rqas.setTaskID(taskId.toString());

				for (int j = 0; j < lqa.length; j++)
				{
					eu.ehealth.db.db.QuestionnaireAnswer qa = 
							(eu.ehealth.db.db.QuestionnaireAnswer) _session.load(eu.ehealth.db.db.QuestionnaireAnswer.class, (Integer) lqa[j]);

					if (qa.getObjectId().equals(task.getObject()))
					{
						QuestionnaireAnswer rqa = new QuestionnaireAnswer();
						rqa.setQuestionID(qa.getQuestion().toString());
						rqa.setValue(qa.getValue());
						rqas.setObjectID(qa.getObjectId().toString());
						rqas.setUserID(qa.getUserId().toString());

						eu.ehealth.db.db.QuestionnaireQuestion qq = 
								(eu.ehealth.db.db.QuestionnaireQuestion) _session.load(eu.ehealth.db.db.QuestionnaireQuestion.class, qa.getQuestion());
						rqa.setGlobalID(qq.getGlobalId().toString());

						rqas.getAnswer().add(rqa);
					}
				}
			}
			catch (Exception e)
			{
				StorageComponentMain.logException(e);
			}

			return rqas;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);

			return rqas;
		}
	}

	
}
