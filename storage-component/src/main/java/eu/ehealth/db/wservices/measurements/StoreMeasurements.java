package eu.ehealth.db.wservices.measurements;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.dataprocessing.DataProcessing;
import eu.ehealth.db.xsd.Measurement;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class StoreMeasurements extends BaseMeasurementsOperations<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public StoreMeasurements(Session session)
	{
		super(session);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			List<Measurement> data = (List<Measurement>) lo.get(0); 
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : StoreMeasurements");			
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			for (int i = 0; i < data.size(); i++)
			{
				data.set(i, nc.check(data.get(i), Measurement.class));
			}

			if (!checkUserPermissions("uploadData", userId, U_CLINICIAN, U_CARER, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			boolean error = false;
			try
			{
				Measurement[] rm = data.toArray(new Measurement[data.size()]);
				Integer id = 0;
				for (int i = 0; i < rm.length; i++)
				{
					try
					{
						_session.beginTransaction();
						id = importMeasurement(rm[i], null);

						String sql = "select a.personid from aladdinuser a inner join task t on (t.object = a.id) where t.id = "
								+ new Integer(rm[i].getTaskID()).toString();
						List<?> dataQ = _session.createSQLQuery(sql).list();
						if (dataQ.size() > 0)
						{
							DataProcessing r = new DataProcessing(_session);
							r.analyzeMeasurement((Integer) dataQ.get(0), rm[i]);
						}

						_session.getTransaction().commit();
					}
					catch (HibernateException e)
					{
						rollbackSession();

						StorageComponentMain.logException(e);
						
						error = true;
						res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_1.getCode(), " : " + e.toString());
					}
				}

				if (!error) {
					// set OperationResult values
					res = Globals.getOpResult("" + id.toString(), "");
				}
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
