
package eu.ehealth.db.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="PatientId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MeasurementType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fromData" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="toData" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
    "patientId",
    "measurementType",
    "fromData",
    "toData",
    "userId"
})
@XmlRootElement(name = "GetPatientMeasurement")
public class GetPatientMeasurement {

    @XmlElement(name = "PatientId", required = true)
    protected String patientId;
    @XmlElement(name = "MeasurementType")
    protected int measurementType;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fromData;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar toData;
    @XmlElement(required = true)
    protected String userId;

    /**
     * Obtiene el valor de la propiedad patientId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * Define el valor de la propiedad patientId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientId(String value) {
        this.patientId = value;
    }

    /**
     * Obtiene el valor de la propiedad measurementType.
     * 
     */
    public int getMeasurementType() {
        return measurementType;
    }

    /**
     * Define el valor de la propiedad measurementType.
     * 
     */
    public void setMeasurementType(int value) {
        this.measurementType = value;
    }

    /**
     * Obtiene el valor de la propiedad fromData.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFromData() {
        return fromData;
    }

    /**
     * Define el valor de la propiedad fromData.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFromData(XMLGregorianCalendar value) {
        this.fromData = value;
    }

    /**
     * Obtiene el valor de la propiedad toData.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getToData() {
        return toData;
    }

    /**
     * Define el valor de la propiedad toData.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setToData(XMLGregorianCalendar value) {
        this.toData = value;
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
