package eu.ehealth.ws_client.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para Clinician complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Clinician">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PersonData" type="{http://aladdin-project.eu/xsd}PersonData"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Clinician", propOrder = { "id", "personData" })
public class Clinician
{


	@XmlElement(name = "ID", required = true)
	protected String id;
	@XmlElement(name = "PersonData", required = true)
	protected PersonData personData;


	public Clinician()
	{
	}


	public Clinician(java.lang.String ID,
			eu.ehealth.ws_client.xsd.PersonData personData)
	{
		this.id = ID;
		this.personData = personData;
	}
	
	
	/**
	 * 
	 */
	public String toString()
	{
    	return this.personData.getSurname()+", "+this.personData.getName();
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
	 * Obtiene el valor de la propiedad personData.
	 * 
	 * @return possible object is {@link PersonData }
	 * 
	 */
	public PersonData getPersonData()
	{
		return personData;
	}


	/**
	 * Define el valor de la propiedad personData.
	 * 
	 * @param value allowed object is {@link PersonData }
	 * 
	 */
	public void setPersonData(PersonData value)
	{
		this.personData = value;
	}

}
