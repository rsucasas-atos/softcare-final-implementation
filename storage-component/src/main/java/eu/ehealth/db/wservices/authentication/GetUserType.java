package eu.ehealth.db.wservices.authentication;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.util.NullChecker;
import eu.ehealth.util.StorageComponentUtilities;


/**
 * 
 * @author a572832
 *
 */
public class GetUserType extends DbStorageComponent<OperationResult, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetUserType(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<String> lo)
	{
		try
		{
			String idv = lo.get(0);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());

			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			idv = nc.check(idv, String.class);

			try
			{
				StorageComponentUtilities.checkIP();
				Integer id = new Integer(idv);
				String sql = "SELECT type FROM aladdinuser WHERE id = '" + id.toString() + "'";
				SQLQuery q = _session.createSQLQuery(sql);
				if (q.list().size() == 1)
				{
					res.setCode(q.list().get(0).toString());
					res.setDescription("ok");
					res.setStatus((short) 1);
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
			catch (Exception e)
			{
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
