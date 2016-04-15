package eu.ehealth.controllers.calendar;

import org.zkoss.calendar.impl.SimpleCalendarEvent;
import java.util.Map;


/**
 * 
 * @author a572832
 *
 */
public class AladdinCalendarEvent extends SimpleCalendarEvent
{

	
	private Map<String,Object> _params;
	
	
	public AladdinCalendarEvent()
	{
		super();
	}
	
	
	public Map<String, Object> getParams() {
		return _params;
	}
	
	
	public void setParams(Map<String, Object> _params) {
		this._params = _params;
	}
	
	
}
