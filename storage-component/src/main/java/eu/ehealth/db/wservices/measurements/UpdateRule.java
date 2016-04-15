package eu.ehealth.db.wservices.measurements;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.Rules;
import eu.ehealth.util.NullChecker;


/**
 * 
 * @author a572832
 *
 */
public class UpdateRule extends BaseMeasurementsOperations<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public UpdateRule(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			Rules data = (Rules) lo.get(0);
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());			
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			data = nc.check(data, Rules.class);

			if (!checkUserPermissions("", userId, U_CLINICIAN, U_ADMIN))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{
				_session.beginTransaction();

				String id = getStringValue(data.getId()); 
				eu.ehealth.db.db.Rules r = (eu.ehealth.db.db.Rules) _session.load(eu.ehealth.db.db.Rules.class, id);
				
				r.setComments(getStringValue(data.getComments())); 
				r.setDescription(getStringValue(data.getDescription())); 
				r.setDataType(getIntegerValue(data.getDataType())); 
				r.setDataTypeDesc(getStringValue(data.getDataTypeDesc())); 
				r.setCallerID(getIntegerValue(data.getCallerID())); 
				r.setCallerIDDesc(getStringValue(data.getCallerIDDesc())); 
				r.setLowerLimit(getIntegerValue(data.getLowerLimit())); 
				r.setUpperLimit(getIntegerValue(data.getUpperLimit())); 
				r.setGetPrevious(getStringValue(data.getGetPrevious())); 
				r.setHighRiskThresh(getIntegerValue(data.getHighRiskThresh())); 
				r.setAverageTotalData(getIntegerValue(data.getAverageTotalData())); 
				r.setAverageWeeksMax(getIntegerValue(data.getAverageWeeksMax())); 
				r.setAlg(getStringValue(data.getAlg())); 
				r.setActivate(getStringValue(data.getActivate())); 

				_session.update(r);
				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("1", "");
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
	
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	private String getStringValue(String v)
	{
		if (v == null)
			return "";
		
		return v;
	}
	
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	private Integer getIntegerValue(Integer v)
	{
		if (v == null)
			return 0;
		
		return v;
	}
	

}
