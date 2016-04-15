package eu.ehealth.db.wservices.measurements;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.PatientAssessment;
import eu.ehealth.db.xsd.SystemParameter;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class GetPatientAssessments extends BaseMeasurementsOperations<List<PatientAssessment>, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetPatientAssessments(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<PatientAssessment> dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			String patientIdv = (String) lo.get(0); 
			String userId = (String) lo.get(1); 
			
			List<PatientAssessment> l = new ArrayList<PatientAssessment>();
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetPatientAssessments");

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			patientIdv = nc.check(patientIdv, String.class);

			if (!checkUserPermissions("accessData", userId, U_CARER, U_CLINICIAN, U_ADMIN))
			{
				return l;
			}

			try
			{
				Integer patientId = new Integer(patientIdv);

				final Query query = _session.createQuery("select p from PatientAssessment p where patient = :patient");
				query.setInteger("patient", patientId);
				query.setCacheable(true);
				query.setCacheRegion(null);
				List<?> data = query.list();

				for (int i = 0; i < data.size(); i++)
				{
					eu.ehealth.db.db.PatientAssessment pa = (eu.ehealth.db.db.PatientAssessment) data.get(i);

					PatientAssessment rpa = new PatientAssessment();

					rpa.setID(pa.getId().toString());
					rpa.setPatientID(pa.getPatient().toString());

					GregorianCalendar c1 = new GregorianCalendar();
					c1.setTimeInMillis(pa.getDateOfAssessment().getTime());
					try
					{
						rpa.setDateOfAssessment(DatatypeFactory.newInstance().newXMLGregorianCalendar(c1));
					}
					catch (Exception ex) 
					{
						StorageComponentMain.logException(ex);
					}

					SystemParameter aetology = new SystemParameter();
					aetology.setCode(pa.getAetology().toString());
					rpa.setAetology(aetology);

					rpa.setTimeEllapsedSinceDiagnosed(pa.getTimeElapsedSinceDiagnose().shortValue());
					rpa.setSeverity(pa.getSeverity().shortValue());
					rpa.setRelevantPathologyAntecedents(pa.getRelevantPathologyAntecedents());
					rpa.setComorbidity(pa.getComorbidity());
					rpa.setCharlsonComorbidityIndex(pa.getCharlsonComobodityIndex().shortValue());
					rpa.setBarthelIndex(pa.getBarthelIndex().shortValue());
					rpa.setLawtonIndex(pa.getLawtonIndex().shortValue());
					rpa.setMMSE(pa.getMMSE().shortValue());
					rpa.setMDRS(pa.getMDRS().shortValue());

					if (pa.getBlessedScalePart1() != null)
						rpa.setBlessedScalePart1(pa.getBlessedScalePart1().doubleValue());
					else
						rpa.setBlessedScalePart1(0.0);

					if (pa.getBlessedScalePart2() != null)
						rpa.setBlessedScalePart2(pa.getBlessedScalePart2().shortValue());
					else
						rpa.setBlessedScalePart2((short) 0);

					if (pa.getBlessedScalePart3() != null)
						rpa.setBlessedScalePart3(pa.getBlessedScalePart3().shortValue());
					else
						rpa.setBlessedScalePart3((short) 0);

					if (pa.getChecklistMBPC() != null)
						rpa.setChecklistMBP(pa.getChecklistMBPC().shortValue());
					else
						rpa.setChecklistMBP((short) 0);

					if (pa.getNPQISeverity() != null)
						rpa.setNPQISeverity(pa.getNPQISeverity().shortValue());
					else
						rpa.setNPQISeverity((short) 0);

					if (pa.getNPQIStress() != null)
						rpa.setNPQIStress(pa.getNPQIStress().shortValue());
					else
						rpa.setNPQIStress((short) 0);

					if (pa.getGDS() != null)
						rpa.setGDS(pa.getGDS().shortValue());
					else
						rpa.setGDS((short) 0);

					rpa.setFalls(pa.getFalls());
					rpa.setIncontinence(pa.getIncontinence());
					rpa.setDelirium(pa.getDelirium());
					rpa.setImmobility(pa.getImmobility());
					rpa.setSensorialDeficits(pa.getSensorialDeficits());
					rpa.setPharmacologicalTreatment(pa.getPharmacologyTreatment());

					Object[] ml = pa.getMeasurements().toArray();

					for (int j = 0; j < ml.length; j++)
					{
						rpa.getClinicalData().add(exportMeasurement((eu.ehealth.db.db.Measurement) ml[j]));
					}

					l.add(rpa);
				}
			}
			catch (Exception e)
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
