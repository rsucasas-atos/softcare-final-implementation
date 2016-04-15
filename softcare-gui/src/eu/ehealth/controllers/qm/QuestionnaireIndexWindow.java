package eu.ehealth.controllers.qm;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;
import eu.ehealth.SystemDictionary;
import eu.ehealth.ws_client.StorageComponentImpl;
import eu.ehealth.ws_client.xsd.OperationResult;


/**
 * 
 * @author 
 *
 */
public class QuestionnaireIndexWindow extends Window
{


	private static final long serialVersionUID = 1967142868699449476L;


	/**
	 * Function to remove Questionnaires from questionnaire manager index page.
	 * 
	 * @param questid Questionnaire id to delete it
	 */
	public void removeQuestionnaire(String questid)
	{
		String redirectURL = "";
		
		OperationResult result = null;
		String userId = (String) Sessions.getCurrent().getAttribute("userid");
		StorageComponentImpl proxy = new StorageComponentImpl();
		try
		{
			result = proxy.deleteQuestionnaire(questid, userId);
			if (result != null) {
				if (result.getCode().equals("-2")) {
					if (result.getDescription().indexOf("ConstraintViolationException") != -1) {
						redirectURL = "/qm/index.zul?error=" + eu.ehealth.ErrorDictionary.CONSTRAINT_VIOLATION;
					}
					else {
						redirectURL = "/qm/index.zul?error=" + eu.ehealth.ErrorDictionary.UNKOW_ERROR;
					}
				}
			}
		}
		catch (Exception re)
		{

		}
		finally
		{
			if (result != null)
			{
				SystemDictionary.webguiLog("DEBUG", "Delete Questionnaire RESULT = "
								+ result.getCode() + ":"
								+ result.getDescription());
			}
			else
			{
				SystemDictionary.webguiLog("WARN", "Delete Questionnaire not executed");
			}
			Executions.getCurrent().sendRedirect(redirectURL);
		}
	}


	/**
	 * Simple redirection to update a Questionnaire
	 * 
	 * @param questid Questionnaire id to update it
	 */
	public void updateQuestionnaire(String questid)
	{
		Executions.getCurrent().sendRedirect("/qm/form.zul?q=" + questid);
	}


	public void translateQuestionnaire(String questid)
	{
		Executions.getCurrent()
				.sendRedirect("/qm/translation.zul?q=" + questid);
	}

}
