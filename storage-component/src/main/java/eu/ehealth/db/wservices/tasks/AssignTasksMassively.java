package eu.ehealth.db.wservices.tasks;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.tasks.dbfunctions.FTasks;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.Task;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 13/03/2014.
 *
 */
public class AssignTasksMassively extends DbStorageComponent<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public AssignTasksMassively(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			Task rtask = (Task) lo.get(0);
			XMLGregorianCalendar startDate = (XMLGregorianCalendar) lo.get(1); 
			XMLGregorianCalendar endDate = (XMLGregorianCalendar) lo.get(2);
			BigInteger frequencyv = (BigInteger) lo.get(3); 
			String userId = (String) lo.get(4);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : AssignTasksMassively");		
			StorageComponentMain.scLog("DEBUG", "frequencyv: " + frequencyv.intValue());
			StorageComponentMain.scLog("DEBUG", "startDate : " + startDate.toString());
			StorageComponentMain.scLog("DEBUG", "endDate   : " + endDate.toString());
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			if (!checkUserPermissions("", userId, U_CLINICIAN, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{
				int frequency = frequencyv.intValue();

				_session.beginTransaction();

				FTasks ft = new FTasks();
				while (startDate.toGregorianCalendar().compareTo(endDate.toGregorianCalendar()) < 0)
				{
					rtask.setDateTimeAssigned(startDate);
					eu.ehealth.db.db.Task task = ft.importTask(rtask);
					_session.save(task);
					//startDate.toGregorianCalendar().add(Calendar.DAY_OF_YEAR, frequency);
					
					// XMLGregorianCalendar to GregorianCalendar
					GregorianCalendar gCalStart = startDate.toGregorianCalendar();
					// Add period / frequency
					gCalStart.add(Calendar.DAY_OF_YEAR, frequency);
					// GregorianCalendar to XMLGregorianCalendar
					startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalStart);
				}

				_session.getTransaction().commit();

				res.setCode("0");
				res.setStatus((short) 1);
				res.setDescription("ok");

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
