package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class DeletePatient extends BaseAppUsersOperations
{

	
	/**
	 * 
	 * @param session
	 */
	public DeletePatient(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			String idv = (String) lo.get(0);
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			idv = nc.check(idv, String.class);

			if (!checkUserPermissions("", userId, U_CLINICIAN, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{

				Integer id = new Integer(idv);

				if (id < 1)
					throw new Exception("error");

				_session.beginTransaction();

				eu.ehealth.db.db.Patient p = (eu.ehealth.db.db.Patient) _session.load(eu.ehealth.db.db.Patient.class, id);
				Integer pd = p.getPersondata();
				Integer sd = p.getSd();

				_session.createSQLQuery("DELETE FROM identifier WHERE persondata = (SELECT persondata FROM patient WHERE id = "
						+ id.toString() + ")").executeUpdate();
				_session.createSQLQuery("DELETE FROM address WHERE persondata = (SELECT persondata FROM patient WHERE id = "
						+ id.toString() + ")").executeUpdate();
				_session.createSQLQuery("DELETE FROM communication WHERE persondata = (SELECT persondata FROM patient WHERE id = "
						+ id.toString() + ")").executeUpdate();
				_session.createSQLQuery("DELETE FROM patient WHERE id = " + id.toString()).executeUpdate();
				_session.createSQLQuery("DELETE FROM persondata WHERE id = " + pd.toString()).executeUpdate();
				_session.createSQLQuery("DELETE FROM sociodemographicdata WHERE id = " + sd.toString()).executeUpdate();

				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("" + id.toString(), "");
			}
			catch (HibernateException e)
			{
				rollbackSession();

				StorageComponentMain.logException(e);

				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_1.getCode(), " : " + e.toString());
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
