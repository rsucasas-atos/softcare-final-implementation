package eu.ehealth.util.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.PatientInfo;
import eu.ehealth.security.DataBasePasswords;


/**
 * 
 * @author a572832
 *
 */
public class DecryptPatientInfoTask extends RecursiveTask<List<PatientInfo>>
{


	private static final long serialVersionUID = 389755912389739714L;
	private List<Object[]> _l;
	private List<PatientInfo> _qiList;
	
	
	/**
	 * 
	 * @param l
	 */
	public DecryptPatientInfoTask(List<Object[]> l) {
		super();
		this._l = l;
		_qiList = new ArrayList<PatientInfo>();
	}


	@Override
	protected List<PatientInfo> compute() {
		try 
		{
			if ((_l != null) && (_l.size() < 10)) 
			{
				for (Object[] objRes : _l) 
				{
					Integer id = (Integer) objRes[0];
					String name = DataBasePasswords.decryptDBValue((String) objRes[1]);
					String surname = DataBasePasswords.decryptDBValue((String) objRes[2]);
					Integer clinicianid = (Integer) objRes[3];
					
					PatientInfo qi = new PatientInfo();
					qi.setID(id.toString());
					qi.setSurname(name);
					qi.setName(surname);
					qi.setClinicianID(clinicianid.toString());
	
					_qiList.add(qi);
				}
			}
			else if (_l != null)
			{
				int ini = 0;
				int mid = ((_l.size() / 2) - 1);
				int end = _l.size();
				
				DecryptPatientInfoTask leftTask = new DecryptPatientInfoTask(_l.subList(ini, mid));
				DecryptPatientInfoTask rightTask = new DecryptPatientInfoTask(_l.subList(mid, end));
				
				// asynchronously execute both left and right tasks in the pool the current task is running in             
	            leftTask.fork();             
	            rightTask.fork();        
	            
	            // wait for the left half result             
	            _qiList.addAll( leftTask.join() );             
	            // wait for the right half result             
	            _qiList.addAll( rightTask.join() ); 
			}
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
		}
		
		return _qiList;
	}	
	
	
}
