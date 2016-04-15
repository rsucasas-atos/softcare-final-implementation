package eu.ehealth.controllers.details;

import java.util.ArrayList;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;
import eu.ehealth.ws_client.xsd.Address;
import eu.ehealth.ws_client.xsd.Carer;
import eu.ehealth.ws_client.xsd.Communication;
import eu.ehealth.ws_client.xsd.PersonData;
import eu.ehealth.ws_client.xsd.SocioDemographicData;


/**
 * 
 * @author a572832
 *
 */
public abstract class DetailPersonController extends Window
{


	private static final long serialVersionUID = -4506077343584761376L;
	protected String currentid = null;
	protected PersonData currentdata = null;
	protected SocioDemographicData currentsd = null;
	protected String currentresponsible = null;
	protected Carer currentcarers = null;


	public abstract Button[] createActionButtons();


	public abstract Listitem[] createDataRows();


	public DetailPersonController() { }


	/**
	 * 
	 * @param id
	 * @param data
	 * @param sddata
	 * @param responsible
	 * @param carers
	 */
	public void setControllerData(String id, PersonData data, SocioDemographicData sddata, String responsible, Carer carers)
	{
		this.setCurrentid(id);
		this.setCurrentdata(data);
		this.setCurrentresponsible(responsible);
		this.setCurrentsd(sddata);
		this.setCurrentcarers(carers);

		Listbox datarows = (Listbox) getFellow("datarows");
		Listitem[] datar = this.createDataRows();
		for (int i = 0; i < datar.length; i++)
		{
			datarows.appendChild(datar[i]);
		}
	}


	/**
	 * 
	 * @return
	 */
	protected Listitem[] getPersonDataListItems()
	{
		ArrayList<Listitem> listret = new ArrayList<Listitem>();

		Listitem lst1 = new Listitem();
		String text = Labels.getLabel("common.name");
		Listcell namecell = new Listcell(text);
		Listcell namecell2 = new Listcell(this.currentdata.getSurname() + ", " + this.currentdata.getName());
		lst1.appendChild(namecell);
		lst1.appendChild(namecell2);
		listret.add(lst1);

		Listitem lst2 = new Listitem();
		String text2 = Labels.getLabel("common.addresses");
		Listcell addtitle = new Listcell(text2);
		addtitle.setSpan(2);
		lst2.appendChild(addtitle);
		listret.add(lst2);

		if (this.currentdata.getAddressList() != null && this.currentdata.getAddressList().getAddress() != null
				&& this.currentdata.getAddressList().getAddress().size() > 0)
		{
			for (int i = 0; i < this.currentdata.getAddressList().getAddress().size(); i++)
			{
				Address addressdata = this.currentdata.getAddressList().getAddress().get(i);
				String addressstring = addressdata.getStreet() + " " + addressdata.getStreetNo() + ", " + addressdata.getCity();
				if (addressdata.getCounty() != null && !addressdata.getCounty().equals("(none)"))
				{
					addressstring += "(" + addressdata.getCounty() + ")";
				}
				addressstring += "\n" + addressdata.getZipCode() + " " + addressdata.getCountry();
				Listitem addresstext = new Listitem();
				addresstext.appendChild(new Listcell(""));
				addresstext.appendChild(new Listcell(addressstring));
				listret.add(addresstext);
			}
		}

		Listitem lst5 = new Listitem();
		String text5 = Labels.getLabel("common.communications");
		Listcell comtitle = new Listcell(text5);
		comtitle.setSpan(2);
		lst5.appendChild(comtitle);
		listret.add(lst5);

		if (this.currentdata.getCommunicationList() != null && this.currentdata.getCommunicationList().getCommunication() != null
				&& this.currentdata.getCommunicationList().getCommunication().size() > 0)
		{
			Communication[] commdata = this.currentdata.getCommunicationList()
					.getCommunication().toArray(new Communication[this.currentdata.getCommunicationList().getCommunication().size()]);
			for (int i = 0; i < commdata.length; i++)
			{
				Listitem lst6 = new Listitem();
				Listcell comcell = new Listcell("");
				Listcell comcell2 = new Listcell("(" + commdata[i].getType() + ") " + commdata[i].getValue());
				lst6.appendChild(comcell);
				lst6.appendChild(comcell2);
				listret.add(lst6);
			}
		}

		Listitem[] ret = new Listitem[listret.size()];
		for (int i = 0; i < listret.size(); i++)
		{
			ret[i] = listret.get(i);
		}
		return ret;
	}


	// GETTERS AND SETTERS

	public String getCurrentid()
	{
		return currentid;
	}


	protected void setCurrentid(String currentid)
	{
		this.currentid = currentid;
	}


	public PersonData getCurrentdata()
	{
		return currentdata;
	}


	protected void setCurrentdata(PersonData currentdata)
	{
		this.currentdata = currentdata;
	}


	public SocioDemographicData getCurrentsd()
	{
		return currentsd;
	}


	protected void setCurrentsd(SocioDemographicData currentsd)
	{
		this.currentsd = currentsd;
	}


	public String getCurrentresponsible()
	{
		return currentresponsible;
	}


	protected void setCurrentresponsible(String currentresponsible)
	{
		this.currentresponsible = currentresponsible;
	}


	public Carer getCurrentcarers()
	{
		return currentcarers;
	}


	protected void setCurrentcarers(Carer currentcarers)
	{
		this.currentcarers = currentcarers;
	}

	
}
