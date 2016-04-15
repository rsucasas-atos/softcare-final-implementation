package eu.ehealth.controllers.externalservices;

import java.rmi.RemoteException;
import java.util.Collection;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Button;
import eu.ehealth.SystemDictionary;
import eu.ehealth.utilities.ComponentsFinder;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.ExternalService;


/**
 * 
 * 
 * @author 
 * @date 22/04/2014.
 *
 */
public class ExternalServicesIndexController extends Window
{


	private static final long serialVersionUID = -8597729277696823899L;
	public Window servicePopup;


	/**
	 * 
	 * @throws InterruptedException
	 */
	public void createService() throws InterruptedException
	{
		servicePopup = (Window) Executions.createComponents("../extsrv/form.zul", this, null);
		((Button) servicePopup.getFellow("savebutton")).setVisible(true);
		String title = Labels.getLabel("extsrv.new");
		servicePopup.setTitle(title);
		servicePopup.setVisible(true);
		servicePopup.doModal();
	}


	/**
	 * 
	 * @param srvid
	 * @throws RemoteException
	 * @throws InterruptedException
	 */
	public void modifyService(String srvid) throws RemoteException, InterruptedException
	{
		StorageComponentImpl proxy = new StorageComponentImpl();
		ExternalService defserv = null;
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		ExternalService[] services = proxy.getAllExternalServicesArr(userid);
		for (int i = 0; i < services.length; i++)
		{
			if (services[i].getID().equals(srvid))
			{
				defserv = services[i];
				break;
			}
		}

		servicePopup = (Window) Executions.createComponents("../extsrv/form.zul", this, null);
		
		int selectedindex = 1;
		if (defserv.getType().equals(SystemDictionary.EXT_SERV_EXERCISES)) {
			selectedindex = 0;
		}
		((Listbox) servicePopup.getFellow("content_type")).setSelectedIndex(selectedindex);
		
		((Button) servicePopup.getFellow("cancelbutton")).setVisible(true);
		((Textbox) servicePopup.getFellow("srvidfield")).setValue(srvid);
		((Textbox) servicePopup.getFellow("srvdescfield")).setValue(defserv.getDescription());
		((Textbox) servicePopup.getFellow("srvuricfield")).setValue(defserv.getAddress());
		String title = Labels.getLabel("extsrv.update");
		servicePopup.setTitle(title);
		servicePopup.setVisible(true);
		servicePopup.doModal();
	}


	/**
	 * 
	 * @param srvid
	 * @throws RemoteException
	 * @throws InterruptedException
	 */
	public void viewService(String srvid) throws RemoteException, InterruptedException
	{
		StorageComponentImpl proxy = new StorageComponentImpl();
		ExternalService defserv = null;
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		ExternalService[] services = proxy.getAllExternalServicesArr(userid);
		for (int i = 0; i < services.length; i++)
		{
			if (services[i].getID().equals(srvid))
			{
				defserv = services[i];
				break;
			}
		}
		servicePopup = (Window) Executions.createComponents("../extsrv/form.zul", this, null);
		
		int selectedindex = 1;
		if (defserv.getType().equals(SystemDictionary.EXT_SERV_EXERCISES)) {
			selectedindex = 0;
		}
		((Listbox) servicePopup.getFellow("content_type")).setSelectedIndex(selectedindex);
		((Listbox) servicePopup.getFellow("content_type")).setDisabled(true);
		
		((Button) servicePopup.getFellow("cancelbutton")).setVisible(false);
		((Button) servicePopup.getFellow("savebutton")).setVisible(false);
		((Textbox) servicePopup.getFellow("srvidfield")).setValue(srvid);
		((Textbox) servicePopup.getFellow("srvidfield")).setReadonly(true);
		((Textbox) servicePopup.getFellow("srvdescfield")).setValue(defserv.getDescription());
		((Textbox) servicePopup.getFellow("srvdescfield")).setReadonly(true);
		((Textbox) servicePopup.getFellow("srvuricfield")).setValue(defserv.getAddress());
		((Textbox) servicePopup.getFellow("srvuricfield")).setReadonly(true);
		String title = Labels.getLabel("common.view");
		servicePopup.setTitle(title);
		servicePopup.setVisible(true);
		servicePopup.doModal();
	}


	/**
	 * 
	 * @param srvid
	 */
	public void delteService(String srvid)
	{
		StorageComponentImpl proxy = new StorageComponentImpl();
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		try
		{
			proxy.deleteExternalService(srvid, userid);
			
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc(null);
			comp.setSrc("../extsrv/index_content.zul");
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
		}
	}
	

}
