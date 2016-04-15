package eu.ehealth.controllers;

import java.util.ArrayList;
import java.util.Collection;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import eu.ehealth.SystemDictionary;
import eu.ehealth.utilities.ComponentsFinder;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.Carer;
import eu.ehealth.ws_client.xsd.Clinician;
import eu.ehealth.ws_client.xsd.Consulter;
import eu.ehealth.ws_client.xsd.GeneralPractitioner;
import eu.ehealth.ws_client.xsd.OperationResult;
import eu.ehealth.ws_client.xsd.Patient;
import eu.ehealth.ws_client.xsd.PersonData;
import eu.ehealth.ws_client.xsd.SocialWorker;
import eu.ehealth.ws_client.xsd.SocioDemographicData;
import eu.ehealth.ws_client.xsd.User;


/**
 * This class handles the creation and the events of the Patient questionnaire.
 * 
 * @author Xavi Sarda (Atos Origin)
 */
public class PattientControllerWindow extends SDFormControllerWindow
{


	private static final long serialVersionUID = 3014122995824061686L;
	private CarerListWindowController clist = null;
	private String currentresp = null;
	private SocialWorker currentsocialworker = null;
	private Consulter currentconsulter = null;
	private GeneralPractitioner currentgeneralpracticioner = null;
	private Carer currentcarers = null;


	/**
	 * Default constructor
	 */
	public PattientControllerWindow()
	{
		isNewUser = true;
		this.buildForm(true);
	}


	/**
	 * Constructor to change the create form into an update form
	 * 
	 * @param current Patient to be updated
	 */
	public PattientControllerWindow(Patient current)
	{
		isNewUser = false;
		
		this.currentid = current.getID();
		this.currentdata = current.getPersonData();
		this.currentsd = current.getSDData();
		this.currentresp = current.getResponsibleClinicianID();
		this.currentsocialworker = current.getSocialWorker();
		this.currentconsulter = current.getConsulterInCharge();
		this.currentgeneralpracticioner = current.getGeneralPractitioner();
		this.currentcarers = current.getPatientCarer();

		this.buildForm(false);

		this.addPersonFieldsValues();
		this.addAddressFieldsValues();
		this.addCommunicationFieldsValues();
		this.addSocioDemographicDataFieldsValue();
		this.addResponsibleClinicianFieldValues();
		this.addCarerFieldValues();
		this.addSocialWorkerConsulterAndGPFieldsValues();
		this.appendChild(this.createUpdateButton());
		this.getFellow("pat_uname").getParent().setVisible(false);
	}


	/**
	 * Build form instructions to be executed
	 */
	public void buildForm(boolean newPatient)
	{
		this.addErrorBox();
		this.addResponsibleClinicianField();
		this.addPersonFields(newPatient);
		this.addAddressFields();
		this.addCommunicationFields();
		this.addSocioDemographicDataFields();
		this.addCarerField();
		this.addSocialWorkerConsulterAndGPFields();
	}


	/**
	 * Submit function: Used on the view layer to create a new Patient (using
	 * the StorageComponentImpl).
	 * 
	 * @return void but saves a new Patient on the server
	 */
	public void sendPatient(boolean newpatient)
	{
		if (!validateForm())
		{
			return;
		}
	
		OperationResult result = null;
		String resClinic = ((Textbox) getFellow("pat_respo")).getValue();
		String carerId = ((Textbox) getFellow("pat_carid")).getValue();

		// Getting information from form fields
		SocioDemographicData sdData = this.getSocioDemographicData();
		PersonData personData = this.getPersonData();
		SocialWorker socialw = this.getSocialWorkerData();
		Consulter consulter = this.getConsulterData();
		GeneralPractitioner gralprac = this.getGeneralPracticionerData();
		try
		{
			StorageComponentImpl proxy = SystemDictionary.getSCProxy();
			Session ses = Sessions.getCurrent();
			String id = (String) ses.getAttribute("userid");
			
			Carer car2set = proxy.getCarer(carerId, id);
			Patient patient = new Patient("", personData, sdData, resClinic, socialw, consulter, gralprac, car2set);
			
			if (newpatient)
			{
				result = proxy.createPatient(patient, id);
				String username = this.getUsername();

				User user = createNewUser(SystemDictionary.USERTYPE_PATIENT, result.getCode(), username);
				result = proxy.createUser(user);
				if (!result.getDescription().equals("ok"))
				{
					Window win = (Window) getFellow("internalformerror");
					((Label) win.getFellow("errorlbl")).setValue("Username not valid");
					getFellow("internalformerror").setVisible(true);
					proxy.deletePatient(user.getPersonID(), id);
					return;
				}
				
				Messagebox.show("#TXT# User (id= " + result.getCode() + ") created succesfully", "#TXT# Create New User", 
						Messagebox.OK, Messagebox.INFORMATION);
			}
			else
			{
				patient.setID(this.currentid);
				result = proxy.updatePatient(patient, id);
				
				Messagebox.show("#TXT# User modified succesfully", "#TXT# Modify User", Messagebox.OK, Messagebox.INFORMATION);
			}

			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc("../patients/index_content.zul");
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
			Messagebox.show("#TXT# Error : " + e.getMessage(), "#TXT# Create New User", Messagebox.OK, Messagebox.ERROR);
		}
	}


	/**
	 * Creates a modal window to select a primary carer for the patient
	 * 
	 * @throws InterruptedException
	 */
	public void createDialog() throws InterruptedException
	{
		try
		{
			this.clist = new CarerListWindowController(this);
			this.appendChild(this.clist);
			this.clist.doModal();
			
			this.clist = null;
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
		}
	}


	/**
	 * This method creates a clinician list for the patient form. It is also in
	 * charge of attaching it to the form and showing it in a modal mode
	 * 
	 * @throws InterruptedException
	 */
	public void createClinicianDialog() throws InterruptedException
	{
		if (this.getFellowIfAny("rclinlistwin") == null)
		{
			ClinicianListForPatients respolist = 
					(ClinicianListForPatients) Executions.getCurrent().createComponents("../patients/clinlist.zul", this, null);
			respolist.doModal();
		}
	}


	/**
	 * This method is used to set Carer form fields with information from the
	 * modal dialog fields
	 * 
	 * @param carerID
	 * @param carerName
	 */
	public void setCarer(String carerID, String carerName)
	{
		((Textbox) this.getFellow("pat_carid")).setValue(carerID);
		((Textbox) this.getFellow("pat_carname")).setValue(carerName);
	}


	/**
	 * This method is used to set responsible clinician form fields with
	 * information from the modal dialog fields
	 * 
	 * @param clinID Id of the responsible clinician
	 * @param clinName Name of the responsible clinician
	 */
	public void setResponsibleClinician(String clinID, String clinName)
	{
		((Textbox) this.getFellow("pat_respo")).setValue(clinID);
		((Textbox) this.getFellow("pat_respo_lbl")).setValue(clinName);
	}


	/**
	 * Protected function to add a responsible clinician field to the window.
	 */
	protected void addResponsibleClinicianField()
	{
		String respo = Labels.getLabel("patients.form.responsible");

		ArrayList<SimpleFieldData> rowsA = new ArrayList<SimpleFieldData>();
		rowsA.add(new SimpleFieldData(respo, "pat_respo_lbl"));

		Grid pgrid = new Grid();
		pgrid.setSclass("grid");
		this.appendColumns(pgrid);

		Rows rows = new Rows();

		Row rowhidf = new Row();
		Label lbl_ins = new Label(respo);
		rowhidf.appendChild(lbl_ins);
		Hbox hbox1 = new Hbox();
		Textbox tbox1 = new Textbox();
		tbox1.setId("pat_respo_lbl");
		tbox1.setReadonly(true);
		tbox1.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				createClinicianDialog();
			}
		});

		Textbox tbox2 = new Textbox();
		tbox2.setVisible(false);
		tbox2.setId("pat_respo");

		hbox1.appendChild(tbox1);
		hbox1.appendChild(tbox2);
		rowhidf.appendChild(hbox1);
		rows.appendChild(rowhidf);

		pgrid.appendChild(rows);
		this.appendChild(pgrid);
	}


	/**
	 * This method sets the responsible clinician values when updating a form
	 */
	protected void addResponsibleClinicianFieldValues()
	{
		String id = (String) Sessions.getCurrent().getAttribute("userid");
		StorageComponentImpl proxy = new StorageComponentImpl();
		try
		{
			Clinician clinician = proxy.getClinician(this.currentresp, id);
			((Textbox) getFellow("pat_respo")).setValue(this.currentresp);
			((Textbox) getFellow("pat_respo_lbl")).setValue(clinician.toString());
		}
		catch (Exception re)
		{
			((Textbox) getFellow("pat_respo_lbl")).setValue("Not available");
		}
	}


	/**
	 * This method appends a carer field to the window. Button will open a modal
	 * dialog to select a carer
	 * 
	 * @see CarerListWindowController
	 */
	protected void addCarerField()
	{
		String carertitle = Labels.getLabel("patients.carer.title");
		String carerlabel = Labels.getLabel("patients.carer.label");

		Grid pgrid = new Grid();
		pgrid.setSclass("grid");
		this.appendColumns(pgrid);

		Rows rows = new Rows();

		this.appendSubFormTitleRow(carertitle, rows);

		Row row = new Row();
		Label lab = new Label();
		lab.setValue(carerlabel);
		row.appendChild(lab);

		Hbox hbox01 = new Hbox();

		Textbox tbox = new Textbox();
		tbox.setId("pat_carname");
		tbox.setReadonly(true);

		Textbox tbox2 = new Textbox();
		tbox2.setId("pat_carid");
		tbox2.setVisible(false);
		tbox2.setReadonly(true);

		tbox.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				createDialog();
			}
		});

		hbox01.appendChild(tbox);
		hbox01.appendChild(tbox2);
		row.appendChild(hbox01);

		rows.appendChild(row);

		pgrid.appendChild(rows);
		this.appendChild(pgrid);
	}


	/**
	 * This method sets the carer values when updating a patient
	 */
	protected void addCarerFieldValues()
	{
		((Textbox) getFellow("pat_carid")).setValue(this.currentcarers.getID());
		((Textbox) getFellow("pat_carname")).setValue(
				this.currentcarers.getPersonData().getName() + ", " + this.currentcarers.getPersonData().getSurname());
	}


	/**
	 * This method adds the social worker, consulter and general practicioner
	 * fields in the form
	 */
	protected void addSocialWorkerConsulterAndGPFields()
	{
		Grid pgrid1 = new Grid();
		Grid pgrid2 = new Grid();
		Grid pgrid3 = new Grid();
		pgrid1.setSclass("grid");
		pgrid2.setSclass("grid");
		pgrid3.setSclass("grid");
		this.appendColumns(pgrid1);
		this.appendColumns(pgrid2);
		this.appendColumns(pgrid3);

		Rows rows1 = new Rows();
		Rows rows2 = new Rows();
		Rows rows3 = new Rows();

		this.appendSubFormTitleRow("#TXT# Social Workrer Info", rows1);
		this.appendSubFormTitleRow("#TXT# Consulter Info", rows2);
		this.appendSubFormTitleRow("#TXT# General Practicioner Info", rows3);

		ArrayList<SimpleFieldData> rowsA = new ArrayList<SimpleFieldData>();
		rowsA.add(new SimpleFieldData("#TXT# Name", "pat_swname"));
		rowsA.add(new SimpleFieldData("#TXT# E-mail", "pat_swmail"));
		rowsA.add(new SimpleFieldData("#TXT# Phone", "pat_swphone"));

		ArrayList<SimpleFieldData> rowsB = new ArrayList<SimpleFieldData>();
		rowsB.add(new SimpleFieldData("#TXT# Name", "pat_consname"));
		rowsB.add(new SimpleFieldData("#TXT# E-mail", "pat_consmail"));
		rowsB.add(new SimpleFieldData("#TXT# Phone", "pat_consphone"));

		ArrayList<SimpleFieldData> rowsC = new ArrayList<SimpleFieldData>();
		rowsC.add(new SimpleFieldData("#TXT# Name", "pat_gpname"));
		rowsC.add(new SimpleFieldData("#TXT# E-mail", "pat_gpmail"));
		rowsC.add(new SimpleFieldData("#TXT# Phone", "pat_gpphone"));

		this.appendTextboxFields(rowsA, rows1);
		this.appendTextboxFields(rowsB, rows2);
		this.appendTextboxFields(rowsC, rows3);

		pgrid1.appendChild(rows1);
		pgrid2.appendChild(rows2);
		pgrid3.appendChild(rows3);
		this.appendChild(pgrid1);
		this.appendChild(pgrid2);
		this.appendChild(pgrid3);
	}


	/**
	 * This method adds the social worker, consulter and general practicioner
	 * values when updating a patient
	 */
	protected void addSocialWorkerConsulterAndGPFieldsValues()
	{
		((Textbox) getFellow("pat_swname")).setValue(this.currentsocialworker.getName());
		((Textbox) getFellow("pat_swmail")).setValue(this.currentsocialworker.getEmail());
		((Textbox) getFellow("pat_swphone")).setValue(this.currentsocialworker.getPhone());
		
		((Textbox) getFellow("pat_consname")).setValue(this.currentconsulter.getName());
		((Textbox) getFellow("pat_consmail")).setValue(this.currentconsulter.getEmail());
		((Textbox) getFellow("pat_consphone")).setValue(this.currentconsulter.getPhone());
		
		((Textbox) getFellow("pat_gpname")).setValue(this.currentgeneralpracticioner.getName());
		((Textbox) getFellow("pat_gpmail")).setValue(this.currentgeneralpracticioner.getEmail());
		((Textbox) getFellow("pat_gpphone")).setValue(this.currentgeneralpracticioner.getPhone());
	}


	/**
	 * Method to retrieve a SocialWorker from the form
	 * 
	 * @return SocialWorker defined on the patient form
	 */
	protected SocialWorker getSocialWorkerData()
	{
		String name = ((Textbox) getFellow("pat_swname")).getValue();
		String mail = ((Textbox) getFellow("pat_swmail")).getValue();
		String phone = ((Textbox) getFellow("pat_swphone")).getValue();
		return new SocialWorker(name, phone, mail);
	}


	/**
	 * Method to retrieve the consulter from the form
	 * 
	 * @return Consulter defined on the patient form
	 */
	protected Consulter getConsulterData()
	{
		String name = ((Textbox) getFellow("pat_consname")).getValue();
		String mail = ((Textbox) getFellow("pat_consmail")).getValue();
		String phone = ((Textbox) getFellow("pat_consphone")).getValue();
		return new Consulter(name, phone, mail);
	}


	/**
	 * Method to retrieve a GeneralPracticioner from the form
	 * 
	 * @return GeneralPracticioner defined on the patient form
	 */
	protected GeneralPractitioner getGeneralPracticionerData()
	{
		String name = ((Textbox) getFellow("pat_gpname")).getValue();
		String mail = ((Textbox) getFellow("pat_gpmail")).getValue();
		String phone = ((Textbox) getFellow("pat_gpphone")).getValue();
		return new GeneralPractitioner(name, phone, mail);
	}


	/**
	 * Method in charge of creating update button for patients updates
	 * operations
	 * 
	 * @return Button to be added to the form
	 */
	public Button createUpdateButton()
	{
		Button btn = new Button();
		String text = Labels.getLabel("patients.update.title");
		btn.setLabel(text);
		btn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				sendPatient(false);
			}
		});

		return btn;
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
			
			if (((Textbox) getFellow("pat_respo")).getValue() == null || ((Textbox) getFellow("pat_respo")).getValue().trim().equals(""))
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("#TXT# You must select some clinician");
				getFellow("internalformerror").setVisible(true);
				
				return false;
			}
			else if (((Textbox) getFellow("pat_carid")).getValue() == null || ((Textbox) getFellow("pat_carid")).getValue().trim().equals(""))
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("#TXT# You must select some carer");
				getFellow("internalformerror").setVisible(true);
				
				return false;
			}
			
			return true;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			SystemDictionary.webguiLog("ERROR", e.getMessage());
			
			String errorMsj = e.getMessage();
			try {
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue(errorMsj);
				getFellow("internalformerror").setVisible(true);
				((Label) win.getFellow("errorlbl")).setFocus(true);
				return false;
			} 
			catch (Exception ex) {}
			
			return false;
		}	
	}
	
	
}
