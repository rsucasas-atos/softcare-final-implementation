package eu.ehealth.db.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * A set of assessment scales that will be administered to the patient face to
 * face at baseline, after three months, after six months and at the end point
 * (after nine months). (Or whenever applicable)
 * 
 * <p>
 * Clase Java para PatientAssessment complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PatientAssessment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PatientID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DateOfAssessment" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Aetology" type="{http://aladdin-project.eu/xsd}SystemParameter"/>
 *         &lt;element name="TimeEllapsedSinceDiagnosed" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="Severity" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="RelevantPathologyAntecedents" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Comorbidity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CharlsonComorbidityIndex" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *         &lt;element name="BarthelIndex" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *         &lt;element name="LawtonIndex" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *         &lt;element name="MMSE" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="MDRS" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *         &lt;element name="BlessedScalePart1" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="BlessedScalePart2" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *         &lt;element name="BlessedScalePart3" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *         &lt;element name="ChecklistMBP" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *         &lt;element name="NPQI_Severity" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *         &lt;element name="NPQI_Stress" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *         &lt;element name="GDS" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *         &lt;element name="Falls" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Incontinence" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Delirium" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Immobility" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SensorialDeficits" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PharmacologicalTreatment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClinicalData" type="{http://aladdin-project.eu/xsd}Measurement" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientAssessment", propOrder = { "id", "patientID",
		"dateOfAssessment", "aetology", "timeEllapsedSinceDiagnosed",
		"severity", "relevantPathologyAntecedents", "comorbidity",
		"charlsonComorbidityIndex", "barthelIndex", "lawtonIndex", "mmse",
		"mdrs", "blessedScalePart1", "blessedScalePart2", "blessedScalePart3",
		"checklistMBP", "npqiSeverity", "npqiStress", "gds", "falls",
		"incontinence", "delirium", "immobility", "sensorialDeficits",
		"pharmacologicalTreatment", "clinicalData" })
public class PatientAssessment
{


	@XmlElement(name = "ID", required = true)
	protected String id;
	@XmlElement(name = "PatientID", required = true)
	protected String patientID;
	@XmlElement(name = "DateOfAssessment", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar dateOfAssessment;
	@XmlElement(name = "Aetology", required = true)
	protected SystemParameter aetology;
	@XmlElement(name = "TimeEllapsedSinceDiagnosed")
	@XmlSchemaType(name = "unsignedByte")
	protected short timeEllapsedSinceDiagnosed;
	@XmlElement(name = "Severity")
	@XmlSchemaType(name = "unsignedByte")
	protected short severity;
	@XmlElement(name = "RelevantPathologyAntecedents")
	protected String relevantPathologyAntecedents;
	@XmlElement(name = "Comorbidity")
	protected String comorbidity;
	@XmlElement(name = "CharlsonComorbidityIndex")
	@XmlSchemaType(name = "unsignedByte")
	protected Short charlsonComorbidityIndex;
	@XmlElement(name = "BarthelIndex")
	@XmlSchemaType(name = "unsignedByte")
	protected Short barthelIndex;
	@XmlElement(name = "LawtonIndex")
	@XmlSchemaType(name = "unsignedByte")
	protected Short lawtonIndex;
	@XmlElement(name = "MMSE")
	@XmlSchemaType(name = "unsignedByte")
	protected short mmse;
	@XmlElement(name = "MDRS")
	@XmlSchemaType(name = "unsignedByte")
	protected Short mdrs;
	@XmlElement(name = "BlessedScalePart1")
	protected Double blessedScalePart1;
	@XmlElement(name = "BlessedScalePart2")
	@XmlSchemaType(name = "unsignedByte")
	protected Short blessedScalePart2;
	@XmlElement(name = "BlessedScalePart3")
	@XmlSchemaType(name = "unsignedByte")
	protected Short blessedScalePart3;
	@XmlElement(name = "ChecklistMBP")
	@XmlSchemaType(name = "unsignedByte")
	protected Short checklistMBP;
	@XmlElement(name = "NPQI_Severity")
	@XmlSchemaType(name = "unsignedByte")
	protected Short npqiSeverity;
	@XmlElement(name = "NPQI_Stress")
	@XmlSchemaType(name = "unsignedByte")
	protected Short npqiStress;
	@XmlElement(name = "GDS")
	@XmlSchemaType(name = "unsignedByte")
	protected Short gds;
	@XmlElement(name = "Falls")
	protected Boolean falls;
	@XmlElement(name = "Incontinence")
	protected Boolean incontinence;
	@XmlElement(name = "Delirium")
	protected Boolean delirium;
	@XmlElement(name = "Immobility")
	protected Boolean immobility;
	@XmlElement(name = "SensorialDeficits")
	protected Boolean sensorialDeficits;
	@XmlElement(name = "PharmacologicalTreatment")
	protected String pharmacologicalTreatment;
	@XmlElement(name = "ClinicalData", required = true)
	protected List<Measurement> clinicalData;


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
	 * Obtiene el valor de la propiedad patientID.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPatientID()
	{
		return patientID;
	}


	/**
	 * Define el valor de la propiedad patientID.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setPatientID(String value)
	{
		this.patientID = value;
	}


	/**
	 * Obtiene el valor de la propiedad dateOfAssessment.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getDateOfAssessment()
	{
		return dateOfAssessment;
	}


	/**
	 * Define el valor de la propiedad dateOfAssessment.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setDateOfAssessment(XMLGregorianCalendar value)
	{
		this.dateOfAssessment = value;
	}


	/**
	 * Obtiene el valor de la propiedad aetology.
	 * 
	 * @return possible object is {@link SystemParameter }
	 * 
	 */
	public SystemParameter getAetology()
	{
		return aetology;
	}


	/**
	 * Define el valor de la propiedad aetology.
	 * 
	 * @param value allowed object is {@link SystemParameter }
	 * 
	 */
	public void setAetology(SystemParameter value)
	{
		this.aetology = value;
	}


	/**
	 * Obtiene el valor de la propiedad timeEllapsedSinceDiagnosed.
	 * 
	 */
	public short getTimeEllapsedSinceDiagnosed()
	{
		return timeEllapsedSinceDiagnosed;
	}


	/**
	 * Define el valor de la propiedad timeEllapsedSinceDiagnosed.
	 * 
	 */
	public void setTimeEllapsedSinceDiagnosed(short value)
	{
		this.timeEllapsedSinceDiagnosed = value;
	}


	/**
	 * Obtiene el valor de la propiedad severity.
	 * 
	 */
	public short getSeverity()
	{
		return severity;
	}


	/**
	 * Define el valor de la propiedad severity.
	 * 
	 */
	public void setSeverity(short value)
	{
		this.severity = value;
	}


	/**
	 * Obtiene el valor de la propiedad relevantPathologyAntecedents.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRelevantPathologyAntecedents()
	{
		return relevantPathologyAntecedents;
	}


	/**
	 * Define el valor de la propiedad relevantPathologyAntecedents.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setRelevantPathologyAntecedents(String value)
	{
		this.relevantPathologyAntecedents = value;
	}


	/**
	 * Obtiene el valor de la propiedad comorbidity.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getComorbidity()
	{
		return comorbidity;
	}


	/**
	 * Define el valor de la propiedad comorbidity.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setComorbidity(String value)
	{
		this.comorbidity = value;
	}


	/**
	 * Obtiene el valor de la propiedad charlsonComorbidityIndex.
	 * 
	 * @return possible object is {@link Short }
	 * 
	 */
	public Short getCharlsonComorbidityIndex()
	{
		if (charlsonComorbidityIndex == null)
			return 0;
		return charlsonComorbidityIndex;
	}


	/**
	 * Define el valor de la propiedad charlsonComorbidityIndex.
	 * 
	 * @param value allowed object is {@link Short }
	 * 
	 */
	public void setCharlsonComorbidityIndex(Short value)
	{
		this.charlsonComorbidityIndex = value;
	}


	/**
	 * Obtiene el valor de la propiedad barthelIndex.
	 * 
	 * @return possible object is {@link Short }
	 * 
	 */
	public Short getBarthelIndex()
	{
		if (barthelIndex == null)
			return 0;
		return barthelIndex;
	}


	/**
	 * Define el valor de la propiedad barthelIndex.
	 * 
	 * @param value allowed object is {@link Short }
	 * 
	 */
	public void setBarthelIndex(Short value)
	{
		this.barthelIndex = value;
	}


	/**
	 * Obtiene el valor de la propiedad lawtonIndex.
	 * 
	 * @return possible object is {@link Short }
	 * 
	 */
	public Short getLawtonIndex()
	{
		if (lawtonIndex == null)
			return 0;
		return lawtonIndex;
	}


	/**
	 * Define el valor de la propiedad lawtonIndex.
	 * 
	 * @param value allowed object is {@link Short }
	 * 
	 */
	public void setLawtonIndex(Short value)
	{
		this.lawtonIndex = value;
	}


	/**
	 * Obtiene el valor de la propiedad mmse.
	 * 
	 */
	public short getMMSE()
	{
		return mmse;
	}


	/**
	 * Define el valor de la propiedad mmse.
	 * 
	 */
	public void setMMSE(short value)
	{
		this.mmse = value;
	}


	/**
	 * Obtiene el valor de la propiedad mdrs.
	 * 
	 * @return possible object is {@link Short }
	 * 
	 */
	public Short getMDRS()
	{
		if (mdrs == null)
			return 0;
		return mdrs;
	}


	/**
	 * Define el valor de la propiedad mdrs.
	 * 
	 * @param value allowed object is {@link Short }
	 * 
	 */
	public void setMDRS(Short value)
	{
		this.mdrs = value;
	}


	/**
	 * Obtiene el valor de la propiedad blessedScalePart1.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getBlessedScalePart1()
	{
		return blessedScalePart1;
	}


	/**
	 * Define el valor de la propiedad blessedScalePart1.
	 * 
	 * @param value allowed object is {@link Double }
	 * 
	 */
	public void setBlessedScalePart1(Double value)
	{
		this.blessedScalePart1 = value;
	}


	/**
	 * Obtiene el valor de la propiedad blessedScalePart2.
	 * 
	 * @return possible object is {@link Short }
	 * 
	 */
	public Short getBlessedScalePart2()
	{
		if (blessedScalePart2 == null)
			return 0;
		return blessedScalePart2;
	}


	/**
	 * Define el valor de la propiedad blessedScalePart2.
	 * 
	 * @param value allowed object is {@link Short }
	 * 
	 */
	public void setBlessedScalePart2(Short value)
	{
		this.blessedScalePart2 = value;
	}


	/**
	 * Obtiene el valor de la propiedad blessedScalePart3.
	 * 
	 * @return possible object is {@link Short }
	 * 
	 */
	public Short getBlessedScalePart3()
	{
		if (blessedScalePart3 == null)
			return 0;
		return blessedScalePart3;
	}


	/**
	 * Define el valor de la propiedad blessedScalePart3.
	 * 
	 * @param value allowed object is {@link Short }
	 * 
	 */
	public void setBlessedScalePart3(Short value)
	{
		this.blessedScalePart3 = value;
	}


	/**
	 * Obtiene el valor de la propiedad checklistMBP.
	 * 
	 * @return possible object is {@link Short }
	 * 
	 */
	public Short getChecklistMBP()
	{
		if (checklistMBP == null)
			return 0;
		return checklistMBP;
	}


	/**
	 * Define el valor de la propiedad checklistMBP.
	 * 
	 * @param value allowed object is {@link Short }
	 * 
	 */
	public void setChecklistMBP(Short value)
	{
		this.checklistMBP = value;
	}


	/**
	 * Obtiene el valor de la propiedad npqiSeverity.
	 * 
	 * @return possible object is {@link Short }
	 * 
	 */
	public Short getNPQISeverity()
	{
		if (npqiSeverity == null)
			return 0;
		return npqiSeverity;
	}


	/**
	 * Define el valor de la propiedad npqiSeverity.
	 * 
	 * @param value allowed object is {@link Short }
	 * 
	 */
	public void setNPQISeverity(Short value)
	{
		this.npqiSeverity = value;
	}


	/**
	 * Obtiene el valor de la propiedad npqiStress.
	 * 
	 * @return possible object is {@link Short }
	 * 
	 */
	public Short getNPQIStress()
	{
		if (npqiStress == null)
			return 0;
		return npqiStress;
	}


	/**
	 * Define el valor de la propiedad npqiStress.
	 * 
	 * @param value allowed object is {@link Short }
	 * 
	 */
	public void setNPQIStress(Short value)
	{
		this.npqiStress = value;
	}


	/**
	 * Obtiene el valor de la propiedad gds.
	 * 
	 * @return possible object is {@link Short }
	 * 
	 */
	public Short getGDS()
	{
		if (gds == null)
			return 0;
		return gds;
	}


	/**
	 * Define el valor de la propiedad gds.
	 * 
	 * @param value allowed object is {@link Short }
	 * 
	 */
	public void setGDS(Short value)
	{
		this.gds = value;
	}


	/**
	 * Obtiene el valor de la propiedad falls.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isFalls()
	{
		return falls;
	}


	/**
	 * Define el valor de la propiedad falls.
	 * 
	 * @param value allowed object is {@link Boolean }
	 * 
	 */
	public void setFalls(Boolean value)
	{
		this.falls = value;
	}


	/**
	 * Obtiene el valor de la propiedad incontinence.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isIncontinence()
	{
		return incontinence;
	}


	/**
	 * Define el valor de la propiedad incontinence.
	 * 
	 * @param value allowed object is {@link Boolean }
	 * 
	 */
	public void setIncontinence(Boolean value)
	{
		this.incontinence = value;
	}


	/**
	 * Obtiene el valor de la propiedad delirium.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isDelirium()
	{
		return delirium;
	}


	/**
	 * Define el valor de la propiedad delirium.
	 * 
	 * @param value allowed object is {@link Boolean }
	 * 
	 */
	public void setDelirium(Boolean value)
	{
		this.delirium = value;
	}


	/**
	 * Obtiene el valor de la propiedad immobility.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isImmobility()
	{
		return immobility;
	}


	/**
	 * Define el valor de la propiedad immobility.
	 * 
	 * @param value allowed object is {@link Boolean }
	 * 
	 */
	public void setImmobility(Boolean value)
	{
		this.immobility = value;
	}


	/**
	 * Obtiene el valor de la propiedad sensorialDeficits.
	 * 
	 * @return possible object is {@link Boolean }
	 * 
	 */
	public Boolean isSensorialDeficits()
	{
		return sensorialDeficits;
	}


	/**
	 * Define el valor de la propiedad sensorialDeficits.
	 * 
	 * @param value allowed object is {@link Boolean }
	 * 
	 */
	public void setSensorialDeficits(Boolean value)
	{
		this.sensorialDeficits = value;
	}


	/**
	 * Obtiene el valor de la propiedad pharmacologicalTreatment.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPharmacologicalTreatment()
	{
		return pharmacologicalTreatment;
	}


	/**
	 * Define el valor de la propiedad pharmacologicalTreatment.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setPharmacologicalTreatment(String value)
	{
		this.pharmacologicalTreatment = value;
	}


	/**
	 * Gets the value of the clinicalData property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the clinicalData property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getClinicalData().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link Measurement }
	 * 
	 * 
	 */
	public List<Measurement> getClinicalData()
	{
		if (clinicalData == null)
		{
			clinicalData = new ArrayList<Measurement>();
		}
		return this.clinicalData;
	}

}
