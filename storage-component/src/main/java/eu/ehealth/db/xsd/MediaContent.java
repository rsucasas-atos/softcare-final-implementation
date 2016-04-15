package eu.ehealth.db.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Media Content
 * 
 * <p>
 * Clase Java para MediaContent complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="MediaContent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="category" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MediaContent", propOrder = { "id", "title", "url", "type",
		"category", "text", "enabled" })
public class MediaContent
{


	@XmlElement(name = "ID", required = true)
	protected String id;
	@XmlElement(required = true)
	protected String title;
	@XmlElement(required = true)
	protected String url;
	@XmlElement(required = true)
	protected String type;
	@XmlElement(required = true)
	protected String category;
	@XmlElement(required = true)
	protected String text;
	protected boolean enabled;


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


	/**
	 * Obtiene el valor de la propiedad url.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUrl()
	{
		return url;
	}


	/**
	 * Define el valor de la propiedad url.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setUrl(String value)
	{
		this.url = value;
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
	 * Obtiene el valor de la propiedad category.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCategory()
	{
		return category;
	}


	/**
	 * Define el valor de la propiedad category.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCategory(String value)
	{
		this.category = value;
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
	 * Obtiene el valor de la propiedad enabled.
	 * 
	 */
	public boolean isEnabled()
	{
		return enabled;
	}


	/**
	 * Define el valor de la propiedad enabled.
	 * 
	 */
	public void setEnabled(boolean value)
	{
		this.enabled = value;
	}

}
