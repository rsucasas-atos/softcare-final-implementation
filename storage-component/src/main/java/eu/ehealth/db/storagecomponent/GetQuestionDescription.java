
package eu.ehealth.db.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.ehealth.db.xsd.SystemParameter;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="questionID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="locale" type="{http://aladdin-project.eu/xsd}SystemParameter"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "questionID",
    "locale"
})
@XmlRootElement(name = "GetQuestionDescription")
public class GetQuestionDescription {

    @XmlElement(required = true)
    protected String questionID;
    @XmlElement(required = true)
    protected SystemParameter locale;

    /**
     * Obtiene el valor de la propiedad questionID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuestionID() {
        return questionID;
    }

    /**
     * Define el valor de la propiedad questionID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuestionID(String value) {
        this.questionID = value;
    }

    /**
     * Obtiene el valor de la propiedad locale.
     * 
     * @return
     *     possible object is
     *     {@link SystemParameter }
     *     
     */
    public SystemParameter getLocale() {
        return locale;
    }

    /**
     * Define el valor de la propiedad locale.
     * 
     * @param value
     *     allowed object is
     *     {@link SystemParameter }
     *     
     */
    public void setLocale(SystemParameter value) {
        this.locale = value;
    }

}
