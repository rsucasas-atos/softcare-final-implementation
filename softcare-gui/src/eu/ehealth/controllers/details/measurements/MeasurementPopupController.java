package eu.ehealth.controllers.details.measurements;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Window;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Timebox;
import eu.ehealth.SystemDictionary;
import eu.ehealth.dataprocess.measurements.MeasurementsProcessor;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.OperationResult;


/**
 * 
 * @author 
 *
 */
public class MeasurementPopupController extends Window
{


	private static final long serialVersionUID = 2129003366036217191L;
	private String patientid;
	
	
	/**
	 * 
	 */
	public void onCreate()  
	{ 
		try 
		{
			// Initialize dates
			Date d = new Date();
			d.setMonth(0); 	// 0-11
			d.setDate(1);  	// 1-31
			d.setHours(0);
			d.setMinutes(0);
			d.setSeconds(0);
			
			// from...
			setFrom(d);
			// to...
			setTo(new Date());
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("ERROR", "ERROR : " + e.getMessage());
		}
	}


	/**
	 * 
	 * @param patientid
	 */
	@SuppressWarnings("unused")
	public void setPatientid(String patientid)
	{
		this.patientid = patientid;
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		String loggeduser = (String) Sessions.getCurrent().getAttribute("userid");
		try
		{
			OperationResult user = proxy.getUserIdByPersonId(this.patientid, SystemDictionary.USERTYPE_PATIENT_INT, loggeduser);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Executions.sendRedirect("");
		}
	}


	/**
	 * 
	 * @param from
	 */
	public void setFrom(Date from)
	{
		try 
		{
			((Datebox) getFellow("dfrom")).setValue(from);
			((Timebox) getFellow("tfrom")).setValue(from);
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("DEBUG", "ERROR : " + e.getMessage());
		}
	}


	/**
	 * 
	 * @param to
	 */
	public void setTo(Date to)
	{
		try 
		{
			((Datebox) getFellow("dto")).setValue(to);
			((Timebox) getFellow("tto")).setValue(to);
		}
		catch (Exception e)
		{
			SystemDictionary.webguiLog("DEBUG", "ERROR : " + e.getMessage());
		}
	}


	/**
	 * 
	 * @throws RemoteException
	 */
	@SuppressWarnings("deprecation")
	public void generateImage() throws RemoteException
	{
		try
		{
			String loggeduser = (String) Sessions.getCurrent().getAttribute("userid");
			String mtype = (String) ((Listbox) getFellow("measurementtype")).getSelectedItem().getValue();
	
			Date from = ((Datebox) getFellow("dfrom")).getValue();
			from.setHours(((Timebox) getFellow("tfrom")).getValue().getHours());
			from.setMinutes(((Timebox) getFellow("tfrom")).getValue().getMinutes());
			from.setSeconds(((Timebox) getFellow("tfrom")).getValue().getSeconds());
	
			Date to = ((Datebox) getFellow("dto")).getValue();
			to.setHours(((Timebox) getFellow("tto")).getValue().getHours());
			to.setMinutes(((Timebox) getFellow("tto")).getValue().getMinutes());
			to.setSeconds(((Timebox) getFellow("tto")).getValue().getSeconds());
	
			Calendar calto = new GregorianCalendar();
			calto.setTime(to);
			Calendar calfrom = new GregorianCalendar();
			calfrom.setTime(from);
			
			MeasurementsProcessor processData = new MeasurementsProcessor();
			AImage image = processData.generateChartImage(this.patientid, loggeduser, mtype, calto, calfrom);
			if (image != null) 
			{
				((Image) getFellow("imagemeas")).setContent(image);
				Button downbutton = (Button) getFellow("measurementdownloadbutton");
				downbutton.addEventListener("onClick", new DownloadListener(image, "monitor.png")); 
			}
			else 
			{
				// ERROR
				SystemDictionary.webguiLog("DEBUG", "ERROR : image == NULL");
			}
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
		}
	}

	/**
	 * 
	 * @author 
	 *
	 */
	private class DownloadListener implements EventListener
	{


		private AImage input;
		private String filename;


		public DownloadListener(AImage in, String fname)
		{
			this.input = in;
			this.filename = fname;
		}


		public void onEvent(Event arg0) throws Exception
		{
			if (this.input != null)
			{
				Filedownload.save(input, filename);
			}
		}
		

	}
	
	
}
