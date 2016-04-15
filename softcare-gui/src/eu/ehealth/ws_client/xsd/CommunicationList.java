package eu.ehealth.ws_client.xsd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>
 * Clase Java para CommunicationList complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CommunicationList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Communication" type="{http://aladdin-project.eu/xsd}Communication" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommunicationList", propOrder = { "communication" })
public class CommunicationList
{


	@XmlElement(name = "Communication")
	protected List<Communication> communication;


	public CommunicationList()
	{
	}


	public CommunicationList(
			eu.ehealth.ws_client.xsd.Communication[] communication)
	{
		try {
			this.communication = Arrays.asList(communication);;
		}
		catch (Exception ex) {}
	}


	/**
	 * Gets the value of the communication property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the communication property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getCommunication().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link Communication }
	 * 
	 * 
	 */
	public List<Communication> getCommunication()
	{
		if (communication == null)
		{
			communication = new ArrayList<Communication>();
		}
		return this.communication;
	}

}
