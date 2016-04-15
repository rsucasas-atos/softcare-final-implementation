package eu.ehealth.controllers;

import java.util.Collection;
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


/**
 * This class manages al events from the carer index page
 * 
 * @author Xavi Sarda (Atos Origin)
 */
public class IndexCarersWindow extends Window
{


	private static final long serialVersionUID = -4585592732261850018L;
	private String carerid = null;


	/**
	 * Invoke this method to remove a carer. It won't work unless you have
	 * called deleteCarerMsg before
	 */
	public void deleteCarer()
	{
		Session ses = Sessions.getCurrent();
		String userid = (String) ses.getAttribute("userid");
		StorageComponentImpl proxy = new StorageComponentImpl();
		try
		{
			proxy.deleteCarer(this.carerid, userid);
			
			this.carerid = null;
			
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc(null);
			comp.setSrc("../carers/index_content.zul");
		}
		catch (Exception re)
		{
			SystemDictionary.logException(re);
			Messagebox.show("#TXT# Error : " + re.getMessage(), "#TXT# Delete Social worker", Messagebox.OK, Messagebox.ERROR);
		}
	}


	/**
	 * This method allow the user to remove carers from the index page with a
	 * confirmation dialog
	 * 
	 * @param id Id of the carer which is going to be deleted
	 */
	public void deleteCarerMsg(String id)
	{
		this.carerid = id;
		ConfirmDeleteCarer auxwin = new ConfirmDeleteCarer(id);
		Button btn = new Button();
		String text = Labels.getLabel("carers.delete.sure");
		btn.setLabel(text);
		btn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				deleteCarer();
			}
		});
		auxwin.addEventListener("onClose", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				carerid = null;
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


	/**
	 * Very simple method to redirect users to update Carers information
	 * 
	 * @param id Carer to be updated
	 */
	public void updateCarer(String id)
	{
		Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
		Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
		comp.setSrc("../carers/update.zul?carerid=" + id);
	}


	/**
	 * Very simple method to redirect users to see Carers details
	 * 
	 * @param id Carer to be shown on the details page
	 
	public void detailsCarer(String id)
	{
		Executions.sendRedirect("/carers/details.zul?carerid=" + id);
	}*/


	@SuppressWarnings("serial")
	public class ConfirmDeleteCarer extends Window
	{


		public ConfirmDeleteCarer(String id)
		{
			Label message = new Label();
			String confirm = Labels.getLabel("carers.delete.confirm");
			message.setValue(confirm + " " + id + "?");
			this.appendChild(message);

			Separator sep = new Separator();
			sep.setHeight("10px");
			this.appendChild(sep);

			String deletetxt = Labels.getLabel("carers.delete");
			this.setTitle(deletetxt);
			this.setBorder("normal");
			this.setClosable(true);
		}
		
		
	}
	

}
