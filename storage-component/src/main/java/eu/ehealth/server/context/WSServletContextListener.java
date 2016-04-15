package eu.ehealth.server.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import eu.ehealth.StorageComponentMain;


/**
 * 
 * @author a572832
 *
 */
public class WSServletContextListener implements ServletContextListener {

	
	private static long MAX_TIME = 600;
	
	/**
	 * users logged and with an active session
	 */
	private static HashMap<String, SessionUsers> hUsers;
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try 
		{
			StorageComponentMain.scLog("INFO", "Initializing WSServletContextListener ...");
			
			hUsers = new HashMap<String, SessionUsers>(133);
		}
		catch (Exception ex) {
			StorageComponentMain.scLog("ERROR", ex.getMessage());
		}
	}
	

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try 
		{
			StorageComponentMain.scLog("INFO", "Destroying WSServletContextListener ...");
			
			hUsers = null; 
		}
		catch (Exception ex) {
			StorageComponentMain.scLog("ERROR", ex.getMessage());
		}
	}

	
	/**
	 * 
	 * @param idUser
	 * @param rol
	 * @param username
	 */
	public static void addUser(String idUser, int rol, String username, String srol) {
		if (hUsers == null) 
		{
			StorageComponentMain.scLog("INFO", "Initializing SessionUsers HashMap object ...");
			hUsers = new HashMap<String, SessionUsers>(133);
		}
		
		if ((hUsers != null) && (hUsers.get(idUser) == null))
		{
			StorageComponentMain.scLog("DEBUG", "Adding user with id '" + idUser + "' and rol '" + rol + "'...");
			SessionUsers newUser = new SessionUsers(idUser, rol, username, srol);
			hUsers.put(idUser, newUser);
		}
	}
	
	
	/**
	 * 
	 * @param idUser
	 * @param sessionId
	 */
	public static void removeUser(String idUser, String sessionId) {
		if ((hUsers != null) && (hUsers.get(idUser) != null))
		{
			hUsers.remove(idUser);
			StorageComponentMain.scLog("DEBUG", "User '" + idUser + "' removed");
		}
	}
	
	
	/**
	 * 
	 * @param idUser
	 * @param rolesAllowed
	 * @return
	 */
	public static boolean hasAccessToOperation(String idUser, int... rolesAllowed) {
		try 
		{
			if (hUsers != null)
			{
				SessionUsers currentuser = hUsers.get(idUser);
				
				if (currentuser != null) 
				{
					// check elapsed time since last connection
					long lastConnectionTime = currentuser.getLastConnectionTime();
					long currentTime = System.currentTimeMillis();
					long res = (currentTime - lastConnectionTime) / 1000;
					
					if (res > MAX_TIME) 
					{
						StorageComponentMain.scLog("DEBUG", "User '" + idUser + "' ... time expired");
						hUsers.remove(idUser);
						StorageComponentMain.scLog("DEBUG", "User '" + idUser + "' removed");
					}
					else 
					{
						// update session user values
						currentuser.updateValues();
						
						// check permissions
						for (int i=0, max=rolesAllowed.length; i<max; i++) 
						{
							if (rolesAllowed[i] == currentuser.getRol()) 
							{
								return true;
							}
						}
						StorageComponentMain.scLog("DEBUG", "User '" + idUser + "' has no permissions");
					}
				}
				else 
				{
					StorageComponentMain.scLog("WARN", "User '" + idUser + "' is not logged");
				}
			}
			else 
			{
				StorageComponentMain.scLog("WARN", "Map of users is null / User not logged");
			}
		}
		catch (Exception ex) {
			StorageComponentMain.scLog("ERROR", ex.getMessage());
		}

		return false;
	}
	
	
	/**
	 * 
	 * @param idUser
	 * @param rolesAllowed
	 * @return
	 */
	private static boolean hasAccessToOperation2(String idUser, int... rolesAllowed) {
		try 
		{
			if (hUsers != null)
			{
				SessionUsers currentuser = hUsers.get(idUser);
				
				if (currentuser != null) 
				{
					// check elapsed time since last connection
					long res = (System.currentTimeMillis() - currentuser.getLastConnectionTime()) / 1000;
					if (res > MAX_TIME) 
					{
						hUsers.remove(idUser);
					}
					else 
					{
						// check permissions
						for (int i=0, max=rolesAllowed.length; i<max; i++) 
						{
							if (rolesAllowed[i] == currentuser.getRol()) 
							{
								return true;
							}
						}
					}
				}
			}
		}
		catch (Exception ex) {
			StorageComponentMain.scLog("ERROR", ex.getMessage());
		}

		return false;
	}
	

	/**
	 * 
	 * @return
	 */
	public static HashMap<String, SessionUsers> gethUsers() {
		return hUsers;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static Object[] getConnectedUsers(String userId) {
		Object[] res = null;
		
		if (hasAccessToOperation2(userId, 1)) {
			if (hUsers != null)
			{
				Collection<SessionUsers> colUsers = hUsers.values();
				res = new Object[colUsers.size()];
				
				int i=0;
				for (SessionUsers user : colUsers) {
					res[i] = user.getFormattedValues();
					i++;
			    }
			}
		}
		
		return res;
	}
	
	
	/**
	 * 
	 */
	public static void checkUsers() 
	{
		try 
		{
			if (hUsers != null)
			{
				long currentTime = System.currentTimeMillis();

				for (Iterator<Map.Entry<String, SessionUsers>> it = hUsers.entrySet().iterator(); it.hasNext(); ) {
			     	Map.Entry<String, SessionUsers> entry = it.next();
			     	
			     	if ( ((currentTime - entry.getValue().getLastConnectionTime()) / 1000) > MAX_TIME)
					{
						it.remove();
						StorageComponentMain.scLog("DEBUG", "User '" + entry.getValue().getUserName() + "' removed [PERIODIC TASK function]");
					}
			    }
			}
		}
		catch (Exception ex) {
			StorageComponentMain.scLog("ERROR", ex.getMessage());
		}
	}

	
}
