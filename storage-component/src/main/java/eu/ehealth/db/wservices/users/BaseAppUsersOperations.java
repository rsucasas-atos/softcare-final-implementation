package eu.ehealth.db.wservices.users;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import org.hibernate.Session;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.xsd.Address;
import eu.ehealth.db.xsd.Communication;
import eu.ehealth.db.xsd.Identifier;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.PersonData;
import eu.ehealth.db.xsd.SocioDemographicData;


/**
 * 
 * @author a572832
 *
 */
public abstract class BaseAppUsersOperations extends DbStorageComponent<OperationResult, Object>
{

	
	/**
	 * 
	 * @param session
	 */
	public BaseAppUsersOperations(Session session)
	{
		super(session);
	}

	
	@Override
	protected abstract OperationResult dbStorageFunction(ArrayList<Object> lo);
	
	
	/**
	 * Store persondata object
	 * 
	 * @param xPersonData persondata
	 * @param id id if exist
	 * @return id of the stored data
	 */
	protected Integer importPersondata(PersonData xPersonData, Integer id)
	{
		eu.ehealth.db.db.PersonData dPersonData = new eu.ehealth.db.db.PersonData();
		dPersonData.setName(xPersonData.getName());
		dPersonData.setSurname(xPersonData.getSurname());
		if (id != null && id > 0)
		{
			dPersonData.setId(id);
			_session.merge(dPersonData);
		}
		else
		{
			_session.save(dPersonData);
		}

		Integer pdid = dPersonData.getId();

		_session.createSQLQuery("DELETE FROM address WHERE persondata = " + dPersonData.getId().toString()).executeUpdate();
		_session.createSQLQuery("DELETE FROM identifier WHERE persondata = " + dPersonData.getId().toString()).executeUpdate();
		_session.createSQLQuery("DELETE FROM communication WHERE persondata = " + dPersonData.getId().toString()).executeUpdate();

		Address[] rad = xPersonData
				.getAddressList().getAddress().toArray(new Address[xPersonData.getAddressList().getAddress().size()]);
		for (int i = 0; i < rad.length; i++)
		{
			importAddress(rad[i], pdid);
		}

		Identifier[] rid = xPersonData
				.getIdentifierList().getIdentifier().toArray(new Identifier[xPersonData.getIdentifierList().getIdentifier().size()]);
		for (int i = 0; i < rid.length; i++)
		{
			importIdentifier(rid[i], pdid);
		}

		Communication[] rcm = xPersonData.getCommunicationList().getCommunication()
				.toArray(new Communication[xPersonData.getCommunicationList().getCommunication().size()]);
		for (int i = 0; i < rcm.length; i++)
		{
			importCommunication(rcm[i], pdid);
		}

		return dPersonData.getId();
	}
	
	
	/**
	 * Store socioDemographicData
	 * 
	 * @param xSD socioDemographicData
	 * @param id id of the data if exist
	 * @return if of the stored data
	 */
	protected Integer importSocioDemographic(SocioDemographicData xSD, Integer id)
	{
		eu.ehealth.db.db.SocioDemographicData dSD = new eu.ehealth.db.db.SocioDemographicData();

		dSD.setBirthday(new Timestamp(xSD.getBirthday().toGregorianCalendar().getTimeInMillis()));

		if (xSD.getGender() != null && xSD.getGender().getCode() != null && !xSD.getGender().getCode().isEmpty())
			dSD.setGender(new Integer(xSD.getGender().getCode()));
		else
			dSD.setGender(0);
		
		if (xSD.getHeight() != null && xSD.getHeight().getCode() != null && !xSD.getHeight().getCode().isEmpty())
			dSD.setHeight(new Integer(xSD.getHeight().getCode()));
		else
			dSD.setHeight(0);

		if (xSD.getMaritalStatus() != null && xSD.getMaritalStatus().getCode() != null && !xSD.getMaritalStatus().getCode().isEmpty())
			dSD.setMaritalStatus(new Integer(xSD.getMaritalStatus().getCode()));
		else
			dSD.setMaritalStatus(0);

		dSD.setChildren(new Integer(xSD.getChildren()));

		if (xSD.getLivingWith() != null && xSD.getLivingWith().getCode() != null && !xSD.getLivingWith().getCode().isEmpty())
			dSD.setLivingWith(new Integer(xSD.getLivingWith().getCode()));
		else
			dSD.setLivingWith(0);

		if (id != null && id > 0)
		{
			dSD.setId(id);
			_session.merge(dSD);
		}
		else
		{
			_session.save(dSD);
		}

		return dSD.getId();
	}
	
	
	/**
	 * Store address
	 * 
	 * @param xAddress address
	 * @param personDataId id of the persondata
	 */
	private void importAddress(Address xAddress, Integer personDataId)
	{
		eu.ehealth.db.db.Address dAddress = new eu.ehealth.db.db.Address();
		dAddress.setPersondata(personDataId);
		dAddress.setCity(xAddress.getCity());
		dAddress.setCountry(xAddress.getCountry());
		dAddress.setCounty(xAddress.getCounty());
		dAddress.setNotes(xAddress.getNotes());
		dAddress.setStreet(xAddress.getStreet());
		dAddress.setStreetNo(xAddress.getStreetNo());
		dAddress.setZipCode(xAddress.getZipCode());
		dAddress.setIsPrimary(xAddress.getIsPrimary());
		_session.save(dAddress);
	}
	
	
	/**
	 * Store identifier object
	 * 
	 * @param xIdentifier identifier
	 * @param personDataId id of the persondata
	 */
	private void importIdentifier(Identifier xIdentifier, Integer personDataId)
	{
		eu.ehealth.db.db.Identifier dIdentifier = new eu.ehealth.db.db.Identifier();
		dIdentifier.setPersondata(personDataId);
		dIdentifier.setType(xIdentifier.getType());
		dIdentifier.setNumber(xIdentifier.getNumber());

		Calendar issueDate = xIdentifier.getIssueDate().toGregorianCalendar();
		long timeInMillis = 0;
		if (issueDate != null)
			timeInMillis = issueDate.getTimeInMillis();
		dIdentifier.setIssueDate(new Timestamp(timeInMillis));
		
		dIdentifier.setIssueAuthority(xIdentifier.getIssueAuthority());
		_session.save(dIdentifier);
	}


	/**
	 * Store communication object
	 * 
	 * @param xCommunication communication
	 * @param personDataId id of the persondata
	 */
	private void importCommunication(Communication xCommunication, Integer personDataId)
	{
		eu.ehealth.db.db.Communication dCommunication = new eu.ehealth.db.db.Communication();
		dCommunication.setPersondata(personDataId);
		dCommunication.setType(xCommunication.getType());
		dCommunication.setValue(xCommunication.getValue());
		dCommunication.setNotes(xCommunication.getNotes());
		dCommunication.setIsPrimary(xCommunication.getIsPrimary());
		_session.save(dCommunication);
	}
	

}
