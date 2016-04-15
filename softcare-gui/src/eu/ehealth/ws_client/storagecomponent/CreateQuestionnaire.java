
package eu.ehealth.ws_client.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.ehealth.ws_client.xsd.Questionnaire;
import eu.ehealth.ws_client.xsd.SystemParameter;


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
 *         &lt;element name="data" type="{http://aladdin-project.eu/xsd}Questionnaire"/>
 *         &lt;element name="locale" type="{http://aladdin-project.eu/xsd}SystemParameter"/>
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "data",
    "locale",
    "userId"
})
@XmlRootElement(name = "CreateQuestionnaire")
public class CreateQuestionnaire {

    @XmlElement(required = true)
    protected Questionnaire data;
    @XmlElement(required = true)
    protected SystemParameter locale;
    @XmlElement(required = true)
    protected String userId;

    /**
     * Obtiene el valor de la propiedad data.
     * 
     * @return
     *     possible object is
     *     {@link Questionnaire }
     *     
     */
    public Questionnaire getData() {
        return data;
    }

    /**
     * Define el valor de la propiedad data.
     * 
     * @param value
     *     allowed object is
     *     {@link Questionnaire }
     *     
     */
    public void setData(Questionnaire value) {
        this.data = value;
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

    /**
     * Obtiene el valor de la propiedad userId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Define el valor de la propiedad userId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

}
