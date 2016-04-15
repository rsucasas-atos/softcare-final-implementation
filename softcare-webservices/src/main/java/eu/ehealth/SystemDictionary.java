package eu.ehealth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ScheduledExecutorService;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import eu.ehealth.db.DbQueries;
import eu.ehealth.security.KeyStoreConfig;


/**
 *
 * 
 * @author 
 */
public class SystemDictionary
{
	
	
	// app version
	public static String APP_VERSION = "softcare-ws v0.0.2 [default]";
		
	// E-Health Web Services configuration variables
	public static Level APPLICATION_DEBUG_LEVEL = Level.DEBUG;	// DEBUG level output : DEBUG, INFO ...
	public static boolean ACTIVATE_INTERCEPTOR_DB = true;		// store SOAP messages in DB - for DEBUG purposes
	public static boolean ACTIVATE_OUT_INTERCEPTOR_DB = false;	// store SOAP messages in DB - for DEBUG purposes
	public static boolean DATABASE_ENCRYPTION = false;			// Encrypt data with JASYPT
	public static boolean HIBERNATE_ENCRYPTION = false;			// Encrypt hibernate properties with JASYPT
	public static boolean DATABASE_WITH_SSL = false;			// SSL Communication with Database 
	public static String RULES_FILE = "rules.json";				// Rules file
	public static boolean USE_SYSPROPS = true;					// use connection string from environment / system properties
	
	// PaaS database
	public static boolean DATABASE_INITIALIZED = true;			// Database correctly installed and configured

	// log
	private static Logger logger = null;
	
	// scheduler
	public static ScheduledExecutorService _scheduler;
	
	// Storage Component
	public static StorageComponentMain STORAGE_COMPONENT;
	
	// system initialized
	private static boolean _initialized = false;

	
	/**
	 * Initialization
	 */
	static
	{
		webguiLog("INFO", "SystemDictionary : retrieving values from properties file ...");

		// PROPERTIES FILE ////////////////////////////////////////////////////
		try 
		{
			PropertiesConfiguration props = new PropertiesConfiguration("ws.properties");
			
			if ((props == null) || (props.isEmpty())) {
				webguiLog("FATAL", SystemDictionary.class.getName() + " : Error getting application properties");
			}
			else {
				webguiLog("INFO", SystemDictionary.class.getName() + " : application properties loaded");
				try {
					Iterator<String> itr = props.getKeys();
					while(itr.hasNext()) { // CONFIG_PROPERTIES.getKeys()
						String key = (String) itr.next();
						webguiLog("INFO", SystemDictionary.class.getName() + " : ..." + key + " : " + props.getString(key));
					}
				}
				catch (Exception ex1) { }
			}
			
			String version = props.getString("version", APP_VERSION);
			// show app version
			webguiLog("INFO", "........................................................................................");
			webguiLog("INFO", SystemDictionary.class.getName() + " : " + version);
			webguiLog("INFO", "........................................................................................");
			
			// RULES_FILE
			String rulesFileCfg = props.getString("rules");
			if ((rulesFileCfg != null) && (rulesFileCfg.trim().length() > 0)) {
				RULES_FILE = rulesFileCfg;
			}
			
			// ENCRYPTED PASSWORDS in hibernate.cfg.xml
			String encryptedHibernateCfg = props.getString("encryptedHibernateCfg");
			if ((encryptedHibernateCfg != null) && (encryptedHibernateCfg.equalsIgnoreCase("true"))) {
				HIBERNATE_ENCRYPTION = true;
			}
			
			// DATABASE_ENCRYPTION
			String encryptionEnabled = props.getString("encryptdb");
			if ((encryptionEnabled != null) && (encryptionEnabled.equalsIgnoreCase("true"))) {
				DATABASE_ENCRYPTION = true;
			}

			// DATABASE_WITH_SSL
			String dbSslEnabled = props.getString("dbSslEnabled");
			if ((dbSslEnabled != null) && (dbSslEnabled.equalsIgnoreCase("true"))) {
				KeyStoreConfig keyCfg = new KeyStoreConfig();
				if (keyCfg.configureSSLParameters(props)) 
				{
					DATABASE_WITH_SSL = true;
				}
			}
			
			USE_SYSPROPS  = props.getBoolean("database.sysproperty", true);
			// Initialize Storage Component
			STORAGE_COMPONENT = new StorageComponentMain(false, rulesFileCfg, HIBERNATE_ENCRYPTION, DATABASE_ENCRYPTION, 
														 DATABASE_WITH_SSL, false, USE_SYSPROPS);
			if (!STORAGE_COMPONENT.initialize()) {
				webguiLog("ERROR", "Storage Component NOT initialized");
			}
		}
		catch (Exception ex) {
			logException(ex);
		}
		
		webguiLog("INFO", "SystemDictionary initialized");
	}
	
	
	/**
	 * 
	 */
	public static boolean initialize() {
		if (!_initialized)
		{
			webguiLog("INFO", SystemDictionary.class.getName() + " : DATABASE_INITIALIZED [db status] ... " + DATABASE_INITIALIZED);
			if (!DATABASE_INITIALIZED) {
				initializeDB(true, null, null);
			}
			webguiLog("INFO", SystemDictionary.class.getName() + " : DATABASE_INITIALIZED [db status] ... " + DATABASE_INITIALIZED);
			
			webguiLog("INFO", SystemDictionary.class.getName() + " : _initialized [eHealth init parameters] ... " + _initialized);
			if ((DATABASE_INITIALIZED) && (!_initialized)) {
				try
				{
					DbQueries db = new DbQueries();
					ArrayList<String[]> roles = (ArrayList<String[]>) db.getEhealthRoles();
					Globals.addValuesEHEALTH_PARAMETERS(roles);
					
					ArrayList<String[]> params = (ArrayList<String[]>) db.getEhealthParameters();
					Globals.addValuesEHEALTH_PARAMETERS(params);
					
					webguiLog("INFO", SystemDictionary.class.getName() + " : EHealth parameters retrieved");
				}
				catch (Exception e)
				{
					SystemDictionary.logException(e);
				}
			
				_initialized = true;
			}
			else 
			{
				webguiLog("WARN", SystemDictionary.class.getName() + " : Could not initialize database");
			}
			webguiLog("INFO", SystemDictionary.class.getName() + " : _initialized [eHealth init parameters] ... " + _initialized);
		}
		
		return _initialized;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static boolean getInitializedValue() {
		return _initialized;
	}
	
	
	/**
	 * 
	 * @param getDataFromProps
	 * @param paaS
	 * @param scriptFile
	 */
	private static void initializeDB(boolean getDataFromProps, String paaS, String scriptFile) {
		try 
		{
			/*if (getDataFromProps) 
			{
				PropertiesConfiguration props = new PropertiesConfiguration("ws.properties");
				paaS = props.getString("PaaS");
				scriptFile = props.getString("sqliniscript");
			}
			
			// DATABASE INITIALIZATION
			webguiLog("INFO", SystemDictionary.class.getName() + " : Checking database ...");
			
			DBInitializer dbInitializer = new DBInitializer(paaS, scriptFile);
			if (!dbInitializer.existsDB()) 
			{
				webguiLog("INFO", SystemDictionary.class.getName() + " : Database is not configured");
				webguiLog("INFO", SystemDictionary.class.getName() + " : Configuring database ...");
				
				DATABASE_INITIALIZED = dbInitializer.initializeDB();
			}*/
			
			if (DATABASE_INITIALIZED) 
			{
				webguiLog("INFO", SystemDictionary.class.getName() + " : Database is configured and running");
			}
			else 
			{
				webguiLog("WARN", SystemDictionary.class.getName() + " : Database is NOT configured");
			}
		}
		catch (Exception ex) {
			SystemDictionary.logException(ex);
		}
	}


	/**
	 * Static method which allows the whole system to log messages without
	 * managing log4java objects
	 * 
	 * @param type Log level such as "DEBUG", "INFO", ...
	 * @param message String containing log message
	 */
	public static void webguiLog(String type, String message)
	{
		if (logger == null)
		{
			logger = Logger.getLogger("WSERVICES");
			PropertyConfigurator.configure(SystemDictionary.class.getClassLoader().getResource("log4j.properties"));
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


}
