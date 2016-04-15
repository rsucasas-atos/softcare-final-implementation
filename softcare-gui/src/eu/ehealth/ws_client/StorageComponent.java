package eu.ehealth.ws_client;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import eu.ehealth.ws_client.storagecomponent.ObjectFactory;


@WebService(targetNamespace = "http://aladdin-project.eu/StorageComponent/", name = "StorageComponent")
@XmlSeeAlso({ ObjectFactory.class, eu.ehealth.ws_client.xsd.ObjectFactory.class })
public interface StorageComponent
{

	
	
	/**
	 * 
	 * @return
	 */
	public String[] getStatus();
	
	
	/**
	 * 
	 * @param id
	 */
	public String initDB(String id);
	
	
	/**
	 * registerUser (user, pass, userId) 
	 */
	public String[] registerUser(String userName, String password, String userId);
	
	
	/**
	 * login  (user, pass) 
	 */
	public String[] login(String userName, String password);
	
	
	/**
	 * lockout  (user)
	 */
	public String[] lockout(String userName);
	
	
	/**
	 * 
	 * @return
	 */
	public List<String[]> getEhealthRoles();
	
	
	/**
	 * 
	 * @return
	 */
	public List<String[]> getEhealthParameters();
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<String[]> getAppConfig(String userId);
	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List<String[]> listOfUsers(String userId);
	
	
	/**
	 * 
	 * @param userId
	 * @param id
	 * @param locked
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.OperationResult lockUser(String userId, int id, boolean locked);
	
	
	/**
	 * 
	 * @param userId
	 * @param id
	 * @param value
	 * @param active
	 * @return
	 */
	public eu.ehealth.ws_client.xsd.OperationResult updateAppConfigRow(String userId, int id, String value, boolean active);
	
	
	/**
	 * 
	 * @param idUser
	 * @param sessionId
	 * @return
	 */
	public void logout(String idUser, String sessionId);
	
	
	/**
	 * 
	 * @param idUser
	 * @return
	 */
	public Object[] getConnectedUsers(String idUser);
	
	
	/**
	 * The GetUserIdByAdminId method receives a person’s identity and returns corresponded user id or an error-code.
	 */
	public eu.ehealth.ws_client.xsd.OperationResult getUserIdByAdminId(String id, String userId);
	
	
	/**
	 * The GetUserIdByCarerId method receives a person’s identity and returns corresponded user id or an error-code.
	 */
	public eu.ehealth.ws_client.xsd.OperationResult getUserIdByCarerId(String id, String userId);
	
	
	/**
	 * The GetUserIdByClinicianId method receives a person’s identity and returns corresponded user id or an error-code.
	 */
	public eu.ehealth.ws_client.xsd.OperationResult getUserIdByClinicianId(String id, String userId);
	
	
	/**
	 * The getUserIdByPatientId method receives a person’s identity and returns corresponded user id or an error-code.
	 */
	public eu.ehealth.ws_client.xsd.OperationResult getUserIdByPatientId(String id, String userId);
	
	
	/**
	 * The getUserIdByPatientId method receives a person’s identity and returns corresponded user id or an error-code.
	 */
	public eu.ehealth.ws_client.xsd.OperationResult getUserPersonIdByPatientId(String id, String userId);
	

	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetUserType", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetUserType")
	@WebMethod(operationName = "GetUserType", action = "http://aladdin-project.eu/StorageComponent/GetUserType")
	@ResponseWrapper(localName = "GetUserTypeResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetUserTypeResponse")
	public eu.ehealth.ws_client.xsd.OperationResult getUserType(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "DeleteAdministrator", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteAdministrator")
	@WebMethod(operationName = "DeleteAdministrator", action = "http://aladdin-project.eu/StorageComponent/DeleteAdministrator")
	@ResponseWrapper(localName = "DeleteAdministratorResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteAdministratorResponse")
	public eu.ehealth.ws_client.xsd.OperationResult deleteAdministrator(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "UpdateSystemParameter", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateSystemParameter")
	@WebMethod(operationName = "UpdateSystemParameter", action = "http://aladdin-project.eu/StorageComponent/UpdateSystemParameter")
	@ResponseWrapper(localName = "UpdateSystemParameterResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateSystemParameterResponse")
	public eu.ehealth.ws_client.xsd.OperationResult updateSystemParameter(
			@WebParam(name = "type", targetNamespace = "") int type,
			@WebParam(name = "value", targetNamespace = "") eu.ehealth.ws_client.xsd.SystemParameter value,
			@WebParam(name = "locale", targetNamespace = "") eu.ehealth.ws_client.xsd.SystemParameter locale);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetSystemParameterList", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetSystemParameterList")
	@WebMethod(operationName = "GetSystemParameterList", action = "http://aladdin-project.eu/StorageComponent/GetSystemParameterList")
	@ResponseWrapper(localName = "GetSystemParameterListResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetSystemParameterListResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.SystemParameter> getSystemParameterList(
			@WebParam(name = "type", targetNamespace = "") int type,
			@WebParam(name = "locale", targetNamespace = "") eu.ehealth.ws_client.xsd.SystemParameter locale);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetPatient", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetPatient")
	@WebMethod(operationName = "GetPatient", action = "http://aladdin-project.eu/StorageComponent/GetPatient")
	@ResponseWrapper(localName = "GetPatientResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetPatientResponse")
	public eu.ehealth.ws_client.xsd.Patient getPatient(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "DeletePatient", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeletePatient")
	@WebMethod(operationName = "DeletePatient", action = "http://aladdin-project.eu/StorageComponent/DeletePatient")
	@ResponseWrapper(localName = "DeletePatientResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeletePatientResponse")
	public eu.ehealth.ws_client.xsd.OperationResult deletePatient(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "AddMediaContent", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.AddMediaContent")
	@WebMethod(operationName = "AddMediaContent", action = "http://aladdin-project.eu/StorageComponent/AddMediaContent")
	@ResponseWrapper(localName = "AddMediaContentResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.AddMediaContentResponse")
	public eu.ehealth.ws_client.xsd.OperationResult addMediaContent(
			@WebParam(name = "in", targetNamespace = "") eu.ehealth.ws_client.xsd.MediaContent in,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "ChangePassword", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ChangePassword")
	@WebMethod(operationName = "ChangePassword", action = "http://aladdin-project.eu/StorageComponent/ChangePassword")
	@ResponseWrapper(localName = "ChangePasswordResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ChangePasswordResponse")
	public eu.ehealth.ws_client.xsd.OperationResult changePassword(
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId,
			@WebParam(name = "password", targetNamespace = "") java.lang.String password,
			@WebParam(name = "reqId", targetNamespace = "") java.lang.String reqId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetQuestionDescription", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetQuestionDescription")
	@WebMethod(operationName = "GetQuestionDescription", action = "http://aladdin-project.eu/StorageComponent/GetQuestionDescription")
	@ResponseWrapper(localName = "GetQuestionDescriptionResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetQuestionDescriptionResponse")
	public eu.ehealth.ws_client.xsd.OperationResult getQuestionDescription(
			@WebParam(name = "questionID", targetNamespace = "") java.lang.String questionID,
			@WebParam(name = "locale", targetNamespace = "") eu.ehealth.ws_client.xsd.SystemParameter locale);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "ListOfClinicians", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfClinicians")
	@WebMethod(operationName = "ListOfClinicians", action = "http://aladdin-project.eu/StorageComponent/ListOfClinicians")
	@ResponseWrapper(localName = "ListOfCliniciansResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfCliniciansResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.ClinicianInfo> listOfClinicians(
			@WebParam(name = "filter", targetNamespace = "") java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> filter,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetMeasurement", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetMeasurement")
	@WebMethod(operationName = "GetMeasurement", action = "http://aladdin-project.eu/StorageComponent/GetMeasurement")
	@ResponseWrapper(localName = "GetMeasurementResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetMeasurementResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.Measurement> getMeasurement(
			@WebParam(name = "filter", targetNamespace = "") java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> filter,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "UpdateUser", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateUser")
	@WebMethod(operationName = "UpdateUser", action = "http://aladdin-project.eu/StorageComponent/UpdateUser")
	@ResponseWrapper(localName = "UpdateUserResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateUserResponse")
	public eu.ehealth.ws_client.xsd.OperationResult updateUser(
			@WebParam(name = "user", targetNamespace = "") eu.ehealth.ws_client.xsd.User user);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "DeleteExternalService", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteExternalService")
	@WebMethod(operationName = "DeleteExternalService", action = "http://aladdin-project.eu/StorageComponent/DeleteExternalService")
	@ResponseWrapper(localName = "DeleteExternalServiceResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteExternalServiceResponse")
	public eu.ehealth.ws_client.xsd.OperationResult deleteExternalService(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "DeleteUser", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteUser")
	@WebMethod(operationName = "DeleteUser", action = "http://aladdin-project.eu/StorageComponent/DeleteUser")
	@ResponseWrapper(localName = "DeleteUserResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteUserResponse")
	public eu.ehealth.ws_client.xsd.OperationResult deleteUser(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetCarerAssessments", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetCarerAssessments")
	@WebMethod(operationName = "GetCarerAssessments", action = "http://aladdin-project.eu/StorageComponent/GetCarerAssessments")
	@ResponseWrapper(localName = "GetCarerAssessmentsResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetCarerAssessmentsResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.CarerAssessment> getCarerAssessments(
			@WebParam(name = "carerId", targetNamespace = "") java.lang.String carerId,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetQuestionnaireAnswersByTask", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetQuestionnaireAnswersByTask")
	@WebMethod(operationName = "GetQuestionnaireAnswersByTask", action = "http://aladdin-project.eu/StorageComponent/GetQuestionnaireAnswersByTask")
	@ResponseWrapper(localName = "GetQuestionnaireAnswersByTaskResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetQuestionnaireAnswersByTaskResponse")
	public eu.ehealth.ws_client.xsd.QuestionnaireAnswers getQuestionnaireAnswersByTask(
			@WebParam(name = "taskId", targetNamespace = "") java.lang.String taskId,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "SavePatientAssessment", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.SavePatientAssessment")
	@WebMethod(operationName = "SavePatientAssessment", action = "http://aladdin-project.eu/StorageComponent/SavePatientAssessment")
	@ResponseWrapper(localName = "SavePatientAssessmentResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.SavePatientAssessmentResponse")
	public eu.ehealth.ws_client.xsd.OperationResult savePatientAssessment(
			@WebParam(name = "assessment", targetNamespace = "") eu.ehealth.ws_client.xsd.PatientAssessment assessment,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetMediaContent", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetMediaContent")
	@WebMethod(operationName = "GetMediaContent", action = "http://aladdin-project.eu/StorageComponent/GetMediaContent")
	@ResponseWrapper(localName = "GetMediaContentResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetMediaContentResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.MediaContent> getMediaContent(
			@WebParam(name = "filter", targetNamespace = "") java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> filter,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "ListOfQuestionnaires", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfQuestionnaires")
	@WebMethod(operationName = "ListOfQuestionnaires", action = "http://aladdin-project.eu/StorageComponent/ListOfQuestionnaires")
	@ResponseWrapper(localName = "ListOfQuestionnairesResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfQuestionnairesResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.QuestionnaireInfo> listOfQuestionnaires(
			@WebParam(name = "locale", targetNamespace = "") eu.ehealth.ws_client.xsd.SystemParameter locale,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "SaveWarning", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.SaveWarning")
	@WebMethod(operationName = "SaveWarning", action = "http://aladdin-project.eu/StorageComponent/SaveWarning")
	@ResponseWrapper(localName = "SaveWarningResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.SaveWarningResponse")
	public eu.ehealth.ws_client.xsd.OperationResult saveWarning(
			@WebParam(name = "warn", targetNamespace = "") eu.ehealth.ws_client.xsd.Warning warn,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "CreateQuestionnaire", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreateQuestionnaire")
	@WebMethod(operationName = "CreateQuestionnaire", action = "http://aladdin-project.eu/StorageComponent/CreateQuestionnaire")
	@ResponseWrapper(localName = "CreateQuestionnaireResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreateQuestionnaireResponse")
	public eu.ehealth.ws_client.xsd.OperationResult createQuestionnaire(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.Questionnaire data,
			@WebParam(name = "locale", targetNamespace = "") eu.ehealth.ws_client.xsd.SystemParameter locale,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "UpdateCarer", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateCarer")
	@WebMethod(operationName = "UpdateCarer", action = "http://aladdin-project.eu/StorageComponent/UpdateCarer")
	@ResponseWrapper(localName = "UpdateCarerResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateCarerResponse")
	public eu.ehealth.ws_client.xsd.OperationResult updateCarer(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.Carer data,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetUserIdByPersonId", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetUserIdByPersonId")
	@WebMethod(operationName = "GetUserIdByPersonId", action = "http://aladdin-project.eu/StorageComponent/GetUserIdByPersonId")
	@ResponseWrapper(localName = "GetUserIdByPersonIdResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetUserIdByPersonIdResponse")
	public eu.ehealth.ws_client.xsd.OperationResult getUserIdByPersonId(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "type", targetNamespace = "") int type,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "UpdateQuestionnaire", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateQuestionnaire")
	@WebMethod(operationName = "UpdateQuestionnaire", action = "http://aladdin-project.eu/StorageComponent/UpdateQuestionnaire")
	@ResponseWrapper(localName = "UpdateQuestionnaireResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateQuestionnaireResponse")
	public eu.ehealth.ws_client.xsd.OperationResult updateQuestionnaire(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.Questionnaire data,
			@WebParam(name = "locale", targetNamespace = "") eu.ehealth.ws_client.xsd.SystemParameter locale,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "ListOfCarers", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfCarers")
	@WebMethod(operationName = "ListOfCarers", action = "http://aladdin-project.eu/StorageComponent/ListOfCarers")
	@ResponseWrapper(localName = "ListOfCarersResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfCarersResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.CarerInfo> listOfCarers(
			@WebParam(name = "filter", targetNamespace = "") java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> filter,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "DeleteClinician", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteClinician")
	@WebMethod(operationName = "DeleteClinician", action = "http://aladdin-project.eu/StorageComponent/DeleteClinician")
	@ResponseWrapper(localName = "DeleteClinicianResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteClinicianResponse")
	public eu.ehealth.ws_client.xsd.OperationResult deleteClinician(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetQuestionnaireAnswers", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetQuestionnaireAnswers")
	@WebMethod(operationName = "GetQuestionnaireAnswers", action = "http://aladdin-project.eu/StorageComponent/GetQuestionnaireAnswers")
	@ResponseWrapper(localName = "GetQuestionnaireAnswersResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetQuestionnaireAnswersResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.QuestionnaireAnswers> getQuestionnaireAnswers(
			@WebParam(name = "ObjectId", targetNamespace = "") java.lang.String objectId,
			@WebParam(name = "fromDate", targetNamespace = "") javax.xml.datatype.XMLGregorianCalendar fromDate,
			@WebParam(name = "toDate", targetNamespace = "") javax.xml.datatype.XMLGregorianCalendar toDate,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "StoreMeasurements", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.StoreMeasurements")
	@WebMethod(operationName = "StoreMeasurements", action = "http://aladdin-project.eu/StorageComponent/StoreMeasurements")
	@ResponseWrapper(localName = "StoreMeasurementsResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.StoreMeasurementsResponse")
	public eu.ehealth.ws_client.xsd.OperationResult storeMeasurements(
			@WebParam(name = "data", targetNamespace = "") java.util.List<eu.ehealth.ws_client.xsd.Measurement> data,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "AssignTasksMassively", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.AssignTasksMassively")
	@WebMethod(operationName = "AssignTasksMassively", action = "http://aladdin-project.eu/StorageComponent/AssignTasksMassively")
	@ResponseWrapper(localName = "AssignTasksMassivelyResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.AssignTasksMassivelyResponse")
	public eu.ehealth.ws_client.xsd.OperationResult assignTasksMassively(
			@WebParam(name = "task", targetNamespace = "") eu.ehealth.ws_client.xsd.Task task,
			@WebParam(name = "startDate", targetNamespace = "") javax.xml.datatype.XMLGregorianCalendar startDate,
			@WebParam(name = "endDate", targetNamespace = "") javax.xml.datatype.XMLGregorianCalendar endDate,
			@WebParam(name = "frequency", targetNamespace = "") java.math.BigInteger frequency,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "getAvailableCarers", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetAvailableCarers")
	@WebMethod(action = "http://aladdin-project.eu/StorageComponent/getAvailableCarers")
	@ResponseWrapper(localName = "getAvailableCarersResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetAvailableCarersResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.CarerInfo> getAvailableCarers(
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "Auth", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.Auth")
	@WebMethod(operationName = "Auth", action = "http://aladdin-project.eu/StorageComponent/Auth")
	@ResponseWrapper(localName = "AuthResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.AuthResponse")
	public eu.ehealth.ws_client.xsd.OperationResult auth(
			@WebParam(name = "login", targetNamespace = "") java.lang.String login,
			@WebParam(name = "password", targetNamespace = "") java.lang.String password,
			@WebParam(name = "token", targetNamespace = "") java.lang.String token);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "DeleteCarerAssessment", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteCarerAssessment")
	@WebMethod(operationName = "DeleteCarerAssessment", action = "http://aladdin-project.eu/StorageComponent/DeleteCarerAssessment")
	@ResponseWrapper(localName = "DeleteCarerAssessmentResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteCarerAssessmentResponse")
	public eu.ehealth.ws_client.xsd.OperationResult deleteCarerAssessment(
			@WebParam(name = "assessmentId", targetNamespace = "") java.lang.String assessmentId,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "CreateClinician", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreateClinician")
	@WebMethod(operationName = "CreateClinician", action = "http://aladdin-project.eu/StorageComponent/CreateClinician")
	@ResponseWrapper(localName = "CreateClinicianResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreateClinicianResponse")
	public eu.ehealth.ws_client.xsd.OperationResult createClinician(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.Clinician data,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "UpdateMediaContent", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateMediaContent")
	@WebMethod(operationName = "UpdateMediaContent", action = "http://aladdin-project.eu/StorageComponent/UpdateMediaContent")
	@ResponseWrapper(localName = "UpdateMediaContentResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateMediaContentResponse")
	public eu.ehealth.ws_client.xsd.OperationResult updateMediaContent(
			@WebParam(name = "ec", targetNamespace = "") eu.ehealth.ws_client.xsd.MediaContent ec,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetPatientsForCaregiver", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetPatientsForCaregiver")
	@WebMethod(operationName = "GetPatientsForCaregiver", action = "http://aladdin-project.eu/StorageComponent/GetPatientsForCaregiver")
	@ResponseWrapper(localName = "GetPatientsForCaregiverResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetPatientsForCaregiverResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.PatientInfo> getPatientsForCaregiver(
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "ListOfPossibleTasks", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfPossibleTasks")
	@WebMethod(operationName = "ListOfPossibleTasks", action = "http://aladdin-project.eu/StorageComponent/ListOfPossibleTasks")
	@ResponseWrapper(localName = "ListOfPossibleTasksResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfPossibleTasksResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.SystemParameter> listOfPossibleTasks(
			@WebParam(name = "userType", targetNamespace = "") int userType);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "StoreQuestionnaireAnswers", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.StoreQuestionnaireAnswers")
	@WebMethod(operationName = "StoreQuestionnaireAnswers", action = "http://aladdin-project.eu/StorageComponent/StoreQuestionnaireAnswers")
	@ResponseWrapper(localName = "StoreQuestionnaireAnswersResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.StoreQuestionnaireAnswersResponse")
	public eu.ehealth.ws_client.xsd.OperationResult storeQuestionnaireAnswers(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.QuestionnaireAnswers data,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetCarer", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetCarer")
	@WebMethod(operationName = "GetCarer", action = "http://aladdin-project.eu/StorageComponent/GetCarer")
	@ResponseWrapper(localName = "GetCarerResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetCarerResponse")
	public eu.ehealth.ws_client.xsd.Carer getCarer(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "ListOfAdministrators", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfAdministrators")
	@WebMethod(operationName = "ListOfAdministrators", action = "http://aladdin-project.eu/StorageComponent/ListOfAdministrators")
	@ResponseWrapper(localName = "ListOfAdministratorsResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfAdministratorsResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.AdministratorInfo> listOfAdministrators(
			@WebParam(name = "filter", targetNamespace = "") java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> filter,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "CreateAdministrator", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreateAdministrator")
	@WebMethod(operationName = "CreateAdministrator", action = "http://aladdin-project.eu/StorageComponent/CreateAdministrator")
	@ResponseWrapper(localName = "CreateAdministratorResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreateAdministratorResponse")
	public eu.ehealth.ws_client.xsd.OperationResult createAdministrator(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.Administrator data,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "UpdateExternalService", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateExternalService")
	@WebMethod(operationName = "UpdateExternalService", action = "http://aladdin-project.eu/StorageComponent/UpdateExternalService")
	@ResponseWrapper(localName = "UpdateExternalServiceResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateExternalServiceResponse")
	public eu.ehealth.ws_client.xsd.OperationResult updateExternalService(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.ExternalService data,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetPatientMeasurement", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetPatientMeasurement")
	@WebMethod(operationName = "GetPatientMeasurement", action = "http://aladdin-project.eu/StorageComponent/GetPatientMeasurement")
	@ResponseWrapper(localName = "GetPatientMeasurementResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetPatientMeasurementResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.Measurement> getPatientMeasurement(
			@WebParam(name = "PatientId", targetNamespace = "") java.lang.String patientId,
			@WebParam(name = "MeasurementType", targetNamespace = "") int measurementType,
			@WebParam(name = "fromData", targetNamespace = "") javax.xml.datatype.XMLGregorianCalendar fromData,
			@WebParam(name = "toData", targetNamespace = "") javax.xml.datatype.XMLGregorianCalendar toData,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "DeleteCarer", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteCarer")
	@WebMethod(operationName = "DeleteCarer", action = "http://aladdin-project.eu/StorageComponent/DeleteCarer")
	@ResponseWrapper(localName = "DeleteCarerResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteCarerResponse")
	public eu.ehealth.ws_client.xsd.OperationResult deleteCarer(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetAllExternalServices", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetAllExternalServices")
	@WebMethod(operationName = "GetAllExternalServices", action = "http://aladdin-project.eu/StorageComponent/GetAllExternalServices")
	@ResponseWrapper(localName = "GetAllExternalServicesResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetAllExternalServicesResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.ExternalService> getAllExternalServices(
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetWarnings", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetWarnings")
	@WebMethod(operationName = "GetWarnings", action = "http://aladdin-project.eu/StorageComponent/GetWarnings")
	@ResponseWrapper(localName = "GetWarningsResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetWarningsResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.Warning> getWarnings(
			@WebParam(name = "warn", targetNamespace = "") java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> warn,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "ListOfPatients", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfPatients")
	@WebMethod(operationName = "ListOfPatients", action = "http://aladdin-project.eu/StorageComponent/ListOfPatients")
	@ResponseWrapper(localName = "ListOfPatientsResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfPatientsResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.PatientInfo> listOfPatients(
			@WebParam(name = "filter", targetNamespace = "") java.util.List<eu.ehealth.ws_client.xsd.SearchCriteria> filter,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "UpdateClinician", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateClinician")
	@WebMethod(operationName = "UpdateClinician", action = "http://aladdin-project.eu/StorageComponent/UpdateClinician")
	@ResponseWrapper(localName = "UpdateClinicianResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateClinicianResponse")
	public eu.ehealth.ws_client.xsd.OperationResult updateClinician(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.Clinician data,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "MarkWarningAsRead", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.MarkWarningAsRead")
	@WebMethod(operationName = "MarkWarningAsRead", action = "http://aladdin-project.eu/StorageComponent/MarkWarningAsRead")
	@ResponseWrapper(localName = "MarkWarningAsReadResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.MarkWarningAsReadResponse")
	public eu.ehealth.ws_client.xsd.OperationResult markWarningAsRead(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "CreateExternalService", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreateExternalService")
	@WebMethod(operationName = "CreateExternalService", action = "http://aladdin-project.eu/StorageComponent/CreateExternalService")
	@ResponseWrapper(localName = "CreateExternalServiceResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreateExternalServiceResponse")
	public eu.ehealth.ws_client.xsd.OperationResult createExternalService(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.ExternalService data,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetUser", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetUser")
	@WebMethod(operationName = "GetUser", action = "http://aladdin-project.eu/StorageComponent/GetUser")
	@ResponseWrapper(localName = "GetUserResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetUserResponse")
	public eu.ehealth.ws_client.xsd.User getUser(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "UpdateAdministrator", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateAdministrator")
	@WebMethod(operationName = "UpdateAdministrator", action = "http://aladdin-project.eu/StorageComponent/UpdateAdministrator")
	@ResponseWrapper(localName = "UpdateAdministratorResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdateAdministratorResponse")
	public eu.ehealth.ws_client.xsd.OperationResult updateAdministrator(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.Administrator data,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetClinician", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetClinician")
	@WebMethod(operationName = "GetClinician", action = "http://aladdin-project.eu/StorageComponent/GetClinician")
	@ResponseWrapper(localName = "GetClinicianResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetClinicianResponse")
	public eu.ehealth.ws_client.xsd.Clinician getClinician(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetTask", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetTask")
	@WebMethod(operationName = "GetTask", action = "http://aladdin-project.eu/StorageComponent/GetTask")
	@ResponseWrapper(localName = "GetTaskResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetTaskResponse")
	public eu.ehealth.ws_client.xsd.Task getTask(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "locale", targetNamespace = "") eu.ehealth.ws_client.xsd.SystemParameter locale,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetUserPlannedTasks", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetUserPlannedTasks")
	@WebMethod(operationName = "GetUserPlannedTasks", action = "http://aladdin-project.eu/StorageComponent/GetUserPlannedTasks")
	@ResponseWrapper(localName = "GetUserPlannedTasksResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetUserPlannedTasksResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.Task> getUserPlannedTasks(
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId,
			@WebParam(name = "fromDate", targetNamespace = "") javax.xml.datatype.XMLGregorianCalendar fromDate,
			@WebParam(name = "toDate", targetNamespace = "") javax.xml.datatype.XMLGregorianCalendar toDate,
			@WebParam(name = "locale", targetNamespace = "") eu.ehealth.ws_client.xsd.SystemParameter locale,
			@WebParam(name = "requesterId", targetNamespace = "") java.lang.String requesterId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "CreateUser", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreateUser")
	@WebMethod(operationName = "CreateUser", action = "http://aladdin-project.eu/StorageComponent/CreateUser")
	@ResponseWrapper(localName = "CreateUserResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreateUserResponse")
	public eu.ehealth.ws_client.xsd.OperationResult createUser(
			@WebParam(name = "user", targetNamespace = "") eu.ehealth.ws_client.xsd.User user);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "AssignTask", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.AssignTask")
	@WebMethod(operationName = "AssignTask", action = "http://aladdin-project.eu/StorageComponent/AssignTask")
	@ResponseWrapper(localName = "AssignTaskResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.AssignTaskResponse")
	public eu.ehealth.ws_client.xsd.OperationResult assignTask(
			@WebParam(name = "task", targetNamespace = "") eu.ehealth.ws_client.xsd.Task task,
			@WebParam(name = "locale", targetNamespace = "") eu.ehealth.ws_client.xsd.SystemParameter locale,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "SaveCarerAssessment", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.SaveCarerAssessment")
	@WebMethod(operationName = "SaveCarerAssessment", action = "http://aladdin-project.eu/StorageComponent/SaveCarerAssessment")
	@ResponseWrapper(localName = "SaveCarerAssessmentResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.SaveCarerAssessmentResponse")
	public eu.ehealth.ws_client.xsd.OperationResult saveCarerAssessment(
			@WebParam(name = "assessment", targetNamespace = "") eu.ehealth.ws_client.xsd.CarerAssessment assessment,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "DeleteMediaContent", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteMediaContent")
	@WebMethod(operationName = "DeleteMediaContent", action = "http://aladdin-project.eu/StorageComponent/DeleteMediaContent")
	@ResponseWrapper(localName = "DeleteMediaContentResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteMediaContentResponse")
	public eu.ehealth.ws_client.xsd.OperationResult deleteMediaContent(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "CreateCarer", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreateCarer")
	@WebMethod(operationName = "CreateCarer", action = "http://aladdin-project.eu/StorageComponent/CreateCarer")
	@ResponseWrapper(localName = "CreateCarerResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreateCarerResponse")
	public eu.ehealth.ws_client.xsd.OperationResult createCarer(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.Carer data,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "RemoveTaskMassively", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.RemoveTaskMassively")
	@WebMethod(operationName = "RemoveTaskMassively", action = "http://aladdin-project.eu/StorageComponent/RemoveTaskMassively")
	@ResponseWrapper(localName = "RemoveTaskMassivelyResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.RemoveTaskMassivelyResponse")
	public eu.ehealth.ws_client.xsd.OperationResult removeTaskMassively(
			@WebParam(name = "patientId", targetNamespace = "") java.lang.String patientId,
			@WebParam(name = "typeOfTask", targetNamespace = "") java.math.BigInteger typeOfTask,
			@WebParam(name = "startDate", targetNamespace = "") javax.xml.datatype.XMLGregorianCalendar startDate,
			@WebParam(name = "endDate", targetNamespace = "") javax.xml.datatype.XMLGregorianCalendar endDate,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetAdministrator", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetAdministrator")
	@WebMethod(operationName = "GetAdministrator", action = "http://aladdin-project.eu/StorageComponent/GetAdministrator")
	@ResponseWrapper(localName = "GetAdministratorResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetAdministratorResponse")
	public eu.ehealth.ws_client.xsd.Administrator getAdministrator(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "ListOfSupportedLocales", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfSupportedLocales")
	@WebMethod(operationName = "ListOfSupportedLocales", action = "http://aladdin-project.eu/StorageComponent/ListOfSupportedLocales")
	@ResponseWrapper(localName = "ListOfSupportedLocalesResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ListOfSupportedLocalesResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.SystemParameter> listOfSupportedLocales();


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "ChangeTaskStatus", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ChangeTaskStatus")
	@WebMethod(operationName = "ChangeTaskStatus", action = "http://aladdin-project.eu/StorageComponent/ChangeTaskStatus")
	@ResponseWrapper(localName = "ChangeTaskStatusResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.ChangeTaskStatusResponse")
	public eu.ehealth.ws_client.xsd.OperationResult changeTaskStatus(
			@WebParam(name = "TaskId", targetNamespace = "") int taskId,
			@WebParam(name = "TaskStatus", targetNamespace = "") int taskStatus,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetQuestionnaire", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetQuestionnaire")
	@WebMethod(operationName = "GetQuestionnaire", action = "http://aladdin-project.eu/StorageComponent/GetQuestionnaire")
	@ResponseWrapper(localName = "GetQuestionnaireResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetQuestionnaireResponse")
	public eu.ehealth.ws_client.xsd.Questionnaire getQuestionnaire(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "locale", targetNamespace = "") eu.ehealth.ws_client.xsd.SystemParameter locale,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "GetPatientAssessments", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetPatientAssessments")
	@WebMethod(operationName = "GetPatientAssessments", action = "http://aladdin-project.eu/StorageComponent/GetPatientAssessments")
	@ResponseWrapper(localName = "GetPatientAssessmentsResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetPatientAssessmentsResponse")
	public java.util.List<eu.ehealth.ws_client.xsd.PatientAssessment> getPatientAssessments(
			@WebParam(name = "patientId", targetNamespace = "") java.lang.String patientId,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "UpdatePatient", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdatePatient")
	@WebMethod(operationName = "UpdatePatient", action = "http://aladdin-project.eu/StorageComponent/UpdatePatient")
	@ResponseWrapper(localName = "UpdatePatientResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.UpdatePatientResponse")
	public eu.ehealth.ws_client.xsd.OperationResult updatePatient(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.Patient data,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "getQuestionnaireAnswerValue", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetQuestionnaireAnswerValue")
	@WebMethod(action = "http://aladdin-project.eu/StorageComponent/getQuestionnaireAnswerValue")
	@ResponseWrapper(localName = "getQuestionnaireAnswerValueResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.GetQuestionnaireAnswerValueResponse")
	public java.lang.String getQuestionnaireAnswerValue(
			@WebParam(name = "questionId", targetNamespace = "") java.lang.String questionId,
			@WebParam(name = "value", targetNamespace = "") java.lang.String value,
			@WebParam(name = "locale", targetNamespace = "") eu.ehealth.ws_client.xsd.SystemParameter locale);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "DeletePatientAssessment", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeletePatientAssessment")
	@WebMethod(operationName = "DeletePatientAssessment", action = "http://aladdin-project.eu/StorageComponent/DeletePatientAssessment")
	@ResponseWrapper(localName = "DeletePatientAssessmentResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeletePatientAssessmentResponse")
	public eu.ehealth.ws_client.xsd.OperationResult deletePatientAssessment(
			@WebParam(name = "assessmentId", targetNamespace = "") java.lang.String assessmentId,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "DeleteQuestionnaire", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteQuestionnaire")
	@WebMethod(operationName = "DeleteQuestionnaire", action = "http://aladdin-project.eu/StorageComponent/DeleteQuestionnaire")
	@ResponseWrapper(localName = "DeleteQuestionnaireResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.DeleteQuestionnaireResponse")
	public eu.ehealth.ws_client.xsd.OperationResult deleteQuestionnaire(
			@WebParam(name = "id", targetNamespace = "") java.lang.String id,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);


	@WebResult(name = "out", targetNamespace = "")
	@RequestWrapper(localName = "CreatePatient", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreatePatient")
	@WebMethod(operationName = "CreatePatient", action = "http://aladdin-project.eu/StorageComponent/CreatePatient")
	@ResponseWrapper(localName = "CreatePatientResponse", targetNamespace = "http://aladdin-project.eu/StorageComponent/", className = "eu.aladdin_project.storagecomponent.CreatePatientResponse")
	public eu.ehealth.ws_client.xsd.OperationResult createPatient(
			@WebParam(name = "data", targetNamespace = "") eu.ehealth.ws_client.xsd.Patient data,
			@WebParam(name = "userId", targetNamespace = "") java.lang.String userId);
}
