package eu.ehealth.db.wservices.questionnaires;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.questionnaires.dbfunctions.FQuestionnaires;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.Questionnaire;
import eu.ehealth.db.xsd.SystemParameter;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class CreateQuestionnaire extends DbStorageComponent<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public CreateQuestionnaire(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			Questionnaire data = (Questionnaire) lo.get(0); 
			SystemParameter locale = (SystemParameter) lo.get(1); 
			String userId = (String) lo.get(2);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : CreateQuestionnaire");
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			data = nc.check(data, Questionnaire.class);

			if (!checkUserPermissions("", userId, U_CLINICIAN, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{
				_session.beginTransaction();

				data.setID(null);
				FQuestionnaires fq = new FQuestionnaires(_session);
				eu.ehealth.db.db.Questionnaire q = fq.importQuestionnaire(data, locale);

				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("" + q.getId().toString(), "");
			}
			catch (HibernateException e)
			{
				rollbackSession();

				StorageComponentMain.logException(e);

				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_1.getCode(), " : " + e.toString());
			}
			catch (Exception e)
			{
				rollbackSession();

				StorageComponentMain.logException(e);

				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_2.getCode(), " : " + e.toString());
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
