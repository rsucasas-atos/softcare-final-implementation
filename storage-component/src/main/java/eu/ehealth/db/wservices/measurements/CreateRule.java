package eu.ehealth.db.wservices.measurements;

import java.util.ArrayList;
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
public class CreateRule extends BaseMeasurementsOperations<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public CreateRule(Session session)
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
				String comments = getStringValue(data.getComments()); 
				String description = getStringValue(data.getDescription()); 
				Integer dataType = getIntegerValue(data.getDataType()); 
				String dataTypeDesc = getStringValue(data.getDataTypeDesc()); 
				Integer callerID = getIntegerValue(data.getCallerID()); 
				String callerIDDesc = getStringValue(data.getCallerIDDesc()); 
				Integer lowerLimit = getIntegerValue(data.getLowerLimit()); 
				Integer upperLimit = getIntegerValue(data.getUpperLimit()); 
				String getPrevious = getStringValue(data.getGetPrevious()); 
				Integer highRiskThresh = getIntegerValue(data.getHighRiskThresh()); 
				Integer averageTotalData = getIntegerValue(data.getAverageTotalData()); 
				Integer averageWeeksMax = getIntegerValue(data.getAverageWeeksMax()); 
				String alg = getStringValue(data.getAlg()); 
				String activate = getStringValue(data.getActivate()); 
				
				eu.ehealth.db.db.Rules r = new eu.ehealth.db.db.Rules(id, comments, description, dataType, dataTypeDesc,
						callerID, callerIDDesc, lowerLimit, upperLimit, getPrevious,
						highRiskThresh, averageTotalData, averageWeeksMax, alg, activate);

				_session.save(r);

				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("1", "");
			}
			catch (Exception e)
			{
				rollbackSession();

				StorageComponentMain.logException(e);

				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_2.getCode(), " : " + e.toString());
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
