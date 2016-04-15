package eu.ehealth.db.wservices.tasks;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.tasks.dbfunctions.FTasks;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.SystemParameter;
import eu.ehealth.db.xsd.Task;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class AssignTask extends DbStorageComponent<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public AssignTask(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			Task taskv = (Task) lo.get(0);
			SystemParameter locale = (SystemParameter) lo.get(1); 
			String userId = (String) lo.get(2);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : AssignTask");			
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			taskv = nc.check(taskv, Task.class);

			if (!checkUserPermissions("", userId, U_CLINICIAN, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{
				_session.beginTransaction();

				FTasks ft = new FTasks();
				eu.ehealth.db.db.Task task = ft.importTask(taskv);
				_session.save(task);
				
				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("" + task.getId().toString(), "");
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
