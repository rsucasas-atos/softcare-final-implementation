package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.PatientInfo;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 13/03/2014.
 *
 */
public class GetPatientsForCaregiver extends DbStorageComponent<List<PatientInfo>, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetPatientsForCaregiver(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<PatientInfo> dbStorageFunction(ArrayList<String> lo)
	{
		try
		{
			String userId = (String) lo.get(0);
			
			List<PatientInfo> l = new ArrayList<PatientInfo>();
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			if (!checkUserPermissions("", userId, U_CARER, U_CLINICIAN, U_ADMIN))
			{
				return l;
			}

			try
			{
				Integer uid = new Integer(userId);
				String sql = "SELECT personid FROM aladdinuser WHERE id = '" + uid.toString() + "' AND type = '3'";
				SQLQuery q = _session.createSQLQuery(sql);
				if (q.list().size() == 1)
				{
					sql = "SELECT p.id FROM patient p " + 
						  "LEFT JOIN carer c ON c.id = p.carer " +
						  "WHERE c.persondata = '" + q.list().get(0).toString() + "' ";
					
					//sql = "SELECT id FROM patient WHERE carer = '" + q.list().get(0).toString() + "'";
					StorageComponentMain.scLog("DEBUG", sql);

					Object[] ql = _session.createSQLQuery(sql).list().toArray();
					for (int i = 0; i < ql.length; i++)
					{
						Integer id = (Integer) ql[i];
						eu.ehealth.db.db.Patient p = (eu.ehealth.db.db.Patient) _session.load(eu.ehealth.db.db.Patient.class, id);
						PatientInfo qi = new PatientInfo();
						qi.setID(p.getId().toString());
						qi.setSurname(p.getM_PersonData().getSurname());
						qi.setName(p.getM_PersonData().getName());

						l.add(qi);
					}
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
