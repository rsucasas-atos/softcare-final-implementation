package eu.ehealth.dataprocessing;

import java.io.FileReader;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeFactory;
import org.hibernate.Session;
import com.google.gson.Gson;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.db.AladdinUser;
import eu.ehealth.db.wservices.common.Translations;
import eu.ehealth.db.wservices.users.GetFromPCCATablesIdByPersonId;
import eu.ehealth.db.xsd.Measurement;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.QuestionnaireAnswer;
import eu.ehealth.db.xsd.QuestionnaireAnswers;
import eu.ehealth.db.xsd.SystemParameter;


/**
 * 
 * @author
 *
 */
public class DataProcessing
{
	
	
	/**
	 * MAP of rules
	 */
	private static HashMap<String, DataProcessingRule> RULES_MAP;
	// Instance of the session
	private Session _session;
	
	// rules
	private static final int LessThanRuleType = 1;
	private static final int DoubleCompareRuleType = 2;
	private static final int GreaterThanRuleType = 3;
	
	// types of analysis
	private static final int MeasurementAnalysis = 1;
	private static final int QuestionnaireAnalysis = 2;
	
	// questionnaires
	private QuestionnaireAnswer[] previousAnswerArray;
	
	
	/**
	 * 
	 * @param s
	 */
	public DataProcessing(Session s)
	{
		_session = s;
	}
	
	
	/**
	 * 
	 * @return
	 */
	private HashMap<String, DataProcessingRule> getRulesFromJson() 
	{
        try 
        {
        	ClassLoader loader = this.getClass().getClassLoader();
        	String filePath = loader.getResource(StorageComponentMain.RULES_FILE).getFile();
        	String encodeType = StorageComponentMain.ENCODING; //SystemDictionary.CONFIG_PROPERTIES.getString("encoding");
        	filePath = URLDecoder.decode(filePath, encodeType);
        	
        	if (filePath == null)
        	{
        		StorageComponentMain.scLog("ERROR", "Rules file path is NULL");
        		return null;
        	}
        	else
        	{
        		StorageComponentMain.scLog("DEBUG", "Rules file path : " + filePath + "");
            	
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<ArrayList<DataProcessingRule>>(){}.getType();
                Gson gson = new Gson();
                // json content to ArrayList
                ArrayList<DataProcessingRule> lres = gson.fromJson(new FileReader(filePath), type);
                
                // create Hashmap
                HashMap<String, DataProcessingRule> pMap = new HashMap<>();
                for (DataProcessingRule dr : lres) 
                {
                    pMap.put(dr.getId(), dr);
                }

                return pMap;
        	}
        }
        catch (Exception ex) 
        {
        	StorageComponentMain.logException(ex);
            return null;
        }
    }
	
	
	/**
	 * 
	 * @param patientPersonId
	 * @param m
	 * @return
	 */
	public void analyzeMeasurement(Integer patientPersonId, Measurement m)
	{
		try
		{
			// DEBUG
			StorageComponentMain.scLog("DEBUG", "Analyzing measurement from patient '" + patientPersonId + "' ...");
			
			// rules
			if (RULES_MAP == null)
				RULES_MAP = getRulesFromJson();

			// patient's measurement type
			String measurementType = m.getType().getCode();
			String measurementDescription = "";
			String typeMeasurement = "";

			if (measurementType.equals("2"))
			{
				measurementDescription = "Weight";
				typeMeasurement = measurementType;
			}
			else if (measurementType.equals("11"))
			{
				measurementDescription = "Systolic Blood Pressure";
				typeMeasurement = Globals.ServerAppValue_BloodPressure;
			}
			else if (measurementType.equals("12"))
			{
				measurementDescription = "Diastolic Blood Pressure";
				typeMeasurement = Globals.ServerAppValue_BloodPressure;
			}
			else
			{
				typeMeasurement = measurementType;
				StorageComponentMain.scLog("WARN", "'typeMeasurement' assigned to default value");
			}
			
			// DEBUG
			StorageComponentMain.scLog("DEBUG", "Measurement type  : " + measurementDescription);
			StorageComponentMain.scLog("DEBUG", "Measurement value : " + m.getValue());
			
			DataProcessingRule currentRule = null;
			for (DataProcessingRule dpr : RULES_MAP.values()) 
			{
				if (measurementType.equals(dpr.getDataType() + ""))
				{
					currentRule = dpr;
					break;
				}
			}

			if (currentRule == null) // Rule not found
			{
				StorageComponentMain.scLog("WARN", "Rule not found");
				return;
			}
			
			StorageComponentMain.scLog("DEBUG", "Using rule CallerID: [" + currentRule.getCallerID() + "]   dataType: [" + currentRule.getDataType() + "]");

			// get id from patient ('id' field)
			GetFromPCCATablesIdByPersonId getId = new GetFromPCCATablesIdByPersonId();
			ArrayList<String> lo = new ArrayList<String>(2);
			lo.add(patientPersonId.toString());
			lo.add("patient");
			
			OperationResult res = getId.execute(_session, lo);
			if ( Integer.parseInt(res.getCode()) > 0 )
			{
				Integer patientId = Integer.parseInt(res.getCode());
				
				StorageComponentMain.scLog("DEBUG", "patient Id: 		'" + patientId + "'");
				StorageComponentMain.scLog("DEBUG", "patient personId: 	'" + patientPersonId + "'");
				
				eu.ehealth.db.db.Warning generatedWarning = null;
				
				switch (currentRule.getCallerID())
				{
					// Weight
					case LessThanRuleType:
						StorageComponentMain.scLog("DEBUG", "Weight .... Rule name : LessThanRuleType");
						Calendar currentDate = Calendar.getInstance();
						currentDate.add(Calendar.DATE, -1);
						Calendar oneWeekBefore = (Calendar) Calendar.getInstance();
						oneWeekBefore.add(Calendar.DATE, -8);

						// look for measurements during the last week
						List<eu.ehealth.db.db.Measurement> measurements = getPatientMeasurementX(patientPersonId, oneWeekBefore, currentDate, typeMeasurement);
						if (measurements.size() < 1)
						{
							StorageComponentMain.scLog("WARN", "No measurements found during the last week");
							return;
						}

						Collections.sort(measurements, Date_Order);

						// LessThanRule(patientID, description, Current, Previous, Threshold, TypeOfAnalysis)
						generatedWarning = LessThanRule(patientId, measurementDescription, m.getValue(), 
								measurements.get(measurements.size() - 1).getValue().floatValue(), 
								currentRule.getUpperLimit(), MeasurementAnalysis);
						break;
						
					// Blood pressure: Systolic & Diastolic
					case DoubleCompareRuleType:
						StorageComponentMain.scLog("DEBUG", "Blood pressure: Systolic & Diastolic .... Rule name : DoubleCompareRuleType");
						generatedWarning = DoubleCompareRule(patientId, measurementDescription, 
								m.getValue(), currentRule.getUpperLimit(), 
								currentRule.getLowerLimit(), MeasurementAnalysis);
						break;
				}

				if (generatedWarning != null)
				{
					StorageComponentMain.scLog("DEBUG", "Creating warning ...");
					_session.save(generatedWarning);
				}
			}
			else 
			{
				// ERROR
				StorageComponentMain.scLog("ERROR", "Error getting id from patient");
			}
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
		}
	}
	
	
	/**
	 * 
	 * @param currentAnswerArray
	 * @param ObjectUserID
	 */
	public void analyzeQuestionnaires(QuestionnaireAnswer[] currentAnswerArray, Integer ObjectUserID)
	{
		try
		{
			// DEBUG
			StorageComponentMain.scLog("DEBUG", "Analyzing questionnaire from patient '" + ObjectUserID.intValue() + "' ...");
			
			// rules
			if (RULES_MAP == null)
				RULES_MAP = getRulesFromJson();

			eu.ehealth.db.db.Warning generatedWarning = null;
			eu.ehealth.db.db.AladdinUser user = (AladdinUser) _session.load(eu.ehealth.db.db.AladdinUser.class, ObjectUserID);

			// get all questionnaire answers
			Calendar currentDate = Calendar.getInstance();
			currentDate.add(Calendar.DATE, -1);
			Calendar twoMonthsBefore = Calendar.getInstance();
			twoMonthsBefore.add(Calendar.DATE, -60);

			List<QuestionnaireAnswers> gQuestAnsResp = getQuestionnaireAnswers(twoMonthsBefore, currentDate, ObjectUserID); // qDocument,
			if (gQuestAnsResp.size() == 0)
			{
				return;
			}
			QuestionnaireAnswers[] qanswers = gQuestAnsResp.toArray(new QuestionnaireAnswers[gQuestAnsResp.size()]);

			ArrayList<QuestionnaireAnswers> SortedAnswers = new ArrayList<QuestionnaireAnswers>(Arrays.asList(qanswers));
			Collections.sort(SortedAnswers, QDate_Order);

			QuestionnaireAnswers previousQuestionnaireAnswers = SortedAnswers.get(SortedAnswers.size() - 1);
			previousAnswerArray = previousQuestionnaireAnswers.getAnswer()
					.toArray(new QuestionnaireAnswer[previousQuestionnaireAnswers.getAnswer().size()]);

			double previousScore = 0;
			double currentScore = 0;

			for (int j = 0; j < previousAnswerArray.length; j++)
			{
				String globalID = previousAnswerArray[j].getGlobalID();
				StorageComponentMain.scLog("DEBUG", "AAA Reading previous answer GlobalID = " + globalID);
				String globalIDGroup = getglobalIDGroup(globalID);
				double globalIDGroupAsDouble = -1;

				try
				{
					globalIDGroupAsDouble = Double.valueOf(globalIDGroup);
				}
				catch (Exception e)
				{
					StorageComponentMain.logException(e);
				}

				if (globalIDGroupAsDouble == -1)
					continue;

				if (globalIDGroupAsDouble == 4000)
				{
					double previousScoreAsDouble = Double.valueOf(previousAnswerArray[j].getValue());
					previousScore += previousScoreAsDouble;
				}
			}

			for (int j = 0; j < currentAnswerArray.length; j++)
			{
				String globalID = currentAnswerArray[j].getGlobalID();
				StorageComponentMain.scLog("DEBUG", "AAA Reading current answer GlobalID = " + globalID);
				String globalIDGroup = getglobalIDGroup(globalID);
				double globalIDGroupAsDouble = -1;
				try
				{
					globalIDGroupAsDouble = Double.valueOf(globalIDGroup);
				}
				catch (Exception e)
				{
					StorageComponentMain.logException(e);
				}
				if (globalIDGroupAsDouble == -1)
					continue;

				if (globalIDGroupAsDouble == 4000)
				{
					double currentScoreAsDouble = Double.valueOf(currentAnswerArray[j].getValue());
					currentScore += currentScoreAsDouble;
				}
			}

			for (int k = 0; k < currentAnswerArray.length; k++)
			{
				QuestionnaireAnswer currentAnswer = currentAnswerArray[k];
				QuestionnaireAnswer previousAnswer = getPreviousQuestionnaireAnswer(currentAnswer.getQuestionID());

				// For non-existant answers, create a new answer with "Never"
				if (previousAnswer == null)
				{
					QuestionnaireAnswer neverAnswer = new QuestionnaireAnswer();
					neverAnswer.setQuestionID(currentAnswer.getQuestionID());
					neverAnswer.setGlobalID(currentAnswer.getGlobalID());
					neverAnswer.setValue("0");
					previousAnswer = neverAnswer;
				}

				String currentValueStr = currentAnswer.getValue();
				System.out.println("AAA Reading current value = " + currentValueStr);
				String previousValueStr = previousAnswer.getValue();
				StorageComponentMain.scLog("DEBUG", "AAA Reading previous value = " + previousValueStr);

				if ("9".equals(currentValueStr) || "9".equals(previousValueStr))
					continue;

				double currentValue;
				double previousValue;

				try
				{
					currentValue = Double.valueOf(currentValueStr);
					previousValue = Double.valueOf(previousValueStr);
				}
				catch (Exception ex)
				{
					continue;
				}

				String globaID = currentAnswer.getGlobalID();

				if (globaID == null || "".equals(globaID))
					continue;

				int globalIDasInteger = Integer.valueOf(globaID);
				if (globalIDasInteger < 1000)
					continue;

				String globalIDGroup = getglobalIDGroup(globaID);
				if (globalIDGroup == null)
					continue;

				double globalIDGroupAsDouble = Double.valueOf(globalIDGroup);
				if (globalIDGroupAsDouble == 4000)
					continue;

				DataProcessingRule currentRule = null;
				for (DataProcessingRule dpr : RULES_MAP.values()) 
				{
					String ruleDataType = dpr.getDataType() + "";
					double ruleDataTypeAsDouble = Double.valueOf(ruleDataType);
					if (globalIDGroupAsDouble == ruleDataTypeAsDouble)
					{
						currentRule = dpr;
						break;
					}
				}

				if (currentRule == null)
				{ 
					// Rule not found
					StorageComponentMain.scLog("WARN", "rule not found");
					return;
				}

				SystemParameter locale = new SystemParameter();
				locale.setCode("en_UK");

				Translations languageTrans = new Translations(_session);
				String questionDescription = languageTrans.getTranslate("questionnairequestion", new Integer(currentAnswer.getQuestionID()), locale, "");

				String description = String.format("Question '%s' changed from '%s' to '%s'", questionDescription
								.replaceAll("\n", ""), GetAnswerDescription(globalIDGroupAsDouble, previousValue), GetAnswerDescription(globalIDGroupAsDouble, currentValue));

				StorageComponentMain.scLog("DEBUG", "currentRule.getCallerID() " + currentRule.getCallerID());
				switch (currentRule.getCallerID())
				{
					case GreaterThanRuleType:
						StorageComponentMain.scLog("DEBUG", "GreaterThanRuleType");
						generatedWarning = GreaterThanRule(user.getPersonId(), description, currentValue, previousValue, 
								currentRule.getLowerLimit(), QuestionnaireAnalysis);
						break;
					case LessThanRuleType:
						StorageComponentMain.scLog("DEBUG", "LessThanRuleType");
						generatedWarning = LessThanRule(user.getPersonId(), description, currentValue, previousValue, 
								currentRule.getLowerLimit(), QuestionnaireAnalysis);
						break;
				}

				if (generatedWarning != null)
				{
					StorageComponentMain.scLog("WARN", "generatedWarning");
					_session.save(generatedWarning);
				}
			}

			if (currentScore > 0)
			{
				StorageComponentMain.scLog("DEBUG", "currentScore > 0");

				String description = String.format("Change in Zarit Burden Interview from '%s' to '%s'", previousScore, currentScore);

				DataProcessingRule currentRule = null;
				for (DataProcessingRule dpr : RULES_MAP.values()) 
				{
					String ruleDataType = dpr.getDataType() + "";
					double ruleDataTypeAsDouble = Double.valueOf(ruleDataType);
					if (ruleDataTypeAsDouble == 4000)
					{
						currentRule = dpr;
						break;
					}
				}

				generatedWarning = CategoryChangeRule(user.getPersonId(), description, currentScore, previousScore, currentRule
						.getLowerLimit(), currentRule.getUpperLimit(), QuestionnaireAnalysis);

				if (generatedWarning != null)
				{
					StorageComponentMain.scLog("WARN", "generatedWarning");
					_session.save(generatedWarning);
				}
			}
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
		}
	}
	
	
	/**
	 * Create Comparator for Sorting dates
	 */
	private static final Comparator<eu.ehealth.db.db.Measurement> Date_Order = new Comparator<eu.ehealth.db.db.Measurement>() {

		public int compare(eu.ehealth.db.db.Measurement a,
				eu.ehealth.db.db.Measurement b)
		{
			return a.getDatetime().compareTo(b.getDatetime());
		}
		
	};
	
	
	/**
	 * Create Comparator for Sorting questionnaires
	 */
	private static final Comparator<QuestionnaireAnswers> QDate_Order = new Comparator<QuestionnaireAnswers>() {

		public int compare(QuestionnaireAnswers a, QuestionnaireAnswers b)
		{
			return a.getDateTime().toGregorianCalendar().compareTo(b.getDateTime().toGregorianCalendar());
		}
		
	};
	
	
	/**
	 * returns the Global ID group (e.g. 1000, 2000, 3000 etc) based on GlobalID
	 *  
	 * @param globalID
	 * @return
	 */
	private String getglobalIDGroup(String globalID)
	{
		try
		{
			int globalIDInt = Integer.valueOf(globalID);
			double res = java.lang.Math.floor(globalIDInt / 1000) * 1000;
			return String.valueOf(res);
		}
		catch (Exception ex)
		{
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param QuestionID
	 * @return
	 */
	private QuestionnaireAnswer getPreviousQuestionnaireAnswer(String QuestionID)
	{
		for (int i = 0; i < previousAnswerArray.length; i++)
		{
			if (previousAnswerArray[i].getQuestionID().equals(QuestionID))
				return previousAnswerArray[i];
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param globalIDGroupAsDouble
	 * @param value
	 * @return
	 */
	private String GetAnswerDescription(double globalIDGroupAsDouble, double value)
	{
		if (globalIDGroupAsDouble == 1000 || globalIDGroupAsDouble == 3000)
		{
			if (value == 0)
				return "YES";
			else if (value == 1)
				return "NO";
		}

		if (globalIDGroupAsDouble == 2000)
		{
			if (value == 0)
				return "Never";
			else if (value == 1)
				return "Happened but not in the last week";
			else if (value == 2)
				return "1 or 2 times in the last week";
			else if (value == 3)
				return "From 3 to 6 times in the last week";
			else if (value == 4)
				return "Everyday";
		}

		return "";
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// RULES
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Rule 1 - Less Than rule
	 * 
	 * @param patientID
	 * @param description
	 * @param Current
	 * @param Previous
	 * @param Threshold
	 * @param TypeOfAnalysis
	 * @return
	 */
	protected static eu.ehealth.db.db.Warning LessThanRule(Integer patientID,
			String description, double Current, double Previous, double Threshold, int TypeOfAnalysis)
	{
		StorageComponentMain.scLog("DEBUG", "LessThanRule : Current " + Current);
		StorageComponentMain.scLog("DEBUG", "LessThanRule : Previous " + Previous);
		StorageComponentMain.scLog("DEBUG", "LessThanRule : Threshold " + Threshold);
		
		if (Current <= Previous - Threshold) {
			StorageComponentMain.scLog("DEBUG", "LessThanRule : Current <= Previous - Threshold? " + (Current <= Previous - Threshold));
			
			return GenerateWarning(patientID, description, Current, Previous, TypeOfAnalysis, "");
		}
		else if (Current >= Previous + Threshold) {
			StorageComponentMain.scLog("DEBUG", "LessThanRule : Current >= Previous + Threshold? " + (Current >= Previous + Threshold));
			
			return GenerateWarning(patientID, description, Current, Previous, TypeOfAnalysis, "");
		}
		return null;
	}
	
	
	/**
	 *  Rule 2 - Double Compare rule
	 *  
	 * @param patientID
	 * @param description
	 * @param Current
	 * @param Upper
	 * @param Lower
	 * @param TypeOfAnalysis
	 * @return
	 */
	protected static eu.ehealth.db.db.Warning DoubleCompareRule(Integer patientID, String description, double Current,
			double Upper, double Lower, int TypeOfAnalysis)
	{
		if ((Current > Upper || Current < Lower)) 
		{
			String txt = String.format("-%s- [Current value = %s, Upper limit = %s, Lower limit = %s]", description, Current, Upper, Lower);
			return GenerateWarning(patientID, description, Current, 0.0, TypeOfAnalysis, txt);
		}
		return null;
	}
	
	
	/**
	 * Rule 3 - Greater Than Rule
	 * 
	 * @param patientID
	 * @param description
	 * @param Current
	 * @param Previous
	 * @param Threshold
	 * @param TypeOfAnalysis
	 * @return
	 */
	protected static eu.ehealth.db.db.Warning GreaterThanRule(Integer patientID,
			String description, double Current, double Previous, double Threshold, int TypeOfAnalysis)
	{
		if (Current >= Previous + Threshold) 
		{
			return GenerateWarning(patientID, description, Current, Previous, TypeOfAnalysis, "");
		}
		return null;
	}
	
	
	/**
	 * Rule 4 - Category Change Rule
	 * 
	 * @param patientID
	 * @param description
	 * @param Current
	 * @param Previous
	 * @param LowerThreshold
	 * @param UpperThreshold
	 * @param TypeOfAnalysis
	 * @return
	 */
	private static eu.ehealth.db.db.Warning CategoryChangeRule(Integer patientID,
			String description, double Current, double Previous,
			double LowerThreshold, double UpperThreshold, int TypeOfAnalysis)
	{
		if (Previous <= LowerThreshold && Current > UpperThreshold)
			return GenerateWarning(patientID, description, Current, Previous, TypeOfAnalysis, "");
		return null;
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// DATABASE OPERATIONS
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param patientId
	 * @param _fromDate
	 * @param _toDate
	 * @param measurementType
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private List<eu.ehealth.db.db.Measurement> getPatientMeasurementX(Integer patientId, Calendar _fromDate, 
																		Calendar _toDate, String measurementType)
	{
		String fromDate = _fromDate.toString();
		String fromDateSQLFormat = _fromDate.get(Calendar.YEAR) + "-" + 
								   (_fromDate.get(Calendar.MONTH) + 1) + "-" + 
								   _fromDate.get(Calendar.DAY_OF_MONTH) + " " +
								   "00:00:00";
		String toDate = _toDate.toString();
		String toDateSQLFormat = _toDate.get(Calendar.YEAR) + "-" + 
							     (_toDate.get(Calendar.MONTH) + 1) + "-" + 
							     _toDate.get(Calendar.DAY_OF_MONTH) + " " +
							     "23:59:59";

		if (fromDate.compareTo(toDate) == 0)
		{
			Date time = _fromDate.getTime();
			time.setHours(time.getHours() + 23);
			time.setMinutes(time.getMinutes() + 59);
			time.setSeconds(time.getSeconds() + 59);
			toDate = time.toString();
		}
		else
		{
			Date time1 = _toDate.getTime();
			time1.setHours(23);
			time1.setMinutes(59);
			time1.setSeconds(59);
			toDate = time1.toString();

			Date time2 = _fromDate.getTime();
			time2.setHours(0);
			time2.setMinutes(0);
			time2.setSeconds(0);
			fromDate = time2.toString();
		}

		String sql = "";
		if (StorageComponentMain.DATABASE.compareTo(StorageComponentMain.DataBase.MySQL) == 0) 
		{
			// compare dates : example ... STR_TO_DATE('2013-12-31 00:00:01', '%Y-%m-%d %H:%i:%s')
			sql = "SELECT m.id FROM measurement as m inner join task as t on (t.id = m.task) inner join aladdinuser as u on (u.id = t.object) WHERE u.personid = '"
					+ patientId.toString()
					+ "' AND m.datetime BETWEEN STR_TO_DATE('"
					+ fromDateSQLFormat
					+ "', '%Y-%m-%d %H:%i:%s') AND STR_TO_DATE('"
					+ toDateSQLFormat
					+ "', '%Y-%m-%d %H:%i:%s') AND m.type = '"
					+ measurementType + "'";
		}
		else 
		{
			sql = "SELECT m.id FROM measurement as m inner join task as t on (t.id = m.task) inner join aladdinuser as u on (u.id = t.object) WHERE u.personid = '"
					+ patientId.toString()
					+ "' AND m.datetime BETWEEN '"
					+ fromDate
					+ "' AND '"
					+ toDate
					+ "' AND m.type = '"
					+ measurementType + "'";
		}
		
		StorageComponentMain.scLog("DEBUG", sql);
		Object[] ml = _session.createSQLQuery(sql).list().toArray();

		ArrayList<eu.ehealth.db.db.Measurement> export = new ArrayList<eu.ehealth.db.db.Measurement>();
		for (int i = 0; i < ml.length; i++)
		{
			Integer id = (Integer) ml[i];
			eu.ehealth.db.db.Measurement m = (eu.ehealth.db.db.Measurement) _session.load(eu.ehealth.db.db.Measurement.class, id);
			export.add(m);
		}
		return export;
	}
	
	
	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param objectId
	 * @return
	 */
	private List<QuestionnaireAnswers> getQuestionnaireAnswers(Calendar fromDate, Calendar toDate, Integer objectId)
	{
		List<QuestionnaireAnswers> l = new ArrayList<QuestionnaireAnswers>();

		String sql = "SELECT qa.timestamp, qq.quest, qa.objectid, qa.userid FROM questionnaireanswer qa inner join questionnairequestion qq on (qq.id = qa.question) WHERE qa.objectid = '"
				+ objectId.toString()
				+ "' AND qa.timestamp BETWEEN '"
				+ fromDate.toString()
				+ "' AND '"
				+ toDate.toString()
				+ "' GROUP BY qa.timestamp, qq.quest, qa.objectid, qa.userid";

		Object[] questionids = _session.createSQLQuery(sql).list().toArray();

		for (int i = 0; i < questionids.length; i++)
		{
			Object[] q = (Object[]) questionids[i];
			TimeZone zone = TimeZone.getDefault();
			Timestamp timestamp = (Timestamp) q[0];

			// work around
			Calendar before = Calendar.getInstance();
			before.setTimeInMillis(timestamp.getTime());
			Calendar after = Calendar.getInstance();
			after.setTimeInMillis(timestamp.getTime() + 1000);

			Integer question = (Integer) q[1];
			sql = "SELECT id FROM questionnaireanswer WHERE objectid = '" + objectId.toString();
			sql += "' AND timestamp BETWEEN '" + before.getTime().toString();
			sql += "' AND '" + after.getTime().toString();
			sql += "' AND question in (select id from questionnairequestion where quest = " + question.toString() + ")";

			Object[] lqa = _session.createSQLQuery(sql).list().toArray();
			QuestionnaireAnswers rqas = new QuestionnaireAnswers();

			GregorianCalendar c = new GregorianCalendar(zone);
			c.setTimeInMillis(timestamp.getTime());
			try
			{
				rqas.setDateTime(DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(c));
			}
			catch (Exception ex)
			{
			}

			rqas.setObjectID(((Integer) q[2]).toString());
			rqas.setUserID(((Integer) q[3]).toString());

			String sqlTask = "SELECT id FROM task WHERE datetimefulfilled = '" + before.getTime().toString() + "'";
			Object[] lt = _session.createSQLQuery(sqlTask).list().toArray();
			if (lt.length > 0)
			{
				rqas.setTaskID(((Integer) lt[0]).toString());
			}

			for (int j = 0; j < lqa.length; j++)
			{
				QuestionnaireAnswer rqa = new QuestionnaireAnswer();
				eu.ehealth.db.db.QuestionnaireAnswer qa = (eu.ehealth.db.db.QuestionnaireAnswer) _session
						.load(eu.ehealth.db.db.QuestionnaireAnswer.class, (Integer) lqa[j]);
				rqa.setQuestionID(qa.getQuestion().toString());
				rqa.setValue(qa.getValue());
				rqas.setObjectID(qa.getObjectId().toString());
				rqas.setUserID(qa.getUserId().toString());

				eu.ehealth.db.db.QuestionnaireQuestion qq = (eu.ehealth.db.db.QuestionnaireQuestion) _session
						.load(eu.ehealth.db.db.QuestionnaireQuestion.class, qa.getQuestion());
				rqa.setGlobalID(qq.getGlobalId().toString());

				rqas.getAnswer().add(rqa);
			}

			l.add(rqas);
		}

		return l;
	}
	
	
	/**
	 * Generate Warning
	 * 
	 * @param patientID
	 * @param description
	 * @param RiskValue
	 * @param PreviousValue
	 * @param TypeOfAnalysis
	 * @param justTxt
	 * @return
	 */
	protected static eu.ehealth.db.db.Warning GenerateWarning(Integer patientID,
			String description, double RiskValue, double PreviousValue, int TypeOfAnalysis, String justTxt)
	{
		StorageComponentMain.scLog("INFO", "Creating warning : patientID " + patientID);

		eu.ehealth.db.db.Warning warning = new eu.ehealth.db.db.Warning();
		warning.setPatient(patientID);
		warning.setTypeOfWarning(2);
		warning.setDateTimeOfWarning(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		warning.setDelivered(false);

		if (TypeOfAnalysis == MeasurementAnalysis)
		{
			if (justTxt.trim().length() == 0) {
				warning.setJustificationText(String.format("-%s- [Current value = %s, Previous value = %s]", description, RiskValue, PreviousValue));
			}
			else {
				warning.setJustificationText(justTxt);
			}
		}
		else if (TypeOfAnalysis == QuestionnaireAnalysis) 
		{
			warning.setJustificationText(description);
		}

		return warning;
	}


}
