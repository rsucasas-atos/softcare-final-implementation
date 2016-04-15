package eu.ehealth.dataprocessing;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbQueries;


/**
 * 
 * @author 
 *
 */
public class DataProcessingThread implements Runnable
{
	
	
	
	private ArrayList<DataObj> dataRM;
	private String measurementType;
	
	public static final String Measurement_BloodPressure = "1";
	public static final String Measurement_Weight = "2";
	public static final String Measurement_Activity = "3";

	
	/**
	 * 
	 * @param dataRM
	 * @param measurementType
	 */
	public DataProcessingThread(ArrayList<DataObj> vdataRM, String vmeasurementType)
	{
		this.dataRM = vdataRM;
		this.measurementType = vmeasurementType;
	}
	
	
	@Override
	public void run()
	{
		try 
        {
			StorageComponentMain.scLog("DEBUG", "Starting data processing ... ");
			
			WarningObj wobj = null;
			MTablesAlg alg = new MTablesAlg();
			boolean warning_generated = false;
			
			switch (measurementType)
			{
				case Measurement_BloodPressure:
					warning_generated = alg.generateWarning(MTablesAlg.MEASUREMENT_TYPE.BLOODPRESS, dataRM, wobj);
					break;
					
				case Measurement_Weight:
					if (dataRM.size() == 1)
					{
						warning_generated = alg.generateWarning(MTablesAlg.MEASUREMENT_TYPE.WEIGHT, dataRM, wobj);
					}
					else
					{
						warning_generated = alg.generateWarning(MTablesAlg.MEASUREMENT_TYPE.WEIGHT_WITHACTIVITY, dataRM, wobj);
					}
					break;
					
				case Measurement_Activity:
					if (dataRM.size() == 1)
					{
						warning_generated = alg.generateWarning(MTablesAlg.MEASUREMENT_TYPE.ACTIVITY, dataRM, wobj);
					}
					else
					{
						warning_generated = alg.generateWarning(MTablesAlg.MEASUREMENT_TYPE.ACTIVITY_WITHWEIGHT, dataRM, wobj);
					}
					break;
					
				default:
					break;
			}
			
			if (warning_generated)
			{
				DbQueries dbq = new DbQueries();
				
				dbq.createWarning(0, 0, 0, wobj.getLevel().ordinal(), wobj.getDescription(), wobj.getLevel().ordinal(), dataRM.get(0).getSubject_personId());
			}
			
			StorageComponentMain.scLog("DEBUG", "Data processing finished");
        }
        catch (Exception ex) 
        {
        	StorageComponentMain.logException(ex);
        }
	}
	

}
