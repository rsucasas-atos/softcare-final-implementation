package eu.ehealth.controllers;

import java.util.Collection;
import java.util.StringTokenizer;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Window;

import eu.ehealth.SystemDictionary;
import eu.ehealth.utilities.ComponentsFinder;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.OperationResult;


/**
 * 
 * @author a572832
 *
 */
public class IndexAdministratorsWindow extends Window
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1229113934597753386L;
	
	
	private String adminid = null;


	/**
	 * Invoke this method to remove an administrator. It won't work unless you have set
	 * up the administratorid attribute before
	 */
	public void deleteAdministrator()
	{
		Session ses = Sessions.getCurrent();
		String userid = (String) ses.getAttribute("userid");
		StorageComponentImpl proxy = new StorageComponentImpl();
		try
		{
			proxy.deleteAdministrator(this.adminid, userid);
			
			this.adminid = null;
			
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc(null);
			comp.setSrc("../administration/index_content.zul");
		}
		catch (Exception re)
		{
			SystemDictionary.logException(re);
			Messagebox.show("#TXT# Error : " + re.getMessage(), "#TXT# Delete Administrator", Messagebox.OK, Messagebox.ERROR);
		}
	}


	/**
	 * This method allow the user to remove administrator on the index page with a
	 * confirmation dialog
	 * 
	 * @param id_personid Id of the administrator which is going to be deleted
	 */
	public void deleteAdministratorMsg(String id_personid)
	{
		try {
			StringTokenizer tokens = new StringTokenizer(id_personid, "-");

			String userid = (String) Sessions.getCurrent().getAttribute("userid");
			
			if ((tokens == null) || (tokens.countTokens() != 2)) 
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("-Internal error-");
				getFellow("internalformerror").setVisible(true);
				return;
			}

			String id = tokens.nextToken().trim();
			String personid = tokens.nextToken().trim();
			
			StorageComponentImpl proxy = new StorageComponentImpl();
			OperationResult ores = proxy.getUserIdByPersonId(personid, SystemDictionary.USERTYPE_ADMIN_INT, userid);
			
			if (ores.getCode().equals(userid)) {
				// CAN'T DELETE YOURSELF
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("#TXT# -YOU CAN'T DELETE YOURSELF-");
				getFellow("internalformerror").setVisible(true);
				return;
			}

			this.adminid = id;
			ConfirmDeleteAdministrator auxwin = new ConfirmDeleteAdministrator(id);
			Button btn = new Button();
			String text = Labels.getLabel("administrators.delete.sure");
			btn.setLabel(text);
			btn.addEventListener("onClick", new EventListener() {
				public void onEvent(Event arg0) throws Exception
				{
					deleteAdministrator();
				}
			});
			auxwin.addEventListener("onClose", new EventListener() {
				public void onEvent(Event arg0) throws Exception
				{
					adminid = null;
				}
			});
			auxwin.appendChild(btn);
			this.appendChild(auxwin);
			try
			{
				auxwin.doModal();
			}
			catch (Exception ee)
			{
				SystemDictionary.logException(ee);
			}
		}
		catch (Exception ee)
		{
			SystemDictionary.logException(ee);
		}
	}


	/**
	 * Very simple method to redirect users to update Administrators information
	 * 
	 * @param id Administrator to be updated
	 */
	public void updateAdministrator(String id_personid)
	{
		StringTokenizer tokens = new StringTokenizer(id_personid, "-");
	
		if ((tokens == null) || (tokens.countTokens() != 2))
		{
			Window win = (Window) getFellow("internalformerror");
			((Label) win.getFellow("errorlbl")).setValue("-Internal error-");
			getFellow("internalformerror").setVisible(true);
			return;
		}

		String id = tokens.nextToken().trim();
		
		Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
		Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
		comp.setSrc("../administration/update.zul?clinid=" + id);
	}


	/**
	 * Very simple method to redirect users to see Administrators details
	 * 
	 * @param id Administrator to be shown on the details page
	 */
	public void detailsAdministrator(String id_personid)
	{
		StringTokenizer tokens = new StringTokenizer(id_personid, "-");
	
		if ((tokens == null) || (tokens.countTokens() != 2))
		{
			Window win = (Window) getFellow("internalformerror");
			((Label) win.getFellow("errorlbl")).setValue("-Internal error-");
			getFellow("internalformerror").setVisible(true);
			return;
		}

		String id = tokens.nextToken().trim();
		
		Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
		Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
		comp.setSrc("../administration/details.zul?clinid=" + id);
	}


	@SuppressWarnings("serial")
	public class ConfirmDeleteAdministrator extends Window
	{


		public ConfirmDeleteAdministrator(String id)
		{
			Label message = new Label();
			String text = Labels.getLabel("administrators.delete.confirm");
			message.setValue(text + " " + id + "?");
			this.appendChild(message);

			Separator sep = new Separator();
			sep.setHeight("10px");
			this.appendChild(sep);

			String text2 = Labels.getLabel("administrators.delete");
			this.setTitle(text2);
			this.setBorder("normal");
			this.setClosable(true);
		}
		
		
	}
	

}
