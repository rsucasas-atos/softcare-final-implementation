package eu.ehealth.db.wservices.authentication;

import java.util.ArrayList;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class LockUser extends DbStorageComponent<OperationResult, String> 
{

	
	/**
	 * 
	 * @param session
	 */
	public LockUser(Session session) 
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<String> lo) {
		try {
			// 'in' parameters
			String userId = lo.get(0);
			int id_aladdinuser = Integer.parseInt(lo.get(1));
			boolean locked = Boolean.parseBoolean(lo.get(2));
			
			// 'out'
			OperationResult res = new OperationResult();
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			if (!checkUserPermissions("", userId, U_ADMIN))
			{
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			int rowsUpdated = _session.createSQLQuery("UPDATE aladdinuser set locked = :locked where id = :id")
				.setBoolean("locked", locked)
				.setInteger("id", id_aladdinuser)
				.executeUpdate();
			
			if (rowsUpdated == 1) {
				res = Globals.getOpResult("" + id_aladdinuser, "");
			}
			else {
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_1.getCode(), " : rows updated ... " + rowsUpdated);
			}
			
			return res;
		}
		catch (Exception ex) {
			StorageComponentMain.logException(ex);
			
			// set OperationResult values
			return Globals.getOpResult(Globals.ResponseCode.UNKNOWN_ERROR.getCode(), " : " + ex.toString());
		}
	}
	

}
