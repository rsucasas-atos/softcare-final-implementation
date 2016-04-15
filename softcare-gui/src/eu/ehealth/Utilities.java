package eu.ehealth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
 * @author a572832
 *
 */
public class Utilities
{

	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } 
        catch (NumberFormatException nfe) {}
        
        return false;
    }
	
	
	/**
	 * 
	 * @param d
	 * @return
	 */
	public static String dateToStringFormat(Date d) {
        try 
        {
        	// Create an instance of SimpleDateFormat used for formatting 
        	// the string representation of date (month/day/year)
        	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
      
        	// Using DateFormat format method we can create a string 
        	// representation of a date with the defined format.
        	String reportDate = df.format(d);

        	// Print what date is today!
        	return reportDate;
        } 
        catch (Exception e) 
        {
        	SystemDictionary.logException(e);
        }
        
        return "";
    }
	
	
	/**
	 * 
	 * @param sobj
	 * @param defaultVal
	 * @return
	 */
	public static int getIntValue(String sobj, int defaultVal) 
	{
		try 
		{
			return Integer.parseInt(sobj);
		}
		catch (Exception ex) {
			SystemDictionary.logException(ex);
			return defaultVal;
		}
	}
	
	
}
