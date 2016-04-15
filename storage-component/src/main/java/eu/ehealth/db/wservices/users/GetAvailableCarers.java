package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.CarerInfo;
import eu.ehealth.util.NullChecker;
import eu.ehealth.util.threading.DecryptCarerInfoTask;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class GetAvailableCarers extends DbStorageComponent<List<CarerInfo>, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetAvailableCarers(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<CarerInfo> dbStorageFunction(ArrayList<String> lo)
	{
		List<CarerInfo> l = new ArrayList<CarerInfo>();
		try
		{
			String userId = (String) lo.get(0);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			if (!checkUserPermissions("", userId, U_SERVICE, U_CLINICIAN, U_ADMIN))
			{
				return l;
			}

			try
			{
				String sql = "SELECT p.id, pd.name, pd.surname FROM carer p " + 
							 "LEFT JOIN persondata pd ON (pd.id = p.persondata) " + 
							 "WHERE p.id not in (select carer from patient)";
				
				List<Object[]> ql = _session.createSQLQuery(sql).list();
				
				ForkJoinPool pool = new ForkJoinPool();
				DecryptCarerInfoTask task = new DecryptCarerInfoTask(ql);
				l = pool.invoke(task);
			}
			catch (Exception e)
			{
				StorageComponentMain.logException(e);
			}

			return l;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);

			return l;
		}
	}
	

}
