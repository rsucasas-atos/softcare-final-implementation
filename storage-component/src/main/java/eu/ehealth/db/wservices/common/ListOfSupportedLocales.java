package eu.ehealth.db.wservices.common;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.SystemParameter;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class ListOfSupportedLocales extends DbStorageComponent<List<SystemParameter>, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public ListOfSupportedLocales(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<SystemParameter> dbStorageFunction(ArrayList<Object> lo)
	{
		List<SystemParameter> lres = new ArrayList<SystemParameter>();
		try
		{
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : ListOfSupportedLocales");

			try
			{
				@SuppressWarnings("unchecked")
				Object[] array = _session.createQuery("from Locale").list().toArray(new eu.ehealth.db.db.Locale[0]);
				eu.ehealth.db.db.Locale[] locale = (eu.ehealth.db.db.Locale[]) array;
				for (int i = 0; i < locale.length; i++)
				{
					SystemParameter l = new SystemParameter();
					l.setCode(locale[i].getId().toString());
					l.setDescription(locale[i].getName());

					lres.add(l);
				}

			}
			catch (Exception e)
			{
				StorageComponentMain.logException(e);
			}

			return lres;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);

			return lres;
		}
	}
	

}
