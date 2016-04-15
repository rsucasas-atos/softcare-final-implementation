package eu.ehealth.db.wservices.questionnaires;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.questionnaires.dbfunctions.FQuestionnaires;
import eu.ehealth.db.xsd.QuestionnaireAnswers;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class GetQuestionnaireAnswers extends DbStorageComponent<List<QuestionnaireAnswers>, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetQuestionnaireAnswers(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<QuestionnaireAnswers> dbStorageFunction(ArrayList<Object> lo)
	{
		List<QuestionnaireAnswers> l = new ArrayList<QuestionnaireAnswers>();
		try
		{
			String objectId = (String) lo.get(0);
			XMLGregorianCalendar fromDate = (XMLGregorianCalendar) lo.get(1);
			XMLGregorianCalendar toDate = (XMLGregorianCalendar) lo.get(2);
			String userId = (String) lo.get(0);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetQuestionnaireAnswers");

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			objectId = nc.check(objectId, String.class);
			fromDate = nc.check(fromDate, XMLGregorianCalendar.class);
			toDate = nc.check(toDate, XMLGregorianCalendar.class);

			if (!checkUserPermissions("", userId, U_SERVICE, U_CLINICIAN, U_ADMIN))
			{
				return l;
			}

			try
			{
				FQuestionnaires fq = new FQuestionnaires(_session);
				l = fq.getQuestionnaireAnswers(fromDate.toGregorianCalendar(), toDate.toGregorianCalendar(), new Integer(objectId));
			}
			catch (HibernateException e)
			{
				StorageComponentMain.logException(e);
			}

			return l;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);

			return l;
		}
	}
	

}
