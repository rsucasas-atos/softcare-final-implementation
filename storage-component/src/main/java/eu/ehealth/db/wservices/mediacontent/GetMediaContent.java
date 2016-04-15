package eu.ehealth.db.wservices.mediacontent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.MediaContent;
import eu.ehealth.db.xsd.SearchCriteria;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class GetMediaContent extends DbStorageComponent<List<MediaContent>, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public GetMediaContent(Session session)
	{
		super(session);
	}

	
	@Override
	protected List<MediaContent> dbStorageFunction(ArrayList<Object> lo)
	{
		List<MediaContent> l = new ArrayList<MediaContent>();
		try
		{
			List<SearchCriteria> filter = (List<SearchCriteria>) lo.get(0);
			String userId = (String) lo.get(1);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : GetMediaContent");

			try
			{
				Field[] field = eu.ehealth.db.db.EntertainmentContent.class.getDeclaredFields();
				String sql = "SELECT id FROM entertainmentcontent WHERE ";

				SearchCriteria[] sc = filter.toArray(new SearchCriteria[filter.size()]);
				for (int i = 0; i < sc.length; i++)
				{
					for (int j = 0; j < field.length; j++)
					{
						if (field[j].getName().compareToIgnoreCase(sc[i].getFieldName()) == 0)
						{
							Integer opnum = new Integer(sc[i].getCompareOp().getCode());
							sql += String.format(operationsMap.get(opnum), sc[i].getFieldName(), 
										sc[i].getFieldValue1(), sc[i] .getFieldValue2());
							sql += " AND ";
						}
					}
				}

				sql += "1 = 1";

				Object[] list = _session.createSQLQuery(sql).list().toArray();
				for (int i = 0; i < list.length; i++)
				{
					Integer id = (Integer) list[i];
					eu.ehealth.db.db.EntertainmentContent ec = (eu.ehealth.db.db.EntertainmentContent) _session
							.load(eu.ehealth.db.db.EntertainmentContent.class, id);
					MediaContent out = new MediaContent();

					out.setID(ec.getId().toString());
					out.setCategory(ec.getCategory());
					out.setText(ec.getText());
					out.setTitle(ec.getTitle());
					out.setType(ec.getType());
					out.setUrl(ec.getUrl());
					out.setEnabled(ec.getEnabled());

					l.add(out);
				}
			}
			catch (HibernateException e)
			{
				StorageComponentMain.logException(e);
			}

			return l;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);

			return l;
		}
	}
	

}
