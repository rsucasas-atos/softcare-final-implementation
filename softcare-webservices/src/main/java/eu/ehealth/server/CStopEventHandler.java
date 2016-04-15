package eu.ehealth.server;

import java.util.concurrent.TimeUnit;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import eu.ehealth.SystemDictionary;


/**
 * 
 * @author a572832
 *
 */
public class CStopEventHandler implements ApplicationListener<ContextStoppedEvent> 
{

	
	@Override
	public void onApplicationEvent(ContextStoppedEvent arg0) 
	{
		SystemDictionary.webguiLog("DEBUG", "ContextStoppedEvent ...");
	
		if (SystemDictionary._scheduler != null) {
			try 
			{
				SystemDictionary._scheduler.awaitTermination(10, TimeUnit.SECONDS);
				SystemDictionary.webguiLog("DEBUG", "Task stopped ...");
			} 
			catch (InterruptedException e) 
			{
				SystemDictionary.logException(e);
			}
		}
	}

	
}
