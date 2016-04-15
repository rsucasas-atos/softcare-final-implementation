package eu.ehealth.db.wservices.tasks;

import java.math.BigInteger;
import java.util.ArrayList;
import javax.xml.datatype.XMLGregorianCalendar;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class RemoveTaskMassively extends DbStorageComponent<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public RemoveTaskMassively(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			String patientIdv = (String) lo.get(0);
			BigInteger typeOfTask = (BigInteger) lo.get(1); 
			XMLGregorianCalendar startDate = (XMLGregorianCalendar) lo.get(2);
			XMLGregorianCalendar endDate = (XMLGregorianCalendar) lo.get(3); 
			String userId = (String) lo.get(4);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : RemoveTaskMassively");		
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			patientIdv = nc.check(patientIdv, String.class);
			userId = nc.check(userId, String.class);

			if (!checkUserPermissions("", userId, U_CARER, U_CLINICIAN, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{
				Integer patientId = new Integer(patientIdv);

				String sql = "DELETE FROM task WHERE datetimeassigned >= '"
						+ startDate.toString() + "' AND datetimeassigned <= '"
						+ endDate.toString() + "' AND tasktype = '"
						+ typeOfTask.toString() + "' AND object = '"
						+ patientId.toString() + "' AND taskstatus != 3";

				_session.beginTransaction();
				_session.createSQLQuery(sql).executeUpdate();
				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("0", "");
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
