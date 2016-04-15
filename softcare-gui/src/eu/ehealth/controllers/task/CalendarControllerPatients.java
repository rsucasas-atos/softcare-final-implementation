package eu.ehealth.controllers.task;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Window;
import org.zkoss.zul.Datebox;
import eu.ehealth.SystemDictionary;
import eu.ehealth.controllers.calendar.AladdinCalendarEvent;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.Carer;
import eu.ehealth.ws_client.xsd.Clinician;
import eu.ehealth.ws_client.xsd.Measurement;
import eu.ehealth.ws_client.xsd.OperationResult;
import eu.ehealth.ws_client.xsd.Patient;
import eu.ehealth.ws_client.xsd.Questionnaire;
import eu.ehealth.ws_client.xsd.QuestionnaireAnswer;
import eu.ehealth.ws_client.xsd.QuestionnaireAnswers;
import eu.ehealth.ws_client.xsd.QuestionnaireQuestion;
import eu.ehealth.ws_client.xsd.SearchCriteria;
import eu.ehealth.ws_client.xsd.SystemParameter;
import eu.ehealth.ws_client.xsd.User;


/**
 * 
 * @author
 *
 */
public class CalendarControllerPatients extends GenericForwardComposer
{


	private static final long serialVersionUID = 992086726834807326L;
	public Window bookEventWin;
	public Calendars cal;
	
	
	/**
	 * 
	 * @return
	 */
	private Patient getCurrentPatient() {
		try 
		{
			// userId
			String userid = (String) Sessions.getCurrent().getAttribute("userid");
			// current executor
			String currentexecutor = getCurrentExecutor();

			Patient patient = SystemDictionary.getSCProxy().getPatient(currentexecutor, userid);
			
			SystemDictionary.webguiLog("DEBUG", "-> patient: " + patient.toString());
			
			return patient;
		}
		catch (Exception ex)
		{
			SystemDictionary.logException(ex);
			return null;
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	private String getCurrentExecutor() {
		try 
		{
			Toolbar toolbar = (Toolbar) self.getFellow("toolbar");
			String currentexecutor = ((Label) toolbar.getFellow("exechelp")).getValue();
			
			SystemDictionary.webguiLog("DEBUG", "-> current executor: " + currentexecutor);
			
			return currentexecutor;
		}
		catch (Exception ex)
		{
			SystemDictionary.logException(ex);
			return null;
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	private Clinician getCurrentClinician() {
		try 
		{
			// userId
			String userid = (String) Sessions.getCurrent().getAttribute("userid");
			// clinician id
			String responsibleClinicianID = (String) session.getAttribute("ResponsibleClinicianID");
			
			Clinician assg = SystemDictionary.getSCProxy().getClinician(responsibleClinicianID, userid);
			
			SystemDictionary.webguiLog("DEBUG", "-> clinician: " + assg.toString());
			
			return assg;
		}
		catch (Exception ex)
		{
			SystemDictionary.logException(ex);
			return null;
		}
	}


	/**
	 * 
	 * @param event
	 * @throws InterruptedException
	 * @throws RemoteException
	 */
	public void onEventCreate(CalendarsEvent event) 
	{
		try {
			bookEventWin = (Window) Executions.createComponents("../patients/bookEvent.zul", self.getParent(), null);
			bookEventWin.setAttribute("calendars", cal);
			bookEventWin.setAttribute("calevent", event);
			bookEventWin.getFellow("qsrow").setVisible(true);
			
			Date setting = event.getBeginDate();
			setting.setTime(setting.getTime() + 43200000);
			((Datebox) bookEventWin.getFellow("datetask")).setValue(setting);
			((Timebox) bookEventWin.getFellow("timetask")).setValue(setting);
			((Button) bookEventWin.getFellow("savebutton")).setVisible(true);
			((Listbox) bookEventWin.getFellow("tasktypesel")).setSelectedIndex(0);
			String userid = (String) Sessions.getCurrent().getAttribute("userid");
			((Textbox) bookEventWin.getFellow("userid")).setValue(userid);
			
			String currentexecutor = getCurrentExecutor();
			Patient patient = getCurrentPatient();
			Carer carer = patient.getPatientCarer();
			
			StorageComponentImpl proxy = SystemDictionary.getSCProxy();
			OperationResult opres = proxy.getUserIdByPatientId(currentexecutor, userid);
			OperationResult realexecutor = proxy.getUserIdByCarerId(carer.getID(), userid);
			
			((Textbox) bookEventWin.getFellow("objid")).setValue(realexecutor.getCode());	// id aladdinuser ... carer
			((Textbox) bookEventWin.getFellow("addressedid")).setValue(opres.getCode());	// id aladdinuser ... patient
			((Textbox) bookEventWin.getFellow("objstr")).setValue(carer.toString());
			((Textbox) bookEventWin.getFellow("addressedstr")).setValue(patient.toString());
			((Textbox) bookEventWin.getFellow("patid_value")).setValue(patient.getID());
			
			String responsibleClinicianID = (String) session.getAttribute("ResponsibleClinicianID");
			
			Clinician assg = proxy.getClinician(responsibleClinicianID, userid);
			
			((Textbox) bookEventWin.getFellow("userstr")).setValue(assg.toString());
			try {
				((Listbox) bookEventWin.getFellow("questnamefield")).setSelectedIndex(0);
			} catch (Exception ex) {}
			
			bookEventWin.getFellow("massivequestionrow").setVisible(true);
			bookEventWin.getFellow("massivecheckrow").setVisible(true);
			bookEventWin.setTitle("New Task");
			bookEventWin.setVisible(true);
			bookEventWin.doModal();
		}
		catch (Exception ex) {
			SystemDictionary.webguiLog("ERROR", ex.getMessage());
			ex.printStackTrace();
			try
			{
				throw ex;
			}
			catch (Exception e) { }
		}
	}


	/**
	 * 
	 * @param questions
	 * @return
	 */
	private List<QuestionnaireQuestion> toPlainList(List<QuestionnaireQuestion> questions)
	{
		List<QuestionnaireQuestion> res = new ArrayList<QuestionnaireQuestion>();

		if (questions != null && questions.size() > 0) 
		{
			for (QuestionnaireQuestion question : questions)
			{
				res.add(question);
				res.addAll(toPlainList(question.getQuestions().getQuestion()));
			}
		}
		
		return res;
	}


	/**
	 * 
	 * @param event
	 * @throws InterruptedException
	 * @throws RemoteException
	 */
	public void onEventEdit(CalendarsEvent event) throws InterruptedException, RemoteException
	{
		try
		{
			Session ses = Sessions.getCurrent();
			String userid = (String) ses.getAttribute("userid");
			bookEventWin = (Window) Executions.createComponents("../patients/bookEvent.zul", self.getParent(), null);
			bookEventWin.setAttribute("calendars", cal);
			bookEventWin.setAttribute("calevent", event);
			Date setting = event.getCalendarEvent().getBeginDate();
			AladdinCalendarEvent scevent = (AladdinCalendarEvent) event.getCalendarEvent();
			bookEventWin.getFellow("cancelbutton").setVisible(true);
			((Datebox) bookEventWin.getFellow("datetask")).setValue(setting);
			((Datebox) bookEventWin.getFellow("datetask")).setReadonly(true);
			((Datebox) bookEventWin.getFellow("datetask")).setButtonVisible(false);
			((Timebox) bookEventWin.getFellow("timetask")).setValue(setting);
			((Timebox) bookEventWin.getFellow("timetask")).setReadonly(true);
			((Timebox) bookEventWin.getFellow("timetask")).setButtonVisible(false);
			((Textbox) bookEventWin.getFellow("taskidfield")).setValue((String) scevent.getParams().get("task"));
			((Textbox) bookEventWin.getFellow("taskstatusfield"))
					.setValue(SystemDictionary.getTaskStatusLabel((String) scevent.getParams().get("status")));
			bookEventWin.getFellow("rowtaskstatus").setVisible(true);
			
			StorageComponentImpl proxy = SystemDictionary.getSCProxy();
			if (scevent.getParams().get("status").equals(SystemDictionary.TASK_STATUS_COMPLETED))
			{
				bookEventWin.getFellow("cancelbutton").setVisible(false);
				String tasktype = (String) scevent.getParams().get("type");
				
				if (tasktype.equals(SystemDictionary.TASK_TYPE_CARERQS) || tasktype.equals(SystemDictionary.TASK_TYPE_PATIENTQS))
				{
					// TODO retrieve Questionnaire answers and show in the task window
					Questionnaire q = (Questionnaire) scevent.getParams().get("questionnaire");
					QuestionnaireAnswers qa = proxy.getQuestionnaireAnswersByTask((String) scevent.getParams().get("task"), userid);
					SystemDictionary.webguiLog("INFO", "task id: " + (String) scevent.getParams().get("task"));

					ArrayList<QuestionnaireAnswer> qalist = new ArrayList<QuestionnaireAnswer>();
					if (qa.getAnswer() != null) 
					{
						for (int i = 0; i < qa.getAnswer().size(); i++)
						{
							qalist.add(i, qa.getAnswer().get(i));
						}
					}
					String responses = provideQuestionnaireResponse(toPlainList(q.getQuestion()), qalist);
					SystemDictionary.webguiLog("DEBUG", "RESPONSES: " + responses);
					bookEventWin.getFellow("qsanswersrow").setVisible(true);
					((Label) bookEventWin.getFellow("qsanswersfield")).setValue(responses);
				}
				else if (tasktype.equals(SystemDictionary.TASK_TYPE_BLOODPRESSURE_MEASUREMENT)
						|| tasktype.equals(SystemDictionary.TASK_TYPE_WEIGHT_MEASUREMENT)
						|| tasktype.equals(SystemDictionary.TASK_TYPE_ACTMONITOR))
				{
					String resultfieldvalue = "";
					SearchCriteria searchc = new SearchCriteria(
							"task",
							new SystemParameter(SystemDictionary.COMPARE_EQ, ""),
							(String) scevent.getParams().get("task"), "");

					Measurement[] results = proxy.getMeasurementArr(new SearchCriteria[] { searchc }, userid);
					if (results.length <= 0)
					{
						resultfieldvalue = "No measurement could be retrieved";
					}
					else
					{
						resultfieldvalue = "Use 'Monitor Measurements' button for viewing the measurements";
					}
					bookEventWin.getFellow("mresultrow").setVisible(true);
					((Label) bookEventWin.getFellow("mresultrowfield")).setValue(resultfieldvalue);
				}

			}
			
			((Textbox) bookEventWin.getFellow("addressedid")).setValue((String) scevent.getParams().get("objid"));
			
			Patient patient = getCurrentPatient();
			((Textbox) bookEventWin.getFellow("addressedstr")).setValue(patient.toString());
			((Textbox) bookEventWin.getFellow("patid_value")).setValue(patient.getID());
			User object = proxy.getUser((String) scevent.getParams().get("exec"));
			
			((Textbox) bookEventWin.getFellow("objid")).setValue(object.getID());
			((Textbox) bookEventWin.getFellow("objstr")).setValue(object.getUsername()); 

			Clinician assg = getCurrentClinician(); 
			((Textbox) bookEventWin.getFellow("userid")).setValue((String) scevent.getParams().get("assign"));
			((Textbox) bookEventWin.getFellow("userstr")).setValue(assg.toString());
			
			String contentTypeTxt = scevent.getContent();
			if (contentTypeTxt.endsWith("..")) 
			{
				try {
					contentTypeTxt = SystemDictionary.getTaskTypeLabel((String) scevent.getParams().get("type"));
				}
				catch (Exception ex) {
					SystemDictionary.logException(ex);
				}
			}
			
			((Textbox) bookEventWin.getFellow("tasktypetext")).setValue(contentTypeTxt);
			bookEventWin.getFellow("tasktypetext").setVisible(true);

			int tasktype2 = Integer.parseInt((String) scevent.getParams().get("type"));
			this.showCustomFields(tasktype2);
			bookEventWin.getFellow("tasktypesel").setVisible(false);
			
			// urlrowEXERCISES
			List items1 = ((Listbox) bookEventWin.getFellow("urlfieldEXERCISES")).getItems();
			Iterator it1 = items1.iterator();
			while (it1.hasNext())
			{
				Listitem currentitem = (Listitem) it1.next();
				if (currentitem.getValue().equals((String) scevent.getParams().get("url")))
				{
					((Listbox) bookEventWin.getFellow("urlfieldEXERCISES")).setSelectedItem(currentitem);
					break;
				}
			}
			
			// urlfieldCOGNGAMES
			List items2 = ((Listbox) bookEventWin.getFellow("urlfieldCOGNGAMES")).getItems();
			Iterator it2 = items2.iterator();
			while (it2.hasNext())
			{
				Listitem currentitem = (Listitem) it2.next();
				if (currentitem.getValue().equals((String) scevent.getParams().get("url")))
				{
					((Listbox) bookEventWin.getFellow("urlfieldCOGNGAMES")).setSelectedItem(currentitem);
					break;
				}
			}
			
			((Listbox) bookEventWin.getFellow("urlfieldEXERCISES")).setDisabled(true);
			((Listbox) bookEventWin.getFellow("urlfieldCOGNGAMES")).setDisabled(true);
			
			if (scevent.getParams().get("questionnaire") != null)
			{
				List items = ((Listbox) bookEventWin.getFellow("questnamefield")).getItems();
				Iterator it = items.iterator();
				while (it.hasNext())
				{
					Listitem currentitem = (Listitem) it.next();
					if (currentitem.getValue().equals(((Questionnaire) scevent.getParams().get("questionnaire")).getID()))
					{
						((Listbox) bookEventWin.getFellow("questnamefield")).setSelectedItem(currentitem);
						break;
					}
				}
			}
			
			((Listbox) bookEventWin.getFellow("questnamefield")).setDisabled(true);
			((Textbox) bookEventWin.getFellow("textfield")).setValue((String) scevent.getParams().get("text"));

			bookEventWin.setTitle("View Task");
			bookEventWin.setVisible(true);
			bookEventWin.doModal();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Executions.sendRedirect("");
		}
	}


	/**
	 * 
	 * @param questions
	 * @param answers
	 * @return
	 * @throws RemoteException
	 */
	private String provideQuestionnaireResponse(List<QuestionnaireQuestion> questions,
												ArrayList<QuestionnaireAnswer> answers) throws RemoteException
	{
		String ret = "";
		for (int i = 0; i < answers.size(); i++)
		{
			QuestionnaireAnswer currentAnswer = answers.get(i);
			QuestionnaireQuestion currentQuestion = null;
			for (QuestionnaireQuestion question : questions)
			{
				if (question.getId().equals(currentAnswer.getQuestionID()))
				{
					currentQuestion = question;
					break;
				}
			}
			if (currentQuestion != null)
			{
				StorageComponentImpl proxy = SystemDictionary.getSCProxy();
				String valuestring = proxy
						.getQuestionnaireAnswerValue(currentQuestion.getId(), currentAnswer
								.getValue(), SystemDictionary.getLocale());
				if (valuestring == null)
				{
					valuestring = currentAnswer.getValue();
				}
				if (valuestring != null && !valuestring.isEmpty())
				{
					ret += currentQuestion.getTitle() + " " + valuestring + "\n";
				}
			}
		}
		return ret;
	}


	/**
	 * 
	 * @param tasktype
	 */
	private void showCustomFields(int tasktype)
	{
		boolean urlrowCOGNGAMES = false;
		boolean urlrowEXERCISES = false;
		boolean qsrow = false;
		boolean txtrow = false;
		
		switch (tasktype)
		{
			case SystemDictionary.TASK_TYPE_COGGAME_INT:
				urlrowCOGNGAMES = true;
				break;
				
			case SystemDictionary.TASK_TYPE_CARERQS_INT:
			case SystemDictionary.TASK_TYPE_PATIENTQS_INT:
				qsrow = true;
				break;
				
			case SystemDictionary.TASK_TYPE_EXERCISE_INT:
				txtrow = true;
				urlrowEXERCISES = true;
				break;
				
			case SystemDictionary.TASK_TYPE_TXT_INT:
				txtrow = true;
				break;
				
			default:
				break;
		}
		
		bookEventWin.getFellow("urlrowCOGNGAMES").setVisible(urlrowCOGNGAMES);
		bookEventWin.getFellow("urlrowEXERCISES").setVisible(urlrowEXERCISES);
		bookEventWin.getFellow("qsrow").setVisible(qsrow);
		bookEventWin.getFellow("textrow").setVisible(txtrow);
		((Textbox) bookEventWin.getFellow("textfield")).setReadonly(true);
	}
	
	
}
