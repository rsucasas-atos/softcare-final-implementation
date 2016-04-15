package eu.ehealth.db.wservices.warnings;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.Warning;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class SaveWarning extends DbStorageComponent<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public SaveWarning(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			Warning rwarn = (Warning) lo.get(0);
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : SaveWarnings");
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);
			rwarn = nc.check(rwarn, Warning.class);

			if (!checkUserPermissions("", userId, U_CARER, U_CLINICIAN, U_ADMIN, U_SERVICE))
			{
				// set OperationResult values
				return Globals.getOpResult(Globals.ResponseCode.PERMISSION_ERROR.getCode(), "");
			}

			try
			{
				_session.beginTransaction();

				eu.ehealth.db.db.Warning warn = new eu.ehealth.db.db.Warning();
				try
				{
					warn.setTypeOfWarning(new Integer(rwarn.getTypeOfWarning().getCode()));
				}
				catch (NumberFormatException e)
				{
				}

				long timeInMillis = 0;
				if (rwarn.getDateTimeOfWarning() != null)
					timeInMillis = rwarn.getDateTimeOfWarning().toGregorianCalendar().getTimeInMillis();
				warn.setDateTimeOfWarning(new Timestamp(timeInMillis));

				try
				{
					warn.setEffect(new Integer(rwarn.getEffect().getCode()));
				}
				catch (NumberFormatException e)
				{}

				try
				{
					warn.setIndicator(new Integer(rwarn.getIndicator().getCode()));
				}
				catch (NumberFormatException e)
				{}

				try
				{
					warn.setRiskLevel(new Integer(rwarn.getRiskLevel().getCode()));
				}
				catch (NumberFormatException e)
				{}

				warn.setJustificationText(rwarn.getJustificationText());

				try
				{
					warn.setEmergencyLevel(new Integer(rwarn.getEmergencyLevel().getCode()));
				}
				catch (NumberFormatException e)
				{}

				if (rwarn.getPatient() != null)
					warn.setPatient(new Integer(rwarn.getPatient().getID()));

				warn.setDelivered(rwarn.isDelivered());

				_session.save(warn);

				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("" + warn.getId().toString(), "");
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
