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
 * This class manages al events from the clinicians index page
 * 
 * @author Xavi Sarda (Atos Origin)
 */
public class IndexCliniciansWindow extends Window
{


	private static final long serialVersionUID = 3432071789993143298L;
	private String clinicianid = null;


	/**
	 * Invoke this method to remove a carer. It won't work unless you have set
	 * up the clinicianid attribute before
	 */
	public void deleteClinician()
	{
		Session ses = Sessions.getCurrent();
		String userid = (String) ses.getAttribute("userid");
		StorageComponentImpl proxy = new StorageComponentImpl();
		try
		{
			proxy.deleteClinician(this.clinicianid, userid);
			
			this.clinicianid = null;
			
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc(null);
			comp.setSrc("../clinicians/index_content.zul");
		}
		catch (Exception re)
		{
			SystemDictionary.logException(re);
			Messagebox.show("#TXT# Error : " + re.getMessage(), "#TXT# Delete Clinician", Messagebox.OK, Messagebox.ERROR);
		}
	}


	/**
	 * This method allow the user to remove clinicians on the index page with a
	 * confirmation dialog
	 * 
	 * @param id Id of the clinician which is going to be deleted
	 */
	public void deleteClinicianMsg(String id)
	{
		this.clinicianid = id;
		ConfirmDeleteClinician auxwin = new ConfirmDeleteClinician(id);
		Button btn = new Button();
		String text = Labels.getLabel("clinicians.delete.sure");
		btn.setLabel(text);
		btn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				deleteClinician();
			}
		});
		auxwin.addEventListener("onClose", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				clinicianid = null;
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
	 * Very simple method to redirect users to update clinicians information
	 * 
	 * @param id Clinician to be updated
	 */
	public void updateClinician(String id)
	{
		Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
		Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
		comp.setSrc("../clinicians/update.zul?clinid=" + id);
	}


	/**
	 * Very simple method to redirect users to see Clinicians details
	 * 
	 * @param id Clinician to be shown on the details page
	 */
	public void detailsClinician(String id)
	{
		Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
		Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
		comp.setSrc("../clinicians/details.zul?clinid=" + id);
	}


	@SuppressWarnings("serial")
	public class ConfirmDeleteClinician extends Window
	{


		public ConfirmDeleteClinician(String id)
		{
			Label message = new Label();
			String text = Labels.getLabel("clinicians.delete.confirm");
			message.setValue(text + " " + id + "?");
			this.appendChild(message);

			Separator sep = new Separator();
			sep.setHeight("10px");
			this.appendChild(sep);

			String text2 = Labels.getLabel("clinicians.delete");
			this.setTitle(text2);
			this.setBorder("normal");
			this.setClosable(true);
		}
		
		
	}

	
}
