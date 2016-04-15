
package eu.ehealth.db.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.ehealth.db.xsd.PatientAssessment;


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
 *         &lt;element name="assessment" type="{http://aladdin-project.eu/xsd}PatientAssessment"/>
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
@XmlRootElement(name = "SavePatientAssessment")
public class SavePatientAssessment {

    @XmlElement(required = true)
    protected PatientAssessment assessment;
    @XmlElement(required = true)
    protected String userId;

    /**
     * Obtiene el valor de la propiedad assessment.
     * 
     * @return
     *     possible object is
     *     {@link PatientAssessment }
     *     
     */
    public PatientAssessment getAssessment() {
        return assessment;
    }

    /**
     * Define el valor de la propiedad assessment.
     * 
     * @param value
     *     allowed object is
     *     {@link PatientAssessment }
     *     
     */
    public void setAssessment(PatientAssessment value) {
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
