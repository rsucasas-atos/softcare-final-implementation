package eu.ehealth.security;

import java.io.FileInputStream;
import java.net.URLDecoder;
import eu.ehealth.SystemDictionary;


/**
 * 
 * @author a572832
 *
 */
public class KeyStoreConfig
{

	
	/**
	 * 
	 */
	public void configureSSLParameters() 
	{
		try 
		{
			ClassLoader loader = this.getClass().getClassLoader();
			
			String encodeType = SystemDictionary.CONFIG_PROPERTIES.getString("encoding");
			
			// stores
			String res1 = loader.getResource(SystemDictionary.CONFIG_PROPERTIES.getString("sslTruststore")).getFile();
			res1 = URLDecoder.decode(res1, encodeType);
			SystemDictionary.webguiLog("DEBUG", "sslTruststore : " + res1);
			System.setProperty("javax.net.ssl.trustStore", res1);
			
			String res2 = loader.getResource(SystemDictionary.CONFIG_PROPERTIES.getString("sslKeystore")).getFile();
			res2 = URLDecoder.decode(res2, encodeType);
			SystemDictionary.webguiLog("DEBUG", "sslKeystore : " + res2);
			System.setProperty("javax.net.ssl.keyStore", res2);
			
			// passwords
			System.setProperty("javax.net.ssl.keyStorePassword", SystemDictionary.CONFIG_PROPERTIES.getString("sslKeystorePassword"));
			System.setProperty("javax.net.ssl.trustStorePassword", SystemDictionary.CONFIG_PROPERTIES.getString("sslTruststorePassword"));
			
			String keyAlias = SystemDictionary.CONFIG_PROPERTIES.getString("sslKeyAlias");
			if ((keyAlias != null) && (keyAlias.trim().length() > 0)) 
			{
				System.setProperty("javax.net.ssl.keyAlias", SystemDictionary.CONFIG_PROPERTIES.getString("sslKeyAlias"));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param storeName
	 * @return
	 */
	public FileInputStream getStoreFileInputStream(String storeName) 
	{
		try 
		{
			String encodeType = SystemDictionary.CONFIG_PROPERTIES.getString("encoding");
			
			ClassLoader loader = this.getClass().getClassLoader();
			String res1 = loader.getResource(storeName).getFile();
			
			res1 = URLDecoder.decode(res1, encodeType);
			SystemDictionary.webguiLog("DEBUG", "storeName : " + res1);
			
			return new FileInputStream(res1);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
}
