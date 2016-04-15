package eu.ehealth.ws_client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.apache.commons.configuration.PropertiesConfiguration;
import eu.ehealth.SystemDictionary;
import eu.ehealth.ws_client.xsd.AdministratorInfo;
import eu.ehealth.ws_client.xsd.CarerAssessment;
import eu.ehealth.ws_client.xsd.CarerInfo;
import eu.ehealth.ws_client.xsd.ClinicianInfo;
import eu.ehealth.ws_client.xsd.ExternalService;
import eu.ehealth.ws_client.xsd.Measurement;
import eu.ehealth.ws_client.xsd.MediaContent;
import eu.ehealth.ws_client.xsd.OperationResult;
import eu.ehealth.ws_client.xsd.PatientAssessment;
import eu.ehealth.ws_client.xsd.PatientInfo;
import eu.ehealth.ws_client.xsd.QuestionnaireInfo;
import eu.ehealth.ws_client.xsd.SystemParameter;
import eu.ehealth.ws_client.xsd.Task;
import eu.ehealth.ws_client.xsd.Warning;
import it.polimi.tower4clouds.java_app_dc.Monitor;


/**
 * 
 * @author a572832
 *
 */
@javax.jws.WebService(serviceName = "StorageComponentImplService", portName = "StorageComponentImplPort", targetNamespace = "http://ehealth.eu/", wsdlLocation = "file:/C:/Users/A572832/workspace/cxf-hibernate/services.wsdl", endpointInterface = "eu.ehealth.db.storagecomponent.StorageComponent")
public class StorageComponentImpl implements StorageComponent
{
	

	private static final QName SERVICE_NAME = new QName("http://ehealth.eu/", "StorageComponentImplService");
	private StorageComponent _port;
	
	// global connection values
	private static URL _wsdlURL;
	private static StorageComponentImplService _storageComponentImplService;
	
	
	/**
	 * Init properties
	 */
	static {
		try
		{
			SystemDictionary.webguiLog("INFO", "[StorageComponentImpl] Initializing static connection properties ...");
			
			if ((SystemDictionary.CONFIG_PROPERTIES != null) && (!SystemDictionary.CONFIG_PROPERTIES.isEmpty())) 
			{
				SystemDictionary.webguiLog("WARNING", "[StorageComponentImpl] CONFIG_PROPERTIES is null or empty");
				SystemDictionary.webguiLog("WARNING", "[StorageComponentImpl] Reloading file...");
				
				try {
					SystemDictionary.CONFIG_PROPERTIES = new PropertiesConfiguration("webgui.properties");
				}
				catch (Exception ex) {
					SystemDictionary.logException(ex);
				}
			}
			
			if ((SystemDictionary.CONFIG_PROPERTIES != null) && (!SystemDictionary.CONFIG_PROPERTIES.isEmpty())) 
			{
				// Take URI from 'webgui.properties' file, key 'storagecomponent.uri'
				//ClassLoader loader = StorageComponentImpl.class.getClassLoader();

				// _wsdlURL
				//_wsdlURL = loader.getResource(SystemDictionary.CONFIG_PROPERTIES.getString("storagecomponent.local.uri.file"));
				_wsdlURL = new URL(SystemDictionary.getWebServicesURL());
				SystemDictionary.webguiLog("INFO", "[StorageComponentImpl] wsdl URL ... [" + _wsdlURL + "]");
				
				// service
				_storageComponentImplService = new StorageComponentImplService(_wsdlURL, SERVICE_NAME);

			    SystemDictionary.webguiLog("INFO", "[StorageComponentImpl] Static connection initialized");
			}
			else 
			{
				SystemDictionary.webguiLog("FATAL", "[StorageComponentImpl] CONFIG_PROPERTIES is null or empty");
			}
		}
		catch (Exception severe)
		{
			SystemDictionary.logException(severe);
		}
	}
	
	
	/**
	 * 
	 */
	public StorageComponentImpl() { }
	

	/**
	 * 
	 */
	private void initStorageComponentImpl()
	{
		try
		{
			if ((SystemDictionary.CONFIG_PROPERTIES != null) && (!SystemDictionary.CONFIG_PROPERTIES.isEmpty() 
					&& (_storageComponentImplService != null))) 
			{
				_port = _storageComponentImplService.getStorageComponentImplPort(); 
			}
			else 
			{
				SystemDictionary.webguiLog("FATAL", "[StorageComponentImpl] CONFIG_PROPERTIES is null or empty");
			}
		}
		catch (Exception severe)
		{
			SystemDictionary.logException(severe);
		}
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult getUserType(java.lang.String id)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getUserType(id);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult deleteAdministrator(java.lang.String id, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.deleteAdministrator(id, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult updateSystemParameter(int type, 
			eu.ehealth.ws_client.xsd.SystemParameter value, eu.ehealth.ws_client.xsd.SystemParameter locale)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.updateSystemParameter(type, value, locale);
	}

	
	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.SystemParameter> getSystemParameterList(int type, 
			eu.ehealth.ws_client.xsd.SystemParameter locale)
	{
		if (_port == null)
			initStorageComponentImpl();
        return _port.getSystemParameterList(type, locale);
	}
    

	@Override
	public eu.ehealth.ws_client.xsd.Patient getPatient(java.lang.String id, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getPatient(id, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult addMediaContent(eu.ehealth.ws_client.xsd.MediaContent in, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.addMediaContent(in, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult changePassword(java.lang.String userId, java.lang.String password,
			java.lang.String reqId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.changePassword(userId, password, reqId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult getQuestionDescription(java.lang.String questionID,
			eu.ehealth.ws_client.xsd.SystemParameter locale)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getQuestionDescription(questionID, locale);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.Measurement> getMeasurement(
			java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> filter, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getMeasurement(filter, userId);
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.Measurement>
	 * to
	 * eu.aladdin_project.ws_client.xsd.Measurement[]
	 * 
	 * @param filter
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.Measurement[] getMeasurementArr(eu.ehealth.ws_client.xsd.SearchCriteria[] filter, java.lang.String userId)
	{
		eu.ehealth.ws_client.xsd.Measurement[] arr = new eu.ehealth.ws_client.xsd.Measurement[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.Measurement> l = 
					(ArrayList<Measurement>) getMeasurement(Arrays.asList(filter), userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.Measurement[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult deleteExternalService(java.lang.String id, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.deleteExternalService(id, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult deleteUser(java.lang.String id)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.deleteUser(id);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.CarerAssessment> getCarerAssessments(java.lang.String carerId, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getCarerAssessments(carerId, userId);
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.CarerAssessment>
	 * to
	 * eu.aladdin_project.ws_client.xsd.CarerAssessment[]
	 * 
	 * @param carerId
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.CarerAssessment[] getCarerAssessmentsArr(java.lang.String carerId, java.lang.String userId)
	{
		eu.ehealth.ws_client.xsd.CarerAssessment[] arr = new eu.ehealth.ws_client.xsd.CarerAssessment[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.CarerAssessment> l = (ArrayList<CarerAssessment>) getCarerAssessments(carerId, userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.CarerAssessment[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}


	@Override
	public eu.ehealth.ws_client.xsd.QuestionnaireAnswers getQuestionnaireAnswersByTask(java.lang.String taskId, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getQuestionnaireAnswersByTask(taskId, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult savePatientAssessment(eu.ehealth.ws_client.xsd.PatientAssessment assessment,
			java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.savePatientAssessment(assessment, userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.MediaContent> getMediaContent(java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> filter,
			java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getMediaContent(filter, userId);
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.MediaContent>
	 * to
	 * eu.aladdin_project.ws_client.xsd.MediaContent[]
	 * 
	 * @param filter
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.MediaContent[] getMediaContentArr(eu.ehealth.ws_client.xsd.SearchCriteria[] filter, java.lang.String userId)
	{
		eu.ehealth.ws_client.xsd.MediaContent[] arr = new eu.ehealth.ws_client.xsd.MediaContent[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.MediaContent> l = (ArrayList<MediaContent>) getMediaContent(Arrays.asList(filter), userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.MediaContent[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.QuestionnaireInfo> listOfQuestionnaires(
			eu.ehealth.ws_client.xsd.SystemParameter locale, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.listOfQuestionnaires(locale, userId);
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.QuestionnaireInfo>
	 * to
	 * eu.aladdin_project.ws_client.xsd.QuestionnaireInfo[]
	 * 
	 * @param locale
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.QuestionnaireInfo[] listOfQuestionnairesArr(
			eu.ehealth.ws_client.xsd.SystemParameter locale, java.lang.String userId)
	{
		eu.ehealth.ws_client.xsd.QuestionnaireInfo[] arr = new eu.ehealth.ws_client.xsd.QuestionnaireInfo[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.QuestionnaireInfo> l = 
					(ArrayList<QuestionnaireInfo>) listOfQuestionnaires(locale, userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.QuestionnaireInfo[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult saveWarning(eu.ehealth.ws_client.xsd.Warning warn, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.saveWarning(warn, userId);
	}

	@Override
	public eu.ehealth.ws_client.xsd.OperationResult createQuestionnaire(
			eu.ehealth.ws_client.xsd.Questionnaire data, eu.ehealth.ws_client.xsd.SystemParameter locale, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.createQuestionnaire(data, locale, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult updateCarer(eu.ehealth.ws_client.xsd.Carer data, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.updateCarer(data, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult getUserIdByPersonId(java.lang.String id, int type, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getUserIdByPersonId(id, type, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult updateQuestionnaire(
			eu.ehealth.ws_client.xsd.Questionnaire data, eu.ehealth.ws_client.xsd.SystemParameter locale, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.updateQuestionnaire(data, locale, userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.CarerInfo> listOfCarers(
			java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> filter, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.listOfCarers(filter, userId);
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.CarerInfo>
	 * to
	 * eu.aladdin_project.ws_client.xsd.CarerInfo[]
	 * 
	 * @param filter
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.CarerInfo[] listOfCarersArr(eu.ehealth.ws_client.xsd.SearchCriteria[] filter, java.lang.String userId)
	{
		eu.ehealth.ws_client.xsd.CarerInfo[] arr = new eu.ehealth.ws_client.xsd.CarerInfo[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.CarerInfo> l = (ArrayList<CarerInfo>) listOfCarers(Arrays.asList(filter), userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.CarerInfo[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult deleteClinician(java.lang.String id, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.deleteClinician(id, userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.QuestionnaireAnswers> getQuestionnaireAnswers(java.lang.String objectId,
			javax.xml.datatype.XMLGregorianCalendar fromDate, javax.xml.datatype.XMLGregorianCalendar toDate, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getQuestionnaireAnswers(objectId, fromDate, toDate, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult storeMeasurements(
			java.util.List<eu.ehealth.ws_client.xsd.Measurement> data, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.storeMeasurements(data, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult assignTasksMassively(
			eu.ehealth.ws_client.xsd.Task task, javax.xml.datatype.XMLGregorianCalendar startDate,
			javax.xml.datatype.XMLGregorianCalendar endDate, java.math.BigInteger frequency, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.assignTasksMassively(task, startDate, endDate, frequency, userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.CarerInfo> getAvailableCarers(java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getAvailableCarers(userId);
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.Carer>
	 * to
	 * eu.aladdin_project.ws_client.xsd.Carer[]
	 * 
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.CarerInfo[] getAvailableCarersArr(java.lang.String userId)
	{
		eu.ehealth.ws_client.xsd.CarerInfo[] arr = new eu.ehealth.ws_client.xsd.CarerInfo[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.CarerInfo> l = (ArrayList<CarerInfo>) getAvailableCarers(userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.CarerInfo[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult deleteCarerAssessment(java.lang.String assessmentId, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.deleteCarerAssessment(assessmentId, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult createClinician(eu.ehealth.ws_client.xsd.Clinician data, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.createClinician(data, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult updateMediaContent(eu.ehealth.ws_client.xsd.MediaContent ec, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.updateMediaContent(ec, userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.PatientInfo> getPatientsForCaregiver(java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getPatientsForCaregiver(userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.SystemParameter> listOfPossibleTasks(int userType)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.listOfPossibleTasks(userType);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult storeQuestionnaireAnswers(
			eu.ehealth.ws_client.xsd.QuestionnaireAnswers data, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.storeQuestionnaireAnswers(data, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.Carer getCarer(java.lang.String id, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getCarer(id, userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.AdministratorInfo> listOfAdministrators(
			java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> filter, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.listOfAdministrators(filter, userId);
	}
	
	
	/**
	 * 
	 * @param filter
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.AdministratorInfo[] listOfAdministratorsArr(
			eu.ehealth.ws_client.xsd.SearchCriteria[] filter, java.lang.String userId)
	{
		eu.ehealth.ws_client.xsd.AdministratorInfo[] arr = new eu.ehealth.ws_client.xsd.AdministratorInfo[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.AdministratorInfo> l = 
					(ArrayList<AdministratorInfo>) listOfAdministrators(Arrays.asList(filter), userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.AdministratorInfo[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}
	
	
	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.ClinicianInfo> listOfClinicians(
			java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> filter, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.listOfClinicians(filter, userId);
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.ClinicianInfo>
	 * to
	 * eu.aladdin_project.ws_client.xsd.ClinicianInfo[]
	 * 
	 * @param filter
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.ClinicianInfo[] listOfCliniciansArr(
			eu.ehealth.ws_client.xsd.SearchCriteria[] filter, java.lang.String userId)
	{		
		eu.ehealth.ws_client.xsd.ClinicianInfo[] arr = new eu.ehealth.ws_client.xsd.ClinicianInfo[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.ClinicianInfo> l = 
					(ArrayList<ClinicianInfo>) listOfClinicians(Arrays.asList(filter), userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.ClinicianInfo[l.size()]);
			}
		}
		catch (Exception ex) {
			SystemDictionary.logException(ex);
		}
		
		return arr;
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult createAdministrator(eu.ehealth.ws_client.xsd.Administrator data, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.createAdministrator(data, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult updateExternalService(eu.ehealth.ws_client.xsd.ExternalService data, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.updateExternalService(data, userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.Measurement> getPatientMeasurement(java.lang.String patientId, int measurementType,
			javax.xml.datatype.XMLGregorianCalendar fromData, javax.xml.datatype.XMLGregorianCalendar toData, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getPatientMeasurement(patientId, measurementType, fromData, toData, userId);
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.Measurement>
	 * to
	 * eu.aladdin_project.ws_client.xsd.Measurement[]
	 * 
	 * @param patientId
	 * @param measurementType
	 * @param fromData
	 * @param toData
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.Measurement[] getPatientMeasurementArr(
			java.lang.String patientId, int measurementType, Calendar fromData, Calendar toData, java.lang.String userId)
	{
		eu.ehealth.ws_client.xsd.Measurement[] arr = new eu.ehealth.ws_client.xsd.Measurement[0];
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(fromData.getTime());
			XMLGregorianCalendar date_fromData = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			GregorianCalendar c2 = new GregorianCalendar();
			c2.setTime(toData.getTime());
			XMLGregorianCalendar date_toData = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);
			
			ArrayList<eu.ehealth.ws_client.xsd.Measurement> l = 
					(ArrayList<Measurement>) getPatientMeasurement(patientId, measurementType, date_fromData, date_toData, userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.Measurement[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult deleteCarer(java.lang.String id, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.deleteCarer(id, userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.ExternalService> getAllExternalServices(java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getAllExternalServices(userId);
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.ExternalService>
	 * to
	 * eu.aladdin_project.ws_client.xsd.Measurement[]
	 * 
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.ExternalService[] getAllExternalServicesArr(java.lang.String userId)
	{
		eu.ehealth.ws_client.xsd.ExternalService[] arr = new eu.ehealth.ws_client.xsd.ExternalService[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.ExternalService> l = 
					(ArrayList<ExternalService>) getAllExternalServices(userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.ExternalService[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public ArrayList<eu.ehealth.ws_client.xsd.ExternalService> getAllExternalServicesArrayList(java.lang.String userId)
	{
		return (ArrayList<ExternalService>) getAllExternalServices(userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.Warning> getWarnings(
			java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> warn, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getWarnings(warn, userId);
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.Warning>
	 * to
	 * eu.aladdin_project.ws_client.xsd.Warning[]
	 * 
	 * @param warn
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.Warning[] getWarningsArr(eu.ehealth.ws_client.xsd.SearchCriteria[] warn, java.lang.String userId)
	{
		eu.ehealth.ws_client.xsd.Warning[] arr = new eu.ehealth.ws_client.xsd.Warning[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.Warning> l = 
					(ArrayList<Warning>) getWarnings(Arrays.asList(warn), userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.Warning[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.PatientInfo>
	 * to
	 * eu.aladdin_project.ws_client.xsd.PatientInfo[]
	 * 
	 * @param filter
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.PatientInfo[] listOfPatientsArr(eu.ehealth.ws_client.xsd.SearchCriteria[] filter, java.lang.String userId)
	{
		eu.ehealth.ws_client.xsd.PatientInfo[] arr = new eu.ehealth.ws_client.xsd.PatientInfo[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.PatientInfo> l = 
					(ArrayList<PatientInfo>) listOfPatients(Arrays.asList(filter), userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.PatientInfo[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult updateClinician(eu.ehealth.ws_client.xsd.Clinician data, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.updateClinician(data, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult markWarningAsRead(java.lang.String id, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.markWarningAsRead(id, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult createExternalService(eu.ehealth.ws_client.xsd.ExternalService data, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.createExternalService(data, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.User getUser(java.lang.String id)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getUser(id);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult updateAdministrator(eu.ehealth.ws_client.xsd.Administrator data, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.updateAdministrator(data, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.Clinician getClinician(java.lang.String id, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getClinician(id, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.Task getTask(java.lang.String id, eu.ehealth.ws_client.xsd.SystemParameter locale, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getTask(id, locale, userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.Task> getUserPlannedTasks(java.lang.String userId, javax.xml.datatype.XMLGregorianCalendar fromDate,
			javax.xml.datatype.XMLGregorianCalendar toDate, eu.ehealth.ws_client.xsd.SystemParameter locale, java.lang.String requesterId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getUserPlannedTasks(userId, fromDate, toDate, locale, requesterId);
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.Task>
	 * to
	 * eu.aladdin_project.ws_client.xsd.Task[]
	 * 
	 * @param userId
	 * @param fromDate
	 * @param toDate
	 * @param locale
	 * @param requesterId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.Task[] getUserPlannedTasksArr(java.lang.String userId, Calendar fromDate,
			Calendar toDate, eu.ehealth.ws_client.xsd.SystemParameter locale, java.lang.String requesterId)
	{
		eu.ehealth.ws_client.xsd.Task[] arr = new eu.ehealth.ws_client.xsd.Task[0];
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(fromDate.getTime());
			XMLGregorianCalendar date_fromData = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

			GregorianCalendar c2 = new GregorianCalendar();
			c2.setTime(toDate.getTime());
			XMLGregorianCalendar date_toData = DatatypeFactory.newInstance().newXMLGregorianCalendar(c2);
			
			ArrayList<eu.ehealth.ws_client.xsd.Task> l = 
					(ArrayList<Task>) getUserPlannedTasks(userId, date_fromData, date_toData, locale, requesterId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.Task[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult createUser(eu.ehealth.ws_client.xsd.User user)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.createUser(user);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult assignTask(eu.ehealth.ws_client.xsd.Task task,
			eu.ehealth.ws_client.xsd.SystemParameter locale, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.assignTask(task, locale, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult saveCarerAssessment(eu.ehealth.ws_client.xsd.CarerAssessment assessment, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.saveCarerAssessment(assessment, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult deleteMediaContent(java.lang.String id, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.deleteMediaContent(id, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult createCarer(eu.ehealth.ws_client.xsd.Carer data, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.createCarer(data, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult removeTaskMassively(java.lang.String patientId, java.math.BigInteger typeOfTask,
			javax.xml.datatype.XMLGregorianCalendar startDate, javax.xml.datatype.XMLGregorianCalendar endDate, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.removeTaskMassively(patientId, typeOfTask, startDate, endDate, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.Administrator getAdministrator(java.lang.String id, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getAdministrator(id, userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.SystemParameter> listOfSupportedLocales()
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.listOfSupportedLocales();
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.SystemParameter>
	 * to
	 * eu.aladdin_project.ws_client.xsd.SystemParameter[]
	 * 
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.SystemParameter[] listOfSupportedLocalesArr()
	{
		eu.ehealth.ws_client.xsd.SystemParameter[] arr = new eu.ehealth.ws_client.xsd.SystemParameter[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.SystemParameter> l = 
					(ArrayList<SystemParameter>) listOfSupportedLocales();
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.SystemParameter[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult changeTaskStatus(int taskId, int taskStatus, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.changeTaskStatus(taskId, taskStatus, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.Questionnaire getQuestionnaire(java.lang.String id, eu.ehealth.ws_client.xsd.SystemParameter locale, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getQuestionnaire(id, locale, userId);
	}


	@Override
	public java.util.List<eu.ehealth.ws_client.xsd.PatientAssessment> getPatientAssessments(java.lang.String patientId, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getPatientAssessments(patientId, userId);
	}
	
	
	/**
	 * java.util.List<eu.aladdin_project.ws_client.xsd.PatientAssessment>
	 * to
	 * eu.aladdin_project.ws_client.xsd.Task[]
	 * 
	 * @param patientId
	 * @param userId
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.PatientAssessment[] getPatientAssessmentsArr(java.lang.String patientId, java.lang.String userId)
	{
		eu.ehealth.ws_client.xsd.PatientAssessment[] arr = new eu.ehealth.ws_client.xsd.PatientAssessment[0];
		try {
			ArrayList<eu.ehealth.ws_client.xsd.PatientAssessment> l = 
					(ArrayList<PatientAssessment>) getPatientAssessments(patientId, userId);
			
			if (l != null) {
				arr = l.toArray(new eu.ehealth.ws_client.xsd.PatientAssessment[l.size()]);
			}
		}
		catch (Exception ex) {}
		
		return arr;
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult updatePatient(eu.ehealth.ws_client.xsd.Patient data, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.updatePatient(data, userId);
	}


	@Override
	public java.lang.String getQuestionnaireAnswerValue(java.lang.String questionId, java.lang.String value, eu.ehealth.ws_client.xsd.SystemParameter locale)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getQuestionnaireAnswerValue(questionId, value, locale);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult deletePatientAssessment(java.lang.String assessmentId, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.deletePatientAssessment(assessmentId, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult deleteQuestionnaire(java.lang.String id, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.deleteQuestionnaire(id, userId);
	}


	@Override
	public eu.ehealth.ws_client.xsd.OperationResult createPatient(eu.ehealth.ws_client.xsd.Patient data, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.createPatient(data, userId);
	}


	@Override
	public String[] registerUser(String userName, String password, String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.registerUser(userName, password, userId);
	}


	@Override
	public String[] lockout(String userName)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.lockout(userName);
	}


	@Override
	public OperationResult getUserIdByCarerId(String id, String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getUserIdByCarerId(id, userId);
	}
	
	
	@Override
	public OperationResult getUserIdByAdminId(String id, String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getUserIdByAdminId(id, userId);
	}
	
	
	@Override
	public OperationResult getUserIdByClinicianId(String id, String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getUserIdByClinicianId(id, userId);
	}


	@Override
	public OperationResult getUserIdByPatientId(String id, String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getUserIdByPatientId(id, userId);
	}


	@Override
	public OperationResult getUserPersonIdByPatientId(String id, String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getUserPersonIdByPatientId(id, userId);
	}


	@Override
	public List<String[]> getEhealthRoles()
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getEhealthRoles();
	}


	@Override
	public List<String[]> getEhealthParameters()
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getEhealthParameters();
	}


	@Override
	public void logout(String idUser, String sessionId) {
		if (_port == null)
			initStorageComponentImpl();
		_port.logout(idUser, sessionId);
	}


	@Override
	public Object[] getConnectedUsers(String idUser) {
		if (_port == null)
			initStorageComponentImpl();
		return _port.getConnectedUsers(idUser);
	}


	@Override
	public List<String[]> getAppConfig(String userId) {
		if (_port == null)
			initStorageComponentImpl();
		return _port.getAppConfig(userId);
	}


	@Override
	public List<String[]> listOfUsers(String userId) {
		if (_port == null)
			initStorageComponentImpl();
		return _port.listOfUsers(userId);
	}


	@Override
	public OperationResult lockUser(String userId, int id, boolean locked) {
		if (_port == null)
			initStorageComponentImpl();
		return _port.lockUser(userId, id, locked);
	}


	@Override
	public OperationResult updateAppConfigRow(String userId, int id, String value, boolean active) {
		if (_port == null)
			initStorageComponentImpl();
		return _port.updateAppConfigRow(userId, id, value, active);
	}


	@Override
	public String[] getStatus()
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.getStatus();
	}


	@Override
	public String initDB(String id)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.initDB(id);
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// MONITORED METHODS
	// 		--> login, updateData, getData, deleteData
	///////////////////////////////////////////////////////////////////////////

	@Override
	@Monitor(type = "login")
	public eu.ehealth.ws_client.xsd.OperationResult auth(java.lang.String login, java.lang.String password, java.lang.String token)
	{
		SystemDictionary.webguiLog("INFO", "[StorageComponentImpl] auth called ...");
		try
		{
			Thread.sleep(6000);
		}
		catch (Exception ex)
		{
		}
		SystemDictionary.webguiLog("INFO", "[StorageComponentImpl] ... 6 seconds ...");
		
		if (_port == null)
			initStorageComponentImpl();		
		return _port.auth(login, password, token);
	}
	
	
	@Override
	public String[] login(String userName, String password)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.login(userName, password);
	}
	
	
	@Override
	@Monitor(type = "updateData")
	public eu.ehealth.ws_client.xsd.OperationResult updateUser(eu.ehealth.ws_client.xsd.User user)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.updateUser(user);
	}
	
	
	@Override
	@Monitor(type = "getData")
	public java.util.List<eu.ehealth.ws_client.xsd.PatientInfo> listOfPatients(
			java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> filter, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.listOfPatients(filter, userId);
	}
	
	
	@Override
	@Monitor(type = "deleteData")
	public eu.ehealth.ws_client.xsd.OperationResult deletePatient(java.lang.String id, java.lang.String userId)
	{
		if (_port == null)
			initStorageComponentImpl();
		return _port.deletePatient(id, userId);
	}
	
	
}
