package eu.ehealth.qm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import eu.ehealth.SystemDictionary;
import eu.ehealth.ws_client.xsd.QuestionnaireQuestion;
import eu.ehealth.ws_client.xsd.QuestionnaireQuestionAnswer;
import eu.ehealth.ws_client.xsd.QuestionnaireQuestionAnswerList;
import eu.ehealth.ws_client.xsd.QuestionnaireQuestionList;


/**
 * 
 * @author
 *
 */
public class QuestionWindow extends Window
{


	private static final long serialVersionUID = 4976983401212316183L;
	private QuestionnaireFormWindow pform = null;
	private String ID = null;
	private int globalID;
	@SuppressWarnings("unused")
	private int position;
	private String type = null;
	private String parent = null;
	private ArrayList<QuestionnaireQuestionAnswer> answers = new ArrayList<QuestionnaireQuestionAnswer>();


	/**
	 * Constructor with mandatory reference to "parent" Window. This reference
	 * will be used to send information about the question
	 * 
	 * @param QUestionnaireFormWindow pform parent Window
	 * @param String ID question id
	 * @param String type question type
	 */
	public QuestionWindow(QuestionnaireFormWindow pform, String ID,
			String parent)
	{
		this.pform = pform;
		this.ID = ID;
		this.globalID = 0;
		this.parent = parent;
		this.type = SystemDictionary.QUESTION_TYPE_ONE_ANSWER;
		this.setTitle(Labels.getLabel("qm.ans.title.new"));
		this.setClosable(true);
		this.setBorder("normal");
		this.setWidth("650px");
		this.addMainQuestionGrid();
		this.getFellow("freeanswrow").setVisible(false);
	}


	/**
	 * 
	 * @param pform
	 * @param question
	 * @param parent
	 */
	public QuestionWindow(QuestionnaireFormWindow pform, QuestionnaireQuestion question, String parent)
	{
		this.pform = pform;
		this.ID = question.getId();
		this.globalID = question.getGlobalID();
		this.parent = parent;
		this.type = question.getType();
		this.position = question.getPosition();

		SystemDictionary.webguiLog("DEBUG", "QUESTION TYPE: " + this.type + ":" + SystemDictionary.QUESTION_TYPE_FREE_TEXT);
		if (!this.type.equals(SystemDictionary.QUESTION_TYPE_FREE_TEXT))
		{
			QuestionnaireQuestionAnswer[] qans = question.getAnswers().getAnswer().toArray(
						new QuestionnaireQuestionAnswer[question.getAnswers().getAnswer().size()]);

			List<QuestionnaireQuestionAnswer> anslist = Arrays.asList(qans);
			Collections.sort(anslist, new QuestionAnswerSort());
			for (int i = 0; i < anslist.size(); i++)
			{
				this.answers.add(anslist.get(i));
			}
		}

		this.setTitle(Labels.getLabel("qm.ans.title.update"));
		this.setClosable(true);
		this.setBorder("normal");
		this.setWidth("650px");
		this.addMainQuestionGrid();
		this.setMainGridFields(question);
		if (!this.type.equals(SystemDictionary.QUESTION_TYPE_FREE_TEXT))
		{
			this.setAnswersRows();
		}
		this.changeQuestionType();
	}


	/**
	 * 
	 */
	public void saveQuestion()
	{
		SystemDictionary.webguiLog("INFO", "QUESTION TYPE = " + this.type);
		if ((this.answers == null || this.answers.size() == 0)
				&& !this.type.equals(SystemDictionary.QUESTION_TYPE_FREE_TEXT))
		{
			showErrorMessage(true);
		}
		else
		{
			QuestionnaireQuestion q = new QuestionnaireQuestion();
			q.setType(this.type);
			q.setId(this.ID);

			String title = ((Textbox) getFellow("question_text")).getValue();
			String globalid = ((Textbox) getFellow("question_idg")).getValue();
			int position = ((Intbox) getFellow("question_position")).getValue();
			if (getFellow("question_condrow").isVisible())
			{
				Integer condition = ((Intbox) getFellow("question_cond")).getValue();
				q.setCondition(condition.shortValue());
			}
			q.setTitle(title);
			q.setPosition(position);
			q.setGlobalID(Integer.parseInt(globalid));
			QuestionnaireQuestionList qqqlist = new QuestionnaireQuestionList();
			q.setQuestions(qqqlist);
			
			QuestionnaireQuestionAnswerList lQuestionnaireQuestionAnswerList = new QuestionnaireQuestionAnswerList();

			if (this.type.equals(SystemDictionary.QUESTION_TYPE_ONE_ANSWER) || this.type.equals(SystemDictionary.QUESTION_TYPE_MANY_ANSWERS))
			{
				QuestionnaireQuestionAnswer[] answersvec = new QuestionnaireQuestionAnswer[this.answers.size()];
				for (int i = 0; i < this.answers.size(); i++)
				{
					answersvec[i] = this.answers.get(i);
				}
				lQuestionnaireQuestionAnswerList.setAnswer(Arrays.asList(answersvec));
			}

			q.setAnswers(lQuestionnaireQuestionAnswerList);
			
			this.pform.addQuestion(q, parent);
			this.setVisible(false);
			this.getParent().removeChild(this);
		}
	}


	/**
	 * This method is called when users submits a new answer. Submitted answer
	 * is stored on the ArrayList and appended to the main grid.
	 * 
	 */
	public void apendAnswer()
	{
		Intbox positionbox = (Intbox) getFellow("ans_pos");
		int pos = positionbox.getValue();

		Textbox valuebox = (Textbox) getFellow("ans_val");
		String val = valuebox.getValue();

		Textbox txtbox = (Textbox) getFellow("ans_text");
		String text = txtbox.getValue();

		Row row = new Row();

		Label lab0 = new Label();
		lab0.setValue(pos + "");
		row.appendChild(lab0);

		Label lab1 = new Label();
		lab1.setValue(val);
		row.appendChild(lab1);

		Label lab2 = new Label();
		lab2.setValue(text);
		row.appendChild(lab2);

		Button btn = new Button();
		btn.setLabel("-");
		btn.addEventListener("onClick", new RemoveAnswerListener(row, text));
		row.appendChild(btn);

		Rows rows = (Rows) getFellow("rows");
		Row rowRef = (Row) getFellow("rowform");
		rows.removeChild(rowRef);
		rows.appendChild(row);
		rows.appendChild(rowRef);

		valuebox.setValue("");
		txtbox.setValue("");

		QuestionnaireQuestionAnswer qqanswer = new QuestionnaireQuestionAnswer();
		qqanswer.setDescription(text);
		qqanswer.setValue(new Short(val));
		qqanswer.setPosition(pos);

		this.answers.add(qqanswer);
	}


	/**
	 * This method removes an answer from the grid and the ArrayList
	 * 
	 * @param comp Component to remove. It is a Row
	 */
	public void removeRow(Component comp, String text)
	{
		Rows rows = (Rows) getFellow("rows");
		rows.removeChild(comp);
		SystemDictionary.webguiLog("INFO", "SIZE before: " + this.answers.size());
		this.removeAnswer(text);
		SystemDictionary.webguiLog("INFO", "SIZE after: " + this.answers.size());
	}


	/**
	 * Helper method to remove an answer from the ArrayList
	 * 
	 * @param ID String which can be parsed to an UnsignedByte
	 */
	private void removeAnswer(String text)
	{
		for (int i = 0; i < this.answers.size(); i++)
		{
			QuestionnaireQuestionAnswer elem = this.answers.get(i);
			SystemDictionary.webguiLog("INFO", "TEXT: " + text + " VS. ANSWER VAL: " + elem.getDescription());
			if (elem.getDescription().equals(text))
			{
				this.answers.remove(i);
				break;
			}
		}
	}


	/**
	 * 
	 * @param show
	 */
	public void showErrorMessage(boolean show)
	{
		getFellow("error-no-answers").setVisible(show);
	}


	/**
	 * This method builds up a one answer form
	 * 
	 */
	private void addMainQuestionGrid()
	{
		Grid grid = new Grid();

		Columns cols = new Columns();
		Column col1 = new Column();
		col1.setWidth("150px");
		col1.setAlign("right");
		col1.setValign("top");
		Column col2 = new Column();
		cols.appendChild(col1);
		cols.appendChild(col2);
		grid.appendChild(cols);
		Rows rows = new Rows();
		rows.setId("answersrows");

		Row row000 = new Row();
		row000.setId("error-no-answers");
		row000.setVisible(false);
		Label lab000 = new Label("");
		row000.appendChild(lab000);

		Hbox errmsg = new Hbox();
		Label lab001 = new Label(
				"You must enter some answers to save the question ");
		Label lab002 = new Label("close");
		lab002.setSclass("link");
		lab002.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				showErrorMessage(false);
			}
		});
		errmsg.appendChild(lab001);
		errmsg.appendChild(lab002);
		row000.appendChild(errmsg);
		rows.appendChild(row000);

		Row row00 = new Row();
		Label lab00 = new Label();
		lab00.setValue(Labels.getLabel("qm.ans.fields.type"));
		row00.appendChild(lab00);

		Listbox lbox00 = new Listbox();
		lbox00.setId("question_typesel");
		lbox00.setMold("select");
		lbox00.setRows(1);
		lbox00.appendItem("One answer", "1");
		lbox00.appendItem("Multiple answers", "2");
		lbox00.appendItem("Free text", "3");
		lbox00.setSelectedIndex(0);
		lbox00.addEventListener("onSelect", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				changeQuestionType();
			}
		});
		row00.appendChild(lbox00);
		rows.appendChild(row00);

		Row row0 = new Row();
		Label lab0 = new Label();
		lab0.setValue(Labels.getLabel("qm.ans.fields.id"));
		row0.appendChild(lab0);

		Textbox tbox0 = new Textbox();
		tbox0.setId("question_id");
		tbox0.setValue(this.ID);
		tbox0.setReadonly(true);
		row0.appendChild(tbox0);
		rows.appendChild(row0);

		Row row011 = new Row();
		Label lab011 = new Label();
		lab011.setValue("Position");
		row011.appendChild(lab011);

		Intbox tbox011 = new Intbox();
		tbox011.setId("question_position");
		tbox011.setConstraint("no empty");
		row011.appendChild(tbox011);
		rows.appendChild(row011);

		Row row01 = new Row();
		Label lab01 = new Label();
		lab01.setValue(Labels.getLabel("qm.ans.fields.id.global"));
		row01.appendChild(lab01);

		Textbox tbox01 = new Textbox();
		tbox01.setId("question_idg");
		tbox01.setValue("" + (this.globalID == 0 ? "" : this.globalID));
		row01.appendChild(tbox01);
		rows.appendChild(row01);

		Row row1 = new Row();
		Label lab1 = new Label();
		lab1.setValue(Labels.getLabel("qm.ans.fields.text"));
		row1.appendChild(lab1);

		Textbox tbox1 = new Textbox();
		tbox1.setId("question_text");
		tbox1.setWidth("350px");
		tbox1.setMultiline(true);
		tbox1.setRows(3);
		row1.appendChild(tbox1);
		rows.appendChild(row1);

		Row row02 = new Row();
		Label lab02 = new Label();
		lab02.setValue("Condition (numeric)");
		row02.appendChild(lab02);

		Intbox tbox02 = new Intbox();
		tbox02.setId("question_cond");
		row02.appendChild(tbox02);
		row02.setVisible(false);
		row02.setId("question_condrow");
		rows.appendChild(row02);

		grid.appendChild(rows);

		this.appendChild(grid);
		this.addFreeAnswerRow();
		this.addAnswersRow();
		Button btn = new Button();
		btn.setLabel(Labels.getLabel("qm.ans.fields.addans"));
		btn.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				saveQuestion();
			}
		});
		this.appendChild(btn);
	}


	/**
	 * 
	 * @param q
	 */
	private void setMainGridFields(QuestionnaireQuestion q)
	{
		((Textbox) getFellow("question_text")).setValue(q.getTitle());
		((Intbox) getFellow("question_position")).setValue(q.getPosition());
		if (q.getCondition() != null)
		{
			((Intbox) getFellow("question_cond")).setValue(new Integer(q.getCondition().toString()));
		}
		int selindex = 0;
		if (q.getType().equals(SystemDictionary.QUESTION_TYPE_FREE_TEXT))
		{
			selindex = 2;
		}
		else if (q.getType().equals(SystemDictionary.QUESTION_TYPE_MANY_ANSWERS))
		{
			selindex = 1;
		}
		else if (q.getType().equals(SystemDictionary.QUESTION_TYPE_ONE_ANSWER))
		{
			selindex = 0;
		}
		((Listbox) getFellow("question_typesel")).setSelectedIndex(selindex);
	}


	/**
	 * 
	 */
	private void addFreeAnswerRow()
	{
		Rows rows = (Rows) getFellow("answersrows");
		Row row2 = new Row();
		row2.setId("freeanswrow");

		Label lab2 = new Label();
		lab2.setValue(Labels.getLabel("qm.ans.fields.grid.answers"));
		row2.appendChild(lab2);

		Label lab3 = new Label();
		lab3.setValue(Labels.getLabel("qm.ans.fields.grid.free"));
		row2.appendChild(lab3);

		rows.appendChild(row2);
	}


	/**
	 * 
	 */
	private void addAnswersRow()
	{
		Rows rows = (Rows) getFellow("answersrows");
		Row row2 = new Row();
		row2.setId("multipleanswrow");

		Label lab2 = new Label();
		lab2.setValue(Labels.getLabel("qm.ans.fields.grid.answers"));
		row2.appendChild(lab2);

		Grid answ = new Grid();
		Columns columns2 = new Columns();

		Column cola1 = new Column();
		cola1.setLabel(Labels.getLabel("qm.ans.fields.grid.value"));
		Column cola2 = new Column();
		cola2.setLabel(Labels.getLabel("qm.ans.fields.grid.text"));
		Column cola3 = new Column();
		cola3.setWidth("45px");
		Column cola4 = new Column();
		cola4.setLabel("Position");

		columns2.appendChild(cola4);
		columns2.appendChild(cola1);
		columns2.appendChild(cola2);
		columns2.appendChild(cola3);
		answ.appendChild(columns2);

		Rows arows = new Rows();
		arows.setId("rows");
		Row rowa1 = new Row();
		rowa1.setId("rowform");
		Intbox tboxans0 = new Intbox();
		tboxans0.setId("ans_pos");
		tboxans0.setConstraint("no empty");
		Textbox tboxans1 = new Textbox();
		tboxans1.setId("ans_val");
		Textbox tboxans2 = new Textbox();
		tboxans2.setId("ans_text");
		Button butonans1 = new Button();
		butonans1.setLabel("+");
		butonans1.addEventListener("onClick", new EventListener() {
			public void onEvent(Event arg0) throws Exception
			{
				apendAnswer();

			}
		});

		rowa1.appendChild(tboxans0);
		rowa1.appendChild(tboxans1);
		rowa1.appendChild(tboxans2);
		rowa1.appendChild(butonans1);
		arows.appendChild(rowa1);

		answ.appendChild(arows);
		row2.appendChild(answ);

		rows.appendChild(row2);
	}


	/**
	 * 
	 */
	private void setAnswersRows()
	{
		for (int i = 0; i < this.answers.size(); i++)
		{
			QuestionnaireQuestionAnswer answer = this.answers.get(i);
			Row row = new Row();
			Label lab0 = new Label();
			lab0.setValue(answer.getPosition() + "");
			row.appendChild(lab0);

			Label lab1 = new Label();
			lab1.setValue(answer.getValue().toString());
			row.appendChild(lab1);

			Label lab2 = new Label();
			lab2.setValue(answer.getDescription());
			row.appendChild(lab2);

			Button btn = new Button();
			btn.setLabel("-");
			btn.addEventListener("onClick", new RemoveAnswerListener(row, answer.getDescription()));
			row.appendChild(btn);

			Rows rows = (Rows) getFellow("rows");
			Row rowRef = (Row) getFellow("rowform");
			rows.removeChild(rowRef);
			rows.appendChild(row);
			rows.appendChild(rowRef);
		}

	}


	/**
	 * 
	 */
	public void changeQuestionType()
	{
		int selected = ((Listbox) this.getFellow("question_typesel"))
				.getSelectedIndex();
		switch (selected)
		{
			case 0:
				this.type = SystemDictionary.QUESTION_TYPE_ONE_ANSWER;
				this.getFellow("freeanswrow").setVisible(false);
				this.getFellow("multipleanswrow").setVisible(true);
				break;
			case 1:
				this.type = SystemDictionary.QUESTION_TYPE_MANY_ANSWERS;
				this.getFellow("freeanswrow").setVisible(false);
				this.getFellow("multipleanswrow").setVisible(true);
				break;
			case 2:
				this.type = SystemDictionary.QUESTION_TYPE_FREE_TEXT;
				this.getFellow("freeanswrow").setVisible(true);
				this.getFellow("multipleanswrow").setVisible(false);
				break;
		}
	}


	/**
	 * 
	 * @author 
	 *
	 */
	private class RemoveAnswerListener implements EventListener
	{


		private Component comp;
		private String text;


		public RemoveAnswerListener(Component compR, String textR)
		{
			comp = compR;
			text = textR;
		}


		public void onEvent(Event arg0) throws Exception
		{
			removeRow(comp, text);
		}
		
		
	}
	

}
