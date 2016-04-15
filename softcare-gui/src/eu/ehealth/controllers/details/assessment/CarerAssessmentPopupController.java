package eu.ehealth.controllers.details.assessment;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import eu.ehealth.SystemDictionary;
import eu.ehealth.utilities.ComponentsFinder;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.CarerAssessment;
import eu.ehealth.ws_client.xsd.OperationResult;
import eu.ehealth.ws_client.xsd.User;


/**
 * 
 * 
 * @author 
 * @date 25/04/2014.
 *
 */
public class CarerAssessmentPopupController extends Window
{


	private static final long serialVersionUID = -8244017099128943269L;


	/**
	 * 
	 */
	public void saveAssessment()
	{
		CarerAssessment cass = new CarerAssessment();
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		cass.setID("");
		String carerid = ((Textbox) this.getFellow("carerid")).getValue();
		Calendar caldate = new GregorianCalendar();
		caldate.setTime(new Date());
		String relevant = ((Textbox) this.getFellow("field00_in")).getValue();
		String sever = ((Textbox) this.getFellow("field01_in")).getValue();
		String emotional = ((Textbox) this.getFellow("field02_in")).getValue();
		String pyscoact = ((Textbox) this.getFellow("field03_in")).getValue();
		String lifeq = ((Textbox) this.getFellow("field04_in")).getValue();

		cass.setCarerID(carerid);

		try
		{
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(caldate.getTime());
			cass.setDateOfAssessment(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
		}
		catch (DatatypeConfigurationException e)
		{
			e.printStackTrace();
		}
		
		cass.setRelevantHealthProblem(relevant);
		short valShort = 0;
		if (sever.equals(""))
		{
			cass.setSeverityOfBurden(valShort);
		}
		else
		{
			cass.setSeverityOfBurden(new Short(sever));
		}
		cass.setEmotionalOrMentalDisorders(emotional);
		cass.setPsychoactiveDrugs(pyscoact);
		if (lifeq.equals(""))
		{
			cass.setQualityOfLife(valShort);
		}
		else
		{
			cass.setQualityOfLife(new Short(lifeq));
		}
		
		try
		{
			StorageComponentImpl proxy = SystemDictionary.getSCProxy();
			User us = proxy.getUser(userid);
			cass.setClinicianID(us.getPersonID());
			OperationResult res = proxy.saveCarerAssessment(cass, userid);
			SystemDictionary.webguiLog("INFO", "CARER ASSESSMENT: " + res.getCode() + "-" + res.getDescription());
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
			comp.setSrc("../carers/details.zul?patid=" + carerid);
		}
	}

	
}
