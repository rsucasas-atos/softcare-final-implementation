package eu.ehealth.dataprocessing;


/**
 * 
 * @author 
 *
 */
public class DataProcessingRule
{

	
	/*
	 * json element example:
	 * 
	 *	"comments": "DataType: 12...Diastolic // CallerID: 2...DoubleCompareRuleType",
	 *	"description": "Blood Pressure: Diastolic",
	 *	"id": "Diastolic",
	 *	"dataType": 12,
	 *	"callerID": 2,
	 *	"lowerLimit": 50,
	 *	"upperLimit": 90,
	 *	"getPrevious": "false",
	 *	"highRiskThresh": 0
	 * 
	 */
	private String comments;
	private String description;
	private String id;
	private int dataType;
	private int callerID;
	private int lowerLimit;
	private int upperLimit;
	private String getPrevious;
	private int highRiskThresh;
	
	
	/**
	 * 
	 * @return
	 */
	public String getComments()
	{
		return comments;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getDescription()
	{
		return description;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getId()
	{
		return id;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getDataType()
	{
		return dataType;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getCallerID()
	{
		return callerID;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getLowerLimit()
	{
		return lowerLimit;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getUpperLimit()
	{
		return upperLimit;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getGetPrevious()
	{
		return getPrevious;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getHighRiskThresh()
	{
		return highRiskThresh;
	}
	

}
