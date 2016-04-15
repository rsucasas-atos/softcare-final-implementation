package eu.ehealth.server.users;

import java.util.TimerTask;
import eu.ehealth.SystemDictionary;
import eu.ehealth.server.context.WSServletContextListener;


/**
 * 
 * @author a572832
 *
 */
public class TimerTaskCheckSessionUsers extends TimerTask 
{

	
	@Override
	public void run() 
	{
		try 
		{
			WSServletContextListener.checkUsers();
		}
		catch (Exception ex) {
			SystemDictionary.logException(ex);
		}
	}
	

}
