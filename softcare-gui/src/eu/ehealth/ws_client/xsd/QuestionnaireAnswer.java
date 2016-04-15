package eu.ehealth.ws_client.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>
 * Clase Java para QuestionnaireAnswer complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="QuestionnaireAnswer">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="value1" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="questionID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="globalID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestionnaireAnswer", propOrder = { "value" })
public class QuestionnaireAnswer
{


	@XmlValue
	protected String value;
	@XmlAttribute(name = "value1")
	protected String value1;
	@XmlAttribute(name = "questionID")
	protected String questionID;
	@XmlAttribute(name = "globalID")
	protected String globalID;


	public java.lang.String toString()
	{
		return value;
	}


	/**
	 * Obtiene el valor de la propiedad value.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValue()
	{
		return value;
	}


	/**
	 * Define el valor de la propiedad value.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setValue(String value)
	{
		this.value = value;
	}


	/**
	 * Obtiene el valor de la propiedad value1.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValue1()
	{
		return value1;
	}


	/**
	 * Define el valor de la propiedad value1.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setValue1(String value)
	{
		this.value1 = value;
	}


	/**
	 * Obtiene el valor de la propiedad questionID.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getQuestionID()
	{
		return questionID;
	}


	/**
	 * Define el valor de la propiedad questionID.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setQuestionID(String value)
	{
		this.questionID = value;
	}


	/**
	 * Obtiene el valor de la propiedad globalID.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getGlobalID()
	{
		return globalID;
	}


	/**
	 * Define el valor de la propiedad globalID.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setGlobalID(String value)
	{
		this.globalID = value;
	}

}
