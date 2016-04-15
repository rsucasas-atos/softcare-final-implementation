package eu.ehealth.db.wservices.authentication;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.security.DataBasePasswords;
import eu.ehealth.server.context.WSServletContextListener;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class Auth extends DbStorageComponent<OperationResult, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public Auth(Session session)
	{
		super(session);
	}
	

	@Override
	protected OperationResult dbStorageFunction(ArrayList<String> lo)
	{
		try
		{
			String login = lo.get(0);
			String password = lo.get(1);

			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			login = nc.check(login, String.class);
			password = nc.check(password, String.class);

			try
			{
				_session.beginTransaction();
				
				// WITH ENCRYPTION
				if (StorageComponentMain.DATABASE_ENCRYPTION)
				{
					res = encryptedAuth(login, password);
				}
				// NO ENCRYPTION
				else 
				{
					res = auth(login, password);
				}

				_session.getTransaction().commit();
			}
			catch (HibernateException e)
			{
				rollbackSession();
				
				StorageComponentMain.logException(e);

				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_1.getCode(), " : " + e.toString());
			}
			
			try 
			{
				String userRol = "NO_ROLE";
				Integer id = new Integer(res.getCode());
				String sql = "SELECT type FROM aladdinuser WHERE id = '" + id.toString() + "'";
				SQLQuery q = _session.createSQLQuery(sql);
				if (q.list().size() == 1)
				{
					String rol = q.list().get(0).toString();
					
					if (rol.equals("1")) {
						userRol = "admin";
					}
					else if (rol.equals("2")) {
						userRol = "clinician";
					}
					else if (rol.equals("3")) {
						userRol = "carer";
					}
					else if (rol.equals("4")) {
						userRol = "patient";
					}

					// adding user
					WSServletContextListener.addUser(id.toString(), Integer.parseInt(rol), login, userRol);
				}
			}
			catch (Exception ex) 
			{
				StorageComponentMain.logException(ex);
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
	
	
	/**
	 * 
	 * @param login
	 * @param password
	 * @throws Exception
	 */
	private OperationResult encryptedAuth(String login, String password) throws Exception
	{
		OperationResult res = new OperationResult();
		
		String sql = "SELECT id, locked, password FROM aladdinuser WHERE username = '" + login + "'";
		SQLQuery q = _session.createSQLQuery(sql);
		
		if (q.list().size() == 1)
		{
			Object[] resLoging = (Object[])q.list().get(0);
			int id_aladdinuser = ((Integer) resLoging[0]).intValue();
			boolean locked = ((Boolean) resLoging[1]).booleanValue();
			String psswd_db_encrypted = (String) resLoging[2];
			
			if (locked) 
			{
				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.USER_LOCKED.getCode(), "");
			}
			else 
			{
				if (DataBasePasswords.checkPooledPsswd(password, psswd_db_encrypted)) {
					// set OperationResult values
					res = Globals.getOpResult("" + id_aladdinuser, "");
				
					// update aladdin user data
					_session.createSQLQuery("UPDATE aladdinuser set logtries = :logtries, lastlogged = :lastlogged where id = :id")
						.setInteger("logtries", 0)
						.setTimestamp("lastlogged", new Timestamp(Calendar.getInstance().getTimeInMillis()))
						.setInteger("id", id_aladdinuser)
						.executeUpdate();
				}
				else {
					// check / set if user exists in order to update locked / logtries value
					res = checkUserAttempts(login);
				}
			}
		}
		else {
			// ERROR
			res = Globals.getOpResult(Globals.ResponseCode.INVALID_USER.getCode());
		}
		
		return res;
	}
	
	
	/**
	 * 
	 * @param login
	 * @param password
	 * @throws Exception
	 */
	private OperationResult auth(String login, String password) throws Exception
	{
		OperationResult res = new OperationResult();
		
		String sql = "SELECT id, locked FROM aladdinuser WHERE username = '" + login + "' AND password = '" + password + "'";
		SQLQuery q = _session.createSQLQuery(sql);
		
		// Check other properties
		if (q.list().size() == 1)
		{
			// updates lastlogged / logtries value
			try {
				Object[] resLoging = (Object[])q.list().get(0);
				
				int id_aladdinuser = ((Integer) resLoging[0]).intValue();
				boolean locked = ((Boolean) resLoging[1]).booleanValue();
				
				if (locked) 
				{
					// set OperationResult values
					res = Globals.getOpResult(Globals.ResponseCode.USER_LOCKED.getCode(), "");
				}
				else 
				{
					// set OperationResult values
					res = Globals.getOpResult("" + id_aladdinuser, "");
				
					// update aladdin user data
					_session.createSQLQuery("UPDATE aladdinuser set logtries = :logtries, lastlogged = :lastlogged where id = :id")
						.setInteger("logtries", 0)
						.setTimestamp("lastlogged", new Timestamp(Calendar.getInstance().getTimeInMillis()))
						.setInteger("id", id_aladdinuser)
						.executeUpdate();
				}
			}
			catch (Exception ex) {
				StorageComponentMain.logException(ex);
				
				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_2.getCode(), " : " + ex.toString());
			}
		}
		else
		{
			// check if user exists in order to update locked / logtries value
			res = checkUserAttempts(login);
		}
		
		return res;
	}
	
	
	/**
	 * 
	 * @param res
	 * @param login
	 * @throws Exception
	 */
	private OperationResult checkUserAttempts(String login) throws Exception
	{
		OperationResult res = new OperationResult();
		
		// check if user exists in order to update locked / logtries value
		String sql = "SELECT id, logtries FROM aladdinuser WHERE username = '" + login + "'";
		SQLQuery q2 = _session.createSQLQuery(sql);
		if (q2.list().size() == 1) 
		{
			try {
				Object[] resLog = (Object[])q2.list().get(0);
				
				int id_aladdinuser = ((Integer)resLog[0]).intValue();
				short logtries_aladdinuser = ((Short)resLog[1]).shortValue();
				boolean locked = false;
				
				logtries_aladdinuser++;
				
				int login_attempts = Globals.getIntValueEHEALTH_PARAMETERS(Globals.strLOGIN_ATTEMPTS);
				
				if (logtries_aladdinuser >= 1000) //login_attempts) 
				{
					locked = true;
					logtries_aladdinuser = (short) login_attempts;
				}
				
				_session.createSQLQuery("UPDATE aladdinuser set logtries = :logtries, locked = :locked, lastlogged = :lastlogged  where id = :id")
					.setShort("logtries", logtries_aladdinuser)
					.setTimestamp("lastlogged", new Timestamp(Calendar.getInstance().getTimeInMillis()))
					.setBoolean("locked", locked)
					.setInteger("id", id_aladdinuser)
					.executeUpdate();
				
				// set OperationResult values
				if (locked) {
					res = Globals.getOpResult(Globals.ResponseCode.USER_LOCKED.getCode(), "");
				}
				else {
					res = Globals.getOpResult(Globals.ResponseCode.INVALID_PASSWORD.getCode(), 
							" : Attempts made : " + logtries_aladdinuser + " - Max attempts allowed before locking the account : " + login_attempts);
				}
			}
			catch (Exception ex) {
				StorageComponentMain.logException(ex);
				
				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_2.getCode(), " : " + ex.toString());
			}
		}
		else 
		{
			// set OperationResult values
			res = Globals.getOpResult(Globals.ResponseCode.INVALID_USER.getCode(), "");
		}
		
		return res;
	}
	

}
