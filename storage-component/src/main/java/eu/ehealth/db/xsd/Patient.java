package eu.ehealth.db.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * The ALADDIN Patient
 * 
 * <p>
 * Clase Java para Patient complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Patient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PersonData" type="{http://aladdin-project.eu/xsd}PersonData"/>
 *         &lt;element name="SD_Data" type="{http://aladdin-project.eu/xsd}SocioDemographicData"/>
 *         &lt;element name="ResponsibleClinicianID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SocialWorker" type="{http://aladdin-project.eu/xsd}SocialWorker"/>
 *         &lt;element name="ConsulterInCharge" type="{http://aladdin-project.eu/xsd}Consulter"/>
 *         &lt;element name="GeneralPractitioner" type="{http://aladdin-project.eu/xsd}GeneralPractitioner"/>
 *         &lt;element name="PatientCarer" type="{http://aladdin-project.eu/xsd}Carer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Patient", propOrder = { "id", "personData", "sdData",
		"responsibleClinicianID", "socialWorker", "consulterInCharge",
		"generalPractitioner", "patientCarer" })
public class Patient
{


	@XmlElement(name = "ID", required = true)
	protected String id;
	@XmlElement(name = "PersonData", required = true)
	protected PersonData personData;
	@XmlElement(name = "SD_Data", required = true)
	protected SocioDemographicData sdData;
	@XmlElement(name = "ResponsibleClinicianID", required = true)
	protected String responsibleClinicianID;
	@XmlElement(name = "SocialWorker", required = true)
	protected SocialWorker socialWorker;
	@XmlElement(name = "ConsulterInCharge", required = true)
	protected Consulter consulterInCharge;
	@XmlElement(name = "GeneralPractitioner", required = true)
	protected GeneralPractitioner generalPractitioner;
	@XmlElement(name = "PatientCarer", required = true)
	protected Carer patientCarer;


	/**
	 * Obtiene el valor de la propiedad id.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getID()
	{
		return id;
	}


	/**
	 * Define el valor de la propiedad id.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setID(String value)
	{
		this.id = value;
	}


	/**
	 * Obtiene el valor de la propiedad personData.
	 * 
	 * @return possible object is {@link PersonData }
	 * 
	 */
	public PersonData getPersonData()
	{
		return personData;
	}


	/**
	 * Define el valor de la propiedad personData.
	 * 
	 * @param value allowed object is {@link PersonData }
	 * 
	 */
	public void setPersonData(PersonData value)
	{
		this.personData = value;
	}


	/**
	 * Obtiene el valor de la propiedad sdData.
	 * 
	 * @return possible object is {@link SocioDemographicData }
	 * 
	 */
	public SocioDemographicData getSDData()
	{
		return sdData;
	}


	/**
	 * Define el valor de la propiedad sdData.
	 * 
	 * @param value allowed object is {@link SocioDemographicData }
	 * 
	 */
	public void setSDData(SocioDemographicData value)
	{
		this.sdData = value;
	}


	/**
	 * Obtiene el valor de la propiedad responsibleClinicianID.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getResponsibleClinicianID()
	{
		return responsibleClinicianID;
	}


	/**
	 * Define el valor de la propiedad responsibleClinicianID.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setResponsibleClinicianID(String value)
	{
		this.responsibleClinicianID = value;
	}


	/**
	 * Obtiene el valor de la propiedad socialWorker.
	 * 
	 * @return possible object is {@link SocialWorker }
	 * 
	 */
	public SocialWorker getSocialWorker()
	{
		return socialWorker;
	}


	/**
	 * Define el valor de la propiedad socialWorker.
	 * 
	 * @param value allowed object is {@link SocialWorker }
	 * 
	 */
	public void setSocialWorker(SocialWorker value)
	{
		this.socialWorker = value;
	}


	/**
	 * Obtiene el valor de la propiedad consulterInCharge.
	 * 
	 * @return possible object is {@link Consulter }
	 * 
	 */
	public Consulter getConsulterInCharge()
	{
		return consulterInCharge;
	}


	/**
	 * Define el valor de la propiedad consulterInCharge.
	 * 
	 * @param value allowed object is {@link Consulter }
	 * 
	 */
	public void setConsulterInCharge(Consulter value)
	{
		this.consulterInCharge = value;
	}


	/**
	 * Obtiene el valor de la propiedad generalPractitioner.
	 * 
	 * @return possible object is {@link GeneralPractitioner }
	 * 
	 */
	public GeneralPractitioner getGeneralPractitioner()
	{
		return generalPractitioner;
	}


	/**
	 * Define el valor de la propiedad generalPractitioner.
	 * 
	 * @param value allowed object is {@link GeneralPractitioner }
	 * 
	 */
	public void setGeneralPractitioner(GeneralPractitioner value)
	{
		this.generalPractitioner = value;
	}


	/**
	 * Obtiene el valor de la propiedad patientCarer.
	 * 
	 * @return possible object is {@link Carer }
	 * 
	 */
	public Carer getPatientCarer()
	{
		return patientCarer;
	}


	/**
	 * Define el valor de la propiedad patientCarer.
	 * 
	 * @param value allowed object is {@link Carer }
	 * 
	 */
	public void setPatientCarer(Carer value)
	{
		this.patientCarer = value;
	}

}
