package eu.ehealth.controllers.details;

import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listitem;

public class DetailClinicianController extends DetailPersonController{
	private static final long serialVersionUID = 491827285077682351L;

	public Button[] createActionButtons() {
		Button btn = new Button();
		String text = Labels.getLabel("clinicians.update.title");
		btn.setLabel(text);
		btn.setHref("/clinicians/update.zul?clinid="+this.currentid);
		
		Button[] ret = new Button[1];
		ret[0] = btn;
		return ret;
	}

	public Listitem[] createDataRows() {
		return this.getPersonDataListItems();
	}

}
