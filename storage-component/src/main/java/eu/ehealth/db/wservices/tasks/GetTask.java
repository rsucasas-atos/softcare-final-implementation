package eu.ehealth.db.wservices.tasks;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import org.hibernate.HibernateException;
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
public class GetTask extends DbStorageComponent<Task, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetTask(Session session)
	{
		super(session);
	}

	
	@Override
	protected Task dbStorageFunction(ArrayList<Object> lo)
	{
		try
		{
			String id = (String) lo.get(0); 
			SystemParameter locale = (SystemParameter) lo.get(1); 
			String userId = (String) lo.get(2);
			
			Task t = new Task();
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetTask");

			NullChecker nc = new NullChecker();

			userId = nc.check(userId, String.class);

			if (!checkUserPermissions("", userId, U_CARER, U_CLINICIAN, U_ADMIN))
			{
				return t;
			}

			try
			{
				eu.ehealth.db.db.Task dbTask = 
						(eu.ehealth.db.db.Task) _session.load(eu.ehealth.db.db.Task.class, new Integer(id));

				t.setID(dbTask.getId().toString());

				SystemParameter taskStatus = new SystemParameter();
				taskStatus.setCode(dbTask.getTaskStatus().toString());
				t.setTaskStatus(taskStatus);

				SystemParameter taskType = new SystemParameter();
				taskType.setCode(dbTask.getTaskType().toString());
				t.setTaskType(taskType);

				GregorianCalendar c1 = new GregorianCalendar();
				c1.setTimeInMillis(dbTask.getDateTimeAssigned().getTime());
				try
				{
					t.setDateTimeAssigned(DatatypeFactory.newInstance().newXMLGregorianCalendar(c1));
				}
				catch (Exception ex) { }

				GregorianCalendar c2 = new GregorianCalendar();
				c2.setTimeInMillis(dbTask.getDateTimeFulfilled().getTime());
				try
				{
					t.setDateTimeFulfilled(DatatypeFactory.newInstance().newXMLGregorianCalendar(c2));
				}
				catch (Exception ex) { }

				t.setTaskStatus(taskStatus);
				t.setURL(dbTask.getUrl());
				t.setExecutorID(dbTask.getExecutor().toString());
				t.setAssignerID(dbTask.getAssigner().toString());
				t.setObjectID(dbTask.getObject().toString());
				t.setText(dbTask.getText());
				if (dbTask.getQuestionnaire() != null && dbTask.getQuestionnaire() > 0)
				{
					FQuestionnaires fq = new FQuestionnaires(_session);
					t.setQuestionnaire(fq.exportQuestionnaire(dbTask.getM_Questionnaire(), locale));
				}

			}
			catch (HibernateException e)
			{
				StorageComponentMain.logException(e);
			}

			return t;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);

			return null;
		}
	}
	

}
