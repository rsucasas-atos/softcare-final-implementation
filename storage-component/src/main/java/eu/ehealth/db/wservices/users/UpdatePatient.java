package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.Patient;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class UpdatePatient extends BaseAppUsersOperations
{

	
	/**
	 * 
	 * @param session
	 */
	public UpdatePatient(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			Patient data = (Patient) lo.get(0);
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());			
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			data = nc.check(data, Patient.class);

			if (!checkUserPermissions("uploadData", userId, U_CLINICIAN, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{
				_session.beginTransaction();

				Integer id = new Integer(data.getID());
				eu.ehealth.db.db.Patient p = (eu.ehealth.db.db.Patient) _session.load(eu.ehealth.db.db.Patient.class, id);
				importPersondata(data.getPersonData(), p.getPersondata());
				importSocioDemographic(data.getSDData(), p.getSd());

				p.setCcname(data.getConsulterInCharge().getName());
				p.setCcphone(data.getConsulterInCharge().getPhone());
				p.setCcemail(data.getConsulterInCharge().getEmail());

				p.setSwname(data.getSocialWorker().getName());
				p.setSwphone(data.getSocialWorker().getPhone());
				p.setSwemail(data.getSocialWorker().getEmail());

				p.setGpname(data.getGeneralPractitioner().getName());
				p.setGpphone(data.getGeneralPractitioner().getPhone());
				p.setGpemail(data.getGeneralPractitioner().getEmail());

				if (data.getPatientCarer() != null)
					p.setCarer(new Integer(data.getPatientCarer().getID()));

				_session.update(p);
				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("" + p.getId().toString(), "");
			}
			catch (HibernateException e)
			{
				rollbackSession();

				StorageComponentMain.logException(e);

				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_1.getCode(), " : " + e.toString());
			}

			return res;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
			// set OperationResult values
			return Globals.getOpResult(Globals.ResponseCode.UNKNOWN_ERROR.getCode(), " : " + ex.toString());
		}
	}
	

}
