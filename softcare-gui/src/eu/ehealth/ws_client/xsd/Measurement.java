package eu.ehealth.ws_client.xsd;

import java.util.GregorianCalendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>
 * Clase Java para Measurement complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Measurement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Type" type="{http://aladdin-project.eu/xsd}SystemParameter"/>
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="DateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Units" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LowerLimit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="UpperLimit" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TaskID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Measurement", propOrder = { "type", "value", "dateTime",
		"units", "lowerLimit", "upperLimit", "taskID" })
public class Measurement
{


	@XmlElement(name = "Type", required = true)
	protected SystemParameter type;
	@XmlElement(name = "Value")
	protected double value;
	@XmlElement(name = "DateTime", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar dateTime;
	@XmlElement(name = "Units", required = true)
	protected String units;
	@XmlElement(name = "LowerLimit")
	protected Double lowerLimit;
	@XmlElement(name = "UpperLimit")
	protected Double upperLimit;
	@XmlElement(name = "TaskID", required = true)
	protected String taskID;


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
	 * Obtiene el valor de la propiedad value.
	 * 
	 */
	public double getValue()
	{
		return value;
	}


	/**
	 * Define el valor de la propiedad value.
	 * 
	 */
	public void setValue(double value)
	{
		this.value = value;
	}


	/**
	 * Obtiene el valor de la propiedad dateTime.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getDateTime()
	{
		return dateTime;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public GregorianCalendar getDateTimeCalendar()
	{
		return dateTime.toGregorianCalendar();
	}


	/**
	 * Define el valor de la propiedad dateTime.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setDateTime(XMLGregorianCalendar value)
	{
		this.dateTime = value;
	}


	/**
	 * Obtiene el valor de la propiedad units.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUnits()
	{
		return units;
	}


	/**
	 * Define el valor de la propiedad units.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setUnits(String value)
	{
		this.units = value;
	}


	/**
	 * Obtiene el valor de la propiedad lowerLimit.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getLowerLimit()
	{
		return lowerLimit;
	}


	/**
	 * Define el valor de la propiedad lowerLimit.
	 * 
	 * @param value allowed object is {@link Double }
	 * 
	 */
	public void setLowerLimit(Double value)
	{
		this.lowerLimit = value;
	}


	/**
	 * Obtiene el valor de la propiedad upperLimit.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getUpperLimit()
	{
		return upperLimit;
	}


	/**
	 * Define el valor de la propiedad upperLimit.
	 * 
	 * @param value allowed object is {@link Double }
	 * 
	 */
	public void setUpperLimit(Double value)
	{
		this.upperLimit = value;
	}


	/**
	 * Obtiene el valor de la propiedad taskID.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTaskID()
	{
		return taskID;
	}


	/**
	 * Define el valor de la propiedad taskID.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setTaskID(String value)
	{
		this.taskID = value;
	}

}
