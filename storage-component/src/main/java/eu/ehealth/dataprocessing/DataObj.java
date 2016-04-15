package eu.ehealth.dataprocessing;


/**
 * 
 * @author 
 *
 */
public class DataObj
{


	private float current_value;
	private float last_value;
	private float average;
	private int subject_height;
	private int subject_personId;


	/**
	 * 
	 * @param current_value
	 * @param last_value
	 * @param average
	 * @param subject_height
	 * @param subject_personId
	 */
	public DataObj(float current_value, float last_value, float average, int subject_height, int subject_personId)
	{
		this.current_value = current_value;
		this.last_value = last_value;
		this.average = average;
		this.subject_height = subject_height;
		this.subject_personId = subject_personId;
	}


	/**
	 * @return the current_value
	 */
	public float getCurrent_value()
	{
		return current_value;
	}


	/**
	 * @param current_value
	 *            the current_value to set
	 */
	public void setCurrent_value(float current_value)
	{
		this.current_value = current_value;
	}


	/**
	 * @return the average
	 */
	public float getAverage()
	{
		return average;
	}


	/**
	 * @param average
	 *            the average to set
	 */
	public void setAverage(float average)
	{
		this.average = average;
	}

	
	/**
	 * @return the subject_height
	 */
	public int getSubject_height()
	{
		return subject_height;
	}

	
	/**
	 * @param subject_height the subject_height to set
	 */
	public void setSubject_height(int subject_height)
	{
		this.subject_height = subject_height;
	}

	
	/**
	 * @return the last_value
	 */
	public float getLast_value()
	{
		return last_value;
	}


	/**
	 * @param last_value the last_value to set
	 */
	public void setLast_value(float last_value)
	{
		this.last_value = last_value;
	}

	
	/**
	 * @return the subject_personId
	 */
	public int getSubject_personId()
	{
		return subject_personId;
	}
	
	
	/**
	 * @param subject_personId the subject_personId to set
	 */
	public void setSubject_personId(int subject_personId)
	{
		this.subject_personId = subject_personId;
	}


}
