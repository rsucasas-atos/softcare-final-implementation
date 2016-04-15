package eu.ehealth.patient;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.ListModelList;

public class PatientList extends ListModelList{

	private static final long serialVersionUID = 999116754289450437L;
	
	public PatientList(List list){
		super(list,true);
		
	}
	
	public void sort(Comparator cmpr, boolean ascending) {
        Collections.sort(getInnerList() , cmpr);
        fireEvent(org.zkoss.zul.event.ListDataEvent.CONTENTS_CHANGED, -1, -1);
    }

}
