package eu.ehealth.ws_client.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para QuestionnaireQuestionAnswerList complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="QuestionnaireQuestionAnswerList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="answer" type="{http://aladdin-project.eu/xsd}QuestionnaireQuestionAnswer" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestionnaireQuestionAnswerList", propOrder = { "answer" })
public class QuestionnaireQuestionAnswerList
{


	@XmlElement(nillable = true)
	protected List<QuestionnaireQuestionAnswer> answer;


	/**
	 * Gets the value of the answer property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the answer property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAnswer().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link QuestionnaireQuestionAnswer }
	 * 
	 * 
	 */
	public List<QuestionnaireQuestionAnswer> getAnswer()
	{
		if (answer == null)
		{
			answer = new ArrayList<QuestionnaireQuestionAnswer>();
		}
		return this.answer;
	}


	/**
	 * 
	 * @param l
	 */
	public void setAnswer(List<QuestionnaireQuestionAnswer> l)
	{
		this.answer = l;
	}

}
