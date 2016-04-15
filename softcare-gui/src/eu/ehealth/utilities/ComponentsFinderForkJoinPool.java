package eu.ehealth.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import org.zkoss.zk.ui.Component;


/**
 * 
 * @author a572832
 *
 */
public class ComponentsFinderForkJoinPool {

	
	/**
	 * 
	 * @param comp_list
	 * @param idLostComponent
	 * @return
	 */
	public static Component getUIComponent(List<Component> comp_list, String idLostComponent) {
		ForkJoinPool pool = new ForkJoinPool();
		try 
		{
			ComponentsFinderTask task = new ComponentsFinderTask(idLostComponent, comp_list);
			return pool.invoke(task);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param col
	 * @param idLostComponent
	 * @return
	 */
	public static Component getUIComponent(Collection<Component> col, String idLostComponent) {
		ForkJoinPool pool = new ForkJoinPool();
		try 
		{
			List<Component> comp_list = null;
			if (col instanceof List) {
				comp_list = (List<Component>) col;
			}
			else {
				comp_list = new ArrayList<Component>(col);
			}
			
			ComponentsFinderTask task = new ComponentsFinderTask(idLostComponent, comp_list);
			return pool.invoke(task);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
}
