import java.io.File;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.namespace.QName;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import eu.ehealth.SystemDictionary;
import eu.ehealth.ws_client.StorageComponentImplService;
import org.apache.commons.validator.routines.UrlValidator;



/**
 * 
 * @author a572832
 *
 */
public class Main
{

	
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args)
	{
		try 
		{
			
			String s1 = "http://localhost:9080/softcare-ws/StorageComponent?wsdl";
			
			s1 = s1.substring(0, s1.lastIndexOf("/"));
			
			/*try 
			{
				// URL Validator
				URL u = new URL ("https://ehealthws.85.214.217.197.xip.io/StorageComponent?wsdl=StorageComponent.wsdl");
				HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
				
				//TrustModifier.disableCertificateValidation();
				//TrustModifier.relaxHostChecking(huc); // here's where the magic happens
				
				
				*
				-Djavax.net.ssl.keyStoreType=pkcs12
				-Djavax.net.ssl.trustStoreType=jks
				-Djavax.net.ssl.keyStore=clientcertificate.p12
				-Djavax.net.ssl.trustStore=gridserver.keystore
				-Djavax.net.debug=ssl # very verbose debug
				-Djavax.net.ssl.keyStorePassword=$PASS
				-Djavax.net.ssl.trustStorePassword=$PASS
				
				System.setProperty("javax.net.ssl.keyStore", "C:\\Users\\A572832\\git\\webgui\\WebGUI_v2\\src\\keystore.jks");
				System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\A572832\\git\\webgui\\WebGUI_v2\\src\\keystore.jks");
				System.setProperty("javax.net.ssl.keyStorePassword", "password");
				System.setProperty("javax.net.ssl.trustStorePassword", "password");
				*
				System.setProperty("javax.net.ssl.trustStore", "C:\\certsCF");
				System.setProperty("javax.net.ssl.trustStorePassword", "1234qwer");
				
				huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD"); 
				huc.connect () ; 
				int code = huc.getResponseCode() ;
				System.out.println(code);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}*/
			
			
			try {
				System.setProperty("javax.net.ssl.trustStore", "C:\\certsCF");
				System.setProperty("javax.net.ssl.trustStorePassword", "1234qwer");
				
				QName SERVICE_NAME = new QName("http://ehealth.eu/", "StorageComponentImplService");
				String _serviceURL = "file:C:\\TMP\\StorageComponent.wsdl"; //"https://ehealthws.85.214.217.197.xip.io/StorageComponent?wsdl"; //"http://localhost:8080/cxf-hibernate-ws/StorageComponent?wsdl";
				URL wsdlURL = new URL(_serviceURL); 
				StorageComponentImplService ss = new StorageComponentImplService(wsdlURL, SERVICE_NAME);

				List l = ss.getStorageComponentImplPort().listOfSupportedLocales();
				
				String s = "asdasd";
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
			
			String s = "";
			
			
			
			
			
			
			
			/*
			
			try {
				ClassLoader loader = Main.class.getClassLoader();
				
				String res1 = loader.getResource(SystemDictionary.CONFIG_PROPERTIES.getString("sslTruststore")).getFile();
				res1 = URLDecoder.decode(res1, "UTF-8");
				SystemDictionary.webguiLog("DEBUG", "sslTruststore : " + res1);
				System.setProperty("javax.net.ssl.trustStore", res1);
				
				String res2 = loader.getResource(SystemDictionary.CONFIG_PROPERTIES.getString("sslKeystore")).getFile();
				res2 = URLDecoder.decode(res2, "UTF-8");
				SystemDictionary.webguiLog("DEBUG", "sslKeystore : " + res2);
				System.setProperty("javax.net.ssl.keyStore", res2);
				
				
				
				
				String classpath = System.getProperty("java.class.path");
				String classpath2 = new File("").getAbsolutePath() + File.separator + "src" + File.separator + "keystore.jks";

				File f = new File(classpath2);
				
				if (f.exists())
				{
					String s = "";
				}
				else {
					String s1 = "";
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
			
			
			
			try 
			{
				// URL Validator
				URL u = new URL ("https://localhost:8443/cxf-hibernate-ws/StorageComponent?wsdl");
				HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
				
				//TrustModifier.disableCertificateValidation();
				//TrustModifier.relaxHostChecking(huc); // here's where the magic happens
				
				
				/*
				-Djavax.net.ssl.keyStoreType=pkcs12
				-Djavax.net.ssl.trustStoreType=jks
				-Djavax.net.ssl.keyStore=clientcertificate.p12
				-Djavax.net.ssl.trustStore=gridserver.keystore
				-Djavax.net.debug=ssl # very verbose debug
				-Djavax.net.ssl.keyStorePassword=$PASS
				-Djavax.net.ssl.trustStorePassword=$PASS
				*
				System.setProperty("javax.net.ssl.keyStore", "C:\\Users\\A572832\\git\\webgui\\WebGUI_v2\\src\\keystore.jks");
				System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\A572832\\git\\webgui\\WebGUI_v2\\src\\keystore.jks");
				System.setProperty("javax.net.ssl.keyStorePassword", "password");
				System.setProperty("javax.net.ssl.trustStorePassword", "password");
				
				huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD"); 
				huc.connect () ; 
				int code = huc.getResponseCode() ;
				System.out.println(code);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			/*
			
			
			
			
			
			// insert new admin user
			
			
			
			
			
			
			
			
			
			
			// pruebas
			String login = "admin";
			String psswd = "password";
			String skey = "";
			
			System.out.println("- login :" + login);
			System.out.println("- psswd :" + psswd);
			System.out.println("- token :" + skey);
			System.out.println("-------------------");
			try 
			{
				System.out.println("ENCRYPTION ....");
				
				// generate secret key using DES algorithm
				SecretKey key = KeyGenerator.getInstance("DES").generateKey();
				Cipher ecipher = Cipher.getInstance("DES");
				
				// initialize the ciphers with the given key
				ecipher.init(Cipher.ENCRYPT_MODE, key);

				//
				byte[] utf8 = psswd.getBytes("UTF8");
				byte[] enc = ecipher.doFinal(utf8);
				enc = BASE64EncoderStream.encode(enc);
				
				psswd = new String(enc);
				skey = new Main().bytesToString(key.getEncoded());
				
				System.out.println("- login :" + login);
				System.out.println("- psswd :" + psswd);
				System.out.println("- token :" + skey);
				System.out.println("-------------------");
				
				//
				System.out.println("DECRYPTION ....");
				
				Cipher dcipher = Cipher.getInstance("DES");

				byte[] enc2 = new Main().stringToBytes(skey);
				
				//stringKey = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT)
				SecretKey originalKey = new SecretKeySpec(enc2, 0, enc2.length, "DES");
				
				dcipher.init(Cipher.DECRYPT_MODE, originalKey);
				
				//String decrypted = decrypt(encrypted);
				
				// decode with base64 to get bytes
				byte[] dec = BASE64DecoderStream.decode(psswd.getBytes());
				byte[] utf82 = dcipher.doFinal(dec);

				// create new string based on the specified charset
				String decrypted =  new String(utf82, "UTF8");

				System.out.println("- login :" + login);
				System.out.println("- psswd :" + decrypted);
				System.out.println("- token :" + skey);
				System.out.println("-------------------");
			} 
			catch (NoSuchAlgorithmException e) 
			{
	            e.printStackTrace();
	        }
	        catch (NoSuchPaddingException e) 
	        {
	        	e.printStackTrace();
	        }
	        catch (InvalidKeyException e) 
	        {
	        	e.printStackTrace();
	        }
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			*/
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	
	
	private String bytesToString(byte[] b) {
	    byte[] b2 = new byte[b.length + 1];
	    b2[0] = 1;
	    System.arraycopy(b, 0, b2, 1, b.length);
	    return new BigInteger(b2).toString(36);
	}
	
	
	private byte[] stringToBytes(String s) {
	    byte[] b2 = new BigInteger(s, 36).toByteArray();
	    return Arrays.copyOfRange(b2, 1, b2.length);
	}
	
	
}
