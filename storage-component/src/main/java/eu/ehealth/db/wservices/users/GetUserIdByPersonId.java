package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class GetUserIdByPersonId extends DbStorageComponent<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetUserIdByPersonId(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			String idv = (String) lo.get(0); 
			int typev = ((Integer) lo.get(1)).intValue(); 
			String userId = (String) lo.get(2);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());

			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			idv = nc.check(idv, String.class);
			userId = nc.check(userId, String.class);

			if (!checkUserPermissions("", userId, U_SERVICE, U_CARER, U_CLINICIAN, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{
				Integer uid = new Integer(idv);
				Integer type = new Integer(typev);

				String sql = "SELECT id FROM aladdinuser WHERE personid = '" + uid.toString() + "' AND type = '" + type.toString() + "'";
				SQLQuery q = _session.createSQLQuery(sql);
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
