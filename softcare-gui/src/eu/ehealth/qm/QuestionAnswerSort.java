package eu.ehealth.qm;

import java.util.Comparator;
import eu.ehealth.ws_client.xsd.QuestionnaireQuestionAnswer;

public class QuestionAnswerSort implements Comparator <QuestionnaireQuestionAnswer>{

	public int compare(QuestionnaireQuestionAnswer arg0, QuestionnaireQuestionAnswer arg1) {
		return arg0.getPosition()-arg1.getPosition();
	}

}
