
package eu.ehealth.ws_client.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.ehealth.ws_client.xsd.CarerAssessment;


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
 *         &lt;element name="assessment" type="{http://aladdin-project.eu/xsd}CarerAssessment"/>
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
    "assessment",
    "userId"
})
@XmlRootElement(name = "SaveCarerAssessment")
public class SaveCarerAssessment {

    @XmlElement(required = true)
    protected CarerAssessment assessment;
    @XmlElement(required = true)
    protected String userId;

    /**
     * Obtiene el valor de la propiedad assessment.
     * 
     * @return
     *     possible object is
     *     {@link CarerAssessment }
     *     
     */
    public CarerAssessment getAssessment() {
        return assessment;
    }

    /**
     * Define el valor de la propiedad assessment.
     * 
     * @param value
     *     allowed object is
     *     {@link CarerAssessment }
     *     
     */
    public void setAssessment(CarerAssessment value) {
        this.assessment = value;
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
