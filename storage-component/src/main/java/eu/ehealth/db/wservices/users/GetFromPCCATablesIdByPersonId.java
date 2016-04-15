package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.OperationResult;


/**
 * Given a personid value and a table name, this class gets the id from Pacient / Carer / Clinician / Administrator tables.
 * 
 * @author a572832
 * @date 21/03/2014.
 *
 */
public class GetFromPCCATablesIdByPersonId extends DbStorageComponent<OperationResult, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetFromPCCATablesIdByPersonId(Session session)
	{
		super(session);
	}
	
	
	/**
	 * 
	 */
	public GetFromPCCATablesIdByPersonId() 
	{
		super(null);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<String> lo)
	{
		return execute(_session, lo);
	}
	
	
	/**
	 * 
	 * @param session
	 * @param lo
	 * @return
	 */
	public OperationResult execute(Session session, ArrayList<String> lo)
	{
		try
		{
			String personId = (String) lo.get(0); 
			String tableName = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());

			OperationResult res = new OperationResult();

			try
			{
				String sql = "SELECT t.id FROM " + tableName + " t WHERE t.persondata = " + personId;
				SQLQuery q = session.createSQLQuery(sql);
				Object[] obj = q.list().toArray();
				if (obj.length == 1)
				{
					res.setCode(obj[0].toString());
					res.setStatus((short) 1);
					res.setDescription("ok");
				}
				else
				{
					res.setCode("0");
					res.setDescription("none");
					res.setStatus((short) 0);
				}
			}
			catch (HibernateException e)
			{
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
			return Globals.getOpResult(Globals.ResponseCode.UNKNOWN_ERROR.getCode(), " : " + ex.getMessage());
		}
	}
	

}
