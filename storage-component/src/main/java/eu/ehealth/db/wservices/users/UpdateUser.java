package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.users.dbfunctions.FUsers;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.User;
import eu.ehealth.util.NullChecker;
import eu.ehealth.util.StorageComponentUtilities;


/**
 * 
 * @author a572832
 *
 */
public class UpdateUser extends DbStorageComponent<OperationResult, User>
{

	
	/**
	 * 
	 * @param session
	 */
	public UpdateUser(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<User> lo)
	{
		try
		{
			User user = (User) lo.get(0);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());			
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			user = nc.check(user, User.class);

			try
			{
				StorageComponentUtilities.checkIP();

				_session.beginTransaction();

				FUsers fu = new FUsers(_session);
				if (fu.existUser(user.getUsername(), new Integer(user.getID())) == 1)
				{
					res.setCode("-2");
					res.setDescription("user with same username exist");
					res.setStatus((short) 0);
				}

				eu.ehealth.db.db.AladdinUser u = new eu.ehealth.db.db.AladdinUser();

				u.setId(new Integer(user.getID()));
				u.setType(new Integer(user.getType().getCode()));
				u.setPersonId(new Integer(user.getPersonID()));
				u.setUsername(user.getUsername());
				u.setPassword(user.getPassword());
				_session.save(u);

				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("" + u.getId().toString(), "");
			}
			catch (HibernateException e)
			{
				rollbackSession();

				StorageComponentMain.logException(e);
				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_1.getCode(), "");
			}
			catch (Exception e)
			{
				StorageComponentMain.logException(e);
				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_2.getCode(), "");
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
