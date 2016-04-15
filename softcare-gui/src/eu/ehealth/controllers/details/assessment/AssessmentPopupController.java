package eu.ehealth.controllers.details.assessment;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Checkbox;

import eu.ehealth.SystemDictionary;
import eu.ehealth.utilities.ComponentsFinder;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.PatientAssessment;
import eu.ehealth.ws_client.xsd.SystemParameter;


/**
 * 
 * @author a572832
 *
 */
public class AssessmentPopupController extends Window
{


	private static final long serialVersionUID = -4444846754906879668L;


	/**
	 * 
	 */
	public void saveAssessment()
	{
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		PatientAssessment ass = new PatientAssessment();
		ass.setID("");
		String patientid = ((Textbox) this.getFellow("patientid")).getValue();
		ass.setPatientID(patientid);
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		
		try {
			GregorianCalendar c2 = new GregorianCalendar();
			c2.setTime(cal.getTime());
			ass.setDateOfAssessment(DatatypeFactory.newInstance().newXMLGregorianCalendar(c2));
		}
		catch (Exception ex) {}

		Listitem aetiol = ((Listbox) this.getFellow("field03_in")).getSelectedItem();
		String timeelapsed = ((Textbox) this.getFellow("field04_in")).getValue();
		String severity = ((Textbox) this.getFellow("field05_in")).getValue();
		String relevantpa = ((Textbox) this.getFellow("field06_in")).getValue();
		String comorbidity = ((Textbox) this.getFellow("field07_in")).getValue();
		String charlsonC = ((Textbox) this.getFellow("field08_in")).getValue();
		String barthe = ((Textbox) this.getFellow("field09_in")).getValue();
		String lawton = ((Textbox) this.getFellow("field10_in")).getValue();
		String mmse = ((Textbox) this.getFellow("field11_in")).getValue();
		String mdrs = ((Textbox) this.getFellow("field12_in")).getValue();
		String bless1 = ((Textbox) this.getFellow("field13_in")).getValue();
		String bless2 = ((Textbox) this.getFellow("field14_in")).getValue();
		String bless3 = ((Textbox) this.getFellow("field15_in")).getValue();
		String checklistmbpc = ((Textbox) this.getFellow("field16_in")).getValue();
		String npqisev = ((Textbox) this.getFellow("field17_in")).getValue();
		String npqistre = ((Textbox) this.getFellow("field18_in")).getValue();
		String gds = ((Textbox) this.getFellow("field19_in")).getValue();
		boolean falls = ((Checkbox) this.getFellow("field20_in")).isChecked();
		boolean incont = ((Checkbox) this.getFellow("field21_in")).isChecked();
		boolean delir = ((Checkbox) this.getFellow("field22_in")).isChecked();
		boolean immobi = ((Checkbox) this.getFellow("field23_in")).isChecked();
		boolean sensor = ((Checkbox) this.getFellow("field24_in")).isChecked();
		String pharma = ((Textbox) this.getFellow("field25_in")).getValue();
		// TODO aetiology
		ass.setAetology(new SystemParameter((String) aetiol.getValue(), aetiol.getLabel()));
		
		if (timeelapsed.equals(""))
		{
			ass.setTimeEllapsedSinceDiagnosed(new Short("0"));
		}
		else
		{
			ass.setTimeEllapsedSinceDiagnosed(new Short(timeelapsed));
		}
		if (severity.equals(""))
		{
			ass.setSeverity(new Short("0"));
		}
		else
		{
			ass.setSeverity(new Short(severity));
		}
		ass.setRelevantPathologyAntecedents(relevantpa);
		ass.setComorbidity(comorbidity);
		if (charlsonC.equals(""))
		{
			ass.setCharlsonComorbidityIndex(new Short("0"));
		}
		else
		{
			ass.setCharlsonComorbidityIndex(new Short(charlsonC));
		}
		if (barthe.equals(""))
		{
			ass.setBarthelIndex(new Short("0"));
		}
		else
		{
			ass.setBarthelIndex(new Short(barthe));
		}
		if (lawton.equals(""))
		{
			ass.setLawtonIndex(new Short("0"));
		}
		else
		{
			ass.setLawtonIndex(new Short(lawton));
		}
		if (mmse.equals(""))
		{
			ass.setMMSE(new Short("0"));
		}
		else
		{
			ass.setMMSE(new Short(mmse));
		}
		if (mdrs.equals(""))
		{
			ass.setMDRS(new Short("0"));
		}
		else
		{
			ass.setMDRS(new Short(mdrs));
		}
		if (bless1.equals(""))
		{
			ass.setBlessedScalePart1(new Double(0));
		}
		else
		{
			ass.setBlessedScalePart1(new Double(bless1));
		}
		if (bless2.equals(""))
		{
			ass.setBlessedScalePart2(new Short("0"));
		}
		else
		{
			ass.setBlessedScalePart2(new Short(bless2));
		}
		if (bless3.equals(""))
		{
			ass.setBlessedScalePart3(new Short("0"));
		}
		else
		{
			ass.setBlessedScalePart3(new Short(bless3));
		}
		if (checklistmbpc.equals(""))
		{
			ass.setChecklistMBP(new Short("0"));
		}
		else
		{
			ass.setChecklistMBP(new Short(checklistmbpc));
		}
		if (npqisev.equals(""))
		{
			ass.setNPQISeverity(new Short("0"));
		}
		else
		{
			ass.setNPQISeverity(new Short(npqisev));
		}
		if (npqistre.equals(""))
		{
			ass.setNPQIStress(new Short("0"));
		}
		else
		{
			ass.setNPQIStress(new Short(npqistre));
		}
		if (gds.equals(""))
		{
			ass.setGDS(new Short("0"));
		}
		else
		{
			ass.setGDS(new Short(gds));
		}
		ass.setFalls(falls);
		ass.setIncontinence(incont);
		ass.setDelirium(delir);
		ass.setImmobility(immobi);
		ass.setSensorialDeficits(sensor);
		ass.setPharmacologicalTreatment(pharma);
		try
		{
			proxy.savePatientAssessment(ass, userid);
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
			Messagebox.show("Error : " + e.getMessage(), "#TXT# Save assesment", Messagebox.OK, Messagebox.ERROR);
		}
		finally
		{
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc(null);
			comp.setSrc("../patients/details.zul?patid=" + patientid);
		}
	}

	
}
