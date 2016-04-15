package eu.ehealth.db.xsd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para QuestionnaireQuestionAnswerType.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="QuestionnaireQuestionAnswerType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OneAnswer"/>
 *     &lt;enumeration value="ManyAnswers"/>
 *     &lt;enumeration value="FreeText"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "QuestionnaireQuestionAnswerType")
@XmlEnum
public enum QuestionnaireQuestionAnswerType
{

	@XmlEnumValue("OneAnswer")
	ONE_ANSWER("OneAnswer"), @XmlEnumValue("ManyAnswers")
	MANY_ANSWERS("ManyAnswers"), @XmlEnumValue("FreeText")
	FREE_TEXT("FreeText");
	private final String value;


	QuestionnaireQuestionAnswerType(String v)
	{
		value = v;
	}


	public String value()
	{
		return value;
	}


	public static QuestionnaireQuestionAnswerType fromValue(String v)
	{
		for (QuestionnaireQuestionAnswerType c : QuestionnaireQuestionAnswerType
				.values())
		{
			if (c.value.equals(v))
			{
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
