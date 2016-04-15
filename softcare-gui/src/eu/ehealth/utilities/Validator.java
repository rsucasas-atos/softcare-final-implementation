package eu.ehealth.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @author a572832
 *
 */
public class Validator
{

	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean validatePhone(String value) 
	{
		try 
		{
			Pattern p = Pattern.compile("(\\d-)?\\d{9}");
			Matcher m = p.matcher(value);
			return m.matches();
		} 
		catch (Exception ex) {	
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean validateEmail(String value) 
	{
		try 
		{
			Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
			Matcher m = p.matcher(value);
			return m.matches();
		} 
		catch (Exception ex) {	
			return false;
		}
	}
	
	
	
}
