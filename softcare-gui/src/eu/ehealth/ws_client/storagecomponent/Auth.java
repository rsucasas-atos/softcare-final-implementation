package eu.ehealth.ws_client.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para anonymous complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="login" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "login", "password", "token" })
@XmlRootElement(name = "Auth")
public class Auth
{


	@XmlElement(required = true)
	protected String login;
	@XmlElement(required = true)
	protected String password;
	@XmlElement(required = true)
	protected String token;


	/**
	 * Obtiene el valor de la propiedad login.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLogin()
	{
		return login;
	}


	/**
	 * Define el valor de la propiedad login.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setLogin(String value)
	{
		this.login = value;
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
	
	
	/**
	 * Obtiene el valor de la propiedad token.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getToken()
	{
		return token;
	}


	/**
	 * Define el valor de la propiedad token.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setTokne(String value)
	{
		this.token = value;
	}
	

}
