
package eu.ehealth.db.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import eu.ehealth.db.xsd.SystemParameter;
import eu.ehealth.db.xsd.Task;


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
 *         &lt;element name="task" type="{http://aladdin-project.eu/xsd}Task"/>
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
    "task",
    "locale",
    "userId"
})
@XmlRootElement(name = "AssignTask")
public class AssignTask {

    @XmlElement(required = true)
    protected Task task;
    @XmlElement(required = true)
    protected SystemParameter locale;
    @XmlElement(required = true)
    protected String userId;

    /**
     * Obtiene el valor de la propiedad task.
     * 
     * @return
     *     possible object is
     *     {@link Task }
     *     
     */
    public Task getTask() {
        return task;
    }

    /**
     * Define el valor de la propiedad task.
     * 
     * @param value
     *     allowed object is
     *     {@link Task }
     *     
     */
    public void setTask(Task value) {
        this.task = value;
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
