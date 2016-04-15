package eu.ehealth.db.wservices.common;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.SystemParameter;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class GetSystemParameterList extends DbStorageComponent<List<SystemParameter>, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetSystemParameterList(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<SystemParameter> dbStorageFunction(ArrayList<Object> lo)
	{
		List<SystemParameter> l = new ArrayList<SystemParameter>();
		try
		{
			int typev = ((Integer) lo.get(0)).intValue();
			SystemParameter locale = (SystemParameter) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetSystemParameterList");

			try
			{
				Integer type = new Integer(typev);

				if (locale == null)
					locale = new SystemParameter();
				if (locale.getCode() == null || locale.getCode() == "")
					locale.setCode("en_UK");

				String sql = "SELECT code, description FROM dict as d INNER JOIN locale as l ON (l.id = d.locale) WHERE d.type = '"
						+ type.toString() + "' AND l.name = '" + locale.getCode() + "'";
				Object[] ret = _session.createSQLQuery(sql).list().toArray();

				for (int i = 0; i < ret.length; i++)
				{
					Object[] obj = (Object[]) ret[i];
					SystemParameter sp = new SystemParameter();
					sp.setCode(obj[0].toString());
					sp.setDescription(obj[1].toString());

					l.add(sp);
				}
			}
			catch (HibernateException e)
			{
				StorageComponentMain.logException(e);
			}

			return l;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);

			return l;
		}
	}
	

}
