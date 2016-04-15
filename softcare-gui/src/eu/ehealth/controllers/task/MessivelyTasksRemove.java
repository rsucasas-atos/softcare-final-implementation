package eu.ehealth.controllers.task;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;
import eu.ehealth.SystemDictionary;
import eu.ehealth.controllers.details.DetailPatientController;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.OperationResult;
import eu.ehealth.ws_client.xsd.Patient;


public class MessivelyTasksRemove extends Window
{


	private static final long serialVersionUID = 350369571674329276L;


	/**
	 * 
	 * @param patientid
	 * @param tasktype
	 * @param start
	 * @param end
	 * @throws RemoteException
	 */
	public void removeMassively(String patientid, Object tasktype, Date start, Date end) throws RemoteException
	{
		String tasktypestr = (Integer) tasktype + "";
		SystemDictionary.webguiLog("INFO", "TASK TYPE: " + tasktypestr);
		BigInteger tasktypedef = new BigInteger(tasktypestr);
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		String userId = (String) Sessions.getCurrent().getAttribute("userid");
		Patient pat = proxy.getPatient(patientid, userId);
		String carer = pat.getPatientCarer().getID();
		
		OperationResult careruser = proxy.getUserIdByPersonId(carer, SystemDictionary.USERTYPE_CARER_INT, userId);
		OperationResult patientuser = proxy.getUserIdByPersonId(patientid, SystemDictionary.USERTYPE_PATIENT_INT, userId);
		
		SystemDictionary.webguiLog("INFO", "CARER ID: " + careruser.getCode());
		
		XMLGregorianCalendar xmlStartDate = null;
		XMLGregorianCalendar xmlEndDate = null;
		
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(start);
			xmlStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch (Exception ex) {}
		
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(end);
			xmlEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch (Exception ex) {}
		
		OperationResult ores = proxy.removeTaskMassively(patientuser.getCode(), tasktypedef, xmlStartDate, xmlEndDate, userId);
		SystemDictionary.webguiLog("INFO", "RESULT: " + ores.getDescription()
				+ "-" + ores.getCode() + "-" + ores.getStatus());
		this.setVisible(false);
		DetailPatientController parentwindow = ((DetailPatientController) this
				.getParent());
		parentwindow.refreshCalendarData();
		parentwindow.removeChild(this);
	}

	
}
