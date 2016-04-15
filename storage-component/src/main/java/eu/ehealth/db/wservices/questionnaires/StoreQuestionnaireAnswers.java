package eu.ehealth.db.wservices.questionnaires;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.dataprocessing.DataProcessing;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.OperationResult;
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
public class StoreQuestionnaireAnswers extends DbStorageComponent<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public StoreQuestionnaireAnswers(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			QuestionnaireAnswers data = (QuestionnaireAnswers) lo.get(0); 
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : StoreQuestionnaireAnswers");			
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			data = nc.check(data, QuestionnaireAnswers.class);

			if (!checkUserPermissions("", userId, U_CARER, U_CLINICIAN, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{
				_session.beginTransaction();

				Timestamp datetime = new Timestamp(Calendar.getInstance().getTimeInMillis());

				Integer objectId = new Integer(data.getObjectID());
				Integer taskId = new Integer(data.getTaskID());

				Integer id = 0;
				QuestionnaireAnswer[] rqal = data.getAnswer().toArray(new QuestionnaireAnswer[data.getAnswer().size()]);
				for (int i = 0; i < rqal.length; i++)
				{
					eu.ehealth.db.db.QuestionnaireAnswer qa = new eu.ehealth.db.db.QuestionnaireAnswer();
					if (rqal[i].getQuestionID() != null)
						qa.setQuestion(new Integer(rqal[i].getQuestionID()));
					qa.setValue(rqal[i].getValue());
					qa.setUserId(new Integer(userId));
					qa.setObjectId(objectId);
					qa.setDateTime(datetime);
					_session.save(qa);
					id = qa.getId();
					StorageComponentMain.scLog("DEBUG", rqal[i].getValue());
				}

				String sql = "UPDATE task SET datetimefulfilled = '" + datetime.toString() + "' WHERE id = " + taskId.toString();
				_session.createSQLQuery(sql).executeUpdate();

				DataProcessing r = new DataProcessing(_session);
				r.analyzeQuestionnaires(rqal, objectId);

				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("" + id.toString(), "");
			}
			catch (HibernateException e)
			{
				rollbackSession();

				StorageComponentMain.logException(e);

				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_1.getCode(), " : " + e.toString());
			}

			return res;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
			// set OperationResult values
			return Globals.getOpResult(Globals.ResponseCode.UNKNOWN_ERROR.getCode(), " : " + ex.toString());
		}
	}
	

}
