package eu.ehealth.db.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Carer assessment (whenever applicable)
 * 
 * <p>
 * Clase Java para CarerAssessment complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CarerAssessment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CarerID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ClinicianID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DateOfAssessment" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="RelevantHealthProblem" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SeverityOfBurden" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="EmotionalOrMentalDisorders" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PsychoactiveDrugs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="QualityOfLife" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CarerAssessment", propOrder = { "id", "carerID",
		"clinicianID", "dateOfAssessment", "relevantHealthProblem",
		"severityOfBurden", "emotionalOrMentalDisorders", "psychoactiveDrugs",
		"qualityOfLife" })
public class CarerAssessment
{


	@XmlElement(name = "ID", required = true)
	protected String id;
	@XmlElement(name = "CarerID", required = true)
	protected String carerID;
	@XmlElement(name = "ClinicianID", required = true)
	protected String clinicianID;
	@XmlElement(name = "DateOfAssessment", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar dateOfAssessment;
	@XmlElement(name = "RelevantHealthProblem")
	protected String relevantHealthProblem;
	@XmlElement(name = "SeverityOfBurden")
	@XmlSchemaType(name = "unsignedByte")
	protected short severityOfBurden;
	@XmlElement(name = "EmotionalOrMentalDisorders")
	protected String emotionalOrMentalDisorders;
	@XmlElement(name = "PsychoactiveDrugs")
	protected String psychoactiveDrugs;
	@XmlElement(name = "QualityOfLife")
	@XmlSchemaType(name = "unsignedByte")
	protected Short qualityOfLife;


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
	 * Obtiene el valor de la propiedad carerID.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCarerID()
	{
		return carerID;
	}


	/**
	 * Define el valor de la propiedad carerID.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCarerID(String value)
	{
		this.carerID = value;
	}


	/**
	 * Obtiene el valor de la propiedad clinicianID.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getClinicianID()
	{
		return clinicianID;
	}


	/**
	 * Define el valor de la propiedad clinicianID.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setClinicianID(String value)
	{
		this.clinicianID = value;
	}


	/**
	 * Obtiene el valor de la propiedad dateOfAssessment.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getDateOfAssessment()
	{
		return dateOfAssessment;
	}


	/**
	 * Define el valor de la propiedad dateOfAssessment.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setDateOfAssessment(XMLGregorianCalendar value)
	{
		this.dateOfAssessment = value;
	}


	/**
	 * Obtiene el valor de la propiedad relevantHealthProblem.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRelevantHealthProblem()
	{
		return relevantHealthProblem;
	}


	/**
	 * Define el valor de la propiedad relevantHealthProblem.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setRelevantHealthProblem(String value)
	{
		this.relevantHealthProblem = value;
	}


	/**
	 * Obtiene el valor de la propiedad severityOfBurden.
	 * 
	 */
	public short getSeverityOfBurden()
	{
		return severityOfBurden;
	}


	/**
	 * Define el valor de la propiedad severityOfBurden.
	 * 
	 */
	public void setSeverityOfBurden(short value)
	{
		this.severityOfBurden = value;
	}


	/**
	 * Obtiene el valor de la propiedad emotionalOrMentalDisorders.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEmotionalOrMentalDisorders()
	{
		return emotionalOrMentalDisorders;
	}


	/**
	 * Define el valor de la propiedad emotionalOrMentalDisorders.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setEmotionalOrMentalDisorders(String value)
	{
		this.emotionalOrMentalDisorders = value;
	}


	/**
	 * Obtiene el valor de la propiedad psychoactiveDrugs.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPsychoactiveDrugs()
	{
		return psychoactiveDrugs;
	}


	/**
	 * Define el valor de la propiedad psychoactiveDrugs.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setPsychoactiveDrugs(String value)
	{
		this.psychoactiveDrugs = value;
	}


	/**
	 * Obtiene el valor de la propiedad qualityOfLife.
	 * 
	 * @return possible object is {@link Short }
	 * 
	 */
	public Short getQualityOfLife()
	{
		if (qualityOfLife == null)
			return 0;
		return qualityOfLife;
	}


	/**
	 * Define el valor de la propiedad qualityOfLife.
	 * 
	 * @param value allowed object is {@link Short }
	 * 
	 */
	public void setQualityOfLife(Short value)
	{
		this.qualityOfLife = value;
	}

}
