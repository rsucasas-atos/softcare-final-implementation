
package eu.ehealth.ws_client.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.ehealth.ws_client.xsd.MediaContent;


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
 *         &lt;element name="ec" type="{http://aladdin-project.eu/xsd}MediaContent"/>
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
    "ec",
    "userId"
})
@XmlRootElement(name = "UpdateMediaContent")
public class UpdateMediaContent {

    @XmlElement(required = true)
    protected MediaContent ec;
    @XmlElement(required = true)
    protected String userId;

    /**
     * Obtiene el valor de la propiedad ec.
     * 
     * @return
     *     possible object is
     *     {@link MediaContent }
     *     
     */
    public MediaContent getEc() {
        return ec;
    }

    /**
     * Define el valor de la propiedad ec.
     * 
     * @param value
     *     allowed object is
     *     {@link MediaContent }
     *     
     */
    public void setEc(MediaContent value) {
        this.ec = value;
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
