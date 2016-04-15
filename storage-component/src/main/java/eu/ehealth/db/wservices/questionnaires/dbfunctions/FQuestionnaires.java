package eu.ehealth.db.wservices.questionnaires.dbfunctions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javax.xml.datatype.DatatypeFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.wservices.common.Translations;
import eu.ehealth.db.xsd.Questionnaire;
import eu.ehealth.db.xsd.QuestionnaireAnswer;
import eu.ehealth.db.xsd.QuestionnaireAnswers;
import eu.ehealth.db.xsd.QuestionnaireQuestion;
import eu.ehealth.db.xsd.QuestionnaireQuestionAnswer;
import eu.ehealth.db.xsd.QuestionnaireQuestionAnswerList;
import eu.ehealth.db.xsd.QuestionnaireQuestionList;
import eu.ehealth.db.xsd.SystemParameter;
import eu.ehealth.util.LocaleException;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class FQuestionnaires
{

	
	/**
	 * Instance of the session
	 */
	private Session session;


	/**
	 * 
	 * @param s
	 */
	public FQuestionnaires(Session s)
	{
		session = s;
	}
	
	
	/**
	 * Store Questionnaire
	 * 
	 * @param xQuestionnaire questionnaire for storing
	 * @param locale
	 * @return DB object
	 * @throws HibernateException
	 * @throws LocaleException
	 */
	public eu.ehealth.db.db.Questionnaire importQuestionnaire(Questionnaire xQuestionnaire, SystemParameter locale)
			throws HibernateException, LocaleException
	{
		if (xQuestionnaire.getID() != null)
		{
			try
			{
				@SuppressWarnings("unused")
				Integer id = new Integer(xQuestionnaire.getID());
			}
			catch (NumberFormatException e)
			{
				return null;
			}
			catch (Exception e)
			{
				return null;
			}
		}

		eu.ehealth.db.db.Questionnaire dQuestionnaire = new eu.ehealth.db.db.Questionnaire();

		dQuestionnaire.setVersion(new BigDecimal(xQuestionnaire.getVersion()));
		if (xQuestionnaire.getID() != null)
		{
			try
			{
				dQuestionnaire.setId(new Integer(xQuestionnaire.getID()));
			}
			catch (Exception e)
			{
				StorageComponentMain.logException(e);
				dQuestionnaire = null;
				return null;
			}
		}
		session.saveOrUpdate(dQuestionnaire);

		Translations languageTrans = new Translations(session);
		if (!languageTrans.setTranslate("questionnaire", dQuestionnaire.getId(), locale, xQuestionnaire.getTitle()))
		{
			dQuestionnaire.setTitle(xQuestionnaire.getTitle());
			session.saveOrUpdate(dQuestionnaire);
		}

		QuestionnaireQuestion[] xQuestionnaireQuestions = xQuestionnaire
				.getQuestion().toArray(new QuestionnaireQuestion[xQuestionnaire.getQuestion().size()]);

		for (int i = 0; i < xQuestionnaireQuestions.length; i++)
		{
			updateQuestionnaireQuestion(xQuestionnaireQuestions[i], dQuestionnaire.getId(), null, locale);
		}
		return dQuestionnaire;
	}
	
	
	/**
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param objectId
	 * @return
	 */
	public List<QuestionnaireAnswers> getQuestionnaireAnswers(GregorianCalendar fromDate, GregorianCalendar toDate, Integer objectId)
	{
		List<QuestionnaireAnswers> lRes = new ArrayList<QuestionnaireAnswers>();

		String sql = "SELECT qa.timestamp, qq.quest, qa.objectid, qa.userid FROM questionnaireanswer qa inner join questionnairequestion qq on (qq.id = qa.question) WHERE qa.objectid = '"
				+ objectId.toString()
				+ "' AND qa.timestamp BETWEEN '"
				+ fromDate.toString()
				+ "' AND '"
				+ toDate.toString()
				+ "' GROUP BY qa.timestamp, qq.quest, qa.objectid, qa.userid";

		Object[] questionids = session.createSQLQuery(sql).list().toArray();

		for (int i = 0; i < questionids.length; i++)
		{
			Object[] q = (Object[]) questionids[i];
			TimeZone zone = TimeZone.getDefault();
			GregorianCalendar cal = new GregorianCalendar(zone);
			Timestamp timestamp = (Timestamp) q[0];
			cal.setTimeInMillis(timestamp.getTime());

			// work around
			GregorianCalendar before = new GregorianCalendar();
			before.setTimeInMillis(timestamp.getTime());
			GregorianCalendar after = new GregorianCalendar();
			after.setTimeInMillis(timestamp.getTime() + 1000);

			Integer question = (Integer) q[1];
			sql = "SELECT id FROM questionnaireanswer WHERE objectid = '" + objectId.toString();
			sql += "' AND timestamp BETWEEN '" + before.getTime().toString();
			sql += "' AND '" + after.getTime().toString();
			sql += "' AND question in (select id from questionnairequestion where quest = " + question.toString() + ")";

			Object[] lqa = session.createSQLQuery(sql).list().toArray();

			QuestionnaireAnswers rqas = new QuestionnaireAnswers();
			try
			{
				rqas.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
			}
			catch (Exception ex) {}
			
			rqas.setObjectID(((Integer) q[2]).toString());
			rqas.setUserID(((Integer) q[3]).toString());

			String sqlTask = "SELECT id FROM task WHERE datetimefulfilled = '" + before.getTime().toString() + "'";
			Object[] lt = session.createSQLQuery(sqlTask).list().toArray();
			if (lt.length > 0)
			{
				rqas.setTaskID(((Integer) lt[0]).toString());
			}

			for (int j = 0; j < lqa.length; j++)
			{
				QuestionnaireAnswer rqa = new QuestionnaireAnswer();
				eu.ehealth.db.db.QuestionnaireAnswer qa = (eu.ehealth.db.db.QuestionnaireAnswer) session
						.load(eu.ehealth.db.db.QuestionnaireAnswer.class, (Integer) lqa[j]);
				rqa.setQuestionID(qa.getQuestion().toString());
				rqa.setValue(qa.getValue());

				rqas.setObjectID(qa.getObjectId().toString());
				rqas.setUserID(qa.getUserId().toString());

				eu.ehealth.db.db.QuestionnaireQuestion qq = (eu.ehealth.db.db.QuestionnaireQuestion) session
						.load(eu.ehealth.db.db.QuestionnaireQuestion.class, qa.getQuestion());
				rqa.setGlobalID(qq.getGlobalId().toString());

				rqas.getAnswer().add(rqa);
			}

			lRes.add(rqas);
		}

		return lRes;
	}


	/**
	 * Delete QuestionnaireQuestion
	 * 
	 * @param id of the QuestionnaireQuestion
	 */
	public void dropQuestionnaireQuestion(Integer id)
	{
		Object[] qq = session.createSQLQuery("SELECT id FROM questionnairequestion WHERE parentid = " + id.toString()).list().toArray();
		for (int i = 0; i < qq.length; i++)
		{
			dropQuestionnaireQuestion((Integer) qq[i]);
		}
		
		session.createSQLQuery("DELETE FROM questionnairequestionanswer WHERE question = "
				+ id.toString()).executeUpdate();
		session.createSQLQuery("DELETE FROM questionnaireanswer WHERE question = "
				+ id.toString()).executeUpdate();
		session.createSQLQuery("DELETE FROM questionnairequestion WHERE id = "
				+ id.toString()).executeUpdate();
	}
	
	
	/**
	 * Export Questionnaire
	 * 
	 * @param dQuestionnaire DB object for export
	 * @param locale
	 * @return XSD conform
	 */
	public Questionnaire exportQuestionnaire(eu.ehealth.db.db.Questionnaire dQuestionnaire, SystemParameter locale)
	{
		Questionnaire xQuestionnaire = new Questionnaire();
		xQuestionnaire.setID(dQuestionnaire.getId().toString());

		Translations languageTrans = new Translations(session);
		xQuestionnaire.setTitle(languageTrans.getTranslate("questionnaire", dQuestionnaire.getId(), locale, dQuestionnaire.getTitle()));

		xQuestionnaire.setVersion(dQuestionnaire.getVersion().doubleValue());

		final Query query = session.createQuery("select qq from QuestionnaireQuestion qq where qq.quest = :quest AND qq.parentid is null");
		query.setInteger("quest", dQuestionnaire.getId());
		query.setCacheable(true);
		query.setCacheRegion(null);
		List<?> qql = query.list();

		for (int i = 0; i < qql.size(); i++)
		{
			xQuestionnaire
					.getQuestion()
					.add(exportQuestionnaireQuestion((eu.ehealth.db.db.QuestionnaireQuestion) qql.get(i), true, locale));
		}

		return xQuestionnaire;
	}
	
	
	/**
	 * Export QuestionnaireQuestion
	 * 
	 * @param dQuestionnaireQuestion DB object for export
	 * @param level1
	 * @param locale
	 * @return XSD conform
	 */
	private QuestionnaireQuestion exportQuestionnaireQuestion(
			eu.ehealth.db.db.QuestionnaireQuestion dQuestionnaireQuestion, boolean level1, SystemParameter locale)
	{
		QuestionnaireQuestion xQuestionnaireQuestion = new QuestionnaireQuestion();

		xQuestionnaireQuestion.setType(dQuestionnaireQuestion.getType());
		xQuestionnaireQuestion.setId(dQuestionnaireQuestion.getId().toString());
		xQuestionnaireQuestion.setGlobalID(dQuestionnaireQuestion.getGlobalId());
		xQuestionnaireQuestion.setPosition(dQuestionnaireQuestion.getPosition());

		if (!level1)
		{
			xQuestionnaireQuestion.setCondition(dQuestionnaireQuestion.getCondition().shortValue());
		}

		Translations languageTrans = new Translations(session);
		xQuestionnaireQuestion.setTitle(languageTrans.getTranslate("questionnairequestion", dQuestionnaireQuestion.getId(), locale, ""));

		final Query q2 = session
				.createQuery("select qqa from QuestionnaireQuestionAnswer qqa where deleted = :deleted and question = :question");
		q2.setInteger("question", dQuestionnaireQuestion.getId());
		q2.setBoolean("deleted", false);
		q2.setCacheable(true);
		q2.setCacheRegion(null);
		List<?> qqal = q2.list();

		QuestionnaireQuestionAnswerList la = new QuestionnaireQuestionAnswerList();

		for (int i = 0; i < qqal.size(); i++)
		{
			la.getAnswer()
					.add(exportQuestionnaireQuestionAnswer((eu.ehealth.db.db.QuestionnaireQuestionAnswer) qqal.get(i), locale));
		}
		xQuestionnaireQuestion.setAnswers(la);

		final Query q3 = session.createQuery("select qq from QuestionnaireQuestion qq where parentid = :parentid");
		q3.setInteger("parentid", dQuestionnaireQuestion.getId());
		q3.setCacheable(true);
		q3.setCacheRegion(null);
		List<?> qql = q3.list();

		QuestionnaireQuestionList lq = new QuestionnaireQuestionList();

		for (int i = 0; i < qql.size(); i++)
		{
			lq.getQuestion()
					.add(exportQuestionnaireQuestion((eu.ehealth.db.db.QuestionnaireQuestion) qql.get(i), false, locale));
		}
		xQuestionnaireQuestion.setQuestions(lq);

		return xQuestionnaireQuestion;
	}


	/**
	 * Export QuestionnaireQuestionAnswer
	 * 
	 * @param dQuestionnaireQuestionAnswer DB object for export
	 * @param locale
	 * @return XSD conform
	 */
	private QuestionnaireQuestionAnswer exportQuestionnaireQuestionAnswer(
			eu.ehealth.db.db.QuestionnaireQuestionAnswer dQuestionnaireQuestionAnswer, SystemParameter locale)
	{
		QuestionnaireQuestionAnswer xQuestionnaireQuestionAnswer = new QuestionnaireQuestionAnswer();

		Translations languageTrans = new Translations(session);
		xQuestionnaireQuestionAnswer
				.setDescription(languageTrans.getTranslate("questionnairequestionanswer", dQuestionnaireQuestionAnswer.getId(), locale, ""));
		
		if (dQuestionnaireQuestionAnswer.getPosition() != null)
			xQuestionnaireQuestionAnswer.setPosition(dQuestionnaireQuestionAnswer.getPosition());

		xQuestionnaireQuestionAnswer.setValue(dQuestionnaireQuestionAnswer.getValue().shortValue());

		return xQuestionnaireQuestionAnswer;
	}
	
	
	/**
	 * Update QuestionnaireQuestion
	 * 
	 * @param xQuestionnaireQuestion QuestionnaireQuestion
	 * @param questionnaireId questionnaire id
	 * @param parentId id of the parent question
	 * @param locale
	 * @throws HibernateException
	 * @throws LocaleException
	 */
	private void updateQuestionnaireQuestion(QuestionnaireQuestion xQuestionnaireQuestion, int questionnaireId,
			Integer parentId, SystemParameter locale) throws HibernateException, LocaleException
	{
		eu.ehealth.db.db.QuestionnaireQuestion dQuestionnaireQuestion = new eu.ehealth.db.db.QuestionnaireQuestion();
		dQuestionnaireQuestion.setType(xQuestionnaireQuestion.getType());
		StorageComponentMain.scLog("DEBUG", "updateQuestionnaireQuestion : param 1 :   " + dQuestionnaireQuestion.getType());
		try
		{
			dQuestionnaireQuestion.setId(new Integer(xQuestionnaireQuestion.getId()));
			StorageComponentMain.scLog("DEBUG", "updateQuestionnaireQuestion : param 2 :   " + dQuestionnaireQuestion.getId());
		}
		catch (Exception e)
		{
			StorageComponentMain.scLog("ERROR", "updateQuestionnaireQuestion : xQuestionnaireQuestion.getId() is not a valid number : " + e.getMessage());
			//dQuestionnaireQuestion.setId(null);
		}
		
		dQuestionnaireQuestion.setCondition(new Integer(xQuestionnaireQuestion.getCondition()));
		dQuestionnaireQuestion.setPosition(xQuestionnaireQuestion.getPosition());
		dQuestionnaireQuestion.setParentid(parentId);
		dQuestionnaireQuestion.setQuest(questionnaireId);
		dQuestionnaireQuestion.setGlobalId(xQuestionnaireQuestion.getGlobalID());
		dQuestionnaireQuestion.setTitle(xQuestionnaireQuestion.getTitle());
		dQuestionnaireQuestion.setIsPrimary(false);
		dQuestionnaireQuestion.setDeleted(false);
		
		// insert into QuestionnaireQuestion (type, isPrimary, parentid, title, quest, condition, deleted, position, GlobalId) 
		// 							  values (?, ?, ?, ?, ?, ?, ?, ?, ?)
		session.saveOrUpdate(dQuestionnaireQuestion);

		Translations languageTrans = new Translations(session);
		if (!languageTrans.setTranslate("questionnairequestion", dQuestionnaireQuestion.getId(), locale, xQuestionnaireQuestion.getTitle()))
		{
			dQuestionnaireQuestion.setTitle(xQuestionnaireQuestion.getTitle());
			session.saveOrUpdate(dQuestionnaireQuestion);
		}

		if (xQuestionnaireQuestion.getQuestions() != null && xQuestionnaireQuestion.getQuestions().getQuestion() != null)
		{
			for (int i = 0; i < xQuestionnaireQuestion.getQuestions().getQuestion().size(); i++)
			{
				updateQuestionnaireQuestion(xQuestionnaireQuestion
						.getQuestions().getQuestion().get(i), questionnaireId, dQuestionnaireQuestion.getId(), locale);
			}
		}

		if (xQuestionnaireQuestion.getAnswers() != null && xQuestionnaireQuestion.getAnswers().getAnswer() != null)
		{
			QuestionnaireQuestionAnswer rqqa = null;
			List<Integer> qqaId = new ArrayList<Integer>();

			for (int i = 0; i < xQuestionnaireQuestion.getAnswers().getAnswer().size(); i++)
			{
				rqqa = xQuestionnaireQuestion.getAnswers().getAnswer().get(i);

				String sql = "SELECT id FROM questionnairequestionanswer WHERE question = '"
						+ dQuestionnaireQuestion.getId().toString()
						+ "' AND value = '"
						+ new Integer(rqqa.getValue()).toString() + "'";
				List<?> _id = session.createSQLQuery(sql).list();

				eu.ehealth.db.db.QuestionnaireQuestionAnswer qqa = null;

				if (_id.size() > 0)
				{
					qqa = (eu.ehealth.db.db.QuestionnaireQuestionAnswer) session
							.load(eu.ehealth.db.db.QuestionnaireQuestionAnswer.class, (Integer) _id.get(0));
					qqa.setQuestion(dQuestionnaireQuestion.getId());
					qqa.setValue(new Integer(rqqa.getValue()));
					qqa.setDeleted(false);
					qqa.setPosition(rqqa.getPosition());
					session.merge(qqa);
				}
				else
				{
					qqa = new eu.ehealth.db.db.QuestionnaireQuestionAnswer();
					qqa.setValue(new Integer(rqqa.getValue()));
					qqa.setQuestion(dQuestionnaireQuestion.getId());
					qqa.setPosition(rqqa.getPosition());
					qqa.setDeleted(false);
					session.saveOrUpdate(qqa);
				}

				qqaId.add(qqa.getId());

				Translations languageTrans1 = new Translations(session);
				if (!languageTrans1.setTranslate("questionnairequestionanswer", qqa.getId(), locale, rqqa.getDescription()))
				{
					qqa.setDescription(rqqa.getDescription());
					session.saveOrUpdate(dQuestionnaireQuestion);
				}
			}
		}
	}
	
	
}
