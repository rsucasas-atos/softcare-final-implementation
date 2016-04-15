package eu.ehealth.db.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.ehealth.db.xsd.OperationResult;


/**
 * 
 * @author a572832
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "out" })
@XmlRootElement(name = "UpdateRuleResponse")
public class UpdateRuleResponse
{


	@XmlElement(required = true)
	protected OperationResult out;


	/**
	 * Obtiene el valor de la propiedad out.
	 * 
	 * @return possible object is {@link OperationResult }
	 * 
	 */
	public OperationResult getOut()
	{
		return out;
	}


	/**
	 * Define el valor de la propiedad out.
	 * 
	 * @param value
	 *            allowed object is {@link OperationResult }
	 * 
	 */
	public void setOut(OperationResult value)
	{
		this.out = value;
	}

}
