package eu.ehealth.controllers.login;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;
import eu.ehealth.Exceptions;
import eu.ehealth.SystemDictionary;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.OperationResult;


/**
 * 
 * @author a572832
 *
 */
public class LoginFormController extends GenericForwardComposer 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4591301793633284048L;
	

	Textbox login_user;
    Textbox login_password;
    
    
    /**
     * 
     */
    public void onOK() {
    	StorageComponentImpl proxy = SystemDictionary.getSCProxy();
		String pageRedirect = "/";
		
		try
		{
			OperationResult result = proxy.auth(login_user.getValue(), login_password.getValue(), "");
			
			SystemDictionary.webguiLog("DEBUG", "LOGIN REQUEST CODE: " + result.getCode());
			SystemDictionary.webguiLog("DEBUG", "LOGIN REQUEST DESC: " + result.getDescription());
			SystemDictionary.webguiLog("DEBUG", "LOGIN REQUEST STATUS: " + result.getStatus());
			
			/*
			 * result.getCode():
			 * 
			 * 	> 0 ... all is ok, it's a valid user
			 * 
			 * 	others:
			 *		INVALID_USER 		("0", 	"Invalid user - incorrect password"),
			 *		DATABASE_ERROR_1 	("-2", 	"Database error (-2)"),
			 *		DATABASE_ERROR_2 	("-4", 	"Database error (-4)"),
			 *    	USER_LOCKED 		("-10", "User locked"),
			 *   	INVALID_PASSWORD 	("-11", "Invalid password"),
			 *   	UNKNOWN_ERROR 		("-12", "Unknown error while processing an action"),
			 *    	RESPONSE_OK 		("-99", "Operation executed succesfully");
		     */
			
			// Check if getCode() function return error code (0)
			if (result.getCode().equals("0"))
			{
				throw new Exceptions.InvalidCredentialsException();
			}
			else if (Integer.parseInt(result.getCode()) < 0) 
			{
				throw new Exceptions.InvalidResponseCodeException(result.getCode(), result.getDescription());
			}

			// TODO Retrieve user info from SC (there is no data in SC sample to implement this)
			OperationResult restype = proxy.getUserType(result.getCode());
			String usertype = null;
			if (restype.getCode().equals(SystemDictionary.USERTYPE_ADMIN))
			{
				SystemDictionary.webguiLog("INFO", "Administrator logged in");
				usertype = SystemDictionary.USERTYPE_ADMIN;
			}
			else if (restype.getCode().equals(SystemDictionary.USERTYPE_CLINICIAN))
			{
				SystemDictionary.webguiLog("INFO", "Clinician logged in");
				usertype = SystemDictionary.USERTYPE_CLINICIAN;
			}
			else
			{
				// TODO Return not allowed user type
				throw new Exceptions.InvalidUserTypeException();
			}
			String adminlbl = SystemDictionary.USERTYPE_ADMIN;
			Boolean isadmin = adminlbl.equals(usertype);
			session.setAttribute("user", login_user.getValue());
			session.setAttribute("userid", result.getCode());
			session.setAttribute("usertype", usertype);
			session.setAttribute("isadmin", isadmin);

			pageRedirect = "/index.zul";
		}
		catch(Exceptions.InvalidUserTypeException ue)
		{
			pageRedirect = "/login.zul?error=" + eu.ehealth.ErrorDictionary.USERTYPE_NOT_ALLOWED;	
		}
		catch(Exceptions.InvalidCredentialsException ce)
		{
			pageRedirect = "/login.zul?error=" + eu.ehealth.ErrorDictionary.INVALID_CREDENTIALS;
		}
		catch(Exceptions.InvalidResponseCodeException re)
		{
			/*
			 *		DATABASE_ERROR_1 	("-2", 	"Database error (-2)"),
			 *		DATABASE_ERROR_2 	("-4", 	"Database error (-4)"),
			 *    	USER_LOCKED 		("-10", "User locked"),
			 *   	INVALID_PASSWORD 	("-11", "Invalid password"),
			 *   	UNKNOWN_ERROR 		("-12", "Unknown error while processing an action"),
			 *    	RESPONSE_OK 		("-99", "Operation executed succesfully");
		     */
			pageRedirect = "/login.zul?errorCode=CODE " + re.getExCode() + "&errorDesc=" + re.getExDesc();
		}
		catch(Exception e)
		{
			pageRedirect = "/login.zul?error=" + eu.ehealth.ErrorDictionary.UNKOW_ERROR;
		}
		finally
		{
			Executions.getCurrent().sendRedirect(pageRedirect);
		}
    }
	

}