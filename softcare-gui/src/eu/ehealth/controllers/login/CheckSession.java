package eu.ehealth.controllers.login;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import eu.ehealth.SystemDictionary;



/**
 * 
 * @author a572832
 *
 */
public class CheckSession implements org.zkoss.zk.ui.util.Initiator
{

	
	@Override
	public void doInit(Page page, Map args) throws Exception
	{
		SystemDictionary.webguiLog("DEBUG", "Checking session...");
		try {
			Session currentSession = Executions.getCurrent().getDesktop().getSession();
			String user = (String)currentSession.getAttribute("user");
			if ((user == null) || (user.length() == 0))
			{		
				SystemDictionary.webguiLog("DEBUG", "Session expired!!!");
				Execution exec = Executions.getCurrent();
			    HttpServletResponse response = (HttpServletResponse)exec.getNativeResponse();
			    response.sendRedirect(response.encodeRedirectURL("/login.zul?error=" + eu.ehealth.ErrorDictionary.EXPIRED_SESSION));
			    exec.setVoided(true); //no need to create UI since redirect will take place
				//Executions.sendRedirect("/login.zul?error=" + eu.aladdin_project.ErrorDictionary.EXPIRED_SESSION);
			}
			else 
			{
				SystemDictionary.webguiLog("DEBUG", "Session not expired");
			}
		}
		catch (Exception ex) 
		{
			SystemDictionary.webguiLog("ERROR", "Session expired? : " + ex.getMessage());
			Executions.sendRedirect("/login.zul?error=" + eu.ehealth.ErrorDictionary.EXPIRED_SESSION);
		}
	}
	
/*
	@Override
	public void doAfterCompose(Page page) throws Exception { }
	

	@Override
	public boolean doCatch(Throwable ex) throws Exception
	{
		return false;
	}

	
	@Override
	public void doFinally() throws Exception { }
	*/

}
