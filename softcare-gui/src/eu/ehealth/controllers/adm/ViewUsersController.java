package eu.ehealth.controllers.adm;

import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import eu.ehealth.SystemDictionary;


/**
 * 
 * @author a572832
 *
 */
public class ViewUsersController extends GenericForwardComposer 
{
	

	private static final long serialVersionUID = -4020287853971832463L;
	private Grid inboxGrid;

	
	/**
	 * 
	 */
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		inboxGrid.setModel(new ListModelList(getData()));
	}

	
	/**
	 * 
	 * @param e
	 */
	public void onTimer$timer(Event e) {
		inboxGrid.setModel(new ListModelList(getData()));
	}

	
	/*
	 * simply return a small model here , you could read data from database for
	 * your own implementation.
	 */
	private List<String[]> getData() 
	{
		ArrayList<String[]> list = new ArrayList<String[]>();
		
		try 
		{
			String userid = (String) session.getAttribute("userid");
			
			Object[] res = SystemDictionary.getSCProxy().getConnectedUsers(userid);
			
			if ((res != null) && (res.length > 0))
			{
				for (int i=0; i<res.length; i++) 
				{
					list.add((String[]) res[i]);
				}
			}
		}
		catch (Exception ex) 
		{
			SystemDictionary.logException(ex);
		}
		
		return list;
	}

	
}
