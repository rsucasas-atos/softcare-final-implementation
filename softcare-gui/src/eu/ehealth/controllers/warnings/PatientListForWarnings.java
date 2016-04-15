package eu.ehealth.controllers.warnings;

import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;


/**
 * 
 * 
 * @author 
 * @date 23/04/2014.
 *
 */
public class PatientListForWarnings extends Window
{
	
	
	private static final long serialVersionUID = -3537252407835100621L;

	
	/**
	 * 
	 */
	public PatientListForWarnings() { }
	
	
	/**
	 * 
	 */
	public void setPatient()
	{
		Radiogroup rgroup = (Radiogroup)this.getFellow("respo_rgroup");
		Radio radio = rgroup.getSelectedItem();
		if(radio != null){
			WarningsWindowController wincon = (WarningsWindowController)this.getParent(); 
			wincon.setPatientForFilter((String)radio.getValue(), radio.getLabel());
		}
		this.getParent().removeChild(this);
	}
	
	
}
