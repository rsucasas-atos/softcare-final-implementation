package eu.ehealth.db.wservices.mediacontent;

import java.util.ArrayList;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.mediacontent.dbfunctions.FMediaContent;
import eu.ehealth.db.xsd.MediaContent;
import eu.ehealth.db.xsd.OperationResult;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class UpdateMediaContent extends DbStorageComponent<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public UpdateMediaContent(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			MediaContent rEC = (MediaContent) lo.get(0);  
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : UpdateMediaContent");			
			
			OperationResult res = new OperationResult();

			try
			{
				_session.beginTransaction();

				FMediaContent fm = new FMediaContent(_session);
				Integer savedId = fm.importMediaContent(rEC, new Integer(rEC.getID()));

				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("" + savedId.toString(), "");
			}
			catch (Exception e)
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
