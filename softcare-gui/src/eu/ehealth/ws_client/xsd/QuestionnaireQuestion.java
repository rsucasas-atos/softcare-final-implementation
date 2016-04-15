
package eu.ehealth.ws_client.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para QuestionnaireQuestion complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="QuestionnaireQuestion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GlobalID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="answers" type="{http://aladdin-project.eu/xsd}QuestionnaireQuestionAnswerList"/>
 *         &lt;element name="questions" type="{http://aladdin-project.eu/xsd}QuestionnaireQuestionList"/>
 *         &lt;element name="position" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="condition" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestionnaireQuestion", propOrder = {
    "title",
    "globalID",
    "answers",
    "questions",
    "position"
})
public class QuestionnaireQuestion {

    @XmlElement(required = true)
    protected String title;
    @XmlElement(name = "GlobalID")
    protected int globalID;
    @XmlElement(required = true)
    protected QuestionnaireQuestionAnswerList answers;
    @XmlElement(required = true)
    protected QuestionnaireQuestionList questions;
    protected int position;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "type")
    protected String type;
    @XmlAttribute(name = "condition")
    @XmlSchemaType(name = "unsignedByte")
    protected Short condition;

    /**
     * Obtiene el valor de la propiedad title.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Define el valor de la propiedad title.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Obtiene el valor de la propiedad globalID.
     * 
     */
    public int getGlobalID() {
        return globalID;
    }

    /**
     * Define el valor de la propiedad globalID.
     * 
     */
    public void setGlobalID(int value) {
        this.globalID = value;
    }

    /**
     * Obtiene el valor de la propiedad answers.
     * 
     * @return
     *     possible object is
     *     {@link QuestionnaireQuestionAnswerList }
     *     
     */
    public QuestionnaireQuestionAnswerList getAnswers() {
        return answers;
    }

    /**
     * Define el valor de la propiedad answers.
     * 
     * @param value
     *     allowed object is
     *     {@link QuestionnaireQuestionAnswerList }
     *     
     */
    public void setAnswers(QuestionnaireQuestionAnswerList value) {
        this.answers = value;
    }

    /**
     * Obtiene el valor de la propiedad questions.
     * 
     * @return
     *     possible object is
     *     {@link QuestionnaireQuestionList }
     *     
     */
    public QuestionnaireQuestionList getQuestions() {
        return questions;
    }

    /**
     * Define el valor de la propiedad questions.
     * 
     * @param value
     *     allowed object is
     *     {@link QuestionnaireQuestionList }
     *     
     */
    public void setQuestions(QuestionnaireQuestionList value) {
        this.questions = value;
    }

    /**
     * Obtiene el valor de la propiedad position.
     * 
     */
    public int getPosition() {
        return position;
    }

    /**
     * Define el valor de la propiedad position.
     * 
     */
    public void setPosition(int value) {
        this.position = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Define el valor de la propiedad type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Obtiene el valor de la propiedad condition.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCondition() {
        return condition;
    }

    /**
     * Define el valor de la propiedad condition.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCondition(Short value) {
        this.condition = value;
    }

}
