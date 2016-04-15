package eu.ehealth.dataprocessing;

import eu.ehealth.StorageComponentMain;


/**
 * 
 * @author 
 *
 */
public class WarningObj
{


	private String name;
	private String description;
	private String warning;


	public static enum warning_level
	{
		low, normal, high, very_high
	}


	private warning_level level;


	/**
	 * @param name
	 * @param description
	 * @param warning
	 * @param level
	 */
	public WarningObj(String name, String description, String warning, warning_level level)
	{
		this.name = name;
		this.description = description;
		this.warning = warning;
		this.level = level;
		
		// DEBUG
		StorageComponentMain.scLog("INFO", "[WarningObj] : name : " + name);
		StorageComponentMain.scLog("INFO", "[WarningObj] : description : " + description);
		StorageComponentMain.scLog("INFO", "[WarningObj] : warning : " + warning);
		StorageComponentMain.scLog("INFO", "[WarningObj] : level : " + level.name());
	}


	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}


	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}


	/**
	 * @return the warning
	 */
	public String getWarning()
	{
		return warning;
	}


	/**
	 * @param warning
	 *            the warning to set
	 */
	public void setWarning(String warning)
	{
		this.warning = warning;
	}


	/**
	 * @return the level
	 */
	public warning_level getLevel()
	{
		return level;
	}


	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(warning_level level)
	{
		this.level = level;
	}

	
}
