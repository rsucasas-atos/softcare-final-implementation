package eu.ehealth.controllers.details.assessment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import eu.ehealth.ws_client.xsd.PatientAssessment;


/**
 * This class is just a front end for the PatientAssessment class. It is needed
 * to show the information in a proper way. This customization was not developed
 * on top of the original class due to the often WSDL/XSD updates.
 * 
 * @author Xavi Sarda (Atos Origin)
 */
public class AssessmentInfo
{


	private PatientAssessment assessment;
	private String dateOfAssessment;


	/**
	 * This constructor saves the PatientAssessment and generates a human
	 * readable string for the dateOfAssessment attribute.
	 * 
	 * @param assessment PatientAssessment to be saved
	 */
	public AssessmentInfo(PatientAssessment assessment)
	{
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		this.assessment = assessment;
		
		Calendar calendar = assessment.getDateOfAssessment().toGregorianCalendar();
		this.dateOfAssessment = sdf.format(calendar.getTime());
	}


	public void setAssessment(PatientAssessment assessment)
	{
		this.assessment = assessment;
	}


	public void setDateOfAssessment(String dateOfAssessment)
	{
		this.dateOfAssessment = dateOfAssessment;
	}


	public PatientAssessment getAssessment()
	{
		return assessment;
	}


	public String getDateOfAssessment()
	{
		return dateOfAssessment;
	}


	public java.lang.String getID()
	{
		return this.assessment.getID();
	}


	public java.lang.String getPatientID()
	{
		return this.assessment.getPatientID();
	}


	public eu.ehealth.ws_client.xsd.SystemParameter getAetology()
	{
		return this.assessment.getAetology();
	}


	public Short getTimeEllapsedSinceDiagnosed()
	{
		return this.assessment.getTimeEllapsedSinceDiagnosed();
	}


	public Short getSeverity()
	{
		return this.assessment.getSeverity();
	}


	public java.lang.String getRelevantPathologyAntecedents()
	{
		return this.assessment.getRelevantPathologyAntecedents();
	}


	public java.lang.String getComorbidity()
	{
		return this.assessment.getComorbidity();
	}


	public Short getCharlsonComorbidityIndex()
	{
		return this.assessment.getCharlsonComorbidityIndex();
	}


	public Short getBarthelIndex()
	{
		return this.assessment.getBarthelIndex();
	}


	public Short getLawtonIndex()
	{
		return this.assessment.getLawtonIndex();
	}


	public Short getMMSE()
	{
		return this.assessment.getMMSE();
	}


	public Short getMDRS()
	{
		return this.assessment.getMDRS();
	}


	public java.lang.Double getBlessedScalePart1()
	{
		return this.assessment.getBlessedScalePart1();
	}


	public Short getBlessedScalePart2()
	{
		return this.assessment.getBlessedScalePart2();
	}


	public Short getBlessedScalePart3()
	{
		return this.assessment.getBlessedScalePart3();
	}


	public Short getChecklistMBP()
	{
		return this.assessment.getChecklistMBP();
	}


	public Short getNPQI_Severity()
	{
		return this.assessment.getNPQISeverity();
	}


	public Short getNPQI_Stress()
	{
		return this.assessment.getNPQIStress();
	}


	public Short getGDS()
	{
		return this.assessment.getGDS();
	}


	public java.lang.Boolean getFalls()
	{
		return this.assessment.isFalls();
	}


	public java.lang.Boolean getIncontinence()
	{
		return this.assessment.isIncontinence();
	}


	public java.lang.Boolean getDelirium()
	{
		return this.assessment.isDelirium();
	}


	public java.lang.Boolean getImmobility()
	{
		return this.assessment.isImmobility();
	}


	public java.lang.Boolean getSensorialDeficits()
	{
		return this.assessment.isSensorialDeficits();
	}


	public java.lang.String getPharmacologicalTreatment()
	{
		return this.assessment.getPharmacologicalTreatment();
	}


	public eu.ehealth.ws_client.xsd.Measurement[] getClinicalData()
	{
		try {
			return this.assessment.getClinicalData().toArray(
					new eu.ehealth.ws_client.xsd.Measurement[this.assessment.getClinicalData().size()]);
		}
		catch (Exception ex) {
			return new eu.ehealth.ws_client.xsd.Measurement[0];
		}
	}


	public eu.ehealth.ws_client.xsd.Measurement getClinicalData(int i)
	{
		try {
			return this.assessment.getClinicalData().get(i);
		}
		catch (Exception ex) {
			return new eu.ehealth.ws_client.xsd.Measurement();
		}
	}

}
