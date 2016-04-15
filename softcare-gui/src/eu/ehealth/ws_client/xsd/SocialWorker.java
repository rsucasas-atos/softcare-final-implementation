package eu.ehealth.ws_client.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para SocialWorker complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SocialWorker">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="phone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocialWorker", propOrder = { "name", "phone", "email" })
public class SocialWorker
{


	@XmlElement(required = true)
	protected String name;
	@XmlElement(required = true)
	protected String phone;
	@XmlElement(required = true)
	protected String email;


	public SocialWorker()
	{
	}


	public SocialWorker(java.lang.String name, java.lang.String phone,
			java.lang.String email)
	{
		this.name = name;
		this.phone = phone;
		this.email = email;
	}


	/**
	 * Obtiene el valor de la propiedad name.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * Define el valor de la propiedad name.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setName(String value)
	{
		this.name = value;
	}


	/**
	 * Obtiene el valor de la propiedad phone.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPhone()
	{
		return phone;
	}


	/**
	 * Define el valor de la propiedad phone.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setPhone(String value)
	{
		this.phone = value;
	}


	/**
	 * Obtiene el valor de la propiedad email.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEmail()
	{
		return email;
	}


	/**
	 * Define el valor de la propiedad email.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setEmail(String value)
	{
		this.email = value;
	}

}
