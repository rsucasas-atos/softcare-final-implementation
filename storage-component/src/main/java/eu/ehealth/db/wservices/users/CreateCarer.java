package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.Carer;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class CreateCarer extends BaseAppUsersOperations
{

	
	/**
	 * 
	 * @param session
	 */
	public CreateCarer(Session session)
	{
		super(session);
	}
	

	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			Carer data = (Carer) lo.get(0);
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());	
			
			StorageComponentMain.scLog("DEBUG", "CreateCarer CALL ... ");
			StorageComponentMain.scLog("DEBUG", "CreateCarer CALL params : Carer object / " + userId);
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			data = nc.check(data, Carer.class);

			if (!checkUserPermissions("", userId, U_CLINICIAN, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{
				_session.beginTransaction();

				eu.ehealth.db.db.Carer p = new eu.ehealth.db.db.Carer();

				Integer pdid = importPersondata(data.getPersonData(), null);

				Integer sdid = importSocioDemographic(data.getSDData(), null);

				p.setPersondata(pdid);
				p.setSd(sdid);
				_session.save(p);

				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("" + p.getPersondata().toString(), "");
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
