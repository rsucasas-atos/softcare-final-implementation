package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.SystemParameter;
import eu.ehealth.db.xsd.User;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class GetUser extends DbStorageComponent<User, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetUser(Session session)
	{
		super(session);
	}

	
	@Override
	protected User dbStorageFunction(ArrayList<String> lo)
	{
		User ru = new User();
		try
		{
			String idv = (String) lo.get(0);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());

			NullChecker nc = new NullChecker();

			idv = nc.check(idv, String.class);

			try
			{
				Integer id = new Integer(idv);
				eu.ehealth.db.db.AladdinUser user = 
						(eu.ehealth.db.db.AladdinUser) _session.load(eu.ehealth.db.db.AladdinUser.class, id);

				ru.setID(user.getId().toString());
				ru.setPassword("");
				ru.setPersonID(user.getPersonId().toString());
				SystemParameter spType = new SystemParameter();
				spType.setCode(user.getType().toString());
				ru.setType(spType);
				ru.setUsername(user.getUsername());
			}
			catch (HibernateException e)
			{
				StorageComponentMain.logException(e);
			}
			catch (Exception e)
			{
				StorageComponentMain.logException(e);
			}

			return ru;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);

			return ru;
		}
	}

	
}
