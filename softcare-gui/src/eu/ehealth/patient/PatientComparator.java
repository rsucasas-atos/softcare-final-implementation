package eu.ehealth.patient;

import java.util.Comparator;
import eu.ehealth.ws_client.xsd.Patient;

public class PatientComparator implements Comparator{

	private boolean ascending;
	private int columnIndex;
	
	public PatientComparator(boolean ascending, int columnIndex){
		this.ascending = ascending;
		this.columnIndex = columnIndex;
	}
	
	public int compare(Object arg0, Object arg1) {
		Patient p1 = (Patient)arg0;
		Patient p2 = (Patient)arg1;
		int v = 0;
		switch(this.columnIndex){
			case 0:
				v = p1.getID().compareTo(p2.getID());
				break;
			case 1:
				v = p1.getPersonData().getName().compareTo(p2.getPersonData().getName());
				break;
			case 2:
				v = p1.getPersonData().getSurname().compareTo(p2.getPersonData().getSurname());
		}
		return this.ascending ? v: -v;
	}
	

}
