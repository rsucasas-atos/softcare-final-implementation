package eu.ehealth.controllers.warnings;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Row;
import eu.ehealth.ErrorDictionary;
import eu.ehealth.SystemDictionary;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.Patient;
import eu.ehealth.ws_client.xsd.SearchCriteria;
import eu.ehealth.ws_client.xsd.SystemParameter;
import eu.ehealth.ws_client.xsd.Warning;


/**
 * 
 * @author 
 *
 */
public class WarningsWindowController extends Window
{


	private static final long serialVersionUID = 8199279504649823907L;
	public WarningsPopupController warningPopup;


	/**
	 * 
	 * @param warningid
	 */
	public void readWarning(String warningid)
	{
		SystemDictionary.webguiLog("DEBUG", "readWarning : " + warningid);
		
		SearchCriteria filter = new SearchCriteria();
		filter.setFieldName("ID");
		filter.setCompareOp(new SystemParameter("3", ""));
		filter.setFieldValue1(warningid);
		
		String usid = (String) Sessions.getCurrent().getAttribute("userid");

		try
		{
			StorageComponentImpl proxy = new StorageComponentImpl();
			Warning[] warning = proxy.getWarningsArr(new SearchCriteria[] { filter }, usid);
			if (warning.length != 1)
			{
				Messagebox.show("#TXT# Error (4) while reading 'warning' data", "#TXT# Read Warning", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			Patient pinfo = warning[0].getPatient();

			warningPopup = (WarningsPopupController) Executions.createComponents("../warnings/form.zul", this, null);
			warningPopup.setWarningid(warningid);
			
			((Textbox) warningPopup.getFellow("wid")).setValue(warning[0].getID());
			((Textbox) warningPopup.getFellow("typefield")).setValue(SystemDictionary.getWarningTypeLabel(warning[0].getTypeOfWarning().getCode()));
			((Textbox) warningPopup.getFellow("datefield")).setValue(((Label) getFellow("date" + warningid)).getValue());
			((Textbox) warningPopup.getFellow("effectfield")).setValue(SystemDictionary.getWarningEffectLabel(warning[0].getEffect().getCode()));
			((Textbox) warningPopup.getFellow("indfield")).setValue(SystemDictionary.getWarningIndicatorLabel(warning[0].getIndicator().getCode()));
			((Textbox) warningPopup.getFellow("riskfield")).setValue(SystemDictionary.getWarningRiskLevel(warning[0].getRiskLevel().getCode()));
			((Textbox) warningPopup.getFellow("justfield")).setValue(warning[0].getJustificationText());
			((Textbox) warningPopup.getFellow("emergencyfield")).setValue(SystemDictionary.getWarningEmergencyLevelLabel(warning[0].getEmergencyLevel().getCode()));
			((Textbox) warningPopup.getFellow("patientfield")).setValue(warning[0].getID());
			
			if (pinfo != null && pinfo.getPersonData() != null)
			{
				((Textbox) warningPopup.getFellow("patientnamefield"))
						.setValue(pinfo.getPersonData().getSurname() + ", " + pinfo.getPersonData().getName());
			}
			else
			{
				((Textbox) warningPopup.getFellow("patientnamefield")).setValue("N/A");
			}
			((Textbox) warningPopup.getFellow("delivfield")).setValue(warning[0].isDelivered() ? Labels.getLabel("common.yes") : Labels.getLabel("common.no"));
			((Row) warningPopup.getFellow("warningid")).setVisible(false);
			
			if (warning[0].isDelivered())
			{
				((Row) warningPopup.getFellow("buttonrow")).setVisible(false);
			}
			warningPopup.setTitle(Labels.getLabel("menu.warnings"));
			warningPopup.setVisible(true);
			warningPopup.doModal();
		}
		catch (Exception e)
		{
			Messagebox.show("#TXT# Error (" + ErrorDictionary.WARNING_RETRIEVE_ERROR + ") while reading 'warning' data", "#TXT# Read Warning", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * @throws InterruptedException
	 */
	public void createPatientsDialog() throws InterruptedException
	{
		PatientListForWarnings respolist = (PatientListForWarnings) Executions.getCurrent().createComponents("../warnings/patientlist.zul", this, null);
		respolist.doModal();
	}
	
	
	/**
	 * 
	 * @param patID
	 * @param patName
	 */
	public void setPatientForFilter(String patID, String patName)
	{
		((Textbox) this.getFellow("filterpatient")).setValue(patID);
		((Textbox) this.getFellow("filterpatienttrigger")).setValue(patName);
	}


	/**
	 * 
	 * @return
	 */
	public SearchCriteria showFromFilter()
	{
		Datebox filterfrom = (Datebox) this.getFellow("datefromfilter");
		Date dat = filterfrom.getValue();
		SearchCriteria sc = null;
		if (dat != null)
		{
			Calendar cal = new GregorianCalendar();
			cal.setTime(dat);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dstr = sdf.format(cal.getTime());
			sc = new SearchCriteria();
			sc.setFieldName("dateTimeOfWarning");
			sc.setCompareOp(new SystemParameter(SystemDictionary.COMPARE_GREAT, ""));
			SystemDictionary.webguiLog("DEBUG", "DATE TO FILTER: " + dstr);
			sc.setFieldValue1(dstr);
		}
		return sc;
	}


	/**
	 * 
	 * @return
	 */
	public SearchCriteria showToFilter()
	{
		Datebox filterto = (Datebox) this.getFellow("datetofilter");
		Date dat = filterto.getValue();
		SearchCriteria sc = null;
		if (dat != null)
		{
			Calendar cal = new GregorianCalendar();
			cal.setTime(dat);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dstr = sdf.format(cal.getTime());
			sc = new SearchCriteria();
			sc.setFieldName("dateTimeOfWarning");
			sc.setCompareOp(new SystemParameter(SystemDictionary.COMPARE_LESS, ""));
			SystemDictionary.webguiLog("DEBUG", "DATE TO FILTER: " + dstr);
			sc.setFieldValue1(dstr);
		}
		return sc;
	}


	/**
	 * 
	 */
	public void refreshWarnings()
	{
		/*String id = (String) Sessions.getCurrent().getAttribute("userid");
		try
		{
			ArrayList<SearchCriteria> zerolist = new ArrayList<SearchCriteria>();
			//SearchCriteria patfil = this.showPatientWarnings();
			//SearchCriteria readfil = this.showReadOrAll();
			SearchCriteria datefrom = this.showFromFilter();
			SearchCriteria dateto = this.showToFilter();
			if (patfil != null)
			{
				zerolist.add(patfil);
			}
			/*if (readfil != null)
			{
				zerolist.add(readfil);
			}
			if (datefrom != null)
			{
				zerolist.add(datefrom);
			}
			if (dateto != null)
			{
				zerolist.add(dateto);
			}
			SearchCriteria[] sc = new SearchCriteria[zerolist.size()];
			SystemDictionary.webguiLog("TRACE", "ZEROLIST length: " + zerolist.size());
			for (int i = 0; i < zerolist.size(); i++)
			{
				sc[i] = zerolist.get(i);
			}
			WarningInfo[] wi = null;
			Warning[] wli = null;

			StorageComponentImpl proxy = new StorageComponentImpl();
			wli = proxy.getWarningsArr(sc, id);
			if (wli == null || wli.length == 0)
			{
				wi = new WarningInfo[0];
			}
			else
			{
				wi = new WarningInfo[wli.length];
				for (int i = 0; wli.length > i; i++)
				{
					wi[i] = new WarningInfo(wli[i], id);
				}
			}
			
			SystemDictionary.webguiLog("DEBUG", "Warning list: " + wi.length);
			
			Grid warningsgrid = (Grid) this.getFellow("warningsgrid");
			Rows newsrows = new Rows();
			for (WarningInfo rowi : wi)
			{
				Row newrow = new Row();
				newrow.appendChild(new Label(rowi.getType()));
				
				// Date
				Label datelabel = new Label(rowi.getDate());
				datelabel.setId("date" + rowi.getID());
				datelabel.setStyle("color: blue; font-size: 10px;");
				newrow.appendChild(datelabel);
				
				//
				newrow.appendChild(new Label(rowi.getPatientName()));
				newrow.appendChild(new Label(rowi.getJustification()));
				
				// Readed label
				Label lreaded = new Label(rowi.getDelivered().equals("true") ? org.zkoss.util.resource.Labels.getLabel("cwarn.Readed")
								: org.zkoss.util.resource.Labels.getLabel("cwarn.NotReaded"));
				if (rowi.getDelivered().equals("true")) {
					lreaded.setStyle("color: blue; font-style: italic;");
				}
				else {
					lreaded.setStyle("color: black; font-style: italic;");
				}
				newrow.appendChild(lreaded);
				
				// Details link
				Label link = new Label("Details");
				link.setSclass("link");
				link.setStyle("font-size: 9px;");
				link.addEventListener("onClick", new ReadWarningListener(rowi.getID()));
				newrow.appendChild(link);
				
				newrow.setId(rowi.getID());
				newsrows.appendChild(newrow);
			}
			warningsgrid.removeChild(warningsgrid.getFellow("filteredrows"));
			newsrows.setId("filteredrows");
			warningsgrid.appendChild(newsrows);

			//warningsgrid..renderAll();
		}
		catch (Exception re)
		{
			re.printStackTrace();
		}*/
	}


}
