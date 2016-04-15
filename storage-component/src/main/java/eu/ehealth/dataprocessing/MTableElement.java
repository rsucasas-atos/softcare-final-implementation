package eu.ehealth.dataprocessing;

import eu.ehealth.StorageComponentMain;


/**
 * 
 * @author
 *
 */
public class MTableElement
{


	private String level;
	private String res;
	private float min;
	private float max;


	/**
	 * @param level
	 * @param res
	 * @param min
	 */
	public MTableElement(String level, String res, float min)
	{
		this.level = level;
		this.res = res;
		this.min = min;
	}


	/**
	 * @return the level
	 */
	public String getLevel()
	{
		return level;
	}


	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(String level)
	{
		this.level = level;
	}


	/**
	 * @return the res
	 */
	public String getRes()
	{
		return res;
	}


	/**
	 * @param res
	 *            the res to set
	 */
	public void setRes(String res)
	{
		this.res = res;
	}


	/**
	 * @return the min
	 */
	public float getMin()
	{
		return StorageComponentMain.round(min, 2);
	}


	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(float min)
	{
		this.min = min;
	}


	/**
	 * @return the max
	 */
	public float getMax()
	{
		return StorageComponentMain.round(max, 2);
	}


	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(float max)
	{
		this.max = max;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean containValue(float value)
	{
		if ((StorageComponentMain.round(value, 2) <= max) && (StorageComponentMain.round(value, 2) >= min))
		{
			return true;
		}
		
		return false;
	}
	

}
