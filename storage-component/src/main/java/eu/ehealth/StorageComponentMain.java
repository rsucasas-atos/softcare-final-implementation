package eu.ehealth;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import eu.ehealth.db.DbQueries;
import eu.ehealth.security.DataBasePasswords;


/**
 *
 * 
 * @author 
 */
public class StorageComponentMain
{
	
	
	// app version
	public static String APP_VERSION = "storage-component v1.0.4.2 [18.03.2016-1]";					
	
	// E-Health Web Services configuration variables
	public static Level APPLICATION_DEBUG_LEVEL = Level.DEBUG;	// DEBUG level output : DEBUG, INFO ...
	public static DataBase DATABASE = DataBase.MySQL;			// DataBase used
	public static boolean DATABASE_ENCRYPTION = false;			// Encrypt data with JASYPT
	public static boolean HIBERNATE_ENCRYPTION = false;			// Encrypt hibernate properties with JASYPT
	public static boolean DATABASE_WITH_SSL = false;			// SSL Communication with Database 
	public static String RULES_FILE = "rules.json";				// Rules file
	public static String TABLE_ACTIVITY = "activity.json";		// TABLE_ACTIVITY file
	public static String TABLE_WEIGHT = "bmi.json";				// TABLE_WEIGHT file
	public static String TABLE_SYS = "bloodpress_sys.json";		// TABLE_SYS file
	public static String TABLE_DIA = "bloodpress_dia.json";		// TABLE_DIA file
	public static String ENCODING = "UTF-8";					// encoding
	public static boolean USE_SYSPROPS = true;					// use connection string from environment / system properties
	public static enum DataBase {
		MySQL, OTHER
	}

	// logger
	private static Logger logger = null;
	
	
	/**
	 * 
	 * @param vCUMULUS_EVENTS_CAPTOR
	 * @param vRULES_FILE
	 * @param vHIBERNATE_ENCRYPTION
	 * @param vDATABASE_ENCRYPTION
	 * @param vDATABASE_WITH_SSL
	 * @param vPERIODIC_TASKS
	 */
	public StorageComponentMain(boolean vCUMULUS_EVENTS_CAPTOR, String vRULES_FILE, boolean vHIBERNATE_ENCRYPTION,
			boolean vDATABASE_ENCRYPTION, boolean vDATABASE_WITH_SSL, boolean vPERIODIC_TASKS) {
		try
		{
			// show app version
			scLog("INFO", "............................................................................");
			scLog("INFO", this.getClass().getName() + " : " + APP_VERSION);
			scLog("INFO", "............................................................................");
			
			// normal flow - log
			scLog("INFO", this.getClass().getName() + " : Initializing Storage Component application ...");
			
			RULES_FILE = vRULES_FILE;
			if ((RULES_FILE != null) && (RULES_FILE.trim().length() > 0)) 
			{
				scLog("INFO", this.getClass().getName() + " : RULES_FILE ... " + RULES_FILE);
			}
			else 
			{
				scLog("INFO", this.getClass().getName() + " : RULES_FILE ... DEFAULT");
			}
			
			HIBERNATE_ENCRYPTION = vHIBERNATE_ENCRYPTION;
			if (HIBERNATE_ENCRYPTION) 
			{
				scLog("INFO", this.getClass().getName() + " : HIBERNATE_ENCRYPTION ... true");
			}
			else 
			{
				scLog("WARN", this.getClass().getName() + " : HIBERNATE_ENCRYPTION ... false");
			}
			
			DATABASE_ENCRYPTION = vDATABASE_ENCRYPTION;
			if (DATABASE_ENCRYPTION) 
			{
				DataBasePasswords.registerEncryptionMethods();
				scLog("INFO", this.getClass().getName() + " : DATABASE_ENCRYPTION ... true");
			}
			else {
				scLog("WARN", this.getClass().getName() + " : DATABASE_ENCRYPTION ... false");
			}
			
			DATABASE_WITH_SSL = vDATABASE_WITH_SSL;
			if (DATABASE_WITH_SSL) {
				scLog("INFO", this.getClass().getName() + " : DATABASE_WITH_SSL ... true");
			}
			else 
			{
				scLog("WARN", this.getClass().getName() + " : DATABASE_WITH_SSL ... false");
			}
		}
		catch (Exception e)
		{
			logException(e);
		}
	}
	
	
	/**
	 * 
	 * @param vCUMULUS_EVENTS_CAPTOR
	 * @param vRULES_FILE
	 * @param vHIBERNATE_ENCRYPTION
	 * @param vDATABASE_ENCRYPTION
	 * @param vDATABASE_WITH_SSL
	 * @param vPERIODIC_TASKS
	 * @param vUSE_SYSPROPS
	 */
	public StorageComponentMain(boolean vCUMULUS_EVENTS_CAPTOR, String vRULES_FILE, boolean vHIBERNATE_ENCRYPTION,
			boolean vDATABASE_ENCRYPTION, boolean vDATABASE_WITH_SSL, boolean vPERIODIC_TASKS, boolean vUSE_SYSPROPS) 
	{
		this(vCUMULUS_EVENTS_CAPTOR, vRULES_FILE, vHIBERNATE_ENCRYPTION, vDATABASE_ENCRYPTION, vDATABASE_WITH_SSL, vPERIODIC_TASKS);
		
		USE_SYSPROPS = vUSE_SYSPROPS;
	}
	
	
	/**
	 * 
	 */
	public boolean initialize() {
		boolean initialized = false;
		
		try
		{
			DbQueries db = new DbQueries();
			ArrayList<String[]> roles = (ArrayList<String[]>) db.getEhealthRoles();
			Globals.addValuesEHEALTH_PARAMETERS(roles);
			
			ArrayList<String[]> params = (ArrayList<String[]>) db.getEhealthParameters();
			Globals.addValuesEHEALTH_PARAMETERS(params);
			
			scLog("INFO", "Configuration parameters retrieved");
			
			initialized = true;
		}
		catch (Exception e)
		{
			logException(e);
		}
		
		return initialized;
	}


	/**
	 * Static method which allows the whole system to log messages without
	 * managing log4java objects
	 * 
	 * @param type Log level such as "DEBUG", "INFO", ...
	 * @param message String containing log message
	 */
	public static void scLog(String type, String message)
	{
		if (logger == null)
		{
			logger = Logger.getLogger("STORAGE-COMPONENT");
			PropertyConfigurator.configure(StorageComponentMain.class.getClassLoader().getResource("log4j.properties"));
			logger.setLevel(APPLICATION_DEBUG_LEVEL);
		}
		if (type.equals("INFO"))
		{
			logger.info(message);
		}
		else if (type.equals("DEBUG"))
		{
			logger.debug(message);
		}
		else if (type.equals("WARN"))
		{
			logger.warn(message);
		}
		else if (type.equals("ERROR"))
		{
			logger.error(message);
		}
		else if (type.equals("FATAL"))
		{
			logger.fatal(message);
		}
		else if (type.equals("TRACE"))
		{
			logger.trace(message);
		}
	}
	
	
	/**
     * 
     * @param ex
     */
	public static void logException(Exception ex) 
    {
		logger.error(ex.getMessage(), ex);
    }
	
	
	
	/**
	 * 
	 * @param d
	 * @param places
	 * @return
	 */
	public static float round(float d, int places) 
	{
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }


}
