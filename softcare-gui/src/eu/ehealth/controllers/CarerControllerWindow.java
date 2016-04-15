package eu.ehealth.controllers;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Window;
import eu.ehealth.SystemDictionary;
import eu.ehealth.controllers.details.ChangePassword;
import eu.ehealth.controllers.details.assessment.CarerAssessmentInfo;
import eu.ehealth.controllers.details.assessment.CarerAssessmentPopupController;
import eu.ehealth.utilities.ComponentsFinder;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.Carer;
import eu.ehealth.ws_client.xsd.CarerAssessment;
import eu.ehealth.ws_client.xsd.OperationResult;
import eu.ehealth.ws_client.xsd.PersonData;
import eu.ehealth.ws_client.xsd.SocioDemographicData;
import eu.ehealth.ws_client.xsd.User;


/**
 * Public class built to manage Carer forms to create and update carrer objects
 * 
 * @author Xavi Sarda (Atos Origin)
 */
public class CarerControllerWindow extends SDFormControllerWindow
{


	private static final long serialVersionUID = 7241911276370717234L;
	private CarerAssessmentPopupController assessmentWindow;


	/**
	 * Default constructor (for new Carer)
	 */
	public CarerControllerWindow()
	{
		isNewUser = true;
		this.buildForm(true);
	}


	/**
	 * Constructor for updating a Carer
	 * 
	 * @param current Carer object to be updated
	 * @param details Boolean flag to let the called methods that they must be shown in a proper way to update carers objects
	 */
	public CarerControllerWindow(Carer current, boolean details)
	{
		isNewUser = false;
		
		this.currentid = current.getID();
		this.currentdata = current.getPersonData();
		this.currentsd = current.getSDData();
		this.detailsmode = details;
		this.buildForm(false);

		this.addPersonFieldsValues();
		this.addAddressFieldsValues();
		this.addCommunicationFieldsValues();
		this.addBirthdayFieldValue(this.currentsd.getBirthdayDate());
		Hbox buttonshbox = new Hbox();
		if (this.detailsmode)
		{
			buttonshbox.appendChild(this.createEditButton());
		}
		else
		{
			buttonshbox.appendChild(this.createUpdateButton());
		}
		Separator sep = new Separator();
		sep.setWidth("10px");
		sep.setOrient("horizontal");
		buttonshbox.appendChild(sep);
		buttonshbox.appendChild(this.createPasswordButton());
		this.appendChild(buttonshbox);
		this.getFellow("pat_uname").getParent().setVisible(false);
	}


	/**
	 * Build form instructions to be executed
	 */
	public void buildForm(boolean newCarer)
	{
		this.addErrorBox();
		this.addPersonFields(newCarer);
		this.addAddressFields();
		this.addCommunicationFields();
		this.addBirthdayField();
	}


	/**
	 * Submit function: Used on the view layer to create a new Patient (using
	 * the StorageComponentImpl).
	 * 
	 * @return void but saves a new Patient on the StorageComponent
	 */
	public void createCarer()
	{
		if (!validateForm())
		{
			return;
		}
		
		Date birthd = ((Datebox) this.getFellow("pat_age")).getValue();

		// Getting information from form fields
		PersonData personData = this.getPersonData();
		// SocioDemographicData sdData = this.getSocioDemographicData();
		SocioDemographicData sdData = new SocioDemographicData();

		sdData.setBirthdayDate(birthd);
		// TODO isPrimary control on Communication and Addresses
		Carer carer = new Carer("", personData, sdData);
		try
		{
			StorageComponentImpl proxy = new StorageComponentImpl();
			Session ses = Sessions.getCurrent();
			String id = (String) ses.getAttribute("userid");
			OperationResult result = proxy.createCarer(carer, id);
			String username = this.getUsername();
			
			User user = createNewUser(SystemDictionary.USERTYPE_CARER, result.getCode(), username);
			result = proxy.createUser(user);
			if (!result.getDescription().equals("ok"))
			{
				SystemDictionary.webguiLog("WARN", "#TXT# Error creating user");
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("#TXT# Username not valid");
				getFellow("internalformerror").setVisible(true);
				proxy.deleteCarer(user.getPersonID(), id);
				return;
			}
			
			Messagebox.show("#TXT# Social worker created succesfully", "#TXT# Create New Social Worker", Messagebox.OK, Messagebox.INFORMATION);

			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc("../carers/index_content.zul");
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
			Messagebox.show("#TXT# Error : " + e.getMessage(), "#TXT# Create New Social Worker", Messagebox.OK, Messagebox.ERROR);
		}
	}


	/**
	 * Submit function: Used on the view layer to update an existing Patient
	 * using the StorageComponentImpl appropriate call.
	 * 
	 * @return void but saves a new Patient on the StorageComponent
	 */
	public void updateCarer()
	{
		// Getting information from form fields
		PersonData personData = this.getPersonData();
		// SocioDemographicData sdData = this.getSocioDemographicData();
		SocioDemographicData sdData = new SocioDemographicData();
		sdData.setBirthdayDate(((Datebox) this.getFellow("pat_age")).getValue());
		// TODO isPrimary control on Communication and Addresses
		Carer carer = new Carer(this.currentid, personData, sdData);
		try
		{
			StorageComponentImpl proxy = new StorageComponentImpl();
			Session ses = Sessions.getCurrent();
			String id = (String) ses.getAttribute("userid");
			proxy.updateCarer(carer, id);
			
			Messagebox.show("#TXT# Social worker updated succesfully", "#TXT# Update Social Worker", Messagebox.OK, Messagebox.INFORMATION);
			
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc("../carers/index_content.zul");
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
			Messagebox.show("#TXT# Error : " + e.getMessage(), "#TXT# Update Social Worker", Messagebox.OK, Messagebox.ERROR);
		}
	}


	/**
	 * This method creates a button to update carers using the existing form
	 * 
	 * @return Button object to be added to the form
	 */
	public Button createUpdateButton()
	{
		Button btn = new Button();
		String text = Labels.getLabel("carers.update.title");
		btn.setLabel(text);
		btn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				updateCarer();
			}
		});

		return btn;
	}


	/**
	 * This method creates a button to allow modification to the current carer
	 * using this form
	 * 
	 * @return Button object to be added to the form
	 */
	public Button createEditButton()
	{
		Button btn = new Button();
		String text = Labels.getLabel("carers.edit");
		btn.setLabel(text);
		btn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
				Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
				comp.setSrc("../carers/update.zul?carerid=" + currentid);
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
		ChangePassword win = (ChangePassword) Executions.createComponents("../carers/password.zul", this, null);

		this.appendChild(win);
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		//OperationResult ores = proxy.getUserIdByPersonId(this.currentid, SystemDictionary.USERTYPE_CARER_INT, userid);
		OperationResult ores = proxy.getUserIdByCarerId(this.currentid, userid);
		win.setuserid(ores.getCode());
		win.doModal();
	}


	/**
	 * 
	 * @param assid
	 * @param carerid
	 */
	public void viewAssessmentDetail(String assid, String carerid)
	{
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		CarerAssessment assessment = null;
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		try
		{
			CarerAssessment[] info = proxy.getCarerAssessmentsArr(carerid, userid);
			for (int i = 0; i < info.length; i++)
			{
				if (info[i].getID().equals(assid))
				{
					assessment = info[i];
				}
			}
			if (assessment == null)
			{
				throw new Exception("Assessment not found");
			}
			assessmentWindow = (CarerAssessmentPopupController) Executions.createComponents("assessmentcar.zul", this, null);
			this.setAssessmentValues(assessmentWindow, assessment);
			assessmentWindow.setVisible(true);
			assessmentWindow.doModal();
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
		}
	}


	/**
	 * 
	 * @throws SuspendNotAllowedException
	 * @throws InterruptedException
	 */
	public void createAssessment() throws SuspendNotAllowedException, InterruptedException
	{
		assessmentWindow = (CarerAssessmentPopupController) Executions.createComponents("../carers/assessmentcar.zul", this, null);
		this.turnAssessment2Form(assessmentWindow);
		assessmentWindow.setVisible(true);
		assessmentWindow.doModal();
	}


	/**
	 * 
	 * @param assessmentWindow
	 */
	protected void turnAssessment2Form(CarerAssessmentPopupController assessmentWindow)
	{
		for (int i = 0; i <= 4; i++)
		{
			if (i < 10)
			{
				assessmentWindow.getFellow("field0" + i).setVisible(false);
				assessmentWindow.getFellow("field0" + i + "_in").setVisible(true);
			}
		}
		assessmentWindow.getFellow("buttonrow").setVisible(true);
		assessmentWindow.getFellow("noformrow").setVisible(false);
		assessmentWindow.getFellow("datelabel").setVisible(false);
		((Textbox) assessmentWindow.getFellow("carerid")).setValue(this.currentid);
	}


	/**
	 * 
	 * @param assessmentWindow
	 * @param assessment
	 */
	protected void setAssessmentValues(CarerAssessmentPopupController assessmentWindow, CarerAssessment assessment)
	{
		CarerAssessmentInfo cassessment = new CarerAssessmentInfo(assessment);
		((Label) assessmentWindow.getFellow("field0")).setValue(cassessment.getID());
		((Label) assessmentWindow.getFellow("field1")).setValue(cassessment.getCarerID());
		((Label) assessmentWindow.getFellow("field00")).setValue(cassessment.getRelevantHealthProblem());
		((Label) assessmentWindow.getFellow("field01")).setValue(cassessment.getSeverityOfBurden().toString());
		((Label) assessmentWindow.getFellow("field02")).setValue(cassessment.getEmotionalOrMentalDisorders());
		((Label) assessmentWindow.getFellow("field03")).setValue(cassessment.getPsychoactiveDrugs());
		((Label) assessmentWindow.getFellow("field04")).setValue(cassessment.getQualityOfLife().toString());
		((Label) assessmentWindow.getFellow("field05")).setValue(cassessment.getDateOfAssessment());
	}


	/**
	 * 
	 * @param d
	 */
	public void addBirthdayFieldValue(Date d)
	{
		((Datebox) this.getFellow("pat_age")).setValue(d);
	}
	
	
	/**
	 * 
	 * @return
	 */
	private boolean validateForm() {
		try {
			// check common fields
			if (!validateFormCommonFields())
				return false;
			
			return true;
		}
		catch (Exception e) {
			SystemDictionary.webguiLog("ERROR", e.getMessage());	
			return false;
		}	
	}
	
	
}
