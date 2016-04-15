package eu.ehealth.security;

import org.jasypt.digest.PooledStringDigester;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate4.encryptor.HibernatePBEEncryptorRegistry;
import org.jasypt.salt.RandomSaltGenerator;
import org.jasypt.util.password.BasicPasswordEncryptor;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbConstants;


/**
 * 
 * @author a572832
 *
 */
public class DataBasePasswords extends DbConstants
{

	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// JASYPT DATABASE CONFIGURATION
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Register encryption methods for JASYPT functions
	 */
	public static void registerEncryptionMethods() {
		try
		{
			StandardPBEStringEncryptor strongEncryptor = new StandardPBEStringEncryptor();
			strongEncryptor.setPassword(passwordJasyptHibernateDB);
			strongEncryptor.setAlgorithm(algorithmJasyptHibernateDB);
			
			HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
			registry.registerPBEStringEncryptor(registerNameJasyptHibernateDB, strongEncryptor);
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
		}
	}
	
	
	/**
	 * 
	 * @param encryptedValue
	 */
	public static String decryptDBValue(String encryptedValue) {
		try
		{
			StandardPBEStringEncryptor strongEncryptor = new StandardPBEStringEncryptor();
			strongEncryptor.setPassword(passwordJasyptHibernateDB);
			strongEncryptor.setAlgorithm(algorithmJasyptHibernateDB);
			
			return strongEncryptor.decrypt(encryptedValue);
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
			return "";
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// HIBENATE CONFIG FILE ENCRYPTION METHODS
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param encryptedValue
	 * @return
	 */
	public static String decryptHibernateEncryptions(String encryptedValue) {
		try
		{
			StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
			encryptor.setPassword(passwordHibernateCfg);
			encryptor.setAlgorithm(algorithmHibernateCfg);
			
			return encryptor.decrypt(encryptedValue);
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
			return "";
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// USERs PASSWORDS
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/*
	 * 
	 * http://www.jasypt.org/encrypting-passwords.html
	 * http://www.jasypt.org/easy-usage.html
	 * 
	 * BASIC ENCRYPTOR ////////////////////////////////
	 * 
	 */
	
	/**
	 * 
	 * @param password
	 * @return
	 */
	public static String getEncryptedPsswd(String password) {
		try
		{
			BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
			return passwordEncryptor.encryptPassword(password);
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param password
	 * @param psswd_db_encrypted
	 * @return
	 */
	public static boolean checkPsswd(String password, String psswd_db_encrypted) {
		try
		{
			BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
			if (passwordEncryptor.checkPassword(password, psswd_db_encrypted)) {
				return true;
			}
			return false;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
			return false;
		}
	}
	
	
	/*
	 * 
	 * http://www.jasypt.org/encrypting-passwords.html
	 * http://www.jasypt.org/easy-usage.html
	 * 
	 * CUSTOM ENCRYPTOR ////////////////////////////////
	 * 
	 */
	
	/**
	 * 
	 * @author a572832
	 *
	 */
	private static class CustomEncryptor {
		
		
		private static CustomEncryptor enc_instance;
		private static PooledStringDigester digester;
		
		
		private CustomEncryptor() {
			try 
			{
				digester = new PooledStringDigester();
				digester.setPoolSize(poolSizePsswdStoredJasyptHibernateDB);
				digester.setAlgorithm(algorithmPsswdStoredJasyptHibernateDB);
				digester.setIterations(iterationsPsswdStoredJasyptHibernateDB);
				digester.setSaltGenerator(new RandomSaltGenerator());
			}
			catch (Exception ex) 
			{
				StorageComponentMain.logException(ex);
			}
		}
		
		
		public static CustomEncryptor getInstance() {
			if (enc_instance == null)
				enc_instance = new CustomEncryptor();
			
			return enc_instance;
		}
		
		
		public PooledStringDigester getDigester() {
			return digester;
		}
		
	}
	
	
	/**
	 * 
	 * @param password
	 * @return
	 */
	public static String getPooledEncryptedPsswd(String password) {
		try
		{
			return CustomEncryptor.getInstance().getDigester().digest(password);
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param password
	 * @param psswd_db_encrypted
	 * @return
	 */
	public static boolean checkPooledPsswd(String password, String psswd_db_encrypted) {
		try
		{
			if (CustomEncryptor.getInstance().getDigester().matches(password, psswd_db_encrypted)) {
				return true;
			}
			
			return false;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
			return false;
		}
	}
	
	
}
