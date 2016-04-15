package eu.ehealth.db.wservices.tasks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.hibernate.Query;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.questionnaires.dbfunctions.FQuestionnaires;
import eu.ehealth.db.xsd.SystemParameter;
import eu.ehealth.db.xsd.Task;
import eu.ehealth.util.NullChecker;


/**
 * 
 * 
 * @author a572832
 * @date 13/03/2014.
 *
 */
public class GetUserPlannedTasks extends DbStorageComponent<List<Task>, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetUserPlannedTasks(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<Task> dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			String userIdv = (String) lo.get(0);
			XMLGregorianCalendar fromDate = (XMLGregorianCalendar) lo.get(1);
			XMLGregorianCalendar toDate = (XMLGregorianCalendar) lo.get(2);
			SystemParameter locale = (SystemParameter) lo.get(3); 
			String requesterId = (String) lo.get(4);
			
			List<Task> l = new ArrayList<Task>();
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetUserPlannedTasks");

			NullChecker nc = new NullChecker();

			userIdv = nc.check(userIdv, String.class);
			requesterId = nc.check(requesterId, String.class);
			fromDate = nc.check(fromDate, XMLGregorianCalendar.class);
			toDate = nc.check(toDate, XMLGregorianCalendar.class);

			if (!checkUserPermissions("", requesterId, U_CARER, U_CLINICIAN, U_ADMIN))
			{
				return l;
			}

			try
			{
				Integer userId = new Integer(userIdv);

				fromDate.toGregorianCalendar().set(Calendar.HOUR, 0);
				fromDate.toGregorianCalendar().set(Calendar.MINUTE, 0);
				fromDate.toGregorianCalendar().set(Calendar.SECOND, 0);

				toDate.toGregorianCalendar().set(Calendar.HOUR, 23);
				toDate.toGregorianCalendar().set(Calendar.MINUTE, 59);
				toDate.toGregorianCalendar().set(Calendar.SECOND, 59);

				final Query query = 
						_session.createQuery("select t from Task t where DateTimeAssigned between :a and :b and Executor = :e order by DateTimeAssigned");
				query.setCalendar("a", fromDate.toGregorianCalendar());
				query.setCalendar("b", toDate.toGregorianCalendar());
				query.setInteger("e", userId);
				query.setCacheable(true);
				query.setCacheRegion(null);

				StorageComponentMain.scLog("DEBUG", fromDate.toGregorianCalendar().getTime().toString());
				StorageComponentMain.scLog("DEBUG", toDate.toGregorianCalendar().getTime().toString());
				StorageComponentMain.scLog("DEBUG", "" + userId);

				List<?> tl = query.list();

				for (int i = 0; i < tl.size(); i++)
				{
					eu.ehealth.db.db.Task t = (eu.ehealth.db.db.Task) tl.get(i);
					
					Task rt = new Task();
					rt.setID(t.getId().toString());
					SystemParameter taskType = new SystemParameter();
					taskType.setCode(t.getTaskType().toString());

					rt.setTaskType(taskType);

					GregorianCalendar c1 = new GregorianCalendar();
					c1.setTimeInMillis(t.getDateTimeAssigned().getTime());
					try
					{
						rt.setDateTimeAssigned(DatatypeFactory.newInstance().newXMLGregorianCalendar(c1));
					}
					catch (Exception ex)
					{
					}

					GregorianCalendar c2 = new GregorianCalendar();
					c2.setTimeInMillis(t.getDateTimeFulfilled().getTime());
					try
					{
						rt.setDateTimeFulfilled(DatatypeFactory.newInstance().newXMLGregorianCalendar(c2));
					}
					catch (Exception ex) { }

					SystemParameter taskStatus = new SystemParameter();
					taskStatus.setCode(t.getTaskStatus().toString());

					rt.setTaskStatus(taskStatus);
					rt.setURL(t.getUrl());
					rt.setExecutorID(t.getExecutor().toString());
					rt.setAssignerID(t.getAssigner().toString());
					rt.setObjectID(t.getObject().toString());
					rt.setText(t.getText());
					if (t.getQuestionnaire() != null && t.getQuestionnaire() > 0)
					{
						FQuestionnaires fq = new FQuestionnaires(_session);
						rt.setQuestionnaire(fq.exportQuestionnaire(t.getM_Questionnaire(), locale));
					}
					
					l.add(rt);
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
