package eu.ehealth.db.db;


/**
 * 
 * @author a572832
 *
 */
public class Rules implements java.io.Serializable
{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2064030799921428291L;
	
	private String id;
	private String comments;
	private String description;
	private Integer dataType;
	private String dataTypeDesc;
	private Integer callerID;
	private String callerIDDesc;
	private Integer lowerLimit;
	private Integer upperLimit;
	private String getPrevious;
	private Integer highRiskThresh;
	private Integer averageTotalData;
	private Integer averageWeeksMax;
	private String alg;
	private String activate;


	/**
	 * 
	 */
	public Rules()
	{
	}


	/**
	 * 
	 * @param id
	 * @param comments
	 * @param description
	 * @param dataType
	 * @param dataTypeDesc
	 * @param callerID
	 * @param callerIDDesc
	 * @param lowerLimit
	 * @param upperLimit
	 * @param getPrevious
	 * @param highRiskThresh
	 * @param averageTotalData
	 * @param averageWeeksMax
	 * @param alg
	 * @param activate
	 */
	public Rules(String id, String comments, String description, Integer dataType, String dataTypeDesc,
			Integer callerID, String callerIDDesc, Integer lowerLimit, Integer upperLimit, String getPrevious,
			Integer highRiskThresh, Integer averageTotalData, Integer averageWeeksMax, String alg, String activate)
	{
		this.id = id;
		this.comments = comments;
		this.description = description;
		this.dataType = dataType;
		this.dataTypeDesc = dataTypeDesc;
		this.callerID = callerID;
		this.callerIDDesc = callerIDDesc;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.getPrevious = getPrevious;
		this.highRiskThresh = highRiskThresh;
		this.averageTotalData = averageTotalData;
		this.averageWeeksMax = averageWeeksMax;
		this.alg = alg;
		this.activate = activate;
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
