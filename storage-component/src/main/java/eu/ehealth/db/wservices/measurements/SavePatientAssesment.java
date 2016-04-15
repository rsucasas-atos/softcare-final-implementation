package eu.ehealth.db.wservices.measurements;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.PatientAssessment;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class SavePatientAssesment extends BaseMeasurementsOperations<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public SavePatientAssesment(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			PatientAssessment assessment = (PatientAssessment) lo.get(0); 
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : SavePatientAssesment");
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			assessment = nc.check(assessment, PatientAssessment.class);

			if (!checkUserPermissions("uploadData", userId, U_CLINICIAN, U_CARER, U_PATIENT))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{
				_session.beginTransaction();
				eu.ehealth.db.db.PatientAssessment pa = new eu.ehealth.db.db.PatientAssessment();

				pa.setPatient(new Integer(assessment.getPatientID()));

				long timeInMillis = 0;
				if (assessment.getDateOfAssessment() != null)
					timeInMillis = assessment.getDateOfAssessment().toGregorianCalendar().getTimeInMillis();

				pa.setDateOfAssessment(new Timestamp(timeInMillis));
				pa.setAetology(new Integer(assessment.getAetology().getCode()));
				pa.setTimeElapsedSinceDiagnose((int) assessment.getTimeEllapsedSinceDiagnosed());
				pa.setSeverity((int) assessment.getSeverity());
				pa.setRelevantPathologyAntecedents(assessment.getRelevantPathologyAntecedents());
				pa.setComorbidity(assessment.getComorbidity());
				pa.setCharlsonComobodityIndex((int) assessment.getCharlsonComorbidityIndex());
				pa.setBarthelIndex((int) assessment.getBarthelIndex());
				pa.setLawtonIndex((int) assessment.getLawtonIndex());
				pa.setMMSE((int) assessment.getMMSE());
				pa.setMDRS((int) assessment.getMDRS());
				pa.setBlessedScalePart1(new BigDecimal(assessment.getBlessedScalePart1()));
				pa.setBlessedScalePart2((int) assessment.getBlessedScalePart2());
				pa.setBlessedScalePart3((int) assessment.getBlessedScalePart3());
				pa.setChecklistMBPC((int) assessment.getChecklistMBP());
				pa.setNPQISeverity((int) assessment.getNPQISeverity());
				pa.setNPQIStress((int) assessment.getNPQIStress());
				pa.setGDS((int) assessment.getGDS());
				pa.setFalls(assessment.isFalls());
				pa.setIncontinence(assessment.isIncontinence());
				pa.setDelirium(assessment.isDelirium());
				pa.setImmobility(assessment.isImmobility());
				pa.setSensorialDeficits(assessment.isSensorialDeficits());
				pa.setPharmacologyTreatment(assessment.getPharmacologicalTreatment());
				_session.save(pa);

				Integer pid = new Integer(pa.getId());
				for (int i = 0; i < assessment.getClinicalData().size(); i++)
				{
					importMeasurement(assessment.getClinicalData().get(i), pid);
				}

				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("" + pa.getId().toString(), "");
			}
			catch (HibernateException e)
			{
				rollbackSession();

				StorageComponentMain.logException(e);

				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_1.getCode(), " : " + e.toString());
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
