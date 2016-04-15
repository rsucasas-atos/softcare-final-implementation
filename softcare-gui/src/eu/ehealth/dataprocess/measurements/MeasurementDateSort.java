package eu.ehealth.dataprocess.measurements;

import java.util.Comparator;
import eu.ehealth.ws_client.xsd.Measurement;


public class MeasurementDateSort implements Comparator<Measurement>
{


	public int compare(Measurement arg0, Measurement arg1)
	{
		return arg0.getDateTime().compare(arg1.getDateTime());
	}
	

}
