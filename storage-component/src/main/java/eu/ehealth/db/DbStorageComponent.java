package eu.ehealth.db;

import java.util.ArrayList;
import java.util.HashMap;
//import org.apache.cxf.phase.PhaseInterceptorChain;
import org.hibernate.Session;
import org.hibernate.TransactionException;
//import com.atos.publishformatedevent.RequestEvidence;
//import com.atos.publishformatedevent.ResponseEvidence;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.server.context.WSServletContextListener;


/**
 * 
 * @author a572832
 *
 * @param <R> Response type of dbStorageFunction method 
 * @param <P> Parameter ArrayList type of dbStorageFunction method 
 */
public abstract class DbStorageComponent<R, P>
{
	
	
	/**
	 * Instance of the session
	 */
	protected Session _session;
	
	/**
	 * Constant for the Carer type
	 */
	public final static int U_CARER = 3;
	
	/**
	 * Constant for the Patient type
	 */
	public final static int U_PATIENT = 4;
	
	/**
	 * Constant for the Clinician type
	 */
	public final static int U_CLINICIAN = 2;
	
	/**
	 * Constant for the Admin type
	 */
	public final static int U_ADMIN = 1;
	
	/**
	 * Constant for the Service type
	 */
	public final static int U_SERVICE = 5;
	
	/**
	 * Constant for "<"
	 */
	public final static int OP_LESS = 1;
	
	/**
	 * Constant for ">"
	 */
	public final static int OP_GREAT = 2;
	
	/**
	 * Constant for "="
	 */
	public final static int OP_EQ = 3;
	
	/**
	 * Constant for "!="
	 */
	public final static int OP_NOTEQ = 4;
	
	/**
	 * Constant for "like"
	 */
	public final static int OP_LIKE = 5;
	
	/**
	 * Constant for "between"
	 */
	public final static int OP_BETWEEN = 7;
	
	/**
	 * Map of code of operation => string representation
	 */
	public static HashMap<Integer, String> operationsMap;
	
	/**
	 * 
	 */
	static
	{
		operationsMap = new HashMap<Integer, String>();
		operationsMap.put(OP_LESS, " %s < '%s' ");
		operationsMap.put(OP_GREAT, " %s > '%s' ");
		operationsMap.put(OP_EQ, " %s = '%s' ");
		operationsMap.put(OP_NOTEQ, " %s != '%s' ");
		operationsMap.put(OP_LIKE, "%s like '%s' ");
		operationsMap.put(OP_BETWEEN, " %s BETWEEN '%s' AND '%s' ");
	}
	
	/**
	 * URI of the Forum sc.php
	 */
	public final static String forumSC = "http://dafnis.atosorigin.es/aladdin/phpBB3/includes/sc.php";
	
	
	/**
	 * Constructor
	 * @param session
	 * @param dbHelper
	 */
	public DbStorageComponent(Session session)
	{
		_session = session;
	}
	
	
	/**
	 * Executes database operations
	 * @param lo
	 * @return
	 */
	public R execute(ArrayList<P> lo) {
		// init function : WEB SERVICE
		init();

		try 
		{
			// execute method : WEB SERVICE
			return dbStorageFunction(lo);
		}
		finally 
		{
			// end function : WEB SERVICE
			end();
		}
	}

	
	/**
	 * 
	 * @param lo
	 * @return
	 */
	protected abstract R dbStorageFunction(ArrayList<P> lo);
	
	
	/**
	 * Inits session
	 */
	private void init()
	{
		try
		{
			if (!_session.isConnected())
			{
				_session = HibernateUtil.getSessionFactory().openSession();
			}
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
		}
	}


	/**
	 * Flush & close session
	 */
	private void end()
	{
		try
		{
			_session.flush();
			_session.close();
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
			try {
				_session.close();
			} catch (Exception ex1) {}
		}
	}
	
	
	/**
	 * 
	 * @param cumulus_field
	 * @param userId
	 * @param userTypes
	 * @return
	 */
	protected boolean checkUserPermissions(String cumulus_field, String userId, int... userTypes)
	{
		if (userId == null || userId == "") 
		{
			StorageComponentMain.scLog("DEBUG", "checkUserPermissions : userId is null ");
			return false;
		}
		
		boolean res = WSServletContextListener.hasAccessToOperation(userId, userTypes);
		if (!res) {
			StorageComponentMain.scLog("WARN", "User without permissions. Looking in database for user '" + userId + "' ...");
			res = checkUserPermissionsDB(userId, userTypes);
		}
		
		return res;
	}
	
	
	/**
	 * 
	 * @param userId
	 * @param userTypes
	 * @return
	 */
	private boolean checkUserPermissionsDB(String userId, int... userTypes)
	{
		try
		{
			String sql = "";
			
			if(userTypes.length > 1) 
			{
				String typeWHEREClause = "AND (";
				for (int i=0, max=userTypes.length; i<max; i++) {
					if (i == 0) {
						typeWHEREClause += "type = '" + userTypes[i] + "' ";
					}
					else {
						typeWHEREClause += "OR type = '" + userTypes[i] + "' ";
					}
				}
				typeWHEREClause += ")";
				
				sql = "SELECT * FROM aladdinuser WHERE id = '" + userId + "' " + typeWHEREClause;
			}
			else if(userTypes.length == 1) 
			{
				sql = "SELECT * FROM aladdinuser WHERE id = '" + userId + "' AND type = '" + userTypes[0] + "'";
			}
			
			StorageComponentMain.scLog("DEBUG", "checkUserPermissions : sql : " + sql);

			int size = _session.createSQLQuery(sql).list().size();
			return (size > 0);
		}
		catch (Exception e)
		{
			StorageComponentMain.logException(e);
			return false;
		}
	}

	
	/**
	 * 
	 */
	protected void rollbackSession()  
	{
		try
		{
			StorageComponentMain.scLog("INFO", "Calling rollbackSession method");
			
			if (_session.getTransaction().isActive()) {
				_session.getTransaction().rollback();
				StorageComponentMain.scLog("INFO", "RollbackSession made");
			}
		}
		catch (TransactionException e2) { 
			StorageComponentMain.logException(e2);
		}
	}
	
	
}
