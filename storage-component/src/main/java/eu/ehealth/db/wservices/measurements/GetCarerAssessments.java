package eu.ehealth.db.wservices.measurements;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.CarerAssessment;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class GetCarerAssessments extends DbStorageComponent<List<CarerAssessment>, String>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetCarerAssessments(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<CarerAssessment> dbStorageFunction(ArrayList<String> lo)
	{
		List<CarerAssessment> l = new ArrayList<CarerAssessment>();
		try
		{
			String carerIdv = (String) lo.get(0);
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetCarerAssessments");

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			carerIdv = nc.check(carerIdv, String.class);

			if (!checkUserPermissions("", userId, U_CLINICIAN, U_ADMIN))
			{
				return l;
			}

			try
			{
				Integer carerId = new Integer(carerIdv);
				Object[] cal = _session.createSQLQuery("SELECT id FROM carerassessment WHERE carer = " + carerId.toString()).list().toArray();

				for (int i = 0; i < cal.length; i++)
				{
					Integer id = (Integer) cal[0];
					eu.ehealth.db.db.CarerAssessment ca = 
							(eu.ehealth.db.db.CarerAssessment) _session.load(eu.ehealth.db.db.CarerAssessment.class, id);

					CarerAssessment rca = new CarerAssessment();
					rca.setID(ca.getId().toString());
					rca.setCarerID(ca.getCarer().toString());
					rca.setClinicianID(ca.getClinician().toString());
					GregorianCalendar c1 = new GregorianCalendar();
					c1.setTimeInMillis(ca.getDateOfAssessment().getTime());
					try
					{
						rca.setDateOfAssessment(DatatypeFactory.newInstance().newXMLGregorianCalendar(c1));
					}
					catch (Exception ex) {}

					rca.setRelevantHealthProblem(ca.getRelevantHealthProblem());
					rca.setSeverityOfBurden(ca.getSeverityOfBurden().shortValue());
					rca.setEmotionalOrMentalDisorders(ca.getEmotionalOrMentalDisorder());
					rca.setPsychoactiveDrugs(ca.getPsychoactiveDrugs());
					rca.setQualityOfLife(ca.getQualityOfLife().shortValue());

					l.add(rca);
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
