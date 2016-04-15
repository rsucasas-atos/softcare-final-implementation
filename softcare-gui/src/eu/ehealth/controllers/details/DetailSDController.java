package eu.ehealth.controllers.details;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import eu.ehealth.SystemDictionary;
import eu.ehealth.ws_client.xsd.Carer;
import eu.ehealth.ws_client.xsd.PersonData;
import eu.ehealth.ws_client.xsd.SocioDemographicData;


/**
 * 
 * @author 
 *
 */
public class DetailSDController extends DetailPersonController
{


	private static final long serialVersionUID = -8678452591172228455L;
	protected int usertype;


	/**
	 * 
	 */
	public DetailSDController()
	{
		this.usertype = SystemDictionary.USERTYPE_CARER_INT;
	}


	/**
	 * 
	 */
	public void setControllerData(String id, PersonData data, SocioDemographicData sddata, String responsible, Carer carers)
	{
		super.setControllerData(id, data, sddata, responsible, carers);
	}


	/**
	 * 
	 * @return
	 */
	protected Listitem getSDItem()
	{
		Listitem lst1 = new Listitem();
		String text = Labels.getLabel("common.personal.data");
		Listcell cell1 = new Listcell(text);
		lst1.appendChild(cell1);
		String sddatavalue = SystemDictionary.getGenderLabel(
				this.currentsd .getGender().getCode()) + " (" + SystemDictionary.getDateFormatter(false)
						.format(this.currentsd.getBirthdayDate()) + ")";
		if (this.currentsd.getChildren().toString().equals("0"))
		{
			String text2 = Labels.getLabel("common.children.no");
			sddatavalue += " " + text2;
		}
		else if (this.currentsd.getChildren().toString().equals("1"))
		{
			String text3 = Labels.getLabel("common.children.one");
			sddatavalue += " " + text3;
		}
		else
		{
			String text4 = Labels.getLabel("common.children.more", new String[] { this.currentsd.getChildren().toString() });
			sddatavalue += " " + text4;
		}
		String text5 = Labels.getLabel("patients.form.sd.living");
		sddatavalue += ", "
				+ SystemDictionary.getMaritalStatuspeLabel(this.currentsd
						.getMaritalStatus().getCode())
				+ " "
				+ text5
				+ " "
				+ SystemDictionary.getLivingWithLabel(this.currentsd
						.getLivingWith().getCode());

		Listcell cell2 = new Listcell(sddatavalue);
		lst1.appendChild(cell2);

		return lst1;
	}


	/**
	 * 
	 */
	public Button[] createActionButtons()
	{
		Button btn = new Button();
		String text = Labels.getLabel("carers.update.title");
		btn.setLabel(text);
		btn.setHref("/carers/update.zul?carerid=" + this.currentid);

		Button btn1 = new Button();
		String text2 = Labels.getLabel("common.tasks.new");
		btn1.setLabel(text2);
		btn1.setHref("/carers/newtask.zul?carerid=" + this.currentid);

		Button[] ret = new Button[2];
		ret[0] = btn;
		ret[1] = btn1;

		return ret;
	}


	/**
	 * 
	 */
	public Listitem[] createDataRows()
	{
		Listitem[] rows = this.getPersonDataListItems();
		Listitem[] ret = new Listitem[rows.length + 1];

		ret[0] = rows[0];
		ret[1] = this.getSDItem();
		for (int i = 2; i < ret.length; i++)
		{
			ret[i] = rows[i - 1];
		}
		return ret;
	}
	

}
