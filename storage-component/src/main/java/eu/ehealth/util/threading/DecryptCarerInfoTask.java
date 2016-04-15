package eu.ehealth.util.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.xsd.CarerInfo;
import eu.ehealth.security.DataBasePasswords;


/**
 * 
 * @author a572832
 *
 */
public class DecryptCarerInfoTask extends RecursiveTask<List<CarerInfo>>
{


	private static final long serialVersionUID = 5806774193022301800L;
	private List<Object[]> _l;
	private List<CarerInfo> _qiList;
	
	
	/**
	 * 
	 * @param l
	 */
	public DecryptCarerInfoTask(List<Object[]> l) {
		super();
		this._l = l;
		_qiList = new ArrayList<CarerInfo>();
	}


	@Override
	protected List<CarerInfo> compute() {
		try 
		{
			int totalProcessors = Runtime.getRuntime().availableProcessors();
			
			if ((totalProcessors == 1) || ((_l != null) && (_l.size() < 10))) 
			{
				for (Object[] objRes : _l) 
				{
					Integer id = (Integer) objRes[0];
					String name = DataBasePasswords.decryptDBValue((String) objRes[1]);
					String surname = DataBasePasswords.decryptDBValue((String) objRes[2]);
					
					CarerInfo qi = new CarerInfo();
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
				
				DecryptCarerInfoTask leftTask = new DecryptCarerInfoTask(_l.subList(ini, mid));
				DecryptCarerInfoTask rightTask = new DecryptCarerInfoTask(_l.subList(mid, end));
				
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
