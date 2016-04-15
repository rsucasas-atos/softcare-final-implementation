package eu.ehealth.db.wservices.questionnaires;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.questionnaires.dbfunctions.FQuestionnaires;
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
public class GetQuestionnaire extends DbStorageComponent<Questionnaire, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetQuestionnaire(Session session)
	{
		super(session);
	}

	
	@Override
	protected Questionnaire dbStorageFunction(ArrayList<Object> lo)
	{
		Questionnaire q = new Questionnaire();
		try
		{
			String idv = (String) lo.get(0); 
			SystemParameter locale = (SystemParameter) lo.get(1); 
			String userId = (String) lo.get(2);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetQuestionnaire");

			NullChecker nc = new NullChecker();

			idv = nc.check(idv, String.class);

			try
			{
				Integer id = new Integer(idv);
				eu.ehealth.db.db.Questionnaire qdb = 
						(eu.ehealth.db.db.Questionnaire) _session.load(eu.ehealth.db.db.Questionnaire.class, id);

				FQuestionnaires fq = new FQuestionnaires(_session);
				q = fq.exportQuestionnaire(qdb, locale);
			}
			catch (HibernateException e)
			{
				StorageComponentMain.logException(e);
			}

			return q;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);

			return q;
		}
	}
	

}
