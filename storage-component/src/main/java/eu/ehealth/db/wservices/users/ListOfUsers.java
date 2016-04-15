package eu.ehealth.db.wservices.users;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class ListOfUsers extends DbStorageComponent<List<String[]>, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public ListOfUsers(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<String[]> dbStorageFunction(ArrayList<String> lo)
	{
		String userId = (String) lo.get(0);
		
		List<String[]> l = new ArrayList<String[]>();

		// DEBUG - TRACE
		StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());

		NullChecker nc = new NullChecker();

		userId = nc.check(userId, String.class);

		if (!checkUserPermissions("", userId, U_ADMIN))
		{
			return l;
		}

		try
		{
			String sql = "SELECT c.id, c.username, c.type, c.locked, c.lastlogged, c.creationtime, c.logtries FROM aladdinuser c ";
			List<Object[]> ql = _session.createSQLQuery(sql).list();
			
			String[] res = new String[7];
			for (Object[] objRes : ql) 
			{
				res[0] = ((Integer) objRes[0]).toString();
				res[1] = (String) objRes[1];
				res[2] = ((Integer) objRes[2]).toString();
				res[3] = ((Boolean) objRes[3]).toString();
				res[4] = ((Timestamp) objRes[4]).toString();
				res[5] = ((Timestamp) objRes[5]).toString();
				res[6] = ((Integer) objRes[6]).toString();
				
				l.add(res);
			}
		}
		catch (HibernateException e)
		{
			StorageComponentMain.logException(e);
		}

		return l;
	}


}