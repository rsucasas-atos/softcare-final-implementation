
package eu.ehealth.db.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.ehealth.db.xsd.Carer;


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
 *         &lt;element name="out" type="{http://aladdin-project.eu/xsd}Carer"/>
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
    "out"
})
@XmlRootElement(name = "GetCarerResponse")
public class GetCarerResponse {

    @XmlElement(required = true)
    protected Carer out;

    /**
     * Obtiene el valor de la propiedad out.
     * 
     * @return
     *     possible object is
     *     {@link Carer }
     *     
     */
    public Carer getOut() {
        return out;
    }

    /**
     * Define el valor de la propiedad out.
     * 
     * @param value
     *     allowed object is
     *     {@link Carer }
     *     
     */
    public void setOut(Carer value) {
        this.out = value;
    }

}
