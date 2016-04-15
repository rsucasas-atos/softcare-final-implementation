package eu.ehealth.db;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.security.DataBasePasswords;


/**
 * 
 * @author a572832
 * 
 */
public class HibernateUtil
{


	/**
	 * SessionFactory static object
	 */
	private static SessionFactory sessionFactory;
	
	private static int counter = 0;
	
	
	/**
	 * 
	 * 
	 * 
	 * jdbc:mysql://<HOST>:<PORT>/<DB_NAME>?user=<USER_NAME>&password=<PASWORD>
	 * jdbc:mysql://ec2-52-37-3-24.us-west-2.compute.amazonaws.com:3306/<DB_NAME>?user=<USER_NAME>&password=<PASWORD>
	 * jdbc:mysql://192.0.1.134:3306/<DB_NAME>?user=<USER_NAME>&password=<PASWORD>
	 * 
	 * 
	 * @return
	 */
	private static void buildSessionFactory()
	{
		try
		{
			counter++;
			StorageComponentMain.scLog("INFO", HibernateUtil.class.getName() + " : sessionfactory status : creating new [" + counter + "] sessionfactory [env / sys properties: " + StorageComponentMain.USE_SYSPROPS + "]...");
			
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration().configure();
						
			// get values from environment / system properties
			if (StorageComponentMain.USE_SYSPROPS)
			{
				PropertiesConfiguration props = new PropertiesConfiguration("ws.properties");
				if ((props == null) || (props.isEmpty())) {
					StorageComponentMain.scLog("FATAL", HibernateUtil.class.getName() + " : Error getting application properties");
					throw new ExceptionInInitializerError(new Throwable());
				}
				
				String connurl = props.getString("database.sysproperty.connurl.name", "");
				//String user = props.getString("database.sysproperty.user.name", "");
				//String passwd = props.getString("database.sysproperty.passwd.name", "");
				
				/*
			  	 *<property name="connection.url">jdbc:mysql://95.211.172.242/seaclouds</property> 
			  	 *<property name="connection.username">qV+I7mm367g3lb+L3xdwCykW7eDMlnoQ</property> 
			  	 *<property name="connection.password">Y+aZtFnwxX0QpBuIvglEJDp8vC/XlCzq</property>
				 *
				 * jdbc:mysql://<HOST>:<PORT>/<DB_NAME>?user=<USER_NAME>&password=<PASWORD>
			     * jdbc:mysql://ec2-52-37-3-24.us-west-2.compute.amazonaws.com:3306/<DB_NAME>?user=<USER_NAME>&password=<PASWORD>
				 * jdbc:mysql://192.0.1.134:3306/<DB_NAME>?user=<USER_NAME>&password=<PASWORD>
				 */
				
				/*
				 * CHANGE for version: 1.0.1
				 * SEACLOUDS:
				 * 		connection string comes as jdbc:mysql://<HOST>:<PORT>/<DB_NAME>?user=<USER_NAME>&password=<PASWORD>
				 */
				String seaclouds_conn_str_value =  System.getProperty(connurl);
				if ((seaclouds_conn_str_value == null) || (seaclouds_conn_str_value.isEmpty()))
					seaclouds_conn_str_value = System.getenv(connurl);
				
				String connurlValue = "";
				String userValue = "";
				String passwdValue = "";
				
				StorageComponentMain.scLog("DEBUG", HibernateUtil.class.getName() + " : " + seaclouds_conn_str_value);
				StorageComponentMain.scLog("DEBUG", HibernateUtil.class.getName() + " : parsing values ...");
				if ((seaclouds_conn_str_value != null) && (!seaclouds_conn_str_value.isEmpty()))
				{
					try {
						String[] res1 = seaclouds_conn_str_value.split("user=");
						if ((res1 != null) && (res1.length == 2))
						{
							//
							connurlValue = res1[0].substring(0, res1[0].length() - 1);
							
							//
							String[] user_passwd = res1[1].split("&password=");
							userValue = user_passwd[0];
							StorageComponentMain.scLog("DEBUG", HibernateUtil.class.getName() + " : " + seaclouds_conn_str_value);
							passwdValue = user_passwd[1];;
							StorageComponentMain.scLog("DEBUG", HibernateUtil.class.getName() + " : " + seaclouds_conn_str_value);
							
							StorageComponentMain.scLog("DEBUG", HibernateUtil.class.getName() + " : parsed values : " + connurlValue + " / " + userValue + " / " + passwdValue);
						}
						else
						{
							StorageComponentMain.scLog("ERROR", HibernateUtil.class.getName() + " : Couldn't get user and password from connection string");
						}
					}
					catch (Exception ex)
					{
						StorageComponentMain.logException(ex);
					}
					
					StorageComponentMain.scLog("DEBUG", HibernateUtil.class.getName() + " : Using configuration : " + connurlValue + " / " + userValue + " / " + passwdValue);
					
					configuration.setProperty("hibernate.connection.url", connurlValue);
					configuration.setProperty("hibernate.connection.username", userValue);
					configuration.setProperty("hibernate.connection.password", passwdValue);
				}
				else
				{
					StorageComponentMain.scLog("WARNING", HibernateUtil.class.getName() + " : seaclouds_conn_str_value NULL or EMPTY");
					StorageComponentMain.scLog("DEBUG", HibernateUtil.class.getName() + " : Using default configuration ... ");
					
					// decrypt user / passwd values
					if (StorageComponentMain.HIBERNATE_ENCRYPTION) 
					{
						configuration.setProperty("hibernate.connection.username", 
								DataBasePasswords.decryptHibernateEncryptions(configuration.getProperty("hibernate.connection.username")));   
						
						configuration.setProperty("hibernate.connection.password", 
								DataBasePasswords.decryptHibernateEncryptions(configuration.getProperty("hibernate.connection.password")));   
					}
				}
				
				
				/*
				 *  CHANGE for version: 1.0.1
				 *  SEACLOUDS:
				 *  	next lines are commented for the new version
				 *  
				 */
				/*
				String connurlValue = System.getProperty(connurl);
				if ((connurlValue == null) || (connurlValue.isEmpty()))
					connurlValue = System.getenv(connurl);
				
				String userValue = System.getProperty(user);
				if ((userValue == null) || (userValue.isEmpty()))
					userValue = System.getenv(user);
				
				String passwdValue = System.getProperty(passwd);
				if ((passwdValue == null) || (passwdValue.isEmpty()))
					passwdValue = System.getenv(passwd);
				*/
				
				/*StorageComponentMain.scLog("DEBUG", HibernateUtil.class.getName() + " : Using configuration : " + connurlValue + " / " + userValue + " / " + passwdValue);
				
				configuration.setProperty("hibernate.connection.url", connurlValue);
				configuration.setProperty("hibernate.connection.username", userValue);
				configuration.setProperty("hibernate.connection.password", passwdValue);*/
			}
			// get values from configuration file / code
			else
			{
				// decrypt user / passwd values
				if (StorageComponentMain.HIBERNATE_ENCRYPTION) 
				{
					String user = DataBasePasswords.decryptHibernateEncryptions(configuration.getProperty("hibernate.connection.username"));
					configuration.setProperty("hibernate.connection.username", user);   
					
					String pass = DataBasePasswords.decryptHibernateEncryptions(configuration.getProperty("hibernate.connection.password"));
					configuration.setProperty("hibernate.connection.password", pass);   
				}
			}

			// Database with SSL
			// ?verifyServerCertificate=false&amp;useSSL=true&amp;requireSSL=true
			if (StorageComponentMain.DATABASE_WITH_SSL) 
			{
				StorageComponentMain.scLog("DEBUG", HibernateUtil.class.getName() + " : sessionfactory status : DATABASE_WITH_SSL : setting 'hibernate.connection.url' ...");
				
				String url = configuration.getProperty("hibernate.connection.url");
				//url += "&verifyServerCertificate=false&useSSL=true&requireSSL=true";
				url += "?useSSL=true&requireSSL=true";
				
				configuration.setProperty("hibernate.connection.url", url);  
			}
			
			// DEBUG
			String url = configuration.getProperty("hibernate.connection.url");
			String username = configuration.getProperty("hibernate.connection.username");
			String password = configuration.getProperty("hibernate.connection.password");
			StorageComponentMain.scLog("INFO", HibernateUtil.class.getName() + " ==> URL : " + url + " / " + username + " / " + password);

			// create new sessionFactory
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		catch (Exception ex)
		{
			StorageComponentMain.scLog("ERROR", HibernateUtil.class.getName() + " : Initial SessionFactory creation failed : " + ex.getMessage());
			StorageComponentMain.logException(ex);
		}
	}


	/**
	 * 
	 * @return
	 */
	public static SessionFactory getSessionFactory()
	{
		if (sessionFactory == null)
			buildSessionFactory();
		
		return sessionFactory;
	}
	
		
}
