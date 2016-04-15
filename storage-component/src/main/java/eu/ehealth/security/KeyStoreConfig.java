package eu.ehealth.security;

import java.io.FileInputStream;
import java.net.URLDecoder;
import org.apache.commons.configuration.PropertiesConfiguration;
import eu.ehealth.StorageComponentMain;


/**
 * 
 * @author a572832
 *
 */
public class KeyStoreConfig
{

	
	/**
	 * 
	 * @param props
	 * @return
	 */
	public boolean configureSSLParameters(PropertiesConfiguration props) 
	{
		try 
		{
			ClassLoader loader = this.getClass().getClassLoader();
			
			String encodeType = props.getString("encoding");
			
			// stores
			String res1 = loader.getResource(props.getString("dbSslTruststore")).getFile();
			res1 = URLDecoder.decode(res1, encodeType);
			StorageComponentMain.scLog("DEBUG", "dbSslTruststore : " + res1);
			System.setProperty("javax.net.ssl.trustStore", res1);
			
			String res2 = loader.getResource(props.getString("dbSslKeystore")).getFile();
			res2 = URLDecoder.decode(res2, encodeType);
			StorageComponentMain.scLog("DEBUG", "dbSslKeystore : " + res2);
			System.setProperty("javax.net.ssl.keyStore", res2);
			
			// passwords
			System.setProperty("javax.net.ssl.keyStorePassword", props.getString("dbSslKeystorePassword"));
			System.setProperty("javax.net.ssl.trustStorePassword", props.getString("dbSslTruststorePassword"));
			
			String keyAlias = props.getString("dbSslKeyAlias");
			if ((keyAlias != null) && (keyAlias.trim().length() > 0)) 
			{
				System.setProperty("javax.net.ssl.keyAlias", props.getString("dbSslKeyAlias"));
			}
			
			return true;
		}
		catch (Exception ex) {
			StorageComponentMain.logException(ex);
			
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param props
	 * @param storeName
	 * @return
	 */
	public FileInputStream getStoreFileInputStream(PropertiesConfiguration props, String storeName) 
	{
		try 
		{
			String encodeType = props.getString("encoding");
			
			ClassLoader loader = this.getClass().getClassLoader();
			String res1 = loader.getResource(storeName).getFile();
			
			res1 = URLDecoder.decode(res1, encodeType);
			StorageComponentMain.scLog("DEBUG", "storeName : " + res1);
			
			return new FileInputStream(res1);
		}
		catch (Exception ex) {
			StorageComponentMain.logException(ex);
			return null;
		}
	}
	
	
}
