package eu.ehealth.controllers.warnings;

import java.rmi.RemoteException;
import java.util.Collection;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;
import eu.ehealth.SystemDictionary;
import eu.ehealth.utilities.ComponentsFinder;
import eu.ehealth.ws_client.StorageComponentImpl;


/**
 * 
 * @author a572832
 *
 */
public class WarningsPopupController extends Window
{


	private static final long serialVersionUID = -6712984876432126616L;
	private String warningid;


	/**
	 * 
	 * @throws RemoteException
	 */
	public void markWarningAsRead() throws RemoteException
	{
		StorageComponentImpl proxy = new StorageComponentImpl();
		String uid = (String) Sessions.getCurrent().getAttribute("userid");
		try
		{
			proxy.markWarningAsRead(this.warningid, uid);
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
		}
		finally
		{
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc(null);
			comp.setSrc("../warnings/index_content.zul");
			
			this.setVisible(false);
			this.getParent().removeChild(this);
		}
	}


	/**
	 * 
	 * @return
	 */
	public String getWarningid()
	{
		return warningid;
	}


	/**
	 * 
	 * @param warningid
	 */
	public void setWarningid(String warningid)
	{
		this.warningid = warningid;
	}

	
}
