package eu.ehealth.db.wservices.common;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.SystemParameter;
import eu.ehealth.util.LocaleException;


/**
 * 
 * @author a572832
 *
 */
public class Translations
{

	
	/**
	 * Instance of the session
	 */
	protected Session _session;
	
	
	/**
	 * Constructor
	 * @param session
	 * @param dbHelper
	 */
	public Translations(Session session)
	{
		_session = session;
	}
	
	
	/**
	 * 
	 * @param entity
	 * @param entityId
	 * @param locale
	 * @param def
	 * @return
	 */
	public String getTranslate(String entity, Integer entityId, SystemParameter locale, String def) {
		if (locale == null)
			return def;
		return getTranslate(entity, entityId, locale.getCode(), def);
	}
	
	
	/**
	 * 
	 * @param entity
	 * @param entityId
	 * @param locale
	 * @param def
	 * @return
	 */
	public String getTranslate(String entity, Integer entityId, String locale, String def) 
	{
		if (locale != null && locale.length() > 0) {
			StorageComponentMain.scLog("DEBUG", "METHOD : Translations (1)");
			final Query query = 
					_session.createQuery("select t from Translate t where t.locale = :locale AND t.entity = :entity AND t.entityid = :entityid");
			query.setInteger("locale", getLocaleId(locale));
			query.setString("entity", entity);
			query.setInteger("entityid", entityId);
			query.setCacheable(true);
			query.setCacheRegion(null);

			List<?> data = query.list();
			if (data.size() == 1) 
			{
				eu.ehealth.db.db.Translate trans = (eu.ehealth.db.db.Translate) data.get(0);
				return trans.getValue();
			}
		}
		return def;
	}
	
	
	/**
	 * 
	 * @param locale
	 * @return
	 */
	public Integer getLocaleId(String locale) 
	{
		StorageComponentMain.scLog("DEBUG", "METHOD : Translations (2)");
		final Query query = _session.createQuery("select l from Locale l where name = :name");
		query.setString("name", locale);
		query.setCacheable(true);
		query.setCacheRegion(null);
		List<?> data = query.list();
		
		if (data.size() == 1) {
			return ((eu.ehealth.db.db.Locale) data.get(0)).getId();
		}

		eu.ehealth.db.db.Locale l = new eu.ehealth.db.db.Locale();
		l.setName(locale);
		_session.save(l);
		return l.getId();
	}
	
	
	/**
	 * Save translated value for the object
	 * 
	 * @param entity entity name
	 * @param entityId if of the entity
	 * @param locale
	 * @param def default value
	 * @return true if ok
	 * @throws LocaleException
	 */
	public boolean setTranslate(String entity, Integer entityId, SystemParameter locale, String value) throws LocaleException
	{
		if (locale == null)
			return false;
		return setTranslate(entity, entityId, locale.getCode(), value);
	}


	/**
	 * Save translated value for the object
	 * 
	 * @param entity entity name
	 * @param entityId if of the entity
	 * @param locale
	 * @param def default value
	 * @return true if ok
	 * @throws LocaleException
	 */
	private boolean setTranslate(String entity, Integer entityId, String locale, String value) throws LocaleException
	{
		if (locale != null && locale.length() > 0)
		{
			StorageComponentMain.scLog("DEBUG", "METHOD : Translations (3)");
			String sql = "SELECT t.id, t.value FROM translate as t INNER JOIN locale as l ON (l.id = t.locale) WHERE l.name = '"
					+ locale
					+ "' AND entity = '"
					+ entity
					+ "' AND entityid = " + entityId.toString();
			Object[] trans = _session.createSQLQuery(sql).list().toArray();

			Integer localeId = getLocaleId(locale);
			if (localeId == 0)
				return false;

			eu.ehealth.db.db.Translate t = new eu.ehealth.db.db.Translate();
			t.setValue(value);
			t.setLocale(localeId);
			t.setEntity(entity);
			t.setEntityid(entityId);
			if (trans != null && trans.length > 0)
			{
				t.setId((Integer) ((Object[]) trans[0])[0]);
				_session.merge(t);
			}
			else
			{
				_session.save(t);
			}
			return t.getId() > 0;
		}
		return false;
	}


	
	
	
}
