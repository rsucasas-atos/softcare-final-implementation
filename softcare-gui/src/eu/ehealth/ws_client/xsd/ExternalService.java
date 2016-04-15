package eu.ehealth.ws_client.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para ExternalService complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ExternalService">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExternalService", propOrder = { "id", "description",
		"address", "type" })
public class ExternalService
{


	@XmlElement(name = "ID", required = true)
	protected String id;
	@XmlElement(name = "Description", required = true)
	protected String description;
	@XmlElement(name = "Address", required = true)
	protected String address;
	@XmlElement(name = "Type", required = true)
	protected String type;


	public ExternalService()
	{
	}


	public ExternalService(java.lang.String ID, java.lang.String description,
			java.lang.String address, java.lang.String type)
	{
		this.id = ID;
		this.description = description;
		this.address = address;
		this.type = type;
	}


	/**
	 * Obtiene el valor de la propiedad id.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getID()
	{
		return id;
	}


	/**
	 * Define el valor de la propiedad id.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setID(String value)
	{
		this.id = value;
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


	/**
	 * Obtiene el valor de la propiedad address.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAddress()
	{
		return address;
	}


	/**
	 * Define el valor de la propiedad address.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setAddress(String value)
	{
		this.address = value;
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

}
