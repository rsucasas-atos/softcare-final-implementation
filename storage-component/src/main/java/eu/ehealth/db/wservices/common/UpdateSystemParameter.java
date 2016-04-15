package eu.ehealth.db.wservices.common;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.SystemParameter;


/**
 * 
 * @author a572832
 *
 */
public class UpdateSystemParameter extends DbStorageComponent<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public UpdateSystemParameter(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			int typev = ((Integer) lo.get(0)).intValue(); 
			SystemParameter value = (SystemParameter) lo.get(1);
			SystemParameter locale = (SystemParameter) lo.get(2);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : UpdateSystemParameter");	

			OperationResult res = new OperationResult();

			try
			{
				_session.beginTransaction();

				Translations languageTrans = new Translations(_session);
				Integer localeid = languageTrans.getLocaleId(locale.getCode());
				Integer type = new Integer(typev);

				if (value == null || value.getCode() == null || value.getCode() == ""
						|| value.getDescription() == null || value.getDescription() == "" || type == 0)
					throw new Exception("null");

				String sql = "SELECT id FROM dict WHERE code = '"
						+ value.getCode() + "' AND description LIKE '"
						+ value.getDescription() + "' AND type = '"
						+ type.toString() + "' AND locale = '"
						+ localeid.toString() + "'";
				Object[] exist = _session.createSQLQuery(sql).list().toArray();

				eu.ehealth.db.db.Dict dict;

				if (exist.length == 1)
				{
					Integer id = (Integer) ((Object[]) exist[0])[0];
					dict = (eu.ehealth.db.db.Dict) _session.load(eu.ehealth.db.db.Dict.class, id);
				}
				else
					dict = new eu.ehealth.db.db.Dict();

				dict.setCode(value.getCode());
				dict.setDescription(value.getDescription());
				dict.setLocale(localeid);
				dict.setType(type);

				_session.saveOrUpdate(dict);

				// set OperationResult values
				res = Globals.getOpResult("" + dict.getId().toString(), "");

				_session.getTransaction().commit();
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
