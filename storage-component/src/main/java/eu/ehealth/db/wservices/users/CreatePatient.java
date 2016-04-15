package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.Consulter;
import eu.ehealth.db.xsd.GeneralPractitioner;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.Patient;
import eu.ehealth.db.xsd.SocialWorker;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class CreatePatient extends BaseAppUsersOperations
{

	
	/**
	 * 
	 * @param session
	 */
	public CreatePatient(Session session)
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
			
			StorageComponentMain.scLog("DEBUG", "CreatePatient CALL ... ");
			StorageComponentMain.scLog("DEBUG", "CreatePatient CALL params : Patient object / " + userId);

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

				eu.ehealth.db.db.Patient p = new eu.ehealth.db.db.Patient();

				Integer pdid = importPersondata(data.getPersonData(), null);

				Integer sdid = importSocioDemographic(data.getSDData(), null);

				GeneralPractitioner gp = data.getGeneralPractitioner();
				if (gp != null)
				{
					p.setGpemail(gp.getEmail());
					p.setGpname(gp.getName());
					p.setGpphone(gp.getPhone());
				}

				Consulter c = data.getConsulterInCharge();
				if (c != null)
				{
					p.setCcemail(c.getEmail());
					p.setCcname(c.getName());
					p.setCcphone(c.getPhone());
				}

				SocialWorker sw = data.getSocialWorker();
				if (sw != null)
				{
					p.setSwemail(sw.getEmail());
					p.setSwname(sw.getName());
					p.setSwphone(sw.getPhone());
				}
				p.setPersondata(pdid);
				p.setSd(sdid);
				String responsibleClinicianID = data.getResponsibleClinicianID();
				if (responsibleClinicianID == null)
					responsibleClinicianID = "0";
				p.setClinician(new Integer(responsibleClinicianID));

				if (data.getPatientCarer() != null)
					p.setCarer(new Integer(data.getPatientCarer().getID()));

				_session.save(p);

				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("" + p.getPersondata().toString(), "");
			}
			catch (Exception e)
			{
				rollbackSession();

				StorageComponentMain.logException(e);

				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_2.getCode(), " : " + e.toString());
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
