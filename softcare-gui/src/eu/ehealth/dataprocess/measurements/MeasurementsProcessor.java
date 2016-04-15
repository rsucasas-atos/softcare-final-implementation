package eu.ehealth.dataprocess.measurements;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import org.zkoss.image.AImage;
import eu.ehealth.SystemDictionary;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.Measurement;


/**
 * 
 * 
 * @author a572832
 * @date 20/03/2014.
 *
 */
public class MeasurementsProcessor
{


	private String _patientId;

	
	/**
	 * 
	 * @param patientId
	 * @param loggedUser
	 * @param typeofint
	 * @param calto
	 * @param calfrom
	 * @return
	 */
	public AImage generateChartImage(String patientId, String loggedUser, String typeofint, Calendar calto, Calendar calfrom)
	{
		try
		{
			_patientId = patientId;

			List<Measurement>[] measurementlistJFREECHART = fillData(typeofint, calfrom, calto, loggedUser);

			if (measurementlistJFREECHART != null) 
			{
				MeasurementCharts mchart = new MeasurementCharts(800, 500);
				AImage image = mchart.generateChart(typeofint, measurementlistJFREECHART);
				
				return image;
			}
			
			// ERROR
			SystemDictionary.webguiLog("DEBUG", "ERROR : measurementlistJFREECHART == NULL");
			return null;
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
			return null;
		}
	}


	/**
	 * 
	 * @param typeofint
	 * @param calfrom
	 * @param calto
	 * @param loggeduser
	 * @return
	 * @throws RemoteException
	 */
	private List<Measurement>[] fillData(String typeofint, Calendar calfrom, Calendar calto, String loggeduser) throws Exception
	{
		int typeofmint = -1;
		if (SystemDictionary.TASK_TYPE_BLOODPRESSURE_MEASUREMENT.equals(typeofint))
		{
			typeofmint = SystemDictionary.MEASUREMENT_BLODDPRESSURE_INT; 
		}
		else if (SystemDictionary.TASK_TYPE_WEIGHT_MEASUREMENT.equals(typeofint))
		{
			typeofmint = SystemDictionary.MEASUREMENT_WEIGHT_INT;
		}
		else if (SystemDictionary.TASK_TYPE_ACTMONITOR.equals(typeofint))
		{
			typeofmint = SystemDictionary.MEASUREMENT_ACTIVITY_MONITOR_INT;
		}
		else 
		{
			return null;
		}
		
		StorageComponentImpl sc = SystemDictionary.getSCProxy();
		String patientPersonId = sc.getUserPersonIdByPatientId(_patientId, loggeduser).getCode();

		Measurement[] measures = sc.getPatientMeasurementArr(patientPersonId, typeofmint, calfrom, calto, loggeduser);
		
		if (SystemDictionary.TASK_TYPE_BLOODPRESSURE_MEASUREMENT.equals(typeofint))
		{
			return fillBLODDPRESSURE_Data(measures);
		}

		return fillOTHER_Data(measures);
	}

	
	/**
	 * 
	 * @param measures
	 * @throws Exception
	 */
	private List<Measurement>[] fillOTHER_Data(Measurement[] measures) throws Exception
	{
		SystemDictionary.webguiLog("INFO", "Other-Measures: " + measures.length);

		if (measures != null && measures.length > 0)
		{
			List<Measurement> measurementlist = Arrays.asList(measures);
			
			Collections.sort(measurementlist, new MeasurementDateSort());
			
			List<Measurement> tmp = new ArrayList<Measurement>();

			for (int i = 0; i < measurementlist.size(); i++)
			{
				tmp.add(measurementlist.get(i));
			}

			List<Measurement>[] measurementlistJFREECHART = new ArrayList[1];
			measurementlistJFREECHART[0] = tmp;
			return measurementlistJFREECHART;
		}
		
		return null;
	}
	
	
	/**
	 * 
	 * @param measures
	 * @throws Exception
	 */
	private List<Measurement>[] fillBLODDPRESSURE_Data(Measurement[] measures) throws Exception
	{
		SystemDictionary.webguiLog("INFO", "fillBLODDPRESSURE_Data-Measures : " + measures.length);
		
		if ((measures != null) && (measures.length > 0) && (measures.length % 2 ==0))
		{
			List<Measurement> measurementlist = Arrays.asList(measures);

			Collections.sort(measurementlist, new MeasurementDateSort());
			
			List<Measurement>[] measurementlistJFREECHART = new ArrayList[2];
			measurementlistJFREECHART[0] = new ArrayList<Measurement>();
			measurementlistJFREECHART[1] = new ArrayList<Measurement>();

			for (int i=0, max=measures.length; i<max; i++) {
				SystemDictionary.webguiLog("INFO", "measures: " + i);
				
				if (measurementlist.get(i).getValue() > measurementlist.get(i+1).getValue())
				{
					measurementlistJFREECHART[0].add(measurementlist.get(i));
					measurementlistJFREECHART[1].add(measurementlist.get(i+1));
					
					SystemDictionary.webguiLog("INFO", "diastolic: " + i + " --> " + measurementlist.get(i).getValue());
					SystemDictionary.webguiLog("INFO", "systolic: " + (i+1) + " --> " + measurementlist.get(i+1).getValue());
				}
				else 
				{
					measurementlistJFREECHART[0].add(measurementlist.get(i+1));
					measurementlistJFREECHART[1].add(measurementlist.get(i));

					SystemDictionary.webguiLog("INFO", "systolic: " + i + " --> " + measurementlist.get(i).getValue());
					SystemDictionary.webguiLog("INFO", "diastolic: " + (i+1) + " --> " + measurementlist.get(i+1).getValue());
				}
				
				i++;
			}
			
			return measurementlistJFREECHART;
		}
		
		return null;
	}
	
	
}
