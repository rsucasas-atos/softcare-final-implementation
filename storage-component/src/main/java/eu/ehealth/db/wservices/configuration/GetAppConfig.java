package eu.ehealth.db.wservices.configuration;

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
public class GetAppConfig extends DbStorageComponent<List<String[]>, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetAppConfig(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<String[]> dbStorageFunction(ArrayList<String> lo)
	{
		String userId = (String) lo.get(0);
		
		List<String[]> l = new ArrayList<String[]>();

		// DEBUG - TRACE
		StorageComponentMain.scLog("DEBUG", "METHOD : GetAppConfig");

		NullChecker nc = new NullChecker();

		userId = nc.check(userId, String.class);

		if (!checkUserPermissions("", userId, U_ADMIN))
		{
			return l;
		}

		try
		{
			String sql = "SELECT c.id, c.name, c.desc, c.value, c.active FROM configuration c ";
			List<Object[]> ql = _session.createSQLQuery(sql).list();
			
			String[] res = new String[5];
			for (Object[] objRes : ql) 
			{
				res[0] = ((Integer) objRes[0]).toString();
				res[1] = (String) objRes[1];
				res[2] = (String) objRes[2];
				res[3] = (String) objRes[3];
				res[4] = ((Boolean) objRes[4]).toString();
				
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
