package eu.ehealth.db.wservices.tasks.dbfunctions;

import java.sql.Timestamp;
import eu.ehealth.db.xsd.Task;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class FTasks
{
	
	
	/**
	 * Get count of task types.
	 * 
	 * @return count
	 */
	public Integer getTaskTypesCount()
	{
		return 7;
	}
	
	
	/**
	 * Store task in the DB
	 * 
	 * @param xTask for storing
	 * @return DB object
	 */
	public eu.ehealth.db.db.Task importTask(Task xTask)
	{
		eu.ehealth.db.db.Task dTask = new eu.ehealth.db.db.Task();
		dTask.setTaskType(new Integer(xTask.getTaskType().getCode()));
		dTask.setDateTimeAssigned(new Timestamp(xTask.getDateTimeAssigned().toGregorianCalendar().getTimeInMillis()));
		dTask.setDateTimeFulfilled(new Timestamp(xTask.getDateTimeFulfilled().toGregorianCalendar().getTimeInMillis()));
		dTask.setTaskStatus(new Integer(xTask.getTaskStatus().getCode()));
		dTask.setUrl(xTask.getURL());
		dTask.setExecutor(new Integer(xTask.getExecutorID()));
		dTask.setAssigner(new Integer(xTask.getAssignerID()));
		dTask.setObject(new Integer(xTask.getObjectID()));
		dTask.setText(xTask.getText());

		if (xTask.getQuestionnaire() != null)
		{

			if (xTask.getQuestionnaire().getID() != null && xTask.getQuestionnaire().getID().compareTo("") != 0)
			{
				try
				{
					dTask.setQuestionnaire(new Integer(xTask.getQuestionnaire().getID()));
				}
				catch (Exception e)
				{
					dTask.setQuestionnaire(null);
				}
			}
		}
		return dTask;
	}
	

}
