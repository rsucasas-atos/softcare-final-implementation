package eu.ehealth.db.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Description of an event
 * 
 * <p>
 * Clase Java para Event complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Event">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EventType" type="{http://aladdin-project.eu/xsd}SystemParameter"/>
 *         &lt;element name="Subject" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Text" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Target" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Event", propOrder = { "eventType", "subject", "text", "target" })
public class Event
{


	@XmlElement(name = "EventType", required = true)
	protected SystemParameter eventType;
	@XmlElement(name = "Subject", required = true)
	protected String subject;
	@XmlElement(name = "Text", required = true)
	protected String text;
	@XmlElement(name = "Target", required = true)
	protected String target;


	/**
	 * Obtiene el valor de la propiedad eventType.
	 * 
	 * @return possible object is {@link SystemParameter }
	 * 
	 */
	public SystemParameter getEventType()
	{
		return eventType;
	}


	/**
	 * Define el valor de la propiedad eventType.
	 * 
	 * @param value allowed object is {@link SystemParameter }
	 * 
	 */
	public void setEventType(SystemParameter value)
	{
		this.eventType = value;
	}


	/**
	 * Obtiene el valor de la propiedad subject.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSubject()
	{
		return subject;
	}


	/**
	 * Define el valor de la propiedad subject.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setSubject(String value)
	{
		this.subject = value;
	}


	/**
	 * Obtiene el valor de la propiedad text.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getText()
	{
		return text;
	}


	/**
	 * Define el valor de la propiedad text.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setText(String value)
	{
		this.text = value;
	}


	/**
	 * Obtiene el valor de la propiedad target.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTarget()
	{
		return target;
	}


	/**
	 * Define el valor de la propiedad target.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setTarget(String value)
	{
		this.target = value;
	}

}
