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
 * Clase Java para Task complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Task">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TaskType" type="{http://aladdin-project.eu/xsd}SystemParameter"/>
 *         &lt;element name="DateTimeAssigned" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="DateTimeFulfilled" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="TaskStatus" type="{http://aladdin-project.eu/xsd}SystemParameter"/>
 *         &lt;element name="URL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Text" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Questionnaire" type="{http://aladdin-project.eu/xsd}Questionnaire" minOccurs="0"/>
 *         &lt;element name="ExecutorID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AssignerID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ObjectID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Task", propOrder = { "id", "taskType", "dateTimeAssigned",
		"dateTimeFulfilled", "taskStatus", "url", "text", "questionnaire",
		"executorID", "assignerID", "objectID" })
public class Task
{


	@XmlElement(name = "ID", required = true)
	protected String id;
	@XmlElement(name = "TaskType", required = true)
	protected SystemParameter taskType;
	@XmlElement(name = "DateTimeAssigned", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar dateTimeAssigned;
	@XmlElement(name = "DateTimeFulfilled", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar dateTimeFulfilled;
	@XmlElement(name = "TaskStatus", required = true)
	protected SystemParameter taskStatus;
	@XmlElement(name = "URL")
	protected String url;
	@XmlElement(name = "Text")
	protected String text;
	@XmlElement(name = "Questionnaire")
	protected Questionnaire questionnaire;
	@XmlElement(name = "ExecutorID", required = true)
	protected String executorID;
	@XmlElement(name = "AssignerID", required = true)
	protected String assignerID;
	@XmlElement(name = "ObjectID", required = true)
	protected String objectID;


	public Task()
	{
	}


	public Task(java.lang.String ID,
			eu.ehealth.ws_client.xsd.SystemParameter taskType,
			java.util.Calendar dateTimeAssigned,
			java.util.Calendar dateTimeFulfilled,
			eu.ehealth.ws_client.xsd.SystemParameter taskStatus,
			java.lang.String URL, java.lang.String text,
			eu.ehealth.ws_client.xsd.Questionnaire questionnaire,
			java.lang.String executorID, java.lang.String assignerID,
			java.lang.String objectID)
	{
		this.id = ID;
		this.taskType = taskType;
		this.taskStatus = taskStatus;
		this.url = URL;
		this.text = text;
		this.questionnaire = questionnaire;
		this.executorID = executorID;
		this.assignerID = assignerID;
		this.objectID = objectID;
		
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(dateTimeAssigned.getTime());
			this.dateTimeAssigned = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch (Exception ex) {}
		
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(dateTimeFulfilled.getTime());
			this.dateTimeFulfilled = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch (Exception ex) {}
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
	 * Obtiene el valor de la propiedad taskType.
	 * 
	 * @return possible object is {@link SystemParameter }
	 * 
	 */
	public SystemParameter getTaskType()
	{
		return taskType;
	}


	/**
	 * Define el valor de la propiedad taskType.
	 * 
	 * @param value allowed object is {@link SystemParameter }
	 * 
	 */
	public void setTaskType(SystemParameter value)
	{
		this.taskType = value;
	}


	/**
	 * Obtiene el valor de la propiedad dateTimeAssigned.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getDateTimeAssigned()
	{
		return dateTimeAssigned;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public GregorianCalendar getDateTimeAssignedCalendar()
	{
		return dateTimeAssigned.toGregorianCalendar();
	}


	/**
	 * Define el valor de la propiedad dateTimeAssigned.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setDateTimeAssigned(XMLGregorianCalendar value)
	{
		this.dateTimeAssigned = value;
	}


	/**
	 * Obtiene el valor de la propiedad dateTimeFulfilled.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getDateTimeFulfilled()
	{
		return dateTimeFulfilled;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public GregorianCalendar getDateTimeFulfilledCalendar()
	{
		return dateTimeFulfilled.toGregorianCalendar();
	}


	/**
	 * Define el valor de la propiedad dateTimeFulfilled.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setDateTimeFulfilled(XMLGregorianCalendar value)
	{
		this.dateTimeFulfilled = value;
	}


	/**
	 * Obtiene el valor de la propiedad taskStatus.
	 * 
	 * @return possible object is {@link SystemParameter }
	 * 
	 */
	public SystemParameter getTaskStatus()
	{
		return taskStatus;
	}


	/**
	 * Define el valor de la propiedad taskStatus.
	 * 
	 * @param value allowed object is {@link SystemParameter }
	 * 
	 */
	public void setTaskStatus(SystemParameter value)
	{
		this.taskStatus = value;
	}


	/**
	 * Obtiene el valor de la propiedad url.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getURL()
	{
		return url;
	}


	/**
	 * Define el valor de la propiedad url.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setURL(String value)
	{
		this.url = value;
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
	 * Obtiene el valor de la propiedad questionnaire.
	 * 
	 * @return possible object is {@link Questionnaire }
	 * 
	 */
	public Questionnaire getQuestionnaire()
	{
		return questionnaire;
	}


	/**
	 * Define el valor de la propiedad questionnaire.
	 * 
	 * @param value allowed object is {@link Questionnaire }
	 * 
	 */
	public void setQuestionnaire(Questionnaire value)
	{
		this.questionnaire = value;
	}


	/**
	 * Obtiene el valor de la propiedad executorID.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getExecutorID()
	{
		return executorID;
	}


	/**
	 * Define el valor de la propiedad executorID.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setExecutorID(String value)
	{
		this.executorID = value;
	}


	/**
	 * Obtiene el valor de la propiedad assignerID.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getAssignerID()
	{
		return assignerID;
	}


	/**
	 * Define el valor de la propiedad assignerID.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setAssignerID(String value)
	{
		this.assignerID = value;
	}


	/**
	 * Obtiene el valor de la propiedad objectID.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getObjectID()
	{
		return objectID;
	}


	/**
	 * Define el valor de la propiedad objectID.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setObjectID(String value)
	{
		this.objectID = value;
	}

}
