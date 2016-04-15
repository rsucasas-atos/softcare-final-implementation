package eu.ehealth.ws_client.xsd;

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
 * Clase Java para Identifier complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Identifier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Number" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="IssueAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Identifier", propOrder = { "type", "number", "issueDate",
		"issueAuthority" })
public class Identifier
{


	@XmlElement(name = "Type", required = true)
	protected String type;
	@XmlElement(name = "Number", required = true)
	protected String number;
	@XmlElement(name = "IssueDate")
	@XmlSchemaType(name = "date")
	protected XMLGregorianCalendar issueDate;
	@XmlElement(name = "IssueAuthority")
	protected String issueAuthority;


	public Identifier()
	{
	}


	public Identifier(java.lang.String type, java.lang.String number,
			java.util.Date issueDate, java.lang.String issueAuthority)
	{
		this.type = type;
		this.number = number;
		
		this.issueAuthority = issueAuthority;
		
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(issueDate);
			this.issueDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch (Exception ex) {}
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


	/**
	 * Obtiene el valor de la propiedad number.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNumber()
	{
		return number;
	}


	/**
	 * Define el valor de la propiedad number.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setNumber(String value)
	{
		this.number = value;
	}


	/**
	 * Obtiene el valor de la propiedad issueDate.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getIssueDate()
	{
		return issueDate;
	}


	/**
	 * Define el valor de la propiedad issueDate.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setIssueDate(XMLGregorianCalendar value)
	{
		this.issueDate = value;
	}


	/**
	 * Obtiene el valor de la propiedad issueAuthority.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getIssueAuthority()
	{
		return issueAuthority;
	}


	/**
	 * Define el valor de la propiedad issueAuthority.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setIssueAuthority(String value)
	{
		this.issueAuthority = value;
	}

}
