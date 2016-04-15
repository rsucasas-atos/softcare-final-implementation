package eu.ehealth.ws_client.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para Communication complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Communication">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsPrimary" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Communication", propOrder = { "type", "value", "notes",
		"isPrimary" })
public class Communication
{


	@XmlElement(name = "Type", required = true)
	protected String type;
	@XmlElement(name = "Value", required = true)
	protected String value;
	@XmlElement(name = "Notes")
	protected String notes;
	@XmlElement(name = "IsPrimary")
	protected boolean isPrimary;


	public Communication()
	{
	}


	public Communication(java.lang.String type, java.lang.String value,
			java.lang.String notes, boolean isPrimary)
	{
		this.type = type;
		this.value = value;
		this.notes = notes;
		this.isPrimary = isPrimary;
	}


	/**
	 * Obtiene el valor de la propiedad type.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getType()
	{
		return type;
	}


	/**
	 * Define el valor de la propiedad type.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setType(String value)
	{
		this.type = value;
	}


	/**
	 * Obtiene el valor de la propiedad value.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValue()
	{
		return value;
	}


	/**
	 * Define el valor de la propiedad value.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setValue(String value)
	{
		this.value = value;
	}


	/**
	 * Obtiene el valor de la propiedad notes.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNotes()
	{
		return notes;
	}


	/**
	 * Define el valor de la propiedad notes.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setNotes(String value)
	{
		this.notes = value;
	}


	/**
	 * Obtiene el valor de la propiedad isPrimary.
	 * 
	 */
	public boolean isIsPrimary()
	{
		return isPrimary;
	}


	/**
	 * Define el valor de la propiedad isPrimary.
	 * 
	 */
	public void setIsPrimary(boolean value)
	{
		this.isPrimary = value;
	}

}
