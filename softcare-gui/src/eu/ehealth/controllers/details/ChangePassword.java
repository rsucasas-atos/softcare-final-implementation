package eu.ehealth.controllers.details;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import eu.ehealth.SystemDictionary;
import eu.ehealth.Utilities;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.OperationResult;
import eu.ehealth.ws_client.xsd.User;


/**
 * 
 * @author a572832
 *
 */
public class ChangePassword extends Window
{


	private static final long serialVersionUID = 1203601720297000762L;
	private String uid;
	private User user;


	/**
	 * 
	 * @param pwd
	 */
	public void changePassword(String pwd)
	{
		String userid = (String) Sessions.getCurrent().getAttribute("userid");
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		try
		{
			OperationResult ores = proxy.changePassword(this.uid, pwd, userid);
			
			if (Utilities.getIntValue(ores.getCode(), 0) > 0) {
				Messagebox.show("#TXT# Password changed succesfully", "#TXT# Change Password", Messagebox.OK, Messagebox.INFORMATION);
			}
			else {
				Messagebox.show("#TXT# " + ores.getDescription(), "#TXT# Change Password", Messagebox.OK, Messagebox.EXCLAMATION);
			}

			this.setVisible(false);
			this.getParent().removeChild(this);
		}
		catch (Exception e)
		{
			SystemDictionary.logException(e);
			Messagebox.show("#TXT# Error : " + e.getMessage(), "#TXT# Change Password", Messagebox.OK, Messagebox.ERROR);
		}
	}


	/**
	 * 
	 * @param uid
	 */
	public void setuserid(String uid)
	{
		this.uid = uid;
		StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		this.user = proxy.getUser(uid);
		((Textbox) this.getFellow("uname_show")).setValue(this.user.getUsername());
	}

	
}
