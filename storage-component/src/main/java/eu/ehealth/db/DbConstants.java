package eu.ehealth.db;

import eu.ehealth.StorageComponentMain;


/**
 * 
 * @author a572832
 *
 */
public class DbConstants
{
	
	
	// DIGEST ALGORITHMS:   [MD2, MD5, SHA, SHA-256, SHA-384, SHA-512]
	// PBE ALGORITHMS:      [PBEWITHMD5ANDDES, PBEWITHMD5ANDTRIPLEDES, PBEWITHSHA1ANDDESEDE, PBEWITHSHA1ANDRC2_40]
	
	protected static final String passwordJasyptHibernateDB = "jasypt";
	protected static final String algorithmJasyptHibernateDB = "PBEWithMD5AndTripleDES";
	protected static final String registerNameJasyptHibernateDB = "strongHibernateStringEncryptor";
	
	protected static final String passwordHibernateCfg = "jasypt";
	protected static final String algorithmHibernateCfg = "PBEWITHSHA1ANDDESEDE";
	
	protected static int poolSizePsswdStoredJasyptHibernateDB = 4;
	protected static final String algorithmPsswdStoredJasyptHibernateDB = "SHA-1";
	protected static final int iterationsPsswdStoredJasyptHibernateDB = 100;
	
	
	static 
	{
		try 
		{
			poolSizePsswdStoredJasyptHibernateDB = Runtime.getRuntime().availableProcessors();
		}
		catch (Exception ex) 
		{
			StorageComponentMain.logException(ex);
		}
	}

	
}
