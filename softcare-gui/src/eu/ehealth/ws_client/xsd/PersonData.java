package eu.ehealth.ws_client.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para PersonData complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PersonData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IdentifierList" type="{http://aladdin-project.eu/xsd}IdentifierList"/>
 *         &lt;element name="AddressList" type="{http://aladdin-project.eu/xsd}AddressList"/>
 *         &lt;element name="CommunicationList" type="{http://aladdin-project.eu/xsd}CommunicationList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonData", propOrder = { "surname", "name",
		"identifierList", "addressList", "communicationList" })
public class PersonData
{


	@XmlElement(name = "Surname", required = true)
	protected String surname;
	@XmlElement(name = "Name", required = true)
	protected String name;
	@XmlElement(name = "IdentifierList", required = true)
	protected IdentifierList identifierList;
	@XmlElement(name = "AddressList", required = true)
	protected AddressList addressList;
	@XmlElement(name = "CommunicationList", required = true)
	protected CommunicationList communicationList;


	public PersonData()
	{
	}


	public PersonData(java.lang.String surname, java.lang.String name,
			eu.ehealth.ws_client.xsd.IdentifierList identifierList,
			eu.ehealth.ws_client.xsd.AddressList addressList,
			eu.ehealth.ws_client.xsd.CommunicationList communicationList)
	{
		this.surname = surname;
		this.name = name;
		this.identifierList = identifierList;
		this.addressList = addressList;
		this.communicationList = communicationList;
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


	/**
	 * Obtiene el valor de la propiedad identifierList.
	 * 
	 * @return possible object is {@link IdentifierList }
	 * 
	 */
	public IdentifierList getIdentifierList()
	{
		return identifierList;
	}


	/**
	 * Define el valor de la propiedad identifierList.
	 * 
	 * @param value allowed object is {@link IdentifierList }
	 * 
	 */
	public void setIdentifierList(IdentifierList value)
	{
		this.identifierList = value;
	}


	/**
	 * Obtiene el valor de la propiedad addressList.
	 * 
	 * @return possible object is {@link AddressList }
	 * 
	 */
	public AddressList getAddressList()
	{
		return addressList;
	}


	/**
	 * Define el valor de la propiedad addressList.
	 * 
	 * @param value allowed object is {@link AddressList }
	 * 
	 */
	public void setAddressList(AddressList value)
	{
		this.addressList = value;
	}


	/**
	 * Obtiene el valor de la propiedad communicationList.
	 * 
	 * @return possible object is {@link CommunicationList }
	 * 
	 */
	public CommunicationList getCommunicationList()
	{
		return communicationList;
	}


	/**
	 * Define el valor de la propiedad communicationList.
	 * 
	 * @param value allowed object is {@link CommunicationList }
	 * 
	 */
	public void setCommunicationList(CommunicationList value)
	{
		this.communicationList = value;
	}

}
