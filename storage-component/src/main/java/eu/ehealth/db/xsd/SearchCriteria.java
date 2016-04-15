package eu.ehealth.db.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Description of the search criterias for all entities involved in ALADDIN
 * e.g. patient, carer, clinician, questionnaire, etc.
 * 
 * <p>
 * Clase Java para SearchCriteria complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="SearchCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FieldName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CompareOp" type="{http://aladdin-project.eu/xsd}SystemParameter"/>
 *         &lt;element name="FieldValue1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FieldValue2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchCriteria", propOrder = { "fieldName", "compareOp",
		"fieldValue1", "fieldValue2" })
public class SearchCriteria
{


	@XmlElement(name = "FieldName", required = true)
	protected String fieldName;
	@XmlElement(name = "CompareOp", required = true)
	protected SystemParameter compareOp;
	@XmlElement(name = "FieldValue1", required = true)
	protected String fieldValue1;
	@XmlElement(name = "FieldValue2")
	protected String fieldValue2;


	/**
	 * Obtiene el valor de la propiedad fieldName.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFieldName()
	{
		return fieldName;
	}


	/**
	 * Define el valor de la propiedad fieldName.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setFieldName(String value)
	{
		this.fieldName = value;
	}


	/**
	 * Obtiene el valor de la propiedad compareOp.
	 * 
	 * @return possible object is {@link SystemParameter }
	 * 
	 */
	public SystemParameter getCompareOp()
	{
		return compareOp;
	}


	/**
	 * Define el valor de la propiedad compareOp.
	 * 
	 * @param value allowed object is {@link SystemParameter }
	 * 
	 */
	public void setCompareOp(SystemParameter value)
	{
		this.compareOp = value;
	}


	/**
	 * Obtiene el valor de la propiedad fieldValue1.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFieldValue1()
	{
		return fieldValue1;
	}


	/**
	 * Define el valor de la propiedad fieldValue1.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setFieldValue1(String value)
	{
		this.fieldValue1 = value;
	}


	/**
	 * Obtiene el valor de la propiedad fieldValue2.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFieldValue2()
	{
		return fieldValue2;
	}


	/**
	 * Define el valor de la propiedad fieldValue2.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setFieldValue2(String value)
	{
		this.fieldValue2 = value;
	}

}
