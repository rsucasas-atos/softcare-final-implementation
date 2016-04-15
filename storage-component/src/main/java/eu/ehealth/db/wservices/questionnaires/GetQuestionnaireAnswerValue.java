package eu.ehealth.db.wservices.questionnaires;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.common.Translations;
import eu.ehealth.db.xsd.SystemParameter;


/**
 * 
 * @author a572832
 *
 */
public class GetQuestionnaireAnswerValue extends DbStorageComponent<String, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetQuestionnaireAnswerValue(Session session)
	{
		super(session);
	}

	
	@Override
	protected String dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			String questionIdv= (String) lo.get(0); 
			String valuev= (String) lo.get(1); 
			SystemParameter locale= (SystemParameter) lo.get(2);
			
			String res = "";
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetQuestionnaireAnswerValue");

			try
			{
				Integer questionId = new Integer(questionIdv);
				Integer value = new Integer(valuev);

				String sql = "SELECT id FROM questionnairequestionanswer WHERE question = "
						+ questionId.toString() + " AND value = " + value.toString();
				List<?> _id = _session.createSQLQuery(sql).list();
				if (_id.size() == 1)
				{
					Integer id = (Integer) _id.get(0);
					Translations languageTrans = new Translations(_session);
					res = languageTrans.getTranslate("questionnairequestionanswer", id, locale.getCode(), "");
				}

			}
			catch (Exception e)
			{
				StorageComponentMain.logException(e);
			}

			return res;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);

			return Globals.ResponseCode.UNKNOWN_ERROR.getCode();
		}
	}
	

}
