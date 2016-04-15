
package eu.ehealth.db.storagecomponent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="TaskId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TaskStatus" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "taskId",
    "taskStatus",
    "userId"
})
@XmlRootElement(name = "ChangeTaskStatus")
public class ChangeTaskStatus {

    @XmlElement(name = "TaskId")
    protected int taskId;
    @XmlElement(name = "TaskStatus")
    protected int taskStatus;
    @XmlElement(required = true)
    protected String userId;

    /**
     * Obtiene el valor de la propiedad taskId.
     * 
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * Define el valor de la propiedad taskId.
     * 
     */
    public void setTaskId(int value) {
        this.taskId = value;
    }

    /**
     * Obtiene el valor de la propiedad taskStatus.
     * 
     */
    public int getTaskStatus() {
        return taskStatus;
    }

    /**
     * Define el valor de la propiedad taskStatus.
     * 
     */
    public void setTaskStatus(int value) {
        this.taskStatus = value;
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
