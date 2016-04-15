package eu.ehealth;

import java.util.Collection;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Include;
import eu.ehealth.utilities.ComponentsFinder;


/**
 * Helper class to manage error codes
 * 
 * @author Xavi Sarda (Atos Origin)
 */
public class ErrorDictionary {
	
	
	public final static int UNKOW_ERROR = 0;
	public final static int USERTYPE_NOT_ALLOWED = 1;
	public final static int INVALID_CREDENTIALS = 2;
	public final static int EXPIRED_SESSION = 3;
	public final static int UNKNOWN_SERVER_RESPONSE = 4;
	public final static int WARNING_RETRIEVE_ERROR = 5;
	public final static int PATIENT_ASSESSMENT_RETRIEVE = 6;
	public final static int CREATE_CARER_SERVER = 7;
	public final static int CREATE_PATIENT_SERVER = 8;
	public final static int CREATE_CLINICIAN_SERVER = 9;
	public final static int CONSTRAINT_VIOLATION = 10;
	
	public static String UNKOW_ERROR_LBL = "error.unknow";
	public static String USERTYPE_NOT_ALLOWED_LBL = "error.wrong.type";
	public static String INVALID_CREDENTIALS_LBL = "error.invalid.credentials";
	public static String EXPIRED_SESSION_LBL = "error.expired.session";
	public static String UNKNOWN_SERVER_RESPONSE_LBL = "error.unknow.response";
	public static String WARNING_RETRIEVE_ERROR_LBL = "error.warning.retrieve";
	public static String PATIENT_ASSESSMENT_RETRIEVE_LBL = "error.patient.assessment.retrieve";
	public static String CREATE_CARER_LBL = "error.create.user.failed.server";
	public static String CONSTRAINT_VIOLATION_LBL = "error.constraint.violation.server";
	
	
	/**
	 * Static function which returns a string with human-readable error messages
	 * 
	 * @param errorcode Error code retrieved from requests
	 * @return String containig error messages
	 */
	public static String getErrorLabel(int errorcode){
		String labelrsrc = null;
		switch(errorcode){
		case UNKOW_ERROR:
			labelrsrc = UNKOW_ERROR_LBL;
			break;
		case USERTYPE_NOT_ALLOWED:
			labelrsrc = USERTYPE_NOT_ALLOWED_LBL;
			break;
		case INVALID_CREDENTIALS:
			labelrsrc = INVALID_CREDENTIALS_LBL;
			break;
		case EXPIRED_SESSION:
			labelrsrc = EXPIRED_SESSION_LBL;
			break;
		case UNKNOWN_SERVER_RESPONSE:
			labelrsrc = UNKNOWN_SERVER_RESPONSE_LBL;
			break;
		case WARNING_RETRIEVE_ERROR:
			labelrsrc = WARNING_RETRIEVE_ERROR_LBL;
			break;
		case CREATE_CARER_SERVER:
			labelrsrc = CREATE_CARER_LBL;
			return Labels.getLabel(labelrsrc, new Object[]{Labels.getLabel("error.helper.carer")});
		case CREATE_PATIENT_SERVER:
			labelrsrc = CREATE_CARER_LBL;
			return Labels.getLabel(labelrsrc, new Object[]{Labels.getLabel("error.helper.patient")});
		case CREATE_CLINICIAN_SERVER:
			labelrsrc = CREATE_CARER_LBL;
			return Labels.getLabel(labelrsrc, new Object[]{Labels.getLabel("error.helper.clinician")});
		case CONSTRAINT_VIOLATION:
			labelrsrc = CONSTRAINT_VIOLATION_LBL;
			break;
		default:
			labelrsrc = UNKOW_ERROR_LBL;
			break;
		}
		return Labels.getLabel(labelrsrc);
	}
	
	
	/**
	 * 
	 * @param URL
	 */
	public static void redirectWithError(String URL){
		Sessions.getCurrent().setAttribute("showerror", "1");
		//Executions.getCurrent().sendRedirect(URL);
		
		Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
		Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
		comp.setSrc(URL);
	}
	
	
}
