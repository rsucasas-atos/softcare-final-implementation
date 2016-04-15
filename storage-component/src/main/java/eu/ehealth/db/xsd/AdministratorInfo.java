package eu.ehealth.db.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * The short variant of administrators information
 * 
 * <p>
 * Clase Java para AdministratorInfo complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="AdministratorInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdministratorInfo", propOrder = { "id", "surname", "name", "personId" })
public class AdministratorInfo
{


	@XmlElement(name = "ID", required = true)
	protected String id;
	@XmlElement(name = "Surname", required = true)
	protected String surname;
	@XmlElement(name = "Name", required = true)
	protected String name;
	@XmlElement(name = "PersonId", required = true)
	protected String personId;


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
	 * Obtiene el valor de la propiedad id.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPersonID()
	{
		return personId;
	}


	/**
	 * Define el valor de la propiedad id.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setPersonID(String value)
	{
		this.personId = value;
	}


	/**
	 * Obtiene el valor de la propiedad surname.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSurname()
	{
		return surname;
	}


	/**
	 * Define el valor de la propiedad surname.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setSurname(String value)
	{
		this.surname = value;
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

}
