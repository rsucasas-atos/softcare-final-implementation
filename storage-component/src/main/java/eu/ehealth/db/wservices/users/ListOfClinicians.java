package eu.ehealth.db.wservices.users;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.ClinicianInfo;
import eu.ehealth.db.xsd.SearchCriteria;
import eu.ehealth.util.NullChecker;
import eu.ehealth.util.threading.DecryptClinicianInfoTask;


/**
 * 
 * @author a572832
 *
 */
public class ListOfClinicians extends DbStorageComponent<List<ClinicianInfo>, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public ListOfClinicians(Session session)
	{
		super(session);
	}
	

	@Override
	protected List<ClinicianInfo> dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			List<SearchCriteria> filter = (List<SearchCriteria>) lo.get(0); 
			String userId = (String) lo.get(1);
			
			List<ClinicianInfo> l = new ArrayList<ClinicianInfo>();

			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			for (int i = 0; i < filter.size(); i++)
			{
				filter.set(i, nc.check(filter.get(i), SearchCriteria.class));
			}

			if (!checkUserPermissions("", userId, U_CLINICIAN, U_ADMIN))
			{
				return l;
			}

			try
			{
				List<Field> fl = new ArrayList<Field>();
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.PersonData.class.getDeclaredFields()));
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.Address.class.getDeclaredFields()));
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.Communication.class.getDeclaredFields()));
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.Identifier.class.getDeclaredFields()));

				String sql = "SELECT p.id, pd.name, pd.surname FROM clinician p " +
							 "LEFT JOIN persondata pd ON (pd.id = p.persondata) " +
							 "LEFT JOIN address a ON (a.persondata = pd.id) " + 
							 "LEFT JOIN communication c ON (c.persondata = pd.id) " + 
							 "LEFT JOIN identifier i ON (i.persondata = pd.id) " + 
							 "WHERE ";

				SearchCriteria[] sc = filter.toArray(new SearchCriteria[filter.size()]);
				for (int i = 0; i < sc.length; i++)
				{
					if (sc[i].getFieldName() == null)
						continue;

					for (int j = 0; j < fl.size(); j++)
					{
						if (fl.get(j).getName().compareToIgnoreCase(sc[i].getFieldName()) == 0)
						{
							Integer opnum = new Integer(sc[i].getCompareOp().getCode());
							sql += String.format(operationsMap.get(opnum), sc[i].getFieldName(), sc[i].getFieldValue1(), sc[i].getFieldValue2());
							sql += " AND ";
						}
					}
				}
				sql += " 1=1 GROUP BY p.id, p.persondata";

				List<Object[]> ql = _session.createSQLQuery(sql).list();

				ForkJoinPool pool = new ForkJoinPool();
				DecryptClinicianInfoTask task = new DecryptClinicianInfoTask(ql);
				l = pool.invoke(task);
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
	/*
	protected List<ClinicianInfo> dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			List<SearchCriteria> filter = (List<SearchCriteria>) lo.get(0); 
			String userId = (String) lo.get(1);
			
			List<ClinicianInfo> l = new ArrayList<ClinicianInfo>();

			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : ListOfClinicians");

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			for (int i = 0; i < filter.size(); i++)
			{
				filter.set(i, nc.check(filter.get(i), SearchCriteria.class));
			}

			if (!checkUserPermissions("", userId, U_CLINICIAN, U_ADMIN))
			{
				return l;
			}

			try
			{
				List<Field> fl = new ArrayList<Field>();
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.PersonData.class.getDeclaredFields()));
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.Address.class.getDeclaredFields()));
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.Communication.class.getDeclaredFields()));
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.Identifier.class.getDeclaredFields()));

				String sql = "SELECT p.id FROM clinician p LEFT JOIN persondata pd ON (pd.id = p.persondata) LEFT JOIN address a ON (a.persondata = pd.id) LEFT JOIN communication c ON (c.persondata = pd.id) LEFT JOIN identifier i ON (i.persondata = pd.id) WHERE ";

				SearchCriteria[] sc = filter.toArray(new SearchCriteria[filter.size()]);
				for (int i = 0; i < sc.length; i++)
				{
					if (sc[i].getFieldName() == null)
						continue;

					for (int j = 0; j < fl.size(); j++)
					{
						if (fl.get(j).getName().compareToIgnoreCase(sc[i].getFieldName()) == 0)
						{
							Integer opnum = new Integer(sc[i].getCompareOp().getCode());
							sql += String.format(operationsMap.get(opnum), sc[i].getFieldName(), sc[i].getFieldValue1(), sc[i].getFieldValue2());
							sql += " AND ";
						}
					}
				}
				sql += " 1=1 GROUP BY p.id, p.persondata";

				Object[] ql = _session.createSQLQuery(sql).list().toArray();
				for (int i = 0; i < ql.length; i++)
				{
					Integer id = (Integer) ql[i];
					eu.ehealth.db.db.Clinician p = (eu.ehealth.db.db.Clinician) _session.load(eu.ehealth.db.db.Clinician.class, id);
					ClinicianInfo qi = new ClinicianInfo();
					qi.setID(p.getId().toString());
					qi.setSurname(p.getM_PersonData().getSurname());
					qi.setName(p.getM_PersonData().getName());

					l.add(qi);
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
	 */
	

}
