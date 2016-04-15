package eu.ehealth.controllers;

import java.util.ArrayList;
import java.util.Date;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Datebox;
import eu.ehealth.SystemDictionary;
import eu.ehealth.ws_client.xsd.SocioDemographicData;
import eu.ehealth.ws_client.xsd.SystemParameter;


/**
 * It is almost an "abstract" class to generalize some Patients and Carers
 * operations
 * 
 * @author Xavi Sarda (Atos Origin)
 */
public class SDFormControllerWindow extends AladdinFormControllerWindow
{


	private static final long serialVersionUID = 3010045889522110745L;
	protected SocioDemographicData currentsd = null;


	/**
	 * Default constructor
	 */
	public SDFormControllerWindow() {}


	/**
	 * Protected method to retrieve information from the form to generate a new
	 * SocioDemographicData object
	 * 
	 * @return SocioDemographic Object from the patient form
	 */
	protected SocioDemographicData getSocioDemographicData()
	{
		// Getting fields from Social Demographic data
		Date birthdayStr = ((Datebox) getFellow("pat_birthday")).getValue();

		Integer childrenStr = ((Intbox) getFellow("pat_children")).getValue();
		Short children = childrenStr.shortValue();

		Radiogroup genderRadio = (Radiogroup) getFellow("pat_gender");
		Radio radiosel = genderRadio.getSelectedItem();
		String code = radiosel.getValue();
		String value = radiosel.getLabel();
		
		// Creating gender object to save
		SystemParameter gender = new SystemParameter(code, value);

		Listbox maritallist = (Listbox) getFellow("pat_marital");
		Listitem maritalitem = maritallist.getSelectedItem();
		String mcode = maritalitem.getValue().toString();
		String mvalue = maritalitem.getLabel();
		// Creating marital status object to save
		SystemParameter marital = new SystemParameter(mcode, mvalue);

		Listbox livinglist = (Listbox) getFellow("pat_living");
		Listitem livingitem = livinglist.getSelectedItem();
		String lcode = livingitem.getValue().toString();
		String lvalue = livingitem.getLabel();
		
		// Creating living object to save
		SystemParameter living = new SystemParameter(lcode, lvalue);

		// Creating socio-demographic object to store
		SocioDemographicData sdData = null;
		try
		{
			Integer heightStr = ((Intbox) getFellow("pat_height")).getValue();
			SystemParameter height = new SystemParameter(heightStr.toString(), "Height");
			
			sdData = new SocioDemographicData(gender, marital, children, living, birthdayStr, height);
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("ERROR", e.getMessage());
		}
		
		return sdData;
	}


	/**
	 * This method adds all the SocioDemographic input fields into the form
	 */
	protected void addSocioDemographicDataFields()
	{
		String personal = Labels.getLabel("patients.form.personal");
		String gender = Labels.getLabel("patients.form.sd.gender");
		String marital = Labels.getLabel("patients.form.sd.marital");
		String children = Labels.getLabel("patients.form.sd.children");
		String living = Labels.getLabel("patients.form.sd.living");
		String single = Labels.getLabel("patients.form.sd.marital.single");
		String married = Labels.getLabel("patients.form.sd.marital.married");
		String widow = Labels.getLabel("patients.form.sd.marital.widow");
		String divorced = Labels.getLabel("patients.form.sd.marital.divorced");
		String male = Labels.getLabel("patients.form.male");
		String female = Labels.getLabel("patients.form.female");
		String height = "Height (cm)";

		String alone = SystemDictionary.LIVING_ALONE_LBL;
		String sons = SystemDictionary.LIVING_SONDAUGHTER_LBL;
		String partner = SystemDictionary.LIVING_PARTNER_LBL;
		String carer = SystemDictionary.LIVING_PARTER_SONDAUGHTER_LBL;
		String res = SystemDictionary.LIVING_PARTER_SONDAUGHTER_SDLAW_LBL;
		String health = SystemDictionary.LIVING_PARTER_SONDAUGHTER_SDLAW_GRANDSON_LBL;

		ArrayList<SimpleFieldData> rowsD = new ArrayList<SimpleFieldData>();
		rowsD.add(new SimpleFieldData("#TXT# Birthday (dd/MM/yyyy)", "pat_birthday", "no empty"));

		ArrayList<SimpleFieldData> rowsA = new ArrayList<SimpleFieldData>();
		rowsA.add(new SimpleFieldData(children, "pat_children", "no empty"));
		
		ArrayList<SimpleFieldData> rowsA2 = new ArrayList<SimpleFieldData>();
		rowsA2.add(new SimpleFieldData(height, "pat_height", "no empty"));

		ArrayList<SimpleFieldData> genderlist = new ArrayList<SimpleFieldData>();
		genderlist.add(new SimpleFieldData(male, "1"));
		genderlist.add(new SimpleFieldData(female, "2"));

		ArrayList<SimpleFieldData> maritallist = new ArrayList<SimpleFieldData>();
		maritallist.add(new SimpleFieldData(widow, "1"));
		maritallist.add(new SimpleFieldData(married, "2"));
		maritallist.add(new SimpleFieldData(single, "3"));
		maritallist.add(new SimpleFieldData(divorced, "4"));

		ArrayList<SimpleFieldData> livinglist = new ArrayList<SimpleFieldData>();
		livinglist.add(new SimpleFieldData(Labels.getLabel(alone), SystemDictionary.LIVING_ALONE));
		livinglist.add(new SimpleFieldData(Labels.getLabel(sons), SystemDictionary.LIVING_SONDAUGHTER));
		livinglist.add(new SimpleFieldData(Labels.getLabel(partner), SystemDictionary.LIVING_PARTNER));
		livinglist.add(new SimpleFieldData(Labels.getLabel(carer), SystemDictionary.LIVING_PARTER_SONDAUGHTER));
		livinglist.add(new SimpleFieldData(Labels.getLabel(res), SystemDictionary.LIVING_PARTER_SONDAUGHTER_SDLAW));
		livinglist.add(new SimpleFieldData(Labels.getLabel(health), SystemDictionary.LIVING_PARTER_SONDAUGHTER_SDLAW_GRANDSON));

		Grid pgrid = new Grid();
		pgrid.setSclass("grid");
		this.appendColumns(pgrid);

		Rows rows = new Rows();
		this.appendSubFormTitleRow(personal, rows);
		this.appendDateboxFields(rowsD, rows);
		this.appendIntboxFields(rowsA, rows);
		this.appendIntboxFields(rowsA2, rows);
		this.appendRadioElement(genderlist, rows, gender, "pat_gender");
		this.appendListboxElement(maritallist, rows, marital, "pat_marital");
		this.appendListboxElement(livinglist, rows, living, "pat_living");

		pgrid.appendChild(rows);
		this.appendChild(pgrid);
	}


	/**
	 * This method sets all the SocioDemographic field values when updating
	 * Patients or Carers
	 */
	protected void addSocioDemographicDataFieldsValue()
	{
		try
		{
			((Datebox) this.getFellow("pat_birthday")).setValue(this.currentsd.getBirthdayDate());
			((Intbox) this.getFellow("pat_children")).setValue(new Integer(this.currentsd.getChildren().toString()));
			((Radiogroup) this.getFellow("pat_gender")).setSelectedIndex(Integer.parseInt(this.currentsd.getGender().getCode()) - 1);
			((Listbox) this.getFellow("pat_marital")).setSelectedIndex(Integer.parseInt(this.currentsd.getMaritalStatus().getCode()) - 1);
			((Listbox) this.getFellow("pat_living")).setSelectedIndex(Integer.parseInt(this.currentsd.getLivingWith().getCode()));
			((Intbox) this.getFellow("pat_height")).setValue(new Integer(this.currentsd.getHeight().getCode()));
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("ERROR", e.getMessage());
		}
	}
	

}
