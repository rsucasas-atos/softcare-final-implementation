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
 * Clase Java para AddressList complex type.
 * 
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="AddressList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Address" type="{http://aladdin-project.eu/xsd}Address" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressList", propOrder = { "address" })
public class AddressList
{


	@XmlElement(name = "Address")
	protected List<Address> address;


	public AddressList()
	{
	}


	/**
	 * 
	 * @param address
	 */
	public AddressList(eu.ehealth.ws_client.xsd.Address[] address)
	{
		try {
			this.address = Arrays.asList(address);;
		}
		catch (Exception ex) {}
	}


	/**
	 * Gets the value of the address property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the address property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAddress().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Address }
	 * 
	 * 
	 */
	public List<Address> getAddress()
	{
		if (address == null)
		{
			address = new ArrayList<Address>();
		}
		return this.address;
	}

}
