package eu.ehealth.db.wservices.mediacontent.dbfunctions;

import org.hibernate.Session;
import eu.ehealth.db.xsd.MediaContent;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class FMediaContent
{

	
	/**
	 * Instance of the session
	 */
	private Session session;


	/**
	 * 
	 * @param s
	 */
	public FMediaContent(Session s)
	{
		session = s;
	}
	
	
	/**
	 * Store MediaContent
	 * 
	 * @param xMediaContent data for store
	 * @param id id if exist
	 * @return id of the stored data
	 */
	public Integer importMediaContent(MediaContent xMediaContent, Integer id)
	{
		eu.ehealth.db.db.EntertainmentContent dEntertainmentContent = new eu.ehealth.db.db.EntertainmentContent();
		dEntertainmentContent.setCategory(xMediaContent.getCategory());
		dEntertainmentContent.setText(xMediaContent.getText());
		dEntertainmentContent.setTitle(xMediaContent.getTitle());
		dEntertainmentContent.setType(xMediaContent.getType());
		dEntertainmentContent.setUrl(xMediaContent.getUrl());
		dEntertainmentContent.setEnabled(xMediaContent.isEnabled());
		if (id != null && id > 0)
		{
			dEntertainmentContent.setId(id);
			session.merge(dEntertainmentContent);
		}
		else
		{
			dEntertainmentContent.setId(null);
			session.save(dEntertainmentContent);
		}

		return dEntertainmentContent.getId();
	}
	
	
}
