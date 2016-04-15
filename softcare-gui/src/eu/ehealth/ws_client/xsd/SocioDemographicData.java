package eu.ehealth.ws_client.xsd;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>
 * Clase Java para SocioDemographicData complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SocioDemographicData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Gender" type="{http://aladdin-project.eu/xsd}SystemParameter" minOccurs="0"/>
 *         &lt;element name="MaritalStatus" type="{http://aladdin-project.eu/xsd}SystemParameter" minOccurs="0"/>
 *         &lt;element name="Children" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *         &lt;element name="LivingWith" type="{http://aladdin-project.eu/xsd}SystemParameter" minOccurs="0"/>
 *         &lt;element name="birthday" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocioDemographicData", propOrder = { "gender", "maritalStatus", "children", "livingWith", "birthday", "height" })
public class SocioDemographicData
{


	@XmlElement(name = "Gender")
	protected SystemParameter gender;
	@XmlElement(name = "MaritalStatus")
	protected SystemParameter maritalStatus;
	@XmlElement(name = "Children")
	@XmlSchemaType(name = "unsignedByte")
	protected Short children;
	@XmlElement(name = "LivingWith")
	protected SystemParameter livingWith;
	@XmlElement(required = true)
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar birthday;
	@XmlElement(name = "Height")
	protected SystemParameter height;


	public SocioDemographicData()
	{
	}


	/**
	 * 
	 * @param gender
	 * @param maritalStatus
	 * @param children
	 * @param livingWith
	 * @param birthday
	 * @param height
	 */
	public SocioDemographicData(eu.ehealth.ws_client.xsd.SystemParameter gender,
			eu.ehealth.ws_client.xsd.SystemParameter maritalStatus,
			Short children,
			eu.ehealth.ws_client.xsd.SystemParameter livingWith,
			java.util.Date birthday,
			eu.ehealth.ws_client.xsd.SystemParameter height)
	{
		this.gender = gender;
		this.maritalStatus = maritalStatus;
		this.children = children;
		this.livingWith = livingWith;
		
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(birthday);
			this.birthday = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch (Exception ex) {}
	}


	/**
	 * Obtiene el valor de la propiedad gender.
	 * 
	 * @return possible object is {@link SystemParameter }
	 * 
	 */
	public SystemParameter getGender()
	{
		return gender;
	}


	/**
	 * Define el valor de la propiedad gender.
	 * 
	 * @param value allowed object is {@link SystemParameter }
	 * 
	 */
	public void setGender(SystemParameter value)
	{
		this.gender = value;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public SystemParameter getHeight()
	{
		return height;
	}


	/**
	 * Define el valor de la propiedad Height.
	 * 
	 * @param value
	 *            allowed object is {@link SystemParameter }
	 * 
	 */
	public void setHeight(SystemParameter value)
	{
		this.height = value;
	}


	/**
	 * Obtiene el valor de la propiedad maritalStatus.
	 * 
	 * @return possible object is {@link SystemParameter }
	 * 
	 */
	public SystemParameter getMaritalStatus()
	{
		return maritalStatus;
	}


	/**
	 * Define el valor de la propiedad maritalStatus.
	 * 
	 * @param value allowed object is {@link SystemParameter }
	 * 
	 */
	public void setMaritalStatus(SystemParameter value)
	{
		this.maritalStatus = value;
	}


	/**
	 * Obtiene el valor de la propiedad children.
	 * 
	 * @return possible object is {@link Short }
	 * 
	 */
	public Short getChildren()
	{
		return children;
	}


	/**
	 * Define el valor de la propiedad children.
	 * 
	 * @param value allowed object is {@link Short }
	 * 
	 */
	public void setChildren(Short value)
	{
		this.children = value;
	}


	/**
	 * Obtiene el valor de la propiedad livingWith.
	 * 
	 * @return possible object is {@link SystemParameter }
	 * 
	 */
	public SystemParameter getLivingWith()
	{
		return livingWith;
	}


	/**
	 * Define el valor de la propiedad livingWith.
	 * 
	 * @param value allowed object is {@link SystemParameter }
	 * 
	 */
	public void setLivingWith(SystemParameter value)
	{
		this.livingWith = value;
	}


	/**
	 * Obtiene el valor de la propiedad birthday.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getBirthday()
	{
		return birthday;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Date getBirthdayDate()
	{
		return birthday.toGregorianCalendar().getTime();
	}
	

	/**
	 * Define el valor de la propiedad birthday.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setBirthday(XMLGregorianCalendar value)
	{
		this.birthday = value;
	}
	
	
	/**
	 * 
	 * @param value
	 */
	public void setBirthdayDate(Date value)
	{
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(value);
			this.birthday = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch (Exception ex) {}
	}

}
