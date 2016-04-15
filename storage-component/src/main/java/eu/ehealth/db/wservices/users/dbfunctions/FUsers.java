package eu.ehealth.db.wservices.users.dbfunctions;

import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import org.hibernate.Session;
import eu.ehealth.db.xsd.Address;
import eu.ehealth.db.xsd.AddressList;
import eu.ehealth.db.xsd.Administrator;
import eu.ehealth.db.xsd.Carer;
import eu.ehealth.db.xsd.Clinician;
import eu.ehealth.db.xsd.Communication;
import eu.ehealth.db.xsd.CommunicationList;
import eu.ehealth.db.xsd.Consulter;
import eu.ehealth.db.xsd.GeneralPractitioner;
import eu.ehealth.db.xsd.Identifier;
import eu.ehealth.db.xsd.IdentifierList;
import eu.ehealth.db.xsd.Patient;
import eu.ehealth.db.xsd.PersonData;
import eu.ehealth.db.xsd.SocialWorker;
import eu.ehealth.db.xsd.SocioDemographicData;
import eu.ehealth.db.xsd.SystemParameter;


/**
 * 
 * 
 * @author a572832
 * @date 19/03/2014.
 *
 */
public class FUsers
{

	
	/**
	 * Instance of the session
	 */
	private Session session;


	/**
	 * 
	 * @param s
	 */
	public FUsers(Session s)
	{
		session = s;
	}
	
	
	/**
	 * Export patient into XSD-Schema format
	 * 
	 * @param dPatient data from DB
	 * @return XSD conform
	 */
	public Patient exportPatient(eu.ehealth.db.db.Patient dPatient)
	{
		Patient xPatient = new Patient();
		xPatient.setID(dPatient.getId().toString());
		xPatient.setPersonData(exportPersonData(dPatient.getM_PersonData()));
		xPatient.setSDData(exportSocioDemographicData(dPatient.getM_SocioDemographicData()));
		xPatient.setResponsibleClinicianID(dPatient.getClinician().toString());

		Consulter xConsulter = new Consulter();
		xConsulter.setName(dPatient.getCcname());
		xConsulter.setEmail(dPatient.getCcemail());
		xConsulter.setPhone(dPatient.getCcphone());
		xPatient.setConsulterInCharge(xConsulter);

		SocialWorker xSocialWorker = new SocialWorker();
		xSocialWorker.setName(dPatient.getSwname());
		xSocialWorker.setEmail(dPatient.getSwemail());
		xSocialWorker.setPhone(dPatient.getSwphone());
		xPatient.setSocialWorker(xSocialWorker);

		GeneralPractitioner xGeneralPractitioner = new GeneralPractitioner();
		xGeneralPractitioner.setName(dPatient.getGpname());
		xGeneralPractitioner.setEmail(dPatient.getGpemail());
		xGeneralPractitioner.setPhone(dPatient.getGpphone());
		xPatient.setGeneralPractitioner(xGeneralPractitioner);

		xPatient.setPatientCarer(
				exportCarer((eu.ehealth.db.db.Carer) this.session.load(eu.ehealth.db.db.Carer.class, dPatient.getCarer())));

		return xPatient;
	}
	
	
	/**
	 * Export clinician
	 * 
	 * @param dClinician DB object for export
	 * @return XSD conform
	 */
	public Clinician exportClinician(eu.ehealth.db.db.Clinician dClinician)
	{
		Clinician xClinician = new Clinician();
		xClinician.setID(dClinician.getId().toString());
		xClinician.setPersonData(exportPersonData(dClinician.getM_PersonData()));
		return xClinician;
	}


	/**
	 * Export carer into XSD-Schema format
	 * 
	 * @param dCarer data from DB
	 * @return XSD conform
	 */
	public Carer exportCarer(eu.ehealth.db.db.Carer dCarer)
	{
		Carer xCarer = new Carer();
		xCarer.setID(dCarer.getId().toString());
		xCarer.setPersonData(exportPersonData(dCarer.getM_PersonData()));
		xCarer.setSDData(exportSocioDemographicData(dCarer.getM_SocioDemographicData()));
		return xCarer;
	}


	/**
	 * Export Administrator
	 * 
	 * @param dAdministrator DB object for export
	 * @return XSD conform
	 */
	public Administrator exportAdministrator(eu.ehealth.db.db.Administrator dAdministrator)
	{
		Administrator xAdministrator = new Administrator();
		xAdministrator.setID(dAdministrator.getId().toString());
		xAdministrator.setPersonData(exportPersonData(dAdministrator.getM_PersonData()));
		return xAdministrator;
	}


	/**
	 * Check if the user exist
	 * 
	 * @param username
	 * @param id
	 * @return true if exist
	 */
	public Integer existUser(String username, Integer id)
	{
		if (session.createSQLQuery("SELECT * FROM aladdinuser WHERE username like '"
						+ username + "' AND id != '" + id.toString() + "'").list().size() > 0)
			return 1;
		
		return 0;
	}
	
	
	/**
	 * Export SocioDemographicData into XSD-Schema format
	 * 
	 * @param dSD data from DB
	 * @return XSD conform
	 */
	private SocioDemographicData exportSocioDemographicData(eu.ehealth.db.db.SocioDemographicData dSD)
	{
		SocioDemographicData xSD = new SocioDemographicData();

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(dSD.getBirthday());
		try
		{
			xSD.setBirthday(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
		}
		catch (Exception ex) {}

		SystemParameter gender = new SystemParameter();
		gender.setCode(dSD.getGender().toString());
		xSD.setGender(gender);

		SystemParameter maritalStatus = new SystemParameter();
		maritalStatus.setCode(dSD.getMaritalStatus().toString());
		xSD.setMaritalStatus(maritalStatus);
		
		xSD.setChildren(dSD.getChildren().shortValue());

		SystemParameter livingWith = new SystemParameter();
		livingWith.setCode(dSD.getLivingWith().toString());
		xSD.setLivingWith(livingWith);
		
		SystemParameter height = new SystemParameter();
		height.setCode(dSD.getHeight().toString());
		xSD.setHeight(height);
		
		return xSD;
	}


	/**
	 * Export PersonData into XSD-Schema format
	 * 
	 * @param dPersonData data from DB
	 * @return XSD conform
	 */
	private PersonData exportPersonData(eu.ehealth.db.db.PersonData dPersonData)
	{
		PersonData xPersonData = new PersonData();
		xPersonData.setSurname(dPersonData.getSurname());
		xPersonData.setName(dPersonData.getName());

		Object[] id = dPersonData.getIdentifiers().toArray();
		IdentifierList idl = new IdentifierList();
		for (int i = 0; i < id.length; i++)
		{
			exportIdentifier((eu.ehealth.db.db.Identifier) id[i], idl);
		}
		xPersonData.setIdentifierList(idl);

		Object[] ad = dPersonData.getAddresss().toArray();
		AddressList adl = new AddressList();
		for (int i = 0; i < ad.length; i++)
		{
			exportAddress((eu.ehealth.db.db.Address) ad[i], adl);
		}
		xPersonData.setAddressList(adl);

		Object[] cm = dPersonData.getCommunications().toArray();
		CommunicationList cml = new CommunicationList();
		for (int i = 0; i < cm.length; i++)
		{
			exportCommunication((eu.ehealth.db.db.Communication) cm[i], cml);
		}
		xPersonData.setCommunicationList(cml);

		return xPersonData;
	}
	
	
	/**
	 * Export Address
	 * 
	 * @param dAddress data for export
	 * @param xAddressList place to export
	 */
	private void exportAddress(eu.ehealth.db.db.Address dAddress, AddressList xAddressList)
	{
		Address rad = new Address();
		rad.setStreet(dAddress.getStreet());
		rad.setStreetNo(dAddress.getStreetNo());
		rad.setCity(dAddress.getCity());
		rad.setCountry(dAddress.getCountry());
		rad.setCounty(dAddress.getCounty());
		rad.setZipCode(dAddress.getZipCode());
		rad.setNotes(dAddress.getNotes());
		rad.setIsPrimary(dAddress.getIsPrimary());

		xAddressList.getAddress().add(rad);
	}


	/**
	 * Export communication
	 * 
	 * @param dCommunication data for export
	 * @param xCommunicationList place to export
	 */
	private void exportCommunication(eu.ehealth.db.db.Communication dCommunication, CommunicationList xCommunicationList)
	{
		Communication rcm = new Communication();
		rcm.setType(dCommunication.getType());
		rcm.setValue(dCommunication.getValue());
		rcm.setNotes(dCommunication.getNotes());
		rcm.setIsPrimary(dCommunication.getIsPrimary());

		xCommunicationList.getCommunication().add(rcm);
	}
	
	
	/**
	 * Export Identifier
	 * 
	 * @param dIdentifier data for export
	 * @param xIdentifierList place to export
	 */
	private void exportIdentifier(eu.ehealth.db.db.Identifier dIdentifier, IdentifierList xIdentifierList)
	{
		Identifier rid = new Identifier();
		rid.setType(dIdentifier.getType());
		rid.setNumber(dIdentifier.getNumber());
		GregorianCalendar c = new GregorianCalendar();
		c.setTimeInMillis(dIdentifier.getIssueDate().getTime());
		try
		{
			rid.setIssueDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
		}
		catch (Exception ex) { }
		
		rid.setIssueAuthority(dIdentifier.getIssueAuthority());
		xIdentifierList.getIdentifier().add(rid);
	}
	
	
}
