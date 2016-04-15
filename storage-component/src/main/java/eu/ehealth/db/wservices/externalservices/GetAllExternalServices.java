package eu.ehealth.db.wservices.externalservices;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.ExternalService;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class GetAllExternalServices extends DbStorageComponent<List<ExternalService>, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetAllExternalServices(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<ExternalService> dbStorageFunction(ArrayList<String> lo)
	{
		List<ExternalService> l = new ArrayList<ExternalService>();
		try
		{
			String userId = (String) lo.get(0);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetAllExternalServices");

			try
			{
				Object[] esl = _session.createQuery("from ExternalService").list().toArray();
				for (int i = 0; i < esl.length; i++)
				{
					eu.ehealth.db.db.ExternalService es = (eu.ehealth.db.db.ExternalService) esl[i];
					ExternalService re = new ExternalService();
					re.setAddress(es.getAddress());
					re.setDescription(es.getDescription());
					re.setID(es.getId().toString());
					re.setType(es.getType());

					l.add(re);
				}
			}
			catch (HibernateException e)
			{
				StorageComponentMain.logException(e);
			}

			return l;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);

			return l;
		}
	}
	

}
