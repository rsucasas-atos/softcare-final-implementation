package eu.ehealth.db.wservices.users;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.AdministratorInfo;
import eu.ehealth.db.xsd.SearchCriteria;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class ListOfAdministrators extends DbStorageComponent<List<AdministratorInfo>, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public ListOfAdministrators(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<AdministratorInfo> dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			List<SearchCriteria> filter = (List<SearchCriteria>) lo.get(0); 
			String userId = (String) lo.get(1);
			
			List<AdministratorInfo> l = new ArrayList<AdministratorInfo>();
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			for (int i = 0; i < filter.size(); i++)
			{
				filter.set(i, nc.check(filter.get(i), SearchCriteria.class));
			}

			if (!checkUserPermissions("", userId, U_ADMIN))
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

				String sql = "SELECT p.id, p.persondata FROM administrator p LEFT JOIN persondata pd ON (pd.id = p.persondata) LEFT JOIN address a ON (a.persondata = pd.id) LEFT JOIN communication c ON (c.persondata = pd.id) LEFT JOIN identifier i ON (i.persondata = pd.id) WHERE ";

				SearchCriteria[] sc = filter.toArray(new SearchCriteria[filter.size()]);
				for (int i = 0; i < sc.length; i++)
				{
					for (int j = 0; j < fl.size(); j++)
					{
						if (fl.get(j).getName().compareToIgnoreCase(sc[i].getFieldName()) == 0)
						{
							Integer opnum = new Integer(sc[i].getCompareOp().getCode());
							sql += String.format(operationsMap.get(opnum), sc[i].getFieldName(), sc[i]
											.getFieldValue1(), sc[i].getFieldValue2());
							sql += " AND ";
						}
					}
				}
				sql += " 1=1 GROUP BY p.id, p.persondata";

				Object[] ql = _session.createSQLQuery(sql).list().toArray();
				for (int i = 0; i < ql.length; i++)
				{
					Object[] res = (Object[]) ql[i];
					Integer id = (Integer) res[0];
					String pid = ((Integer) res[1]).toString();
					
					eu.ehealth.db.db.Administrator a = 
							(eu.ehealth.db.db.Administrator) _session.load(eu.ehealth.db.db.Administrator.class, id);
					AdministratorInfo ai = new AdministratorInfo();
					ai.setID(a.getId().toString());
					ai.setSurname(a.getM_PersonData().getSurname());
					ai.setName(a.getM_PersonData().getName());
					ai.setPersonID(pid);

					l.add(ai);
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
