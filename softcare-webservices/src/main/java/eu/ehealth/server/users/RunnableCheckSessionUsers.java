package eu.ehealth.server.users;

import java.text.SimpleDateFormat;
import java.util.Date;
import eu.ehealth.SystemDictionary;
import eu.ehealth.server.context.WSServletContextListener;


/**
 * 
 * @author a572832
 *
 */
public class RunnableCheckSessionUsers implements Runnable 
{

	
	@Override
	public void run() {
		try 
		{
			// debug task
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	        Date resultdate = new Date(System.currentTimeMillis());
	        sdf.format(resultdate);
			SystemDictionary.webguiLog("DEBUG", "PERIODIC TASK: Calling WSServletContextListener.checkUsers() ... [" + resultdate + "]");
			
			WSServletContextListener.checkUsers();
		}
		catch (Exception ex) {
			SystemDictionary.logException(ex);
		}
	}
	

}
