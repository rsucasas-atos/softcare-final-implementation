package eu.ehealth.dataprocessing;

import java.util.HashMap;
import eu.ehealth.StorageComponentMain;


/**
 * 
 * @author
 *
 */
public class MTables
{


	/**
	 * 
	 */
	private HashMap<MTables.TABLAS, HashMap<String, MTableElement>> MTABLES;


	/**
	 * 
	 * @author
	 *
	 */
	public static enum TABLAS
	{
		BMI, 
		ACTIVITY, 
		BLOODPRESS_SYS, 
		BLOODPRESS_DIA, 
		ERROR
	}

	/**
	 * 
	 * @author
	 *
	 */
	public static enum LEVELS_BMI
	{
		Very_severely_underweight, 
		Severely_underweight, 
		Underweight,
		Normal, 
		Overweight, 
		Moderately_obese, 
		Severely_obese, 
		Very_severely_obese, 
		ERROR
	}

	/**
	 * 
	 * @author
	 *
	 */
	public static enum LEVELS_ACTIVITY
	{
		Sedentary, 
		Low_Active, 
		Somewhat_Active, 
		Active, 
		Highly_Active, 
		ERROR
	}

	/**
	 * 
	 * @author
	 *
	 */
	public static enum LEVELS_BLOODPRESS
	{
		Hypotension, 
		Normal, 
		Prehypertension, 
		Hypertension_1, 
		Hypertension_2, 
		Hypertension_3, 
		ERROR
	}
	
	
	/**
	 * 
	 */
	private static MTables _instance;
	
	
	/**
	 * 
	 * @return
	 */
	public static MTables getInstance()
	{
		if (_instance == null)
			_instance = new MTables();
		
		return _instance;
	}


	/**
	 * 
	 */
	private MTables()
	{
		try
		{
			MTableJsonReader jreader = new MTableJsonReader();

			MTABLES = new HashMap<MTables.TABLAS, HashMap<String, MTableElement>>(4);

			MTABLES.put(TABLAS.BMI, jreader.getDataResources(StorageComponentMain.TABLE_WEIGHT));
			MTABLES.put(TABLAS.ACTIVITY, jreader.getDataResources(StorageComponentMain.TABLE_ACTIVITY));
			MTABLES.put(TABLAS.BLOODPRESS_SYS, jreader.getDataResources(StorageComponentMain.TABLE_SYS));
			MTABLES.put(TABLAS.BLOODPRESS_DIA, jreader.getDataResources(StorageComponentMain.TABLE_DIA));
		}
		catch (Exception e)
		{
			StorageComponentMain.logException(e);
		}
	}


	/**
	 * @return the mTABLE
	 */
	public HashMap<MTables.TABLAS, HashMap<String, MTableElement>> getMTABLE()
	{
		return MTABLES;
	}


	/**
	 * 
	 * @param t
	 * @param value
	 * @return
	 */
	public String getLevelForTable(MTables.TABLAS t, float value)
	{
		try
		{
			for (MTableElement elem : MTABLES.get(t).values())
			{
				if (elem.containValue(value))
					return elem.getLevel();
			}
		}
		catch (Exception e)
		{
			StorageComponentMain.logException(e);
		}

		return "";
	}


	/**
	 * 
	 * @param name
	 * @return
	 */
	public MTables.LEVELS_BLOODPRESS getLEVELS_BLOODPRESSByName(String name)
	{
		for (MTables.LEVELS_BLOODPRESS ts : MTables.LEVELS_BLOODPRESS.values())
		{
			if (ts.name().equalsIgnoreCase(name))
				return ts;
		}
		
		return LEVELS_BLOODPRESS.ERROR;
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public MTables.LEVELS_BMI getLEVELS_BMIByName(String name)
	{
		for (MTables.LEVELS_BMI ts : MTables.LEVELS_BMI.values())
		{
			if (ts.name().equalsIgnoreCase(name))
				return ts;
		}
		
		return LEVELS_BMI.ERROR;
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public MTables.LEVELS_ACTIVITY getLEVELS_ACTIVITYByName(String name)
	{
		for (MTables.LEVELS_ACTIVITY ts : MTables.LEVELS_ACTIVITY.values())
		{
			if (ts.name().equalsIgnoreCase(name))
				return ts;
		}
		
		return LEVELS_ACTIVITY.ERROR;
	}
	

}
