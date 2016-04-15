package eu.ehealth;

import javax.jws.WebService;


/**
 * 
 * @author a572832
 *
 */
@WebService
public interface ServicesComponent
{

	
	/**
	 * 
	 * @param title
	 * @param txt
	 * @param sendTo
	 */
	public void sendEmail(String subject, String txt, String patientId);
	
	
	/**
	 * 
	 * @param txt
	 * @param sendTo
	 */
	public void sendSMS(String txt, String sendTo);
	
	
	/**
	 * 
	 */
	public void sendVideo();
	
	
	/**
	 * 
	 * @param id
	 */
	public void getVideo(String id);
	
	
}
