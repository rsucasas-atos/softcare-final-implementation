package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.util.NullChecker;
import eu.ehealth.util.StorageComponentUtilities;


/**
 * 
 * @author a572832
 *
 */
public class DeleteUser extends BaseAppUsersOperations
{

	
	/**
	 * 
	 * @param session
	 */
	public DeleteUser(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			String idv = (String) lo.get(0);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			idv = nc.check(idv, String.class);

			try
			{
				StorageComponentUtilities.checkIP();
				Integer id = new Integer(idv);

				_session.beginTransaction();
				_session.createSQLQuery("DELETE FROM aladdinuser WHERE id = " + id.toString()).executeUpdate();
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
