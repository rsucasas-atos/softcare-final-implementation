package eu.ehealth.db.wservices.questionnaires;

import java.util.ArrayList;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.common.Translations;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.SystemParameter;


/**
 * 
 * @author a572832
 *
 */
public class GetQuestionDescription extends DbStorageComponent<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetQuestionDescription(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			String questionID = (String) lo.get(0);
			SystemParameter locale = (SystemParameter) lo.get(1);
			
			OperationResult res = new OperationResult();
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetQuestionDescription");

			try
			{
				Translations languageTrans = new Translations(_session);
				res.setDescription(languageTrans.getTranslate("questionnairequestion", new Integer(questionID), locale, ""));
				res.setCode(questionID);
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
			// set OperationResult values
			return Globals.getOpResult(Globals.ResponseCode.UNKNOWN_ERROR.getCode(), " : " + ex.toString());
		}
	}
	

}
