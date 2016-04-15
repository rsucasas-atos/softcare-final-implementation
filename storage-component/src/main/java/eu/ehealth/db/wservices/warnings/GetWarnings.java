package eu.ehealth.db.wservices.warnings;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.users.dbfunctions.FUsers;
import eu.ehealth.db.xsd.SearchCriteria;
import eu.ehealth.db.xsd.SystemParameter;
import eu.ehealth.db.xsd.Warning;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class GetWarnings extends DbStorageComponent<List<Warning>, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetWarnings(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<Warning> dbStorageFunction(ArrayList<Object> lo)
	{
		List<Warning> l = new ArrayList<Warning>();
		try
		{
			List<SearchCriteria> warn = (List<SearchCriteria>) lo.get(0); 
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetWarnings");

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			for (int i = 0; i < warn.size(); i++)
			{
				warn.set(i, nc.check(warn.get(i), SearchCriteria.class));
			}

			if (!checkUserPermissions("", userId, U_CLINICIAN, U_ADMIN))
			{
				return l;
			}

			try
			{
				Field[] field = eu.ehealth.db.db.Warning.class.getDeclaredFields();
				String sql = "SELECT warning.id FROM warning inner join patient on (patient.id = warning.patient) WHERE ";

				SearchCriteria[] sc = warn.toArray(new SearchCriteria[warn.size()]);
				for (int i = 0; i < sc.length; i++)
				{
					for (int j = 0; j < field.length; j++)
					{
						if (field[j].getName().compareToIgnoreCase(sc[i].getFieldName()) == 0)
						{
							Integer opnum = new Integer(sc[i].getCompareOp().getCode());
							sql += String.format(operationsMap.get(opnum), "warning." + sc[i].getFieldName(), 
									sc[i].getFieldValue1(), sc[i].getFieldValue2());
							sql += " AND ";
						}
					}
					if (sc[i].getFieldName().compareToIgnoreCase("patient.id") == 0)
					{
						Integer opnum = new Integer(sc[i].getCompareOp().getCode());
						sql += String.format(operationsMap.get(opnum), sc[i].getFieldName(), 
								sc[i].getFieldValue1(), sc[i].getFieldValue2());
						sql += " AND ";
					}
				}
				sql += "1=1";

				Object[] list = _session.createSQLQuery(sql).list().toArray();
				for (int i = 0; i < list.length; i++)
				{
					Integer id = (Integer) list[i];
					eu.ehealth.db.db.Warning w = 
							(eu.ehealth.db.db.Warning) _session.load(eu.ehealth.db.db.Warning.class, id);
					Warning rw = new Warning();
					rw.setID(w.getId().toString());

					SystemParameter typeOfWarning = new SystemParameter();
					typeOfWarning.setCode(w.getTypeOfWarning().toString());

					rw.setTypeOfWarning(typeOfWarning);
					GregorianCalendar c1 = new GregorianCalendar();
					c1.setTimeInMillis(w.getDateTimeOfWarning().getTime());
					try
					{
						rw.setDateTimeOfWarning(DatatypeFactory.newInstance().newXMLGregorianCalendar(c1));
					}
					catch (Exception ex) {}

					SystemParameter effect = new SystemParameter();

					if (w.getEffect() == null)
						w.setEffect(0);

					effect.setCode(w.getEffect().toString());

					rw.setEffect(effect);

					SystemParameter indicator = new SystemParameter();
					if (w.getIndicator() == null)
						w.setIndicator(0);
					indicator.setCode(w.getIndicator().toString());

					rw.setIndicator(indicator);

					SystemParameter riskLevel = new SystemParameter();
					if (w.getRiskLevel() == null)
						w.setRiskLevel(0);
					riskLevel.setCode(w.getRiskLevel().toString());

					rw.setRiskLevel(riskLevel);
					rw.setJustificationText(w.getJustificationText());

					SystemParameter emergencyLevel = new SystemParameter();
					if (w.getEmergencyLevel() == null)
						w.setEmergencyLevel(0);
					emergencyLevel.setCode(w.getEmergencyLevel().toString());

					rw.setEmergencyLevel(emergencyLevel);
					
					FUsers fu = new FUsers(_session);
					rw.setPatient(fu.exportPatient(
							(eu.ehealth.db.db.Patient) _session.load(eu.ehealth.db.db.Patient.class, w.getPatient())));

					rw.setDelivered(w.getDelivered());

					l.add(rw);
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
