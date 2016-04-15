package eu.ehealth.ws_client.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para Address complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Address">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Street" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StreetNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="City" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="County" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZipCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "Address", propOrder = { "street", "streetNo", "city",
		"county", "country", "zipCode", "notes", "isPrimary" })
public class Address
{


	@XmlElement(name = "Street", required = true)
	protected String street;
	@XmlElement(name = "StreetNo")
	protected String streetNo;
	@XmlElement(name = "City", required = true)
	protected String city;
	@XmlElement(name = "County")
	protected String county;
	@XmlElement(name = "Country", required = true)
	protected String country;
	@XmlElement(name = "ZipCode")
	protected String zipCode;
	@XmlElement(name = "Notes")
	protected String notes;
	@XmlElement(name = "IsPrimary")
	protected boolean isPrimary;


	public Address()
	{
	}


	public Address(java.lang.String street, java.lang.String streetNo,
			java.lang.String city, java.lang.String county,
			java.lang.String country, java.lang.String zipCode,
			java.lang.String notes, boolean isPrimary)
	{
		this.street = street;
		this.streetNo = streetNo;
		this.city = city;
		this.county = county;
		this.country = country;
		this.zipCode = zipCode;
		this.notes = notes;
		this.isPrimary = isPrimary;
	}


	/**
	 * Obtiene el valor de la propiedad street.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getStreet()
	{
		return street;
	}


	/**
	 * Define el valor de la propiedad street.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setStreet(String value)
	{
		this.street = value;
	}


	/**
	 * Obtiene el valor de la propiedad streetNo.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getStreetNo()
	{
		return streetNo;
	}


	/**
	 * Define el valor de la propiedad streetNo.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setStreetNo(String value)
	{
		this.streetNo = value;
	}


	/**
	 * Obtiene el valor de la propiedad city.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCity()
	{
		return city;
	}


	/**
	 * Define el valor de la propiedad city.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCity(String value)
	{
		this.city = value;
	}


	/**
	 * Obtiene el valor de la propiedad county.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCounty()
	{
		return county;
	}


	/**
	 * Define el valor de la propiedad county.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCounty(String value)
	{
		this.county = value;
	}


	/**
	 * Obtiene el valor de la propiedad country.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCountry()
	{
		return country;
	}


	/**
	 * Define el valor de la propiedad country.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCountry(String value)
	{
		this.country = value;
	}


	/**
	 * Obtiene el valor de la propiedad zipCode.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZipCode()
	{
		return zipCode;
	}


	/**
	 * Define el valor de la propiedad zipCode.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setZipCode(String value)
	{
		this.zipCode = value;
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
