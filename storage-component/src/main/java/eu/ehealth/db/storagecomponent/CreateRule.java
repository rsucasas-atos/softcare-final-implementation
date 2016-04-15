package eu.ehealth.db.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.ehealth.db.xsd.Rules;


/**
 * 
 * @author a572832
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "data", "userId" })
@XmlRootElement(name = "CreateRule")
public class CreateRule
{


	@XmlElement(required = true)
	protected Rules data;
	@XmlElement(required = true)
	protected String userId;


	/**
	 * Obtiene el valor de la propiedad data.
	 * 
	 * @return possible object is {@link Rules }
	 * 
	 */
	public Rules getData()
	{
		return data;
	}


	/**
	 * Define el valor de la propiedad data.
	 * 
	 * @param value
	 *            allowed object is {@link Rules }
	 * 
	 */
	public void setData(Rules value)
	{
		this.data = value;
	}


	/**
	 * Obtiene el valor de la propiedad userId.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUserId()
	{
		return userId;
	}


	/**
	 * Define el valor de la propiedad userId.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUserId(String value)
	{
		this.userId = value;
	}

}
