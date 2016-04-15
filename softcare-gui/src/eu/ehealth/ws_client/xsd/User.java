package eu.ehealth.ws_client.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para User complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="User">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Type" type="{http://aladdin-project.eu/xsd}SystemParameter"/>
 *         &lt;element name="PersonID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Username" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "User", propOrder = { "id", "type", "personID", "username",
		"password" })
public class User
{


	@XmlElement(name = "ID", required = true)
	protected String id;
	@XmlElement(name = "Type", required = true)
	protected SystemParameter type;
	@XmlElement(name = "PersonID", required = true)
	protected String personID;
	@XmlElement(name = "Username", required = true)
	protected String username;
	@XmlElement(name = "Password", required = true)
	protected String password;


	public User()
	{
	}


	public User(java.lang.String ID,
			eu.ehealth.ws_client.xsd.SystemParameter type,
			java.lang.String personID, java.lang.String username,
			java.lang.String password)
	{
		this.id = ID;
		this.type = type;
		this.personID = personID;
		this.username = username;
		this.password = password;
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
	 * Obtiene el valor de la propiedad type.
	 * 
	 * @return possible object is {@link SystemParameter }
	 * 
	 */
	public SystemParameter getType()
	{
		return type;
	}


	/**
	 * Define el valor de la propiedad type.
	 * 
	 * @param value allowed object is {@link SystemParameter }
	 * 
	 */
	public void setType(SystemParameter value)
	{
		this.type = value;
	}


	/**
	 * Obtiene el valor de la propiedad personID.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPersonID()
	{
		return personID;
	}


	/**
	 * Define el valor de la propiedad personID.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setPersonID(String value)
	{
		this.personID = value;
	}


	/**
	 * Obtiene el valor de la propiedad username.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUsername()
	{
		return username;
	}


	/**
	 * Define el valor de la propiedad username.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setUsername(String value)
	{
		this.username = value;
	}


	/**
	 * Obtiene el valor de la propiedad password.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPassword()
	{
		return password;
	}


	/**
	 * Define el valor de la propiedad password.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setPassword(String value)
	{
		this.password = value;
	}

}
