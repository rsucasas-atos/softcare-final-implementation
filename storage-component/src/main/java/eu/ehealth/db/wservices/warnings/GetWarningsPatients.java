package eu.ehealth.db.wservices.warnings;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.SearchCriteria;
import eu.ehealth.db.xsd.SystemParameter;
import eu.ehealth.db.xsd.Warning;
import eu.ehealth.security.DataBasePasswords;
import eu.ehealth.util.NullChecker;
import eu.ehealth.util.Utilities;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class GetWarningsPatients extends DbStorageComponent<List<Warning>, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetWarningsPatients(Session session)
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
			StorageComponentMain.scLog("DEBUG", "METHOD : GetWarningsPatients");

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
				String sql = "SELECT warning.*, persondata.Name, persondata.Surname, patient.clinician FROM warning " +
							 "LEFT JOIN patient on patient.id = warning.patient " +
							 "LEFT JOIN persondata on persondata.id = patient.persondata WHERE ";

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
				
				List listWarnings = _session.createSQLQuery(sql).list();
				if (listWarnings != null) 
				{
					for (int i=0, max=listWarnings.size(); i<max; i++)
					{
						Object[] col = (Object[]) listWarnings.get(i);
						/* Order of elements:
								warning.id
							    warning.TypeOfWarning
							    warning.DateTimeOfWarning
							    warning.Effect
							    warning.Indicator
							    warning.RiskLevel
							    warning.JustificationText
							    warning.EmergencyLevel
							    warning.Delivered
							    warning.Patient
							    persondata.Name
							    persondata.Surname
							    patient.clinician
					    */
						int wId = (Integer) col[0];
						int wTypeOfWarning = (Integer) col[1];
						Timestamp wDateTimeOfWarning = (Timestamp) col[2];
						int wEffect = Utilities.NumericFunctions.getIntValue(col[3], 0);
						int wIndicator = Utilities.NumericFunctions.getIntValue(col[4], 0);
						int wRiskLevel = Utilities.NumericFunctions.getIntValue(col[5], 0);
						String wJustificationText = (String) col[6];
						int wEmergencyLevel = Utilities.NumericFunctions.getIntValue(col[7], 0);
						Boolean wDelivered = (Boolean) col[8];
						int wPatient = (Integer) col[9];
						String pName = (String) col[10];
						String pSurname = (String) col[11];
						int cId = (Integer) col[12];
						
						if (StorageComponentMain.DATABASE_ENCRYPTION) 
						{
							pName = DataBasePasswords.decryptDBValue(pName);
							pSurname = DataBasePasswords.decryptDBValue(pSurname);
						}
						
						GregorianCalendar c1 = new GregorianCalendar();
						c1.setTimeInMillis(wDateTimeOfWarning.getTime());
						XMLGregorianCalendar date = null;
						try
						{
							date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c1);
						}
						catch (Exception ex) {}
						
						eu.ehealth.db.xsd.PersonData pd = new eu.ehealth.db.xsd.PersonData();
						pd.setName(pName);
						pd.setSurname(pSurname);
						
						eu.ehealth.db.xsd.Patient p = new eu.ehealth.db.xsd.Patient();
						p.setID("" + wPatient);
						p.setPersonData(pd);
						p.setResponsibleClinicianID("" + cId);
						
						Warning w = new Warning();
						w.setPatient(p);
						w.setDateTimeOfWarning(date);
						w.setDelivered(wDelivered);
						w.setEffect(new SystemParameter(wEffect));
						w.setEmergencyLevel(new SystemParameter(wEmergencyLevel));
						w.setID("" + wId);
						w.setIndicator(new SystemParameter(wIndicator));
						w.setJustificationText(wJustificationText);
						w.setRiskLevel(new SystemParameter(wRiskLevel));
						w.setTypeOfWarning(new SystemParameter(wTypeOfWarning));
						
						l.add(w);
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

			return l;
		}
	}

	
}
