package eu.ehealth.db.wservices.mediacontent;

import java.util.ArrayList;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.OperationResult;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class DeleteMediaContent extends DbStorageComponent<OperationResult, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public DeleteMediaContent(Session session)
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
			StorageComponentMain.scLog("DEBUG", "METHOD : DeleteMediaContent");
			
			OperationResult res = new OperationResult();

			try
			{
				Integer id = new Integer(idv);
				_session.beginTransaction();
				_session.createSQLQuery("DELETE FROM entertainmentcontent WHERE id = " + id.toString()).executeUpdate();
				_session.getTransaction().commit();
				
				// set OperationResult values
				res = Globals.getOpResult("" + id.toString(), "");
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
