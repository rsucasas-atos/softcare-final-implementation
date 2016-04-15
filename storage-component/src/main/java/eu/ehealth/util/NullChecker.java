package eu.ehealth.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.AddressList;
import eu.ehealth.db.xsd.Administrator;
import eu.ehealth.db.xsd.Carer;
import eu.ehealth.db.xsd.CarerAssessment;
import eu.ehealth.db.xsd.Clinician;
import eu.ehealth.db.xsd.CommunicationList;
import eu.ehealth.db.xsd.ExternalService;
import eu.ehealth.db.xsd.IdentifierList;
import eu.ehealth.db.xsd.Measurement;
import eu.ehealth.db.xsd.Patient;
import eu.ehealth.db.xsd.PatientAssessment;
import eu.ehealth.db.xsd.PersonData;
import eu.ehealth.db.xsd.Questionnaire;
import eu.ehealth.db.xsd.QuestionnaireAnswer;
import eu.ehealth.db.xsd.QuestionnaireAnswers;
import eu.ehealth.db.xsd.QuestionnaireQuestion;
import eu.ehealth.db.xsd.QuestionnaireQuestionAnswer;
import eu.ehealth.db.xsd.QuestionnaireQuestionAnswerList;
import eu.ehealth.db.xsd.QuestionnaireQuestionList;
import eu.ehealth.db.xsd.Rules;
import eu.ehealth.db.xsd.SearchCriteria;
import eu.ehealth.db.xsd.SocioDemographicData;
import eu.ehealth.db.xsd.SystemParameter;
import eu.ehealth.db.xsd.Task;
import eu.ehealth.db.xsd.User;
import eu.ehealth.db.xsd.Warning;


/**
 * Helper class for checking null fields of object.
 * 
 * @author Andrey Baboshin
 * 
 */
public class NullChecker
{


	/**
	 * Check object for null values
	 * 
	 * @param <T> type of the object
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public <T> T check(T v, Class<T> t)
	{
		if (v == null)
		{
			try
			{
				return t.newInstance();
			}
			catch (java.lang.InstantiationException e)
			{
				StorageComponentMain.scLog("ERROR", e.toString());
			}
			catch (java.lang.IllegalAccessException e)
			{
				StorageComponentMain.scLog("ERROR", e.toString());
			}
		}
		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public Integer check(Integer v, Class<Integer> t)
	{
		if (v == null)
		{
			return new Integer(0);
		}
		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public Calendar check(Calendar v, Class<Calendar> t)
	{
		if (v == null)
			v = Calendar.getInstance();

		return v;
	}
	
	
	/**
	 * 
	 * @param v
	 * @param t
	 * @return
	 */
	public XMLGregorianCalendar check(XMLGregorianCalendar v, Class<XMLGregorianCalendar> t)
	{
		if (v == null) {
			GregorianCalendar gcal = new GregorianCalendar();
			try {
				v = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
			}
			catch (Exception ex) {}
		}

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public AddressList check(AddressList v, Class<AddressList> t)
	{
		if (v == null)
			v = new AddressList();

		for (int i = 0; i < v.getAddress().size(); i++)
		{
			v.getAddress().get(i).setCity(this.check(v.getAddress().get(i).getCity(), String.class));
			v.getAddress().get(i).setCounty(this.check(v.getAddress().get(i).getCounty(), String.class));
			v.getAddress().get(i).setCountry(this.check(v.getAddress().get(i).getCountry(), String.class));
			v.getAddress().get(i).setNotes(this.check(v.getAddress().get(i).getNotes(), String.class));
			v.getAddress().get(i).setStreet(this.check(v.getAddress().get(i).getStreet(), String.class));
			v.getAddress().get(i).setStreetNo(this.check(v.getAddress().get(i).getStreetNo(), String.class));
			v.getAddress().get(i).setZipCode(this.check(v.getAddress().get(i).getZipCode(), String.class));
		}

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public CommunicationList check(CommunicationList v,
			Class<CommunicationList> t)
	{
		if (v == null)
			v = new CommunicationList();

		for (int i = 0; i < v.getCommunication().size(); i++)
		{
			v.getCommunication().get(i).setNotes(this.check(v.getCommunication().get(i).getNotes(), String.class));
			v.getCommunication().get(i).setType(this.check(v.getCommunication().get(i).getType(), String.class));
			v.getCommunication().get(i).setValue(this.check(v.getCommunication().get(i).getValue(), String.class));
		}

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public IdentifierList check(IdentifierList v, Class<IdentifierList> t)
	{
		if (v == null)
			v = new IdentifierList();

		for (int i = 0; i < v.getIdentifier().size(); i++)
		{
			v.getIdentifier().get(i).setIssueAuthority(this.check(v.getIdentifier().get(i).getIssueAuthority(), String.class));
			v.getIdentifier().get(i).setNumber(this.check(v.getIdentifier().get(i).getNumber(), String.class));
			v.getIdentifier().get(i).setType(this.check(v.getIdentifier().get(i).getType(), String.class));
			v.getIdentifier().get(i).setIssueDate(this.check(v.getIdentifier().get(i).getIssueDate(), XMLGregorianCalendar.class));
		}

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public PersonData check(PersonData v, Class<PersonData> t)
	{
		if (v == null)
			v = new PersonData();

		v.setAddressList(this.check(v.getAddressList(), AddressList.class));
		v.setCommunicationList(this.check(v.getCommunicationList(),
				CommunicationList.class));
		v.setIdentifierList(this.check(v.getIdentifierList(),
				IdentifierList.class));

		v.setName(this.check(v.getName(), String.class));
		v.setSurname(this.check(v.getSurname(), String.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public Clinician check(Clinician v, Class<Clinician> t)
	{
		if (v == null)
			v = new Clinician();

		v.setID(this.check(v.getID(), String.class));
		v.setPersonData(this.check(v.getPersonData(), PersonData.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public SystemParameter check(SystemParameter v, Class<SystemParameter> t)
	{
		if (v == null)
			v = new SystemParameter();

		v.setCode(this.check(v.getCode(), String.class));
		v.setDescription(this.check(v.getDescription(), String.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public SocioDemographicData check(SocioDemographicData v,
			Class<SocioDemographicData> t)
	{
		if (v == null)
			v = new SocioDemographicData();

		v.setGender(this.check(v.getGender(), SystemParameter.class));
		v.setLivingWith(this.check(v.getLivingWith(), SystemParameter.class));
		v.setMaritalStatus(this.check(v.getMaritalStatus(), SystemParameter.class));
		v.setHeight(this.check(v.getHeight(), SystemParameter.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public Carer check(Carer v, Class<Carer> t)
	{
		if (v == null)
			v = new Carer();

		v.setPersonData(this.check(v.getPersonData(), PersonData.class));
		v.setID(this.check(v.getID(), String.class));
		v.setSDData(this.check(v.getSDData(), SocioDemographicData.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public Patient check(Patient v, Class<Patient> t)
	{
		if (v == null)
			v = new Patient();

		v.setID(this.check(v.getID(), String.class));
		v.setPersonData(this.check(v.getPersonData(), PersonData.class));
		v.setResponsibleClinicianID(this.check(v.getResponsibleClinicianID(), String.class));
		v.setSDData(this.check(v.getSDData(), SocioDemographicData.class));

		return v;
	}
	
	
	/**
	 * 
	 * @param v
	 * @param t
	 * @return
	 */
	public Rules check(Rules v, Class<Rules> t)
	{
		if (v == null)
		{
			v = new Rules();
			
			v.setId(this.check(v.getId(), String.class));
			v.setDataType(this.check(v.getDataType(), Integer.class));
			v.setCallerID(this.check(v.getCallerID(), Integer.class));
		}

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public QuestionnaireQuestionList check(QuestionnaireQuestionList v, Class<QuestionnaireQuestionList> t)
	{
		if (v == null)
			v = new QuestionnaireQuestionList();

		for (int i = 0; i < v.getQuestion().size(); i++)
		{
			v.getQuestion().set(i, this.check(v.getQuestion().get(i), QuestionnaireQuestion.class));
		}

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public QuestionnaireQuestionAnswer check(QuestionnaireQuestionAnswer v,
			Class<QuestionnaireQuestionAnswer> t)
	{
		if (v == null)
			v = new QuestionnaireQuestionAnswer();

		v.setDescription(this.check(v.getDescription(), String.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public QuestionnaireQuestionAnswerList check(QuestionnaireQuestionAnswerList v, Class<QuestionnaireQuestionAnswerList> t)
	{
		if (v == null)
			v = new QuestionnaireQuestionAnswerList();

		for (int i = 0; i < v.getAnswer().size(); i++)
		{
			v.getAnswer().set(i, this.check(v.getAnswer().get(i), QuestionnaireQuestionAnswer.class));
		}

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public QuestionnaireQuestion check(QuestionnaireQuestion v, Class<QuestionnaireQuestion> t)
	{
		if (v == null)
			v = new QuestionnaireQuestion();

		v.setId(this.check(v.getId(), String.class));
		v.setTitle(this.check(v.getTitle(), String.class));
		v.setType(this.check(v.getType(), String.class));
		v.setQuestions(this.check(v.getQuestions(), QuestionnaireQuestionList.class));
		v.setAnswers(this.check(v.getAnswers(), QuestionnaireQuestionAnswerList.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public Questionnaire check(Questionnaire v, Class<Questionnaire> t)
	{
		if (v == null)
			v = new Questionnaire();

		v.setID(this.check(v.getID(), String.class));
		v.setTitle(this.check(v.getTitle(), String.class));

		for (int i = 0; i < v.getQuestion().size(); i++)
		{
			v.getQuestion().set(i, this.check(v.getQuestion().get(i), QuestionnaireQuestion.class));
		}

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public Warning check(Warning v, Class<Warning> t)
	{
		if (v == null)
			v = new Warning();

		v.setDateTimeOfWarning(this.check(v.getDateTimeOfWarning(), XMLGregorianCalendar.class));
		v.setEffect(this.check(v.getEffect(), SystemParameter.class));
		v.setEmergencyLevel(this.check(v.getEmergencyLevel(), SystemParameter.class));
		v.setID(this.check(v.getID(), String.class));
		v.setIndicator(this.check(v.getIndicator(), SystemParameter.class));
		v.setJustificationText(this.check(v.getJustificationText(), String.class));
		v.setPatient(this.check(v.getPatient(), Patient.class));
		v.setRiskLevel(this.check(v.getRiskLevel(), SystemParameter.class));
		v.setTypeOfWarning(this.check(v.getTypeOfWarning(), SystemParameter.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public SearchCriteria check(SearchCriteria v, Class<SearchCriteria> t)
	{
		if (v == null)
			v = new SearchCriteria();

		v.setCompareOp(this.check(v.getCompareOp(), SystemParameter.class));
		v.setFieldName(this.check(v.getFieldName(), String.class));
		v.setFieldValue1(this.check(v.getFieldValue1(), String.class));
		v.setFieldValue2(this.check(v.getFieldValue2(), String.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public Measurement check(Measurement v, Class<Measurement> t)
	{
		if (v == null)
			v = new Measurement();

		v.setDateTime(this.check(v.getDateTime(), XMLGregorianCalendar.class));
		v.setTaskID(this.check(v.getTaskID(), String.class));
		v.setType(this.check(v.getType(), SystemParameter.class));
		v.setUnits(this.check(v.getUnits(), String.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public PatientAssessment check(PatientAssessment v, Class<PatientAssessment> t)
	{
		if (v == null)
			v = new PatientAssessment();

		v.setAetology(this.check(v.getAetology(), SystemParameter.class));
		v.setDateOfAssessment(this.check(v.getDateOfAssessment(), XMLGregorianCalendar.class));
		v.setID(this.check(v.getID(), String.class));
		v.setPatientID(this.check(v.getPatientID(), String.class));
		v.setPharmacologicalTreatment(this.check(v.getPharmacologicalTreatment(), String.class));
		v.setRelevantPathologyAntecedents(this.check(v.getRelevantPathologyAntecedents(), String.class));

		for (int i = 0; i < v.getClinicalData().size(); i++)
		{
			v.getClinicalData().set(i, this.check(v.getClinicalData().get(i), Measurement.class));
		}

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public Task check(Task v, Class<Task> t)
	{
		if (v == null)
			v = new Task();

		v.setAssignerID(this.check(v.getAssignerID(), String.class));
		v.setDateTimeAssigned(this.check(v.getDateTimeAssigned(), XMLGregorianCalendar.class));
		v.setDateTimeFulfilled(this.check(v.getDateTimeAssigned(), XMLGregorianCalendar.class));
		v.setExecutorID(this.check(v.getExecutorID(), String.class));
		v.setID(this.check(v.getID(), String.class));
		v.setObjectID(this.check(v.getObjectID(), String.class));
		v.setQuestionnaire(this.check(v.getQuestionnaire(), Questionnaire.class));
		v.setTaskStatus(this.check(v.getTaskStatus(), SystemParameter.class));
		v.setTaskType(this.check(v.getTaskType(), SystemParameter.class));
		v.setURL(this.check(v.getURL(), String.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public ExternalService check(ExternalService v, Class<ExternalService> t)
	{
		if (v == null)
			v = new ExternalService();

		v.setAddress(this.check(v.getAddress(), String.class));
		v.setDescription(this.check(v.getDescription(), String.class));
		v.setID(this.check(v.getID(), String.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public CarerAssessment check(CarerAssessment v, Class<CarerAssessment> t)
	{
		if (v == null)
			v = new CarerAssessment();

		v.setCarerID(this.check(v.getCarerID(), String.class));
		v.setClinicianID(this.check(v.getClinicianID(), String.class));
		v.setDateOfAssessment(this.check(v.getDateOfAssessment(), XMLGregorianCalendar.class));
		v.setEmotionalOrMentalDisorders(this.check(v.getEmotionalOrMentalDisorders(), String.class));
		v.setID(this.check(v.getID(), String.class));
		v.setPsychoactiveDrugs(this.check(v.getPsychoactiveDrugs(), String.class));
		v.setRelevantHealthProblem(this.check(v.getRelevantHealthProblem(),
				String.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public Administrator check(Administrator v, Class<Administrator> t)
	{
		if (v == null)
			v = new Administrator();

		v.setID(this.check(v.getID(), String.class));
		v.setPersonData(this.check(v.getPersonData(), PersonData.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public QuestionnaireAnswer check(QuestionnaireAnswer v, Class<QuestionnaireAnswer> t)
	{
		if (v == null)
			v = new QuestionnaireAnswer();

		v.setQuestionID(this.check(v.getQuestionID(), String.class));
		v.setValue(this.check(v.getValue(), String.class));
		v.setValue1(this.check(v.getValue1(), String.class));
		v.setValue(this.check(v.getValue(), String.class));

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v object to be checked
	 * @param t class
	 * @return checked object
	 */
	public QuestionnaireAnswers check(QuestionnaireAnswers v, Class<QuestionnaireAnswers> t)
	{
		if (v == null)
			v = new QuestionnaireAnswers();

		v.setDateTime(this.check(v.getDateTime(), XMLGregorianCalendar.class));
		v.setID(this.check(v.getID(), String.class));
		v.setObjectID(this.check(v.getObjectID(), String.class));
		v.setUserID(this.check(v.getUserID(), String.class));

		for (int i = 0; i < v.getAnswer().size(); i++)
		{
			v.getAnswer().set(i, this.check(v.getAnswer().get(i), QuestionnaireAnswer.class));
		}

		return v;
	}


	/**
	 * Check object for null values
	 * 
	 * @param v
	 *            object to be checked
	 * @param t
	 *            class
	 * @return checked object
	 */
	public User check(User v, Class<User> t)
	{
		if (v == null)
			v = new User();

		v.setID(this.check(v.getID(), String.class));
		v.setPassword(this.check(v.getPassword(), String.class));
		v.setPersonID(this.check(v.getPersonID(), String.class));
		v.setType(this.check(v.getType(), SystemParameter.class));
		v.setUsername(this.check(v.getUsername(), String.class));

		return v;
	}

}
