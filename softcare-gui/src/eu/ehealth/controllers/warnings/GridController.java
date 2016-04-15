package eu.ehealth.controllers.warnings;

import java.util.ArrayList;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Footer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import eu.ehealth.SystemDictionary;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.SearchCriteria;
import eu.ehealth.ws_client.xsd.Warning;


/**
 * 
 * 
 * @author a572832
 * @date 23/04/2014.
 *
 */
public class GridController extends SelectorComposer<Component>
{

	
	private static final long serialVersionUID = 1L;
	
	// grid
	private Grid wList;
	// warnings list
	private WarningInfo[] _aWarnings;
	// footer text
	@Wire
	private Footer footer_category;
	// Filter Options /////////////////
	// Radio buttons
	@Wire
	private Radio radAll;
	@Wire
	private Radio radReaded;
	@Wire
	private Radio radNotReaded;
	// Patient name textbox
	@Wire
	private Textbox filterpatienttrigger;
	// dates
	@Wire
	private Datebox datefromfilter;
	@Wire
	private Datebox datetofilter;
	
	
	/**
	 * 
	 */
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		try 
		{
			wList = (Grid) comp;
			int totalWarnings = setWarningsModel(false);
			footer_category.setLabel("A Total of " + totalWarnings + " warnings");
		}
		catch (Exception re)
		{
			SystemDictionary.logException(re);
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	private WarningInfo[] getWarnings() {
		try 
		{
			if ((_aWarnings != null) && (_aWarnings.length > 0)) {
				SystemDictionary.webguiLog("DEBUG", "_aWarnings is NOT null - length: " + _aWarnings.length);
				return _aWarnings;
			}
			
			SystemDictionary.webguiLog("DEBUG", "_aWarnings is null");
			
			ArrayList<SearchCriteria> zerolist = new ArrayList<SearchCriteria>();
			SearchCriteria[] sc = new SearchCriteria[zerolist.size()];
			SystemDictionary.webguiLog("TRACE", "ZEROLIST length: " + zerolist.size());
			for (int i = 0; i < zerolist.size(); i++)
			{
				sc[i] = zerolist.get(i);
			}
			
			String id = (String) Sessions.getCurrent().getAttribute("userid");
			
			StorageComponentImpl proxy = new StorageComponentImpl();
			Warning[] wli = null;
			wli = proxy.getWarningsArr(sc, id);
			if (wli == null || wli.length == 0)
			{
				_aWarnings = new WarningInfo[0];
			}
			else
			{
				_aWarnings = new WarningInfo[wli.length];
				for (int i = 0; wli.length > i; i++)
				{
					_aWarnings[i] = new WarningInfo(wli[i], id);
				}
			}
			
			return _aWarnings;
		}
		catch (Exception re)
		{
			SystemDictionary.logException(re);
			return new WarningInfo[0];
		}
	}
	

	/**
	 * Reload all data - retrieve data from database
	 */
	@Listen("onClick=#reloadall")
	public void reloadAll() {
		_aWarnings = null;
		
		// reset fields
		radAll.setSelected(true);
		filterpatienttrigger.setText("");
		datefromfilter.setText("");
		datetofilter.setText("");

		// reload data
		int totalWarnings = setWarningsModel(false);
		footer_category.setLabel("Show all warnings - " + totalWarnings + " warnings");
	}
	
	
	/**
	 * 
	 */
	@Listen("onClick=#loadPatientWarnings")
	public void loadPatientWarnings() 
	{
		int totalWarnings = setWarningsModel(true);
		footer_category.setLabel("Warnings from " + filterpatienttrigger.getText() + " - " + totalWarnings + " warnings");
	}

	
	@Listen("onClick=#clearPatientField")
	public void clearPatientField() 
	{
		filterpatienttrigger.setText("");
	}
	
	
	@Listen("onClick=#clearDates")
	public void clearDates() 
	{
		datefromfilter.setText("");
		datetofilter.setText("");
	}
	
	
	@Listen("onClick=#loadHistoric")
	public void loadHistoric() 
	{
		// reset fields
		radAll.setSelected(true);
		filterpatienttrigger.setText("");
		datefromfilter.setText("");
		datetofilter.setText("");
	}
	
	
	/**
	 * 
	 */
	@Listen("onClick=#loadAllPatientWarnings")
	public void loadAllPatientWarnings() 
	{
		// reset fields
		filterpatienttrigger.setText("");
				
		int totalWarnings = setWarningsModel(true);
		footer_category.setLabel("Warnings from all patients - " + totalWarnings + " warnings");
	}
	
	
	/**
	 * 
	 * @param event
	 */
	@Listen("onCheck = #categorySelector1")
	public void selectCategory(CheckEvent event) 
	{
		int totalWarnings = setWarningsModel(true);
		footer_category.setLabel("A Total of " + totalWarnings + " Warnings");
	}
	
	
	/**
	 * 
	 * @return
	 */
	private int setWarningsModel(boolean filter) 
	{
		try 
		{
			ListModel<WarningInfo> model = null;
			if (filter) 
			{
				model = new ListModelList<WarningInfo>(getFilteredWarnings());
			}
			else 
			{
				model = new ListModelList<WarningInfo>(getWarnings());
			}
			
			wList.setModel(model);
			
			return model.getSize();
		}
		catch (Exception ex) 
		{
			SystemDictionary.logException(ex);
			return -1;
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	private WarningInfo[] getFilteredWarnings() {
		try 
		{
			// radio buttons
			boolean rReaded = radReaded.isChecked();
			boolean rNotReaded = radNotReaded.isChecked();
			// Patient name
			String patientName = filterpatienttrigger.getText();
			// dates
			//private Datebox datefromfilter;
			//private Datebox datetofilter;
			
			// get warnings
			WarningInfo[] lall = getWarnings();
			
			// filter
			// Patient name
			if (patientName.trim().length() > 0) 
			{
				lall = getWarnings_PatientName(lall, patientName);
			}
			
			// radio buttons
			if (rReaded) 
			{
				lall = getWarnings_Delivered(lall, "true");
			}
			else if (rNotReaded) 
			{
				lall = getWarnings_Delivered(lall, "false");
			}
			
			ArrayList<WarningInfo> lTemp = new ArrayList<WarningInfo>();
			for (WarningInfo winfo : lall) {
				lTemp.add(winfo);
			}
			
			WarningInfo[] res = lTemp.toArray(new WarningInfo[lTemp.size()]);
			return res;
		}
		catch (Exception re)
		{
			SystemDictionary.logException(re);
			return new WarningInfo[0];
		}
	}
	
	
	/**
	 * 
	 * @param lall
	 * @param value
	 * @return
	 */
	private WarningInfo[] getWarnings_PatientName(WarningInfo[] lall, String value) {
		try 
		{
			SystemDictionary.webguiLog("DEBUG", "getWarnings_PatientName: " + value);
			
			ArrayList<WarningInfo> lTemp = new ArrayList<WarningInfo>();
			
			for (WarningInfo winfo : lall) {
				SystemDictionary.webguiLog("DEBUG", "winfo.getPatientName(): " + winfo.getPatientName());
				
				if (winfo.getPatientName().equals(value)) 
				{
					lTemp.add(winfo);
				}
			}
			
			WarningInfo[] res = lTemp.toArray(new WarningInfo[lTemp.size()]);
			return res;
		}
		catch (Exception re)
		{
			SystemDictionary.logException(re);
			return new WarningInfo[0];
		}
	}
	
	
	/**
	 * 
	 * @param lall
	 * @param value
	 * @return
	 */
	private WarningInfo[] getWarnings_Delivered(WarningInfo[] lall, String value) {
		try 
		{
			ArrayList<WarningInfo> lTemp = new ArrayList<WarningInfo>();
			
			for (WarningInfo winfo : lall) {
				if (winfo.getDelivered().toLowerCase().equals(value)) {
					lTemp.add(winfo);
				}
			}
			
			WarningInfo[] res = lTemp.toArray(new WarningInfo[lTemp.size()]);
			return res;
		}
		catch (Exception re)
		{
			SystemDictionary.logException(re);
			return new WarningInfo[0];
		}
	}
	
	
	/*private SearchCriteria dateCriteria(String id, String compareopt)
	{
		Datebox filterfrom = (Datebox) this.getFellow("datefromfilter");
		Date dat = filterfrom.getValue();
		SearchCriteria sc = null;
		if (dat != null)
		{
			Calendar cal = new GregorianCalendar();
			cal.setTime(dat);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dstr = sdf.format(cal.getTime());
			sc = new SearchCriteria();
			sc.setFieldName("dateTimeOfWarning");
			sc.setCompareOp(new SystemParameter(SystemDictionary.COMPARE_GREAT, ""));
			SystemDictionary.webguiLog("DEBUG", "DATE TO FILTER: " + dstr);
			sc.setFieldValue1(dstr);
		}
		return sc;
	}*/
	
	
}
