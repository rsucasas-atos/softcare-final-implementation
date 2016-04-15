import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;


/**
 * 
 * @author 
 * 
 */
public class Main
{


	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			// ENCRYPTION
			/**/
			String val = "password";
			System.out.println(val);

			StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
			encryptor.setPassword("jasypt");
			encryptor.setAlgorithm("PBEWITHSHA1ANDDESEDE");

			String encryptedVal = encryptor.encrypt(val);
			System.out.println(encryptedVal);

			String decryptedVal = encryptor.decrypt(encryptedVal);
			System.out.println(decryptedVal);
			/**/
			
			
			// OTRAS PRUEBAS
			long time_start, time_end;
			
			time_start = System.currentTimeMillis();

			time_end = System.currentTimeMillis();
			System.out.println("the task '' has taken "+ ( time_end - time_start ) +" milliseconds");
			
			
			String enc1= "U2gixnWHdLsCbahKoaTCFQ==";
			StandardPBEStringEncryptor strongEncryptor = new StandardPBEStringEncryptor();
			//HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
		    //registry.registerPBEStringEncryptor("strongHibernateStringEncryptor", strongEncryptor);
		    
		    strongEncryptor.setPassword("jasypt");
		    strongEncryptor.setAlgorithm("PBEWithMD5AndTripleDES");
		    String plainText = strongEncryptor.decrypt(enc1);
		    System.out.println(plainText);
			
			StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
			textEncryptor.setPassword("jasypt");
			String plainText2 = textEncryptor.decrypt(enc1);
			
			System.out.println(plainText2);
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
