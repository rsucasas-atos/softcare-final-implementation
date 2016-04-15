package eu.ehealth.db.wservices.measurements;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.Measurement;
import eu.ehealth.db.xsd.SearchCriteria;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class GetMeasurement extends BaseMeasurementsOperations<List<Measurement>, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetMeasurement(Session session)
	{
		super(session);
	}


	@Override
	protected List<Measurement> dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			List<SearchCriteria> filter = (List<SearchCriteria>) lo.get(0);
			String userId = (String) lo.get(1);
			
			List<Measurement> l = new ArrayList<Measurement>();
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetMeasurement");

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			for (int i = 0; i < filter.size(); i++)
			{
				filter.set(i, nc.check(filter.get(i), SearchCriteria.class));
			}

			if (!checkUserPermissions("", userId, U_CARER, U_CLINICIAN, U_ADMIN, U_SERVICE))
			{
				return l;
			}

			try
			{
				Field[] field = eu.ehealth.db.db.Measurement.class.getDeclaredFields();
				String sql = "SELECT id FROM measurement WHERE ";

				SearchCriteria[] sc = filter.toArray(new SearchCriteria[filter.size()]);
				for (int i = 0; i < sc.length; i++)
				{
					for (int j = 0; j < field.length; j++)
					{
						if (field[j].getName().compareToIgnoreCase(sc[i].getFieldName()) == 0)
						{
							Integer opnum = new Integer(sc[i].getCompareOp().getCode());
							sql += String.format(operationsMap.get(opnum), sc[i].getFieldName(), sc[i]
											.getFieldValue1(), sc[i].getFieldValue2());
							sql += " AND ";
						}
					}
				}

				sql += "1 = 1";

				Object[] list = _session.createSQLQuery(sql).list().toArray();

				for (int i = 0; i < list.length; i++)
				{
					Integer id = (Integer) list[i];
					eu.ehealth.db.db.Measurement m = (eu.ehealth.db.db.Measurement) _session.load(eu.ehealth.db.db.Measurement.class, id);
					l.add(exportMeasurement(m));
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

			return null;
		}
	}
	

}
