package eu.ehealth.ws_client.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para QuestionnaireInfo complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="QuestionnaireInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
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
@XmlType(name = "QuestionnaireInfo")
public class QuestionnaireInfo
{


	@XmlAttribute(name = "version")
	protected Double version;
	@XmlAttribute(name = "ID")
	protected String id;
	@XmlAttribute(name = "title")
	protected String title;
	
	
	/**
	 * 
	 */
	public String toString(){
    	return this.title + "(v" + version + ")";
    }


	/**
	 * Obtiene el valor de la propiedad version.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getVersion()
	{
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
