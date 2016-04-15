package eu.ehealth.controllers.cms;

import java.util.Collection;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Checkbox;

import eu.ehealth.SystemDictionary;
import eu.ehealth.utilities.ComponentsFinder;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.MediaContent;
import eu.ehealth.ws_client.xsd.SearchCriteria;
import eu.ehealth.ws_client.xsd.SystemParameter;


/**
 * 
 * @author Xavi Sarda (Atos Origin)
 */
public class ContentManagerIndex extends Window
{


	private static final long serialVersionUID = -5977974069200968628L;
	public Window contentpopup;


	/**
	 * 
	 * @throws SuspendNotAllowedException
	 * @throws InterruptedException
	 */
	public void createContent() throws SuspendNotAllowedException, InterruptedException
	{
		contentpopup = (Window) Executions.createComponents("../cms/form.zul", this, null);
		contentpopup.getFellow("savebutton").setVisible(true);
		contentpopup.setTitle("New media content");
		contentpopup.setVisible(true);
		contentpopup.doModal();
	}


	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void modifyContent(String id) throws Exception
	{
		MediaContent mcontent = this.getContentByID(id);
		contentpopup = (Window) Executions.createComponents("../cms/form.zul", this, null);
		contentpopup.getFellow("updatebutton").setVisible(true);
		((Textbox) contentpopup.getFellow("content_id")).setValue(mcontent.getID());
		((Textbox) contentpopup.getFellow("content_title")).setValue(mcontent.getTitle());
		((Textbox) contentpopup.getFellow("content_url")).setValue(mcontent.getUrl());
		((Textbox) contentpopup.getFellow("content_text")).setValue(mcontent.getText());
		String category = mcontent.getCategory();
		int selectedindex = 0;
		if (category.equals("education"))
		{
			selectedindex = 0;
		}
		else if (category.equals("games"))
		{
			selectedindex = 1;
		}
		else if (category.equals("entertainment"))
		{
			selectedindex = 2;
		}
		((Listbox) contentpopup.getFellow("content_cat")).setSelectedIndex(selectedindex);
		((Checkbox) contentpopup.getFellow("content_enabled")).setChecked(mcontent.isEnabled());
		contentpopup.setTitle("Modify media content");
		contentpopup.setVisible(true);
		contentpopup.doModal();
	}
	
	
	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void viewContent(String id) throws Exception
	{
		MediaContent mcontent = this.getContentByID(id);
		contentpopup = (Window) Executions.createComponents("../cms/form.zul", this, null);
		contentpopup.getFellow("updatebutton").setVisible(false);
		((Textbox) contentpopup.getFellow("content_id")).setValue(mcontent.getID());
		((Textbox) contentpopup.getFellow("content_title")).setValue(mcontent.getTitle());
		((Textbox) contentpopup.getFellow("content_title")).setReadonly(true);
		((Textbox) contentpopup.getFellow("content_url")).setValue(mcontent.getUrl());
		((Textbox) contentpopup.getFellow("content_url")).setReadonly(true);
		((Textbox) contentpopup.getFellow("content_text")).setValue(mcontent.getText());
		((Textbox) contentpopup.getFellow("content_text")).setReadonly(true);
		String category = mcontent.getCategory();
		int selectedindex = 0;
		if (category.equals("education"))
		{
			selectedindex = 0;
		}
		else if (category.equals("games"))
		{
			selectedindex = 1;
		}
		else if (category.equals("entertainment"))
		{
			selectedindex = 2;
		}
		((Listbox) contentpopup.getFellow("content_cat")).setSelectedIndex(selectedindex);
		((Listbox) contentpopup.getFellow("content_cat")).setDisabled(true);
		((Checkbox) contentpopup.getFellow("content_enabled")).setChecked(mcontent.isEnabled());
		((Checkbox) contentpopup.getFellow("content_enabled")).setDisabled(true);
		contentpopup.setTitle("Selected media content");
		contentpopup.setVisible(true);
		contentpopup.doModal();
	}


	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void disableContent(String id) throws Exception
	{
		this.processEnableOperations(id, false);
	}


	/**
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void enableContent(String id) throws Exception
	{
		this.processEnableOperations(id, true);
	}


	/**
	 * 
	 * @param id
	 * @param enabled
	 * @throws Exception
	 */
	private void processEnableOperations(String id, boolean enabled) throws Exception
	{
		MediaContent mcontent = this.getContentByID(id);
		mcontent.setEnabled(enabled);
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		proxy.updateMediaContent(mcontent, userid);
		
		Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
		Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
		comp.setSrc(null);
		comp.setSrc("../cms/index_content.zul");
	}


	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private MediaContent getContentByID(String id) throws Exception
	{
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		SearchCriteria scri = new SearchCriteria("ID", new SystemParameter(SystemDictionary.COMPARE_EQ, ""), id, "");
		try
		{
			MediaContent mcontent[] = proxy.getMediaContentArr(new SearchCriteria[] { scri }, userid);
			if (mcontent.length == 1)
			{
				return mcontent[0];
			}
			throw new Exception("More items than expected");
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	
}
