package eu.ehealth.db.wservices.configuration;

import java.util.ArrayList;

import org.hibernate.Session;

import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class UpdateAppConfigRow extends DbStorageComponent<OperationResult, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public UpdateAppConfigRow(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<String> lo)
	{
		try {
			// 'in' parameters
			String userId = lo.get(0);
			int id = Integer.parseInt(lo.get(1));
			String value = lo.get(2);
			boolean active = Boolean.parseBoolean(lo.get(3));
			
			// 'out'
			OperationResult res = new OperationResult();
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : LockUser");

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			if (!checkUserPermissions("", userId, U_ADMIN))
			{
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			int rowsUpdated = _session.createSQLQuery("UPDATE configuration set value = :value, active = :active where id = :id")
				.setString("value", value)
				.setBoolean("active", active)
				.setInteger("id", id)
				.executeUpdate();
			
			if (rowsUpdated == 1) {
				res = Globals.getOpResult("" + id, "");
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
