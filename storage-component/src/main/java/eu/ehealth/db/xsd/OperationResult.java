package eu.ehealth.db.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * OperationResult contains the code of an operation result, the status of the
 * result (passed or failed) and the operation result description
 * 
 * <p>
 * Clase Java para OperationResult complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="OperationResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperationResult", propOrder = { "code", "status",
		"description" })
public class OperationResult
{


	@XmlElement(name = "Code", required = true)
	protected String code;
	@XmlElement(name = "Status")
	@XmlSchemaType(name = "unsignedByte")
	protected short status;
	@XmlElement(name = "Description")
	protected String description;


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
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCode(String value)
	{
		this.code = value;
	}


	/**
	 * Obtiene el valor de la propiedad status.
	 * 
	 */
	public short getStatus()
	{
		return status;
	}


	/**
	 * Define el valor de la propiedad status.
	 * 
	 */
	public void setStatus(short value)
	{
		this.status = value;
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
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescription(String value)
	{
		this.description = value;
	}

}
