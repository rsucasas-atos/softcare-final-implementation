package eu.ehealth.util.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.ClinicianInfo;
import eu.ehealth.security.DataBasePasswords;


/**
 * 
 * @author a572832
 *
 */
public class DecryptClinicianInfoTask extends RecursiveTask<List<ClinicianInfo>>
{

	
	private static final long serialVersionUID = 1600661641707833485L;
	private List<Object[]> _l;
	private List<ClinicianInfo> _qiList;
	
	
	/**
	 * 
	 * @param l
	 */
	public DecryptClinicianInfoTask(List<Object[]> l) {
		super();
		this._l = l;
		_qiList = new ArrayList<ClinicianInfo>();
	}


	@Override
	protected List<ClinicianInfo> compute() {
		try 
		{
			if ((_l != null) && (_l.size() < 10)) 
			{
				for (Object[] objRes : _l) 
				{
					Integer id = (Integer) objRes[0];
					String name = DataBasePasswords.decryptDBValue((String) objRes[1]);
					String surname = DataBasePasswords.decryptDBValue((String) objRes[2]);
					
					ClinicianInfo qi = new ClinicianInfo();
					qi.setID(id.toString());
					qi.setSurname(name);
					qi.setName(surname);
	
					_qiList.add(qi);
				}
			}
			else if (_l != null)
			{
				int ini = 0;
				int mid = ((_l.size() / 2) - 1);
				int end = _l.size();
				
				DecryptClinicianInfoTask leftTask = new DecryptClinicianInfoTask(_l.subList(ini, mid));
				DecryptClinicianInfoTask rightTask = new DecryptClinicianInfoTask(_l.subList(mid, end));
				
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
