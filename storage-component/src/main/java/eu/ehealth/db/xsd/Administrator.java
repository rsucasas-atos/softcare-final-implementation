package eu.ehealth.db.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * The ALADDIN Administrator
 * 
 * <p>
 * Clase Java para Administrator complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Administrator">
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
@XmlType(name = "Administrator", propOrder = { "id", "personData" })
public class Administrator
{


	@XmlElement(name = "ID", required = true)
	protected String id;
	@XmlElement(name = "PersonData", required = true)
	protected PersonData personData;


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
