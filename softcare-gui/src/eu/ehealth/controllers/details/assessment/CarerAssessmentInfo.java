package eu.ehealth.controllers.details.assessment;

import java.text.SimpleDateFormat;
import eu.ehealth.ws_client.xsd.CarerAssessment;


/**
 * This class is just a front end for the CarerAssessment class. It is needed to
 * show the information in a proper way. This customization was not developed on
 * top of the original class due to the often WSDL/XSD updates.
 * 
 * @author Xavi Sarda (Atos Origin)
 */
public class CarerAssessmentInfo
{


	private CarerAssessment assessment;
	private String dateOfAssessment;


	/**
	 * This constructor saves the CarerAssessment and generates a human readable
	 * string for the dateOfAssessment attribute.
	 * 
	 * @param assessment PatientAssessment to be saved
	 */
	public CarerAssessmentInfo(CarerAssessment assessment)
	{
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		this.assessment = assessment;
		this.dateOfAssessment = sdf.format(assessment.getDateOfAssessmentCalendar().getTime());
	}


	public void setAssessment(CarerAssessment assessment)
	{
		this.assessment = assessment;
	}


	public void setDateOfAssessment(String dateOfAssessment)
	{
		this.dateOfAssessment = dateOfAssessment;
	}


	public CarerAssessment getAssessment()
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


	public java.lang.String getCarerID()
	{
		return this.assessment.getCarerID();
	}


	public java.lang.String getClinicianID()
	{
		return this.assessment.getClinicianID();
	}


	public java.lang.String getRelevantHealthProblem()
	{
		return this.assessment.getRelevantHealthProblem();
	}


	public Short getSeverityOfBurden()
	{
		return this.assessment.getSeverityOfBurden();
	}


	public java.lang.String getEmotionalOrMentalDisorders()
	{
		return this.assessment.getEmotionalOrMentalDisorders();
	}


	public java.lang.String getPsychoactiveDrugs()
	{
		return this.assessment.getPsychoactiveDrugs();
	}


	public Short getQualityOfLife()
	{
		return this.assessment.getQualityOfLife();
	}

}
