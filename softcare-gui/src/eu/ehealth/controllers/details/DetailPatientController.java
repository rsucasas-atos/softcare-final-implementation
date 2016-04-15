package eu.ehealth.controllers.details;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.impl.SimpleCalendarModel;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import eu.ehealth.SystemDictionary;
import eu.ehealth.controllers.calendar.AladdinCalendarEvent;
import eu.ehealth.controllers.details.assessment.AssessmentPopupController;
import eu.ehealth.controllers.details.measurements.MeasurementPopupController;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.Carer;
import eu.ehealth.ws_client.xsd.Clinician;
import eu.ehealth.ws_client.xsd.Consulter;
import eu.ehealth.ws_client.xsd.GeneralPractitioner;
import eu.ehealth.ws_client.xsd.OperationResult;
import eu.ehealth.ws_client.xsd.PatientAssessment;
import eu.ehealth.ws_client.xsd.PersonData;
import eu.ehealth.ws_client.xsd.SocialWorker;
import eu.ehealth.ws_client.xsd.SocioDemographicData;
import eu.ehealth.ws_client.xsd.Task;


/**
 * 
 * @author 
 *
 */
public class DetailPatientController extends DetailSDController
{


	private static final long serialVersionUID = 6583102921034384286L;
	
	public AssessmentPopupController assessmentWindow;
	private Window removeMassivelyDialog;
	private Window weightMeasurementDialog;
	protected SimpleCalendarModel calmodel = null;
	protected Calendars calendars = null;
	protected SocialWorker currentsocialworker = null;
	protected Consulter currentconsulter = null;
	protected GeneralPractitioner currentgralprac = null;
	private int patientUserId = -1;


	/**
	 * 
	 */
	public DetailPatientController()
	{
		this.usertype = SystemDictionary.USERTYPE_PATIENT_INT;
	}


	/**
	 * 
	 */
	public void setControllerData(String id, PersonData data, SocioDemographicData sddata, String responsible, Carer carers)
	{
		super.setControllerData(id, data, sddata, responsible, carers);
	}


	/**
	 * 
	 * @param id
	 * @param data
	 * @param sddata
	 * @param responsible
	 * @param carers
	 * @param sw
	 * @param consulter
	 * @param gralpract
	 */
	public void setControllerData(String id, PersonData data, SocioDemographicData sddata, String responsible, Carer carers,
			SocialWorker sw, Consulter consulter, GeneralPractitioner gralpract)
	{
		this.currentsocialworker = sw;
		this.currentconsulter = consulter;
		this.currentgralprac = gralpract;

		this.setControllerData(id, data, sddata, responsible, carers);
		
		StorageComponentImpl proxy = new StorageComponentImpl();
		OperationResult op = proxy.getUserIdByPatientId(id, (String) Sessions.getCurrent().getAttribute("userid"));
		patientUserId = Integer.parseInt( op.getCode() ); 
	}


	/**
	 * 
	 */
	public Button[] createActionButtons()
	{
		Button btn = new Button();
		String text = Labels.getLabel("patients.update.title");
		btn.setLabel(text);
		btn.setHref("../patients/update.zul?patid=" + this.currentid);

		Button btn1 = new Button();
		String text2 = Labels.getLabel("common.tasks.new");
		btn1.setLabel(text2);
		btn1.setHref("/patients");

		Button[] ret = new Button[2];
		ret[0] = btn;
		ret[1] = btn1;

		return ret;
	}


	/**
	 * 
	 */
	public Listitem[] createDataRows()
	{
		Listitem[] rows = this.getPersonDataListItems();
		Listitem[] carerrows = this.getCarerListAsListItems();

		Listitem[] ret = new Listitem[rows.length + carerrows.length + 2 + 3];

		ret[0] = rows[0];
		ret[1] = this.getSDItem();
		ret[2] = this.getCurrentResponsibleListItem();

		for (int i = 0; i < carerrows.length; i++)
		{
			ret[i + 3] = carerrows[i];
		}
		for (int ii = 1; ii < rows.length; ii++)
		{
			ret[carerrows.length + 2 + ii] = rows[ii];
		}

		ret[ret.length - 3] = this.getSocialWorkerListitem();
		ret[ret.length - 2] = this.getConsulterListitem();
		ret[ret.length - 1] = this.getGeneralPracticionerListitem();

		return ret;
	}


	/**
	 * 
	 * @param assid
	 * @param patientid
	 */
	public void viewAssessmentDetail(String assid, String patientid)
	{
		StorageComponentImpl proxy = new StorageComponentImpl();
		PatientAssessment assessment = null;
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		try
		{
			PatientAssessment[] info = proxy.getPatientAssessmentsArr(patientid, userid);
			for (int i = 0; i < info.length; i++)
			{
				if (info[i].getID().equals(assid))
				{
					assessment = info[i];
				}
			}
			if (assessment == null)
			{
				throw new Exception("Assessment not found");
			}
			assessmentWindow = (AssessmentPopupController) Executions.createComponents("../patients/assessment.zul", this, null);
			this.setAssessmentValues(assessmentWindow, assessment);
			assessmentWindow.setVisible(true);
			assessmentWindow.doModal();
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
		}
	}


	/**
	 * 
	 * @throws SuspendNotAllowedException
	 * @throws InterruptedException
	 */
	public void createAssessment() throws SuspendNotAllowedException, InterruptedException
	{
		assessmentWindow = (AssessmentPopupController) Executions.createComponents("../patients/assessment.zul", this, null);
		this.turnAssessment2Form(assessmentWindow);
		assessmentWindow.setVisible(true);
		assessmentWindow.doModal();
	}


	/**
	 * 
	 * @param assessmentWindow
	 */
	protected void turnAssessment2Form(AssessmentPopupController assessmentWindow)
	{
		for (int i = 0; i < 26; i++)
		{
			if (i < 10)
			{
				assessmentWindow.getFellow("field0" + i).setVisible(false);
				assessmentWindow.getFellow("field0" + i + "_in").setVisible(true);
			}
			else
			{
				assessmentWindow.getFellow("field" + i).setVisible(false);
				assessmentWindow.getFellow("field" + i + "_in").setVisible(true);
			}
		}
		
		assessmentWindow.getFellow("measurementsrow").setVisible(false);
		assessmentWindow.getFellow("buttonrow").setVisible(true);
		assessmentWindow.getFellow("noformrow").setVisible(false);
		assessmentWindow.getFellow("datelabel").setVisible(false);
		assessmentWindow.getFellow("datehbox").setVisible(false);
		((Textbox) assessmentWindow.getFellow("patientid")).setValue(this.currentid);
	}


	/**
	 * 
	 * @param assessmentWindow
	 * @param assessment
	 */
	protected void setAssessmentValues(AssessmentPopupController assessmentWindow, PatientAssessment assessment)
	{
		((Label) assessmentWindow.getFellow("field00")).setValue(assessment.getID());
		((Label) assessmentWindow.getFellow("field01")).setValue(assessment.getPatientID());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (assessment.getDateOfAssessment() == null)
		{
			((Label) assessmentWindow.getFellow("field02")).setValue("");
		}
		else
		{
			((Label) assessmentWindow.getFellow("field02")).setValue(sdf.format(assessment.getDateOfAssessmentCalendar().getTime()));
		}

		((Label) assessmentWindow.getFellow("field03")).setValue(assessment.getAetology().toString());
		((Label) assessmentWindow.getFellow("field04")).setValue("" + assessment.getTimeEllapsedSinceDiagnosed());
		((Label) assessmentWindow.getFellow("field05")).setValue("" + assessment.getSeverity());
		((Label) assessmentWindow.getFellow("field06")).setValue(assessment.getRelevantPathologyAntecedents());
		((Label) assessmentWindow.getFellow("field07")).setValue(assessment.getComorbidity());
		((Label) assessmentWindow.getFellow("field08")).setValue(assessment.getCharlsonComorbidityIndex().toString());
		((Label) assessmentWindow.getFellow("field09")).setValue(assessment.getBarthelIndex().toString());
		((Label) assessmentWindow.getFellow("field10")).setValue(assessment.getLawtonIndex().toString());
		((Label) assessmentWindow.getFellow("field11")).setValue("" + assessment.getMMSE());
		((Label) assessmentWindow.getFellow("field12")).setValue(assessment.getMDRS().toString());
		((Label) assessmentWindow.getFellow("field13")).setValue(
				assessment.getBlessedScalePart1() == null ? "Not set" : assessment.getBlessedScalePart1().toString());
		((Label) assessmentWindow.getFellow("field14")).setValue(
				assessment.getBlessedScalePart2() == null ? "Not set" : assessment.getBlessedScalePart2().toString());
		((Label) assessmentWindow.getFellow("field15")).setValue(
				assessment.getBlessedScalePart3() == null ? "Not set" : assessment.getBlessedScalePart3().toString());
		((Label) assessmentWindow.getFellow("field16")).setValue(
				assessment.getChecklistMBP() == null ? "Not set" : assessment.getChecklistMBP().toString());
		((Label) assessmentWindow.getFellow("field17")).setValue(
				assessment.getNPQISeverity() == null ? "Not set" : assessment.getNPQISeverity().toString());
		((Label) assessmentWindow.getFellow("field18")).setValue(
				assessment.getNPQIStress() == null ? "Not set" : assessment.getNPQIStress().toString());
		((Label) assessmentWindow.getFellow("field19")).setValue(
				assessment.getGDS() == null ? "Not set" : assessment.getGDS().toString());
		((Label) assessmentWindow.getFellow("field20")).setValue(
				assessment.isFalls() == null ? "Not set" : assessment.isFalls().toString());
		((Label) assessmentWindow.getFellow("field21")).setValue(
				assessment.isIncontinence() == null ? "Not set" : assessment.isIncontinence().toString());
		((Label) assessmentWindow.getFellow("field22")).setValue(
				assessment.isDelirium() == null ? "Not set" : assessment.isDelirium().toString());
		((Label) assessmentWindow.getFellow("field23")).setValue(
				assessment.isImmobility() == null ? "Not set" : assessment.isImmobility().toString());
		((Label) assessmentWindow.getFellow("field24")).setValue(
				assessment.isSensorialDeficits() == null ? "Not set" : assessment.isSensorialDeficits().toString());
		((Label) assessmentWindow.getFellow("field25")).setValue(
				assessment.getPharmacologicalTreatment() == null ? "Not set" : assessment.getPharmacologicalTreatment().toString());
	}


	/**
	 * 
	 * @return
	 */
	protected Listitem getCurrentResponsibleListItem()
	{
		Listitem lst = new Listitem();
		String text = Labels.getLabel("patients.form.responsible");
		Listcell cell1 = new Listcell(text);
		StorageComponentImpl proxy = new StorageComponentImpl();
		String id = (String) Sessions.getCurrent().getAttribute("userid");
		try
		{
			Clinician clinician = proxy.getClinician(this.currentresponsible, id);
			Listcell cell2 = new Listcell(clinician.toString());

			lst.appendChild(cell1);
			lst.appendChild(cell2);
		}
		catch (Exception re)
		{
			SystemDictionary.logException(re);
		}
		return lst;
	}


	/**
	 * 
	 * @return
	 */
	protected Listitem[] getCarerListAsListItems()
	{
		Listitem[] ret = new Listitem[1];
		String text = "Carer";
		Listitem careritem = new Listitem();
		Listcell empty = new Listcell(text);
		Listcell carername = new Listcell(this.currentcarers.getPersonData().getSurname()
				+ ", " + this.currentcarers.getPersonData().getName());
		careritem.appendChild(empty);
		careritem.appendChild(carername);
		ret[0] = careritem;
		return ret;
	}


	/**
	 * 
	 * @return
	 */
	protected Listitem getSocialWorkerListitem()
	{
		Listitem sw = new Listitem();
		Listcell cell1 = new Listcell("Social worker");
		Listcell cell2 = null;
		if (this.currentsocialworker != null)
		{
			cell2 = new Listcell(this.currentsocialworker.getName() + " ("
					+ this.currentsocialworker.getPhone() + "/"
					+ this.currentsocialworker.getEmail() + ")");
		}
		else
		{
			cell2 = new Listcell("Undefined");
		}
		sw.appendChild(cell1);
		sw.appendChild(cell2);

		return sw;
	}


	/**
	 * 
	 * @return
	 */
	protected Listitem getConsulterListitem()
	{
		Listitem sw = new Listitem();
		Listcell cell1 = new Listcell("Consulter");
		Listcell cell2 = null;
		if (this.currentconsulter != null)
		{
			cell2 = new Listcell(this.currentconsulter.getName() + " ("
					+ this.currentconsulter.getPhone() + "/"
					+ this.currentconsulter.getEmail() + ")");
		}
		else
		{
			cell2 = new Listcell("Undefined");
		}
		sw.appendChild(cell1);
		sw.appendChild(cell2);

		return sw;
	}


	/**
	 * 
	 * @return
	 */
	protected Listitem getGeneralPracticionerListitem()
	{
		Listitem sw = new Listitem();
		Listcell cell1 = new Listcell("General Practicioner");
		Listcell cell2 = null;
		if (this.currentgralprac != null)
		{
			cell2 = new Listcell(this.currentgralprac.getName() + " ("
					+ this.currentgralprac.getPhone() + "/"
					+ this.currentgralprac.getEmail() + ")");
		}
		else
		{
			cell2 = new Listcell("Undefined");
		}
		sw.appendChild(cell1);
		sw.appendChild(cell2);

		return sw;
	}


	/**
	 * 
	 */
	public void refreshCalendarData()
	{
		Session ses = Sessions.getCurrent();
		String userid = (String) ses.getAttribute("userid");
		if (this.calendars == null)
		{
			this.calendars = (Calendars) this.getFellow("cal");
		}
		Calendar calfrom = new GregorianCalendar();
		calfrom.setTime(this.calendars.getBeginDate());
		Calendar calto = new GregorianCalendar();
		calto.setTime(this.calendars.getEndDate());
		try
		{
			StorageComponentImpl proxy = new StorageComponentImpl();
			OperationResult currentor = proxy.getUserIdByCarerId(this.currentcarers.getID(), userid);
			Task[] tasklist = proxy.getUserPlannedTasksArr(currentor.getCode(), calfrom, calto, SystemDictionary.getLocale(), userid);
			this.calmodel = new SimpleCalendarModel();
			if (tasklist != null)
			{
				OperationResult currentuserid = proxy.getUserIdByPatientId(this.currentid, userid);
				for (int i = 0; i < tasklist.length; i++)
				{
					boolean add = true;
					if (!tasklist[i].getObjectID().equals(currentuserid.getCode()))
					{
						continue;
					}
					GregorianCalendar calendar1 = new GregorianCalendar();
					calendar1.setTimeInMillis(tasklist[i].getDateTimeAssignedCalendar().getTimeInMillis());

					GregorianCalendar calendar2 = new GregorianCalendar();
					calendar2.setTimeInMillis(tasklist[i].getDateTimeFulfilledCalendar().getTimeInMillis());

					AladdinCalendarEvent clevent = new AladdinCalendarEvent();
					clevent.setBeginDate(calendar1.getTime());
					clevent.setEndDate(new Date(calendar1.getTime().getTime() + 3600000));
					clevent.setLocked(true);

					String labelContent = SystemDictionary.getTaskTypeLabel(tasklist[i].getTaskType().getCode());
					if (labelContent.length() > 18) {
						labelContent = labelContent.substring(0, 17) + "..";
					}
					
					clevent.setTitle(labelContent);
					clevent.setContent(labelContent);
					switch (Integer.parseInt(tasklist[i].getTaskStatus().getCode()))
					{
						case SystemDictionary.TASK_STATUS_CANCELLED_INT:
							clevent.setHeaderColor("red");
							clevent.setContentColor("red");
							add = false;
							break;
							
						case SystemDictionary.TASK_STATUS_COMPLETED_INT:
							clevent.setHeaderColor("blue");
							clevent.setContentColor("blue");
							break;
							
						case SystemDictionary.TASK_STATUS_PENDING_INT:
							clevent.setHeaderColor("black");
							clevent.setContentColor("black");
							break;
							
						default:
							clevent.setHeaderColor("yellow");
							clevent.setContentColor("yellow");
							break;
					}
					
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("user", userid);
					params.put("objid", tasklist[i].getObjectID());
					params.put("exec", tasklist[i].getExecutorID());
					params.put("assign", tasklist[i].getAssignerID());
					params.put("task", tasklist[i].getID());
					params.put("status", tasklist[i].getTaskStatus().getCode());
					params.put("url", tasklist[i].getURL());
					params.put("text", tasklist[i].getText());
					params.put("type", tasklist[i].getTaskType().getCode());
					if (tasklist[i].getTaskType().getCode().equals(SystemDictionary.TASK_TYPE_CARERQS)
							|| tasklist[i].getTaskType().getCode().equals(SystemDictionary.TASK_TYPE_PATIENTQS))
					{
						if (tasklist[i].getQuestionnaire() != null)
						{
							params.put("qid", tasklist[i].getQuestionnaire().getID());
							params.put("questionnaire", tasklist[i].getQuestionnaire());
						}
						else
						{
							add = false;
						}
					}
					clevent.setParams(params);
					if (add)
					{
						this.calmodel.add(clevent);
					}
				}
			}
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
		}
		finally
		{
			Calendars calendar = (Calendars) getFellow("cal");
			calendar.setModel(this.calmodel);
		}
	}


	/**
	 * 
	 * @throws SuspendNotAllowedException
	 * @throws InterruptedException
	 */
	public void createRemoveMassivelyDialog() throws SuspendNotAllowedException, InterruptedException
	{
		removeMassivelyDialog = (Window) Executions.createComponents("../patients/removetasks.zul", this, null);
		removeMassivelyDialog.setTitle("New Task");
		removeMassivelyDialog.setVisible(true);
		removeMassivelyDialog.doModal();
		((Textbox) removeMassivelyDialog.getFellow("removepatientid")).setValue(this.currentid);
	}


	/**
	 * 
	 * @throws SuspendNotAllowedException
	 * @throws InterruptedException
	 */
	public void createWeightMeasurementDialog() throws SuspendNotAllowedException, InterruptedException
	{
		weightMeasurementDialog = (Window) Executions.createComponents("../patients/measurement.zul", this, null);
		weightMeasurementDialog.setVisible(true);
		weightMeasurementDialog.doModal();

		Date bdate = this.calendars.getBeginDate();
		Date edate = this.calendars.getEndDate();

		((MeasurementPopupController) weightMeasurementDialog).setPatientid(this.currentid);
		((MeasurementPopupController) weightMeasurementDialog).setFrom(bdate);
		((MeasurementPopupController) weightMeasurementDialog).setTo(edate);
	}


	/**
	 * 
	 * @throws SuspendNotAllowedException
	 * @throws InterruptedException
	 * @throws RemoteException
	 */
	public void createPasswordDialog(String pat_id) throws SuspendNotAllowedException, InterruptedException, RemoteException
	{
		ChangePassword win = (ChangePassword) Executions.createComponents("../patients/password.zul", this, null);
		this.appendChild(win);
		win.setuserid("" + patientUserId);
		win.doModal();
	}
	
	
}
