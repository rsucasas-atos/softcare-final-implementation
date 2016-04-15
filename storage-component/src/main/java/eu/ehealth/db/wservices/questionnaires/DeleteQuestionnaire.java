package eu.ehealth.db.wservices.questionnaires;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.questionnaires.dbfunctions.FQuestionnaires;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class DeleteQuestionnaire extends DbStorageComponent<OperationResult, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public DeleteQuestionnaire(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<String> lo)
	{
		try
		{
			String idv = (String) lo.get(0);
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : DeleteQuestionnaire");			
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			idv = nc.check(idv, String.class);

			if (!checkUserPermissions("", userId, U_CLINICIAN, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{
				_session.beginTransaction();
				Integer id = new Integer(idv);
				Object[] qq = _session.createSQLQuery("SELECT id FROM questionnairequestion WHERE quest = " + id.toString()).list().toArray();
				FQuestionnaires fq = new FQuestionnaires(_session);
				for (int i = 0; i < qq.length; i++)
				{
					fq.dropQuestionnaireQuestion((Integer) qq[i]);
				}
				_session.createSQLQuery("DELETE FROM questionnaire WHERE id = " + id.toString()).executeUpdate();

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
