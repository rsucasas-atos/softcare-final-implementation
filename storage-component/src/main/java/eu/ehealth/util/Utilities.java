package eu.ehealth.util;

import eu.ehealth.StorageComponentMain;


/**
 * 
 * @author a572832
 *
 */
public class Utilities
{
	
	
	
	public static class NumericFunctions
	{
		
		
		/**
		 * 
		 * @param value
		 * @param vRef
		 * @return 1 if greater, 0 if equal, -2 if lesser, -2 if error
		 */
		public static int int_isGreaterThan(String value, int vRef) {
			try 
			{
				int iValue = Integer.parseInt(value);

				if (iValue > vRef) 
				{
					return 1;
				}
				else if (iValue == vRef) 
				{
					return 0;
				}
				
				return -1;
			}
			catch (Exception ex) {
				StorageComponentMain.scLog("ERROR", ex.getMessage());
				return -2;
			}
		}
		
		
		/**
		 * 
		 * @param obj
		 * @param defaultVal
		 * @return
		 */
		public static int getIntValue(Object obj, int defaultVal) 
		{
			try 
			{
				if (obj == null)
				{
					return defaultVal;
				}
				
				return (Integer)obj;
			}
			catch (Exception ex) {
				StorageComponentMain.scLog("ERROR", ex.getMessage());
				return defaultVal;
			}
		}

	}
	

}
