package eu.ehealth.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import eu.ehealth.Globals;
import eu.ehealth.SystemDictionary;
import eu.ehealth.Globals.CommunicationType;
import eu.ehealth.utilities.Validator;
import eu.ehealth.ws_client.xsd.Address;
import eu.ehealth.ws_client.xsd.AddressList;
import eu.ehealth.ws_client.xsd.Communication;
import eu.ehealth.ws_client.xsd.CommunicationList;
import eu.ehealth.ws_client.xsd.Identifier;
import eu.ehealth.ws_client.xsd.IdentifierList;
import eu.ehealth.ws_client.xsd.PersonData;
import eu.ehealth.ws_client.xsd.SystemParameter;
import eu.ehealth.ws_client.xsd.User;


/**
 * General profile form controller for Aladdin.
 * 
 * @author Xavi Sarda (Atos Origin)
 */
public class AladdinFormControllerWindow extends Window
{


	private static final long serialVersionUID = 2661475164027020887L;
	protected String currentid = null;
	protected PersonData currentdata = null;
	protected Communication[] communications = null;
	protected Address[] addresses = null;
	protected Window popupaddresses = null;
	protected Window popupcommunications = null;
	protected boolean detailsmode = false;
	// new user (save) - existing user (update)
	protected boolean isNewUser;
	// REGULAR EXPRESSION : letters 'a-zA-Z' and at least one digit 0-9. Length 8-20
	private final String PSSWD_REG_EXPRESSION = "/^(?=.*\\d)(?=.*[a-zA-Z]).{8,20}$/"; 


	/**
	 * Default constructor
	 */
	public AladdinFormControllerWindow() { }


	/**
	 * Override this method to build a concrete form
	 */
	public void buildForm() { }


	/**
	 * Private method which retrieves information to fulfill PersonData fields
	 * 
	 * @return PersonData object to be included on Patient information
	 */
	protected PersonData getPersonData()
	{
		String name = ((Textbox) getFellow("pat_name")).getValue();
		String surname = ((Textbox) getFellow("pat_sname")).getValue();

		IdentifierList idlist = new IdentifierList();
		idlist.getIdentifier().add(this.getIdentifierData());

		Address addresslist[] = this.getNewAddressData();
		if (this.addresses == null)
		{
			addresslist = new Address[0];
		}
		AddressList adrlist = new AddressList(addresslist);

		Communication comlist[] = this.getNewCommunicationData();
		if (this.communications == null)
		{
			communications = new Communication[0];
		}
		CommunicationList comilist = new CommunicationList(comlist);

		PersonData pdData = new PersonData(surname, name, idlist, adrlist, comilist);

		return pdData;
	}


	/**
	 * 
	 * @return
	 */
	protected String getUsername()
	{
		return ((Textbox) getFellow("pat_uname")).getValue();
	}


	/**
	 * Private method which retrieves and generate required information to be
	 * stored on the identifier Object
	 * 
	 * @return Identifier object (it is not an array at all)
	 */
	protected Identifier getIdentifierData()
	{
		// TODO review auto-generated data
		String type = "patient";
		String numbre = "1000";
		String issueAuthority = "authority";
		Date issueDate = new Date(System.currentTimeMillis());

		Identifier ident = new Identifier(type, numbre, issueDate, issueAuthority);
		return ident;
	}


	/**
	 * Private method to retrieve data to store a new Address object
	 * 
	 * @return Address object
	 */
	protected Address[] getNewAddressData()
	{
		return this.addresses;
	}


	/**
	 * This method allows form windows to add addresses to the current list. The
	 * only parameter required is the Window that contains the needed Textboxes.
	 * 
	 * @param windowAddress Window where the address will be added
	 */
	public void addAddressToList(Window windowAddress)
	{
		String street = ((Textbox) windowAddress.getFellow("pat_street")).getValue();
		String streetno = ((Textbox) windowAddress.getFellow("pat_streetno")).getValue();
		String city = ((Textbox) windowAddress.getFellow("pat_city")).getValue();
		String county = ((Textbox) windowAddress.getFellow("pat_county")).getValue();
		String country = ((Textbox) windowAddress.getFellow("pat_country")).getValue();
		String zipcode = ((Textbox) windowAddress.getFellow("pat_zipcode")).getValue();
		String notes = ((Textbox) windowAddress.getFellow("pat_notes")).getValue();

		Checkbox primarybox = (Checkbox) windowAddress.getFellow("pat_primadd");
		boolean isPrimary = primarybox.isChecked();

		Address newAddress = new Address(street, streetno, city, county, country, zipcode, notes, isPrimary);
		if (this.addresses == null || this.addresses.length == 0)
		{
			this.addresses = new Address[1];
			this.addresses[0] = newAddress;
		}
		else
		{
			Address[] helperAddress = new Address[this.addresses.length + 1];
			for (int i = 0; i < this.addresses.length; i++)
			{
				helperAddress[i] = this.addresses[i];
			}
			helperAddress[this.addresses.length] = newAddress;
			this.addresses = helperAddress;
		}
		this.insertAddressRow(newAddress);
		this.removeChild(this.popupaddresses);
	}


	/**
	 * This method inserts an address to the main window
	 * 
	 * @param toInsert Address to be added on the Addresses section
	 */
	protected void insertAddressRow(Address toInsert)
	{
		String removeadd = Labels.getLabel("common.address.remove");
		Rows rows = (Rows) getFellow("addressgridrows");
		Row newrow = new Row();

		Label town = new Label(toInsert.getCity() + "(" + toInsert.getCountry() + ")");
		Hbox hbox = new Hbox();
		String thereststring = toInsert.getStreet() + " " + toInsert.getStreetNo();
		if (toInsert.isIsPrimary())
		{
			thereststring += "(*) ";
		}
		else
		{
			thereststring += " ";
		}
		Label therest = new Label(thereststring);
		hbox.appendChild(therest);
		if (!this.detailsmode)
		{
			Label remove = new Label(removeadd);
			remove.setSclass("link");
			remove.addEventListener("onClick", new RemoveAddressListener(newrow, toInsert));
			hbox.appendChild(remove);
		}
		newrow.appendChild(town);
		newrow.appendChild(hbox);
		rows.appendChild(newrow);
	}


	/**
	 * This method removes an Address from the UI and the profile data model
	 * before saving it.
	 * 
	 * @param row Row object to be deleted, it is used to identify the UI
	 *            element to be deleted
	 * @param address Address to be deleted to identify which address should be
	 *            deleted from the model
	 */
	public void removeAddress(Row rw, Address as)
	{
		Rows rows = (Rows) this.getFellow("addressgridrows");
		rows.removeChild(rw);
		Address[] sustitute = new Address[this.addresses.length - 1];
		int j = 0;
		for (int i = 0; i < this.addresses.length - 1; i++)
		{
			if (this.addresses[j].equals(as))
			{
				j++;
			}
			sustitute[i] = this.addresses[j];
			j++;
		}
		this.addresses = sustitute;
	}


	/**
	 * This method adds a Communication from a Window that contains all the
	 * required information
	 * 
	 * @param windowCom Window where all the information should be contained
	 */
	public void addCommunicationToList(Window windowCom)
	{
		String type = (String) ((Listbox) windowCom.getFellow("pat_comtype")).getSelectedItem().getValue();
		String value = ((Textbox) windowCom.getFellow("pat_comval")).getValue();
		String notes = ((Textbox) windowCom.getFellow("pat_comnote")).getValue();
		
		// check email / phone / other value
		if (type.equals("phone")) {
			if (!Validator.validatePhone(value)) {
				Window win = (Window) windowCom.getFellow("internalcommformerror");
				((Label) win.getFellow("errorlbl")).setValue("Invalid phone value");
				windowCom.getFellow("internalcommformerror").setVisible(true);
				
				return;
			}
		}
		else if (type.equals("email")) {
			if (!Validator.validateEmail(value)) {
				Window win = (Window) windowCom.getFellow("internalcommformerror");
				((Label) win.getFellow("errorlbl")).setValue("Invalid email value");
				windowCom.getFellow("internalcommformerror").setVisible(true);
				
				return;
			}
		}

		Checkbox primarybox = (Checkbox) windowCom.getFellow("pat_comprim");
		boolean isPrimary = primarybox.isChecked();

		Communication communication = new Communication(type, value, notes, isPrimary);
		if (this.communications == null || this.communications.length == 0)
		{
			this.communications = new Communication[1];
			this.communications[0] = communication;
		}
		else
		{
			Communication[] helpercom = new Communication[this.communications.length + 1];
			for (int i = 0; i < this.communications.length; i++)
			{
				helpercom[i] = this.communications[i];
			}
			helpercom[this.communications.length] = communication;
			this.communications = helpercom;
		}
		this.insertComRow(communication);
		this.removeChild(this.popupcommunications);
	}


	/**
	 * Helper method which is in charge of adding Communications objects to the
	 * UI.
	 * 
	 * @param com Communication object to be added to the UI
	 */
	protected void insertComRow(Communication com)
	{
		String removecom = Labels.getLabel("common.communications.remove");
		Rows rows = (Rows) getFellow("comgridrows");
		Row newrow = new Row();

		Label town = new Label("");
		Hbox hbox = new Hbox();
		String thereststring = "(" + com.getType() + ") " + com.getValue();
		if (com.isIsPrimary())
		{
			thereststring += "* ";
		}
		else
		{
			thereststring += " ";
		}
		Label therest = new Label(thereststring);
		hbox.appendChild(therest);
		if (!this.detailsmode)
		{
			Label remove = new Label(removecom);
			remove.setSclass("link");
			remove.addEventListener("onClick", new RemoveComListener(newrow, com));
			hbox.appendChild(remove);
		}
		newrow.appendChild(town);
		newrow.appendChild(hbox);
		rows.appendChild(newrow);
	}


	/**
	 * This method removes a Communications from the UI and the data model.
	 * 
	 * @param row Row object to be removed from the UI
	 * @param comnunication Communication object to be deleted from the data
	 *            model
	 */
	public void removeCommunication(Row rw, Communication com)
	{
		Rows rows = (Rows) this.getFellow("comgridrows");
		rows.removeChild(rw);
		Communication[] sustitute = new Communication[this.communications.length - 1];
		int j = 0;
		for (int i = 0; i < this.communications.length - 1; i++)
		{
			if (this.communications[j].equals(com))
			{
				j++;
			}
			sustitute[i] = this.communications[j];
			j++;
		}
		this.communications = sustitute;
	}


	/**
	 * Private method to generate a new Communication object
	 * 
	 * @return a Communication object, non an Array
	 */
	protected Communication[] getNewCommunicationData()
	{
		return this.communications;
	}


	/**
	 * 
	 */
	protected void addErrorBox()
	{
		Window errorwin = new Window();
		errorwin.setId("internalformerror");
		errorwin.setWidth("50%");
		errorwin.setSclass("mainerror");
		errorwin.setBorder("none");
		errorwin.setClosable(true);
		errorwin.setVisible(false);

		Label errorlbl = new Label();
		errorlbl.setId("errorlbl");

		Label errorclose = new Label();
		errorclose.setValue(" Close");
		errorclose.setSclass("link");
		errorclose.addEventListener("onClick", new ErrorWindowListener(errorwin));

		errorwin.appendChild(errorlbl);
		errorwin.appendChild(errorclose);

		this.appendChild(errorwin);
	}


	/**
	 * Protected method that allows us to add input elements to be able to add
	 * Person objects
	 */
	protected void addPersonFields(boolean newUser)
	{
		String name = Labels.getLabel("patients.form.name");
		String surname = Labels.getLabel("patients.form.surname");

		ArrayList<SimpleFieldData> rowsA = new ArrayList<SimpleFieldData>();
		Grid pgrid = new Grid();
		Rows rows = new Rows();
		pgrid.setSclass("grid");
		
		if (newUser) 
		{
			rowsA.add(new SimpleFieldData(name, "pat_name", "no empty", "", "*")); 
			rowsA.add(new SimpleFieldData(surname, "pat_sname", "no empty", "", "*")); 
			rowsA.add(new SimpleFieldData("#TXT# Username", "pat_uname", "no empty", "", "*"));
			
			rowsA.add(new SimpleFieldData("#TXT# Password", "pat_pwd", "no empty, " + PSSWD_REG_EXPRESSION, "password", "* (at least one character 'a-zA-Z' and at least one digit 0-9. Length 8-20)"));
			rowsA.add(new SimpleFieldData("#TXT# Repeat password", "pat_pwd2", "no empty, " + PSSWD_REG_EXPRESSION, "password", "*"));
			
			this.appendColumnsFields3cols(pgrid); 
			this.appendTextboxFields3cols(rowsA, rows); 
		}
		else 
		{
			rowsA.add(new SimpleFieldData(name, "pat_name", "no empty")); 
			rowsA.add(new SimpleFieldData(surname, "pat_sname", "no empty")); 
			rowsA.add(new SimpleFieldData("#TXT# Username", "pat_uname", "no empty"));
			
			this.appendColumns(pgrid);
			this.appendTextboxFields(rowsA, rows); 
		}

		pgrid.appendChild(rows);
		this.appendChild(pgrid);
	}


	/**
	 * Protected method that allows us to add input elements to be able to add
	 * Person objects
	 */
	protected void addBirthdayField()
	{
		ArrayList<SimpleFieldData> rowsA = new ArrayList<SimpleFieldData>();
		rowsA.add(new SimpleFieldData("#TXT# Birthdate (dd/MM/yyyy)", "pat_age", "no empty"));

		Grid pgrid = new Grid();
		pgrid.setSclass("grid");
		this.appendColumns(pgrid);

		Rows rows = new Rows();
		this.appendDateboxFields(rowsA, rows);

		pgrid.appendChild(rows);
		this.appendChild(pgrid);
	}


	/**
	 * This method fills some person-related fields on the form. These fields
	 * are the name and the surname of the person.
	 */
	protected void addPersonFieldsValues()
	{
		((Textbox) this.getFellow("pat_name")).setValue(this.currentdata.getName());
		((Textbox) this.getFellow("pat_sname")).setValue(this.currentdata.getSurname());
	}


	/**
	 * This method just adds a new row with the button that shows the new
	 * Address form in a pop up
	 */
	protected void addAddressFields()
	{
		String address = Labels.getLabel("patients.form.address");
		String addaddressstr = Labels.getLabel("common.address.add");
		Grid agrid = new Grid();
		agrid.setSclass("grid");
		this.appendColumns(agrid);

		Rows arows = new Rows();
		arows.setId("addressgridrows");
		this.appendSubFormTitleRow(address, arows);
		if (!this.detailsmode)
		{
			Row buttonrw = new Row();
			Label labelbtn = new Label(" ");
			Button btnaddress = new Button(addaddressstr);
			btnaddress.addEventListener("onClick", new EventListener() {

				public void onEvent(Event arg0) throws Exception
				{
					addAddressWindow();
					popupaddresses.doModal();
				}
			});
			buttonrw.appendChild(labelbtn);
			buttonrw.appendChild(btnaddress);
			buttonrw.setId("createaddress-row");
			arows.appendChild(buttonrw);
		}
		agrid.appendChild(arows);
		this.appendChild(agrid);
	}


	/**
	 * This method is called by the button in the "addAddressFields" button. It
	 * only builds up a window with a form to create new addresses.
	 * 
	 * The resultant window is stored on the "popaddresses" instance attribute.
	 */
	protected void addAddressWindow()
	{
		String address = Labels.getLabel("patients.form.address");
		String street = Labels.getLabel("patients.form.street");
		String streetno = Labels.getLabel("patients.form.streetno");
		String city = Labels.getLabel("patients.form.city");
		String county = Labels.getLabel("patients.form.county");
		String country = Labels.getLabel("patients.form.country");
		String zipcode = Labels.getLabel("patients.form.zip");
		String notes = Labels.getLabel("patients.form.notes");
		String addresssave = Labels.getLabel("common.address.save");

		this.popupaddresses = new Window();
		this.popupaddresses.setTitle(address);
		this.popupaddresses.setClosable(true);
		this.popupaddresses.setVisible(false);
		this.popupaddresses.setWidth("800px");
		this.popupaddresses.setPosition("center, center");
		this.popupaddresses.setBorder("normal");
		this.appendChild(this.popupaddresses);

		ArrayList<SimpleFieldData> rowsA = new ArrayList<SimpleFieldData>();
		rowsA.add(new SimpleFieldData(street, "pat_street"));
		rowsA.add(new SimpleFieldData(streetno, "pat_streetno"));
		rowsA.add(new SimpleFieldData(city, "pat_city"));
		rowsA.add(new SimpleFieldData(zipcode, "pat_zipcode"));
		rowsA.add(new SimpleFieldData(county, "pat_county"));
		rowsA.add(new SimpleFieldData(country, "pat_country"));
		rowsA.add(new SimpleFieldData(notes, "pat_notes"));

		ArrayList<SimpleFieldData> rowsB = new ArrayList<SimpleFieldData>();

		rowsB.add(new SimpleFieldData("use this as the primary means of contact", "pat_primadd"));

		Grid pgrid = new Grid();
		pgrid.setSclass("grid");
		this.appendColumns(pgrid);

		Rows rows = new Rows();

		this.appendSubFormTitleRow(address, rows);
		this.appendTextboxFields(rowsA, rows);
		this.appendCheckboxFields(rowsB, rows);

		Row buttonrw = new Row();
		Label empty = new Label("");
		buttonrw.appendChild(empty);
		Button insertAddress = new Button(addresssave);
		insertAddress.addEventListener("onClick", new AddressInsertListener(this.popupaddresses));
		buttonrw.appendChild(insertAddress);
		rows.appendChild(buttonrw);

		pgrid.appendChild(rows);
		this.popupaddresses.appendChild(pgrid);

		Textbox tboxe = (Textbox) rows.getFellow("pat_notes");
		tboxe.setRows(4);
		tboxe.setWidth("70%");
	}


	/**
	 * This method adds all the addresses related with one profile on the main
	 * grid. They are not in a form way, they are being processed to show them
	 * in a plain text way
	 */
	protected void addAddressFieldsValues()
	{
		if (this.currentdata.getAddressList() != null && this.currentdata.getAddressList().getAddress() != null
				&& this.currentdata.getAddressList().getAddress().size() > 0)
		{
			this.addresses = this.currentdata.getAddressList().getAddress().toArray(
					new Address[this.currentdata.getAddressList().getAddress().size()]);
			if (this.addresses != null)
			{
				for (int i = 0; i < this.addresses.length; i++)
				{
					if (this.addresses[i] != null)
					{
						this.insertAddressRow(this.addresses[i]);
					}
				}
			}
		}
	}


	/**
	 * This method just adds a communication row. There will be a button which
	 * will be in charge of showing the "add communication" pop up
	 */
	protected void addCommunicationFields()
	{
		String com = Labels.getLabel("patients.form.comunications");
		String comaddlbl = Labels.getLabel("common.communications.add");

		Grid agrid = new Grid();
		agrid.setSclass("grid");
		this.appendColumns(agrid);

		Rows arows = new Rows();
		arows.setId("comgridrows");
		this.appendSubFormTitleRow(com, arows);
		if (!this.detailsmode)
		{
			Row buttonrw = new Row();
			Label labelbtn = new Label(" ");
			Button btnaddress = new Button(comaddlbl);
			// This listener will be in charge of showing the popup
			btnaddress.addEventListener("onClick", new EventListener() {
				public void onEvent(Event arg0) throws Exception
				{
					addCommunicationWindow();
					popupcommunications.doModal();
				}
			});
			buttonrw.appendChild(labelbtn);
			buttonrw.appendChild(btnaddress);
			buttonrw.setId("createcommunication-row");
			arows.appendChild(buttonrw);
		}
		agrid.appendChild(arows);
		this.appendChild(agrid);
	}


	/**
	 * This method builds up the "add communication" popup
	 */
	protected void addCommunicationWindow()
	{
		String comtype = Labels.getLabel("patients.form.com.type");
		String comval = Labels.getLabel("patients.form.com.value");
		String com = Labels.getLabel("patients.form.comunications");
		String prim = Labels.getLabel("patients.form.primary");
		String notes = Labels.getLabel("patients.form.notes");
		String savecomm = Labels.getLabel("common.communications.save");
		
		// contact type options
		ArrayList<SimpleFieldData> contact_typelist = new ArrayList<SimpleFieldData>();
		
		CommunicationType[] comTypes = Globals.CommunicationType.values();
		
		if (comTypes != null) 
		{
			for (int i=0, max=comTypes.length; i<max; i++) 
			{
				contact_typelist.add(new SimpleFieldData(comTypes[i].toString(), comTypes[i].toString()));
			}
		}
		else 
		{
			contact_typelist.add(new SimpleFieldData("#TXT# email", "email"));
			contact_typelist.add(new SimpleFieldData("#TXT# phone", "phone"));
		}

		this.popupcommunications = new Window();
		this.popupcommunications.setTitle(com);
		this.popupcommunications.setClosable(true);
		this.popupcommunications.setVisible(false);
		this.popupcommunications.setWidth("800px");
		this.popupcommunications.setPosition("center, center");
		this.popupcommunications.setBorder("normal");
		
		this.popupcommunications.appendChild(addCommunicationErrorBox());
		
		this.appendChild(this.popupcommunications);

		ArrayList<SimpleFieldData> rowsA = new ArrayList<SimpleFieldData>();
		
		rowsA.add(new SimpleFieldData(comval, "pat_comval"));
		rowsA.add(new SimpleFieldData(notes, "pat_comnote"));

		ArrayList<SimpleFieldData> rowsB = new ArrayList<SimpleFieldData>();
		rowsB.add(new SimpleFieldData(prim, "pat_comprim"));

		Grid pgrid = new Grid();
		pgrid.setSclass("grid");
		this.appendColumns(pgrid);

		Rows rows = new Rows();
		this.appendSubFormTitleRow(com, rows);
		this.appendListboxElement(contact_typelist, rows, comtype, "pat_comtype");
		this.appendTextboxFields(rowsA, rows);
		this.appendCheckboxFields(rowsB, rows);

		Row buttonrw = new Row();
		Label empty = new Label("");
		buttonrw.appendChild(empty);
		Button insertCom = new Button(savecomm);
		insertCom.addEventListener("onClick", new CommunicationInsertListener(this.popupcommunications));
		buttonrw.appendChild(insertCom);
		rows.appendChild(buttonrw);

		pgrid.appendChild(rows);
		this.popupcommunications.appendChild(pgrid);

		Textbox tboxe = (Textbox) rows.getFellow("pat_comnote");
		tboxe.setRows(4);
		tboxe.setWidth("70%");
	}
	
	
	protected Window addCommunicationErrorBox()
	{
		Window errorwin = new Window();
		errorwin.setId("internalcommformerror");
		errorwin.setWidth("50%");
		errorwin.setSclass("mainerror");
		errorwin.setBorder("none");
		errorwin.setClosable(true);
		errorwin.setVisible(false);

		Label errorlbl = new Label();
		errorlbl.setId("errorlbl");
		
		errorwin.appendChild(errorlbl);

		return errorwin;
	}


	/**
	 * 
	 */
	protected void addCommunicationFieldsValues()
	{
		if (this.currentdata.getCommunicationList() != null && this.currentdata.getCommunicationList().getCommunication() != null
				&& this.currentdata.getCommunicationList().getCommunication().size() > 0)
		{
			this.communications = this.currentdata.getCommunicationList().getCommunication().toArray(
					new Communication[this.currentdata.getCommunicationList().getCommunication().size()]);
			if (this.communications != null)
			{
				SystemDictionary.webguiLog("DEBUG", "Communications LENGTH: "
						+ this.communications.length);
				for (int i = 0; i < this.communications.length; i++)
				{
					if (this.communications[i] != null)
					{
						this.insertComRow(this.communications[i]);
					}
				}
			}
		}
	}


	/**
	 * This method appends the "Columns" element to common grids for person
	 * forms
	 * 
	 * @param grid Where columns are going to be attached
	 */
	protected void appendColumns(Grid grid)
	{
		Columns cols = new Columns();

		Column col1 = new Column();
		col1.setWidth("250px");
		col1.setAlign("right");
		cols.appendChild(col1);

		Column col2 = new Column();
		col2.setAlign("left");
		cols.appendChild(col2);

		grid.appendChild(cols);
	}


	/**
	 * Protected method to ease appending titles on sub-form grids.
	 * 
	 * @param title Title of the sub-form
	 * @param rows Rows element where subtitle will be added
	 */
	@SuppressWarnings("deprecation")
	protected void appendSubFormTitleRow(String title, Rows rows)
	{
		Row row0 = new Row();
		row0.setSpans("2");
		row0.setSclass("center");
		Label lab0 = new Label();
		lab0.setValue(title);
		lab0.setSclass("strong");
		row0.appendChild(lab0);
		rows.appendChild(row0);
	}


	/**
	 * Protected method to add several Textbox fields from a TextboxData
	 * ArrayList
	 * 
	 * @param ArrayList<TextboxData> list
	 * @param Rows rows
	 */
	protected void appendTextboxFields(ArrayList<SimpleFieldData> list, Rows rows)
	{
		Iterator<SimpleFieldData> it = list.iterator();
		while (it.hasNext())
		{
			SimpleFieldData elem = it.next();
			Row rowe = new Row();
			Label labe = new Label();
			labe.setValue(elem.getLabel());
			rowe.appendChild(labe);

			Textbox tboxe = new Textbox();
			tboxe.setId(elem.getId());
			if (elem.getConstraints() != null)
			{
				tboxe.setConstraint(elem.getConstraints());
			}
			if (elem.getType() != null) 
			{
				tboxe.setType(elem.getType());
			}
			if (detailsmode)
			{
				tboxe.setReadonly(true);
			}
			rowe.appendChild(tboxe);		
			rows.appendChild(rowe);
		}
	}
	
	
	/**
	 * 
	 * @param grid
	 */
	protected void appendColumnsFields3cols(Grid grid)
	{
		Columns cols = new Columns();

		Column col1 = new Column();
		col1.setWidth("250px");
		col1.setAlign("right");
		cols.appendChild(col1);

		Column col2 = new Column();
		col2.setWidth("150px");
		col2.setAlign("center");
		cols.appendChild(col2);
		
		Column col3 = new Column();
		col3.setAlign("left");
		cols.appendChild(col3);

		grid.appendChild(cols);
	}
	
	
	/**
	 * 
	 * @param list
	 * @param rows
	 */
	protected void appendTextboxFields3cols(ArrayList<SimpleFieldData> list, Rows rows)
	{
		Iterator<SimpleFieldData> it = list.iterator();
		while (it.hasNext())
		{
			SimpleFieldData elem = it.next();
			Row rowe = new Row();
			
			// label
			Label labe = new Label();
			labe.setValue(elem.getLabel());
			rowe.appendChild(labe);

			// textbox
			Textbox tboxe = new Textbox();
			tboxe.setId(elem.getId());
			if ( (elem.getConstraints() != null) && (elem.getConstraints().length() > 0))
			{
				tboxe.setConstraint(elem.getConstraints());
			}
			if ( (elem.getType() != null) && (elem.getType().length() > 0) )
			{
				tboxe.setType(elem.getType());
			}
			if (detailsmode)
			{
				tboxe.setReadonly(true);
			}
			tboxe.setWidth("95%");
			rowe.appendChild(tboxe);
			
			// label
			Label labelInfo = new Label();
			if (elem.getLabelInfo() != null) 
			{
				labelInfo.setValue(elem.getLabelInfo());
			}
			else 
			{
				labelInfo.setValue("");
			}
			labelInfo.setStyle("color:red; font-size:8pt; font-style: italic;");
			rowe.appendChild(labelInfo);
			
			rows.appendChild(rowe);
		}
	}


	/**
	 * New method to make the addition of several integer fields easier
	 * 
	 * @param list SimpleFieldData arraylist with the fields to be added
	 * @param rows Rows component where the fields will be added
	 */
	protected void appendIntboxFields(ArrayList<SimpleFieldData> list, Rows rows)
	{
		Iterator<SimpleFieldData> it = list.iterator();
		while (it.hasNext())
		{
			SimpleFieldData elem = it.next();
			Row rowe = new Row();
			Label labe = new Label();
			labe.setValue(elem.getLabel());
			rowe.appendChild(labe);

			Intbox tboxe = new Intbox();
			tboxe.setId(elem.getId());
			if (elem.getConstraints() != null)
			{
				tboxe.setConstraint(elem.getConstraints());
			}
			if (detailsmode)
			{
				tboxe.setReadonly(true);
			}
			rowe.appendChild(tboxe);
			rows.appendChild(rowe);
		}
	}


	/**
	 * New method to make the addition of several integer fields easier
	 * 
	 * @param list SimpleFieldData arraylist with the fields to be added
	 * @param rows Rows component where the fields will be added
	 */
	protected void appendDateboxFields(ArrayList<SimpleFieldData> list, Rows rows)
	{
		Iterator<SimpleFieldData> it = list.iterator();
		while (it.hasNext())
		{
			SimpleFieldData elem = it.next();
			Row rowe = new Row();
			Label labe = new Label();
			labe.setValue(elem.getLabel());
			rowe.appendChild(labe);

			Datebox tboxe = new Datebox();
			tboxe.setId(elem.getId());
			tboxe.setFormat("dd/MM/yyyy");

			if (elem.getConstraints() != null)
			{
				tboxe.setConstraint(elem.getConstraints());
			}
			if (detailsmode)
			{
				tboxe.setReadonly(true);
			}
			rowe.appendChild(tboxe);
			rows.appendChild(rowe);
		}
	}


	/**
	 * This method allows the developer to insert several Checkbox fields
	 * 
	 * @param list SimpleDataField arraylist which contains the checkbox values
	 * @param rows Rows component where the checkbox will be added
	 */
	protected void appendCheckboxFields(ArrayList<SimpleFieldData> list, Rows rows)
	{
		Iterator<SimpleFieldData> it = list.iterator();
		while (it.hasNext())
		{
			SimpleFieldData elem = it.next();
			Row rowe = new Row();
			Label labe = new Label();
			labe.setValue(elem.getLabel());
			rowe.appendChild(labe);

			Checkbox cboxe = new Checkbox();
			cboxe.setId(elem.getId());
			rowe.appendChild(cboxe);
			rows.appendChild(rowe);
		}
	}


	/**
	 * This method allows the developer to insert ONE radigroup
	 * 
	 * @param list SimpleDataFields arraylist which contains the different
	 *            radiobutton values
	 * @param rows Rows element where the radiogoup will be added
	 * @param label The string that will shown as title of the radigroup
	 * @param id Id of the radiogroup
	 */
	protected void appendRadioElement(ArrayList<SimpleFieldData> list, Rows rows, String label, String id)
	{
		Row rowe = new Row();
		Label labe = new Label();
		labe.setValue(label);
		rowe.appendChild(labe);

		Radiogroup rgroup = new Radiogroup();
		rgroup.setId(id);
		Iterator<SimpleFieldData> it = list.iterator();
		while (it.hasNext())
		{
			SimpleFieldData elem = it.next();
			Radio rad = new Radio();
			rad.setLabel(elem.getLabel());
			rad.setValue(elem.getId());
			if (this.detailsmode)
			{
				rad.setDisabled(true);
			}
			rgroup.appendChild(rad);
		}
		rgroup.setSelectedIndex(0);
		rowe.appendChild(rgroup);
		rows.appendChild(rowe);
	}


	/**
	 * This method allows the developer to insert ONE listbox (select)
	 * 
	 * @param list SimpleDataFields arraylist which contains the different
	 *            listitem values
	 * @param rows Rows element where the listitem will be added
	 * @param label The string that will shown as title of the listbox
	 * @param id Id of the listbox
	 */
	protected void appendListboxElement(ArrayList<SimpleFieldData> list, Rows rows, String label, String id)
	{
		Row rowe = new Row();
		Label labe = new Label();
		labe.setValue(label);
		rowe.appendChild(labe);

		Listbox lbox = new Listbox();
		lbox.setId(id);
		lbox.setMold("select");
		Iterator<SimpleFieldData> it = list.iterator();
		while (it.hasNext())
		{
			SimpleFieldData elem = it.next();
			Listitem li = new Listitem();
			li.setLabel(elem.getLabel());
			li.setValue(elem.getId());
			lbox.appendChild(li);
		}
		lbox.setSelectedIndex(0);
		if (this.detailsmode)
		{
			lbox.setDisabled(true);
		}
		rowe.appendChild(lbox);
		rows.appendChild(rowe);
	}
	
	
	/**
	 * 
	 * @param userType
	 * @param personId
	 * @param username
	 * @param password
	 * @return
	 */
	protected User createNewUser(String userType, String personId, String username) 
	{
		try 
		{
			String password = ((Textbox) getFellow("pat_pwd")).getValue();
			User user = new User("", new SystemParameter(userType, ""), personId, username, password);
			return user;
		} 
		catch (Exception ex) 
		{
			return null;
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	protected boolean validateFormCommonFields() {
		try {
			// check password fields
			if (isNewUser) {
				if (!validatePsswd())
					return false;
			}
						
			// addresses, communications
			if (this.addresses == null || this.communications == null || this.addresses.length == 0 || this.communications.length == 0)
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("#TXT# You cannot create a user without any address or any way to communicate with");
				getFellow("internalformerror").setVisible(true);
				
				return false;
			}
			
			// pat_name, pat_sname, pat_uname
			if (((Textbox) getFellow("pat_name")).getValue() == null || ((Textbox) getFellow("pat_name")).getValue().trim().equals(""))
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("#TXT# You must enter a valid name");
				getFellow("internalformerror").setVisible(true);
				
				((Textbox) getFellow("pat_name")).select();
				return false;
			}
			else if (((Textbox) getFellow("pat_sname")).getValue() == null || ((Textbox) getFellow("pat_sname")).getValue().trim().equals(""))
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("#TXT# You must enter a valid surname");
				getFellow("internalformerror").setVisible(true);
				
				((Textbox) getFellow("pat_sname")).select();
				return false;
			}
			else if ( (isNewUser) && 
						(((Textbox) getFellow("pat_uname")).getValue() == null || ((Textbox) getFellow("pat_uname")).getValue().trim().equals("")) )
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("#TXT# You must enter a valid username");
				getFellow("internalformerror").setVisible(true);
				
				((Textbox) getFellow("pat_uname")).select();
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
	
	
	/**
	 * 
	 * @return
	 */
	protected boolean validatePsswd() {
		try {
			if (((Textbox) getFellow("pat_pwd")).getValue() == null || ((Textbox) getFellow("pat_pwd")).getValue().trim().equals(""))
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("#TXT# You must enter a valid password");
				getFellow("internalformerror").setVisible(true);
				
				((Textbox) getFellow("pat_pwd")).select();
				return false;
			}
			else if (((Textbox) getFellow("pat_pwd2")).getValue() == null || ((Textbox) getFellow("pat_pwd2")).getValue().trim().equals(""))
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("#TXT# You must enter a valid password");
				getFellow("internalformerror").setVisible(true);
				
				((Textbox) getFellow("pat_pwd2")).select();
				return false;
			}
			else if (!((Textbox) getFellow("pat_pwd2")).getValue().equals(((Textbox) getFellow("pat_pwd")).getValue())) 
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("#TXT# Password fields must match");
				getFellow("internalformerror").setVisible(true);
				
				((Textbox) getFellow("pat_pwd2")).select();
				return false;
			}
			else if ( ((Textbox) getFellow("pat_pwd")).getValue().length() < 8 || ((Textbox) getFellow("pat_pwd")).getValue().length() > 20 ) 
			{
				Window win = (Window) getFellow("internalformerror");
				((Label) win.getFellow("errorlbl")).setValue("#TXT# Password must have at least 8 characters (max 20). Password must have atleast one numeric and one character.");
				getFellow("internalformerror").setVisible(true);
				
				((Textbox) getFellow("pat_pwd2")).select();
				return false;
			}

			return true;
		}
		catch (Exception e) 
		{
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

	
	///////////////////////////////////////////////////////////////////////////

	/**
	 * 
	 * @author 
	 *
	 */
	protected class SimpleFieldData
	{


		private String label = null;
		private String id = null;
		private String contraints = null;
		private String type = null;
		private String labelInfo = null;


		SimpleFieldData(String label, String id)
		{
			this.label = label;
			this.id = id;
		}


		SimpleFieldData(String label, String id, String constraints)
		{
			this.label = label;
			this.id = id;
			this.contraints = constraints;
		}
		
		
		SimpleFieldData(String label, String id, String constraints, String type, String labelInfo)
		{
			this.label = label;
			this.id = id;
			this.contraints = constraints;
			this.type = type;
			this.labelInfo = labelInfo;
		}


		public String getLabel()
		{
			return this.label;
		}


		public String getId()
		{
			return this.id;
		}


		public String getConstraints()
		{
			return this.contraints;
		}
		
		
		public String getType()
		{
			return this.type;
		}
		
		
		public String getLabelInfo()
		{
			return this.labelInfo;
		}
		
		
	}

	
	/**
	 * 
	 * @author 
	 *
	 */
	protected class AddressInsertListener implements EventListener
	{


		private Window win;


		public AddressInsertListener(Window window)
		{
			this.win = window;
		}


		public void onEvent(Event arg0) throws Exception
		{
			addAddressToList(this.win);
		}
		
		
	}

	
	/**
	 * 
	 * @author 
	 *
	 */
	protected class CommunicationInsertListener implements EventListener
	{


		private Window win;


		public CommunicationInsertListener(Window window)
		{
			this.win = window;
		}


		public void onEvent(Event arg0) throws Exception
		{
			addCommunicationToList(this.win);
		}
		
		
	}

	
	/**
	 * 
	 * @author 
	 *
	 */
	protected class RemoveAddressListener implements EventListener
	{


		private Row row;
		private Address as;


		public RemoveAddressListener(Row rowd, Address addressd)
		{
			this.row = rowd;
			this.as = addressd;
		}


		public void onEvent(Event arg0) throws Exception
		{
			removeAddress(this.row, this.as);
		}
		
		
	}

	
	/**
	 * 
	 * @author 
	 *
	 */
	protected class RemoveComListener implements EventListener
	{


		private Row row;
		private Communication com;


		public RemoveComListener(Row rowd, Communication comd)
		{
			this.row = rowd;
			this.com = comd;
		}


		public void onEvent(Event arg0) throws Exception
		{
			removeCommunication(this.row, this.com);
		}
		
		
	}

	
	/**
	 * 
	 * @author 
	 *
	 */
	public class ErrorWindowListener implements EventListener
	{


		private Window window;


		public ErrorWindowListener(Window win)
		{
			this.window = win;
		}


		public void onEvent(Event arg0) throws Exception
		{
			this.window.setVisible(false);
		}
		

	}
	

}
