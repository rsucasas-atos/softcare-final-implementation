package eu.ehealth.controllers;

import java.rmi.RemoteException;
import java.util.Collection;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import eu.ehealth.SystemDictionary;
import eu.ehealth.controllers.details.ChangePassword;
import eu.ehealth.utilities.ComponentsFinder;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.Administrator;
import eu.ehealth.ws_client.xsd.Clinician;
import eu.ehealth.ws_client.xsd.Identifier;
import eu.ehealth.ws_client.xsd.OperationResult;
import eu.ehealth.ws_client.xsd.PersonData;
import eu.ehealth.ws_client.xsd.User;


/**
 * 
 * @author a572832
 *
 */
public class AdministratorControllerWindow extends AladdinFormControllerWindow
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7379494223188983856L;

	
	/**
	 * Default constructor
	 */
	public AdministratorControllerWindow()
	{
		isNewUser = true;
		this.buildForm(true);
	}


	/**
	 * Constructor for changing the create form into an update one
	 * 
	 * @param current Administrator to be updated
	 * @param details Boolean flag to let the constructor know that is a update
	 *            operation
	 */
	public AdministratorControllerWindow(Administrator current, boolean details)
	{
		isNewUser = false;
		
		this.currentid = current.getID();
		this.currentdata = current.getPersonData();
		this.detailsmode = details;

		this.buildForm(false);

		this.addPersonFieldsValues();
		this.addCommunicationFieldsValues();
		Hbox buttonshbox = new Hbox();

		if (this.detailsmode)
		{
			Boolean isadmin = (Boolean) Sessions.getCurrent().getAttribute("isadmin");
			if (isadmin)
			{
				buttonshbox.appendChild(this.createEditButton());
				Separator sep = new Separator();
				sep.setWidth("10px");
				sep.setOrient("horizontal");
				buttonshbox.appendChild(sep);
				buttonshbox.appendChild(this.createPasswordButton());
			}
		}
		else
		{
			buttonshbox.appendChild(this.createUpdateButton());
		}
		this.appendChild(buttonshbox);
		this.getFellow("pat_uname").getParent().setVisible(false);
	}


	/**
	 * Build form instructions to be executed
	 */
	public void buildForm(boolean newAdministrator)
	{
		this.addErrorBox();
		this.addPersonFields(newAdministrator);
		this.addCommunicationFields();
	}


	/**
	 * It creates a new Administrator on the DataStorage. Function to be executed on
	 * "Save" button click.
	 */
	public void createAdministrator()
	{
		if (!validateForm())
		{
			return;
		}
		
		SystemDictionary.webguiLog("TRACE", "Saving Administrator...");
		// Getting information from form fields
		PersonData personData = this.getPersonData();

		// Create ID
		Identifier ident = new Identifier();
		String adminID = ident.getType() + '-' + ident.getNumber();

		// TODO isPrimary control on Communication and Addresses
		Administrator admin = new Administrator(adminID, personData);
		try
		{
			StorageComponentImpl proxy = new StorageComponentImpl();
			Session ses = Sessions.getCurrent();
			String id = (String) ses.getAttribute("userid");
			String username = this.getUsername();
			OperationResult result = proxy.createAdministrator(admin, id);
			
			User user = createNewUser(SystemDictionary.USERTYPE_ADMIN, result.getCode(), username);
			result = proxy.createUser(user);
			if (!result.getDescription().equals("ok"))
			{
				SystemDictionary.webguiLog("WARN", "Error creating user");
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("Username not valid");
				getFellow("internalformerror").setVisible(true);
				SystemDictionary.webguiLog("TRACE", "Deleting Administrator...");
				OperationResult newresult = proxy.deleteClinician(user.getPersonID(), id);
				SystemDictionary.webguiLog("TRACE", "Delete Administrator result: " + newresult.getCode());
				return;
			}
			
			Messagebox.show("#TXT# Admninistrator created succesfully", "#TXT# Create New Administrator", Messagebox.OK, Messagebox.INFORMATION);

			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc("../administration/index_content.zul");
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
			Messagebox.show("#TXT# Error : " + e.getMessage(), "#TXT# Create New Administrator", Messagebox.OK, Messagebox.ERROR);
		}
	}


	/**
	 * It sends the updates of an existing Administrator to the StorageComponent.
	 * Function to be executed on "Update" button click.
	 */
	public void updateAdministrator()
	{
		PersonData personData = this.getPersonData();

		Administrator admin = new Administrator(this.currentid, personData);
		try
		{
			StorageComponentImpl proxy = new StorageComponentImpl();
			Session ses = Sessions.getCurrent();
			String id = (String) ses.getAttribute("userid");
			proxy.updateAdministrator(admin, id); 
			
			Messagebox.show("#TXT# Admninistrator updated succesfully", "#TXT# Update Administrator", Messagebox.OK, Messagebox.INFORMATION);

			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc("../administration/index_content.zul");
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
			Messagebox.show("#TXT# Error : " + e.getMessage(), "#TXT# Update Administrator", Messagebox.OK, Messagebox.ERROR);
		}
	}


	/**
	 * This method creates a button to create new clinicians using the current
	 * form
	 * 
	 * @return Button to be added to the form
	 */
	public Button createUpdateButton()
	{
		Button btn = new Button();
		String text = Labels.getLabel("administrators.update.title");
		btn.setLabel(text);
		btn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				updateAdministrator();
			}
		});

		return btn;
	}


	/**
	 * This method creates a button to modify an existing clinician using the
	 * current form
	 * 
	 * @return Button to be added to the form
	 */
	public Button createEditButton()
	{
		Button btn = new Button();
		String text = Labels.getLabel("administrators.edit");
		btn.setLabel(text);
		btn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
				Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
				comp.setSrc("../administration/update.zul?clinid=" + currentid);
			}
		});

		return btn;
	}


	/**
	 * 
	 * @return
	 */
	public Button createPasswordButton()
	{
		Button btn = new Button("Change Password");
		btn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				createPasswordDialog();
			}
		});
		return btn;
	}


	/**
	 * 
	 * @throws SuspendNotAllowedException
	 * @throws InterruptedException
	 * @throws RemoteException
	 */
	public void createPasswordDialog() throws SuspendNotAllowedException, InterruptedException, RemoteException
	{
		ChangePassword win = (ChangePassword) Executions.createComponents("../administration/password.zul", this, null);

		this.appendChild(win);
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		//OperationResult ores = proxy.getUserIdByPersonId(this.currentid, SystemDictionary.USERTYPE_ADMIN_INT, userid);
		OperationResult ores = proxy.getUserIdByAdminId(this.currentid, userid);
		win.setuserid(ores.getCode());
		win.doModal();
	}
	
	
	/**
	 * 
	 * @return
	 */
	private boolean validateForm() {
		try {
			// check password fields
			if (isNewUser) {
				if (!validatePsswd())
					return false;
			}
						
			// addresses, communications
			if (this.communications == null || this.communications.length == 0)
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("You cannot create a user without any way to communicate with");
				getFellow("internalformerror").setVisible(true);
				
				return false;
			}
			
			// pat_name, pat_sname, pat_uname
			if (((Textbox) getFellow("pat_name")).getValue() == null || ((Textbox) getFellow("pat_name")).getValue().trim().equals(""))
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("You must enter a valid name");
				getFellow("internalformerror").setVisible(true);
				
				((Textbox) getFellow("pat_name")).select();
				return false;
			}
			else if (((Textbox) getFellow("pat_sname")).getValue() == null || ((Textbox) getFellow("pat_sname")).getValue().trim().equals(""))
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("You must enter a valid surname");
				getFellow("internalformerror").setVisible(true);
				
				((Textbox) getFellow("pat_sname")).select();
				return false;
			}
			else if (((Textbox) getFellow("pat_uname")).getValue() == null || ((Textbox) getFellow("pat_uname")).getValue().trim().equals(""))
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("You must enter a valid username");
				getFellow("internalformerror").setVisible(true);
				
				((Textbox) getFellow("pat_uname")).select();
				return false;
			}
					
			return true;
		}
		catch (Exception e) {
			SystemDictionary.webguiLog("ERROR", e.getMessage());
			return false;
		}	
	}
	
	
}
