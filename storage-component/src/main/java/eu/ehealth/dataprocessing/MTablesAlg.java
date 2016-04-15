package eu.ehealth.dataprocessing;

import java.util.ArrayList;
import eu.ehealth.StorageComponentMain;


/**
 * 
 * @author 
 *
 */
public class MTablesAlg
{
	
	
	/**
	 * 
	 * @author
	 *
	 */
	public static enum MEASUREMENT_TYPE
	{
		WEIGHT, 
		ACTIVITY, 
		WEIGHT_WITHACTIVITY,
		ACTIVITY_WITHWEIGHT,
		BLOODPRESS, 
		ERROR
	}

	
	/**
	 * 
	 * @param type
	 * @param data
	 * @param wobj
	 * @return
	 */
	public boolean generateWarning(MEASUREMENT_TYPE type, ArrayList<DataObj> data, WarningObj wobj)
	{
		if (type.compareTo(MEASUREMENT_TYPE.WEIGHT_WITHACTIVITY) == 0)
			return generateWeightWithActivityWarning(data, wobj);
		
		else if (type.compareTo(MEASUREMENT_TYPE.ACTIVITY_WITHWEIGHT) == 0)
			return generateActivityWithWeightWarning(data, wobj);
		
		else if (type.compareTo(MEASUREMENT_TYPE.WEIGHT) == 0)
			return generateActivityWarning(data, wobj);
		
		else if (type.compareTo(MEASUREMENT_TYPE.ACTIVITY) == 0)
			return generateWeightWarning(data, wobj, true);
		
		else if (type.compareTo(MEASUREMENT_TYPE.BLOODPRESS) == 0)
			return generateBloodPressureWarning(data, wobj);
		
		StorageComponentMain.scLog("ERROR", "[MTablesAlg] no data analyzed");
		return false;
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * @param data
	 * @param wobj
	 * @return
	 */
	private boolean generateBloodPressureWarning(ArrayList<DataObj> data, WarningObj wobj)
	{
		if ((data == null) || (data.size() != 2))
		{
			StorageComponentMain.scLog("ERROR", "[MTablesAlg] data is NULL / INCOMPLETE");
			return false;
		}
		
		try
		{
			// data(0) --> sys
			float sys_current_value = data.get(0).getCurrent_value();
			float sys_average = data.get(0).getAverage();
			
			String sys_level = MTables.getInstance().getLevelForTable(MTables.TABLAS.BLOODPRESS_SYS, sys_current_value);
			MTables.LEVELS_BLOODPRESS sys_res = MTables.getInstance().getLEVELS_BLOODPRESSByName(sys_level);
			
			// data(1) --> dia
			float dia_current_value = data.get(1).getCurrent_value();
			float dia_average = data.get(1).getAverage();
			
			String dia_level = MTables.getInstance().getLevelForTable(MTables.TABLAS.BLOODPRESS_DIA, dia_current_value);
			MTables.LEVELS_BLOODPRESS dia_res = MTables.getInstance().getLEVELS_BLOODPRESSByName(dia_level);
			
			// diastolic
			switch (dia_res)
			{
				case Hypotension:
					wobj = new WarningObj("Hypotension", "Hypotension", "Hypotension", WarningObj.warning_level.very_high);
					return true;
					
				case Normal:
				case Prehypertension:
					break;
					
				case Hypertension_1:
					if (dia_current_value > dia_average)
					{
						wobj = new WarningObj("Hypertension_1", "Hypertension 1", "High Blood Pressure (Hypertension) Stage 1", WarningObj.warning_level.high);
						return true;
					}
					break;
					
				case Hypertension_2:
				case Hypertension_3:
					wobj = new WarningObj("Hypertension_2", "Hypertension 2", "High Blood Pressure (Hypertension) Stage 2, 3", WarningObj.warning_level.very_high);
					return true;
					
				default:
					break;
			}
			
			// systolic
			switch (sys_res)
			{
				case Hypotension:
					wobj = new WarningObj("Hypotension", "Hypotension", "Hypotension", WarningObj.warning_level.very_high);
					return true;
					
				case Normal:
				case Prehypertension:
					break;
					
				case Hypertension_1:
					if (sys_current_value > sys_average)
					{
						wobj = new WarningObj("Hypertension_1", "Hypertension 1", "High Blood Pressure (Hypertension) Stage 1", WarningObj.warning_level.high);
						return true;
					}
					break;
					
				case Hypertension_2:
				case Hypertension_3:
					wobj = new WarningObj("Hypertension_2", "Hypertension 2", "High Blood Pressure (Hypertension) Stage 2, 3", WarningObj.warning_level.very_high);
					return true;
					
				default:
					break;
			}
			
			// other warnings
			if ( ( (sys_average < 120) || (dia_average < 80) ) && ( (sys_current_value > 130) || (dia_current_value > 85) ) )
			{
				wobj = new WarningObj("High_value", "high value", "Too high value (compared to average)", WarningObj.warning_level.low);
				return true;
			}
		}
		catch (Exception e)
		{
			StorageComponentMain.logException(e);
		}
		
		return false;
	}

	
	/**
	 * 
	 * data(0) --> Weight & height
	 * data(1) --> Activity
	 * 
	 * @param data
	 * @param wobj
	 * @return
	 */
	private boolean generateWeightWithActivityWarning(ArrayList<DataObj> data, WarningObj wobj)
	{
		if ((data == null) || (data.size() != 2))
		{
			StorageComponentMain.scLog("ERROR", "[MTablesAlg] data is NULL / INCOMPLETE");
			return false;
		}
		
		try
		{
			if (generateWeightWarning(data, wobj, false))
			{
				return true;
			}
			else
			{
				// data(0) --> Weight & height
				float weight_average = data.get(0).getAverage();
				int height = data.get(0).getSubject_height();
				
				float bmi_average = getBMIValue(weight_average, height);
				
				String weight_level = MTables.getInstance().getLevelForTable(MTables.TABLAS.BMI, bmi_average);
				MTables.LEVELS_BMI weight_BMI_res = MTables.getInstance().getLEVELS_BMIByName(weight_level);
				
				// data(1) --> Activity
				float activity_average = data.get(1).getAverage();
				
				String activity_level = MTables.getInstance().getLevelForTable(MTables.TABLAS.ACTIVITY, activity_average);
				MTables.LEVELS_ACTIVITY activity_res = MTables.getInstance().getLEVELS_ACTIVITYByName(activity_level);
				
				switch (weight_BMI_res)
				{
					case Very_severely_underweight:
					case Severely_underweight:
					case Underweight:
					case Normal:
					case Overweight:
						break;
						
					case Moderately_obese:
						switch (activity_res)
						{
							case Sedentary:
								wobj = new WarningObj("Moderately_obese_Sedentary", "Moderately obese and sedentary", "patient is obese and sedentary ", WarningObj.warning_level.normal);
								return true;
								
							case Low_Active:
								wobj = new WarningObj("Moderately_obese_Low_Active", "Moderately obese and low active", "patient is obese and sedentary ", WarningObj.warning_level.low);
								return true;
								
							default:
								break;
						}
						break;
						
					case Severely_obese:
						switch (activity_res)
						{
							case Sedentary:
								wobj = new WarningObj("Severely_obese_Sedentary", "severely obese and sedentary", "patient is obese and sedentary ", WarningObj.warning_level.high);
								return true;
								
							case Low_Active:
								wobj = new WarningObj("Severely_obese_Low_Active", "severely obese and low active", "patient is obese and sedentary ", WarningObj.warning_level.normal);
								return true;
								
							default:
								break;
						}
						break;
						
					case Very_severely_obese:
						switch (activity_res)
						{
							case Sedentary:
								wobj = new WarningObj("Very_severely_obese_Sedentary", "Very severely obese and sedentary", "patient is obese and sedentary ", WarningObj.warning_level.very_high);
								return true;
								
							case Low_Active:
								wobj = new WarningObj("Very_severely_obese_Low_Active", "Very severely obese and low active", "patient is obese and sedentary ", WarningObj.warning_level.high);
								return true;
								
							default:
								break;
						}
						break;
						
					default:
						break;
				}
			}
		}
		catch (Exception e)
		{
			StorageComponentMain.logException(e);
		}
		
		return false;
	}
	
	
	/**
	 * 
	 * data(0) --> Weight & height
	 * data(1) --> Activity
	 * 
	 * @param data
	 * @param wobj
	 * @return
	 */
	private boolean generateActivityWithWeightWarning(ArrayList<DataObj> data, WarningObj wobj)
	{
		if ((data == null) || (data.size() != 2))
		{
			StorageComponentMain.scLog("ERROR", "[MTablesAlg] data is NULL / INCOMPLETE");
			return false;
		}
		
		try
		{
			// data(0) --> Weight & height
			float weight_average = data.get(0).getAverage();
			int height = data.get(0).getSubject_height();
			
			float bmi_average = getBMIValue(weight_average, height);
			
			String weight_level = MTables.getInstance().getLevelForTable(MTables.TABLAS.BMI, bmi_average);
			MTables.LEVELS_BMI weight_BMI_res = MTables.getInstance().getLEVELS_BMIByName(weight_level);
			
			// data(1) --> Activity
			float activity_average = data.get(1).getAverage();
			
			String activity_level = MTables.getInstance().getLevelForTable(MTables.TABLAS.ACTIVITY, activity_average);
			MTables.LEVELS_ACTIVITY activity_res = MTables.getInstance().getLEVELS_ACTIVITYByName(activity_level);
			
			switch (activity_res)
			{
				case Sedentary:
					switch (weight_BMI_res)
					{
						case Moderately_obese:
							wobj = new WarningObj("Moderately_obese_Sedentary", "Moderately obese and sedentary", "patient is obese and sedentary ", WarningObj.warning_level.normal);
							return true;
							
						case Severely_obese:
							wobj = new WarningObj("Severely_obese_Sedentary", "severely obese and sedentary", "patient is obese and sedentary ", WarningObj.warning_level.high);
							return true;
							
						case Very_severely_obese:
							wobj = new WarningObj("Very_severely_obese_Sedentary", "Very severely obese and sedentary", "patient is obese and sedentary ", WarningObj.warning_level.very_high);
							return true;
							
						default:
							break;
					}
					break;
					
				case Low_Active:
					switch (weight_BMI_res)
					{
						case Moderately_obese:
							wobj = new WarningObj("Moderately_obese_Low_Active", "Moderately obese and low active", "patient is obese and sedentary ", WarningObj.warning_level.low);
							return true;
							
						case Severely_obese:
							wobj = new WarningObj("Severely_obese_Low_Active", "severely obese and low active", "patient is obese and sedentary ", WarningObj.warning_level.normal);
							return true;
							
						case Very_severely_obese:
							wobj = new WarningObj("Very_severely_obese_Low_Active", "Very severely obese and low active", "patient is obese and sedentary ", WarningObj.warning_level.high);
							return true;
							
						default:
							break;
					}
					break;
					
				case Somewhat_Active:
				case Active:
				case Highly_Active:
					break;
					
				default:
					break;
			}
		}
		catch (Exception e)
		{
			StorageComponentMain.logException(e);
		}
		
		return false;
	}
	
	
	/**
	 * 
	 * data(0) --> Weight & height
	 * 
	 * 
	 * @param data
	 * @param wobj
	 * @param checkData
	 * @return
	 */
	private boolean generateWeightWarning(ArrayList<DataObj> data, WarningObj wobj, boolean checkData)
	{
		if ( (checkData) && ( (data == null) || (data.size() != 1) ) )
		{
			StorageComponentMain.scLog("ERROR", "[MTablesAlg] data is NULL / INCOMPLETE");
			return false;
		}
		
		try
		{
			// data(0) --> Weight & height
			float weight_current_value = data.get(0).getCurrent_value();
			float weight_last_value = data.get(0).getLast_value();
			float weight_average = data.get(0).getAverage();
			int height = data.get(0).getSubject_height();
		
			float bmi_average = getBMIValue(weight_average, height);
			
			// gets weight level from average BMI
			String weight_level = MTables.getInstance().getLevelForTable(MTables.TABLAS.BMI, bmi_average);
			MTables.LEVELS_BMI weight_BMI_res = MTables.getInstance().getLEVELS_BMIByName(weight_level);
			
			switch (weight_BMI_res)
			{
				case Very_severely_underweight:
					if ((weight_current_value < weight_average) && (weight_current_value < weight_last_value))
					{
						wobj = new WarningObj("Very_severely_underweight", "Very severely underweight", "patient is underweight and is losing weight", WarningObj.warning_level.very_high);
						return true;
					}
					break;
					
				case Severely_underweight:
					if ((weight_current_value < weight_average) && (weight_current_value < weight_last_value))
					{
						wobj = new WarningObj("Severely_underweight", "Severely underweight", "patient is underweight and is losing weight", WarningObj.warning_level.high);
						return true;
					}
					break;
					
				case Underweight:
				case Normal:
				case Overweight:
					break;
					
				case Moderately_obese:
					if ((weight_current_value > weight_average) && (weight_current_value > weight_last_value))
					{
						wobj = new WarningObj("Moderately_obese", "Moderately obese", "patient is obese and is gaining weight", WarningObj.warning_level.normal);
						return true;
					}
					break;
					
				case Severely_obese:
					if ((weight_current_value > weight_average) && (weight_current_value > weight_last_value))
					{
						wobj = new WarningObj("Severely_obese", "Severely obese", "patient is obese and is gaining weight", WarningObj.warning_level.high);
						return true;
					}
					break;
					
				case Very_severely_obese:
					if ((weight_current_value > weight_average) && (weight_current_value > weight_last_value))
					{
						wobj = new WarningObj("Very_severely_obese", "Very severely obese", "patient is obese and is gaining weight", WarningObj.warning_level.very_high);
						return true;
					}
					break;
					
				default:
					break;
			}
			
			// other warnings
			if (weight_current_value > (weight_average + 4))
			{
				wobj = new WarningObj("gains_weight_too_fast", "gains weight too fast", "patient gains weight too fast", WarningObj.warning_level.high);
				return true;
			}
			else if (weight_current_value < (weight_average - 4))
			{
				wobj = new WarningObj("loses_weight_too_fast", "loses weight too fast", "patient loses weight too fast", WarningObj.warning_level.high);
				return true;
			}
		}
		catch (Exception e)
		{
			StorageComponentMain.logException(e);
		}
		
		return false;
	}
	
	
	/**
	 * 
	 * data(0) --> Activity
	 * 
	 * @param data
	 * @param wobj
	 * @return
	 */
	private boolean generateActivityWarning(ArrayList<DataObj> data, WarningObj wobj)
	{
		if ((data == null) || (data.size() != 1))
		{
			StorageComponentMain.scLog("ERROR", "[MTablesAlg] data is NULL / INCOMPLETE");
			return false;
		}
		
		try
		{
			// data(0) --> Activity
			float activity_average = data.get(0).getAverage();
			
			// gets activity level from average
			String activity_level = MTables.getInstance().getLevelForTable(MTables.TABLAS.ACTIVITY, activity_average);
			MTables.LEVELS_ACTIVITY activity_res = MTables.getInstance().getLEVELS_ACTIVITYByName(activity_level);
			
			switch (activity_res)
			{
				case Sedentary:
					wobj = new WarningObj("Sedentary", "sedentary", "patient is sedentary", WarningObj.warning_level.normal);
					return true;
					
				case Low_Active:
					wobj = new WarningObj("Low_Active", "low active", "patient is low active", WarningObj.warning_level.low);
					return true;
					
				case Somewhat_Active:
				case Active:
				case Highly_Active:
				default:
					break;
			}
		}
		catch (Exception e)
		{
			StorageComponentMain.logException(e);
		}
		
		return false;
	}	
	
	
	/**
	 * 
	 * @param weight
	 * @param height
	 * @return
	 */
	private float getBMIValue(float weight, float height)
	{
		if (height > 0 && weight > 0)
			return weight / (height * height);
		
		StorageComponentMain.scLog("ERROR", "[MTablesAlg] BMI value is -1");
		return -1;
	}
	

}
