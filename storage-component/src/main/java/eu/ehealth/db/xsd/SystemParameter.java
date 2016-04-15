package eu.ehealth.db.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * System Parameters, like Gender, Marital Status, Dementia Etiology, etc.
 * System parameters are lists of {code, description} pairs that change rarely
 * or not at all.
 * 
 * <p>
 * Clase Java para SystemParameter complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SystemParameter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SystemParameter", propOrder = { "code", "description" })
public class SystemParameter
{


	@XmlElement(name = "Code", required = true)
	protected String code;
	@XmlElement(name = "Description", required = true)
	protected String description;

	
	/**
	 * 
	 */
	public SystemParameter() { }
	
	
	/**
	 * 
	 * @param code
	 */
	public SystemParameter(int icode) 
	{
		code = "" + icode;
	}
	

	/**
	 * Obtiene el valor de la propiedad code.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCode()
	{
		return code;
	}


	/**
	 * Define el valor de la propiedad code.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCode(String value)
	{
		this.code = value;
	}


	/**
	 * Obtiene el valor de la propiedad description.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescription()
	{
		return description;
	}


	/**
	 * Define el valor de la propiedad description.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value)
	{
		this.description = value;
	}

}
