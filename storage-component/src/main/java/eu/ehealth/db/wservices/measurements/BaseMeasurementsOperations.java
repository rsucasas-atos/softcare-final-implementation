package eu.ehealth.db.wservices.measurements;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.Measurement;
import eu.ehealth.db.xsd.SystemParameter;


/**
 * 
 * @author a572832
 *
 * @param <R>
 * @param <P>
 */
public abstract class BaseMeasurementsOperations<R, P> extends DbStorageComponent<R, P>
{

	
	/**
	 * 
	 * @param session
	 */
	public BaseMeasurementsOperations(Session session)
	{
		super(session);
	}

	
	
	/**
	 * Store measurement
	 * 
	 * @param xMeasurement measurement
	 * @param patientAssessmentId id of the patientAssessment
	 * @return id of the stored data
	 */
	public Integer importMeasurement(Measurement xMeasurement, Integer patientAssessmentId)
	{
		long timeInMillis = 0;
		eu.ehealth.db.db.Measurement dMeasurement = new eu.ehealth.db.db.Measurement();
		if (patientAssessmentId != null)
		{
			dMeasurement.setPatientassessment(patientAssessmentId);
		}
		
		String type = xMeasurement.getType().getCode();
		if (type.equals(Globals.ClientAppValue_DiastolicBloodPressure) || type.equals(Globals.ClientAppValue_SystolicBloodPressure)) 
		{
			type = Globals.ServerAppValue_BloodPressure;
		}
		
		dMeasurement.setType(type);
		dMeasurement.setValue(new BigDecimal(xMeasurement.getValue()));
		if (xMeasurement.getDateTime() != null) 
		{
			timeInMillis = xMeasurement.getDateTime().toGregorianCalendar().getTimeInMillis();
		}
		
		dMeasurement.setField1(xMeasurement.getField1());
		dMeasurement.setField2(xMeasurement.getField2());
		
		dMeasurement.setDatetime(new Timestamp(timeInMillis));
		dMeasurement.setUnits(xMeasurement.getUnits());
		dMeasurement.setLowerlimit(new BigDecimal(xMeasurement.getLowerLimit()));
		dMeasurement.setUpperlimit(new BigDecimal(xMeasurement.getUpperLimit()));
		if (xMeasurement.getTaskID() != null)
		{
			dMeasurement.setTask(new Integer(xMeasurement.getTaskID()));
		}
		return (Integer) _session.save(dMeasurement);
		
		//return dMeasurement.getId();
	}
	
	
	/**
	 * Export measurement
	 * 
	 * @param dMeasurement measurement for export
	 * @return XSD conform
	 */
	protected Measurement exportMeasurement(eu.ehealth.db.db.Measurement dMeasurement)
	{
		Measurement xMeasurement = new Measurement();
		SystemParameter rmeasurementType = new SystemParameter();
		rmeasurementType.setCode(dMeasurement.getType());

		xMeasurement.setType(rmeasurementType);
		xMeasurement.setValue(dMeasurement.getValue().doubleValue());
		Timestamp datetime = dMeasurement.getDatetime();

		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(datetime.getTime());
		try
		{
			xMeasurement.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
		}
		catch (Exception ex) {}
		
		xMeasurement.setField1(dMeasurement.getField1());
		xMeasurement.setField2(dMeasurement.getField2());

		xMeasurement.setUnits(dMeasurement.getUnits());
		xMeasurement.setLowerLimit(dMeasurement.getLowerlimit().doubleValue());
		xMeasurement.setUpperLimit(dMeasurement.getUpperlimit().doubleValue());
		return xMeasurement;
	}
	
	
	/**
	 * 
	 * @param patientId
	 * @param _fromDate
	 * @param _toDate
	 * @param measurementType
	 * @return
	 */
	protected List<Measurement> getPatientMeasurement(Integer patientId, Calendar _fromDate, Calendar _toDate, String measurementType)
	{
		ArrayList<Measurement> export = new ArrayList<Measurement>();

		for (eu.ehealth.db.db.Measurement m : getPatientMeasurementX(patientId, _fromDate, _toDate, measurementType))
		{
			export.add(exportMeasurement(m));
		}

		return export;
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
		if (StorageComponentMain.DATABASE == StorageComponentMain.DataBase.MySQL) 
		{
			// compare dates : example ... STR_TO_DATE('2013-12-31 00:00:01', '%Y-%m-%d %H:%i:%s')
			sql = "SELECT m.id FROM measurement as m inner join task as t on (t.id = m.task) inner join aladdinuser as u on (u.id = t.object) WHERE u.personid = '"
					+ patientId.toString()
					+ "' AND m.datetime BETWEEN STR_TO_DATE('"
					+ fromDateSQLFormat
					+ "', '%Y-%m-%d %H:%i:%s') AND STR_TO_DATE('"
					+ toDateSQLFormat
					+ "', '%Y-%m-%d %H:%i:%s') AND m.type = '"
					+ measurementType.toString() + "'";
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
					+ measurementType.toString() + "'";
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
	
	
}
