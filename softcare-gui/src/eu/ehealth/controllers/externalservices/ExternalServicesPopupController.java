package eu.ehealth.controllers.externalservices;

import java.util.Collection;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
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
public class ExternalServicesPopupController extends Window
{


	private static final long serialVersionUID = 6419201142415762086L;


	/**
	 * 
	 */
	public void createExternalServiceToStorage()
	{
		String description = ((Textbox) getFellow("srvdescfield")).getValue();
		String uri = ((Textbox) getFellow("srvuricfield")).getValue();
		StorageComponentImpl proxy = new StorageComponentImpl();
		Session ses = Sessions.getCurrent();
		String userid = (String) ses.getAttribute("userid");
		String type = (String) ((Listbox) getFellow("content_type")).getSelectedItem().getValue();

		ExternalService extservice = new ExternalService("", description, uri, type);
		try
		{
			proxy.createExternalService(extservice, userid);
		}
		catch (Exception re)
		{
			SystemDictionary.logException(re);
		}
		finally
		{
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc(null);
			comp.setSrc("../extsrv/index_content.zul");
			
			this.setVisible(false);
			this.getParent().removeChild(this);
		}
	}


	/**
	 * 
	 */
	public void modifyExternalServiceToStorage()
	{
		String description = ((Textbox) getFellow("srvdescfield")).getValue();
		String uir = ((Textbox) getFellow("srvuricfield")).getValue();
		String srvid = ((Textbox) getFellow("srvidfield")).getValue();
		StorageComponentImpl proxy = new StorageComponentImpl();
		Session ses = Sessions.getCurrent();
		String userid = (String) ses.getAttribute("userid");
		String type = (String) ((Listbox) getFellow("content_type")).getSelectedItem().getValue();
		
		ExternalService extservice = new ExternalService(srvid, description, uir, type);
		try
		{
			proxy.updateExternalService(extservice, userid);
		}
		catch (Exception re)
		{
			SystemDictionary.logException(re);
		}
		finally
		{
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc(null);
			comp.setSrc("../extsrv/index_content.zul");
			
			this.setVisible(false);
			this.getParent().removeChild(this);
		}
	}
	

}
