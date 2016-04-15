package eu.ehealth;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.datatype.XMLGregorianCalendar;
import org.hibernate.Session;
import eu.ehealth.db.DbQueries;
import eu.ehealth.db.HibernateUtil;
import eu.ehealth.db.wservices.authentication.Auth;
import eu.ehealth.db.wservices.authentication.ChangePassword;
import eu.ehealth.db.wservices.authentication.GetUserType;
import eu.ehealth.db.wservices.authentication.LockUser;
import eu.ehealth.db.wservices.authentication.Lockout;
import eu.ehealth.db.wservices.common.GetSystemParameterList;
import eu.ehealth.db.wservices.common.ListOfSupportedLocales;
import eu.ehealth.db.wservices.common.UpdateSystemParameter;
import eu.ehealth.db.wservices.configuration.GetAppConfig;
import eu.ehealth.db.wservices.configuration.UpdateAppConfigRow;
import eu.ehealth.db.wservices.externalservices.CreateExternalService;
import eu.ehealth.db.wservices.externalservices.DeleteExternalService;
import eu.ehealth.db.wservices.externalservices.GetAllExternalServices;
import eu.ehealth.db.wservices.externalservices.UpdateExternalService;
import eu.ehealth.db.wservices.measurements.CreateRule;
import eu.ehealth.db.wservices.measurements.DeleteCarerAssessment;
import eu.ehealth.db.wservices.measurements.DeletePatientAssessment;
import eu.ehealth.db.wservices.measurements.GetCarerAssessments;
import eu.ehealth.db.wservices.measurements.GetMeasurement;
import eu.ehealth.db.wservices.measurements.GetPatientAssessments;
import eu.ehealth.db.wservices.measurements.GetPatientMeasurement;
import eu.ehealth.db.wservices.measurements.SaveCarerAssessment;
import eu.ehealth.db.wservices.measurements.SavePatientAssesment;
import eu.ehealth.db.wservices.measurements.StoreMeasurements;
import eu.ehealth.db.wservices.measurements.UpdateRule;
import eu.ehealth.db.wservices.mediacontent.AddMediaContent;
import eu.ehealth.db.wservices.mediacontent.DeleteMediaContent;
import eu.ehealth.db.wservices.mediacontent.GetMediaContent;
import eu.ehealth.db.wservices.mediacontent.UpdateMediaContent;
import eu.ehealth.db.wservices.questionnaires.CreateQuestionnaire;
import eu.ehealth.db.wservices.questionnaires.DeleteQuestionnaire;
import eu.ehealth.db.wservices.questionnaires.GetQuestionDescription;
import eu.ehealth.db.wservices.questionnaires.GetQuestionnaire;
import eu.ehealth.db.wservices.questionnaires.GetQuestionnaireAnswerValue;
import eu.ehealth.db.wservices.questionnaires.GetQuestionnaireAnswers;
import eu.ehealth.db.wservices.questionnaires.GetQuestionnaireAnswersByTask;
import eu.ehealth.db.wservices.questionnaires.ListOfQuestionnaires;
import eu.ehealth.db.wservices.questionnaires.StoreQuestionnaireAnswers;
import eu.ehealth.db.wservices.questionnaires.UpdateQuestionnaire;
import eu.ehealth.db.wservices.tasks.AssignTask;
import eu.ehealth.db.wservices.tasks.AssignTasksMassively;
import eu.ehealth.db.wservices.tasks.ChangeTaskStatus;
import eu.ehealth.db.wservices.tasks.GetTask;
import eu.ehealth.db.wservices.tasks.GetUserPlannedTasks;
import eu.ehealth.db.wservices.tasks.ListOfPossibleTasks;
import eu.ehealth.db.wservices.tasks.RemoveTaskMassively;
import eu.ehealth.db.wservices.users.CreateAdministrator;
import eu.ehealth.db.wservices.users.CreateCarer;
import eu.ehealth.db.wservices.users.CreateClinician;
import eu.ehealth.db.wservices.users.CreatePatient;
import eu.ehealth.db.wservices.users.CreateUser;
import eu.ehealth.db.wservices.users.DeleteAdministrator;
import eu.ehealth.db.wservices.users.DeleteCarer;
import eu.ehealth.db.wservices.users.DeleteClinician;
import eu.ehealth.db.wservices.users.DeletePatient;
import eu.ehealth.db.wservices.users.DeleteUser;
import eu.ehealth.db.wservices.users.GetAdministrator;
import eu.ehealth.db.wservices.users.GetAvailableCarers;
import eu.ehealth.db.wservices.users.GetCarer;
import eu.ehealth.db.wservices.users.GetClinician;
import eu.ehealth.db.wservices.users.GetPatient;
import eu.ehealth.db.wservices.users.GetPatientsForCaregiver;
import eu.ehealth.db.wservices.users.GetUser;
import eu.ehealth.db.wservices.users.GetUserIdByAdminId;
import eu.ehealth.db.wservices.users.GetUserIdByCarerId;
import eu.ehealth.db.wservices.users.GetUserIdByClinicianId;
import eu.ehealth.db.wservices.users.GetUserIdByPatientId;
import eu.ehealth.db.wservices.users.GetUserIdByPersonId;
import eu.ehealth.db.wservices.users.GetUserPersonIdByPatientId;
import eu.ehealth.db.wservices.users.ListOfAdministrators;
import eu.ehealth.db.wservices.users.ListOfCarers;
import eu.ehealth.db.wservices.users.ListOfClinicians;
import eu.ehealth.db.wservices.users.ListOfPatients;
import eu.ehealth.db.wservices.users.ListOfPatientsFromDoctor;
import eu.ehealth.db.wservices.users.ListOfUsers;
import eu.ehealth.db.wservices.users.RegisterUser;
import eu.ehealth.db.wservices.users.UpdateAdministrator;
import eu.ehealth.db.wservices.users.UpdateCarer;
import eu.ehealth.db.wservices.users.UpdateClinician;
import eu.ehealth.db.wservices.users.UpdatePatient;
import eu.ehealth.db.wservices.users.UpdateUser;
import eu.ehealth.db.wservices.warnings.GetWarningsPatients;
import eu.ehealth.db.wservices.warnings.MarkWarningAsRead;
import eu.ehealth.db.wservices.warnings.SaveWarning;
import eu.ehealth.db.xsd.Administrator;
import eu.ehealth.db.xsd.AdministratorInfo;
import eu.ehealth.db.xsd.Carer;
import eu.ehealth.db.xsd.CarerAssessment;
import eu.ehealth.db.xsd.CarerInfo;
import eu.ehealth.db.xsd.Clinician;
import eu.ehealth.db.xsd.ClinicianInfo;
import eu.ehealth.db.xsd.ExternalService;
import eu.ehealth.db.xsd.Measurement;
import eu.ehealth.db.xsd.MediaContent;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.Patient;
import eu.ehealth.db.xsd.PatientAssessment;
import eu.ehealth.db.xsd.PatientInfo;
import eu.ehealth.db.xsd.Questionnaire;
import eu.ehealth.db.xsd.QuestionnaireAnswers;
import eu.ehealth.db.xsd.QuestionnaireInfo;
import eu.ehealth.db.xsd.Rules;
import eu.ehealth.db.xsd.SearchCriteria;
import eu.ehealth.db.xsd.SystemParameter;
import eu.ehealth.db.xsd.Task;
import eu.ehealth.db.xsd.User;
import eu.ehealth.db.xsd.Warning;
import eu.ehealth.server.context.WSServletContextListener;
import eu.ehealth.util.Utilities;


/**
 * 
 * @author a572832
 * 
 */
@WebService
public class StorageComponentImpl implements StorageComponent
{


	/**
	 * Instance of the session
	 */
	private Session session;

	
	/**
	 * Default constructor
	 */
	public StorageComponentImpl()
	{
		SystemDictionary.webguiLog("DEBUG", "Initializing session (database) object ...");
		
		if (!SystemDictionary.STORAGE_COMPONENT.initialize()) {
			SystemDictionary.webguiLog("ERROR", "Storage Component NOT initialized: session is null");
		}
		
		session = HibernateUtil.getSessionFactory().openSession();
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// WEB SERVICES
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// EXTERNAL SERVICES ///////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public OperationResult createExternalService(ExternalService data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new CreateExternalService(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult updateExternalService(ExternalService data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new UpdateExternalService(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult deleteExternalService(String idv, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(idv);
		lParams.add(userId);
		
		return new DeleteExternalService(session).execute(lParams);
	}
	
	
	@Override
	public List<ExternalService> getAllExternalServices(String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(1);
		lParams.add(userId);
		
		return new GetAllExternalServices(session).execute(lParams);
	}
	
	
	// MEDIA CONTENT ///////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public OperationResult addMediaContent(MediaContent in, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(in);
		lParams.add(userId);
		
		return new AddMediaContent(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult updateMediaContent(MediaContent rEC, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(rEC);
		lParams.add(userId);
		
		return new UpdateMediaContent(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult deleteMediaContent(String idv, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(idv);
		lParams.add(userId);
		
		return new DeleteMediaContent(session).execute(lParams);
	}
	

	@Override
	public List<MediaContent> getMediaContent(List<SearchCriteria> filter, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(filter);
		lParams.add(userId);
		
		return new GetMediaContent(session).execute(lParams);
	}


	// QUESTIONNAIRES WEB SERVICES /////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public String getQuestionnaireAnswerValue(String questionIdv, String valuev, SystemParameter locale)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(3);
		lParams.add(questionIdv);
		lParams.add(valuev);
		lParams.add(locale);
		
		return new GetQuestionnaireAnswerValue(session).execute(lParams);
	}
	
	
	@Override
	public List<QuestionnaireInfo> listOfQuestionnaires(SystemParameter locale, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(locale);
		lParams.add(userId);
		
		return new ListOfQuestionnaires(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult getQuestionDescription(String questionID, SystemParameter locale)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(questionID);
		lParams.add(locale);
		
		return new GetQuestionDescription(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult deleteQuestionnaire(String idv, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(idv);
		lParams.add(userId);
		
		return new DeleteQuestionnaire(session).execute(lParams);
	}
	
	
	@Override
	public Questionnaire getQuestionnaire(String idv, SystemParameter locale, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(3);
		lParams.add(idv);
		lParams.add(locale);
		lParams.add(userId);
		
		return new GetQuestionnaire(session).execute(lParams);
	}
	
	
	@Override
	public QuestionnaireAnswers getQuestionnaireAnswersByTask(String taskIdv, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(taskIdv);
		lParams.add(userId);
		
		return new GetQuestionnaireAnswersByTask(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult storeQuestionnaireAnswers(QuestionnaireAnswers data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new StoreQuestionnaireAnswers(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult createQuestionnaire(Questionnaire data, SystemParameter locale, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(3);
		lParams.add(data);
		lParams.add(locale);
		lParams.add(userId);
		
		return new CreateQuestionnaire(session).execute(lParams);
	}


	@Override
	public OperationResult updateQuestionnaire(Questionnaire data, SystemParameter locale, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(3);
		lParams.add(data);
		lParams.add(locale);
		lParams.add(userId);
		
		return new UpdateQuestionnaire(session).execute(lParams);
	}


	@Override
	public List<QuestionnaireAnswers> getQuestionnaireAnswers(String objectId,
				XMLGregorianCalendar fromDate, XMLGregorianCalendar toDate, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(4);
		lParams.add(objectId);
		lParams.add(fromDate);
		lParams.add(toDate);
		lParams.add(userId);
		
		return new GetQuestionnaireAnswers(session).execute(lParams);
	}
	
	
	// WARNINGS ////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public List<Warning> getWarnings(List<SearchCriteria> warn, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(warn);
		lParams.add(userId);
		
		return new GetWarningsPatients(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult saveWarning(Warning rwarn, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(rwarn);
		lParams.add(userId);
		
		return new SaveWarning(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult markWarningAsRead(String idv, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(idv);
		lParams.add(userId);
		
		return new MarkWarningAsRead(session).execute(lParams);
	}
	
	
	// OTHERS //////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public OperationResult updateSystemParameter(int typev, SystemParameter value, SystemParameter locale)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(3);
		lParams.add(new Integer(typev));
		lParams.add(value);
		lParams.add(locale);
		
		return new UpdateSystemParameter(session).execute(lParams);
	}
	
	
	@Override
	public List<SystemParameter> getSystemParameterList(int typev, SystemParameter locale)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(new Integer(typev));
		lParams.add(locale);
		
		return new GetSystemParameterList(session).execute(lParams);
	}
	
	
	@Override
	public List<SystemParameter> listOfSupportedLocales()
	{
		return new ListOfSupportedLocales(session).execute(null);
	}
	
	
	@Override
	public List<String[]> getEhealthRoles()
	{
		try
		{
			DbQueries db = new DbQueries();
			return db.getEhealthRoles();
		}
		catch (Exception ex)
		{
			SystemDictionary.logException(ex);

			return null;
		}
	}


	@Override
	public List<String[]> getEhealthParameters()
	{
		try
		{
			DbQueries db = new DbQueries();
			return db.getEhealthParameters();
		}
		catch (Exception ex)
		{
			SystemDictionary.logException(ex);

			return null;
		}
	}
	
	
	// TASKS ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public OperationResult assignTask(Task taskv, SystemParameter locale, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(3);
		lParams.add(taskv);
		lParams.add(locale);
		lParams.add(userId);
		
		return new AssignTask(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult assignTasksMassively(Task rtask,	XMLGregorianCalendar startDate, XMLGregorianCalendar endDate, BigInteger frequencyv, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(5);
		lParams.add(rtask);
		lParams.add(startDate);
		lParams.add(endDate);
		lParams.add(frequencyv);
		lParams.add(userId);
		
		return new AssignTasksMassively(session).execute(lParams);
	}
	
	
	@Override
	public List<SystemParameter> listOfPossibleTasks(int userType)
	{
		ArrayList<Integer> lParams = new ArrayList<Integer>(1);
		lParams.add(new Integer(userType));
		
		return new ListOfPossibleTasks(session).execute(lParams);
	}
	
	
	@Override
	public Task getTask(String id, SystemParameter locale, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(3);
		lParams.add(id);
		lParams.add(locale);
		lParams.add(userId);
		
		return new GetTask(session).execute(lParams);
	}


	@Override
	public List<Task> getUserPlannedTasks(String userIdv, XMLGregorianCalendar fromDate, XMLGregorianCalendar toDate,
								SystemParameter locale, String requesterId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(5);
		lParams.add(userIdv);
		lParams.add(fromDate);
		lParams.add(toDate);
		lParams.add(locale);
		lParams.add(requesterId);
		
		return new GetUserPlannedTasks(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult changeTaskStatus(int taskId, int taskStatus, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(3);
		lParams.add(new Integer(taskId));
		lParams.add(new Integer(taskStatus));
		lParams.add(userId);
		
		return new ChangeTaskStatus(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult removeTaskMassively(String patientIdv, BigInteger typeOfTask, XMLGregorianCalendar startDate,
												XMLGregorianCalendar endDate, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(5);
		lParams.add(patientIdv);
		lParams.add(typeOfTask);
		lParams.add(startDate);
		lParams.add(endDate);
		lParams.add(userId);
		
		return new RemoveTaskMassively(session).execute(lParams);
	}
	
	
	// MEASUREMENTS / ASSESMENTS ///////////////////////////////////////////////////////////////////////////////////////	
	
	@Override
	public List<PatientAssessment> getPatientAssessments(String patientIdv, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(patientIdv);
		lParams.add(userId);
		
		return new GetPatientAssessments(session).execute(lParams);
	}
	

	@Override
	public List<Measurement> getPatientMeasurement(String patientIdv, int measurementTypev, XMLGregorianCalendar fromData, XMLGregorianCalendar toData, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(5);
		lParams.add(patientIdv);
		lParams.add(new Integer(measurementTypev));
		lParams.add(fromData);
		lParams.add(toData);
		lParams.add(userId);
		
		return new GetPatientMeasurement(session).execute(lParams);
	}
	
	
	@Override
	public List<Measurement> getMeasurement(List<SearchCriteria> filter, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(filter);
		lParams.add(userId);
		
		return new GetMeasurement(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult savePatientAssessment(PatientAssessment assessment, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(assessment);
		lParams.add(userId);
		
		return new SavePatientAssesment(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult storeMeasurements(List<Measurement> data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new StoreMeasurements(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult deletePatientAssessment(String assessmentIdv, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(assessmentIdv);
		lParams.add(userId);
		
		return new DeletePatientAssessment(session).execute(lParams);
	}
	
	
	@Override
	public List<CarerAssessment> getCarerAssessments(String carerIdv, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(carerIdv);
		lParams.add(userId);
		
		return new GetCarerAssessments(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult deleteCarerAssessment(String assessmentIdv, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(assessmentIdv);
		lParams.add(userId);
		
		return new DeleteCarerAssessment(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult saveCarerAssessment(CarerAssessment assessment, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(assessment);
		lParams.add(userId);
		
		return new SaveCarerAssessment(session).execute(lParams);
	}

	
	// LOGIN / CREATE / UPDATE / DELETE USERS WEB SERVICES /////////////////////////////////////////////////////////////
	
	@Override
	public OperationResult createUser(User user)
	{
		ArrayList<User> lParams = new ArrayList<User>(1);
		lParams.add(user);
		
		return new CreateUser(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult updateUser(User user)
	{
		ArrayList<User> lParams = new ArrayList<User>(1);
		lParams.add(user);
		
		return new UpdateUser(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult deleteUser(String idv)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(1);
		lParams.add(idv);
		
		return new DeleteUser(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult getUserIdByPersonId(String idv, int typev, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(3);
		lParams.add(idv);
		lParams.add(new Integer(typev));
		lParams.add(userId);
		
		return new GetUserIdByPersonId(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult getUserIdByCarerId(String id, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(id);
		lParams.add(userId);
		
		return new GetUserIdByCarerId(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult getUserIdByPatientId(String id, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(id);
		lParams.add(userId);
		
		return new GetUserIdByPatientId(session).execute(lParams);
	}

	
	@Override
	public OperationResult getUserPersonIdByPatientId(String id, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(id);
		lParams.add(userId);
		
		return new GetUserPersonIdByPatientId(session).execute(lParams);
	}
	
	
	@Override
	public User getUser(String idv)
	{
		ArrayList<String> lParams = new ArrayList<String>(1);
		lParams.add(idv);
		
		return new GetUser(session).execute(lParams);
	}

	
	// ADMINISTRATOR
	
	@Override
	public OperationResult createAdministrator(Administrator data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new CreateAdministrator(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult updateAdministrator(Administrator data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new UpdateAdministrator(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult deleteAdministrator(String idv, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(idv);
		lParams.add(userId);
		
		return new DeleteAdministrator(session).execute(lParams);
	}
	
	
	@Override
	public List<AdministratorInfo> listOfAdministrators(List<SearchCriteria> filter, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(filter);
		lParams.add(userId);
		
		return new ListOfAdministrators(session).execute(lParams);
	}
	
	
	@Override
	public Administrator getAdministrator(String idv, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(idv);
		lParams.add(userId);
		
		return new GetAdministrator(session).execute(lParams);
	}
	
	
	// PATIENT
	
	@Override
	public OperationResult createPatient(Patient data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new CreatePatient(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult updatePatient(Patient data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new UpdatePatient(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult deletePatient(String idv, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(idv);
		lParams.add(userId);
		
		return new DeletePatient(session).execute(lParams);
	}
	
	
	@Override
	public List<PatientInfo> listOfPatients(List<SearchCriteria> filter, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(filter);
		lParams.add(userId);
		
		return new ListOfPatients(session).execute(lParams);
	}
	
	
	@Override
	public List<PatientInfo> listOfPatientsFromDoctor(List<SearchCriteria> filter, String userId, String clinicianId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(filter);
		lParams.add(userId);
		lParams.add(clinicianId);
		
		return new ListOfPatientsFromDoctor(session).execute(lParams);
	}
	
	
	@Override
	public List<PatientInfo> getPatientsForCaregiver(String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(1);
		lParams.add(userId);
		
		return new GetPatientsForCaregiver(session).execute(lParams);
	}
	
	
	@Override
	public Patient getPatient(String idv, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(idv);
		lParams.add(userId);
		
		return new GetPatient(session).execute(lParams);
	}
	
	
	// CAREGIVER
	
	@Override
	public OperationResult createCarer(Carer data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new CreateCarer(session).execute(lParams);
	}

	
	@Override
	public OperationResult updateCarer(Carer data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new UpdateCarer(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult deleteCarer(String idv, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(idv);
		lParams.add(userId);
		
		return new DeleteCarer(session).execute(lParams);
	}
	
	
	@Override
	public List<CarerInfo> listOfCarers(List<SearchCriteria> filter, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(filter);
		lParams.add(userId);
		
		return new ListOfCarers(session).execute(lParams);
	}
	
	
	@Override
	public List<CarerInfo> getAvailableCarers(String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(1);
		lParams.add(userId);
		
		return new GetAvailableCarers(session).execute(lParams);
	}
	
	
	@Override
	public Carer getCarer(String idv, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(idv);
		lParams.add(userId);
		
		return new GetCarer(session).execute(lParams);
	}
	
	
	// CLINICIAN
	
	@Override
	public OperationResult createClinician(Clinician data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new CreateClinician(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult updateClinician(Clinician data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new UpdateClinician(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult deleteClinician(String idv, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(idv);
		lParams.add(userId);
		
		return new DeleteClinician(session).execute(lParams);
	}
	
	
	@Override
	public List<ClinicianInfo> listOfClinicians(List<SearchCriteria> filter, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(filter);
		lParams.add(userId);
		
		return new ListOfClinicians(session).execute(lParams);
	}

	
	@Override
	public Clinician getClinician(String idv, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(idv);
		lParams.add(userId);
		
		return new GetClinician(session).execute(lParams);
	}
	
	
	// AUTHENTICATION //////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public OperationResult auth(String login, String password, String token)
	{
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(login);
		lParams.add(password);
		
		return new Auth(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult changePassword(String userId, String password, String reqId)
	{
		ArrayList<String> lParams = new ArrayList<String>(3);
		lParams.add(userId);
		lParams.add(password);
		lParams.add(reqId);
		
		return new ChangePassword(session).execute(lParams);
	}
	
	
	@Override
	public OperationResult getUserType(String idv)
	{
		ArrayList<String> lParams = new ArrayList<String>(1);
		lParams.add(idv);
		
		return new GetUserType(session).execute(lParams);
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// NEW CUMULUS SERVICES
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String[] registerUser(String userName, String password, String userId)
	{
		ArrayList<String> lParams = new ArrayList<String>(3);
		lParams.add(userName);
		lParams.add(password);
		lParams.add(userId);
		
		return new RegisterUser(session).execute(lParams);
	}


	@Override
	public String[] login(String userName, String password)
	{
		OperationResult res = auth(userName, password, "");
		
		if (Utilities.NumericFunctions.int_isGreaterThan(res.getCode(), 0) == 1) {
			String[] response = new String[2];
			response[0] = res.getCode();
			response[1] = "User authenticated succesfully";
			
			return response;
		}
		
		return ResponseCode.getStringFromCode(res.getCode());
	}
	

	@Override
	public String[] lockout(String userName)
	{
		ArrayList<String> lParams = new ArrayList<String>(1);
		lParams.add(userName);
		
		return new Lockout(session).execute(lParams);
	}
	
	
	
	/**
	 * 
	 * @author a572832
	 *
	 */
	public enum ResponseCode {
		
		// > 0 ... all is ok, it's a valid user
		// others:
		INVALID_USER 			("0", 	"Invalid user - incorrect password"),
		DATABASE_ERROR_1 		("-2", 	"Database error (-2) : HibernateException"),
		DATABASE_ERROR_2 		("-4", 	"Database error (-4)"),
		USER_ALREADY_EXISTS		("-5", 	"Username already exists"),
	    USER_LOCKED 			("-10", "User locked - contact with administrator"),
	    INVALID_PASSWORD 		("-11", "Invalid password"),
	    UNKNOWN_ERROR 			("-12", "Unknown error while processing an action"),
	    PERMISSION_ERROR 		("-13", "Permission denied"),
	    INVALID_PASSWORD_FORMAT ("-14", "Invalid password value : letters 'a-zA-Z' and at least one digit 0-9. Length 8-20"),
	    RESPONSE_OK 			("-99", "Operation executed succesfully");

	    
	    private final String code;       
	    private final String desc;  
	    private static final Map<String, ResponseCode> table = new HashMap<String, ResponseCode>();

	    
	    static {
	    	for (ResponseCode d : ResponseCode.values())
            {
            	table.put(d.getCode(), d);
            }
        }
	    
	    
	    private ResponseCode(String s, String txt) 
	    {
	    	code = s;
	    	desc = txt;
	    }

	    
	    public boolean equalsName(String otherName)
	    {
	        return (otherName == null)? false : code.equals(otherName);
	    }

	    
	    public String toString()
	    {
	    	return code + " : " + desc + " ";
	    }
	    
	    
	    public String[] toStringArray()
	    {
	    	return toStringArray("");
	    }
	    
	    
	    public String[] toStringArray(String txtDesc)
	    {
	    	String[] response = new String[2];
			response[0] = code;
			response[1] = desc + txtDesc;
			
	    	return response;
	    }
	    
	    
	    public static String[] getStringFromCode(String vcode)
	    {
	    	String[] response = new String[2];
			response[0] = vcode;
			response[1] = table.get(vcode).getDesc();
			
	    	return response;
	    }
	    
	    
	    public String getCode()
	    {
	       return code;
	    }
	    
	    
	    public String getDesc()
	    {
	       return desc;
	    }
	    
	    
	    public int getValue()
	    {
	       return Integer.parseInt(code);
	    }
		
	}



	@Override
	public void logout(String idUser, String sessionId) {
		WSServletContextListener.removeUser(idUser, sessionId);
	}


	@Override
	public Object[] getConnectedUsers(String idUser) {
		return WSServletContextListener.getConnectedUsers(idUser);
	}


	@Override
	public OperationResult getUserIdByClinicianId(String id, String userId) {
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(id);
		lParams.add(userId);
		
		return new GetUserIdByClinicianId(session).execute(lParams);
	}


	@Override
	public OperationResult getUserIdByAdminId(String id, String userId) {
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(id);
		lParams.add(userId);
		
		return new GetUserIdByAdminId(session).execute(lParams);
	}


	@Override
	public List<String[]> getAppConfig(String userId) {
		ArrayList<String> lParams = new ArrayList<String>(1);
		lParams.add(userId);
		
		return new GetAppConfig(session).execute(lParams);
	}


	@Override
	public List<String[]> listOfUsers(String userId) {
		ArrayList<String> lParams = new ArrayList<String>(1);
		lParams.add(userId);
		
		return new ListOfUsers(session).execute(lParams);
	}


	@Override
	public OperationResult lockUser(String userId, int id, boolean locked) {
		ArrayList<String> lParams = new ArrayList<String>(3);
		lParams.add(userId);
		lParams.add("" + id);
		lParams.add(Boolean.toString(locked));
		
		return new LockUser(session).execute(lParams);
	}


	@Override
	public OperationResult updateAppConfigRow(String userId, int id, String value, boolean active) {
		ArrayList<String> lParams = new ArrayList<String>(4);
		lParams.add(userId);
		lParams.add("" + id);
		lParams.add(value);
		lParams.add(Boolean.toString(active));
		
		return new UpdateAppConfigRow(session).execute(lParams);
	}


	@Override
	public String[] getStatus()
	{
		try
		{
			String res[] = new String[6];
			
			res[0] = "DATABASE_URL_MYDB: " + System.getProperty("DATABASE_URL_MYDB");
			res[1] = "DATABASE_USERNAME_MYDB: " + System.getProperty("DATABASE_USERNAME_MYDB");
			res[2] = "DATABASE_PASSWORD_MYDB: " + System.getProperty("DATABASE_PASSWORD_MYDB");
			res[3] = "DATABASE_INITIALIZED: " + SystemDictionary.DATABASE_INITIALIZED;
			res[4] = "DATABASE_RUNNING: " + SystemDictionary.getInitializedValue();
			res[5] = "off";
			
			if (SystemDictionary.DATABASE_INITIALIZED && SystemDictionary.getInitializedValue()) 
			{
				res[5] = "running";
			}
			
			return res;
		}
		catch (Exception e) {
			return null;
		}
	}


	@Override
	public String initDB(String id)
	{
		SystemDictionary.webguiLog("INFO", "... DATABASE_URL_MYDB : " + System.getProperty("DATABASE_URL_MYDB"));
		SystemDictionary.webguiLog("INFO", "... DATABASE_USERNAME_MYDB : " + System.getProperty("DATABASE_USERNAME_MYDB"));
		SystemDictionary.webguiLog("INFO", "... DATABASE_PASSWORD_MYDB : " + System.getProperty("DATABASE_PASSWORD_MYDB"));
		
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mydb");
			SystemDictionary.webguiLog("INFO", ds.getConnection().toString());
		}
		catch (Exception ex) {
			SystemDictionary.logException(ex);
		}
		
		if (SystemDictionary.initialize()) 
		{
			return "Database initialized";
		}
		
		return "Database NOT initialized";
	}
	
	
	@Override
	public OperationResult createRules(Rules data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new CreateRule(session).execute(lParams);
	}


	@Override
	public OperationResult updateRules(Rules data, String userId)
	{
		ArrayList<Object> lParams = new ArrayList<Object>(2);
		lParams.add(data);
		lParams.add(userId);
		
		return new UpdateRule(session).execute(lParams);
	}


}
