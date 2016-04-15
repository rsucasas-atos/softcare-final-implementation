package eu.ehealth.db.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Rules", propOrder = { "id", "comments", "description", "dataType", "dataTypeDesc", "callerID",
		"callerIDDesc", "lowerLimit", "upperLimit", "getPrevious", "highRiskThresh", "averageTotalData",
		"averageWeeksMax", "alg", "activate" })
public class Rules
{


	@XmlElement(name = "id", required = true)
	protected String id;
	@XmlElement(name = "comments", required = false)
	protected String comments;
	@XmlElement(name = "description", required = false)
	protected String description;
	@XmlElement(name = "dataType", required = true)
	protected Integer dataType;
	@XmlElement(name = "dataTypeDesc", required = false)
	protected String dataTypeDesc;
	@XmlElement(name = "callerID", required = true)
	protected Integer callerID;
	@XmlElement(name = "callerIDDesc", required = false)
	protected String callerIDDesc;
	@XmlElement(name = "lowerLimit", required = false)
	protected Integer lowerLimit;
	@XmlElement(name = "upperLimit", required = false)
	protected Integer upperLimit;
	@XmlElement(name = "getPrevious", required = false)
	protected String getPrevious;
	@XmlElement(name = "highRiskThresh", required = false)
	protected Integer highRiskThresh;
	@XmlElement(name = "averageTotalData", required = false)
	protected Integer averageTotalData;
	@XmlElement(name = "averageWeeksMax", required = false)
	protected Integer averageWeeksMax;
	@XmlElement(name = "alg", required = false)
	protected String alg;
	@XmlElement(name = "activate", required = false)
	protected String activate;

	
	/**
	 * 
	 */
	public Rules()
	{
		this.id = "";
		this.comments = "";
		this.description = "";
		this.dataType = 0;
		this.dataTypeDesc = "";
		this.callerID = 0;
		this.callerIDDesc = "";
		this.lowerLimit = 0;
		this.upperLimit = 0;
		this.getPrevious = "false";
		this.highRiskThresh = 0;
		this.averageTotalData = 0;
		this.averageWeeksMax = 0;
		this.alg = "";
		this.activate = "true";
	}
	

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}


	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}


	/**
	 * @return the comments
	 */
	public String getComments()
	{
		return comments;
	}


	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments)
	{
		this.comments = comments;
	}


	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}


	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}


	/**
	 * @return the dataType
	 */
	public Integer getDataType()
	{
		return dataType;
	}


	/**
	 * @param dataType
	 *            the dataType to set
	 */
	public void setDataType(Integer dataType)
	{
		this.dataType = dataType;
	}


	/**
	 * @return the dataTypeDesc
	 */
	public String getDataTypeDesc()
	{
		return dataTypeDesc;
	}


	/**
	 * @param dataTypeDesc
	 *            the dataTypeDesc to set
	 */
	public void setDataTypeDesc(String dataTypeDesc)
	{
		this.dataTypeDesc = dataTypeDesc;
	}


	/**
	 * @return the callerID
	 */
	public Integer getCallerID()
	{
		return callerID;
	}


	/**
	 * @param callerID
	 *            the callerID to set
	 */
	public void setCallerID(Integer callerID)
	{
		this.callerID = callerID;
	}


	/**
	 * @return the callerIDDesc
	 */
	public String getCallerIDDesc()
	{
		return callerIDDesc;
	}


	/**
	 * @param callerIDDesc
	 *            the callerIDDesc to set
	 */
	public void setCallerIDDesc(String callerIDDesc)
	{
		this.callerIDDesc = callerIDDesc;
	}


	/**
	 * @return the lowerLimit
	 */
	public Integer getLowerLimit()
	{
		return lowerLimit;
	}


	/**
	 * @param lowerLimit
	 *            the lowerLimit to set
	 */
	public void setLowerLimit(Integer lowerLimit)
	{
		this.lowerLimit = lowerLimit;
	}


	/**
	 * @return the upperLimit
	 */
	public Integer getUpperLimit()
	{
		return upperLimit;
	}


	/**
	 * @param upperLimit
	 *            the upperLimit to set
	 */
	public void setUpperLimit(Integer upperLimit)
	{
		this.upperLimit = upperLimit;
	}


	/**
	 * @return the getPrevious
	 */
	public String getGetPrevious()
	{
		return getPrevious;
	}


	/**
	 * @param getPrevious
	 *            the getPrevious to set
	 */
	public void setGetPrevious(String getPrevious)
	{
		this.getPrevious = getPrevious;
	}


	/**
	 * @return the highRiskThresh
	 */
	public Integer getHighRiskThresh()
	{
		return highRiskThresh;
	}


	/**
	 * @param highRiskThresh
	 *            the highRiskThresh to set
	 */
	public void setHighRiskThresh(Integer highRiskThresh)
	{
		this.highRiskThresh = highRiskThresh;
	}


	/**
	 * @return the averageTotalData
	 */
	public Integer getAverageTotalData()
	{
		return averageTotalData;
	}


	/**
	 * @param averageTotalData
	 *            the averageTotalData to set
	 */
	public void setAverageTotalData(Integer averageTotalData)
	{
		this.averageTotalData = averageTotalData;
	}


	/**
	 * @return the averageWeeksMax
	 */
	public Integer getAverageWeeksMax()
	{
		return averageWeeksMax;
	}


	/**
	 * @param averageWeeksMax
	 *            the averageWeeksMax to set
	 */
	public void setAverageWeeksMax(Integer averageWeeksMax)
	{
		this.averageWeeksMax = averageWeeksMax;
	}


	/**
	 * @return the alg
	 */
	public String getAlg()
	{
		return alg;
	}


	/**
	 * @param alg
	 *            the alg to set
	 */
	public void setAlg(String alg)
	{
		this.alg = alg;
	}

	
	/**
	 * @return the activate
	 */
	public String getActivate()
	{
		return activate;
	}

	
	/**
	 * @param activate the activate to set
	 */
	public void setActivate(String activate)
	{
		this.activate = activate;
	}

	
}
