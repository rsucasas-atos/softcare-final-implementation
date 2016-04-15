package eu.ehealth.qm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;
import eu.ehealth.SystemDictionary;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.OperationResult;
import eu.ehealth.ws_client.xsd.Questionnaire;
import eu.ehealth.ws_client.xsd.QuestionnaireQuestion;
import eu.ehealth.ws_client.xsd.QuestionnaireQuestionAnswer;
import eu.ehealth.ws_client.xsd.QuestionnaireQuestionList;
import eu.ehealth.ws_client.xsd.SystemParameter;


public class QuestionnaireTranslateWindow extends Window
{


	private static final long serialVersionUID = 8431219123393105081L;
	private ArrayList<RelatedQuestion> questionlist = new ArrayList<RelatedQuestion>();
	private ArrayList<RelatedQuestion> localeqlist = new ArrayList<RelatedQuestion>();
	private String currentquestionnaireid;
	private static String fieldWidth = "300px";


	/**
	 * 
	 */
	public QuestionnaireTranslateWindow() {}


	/**
	 * 
	 * @param qid
	 */
	public void translateQuestionnaire(String qid)
	{
		this.currentquestionnaireid = qid;
		try
		{
			Questionnaire questions = null;
			String userid = (String) Sessions.getCurrent().getAttribute("userid");
			StorageComponentImpl proxy = SystemDictionary.getSCProxy();

			questions = proxy.getQuestionnaire(qid, SystemDictionary.getLocale(), userid);
			Double version = questions.getVersion();
			((Textbox) getFellow("trans_version")).setValue(version.toString());
			((Textbox) getFellow("trans_version")).setReadonly(true);
			((Textbox) getFellow("trans_title_original")).setValue(questions.getTitle());
			printQuestions(questions.getQuestion().toArray(new QuestionnaireQuestion[questions.getQuestion().size()]), "0");
			
			SystemDictionary.webguiLog("DEBUG", "WEBGUI : translateQuestionnaire : questionlist.size() : " + questionlist.size());
			// Initialize Questions
			for (int i = 0; i < questionlist.size(); i++)
			{
				questionlist.get(i).getQuestion().getQuestions().getQuestion().add(new QuestionnaireQuestion());
			}
			
			// Get supported locales
			SystemParameter[] locales = proxy.listOfSupportedLocalesArr();
			for (int i = 0; i < locales.length; i++)
			{
				Listitem litem = new Listitem(locales[i].getDescription(), locales[i].getDescription());
				((Listbox) getFellow("trans_locale")).appendChild(litem);
			}
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("ERROR", "WEBGUI : translateQuestionnaire : " + e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * 
	 * @throws SuspendNotAllowedException
	 * @throws ComponentNotFoundException
	 * @throws InterruptedException
	 */
	public void changeSelectedLocale() throws SuspendNotAllowedException,
			ComponentNotFoundException, InterruptedException
	{
		Listitem locale = ((Listbox) getFellow("trans_locale")).getSelectedItem();
		if (locale.getValue().equals("none"))
		{
			((Window) getFellow("trans_alert")).doModal();
		}
		else
		{
			try
			{
				SystemParameter syslocale = new SystemParameter(locale.getLabel(), locale.getLabel());
				SystemDictionary.webguiLog("TRACE", "LOCALE : " + syslocale.getCode());
				Questionnaire questions = null;
				String userid = (String) Sessions.getCurrent().getAttribute("userid");
				StorageComponentImpl proxy = SystemDictionary.getSCProxy();
				questions = proxy.getQuestionnaire(this.currentquestionnaireid, syslocale, userid);
				printQuestionsToTranslate(questions.getQuestion().toArray(
						new QuestionnaireQuestion[questions.getQuestion().size()]), "0");
				SystemDictionary.webguiLog("TRACE", "TRANSLATED QUESTIONNAIRE: " + questions.getTitle());
				((Textbox) getFellow("trans_title")).setValue(questions.getTitle());
				SystemDictionary.webguiLog("TRACE", "TRANSLATED QUESTIONNAIRE SIZE: " + this.localeqlist.size());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}


	/**
	 * Method to save an already translated Questionnaire
	 * 
	 * @throws SuspendNotAllowedException
	 * @throws ComponentNotFoundException
	 * @throws InterruptedException
	 */
	public void saveTranslation() throws SuspendNotAllowedException,
			ComponentNotFoundException, InterruptedException
	{
		Listitem localeitem = ((Listbox) getFellow("trans_locale")).getSelectedItem();
		String locale = (String) ((Listbox) getFellow("trans_locale")).getSelectedItem().getValue();
		if (locale.equals("none"))
		{
			((Window) getFellow("trans_alert")).doModal();
		}
		else
		{
			String version = (String) ((Textbox) getFellow("trans_version")).getValue();
			String ttitle = (String) ((Textbox) getFellow("trans_title")).getValue();

			Rows allrows = (Rows) getFellow("rows_questions");
			Object[] listrows = allrows.getChildren().toArray();
			// Pushing translations onto questions
			for (int i = 3; i < listrows.length; i++)
			{
				Row currentrow = (Row) listrows[i];
				Vbox rowdata = (Vbox) currentrow.getChildren().toArray()[1];
				Object[] vboxdata = rowdata.getChildren().toArray();
				RelatedQuestion currentq = this.questionlist.get(i - 3);
				Hbox hboxdata = ((Hbox) vboxdata[0]);
				String transquestion = ((Textbox) hboxdata.getChildren().toArray()[1]).getValue();
				SystemDictionary.webguiLog("DEBUG", "Question before: " + currentq.getQuestion().getTitle());
				currentq.getQuestion().setTitle(transquestion);
				SystemDictionary.webguiLog("DEBUG", "Question after: " + currentq.getQuestion().getTitle());
				QuestionnaireQuestionAnswer[] currentans = currentq.getQuestion().getAnswers().getAnswer().toArray(
						new QuestionnaireQuestionAnswer[currentq.getQuestion().getAnswers().getAnswer().size()]);
				for (int k = 1; k < vboxdata.length; k++)
				{
					Hbox currenthbox = (Hbox) vboxdata[k];
					// TODO _value == description??
					SystemDictionary.webguiLog("INFO", "Answer before: " + currentans[k - 1].getDescription());
					currentans[k - 1].setDescription(((Textbox) currenthbox.getChildren().toArray()[1]).getValue());
					SystemDictionary.webguiLog("INFO", "Answer after: " + currentans[k - 1].getDescription());
				}

				List<QuestionnaireQuestionAnswer> l = Arrays.asList(currentans);
				currentq.getQuestion().getAnswers().setAnswer(l);
				
				this.questionlist.set(i - 3, currentq);
			}

			// Start rebuilding the questionnaire structure
			ArrayList<QuestionnaireQuestion> rootQuestions = new ArrayList<QuestionnaireQuestion>();
			for (int ii = 0; ii < questionlist.size(); ii++)
			{
				if (questionlist.get(ii).getParent() == "0")
				{
					rootQuestions.add(questionlist.get(ii).getQuestion());
				}
				else
				{
					for (int k = 0; k < questionlist.size(); k++)
					{
						if (questionlist.get(k).getId().equals(questionlist.get(ii).getParent()))
						{
							QuestionnaireQuestion[] qqqa = null;
							if (questionlist.get(k).getQuestion().getQuestions() == null
									|| questionlist.get(k).getQuestion().getQuestions().getQuestion() == null
									|| questionlist.get(k).getQuestion().getQuestions().getQuestion().size() == 0)
							{
								qqqa = new QuestionnaireQuestion[1];
							}
							else
							{
								qqqa = new QuestionnaireQuestion[questionlist.get(k).getQuestion().getQuestions().getQuestion().size() + 1];
								for (int j = 0; j < qqqa.length - 1; j++)
								{
									qqqa[j] = questionlist.get(k).getQuestion().getQuestions().getQuestion().get(j);
								}
							}
							qqqa[qqqa.length - 1] = questionlist.get(ii).getQuestion();
							questionlist.get(k).getQuestion().setQuestions(new QuestionnaireQuestionList(Arrays.asList(qqqa)));
						}
					}
				}
			}

			// End of rebuilding the questionnaire structure
			QuestionnaireQuestion[] qlist = new QuestionnaireQuestion[rootQuestions.size()];
			for (int l = 0; l < rootQuestions.size(); l++)
			{
				qlist[l] = rootQuestions.get(l);
			}

			StorageComponentImpl proxy = SystemDictionary.getSCProxy();
			Questionnaire transq = new Questionnaire(qlist, Double.parseDouble(version), this.currentquestionnaireid, ttitle);
			try
			{
				String userid = (String) Sessions.getCurrent().getAttribute("userid");
				SystemDictionary.webguiLog("DEBUG", "UPDATED LOCALE: " + localeitem.getLabel());
				OperationResult result = proxy.updateQuestionnaire(transq, new SystemParameter(localeitem.getLabel(), ""), userid);
				SystemDictionary.webguiLog("DEBUG", "Translate Questionnaire: " + result.getCode() + ":" + result.getDescription());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				Executions.getCurrent().sendRedirect("/qm/index.zul");
			}
		}
	}


	/**
	 * Recursive function to show all the questions on the list, including the
	 * inner ones
	 * 
	 * @param qs QuestionnareQuestion array to show
	 * @param parentq String setting parent (mandatory for recursive calls)
	 */
	protected void printQuestions(QuestionnaireQuestion[] qs, String parentq)
	{
		SystemDictionary.webguiLog("TRACE", "Questionnaire lenght: " + qs.length);
		for (int i = 0; i < qs.length; i++)
		{
			this.addQuestion(qs[i], parentq);
			if (qs[i].getQuestions() != null&& qs[i].getQuestions().getQuestion() != null
					&& qs[i].getQuestions().getQuestion().size() > 0)
			{
				printQuestions(qs[i].getQuestions().getQuestion().toArray(
						new QuestionnaireQuestion[qs[i].getQuestions().getQuestion().size()]), qs[i].getId());
			}
		}
	}


	/**
	 * 
	 * @param qs
	 * @param parentq
	 */
	protected void printQuestionsToTranslate(QuestionnaireQuestion[] qs, String parentq)
	{
		for (int i = 0; i < qs.length; i++)
		{
			this.addQuestionToTranslate(qs[i], parentq);
			if (qs[i].getQuestions() != null && qs[i].getQuestions().getQuestion() != null
					&& qs[i].getQuestions().getQuestion().size() > 0)
			{
				printQuestionsToTranslate(qs[i].getQuestions().getQuestion().toArray(
						new QuestionnaireQuestion[qs[i].getQuestions().getQuestion().size()]), qs[i].getId());
			}
		}
	}


	/**
	 * Function called by question windows to append a new question to the
	 * questionnaire
	 * 
	 * @param question to be added
	 */
	public void addQuestion(QuestionnaireQuestion question, String parent)
	{
		this.questionlist.add(new RelatedQuestion(parent, question));
		Rows rows = (Rows) getFellow("rows_questions");
		rows.appendChild(this.createQuestionRow(question, parent));
	}


	/**
	 * 
	 * @param question
	 * @param parent
	 */
	protected void addQuestionToTranslate(QuestionnaireQuestion question, String parent)
	{
		this.localeqlist.add(new RelatedQuestion(parent, question));
		Row currentrow = (Row) getFellow(question.getId() + "-rowqstn");
		Vbox currentvbox = (Vbox) currentrow.getChildren().get(1);
		Hbox titlehbox = (Hbox) currentvbox.getChildren().get(0);
		Textbox translation = (Textbox) titlehbox.getChildren().get(1);
		if (question.getTitle() != null && !question.getTitle().equals("null"))
		{
			translation.setValue(question.getTitle());
		}
		else
		{
			translation.setValue("");
		}
		if (!question.getType().equals(SystemDictionary.QUESTION_TYPE_FREE_TEXT))
		{
			QuestionnaireQuestionAnswer[] transans = question.getAnswers().getAnswer().toArray(
					new QuestionnaireQuestionAnswer[question.getAnswers().getAnswer().size()]);
			List<QuestionnaireQuestionAnswer> anslist = Arrays.asList(transans);
			Collections.sort(anslist, new QuestionAnswerSort());
			for (int i = 1; i <= anslist.size(); i++)
			{
				Hbox anshbox = (Hbox) currentvbox.getChildren().get(i);
				Textbox anstranslation = (Textbox) anshbox.getChildren().get(1);
				if (anslist.get(i - 1) != null)
				{
					anstranslation.setValue(anslist.get(i - 1).getDescription());
				}
				else
				{
					anstranslation.setValue("");
				}
			}
		}
	}


	/**
	 * Function to insert a new row on the questions grid. Do not call it
	 * directly, it's call by addQuestion
	 * 
	 * @param question QuestionnaireQuestion to be inserted
	 * @return Row UI component
	 */
	private Row createQuestionRow(QuestionnaireQuestion question, String parent)
	{
		Row row = new Row();
		row.setId(question.getId() + "-rowqstn");

		Vbox vbox = new Vbox();
		Label lab1 = !question.getType().equals(SystemDictionary.QUESTION_TYPE_FREE_TEXT) ? new Label(
				"Question") : new Label("Free text Question");
		vbox.appendChild(lab1);

		if (!question.getType().equals(SystemDictionary.QUESTION_TYPE_FREE_TEXT))
		{
			Label lab2 = new Label("Answers");
			vbox.appendChild(lab2);
		}

		Vbox vbox2 = new Vbox();
		Hbox hboxtitle = new Hbox();
		Textbox qtitle = new Textbox(question.getTitle());
		qtitle.setReadonly(true);
		qtitle.setWidth(fieldWidth);
		Textbox qtitletrans = new Textbox();
		qtitletrans.setWidth(fieldWidth);
		hboxtitle.appendChild(qtitle);
		hboxtitle.appendChild(qtitletrans);
		vbox2.appendChild(hboxtitle);
		SystemDictionary.webguiLog("DEBUG", "Question type: "+ question.getType() + ":"+ SystemDictionary.QUESTION_TYPE_FREE_TEXT);
		
		if (!question.getType().equals(SystemDictionary.QUESTION_TYPE_FREE_TEXT))
		{
			QuestionnaireQuestionAnswer[] answers = question.getAnswers().getAnswer().toArray(
					new QuestionnaireQuestionAnswer[question.getAnswers().getAnswer().size()]);
			List<QuestionnaireQuestionAnswer> anslist = Arrays.asList(answers);
			Collections.sort(anslist, new QuestionAnswerSort());
			for (int i = 0; i < anslist.size(); i++)
			{
				Hbox hboxans = new Hbox();
				Textbox qans = new Textbox(anslist.get(i).getDescription());
				qans.setReadonly(true);
				qans.setWidth(fieldWidth);
				Textbox qanstrans = new Textbox();
				qanstrans.setWidth(fieldWidth);
				hboxans.appendChild(qans);
				hboxans.appendChild(qanstrans);
				vbox2.appendChild(hboxans);
			}
		}
		row.appendChild(vbox);
		row.appendChild(vbox2);
		return row;
	}


	/**
	 * Inner class to help the related questions management.
	 * 
	 */
	private class RelatedQuestion
	{


		private String parent;
		private String id;
		private QuestionnaireQuestion question;


		public RelatedQuestion(String p, QuestionnaireQuestion q)
		{
			parent = p;
			question = q;
			id = q.getId();
		}


		public String getParent()
		{
			return parent;
		}


		public String getId()
		{
			return id;
		}


		public QuestionnaireQuestion getQuestion()
		{
			return question;
		}

		
	}
	
	
}
