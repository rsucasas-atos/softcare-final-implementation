package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.users.dbfunctions.FUsers;
import eu.ehealth.db.xsd.Patient;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class GetPatient extends DbStorageComponent<Patient, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetPatient(Session session)
	{
		super(session);
	}

	
	@Override
	protected Patient dbStorageFunction(ArrayList<String> lo)
	{
		Patient p = new Patient();
		
		try
		{
			String idv = (String) lo.get(0);
			String userId = (String) lo.get(1);

			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());

			NullChecker nc = new NullChecker();

			idv = nc.check(idv, String.class);
			userId = nc.check(userId, String.class);

			if (!checkUserPermissions("accessData", userId, U_CARER, U_CLINICIAN, U_ADMIN))
			{
				return p;
			}

			try
			{
				Integer id = new Integer(idv);
				eu.ehealth.db.db.Patient patient = (eu.ehealth.db.db.Patient) _session.load(eu.ehealth.db.db.Patient.class, id);
				FUsers fu = new FUsers(_session);
				p = fu.exportPatient(patient);
			}
			catch (HibernateException e)
			{
				StorageComponentMain.logException(e);
			}

			return p;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);

			return p;
		}
	}

	
}
