
package eu.ehealth.ws_client.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.ehealth.ws_client.xsd.Warning;


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
 *         &lt;element name="warn" type="{http://aladdin-project.eu/xsd}Warning"/>
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
    "warn",
    "userId"
})
@XmlRootElement(name = "SaveWarning")
public class SaveWarning {

    @XmlElement(required = true)
    protected Warning warn;
    @XmlElement(required = true)
    protected String userId;

    /**
     * Obtiene el valor de la propiedad warn.
     * 
     * @return
     *     possible object is
     *     {@link Warning }
     *     
     */
    public Warning getWarn() {
        return warn;
    }

    /**
     * Define el valor de la propiedad warn.
     * 
     * @param value
     *     allowed object is
     *     {@link Warning }
     *     
     */
    public void setWarn(Warning value) {
        this.warn = value;
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
