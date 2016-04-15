package eu.ehealth;

import javax.jws.WebService;


/**
 * 
 * @author a572832
 *
 */
@WebService(endpointInterface = "eu.ehealth.ServicesComponent")
public class ServicesComponentImpl implements ServicesComponent
{
	
	
	@Override
	public void sendSMS(String txt, String sendTo)
	{
		// TODO Auto-generated method stub
	}
	

	@Override
	public void sendVideo()
	{
		// TODO Auto-generated method stub
	}

	
	@Override
	public void getVideo(String id)
	{
		// TODO Auto-generated method stub
	}


	@Override
	public void sendEmail(String subject, String txt, String patientId)
	{
		// TODO Auto-generated method stub
		
	}
	

}
