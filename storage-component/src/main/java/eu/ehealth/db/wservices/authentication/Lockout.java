package eu.ehealth.db.wservices.authentication;

import java.util.ArrayList;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;


/**
 * 
 * @author a572832
 *
 */
public class Lockout extends DbStorageComponent<String[], String>
{

	
	/**
	 * 
	 * @param session
	 * @param dbHelper
	 */
	public Lockout(Session session)
	{
		super(session);
	}

	
	@Override
	protected String[] dbStorageFunction(ArrayList<String> lo)
	{
		try {
			// 'in' parameters
			String userName = lo.get(0);
			
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());
			StorageComponentMain.scLog("DEBUG", "Lockout CALL ... ");
			StorageComponentMain.scLog("DEBUG", "Lockout CALL params : " + userName);
			
			String sql = "SELECT id, locked FROM aladdinuser WHERE username = '" + userName + "'";
			SQLQuery q = _session.createSQLQuery(sql);
			
			if (q.list().size() == 1)
			{
				// updates lastlogged / logtries value
				try {
					Object[] resLoging = (Object[])q.list().get(0);
					
					int id_aladdinuser = ((Integer) resLoging[0]).intValue();
					boolean locked = ((Boolean) resLoging[1]).booleanValue();
					
					String[] response = new String[2];
					if (locked)
						response[0] = "-10";
					else 
						response[0] = "1";
					response[1] = Globals.ResponseCode.RESPONSE_OK.getDesc() + " - user with id '" + id_aladdinuser + "' locked? " + locked;
					return response;
				}
				catch (Exception ex) {
					StorageComponentMain.logException(ex);
					
					return Globals.ResponseCode.DATABASE_ERROR_1.toStringArray();
				}
			}
			
			return Globals.ResponseCode.INVALID_USER.toStringArray();
		}
		catch (Exception ex) {
			StorageComponentMain.logException(ex);
			
			return Globals.ResponseCode.DATABASE_ERROR_2.toStringArray();
		}
	}

	
}
