package eu.ehealth.db.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Definition of any questionnaire
 * 
 * <p>
 * Clase Java para Questionnaire complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Questionnaire">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="question" type="{http://aladdin-project.eu/xsd}QuestionnaireQuestion" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="title" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Questionnaire", propOrder = { "question" })
public class Questionnaire
{


	@XmlElement(required = true)
	protected List<QuestionnaireQuestion> question;
	@XmlAttribute(name = "version")
	protected Double version;
	@XmlAttribute(name = "ID")
	protected String id;
	@XmlAttribute(name = "title")
	protected String title;


	/**
	 * Gets the value of the question property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the question property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getQuestion().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link QuestionnaireQuestion }
	 * 
	 * 
	 */
	public List<QuestionnaireQuestion> getQuestion()
	{
		if (question == null)
		{
			question = new ArrayList<QuestionnaireQuestion>();
		}
		return this.question;
	}


	/**
	 * Obtiene el valor de la propiedad version.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getVersion()
	{
		if (version == null)
			return 0.0;
		return version;
	}


	/**
	 * Define el valor de la propiedad version.
	 * 
	 * @param value allowed object is {@link Double }
	 * 
	 */
	public void setVersion(Double value)
	{
		this.version = value;
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
	 * Obtiene el valor de la propiedad title.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTitle()
	{
		return title;
	}


	/**
	 * Define el valor de la propiedad title.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setTitle(String value)
	{
		this.title = value;
	}

}
