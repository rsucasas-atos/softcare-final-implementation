package eu.ehealth.db.wservices.users;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.PatientInfo;
import eu.ehealth.db.xsd.SearchCriteria;
import eu.ehealth.util.NullChecker;
import eu.ehealth.util.threading.DecryptPatientInfoTask;


/**
 * 
 * @author a572832
 *
 */
public class ListOfPatientsFromDoctor extends DbStorageComponent<List<PatientInfo>, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public ListOfPatientsFromDoctor(Session session)
	{
		super(session);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	protected List<PatientInfo> dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			List<SearchCriteria> filter = (List<SearchCriteria>) lo.get(0); 
			String userId = (String) lo.get(1);
			String clinicianId = (String) lo.get(2);
			
			List<PatientInfo> l = new ArrayList<PatientInfo>();
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			for (int i = 0; i < filter.size(); i++)
			{
				filter.set(i, nc.check(filter.get(i), SearchCriteria.class));
			}

			if (!checkUserPermissions("", userId, U_CLINICIAN, U_CARER, U_ADMIN))
			{
				return l;
			}

			try
			{
				List<Field> fl = new ArrayList<Field>();
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.PersonData.class.getDeclaredFields()));
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.SocioDemographicData.class.getDeclaredFields()));
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.Address.class.getDeclaredFields()));
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.Communication.class.getDeclaredFields()));
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.Identifier.class.getDeclaredFields()));
				fl.addAll(java.util.Arrays.asList(eu.ehealth.db.db.Patient.class.getDeclaredFields()));

				String sql = "SELECT p.id, pd.name, pd.surname, p.clinician FROM patient p " + 
							 "LEFT JOIN persondata pd ON (pd.id = p.persondata) " + 
							 "LEFT JOIN address a ON (a.persondata = pd.id) " + 
							 "LEFT JOIN communication c ON (c.persondata = pd.id) " + 
							 "LEFT JOIN identifier i ON (i.persondata = pd.id) " + 
							 "LEFT JOIN sociodemographicdata sd ON (sd.id = p.sd) " + 
							 "WHERE p.clinician=" + clinicianId + " ";

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
							sql += String.format(operationsMap.get(opnum), sc[i].getFieldName(), sc[i]
											.getFieldValue1(), sc[i].getFieldValue2());
							sql += " AND ";
						}
					}
					if (sc[i].getFieldName().compareToIgnoreCase("patient.id") == 0)
					{
						Integer opnum = new Integer(sc[i].getCompareOp().getCode());
						sql += String.format(operationsMap.get(opnum), "p.id", sc[i].getFieldValue1(), sc[i].getFieldValue2());
						sql += " AND ";
					}
				}
				sql += " GROUP BY p.id, p.persondata, p.clinician, p.sd";
				
				List<Object[]> ql = _session.createSQLQuery(sql).list();

				ForkJoinPool pool = new ForkJoinPool();
				DecryptPatientInfoTask task = new DecryptPatientInfoTask(ql);
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
	

}
