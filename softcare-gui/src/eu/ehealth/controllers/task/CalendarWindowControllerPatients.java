package eu.ehealth.controllers.task;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;
import eu.ehealth.SystemDictionary;
import eu.ehealth.utilities.ComponentsFinder;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.ExternalService;
import eu.ehealth.ws_client.xsd.OperationResult;
import eu.ehealth.ws_client.xsd.Questionnaire;
import eu.ehealth.ws_client.xsd.SystemParameter;
import eu.ehealth.ws_client.xsd.Task;


/**
 * 
 * 
 * @author 
 * @date 13/03/2014.
 *
 */
public class CalendarWindowControllerPatients extends Window
{


	private static final long serialVersionUID = -5542409524567322844L;
	public ExternalService[] selected_services;


	/**
	 * 
	 * @param type
	 * @return
	 */
	public static ExternalService[] getListExtServices(String type) 
	{
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		ExternalService[] urilist = null;
		try
		{
			String petusid = (String)Sessions.getCurrent().getAttribute("userid");
			ArrayList<ExternalService> urilistTMP = proxy.getAllExternalServicesArrayList(petusid);
			
			if (urilistTMP != null) 
			{
				ArrayList<ExternalService> urilistTMP2 = new ArrayList<ExternalService>();
				for (int i=0; i<urilistTMP.size(); i++)
				{
					if (urilistTMP.get(i).getType().equals(type)) {
						urilistTMP2.add(urilistTMP.get(i));
					}
				}
				
				urilist = new ExternalService[urilistTMP2.size()];
				for (int i=0; i<urilistTMP2.size(); i++) 
				{
					urilist[i] = urilistTMP2.get(i);
				}
			}
		}
		catch(Exception e)
		{
			SystemDictionary.logException(e);
		} 
		return urilist;
	}
	
	
	/**
	 * 
	 * @param obj
	 */
	public void changeTaskType(Object obj)
	{
		boolean urlrowCOGNGAMES = false;
		boolean urlrowEXERCISES = false;
		boolean qsrow = false;
		boolean txtrow = false;
		
		int comp = (Integer) obj;
		switch (comp)
		{
			case SystemDictionary.TASK_TYPE_COGGAME_INT:
				urlrowCOGNGAMES = true;
				break;
			case SystemDictionary.TASK_TYPE_CARERQS_INT:
			case SystemDictionary.TASK_TYPE_PATIENTQS_INT:
				qsrow = true;
				break;
			case SystemDictionary.TASK_TYPE_EXERCISE_INT:
				urlrowEXERCISES = true;
				txtrow = true;
				break;
			case SystemDictionary.TASK_TYPE_TXT_INT:
				txtrow = true;
				break;
			default:
				break;
		}
		;
		getFellow("urlrowCOGNGAMES").setVisible(urlrowCOGNGAMES);
		getFellow("urlrowEXERCISES").setVisible(urlrowEXERCISES);
		getFellow("qsrow").setVisible(qsrow);
		getFellow("textrow").setVisible(txtrow);
	}


	/**
	 * 
	 * @param checked
	 */
	public void showHidePeriodicFields(boolean checked)
	{
		getFellow("freq1row").setVisible(checked);
		getFellow("till1row").setVisible(checked);
	}


	/**
	 * 
	 */
	public void saveTask()
	{
		String URL = "";
		String text = "";
		Questionnaire questionnaire = new Questionnaire();
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		try
		{
			org.zkoss.zul.Listbox listbox = (org.zkoss.zul.Listbox) getFellow("tasktypesel");
			org.zkoss.zul.Listitem listitem = listbox.getSelectedItem();
			SystemParameter tastype = new SystemParameter(((Integer) listitem.getValue()).toString(), listitem.getLabel());
			String userids = ((Textbox) getFellow("userid")).getValue();
			
			switch (((Integer) listitem.getValue()).intValue())
			{
				case SystemDictionary.TASK_TYPE_CARERQS_INT:
				case SystemDictionary.TASK_TYPE_PATIENTQS_INT:
					String qid = (String) ((Listbox) getFellow("questnamefield")).getSelectedItem().getValue();
					questionnaire = proxy.getQuestionnaire(qid, SystemDictionary.getLocale(), userids);
					break;
				case SystemDictionary.TASK_TYPE_COGGAME_INT:
					Listbox urlfieldCOGNGAMES = (Listbox) getFellow("urlfieldCOGNGAMES");
					Listitem selectedItemCOGNGAMES = urlfieldCOGNGAMES.getSelectedItem();
					if (selectedItemCOGNGAMES == null && urlfieldCOGNGAMES.getItemCount() > 0)
					{
						selectedItemCOGNGAMES = (Listitem) urlfieldCOGNGAMES.getItems().get(0);
					}
					URL = (String) selectedItemCOGNGAMES.getValue();
					break;
				case SystemDictionary.TASK_TYPE_TXT_INT:
					text = ((Textbox) getFellow("textfield")).getValue();
					break;
				case SystemDictionary.TASK_TYPE_EXERCISE_INT:
					Listbox urlfieldEXERCISES = (Listbox) getFellow("urlfieldEXERCISES");
					Listitem selectedItemEXERCISES = urlfieldEXERCISES.getSelectedItem();
					if (selectedItemEXERCISES == null && urlfieldEXERCISES.getItemCount() > 0)
					{
						selectedItemEXERCISES = (Listitem) urlfieldEXERCISES.getItems().get(0);
					}
					URL = (String) selectedItemEXERCISES.getValue();
					text = ((Textbox) getFellow("textfield")).getValue();
					break;
				default:
					// do nothing
					break;
			}

			Datebox dbox = (Datebox) getFellow("datetask");
			Timebox tbox = (Timebox) getFellow("timetask");
			GregorianCalendar caltas = new GregorianCalendar();
			caltas.setTime(dbox.getValue());
			GregorianCalendar caltas2 = new GregorianCalendar();
			caltas2.setTime(tbox.getValue());
			Long added = new Long(caltas2.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000);
			added += caltas.get(Calendar.MINUTE) * 60 * 1000;
			caltas2.setTime(new Date(dbox.getValue().getTime() + added));

			SystemParameter tasstatus = new SystemParameter(SystemDictionary.TASK_STATUS_PENDING, SystemDictionary.TASK_STATUS_PENDING_LBL);
			// Object ID (Person ID)
			String objids = ((Textbox) getFellow("objid")).getValue();
			String addressedid = ((Textbox) getFellow("addressedid")).getValue();
			
			Task ts = new Task("", tastype, caltas, caltas2, tasstatus, URL, text, questionnaire, objids, userids, addressedid);
			boolean massive = ((Checkbox) getFellow("massivecheck")).isChecked();
			OperationResult opres = null;
			if (massive)
			{
				Integer freq = new Integer((String) ((Listbox) getFellow("massivelist")).getSelectedItem().getValue());
				Date tillday = (Date) ((Datebox) getFellow("massivecal")).getValue();
				tillday.setTime(tillday.getTime() + (24 * 60 * 60 * 1000));
				
				XMLGregorianCalendar xmlStartDate = null;
				XMLGregorianCalendar xmlEndDate = null;
				
				try {
					GregorianCalendar c = new GregorianCalendar();
					c.setTime(dbox.getValue());
					xmlStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
				}
				catch (Exception ex) {}
				
				try {
					GregorianCalendar c = new GregorianCalendar();
					c.setTime(tillday);
					xmlEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
				}
				catch (Exception ex) {}
				
				opres = proxy.assignTasksMassively(ts, xmlStartDate, xmlEndDate, BigInteger.valueOf(freq), userids);
			}
			else
			{
				opres = proxy.assignTask(ts, SystemDictionary.getLocale(), userids);
			}
			
			Messagebox.show("#TXT# Task crated succesfully", "#TXT# Save Task", Messagebox.OK, Messagebox.INFORMATION);
			
			String patid_value = ((Textbox) getFellow("patid_value")).getValue();
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc(null);
			comp.setSrc("../patients/details.zul?patid=" + patid_value);
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
			Messagebox.show("#TXT# Error : " + e.getMessage(), "#TXT# Save Task", Messagebox.OK, Messagebox.ERROR);
		}
		finally
		{
			this.getParent().removeChild(this);
		}
	}


	/**
	 * 
	 */
	public void cancelTask()
	{
		StorageComponentImpl proxy = new StorageComponentImpl();
		try
		{
			String task = ((Textbox) getFellow("taskidfield")).getValue();
			Session ses = Sessions.getCurrent();
			String uid = (String) ses.getAttribute("userid");
			proxy.changeTaskStatus(Integer.parseInt(task), SystemDictionary.TASK_STATUS_CANCELLED_INT, uid);

			Messagebox.show("#TXT# Task canceled succesfully", "#TXT# Cancel Task", Messagebox.OK, Messagebox.INFORMATION);
			
			String patid_value = ((Textbox) getFellow("patid_value")).getValue();
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc(null);
			comp.setSrc("../patients/details.zul?patid=" + patid_value);
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
			Messagebox.show("#TXT# Error : " + e.getMessage(), "#TXT# Cancel Task", Messagebox.OK, Messagebox.ERROR);
		}
	}


	/**
	 * 
	 */
	public void setTextFromExercise()
	{
		Listitem item = ((Listbox) getFellow("urlfield")).getSelectedItem();
		String text = (String) item.getValue();
		((Textbox) getFellow("textfield")).setValue(text);
	}


	/**
	 * 
	 */
	public void closeTheWindow()
	{
		this.setVisible(false);
		this.getParent().removeChild(this);
	}
	

}
