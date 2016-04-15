package eu.ehealth.controllers.cms;

import java.util.Collection;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listbox;
import eu.ehealth.SystemDictionary;
import eu.ehealth.utilities.ComponentsFinder;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.MediaContent;


/**
 * 
 * 
 * @author 
 * @date 22/04/2014.
 *
 */
public class ContentManagerPopup extends Window
{


	private static final long serialVersionUID = -3943905769559638872L;
	private String id;
	private String title;
	private String url;
	private String type;
	private String category;
	private String text;
	private boolean enabled;


	/**
	 * 
	 */
	public void saveContent()
	{
		this.processContent(false);
	}


	/**
	 * 
	 */
	public void updateContent()
	{
		this.processContent(true);
	}


	/**
	 * 
	 * @param hasid
	 */
	private void processContent(boolean hasid)
	{
		this.id = hasid ? ((Textbox) getFellow("content_id")).getValue() : "";
		this.title = ((Textbox) getFellow("content_title")).getValue();
		this.url = ((Textbox) getFellow("content_url")).getValue();
		this.type = "Media Content";
		this.category = (String) ((Listbox) getFellow("content_cat")).getSelectedItem().getValue();
		this.text = ((Textbox) getFellow("content_text")).getValue();
		this.enabled = ((Checkbox) getFellow("content_enabled")).isChecked();
		
		MediaContent mcontent = new MediaContent(this.id, this.title, this.url, this.type, this.category, this.text, this.enabled);
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		try
		{
			String userid = (String) Sessions.getCurrent().getAttribute("userid");
			if (hasid)
			{
				proxy.updateMediaContent(mcontent, userid);
			}
			else
			{
				proxy.addMediaContent(mcontent, userid);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			Collection<Component> col = Executions.getCurrent().getDesktop().getComponents();
			Include comp = (Include) ComponentsFinder.getUIComponent(col, "app_content");
			comp.setSrc(null);
			comp.setSrc("../cms/index_content.zul");
			
			this.setVisible(false);
			this.getParent().removeChild(this);
		}
	}
	
	
}
