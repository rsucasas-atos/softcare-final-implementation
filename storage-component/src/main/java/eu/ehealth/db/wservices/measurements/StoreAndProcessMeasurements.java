package eu.ehealth.db.wservices.measurements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.dataprocessing.DataObj;
import eu.ehealth.dataprocessing.DataProcessingThread;
import eu.ehealth.db.xsd.Measurement;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class StoreAndProcessMeasurements extends BaseMeasurementsOperations<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public StoreAndProcessMeasurements(Session session)
	{
		super(session);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			List<Measurement> data = (List<Measurement>) lo.get(0);
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : StoreMeasurements");			
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			for (int i = 0; i < data.size(); i++)
			{
				data.set(i, nc.check(data.get(i), Measurement.class));
			}

			if (!checkUserPermissions("uploadData", userId, U_CLINICIAN, U_CARER, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			boolean error = false;
			try
			{
				Measurement[] rm = data.toArray(new Measurement[data.size()]);
				Integer id = 0;
				for (int i = 0; i < rm.length; i++)
				{
					try
					{
						_session.beginTransaction();
						id = importMeasurement(rm[i], null);	// store data
						_session.getTransaction().commit();
					}
					catch (HibernateException e)
					{
						rollbackSession();

						StorageComponentMain.logException(e);
						
						error = true;
						res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_1.getCode(), " : " + e.toString());
					}
				}

				if (!error) 
				{
					// set OperationResult values
					res = Globals.getOpResult("" + id.toString(), "");
					
					// process data
					String measurement_type = getStrMeasurementType(rm[0].getType().getCode());
					ArrayList<DataObj> dataRM = getDataProcessing(rm, measurement_type);
					
					if (dataRM.size() > 0)
					{
						DataProcessingThread runnable = new DataProcessingThread(dataRM, measurement_type);
						Thread thread = new Thread(runnable, "DataProcessingThread");
						thread.start();
					}
				}
			}
			catch (HibernateException e)
			{
				rollbackSession();

				StorageComponentMain.logException(e);

				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_1.getCode(), " : " + e.toString());
			}

			return res;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
			// set OperationResult values
			return Globals.getOpResult(Globals.ResponseCode.UNKNOWN_ERROR.getCode(), " : " + ex.toString());
		}
	}
	
	
	/**
	 * 
	 * @param rm
	 * @param measurement_type
	 * @return
	 */
	private ArrayList<DataObj> getDataProcessing(Measurement[] rm, String measurement_type)
	{
		try
		{
			String taskId = rm[0].getTaskID();
			
			Integer[] res =  getPersonIdAndHeight(taskId);
			if (res != null)
			{
				Integer personId = res[0];
				Integer height = res[1];
				
				Calendar currentDate = Calendar.getInstance();
				Calendar oneWeekBefore = (Calendar) Calendar.getInstance();
				oneWeekBefore.add(Calendar.DATE, -10);
				
				List<eu.ehealth.db.db.Measurement> measurements_main = getPatientMeasurementX(personId, 
																						 oneWeekBefore, 
																						 currentDate, 
																						 measurement_type);
				if (measurements_main.size() < 1)
				{
					StorageComponentMain.scLog("WARN", "No measurements found during the last 10 days");
				}
				else 
				{
					ArrayList<DataObj> data = new ArrayList<DataObj>(2);
					
					switch (measurement_type)
					{
						case Measurement_BloodPressure:

							break;
							
						case Measurement_Weight:
						case Measurement_Activity:
							DataObj data_Weight0 = null;
							DataObj data_Activity1 = null;
							
							float current_value = (float)rm[0].getValue();
							float last_value = measurements_main.get(measurements_main.size() - 1).getValue().floatValue();
							
							if (measurement_type.equalsIgnoreCase(Measurement_Weight))
							{
								float average_Weight = getWeightAverage(measurements_main);
								
								data_Weight0 = new DataObj(current_value, last_value, average_Weight, height.intValue(), personId.intValue());

								List<eu.ehealth.db.db.Measurement> measurements_Activity = 
										getPatientMeasurementX(personId, oneWeekBefore, currentDate, Measurement_Activity);
								
								if (measurements_Activity.size() > 0)
								{
									float average_Activity = getActivityAverage(measurements_Activity);
									data_Activity1 = new DataObj(0, 0, average_Activity, height.intValue(), personId.intValue());
									
									data.add(data_Weight0);
									data.add(data_Activity1);
								}
								else
								{
									data.add(data_Weight0);
								}
							}
							else if (measurement_type.equalsIgnoreCase(Measurement_Activity))
							{
								float average_Activity = getActivityAverage(measurements_main);
								
								data_Activity1 = new DataObj(current_value, last_value, average_Activity, height.intValue(), personId.intValue());
								
								List<eu.ehealth.db.db.Measurement> measurements_Weight = 
										getPatientMeasurementX(personId, oneWeekBefore, currentDate, Measurement_Weight);
								
								if (measurements_Weight.size() > 0)
								{
									float average_Weight = getWeightAverage(measurements_Weight);
									data_Weight0 = new DataObj(0, 0, average_Weight, height.intValue(), personId.intValue());
									
									data.add(data_Weight0);
									data.add(data_Activity1);
								}
								else
								{
									data.add(data_Activity1);
								}
							}

							break;
					}
					
					return data;
				}
			}
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
		}
		
		StorageComponentMain.scLog("WARN", "No data processed ");
		return new ArrayList<DataObj>(0);
	}
	
	
	/**
	 * 
	 * @param measurements
	 * @return
	 */
	private float getWeightAverage(List<eu.ehealth.db.db.Measurement> measurements)
	{
		float res = 0;
		
		for (eu.ehealth.db.db.Measurement m : measurements)
		{
			res += m.getValue().floatValue();
		}
		
		res = res / measurements.size();
		
		return StorageComponentMain.round(res, 2);
	}
	
	
	/**
	 * 
	 * @param measurements
	 * @return
	 */
	private float getActivityAverage(List<eu.ehealth.db.db.Measurement> measurements)
	{
		float res = 0;
		
		for (eu.ehealth.db.db.Measurement m : measurements)
		{
			res += m.getValue().floatValue();
		}
		
		res = res / measurements.size();
		
		return StorageComponentMain.round(res, 2);
	}
	
	
	/**
	 * 
	 * @param measurements
	 * @return
	 */
	private float getBloodPressureSysAverage(List<eu.ehealth.db.db.Measurement> measurements)
	{
		float res = 0;
		
		for (eu.ehealth.db.db.Measurement m : measurements)
		{
			if (m.getField1().equalsIgnoreCase(Measurement_BloodPressure_sys))
				res += m.getValue().floatValue();
		}
		
		res = res / measurements.size();
		
		return StorageComponentMain.round(res, 2);
	}
	
	
	/**
	 * 
	 * @param measurements
	 * @return
	 */
	private float getBloodPressureDiaAverage(List<eu.ehealth.db.db.Measurement> measurements)
	{
		float res = 0;
		
		for (eu.ehealth.db.db.Measurement m : measurements)
		{
			if (m.getField1().equalsIgnoreCase(Measurement_BloodPressure_dia))
				res += m.getValue().floatValue();
		}
		
		res = res / measurements.size();
		
		return StorageComponentMain.round(res, 2);
	}
	
	
	/**
	 * 
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Integer[] getPersonIdAndHeight(String taskId)
	{
		try
		{
			// id, username, personid from Patient
			String sql1 = "SELECT a.personid, a.username, a.id, s.height FROM aladdinuser a " +
						   "INNER JOIN task t ON (t.object = a.id)" +
						   "LEFT JOIN persondata pd ON (a.personid = pd.id) " +
						   "LEFT JOIN patient p ON (p.persondata = pd.id) " +
						   "LEFT JOIN sociodemographicdata s ON (p.sd = s.id)" +
						   "WHERE t.id = " + taskId;
			
			List<Object[]> dataQ = _session.createSQLQuery(sql1).list();

			if (dataQ.size() > 0)
			{
				Integer personId = (Integer) dataQ.get(0)[0]; // persondata id
				//String username = (String) dataQ.get(0)[1];
				//Integer userId = (Integer) dataQ.get(0)[2];
				Integer height = (Integer) dataQ.get(0)[3];
				
				Integer[] res = new Integer[2];
				res[0] = personId;
				res[1] = height;
				
				return res;
			}
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
		}
		
		return null;
	}
	
	
	/**
	 * 
	 * @param patientId
	 * @param _fromDate
	 * @param _toDate
	 * @param measurementType
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private List<eu.ehealth.db.db.Measurement> getPatientMeasurementX(Integer patientId, Calendar _fromDate, Calendar _toDate, String measurementType)
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
	
	
	public static final String Measurement_BloodPressure = "1";
	public static final String Measurement_Weight = "2";
	public static final String Measurement_Activity = "3";
	
	public static final String Measurement_BloodPressure_sys = "sys";
	public static final String Measurement_BloodPressure_dia = "dia";
	
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	private String getStrMeasurementType(String code)
	{
		// "Weight"
		if (code.equals("2")) 
		{
			return Measurement_Weight;
		}
		// "Activity"
		if (code.equals("3")) 
		{
			return Measurement_Activity;
		}
		// "Systolic Blood Pressure"
		else if (code.equals("11")) 
		{
			return Measurement_BloodPressure;
		}
		// "Diastolic Blood Pressure"
		else if (code.equals("12")) 
		{	
			return Measurement_BloodPressure;
		}
		// error
		else
		{
			return "-1";
		}
	}
	

}
