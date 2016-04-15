package eu.ehealth.utilities;

import java.util.List;
import java.util.concurrent.RecursiveTask;
import org.zkoss.zk.ui.Component;


/**
 * 
 * @author a572832
 *
 */
public class ComponentsFinderTask extends RecursiveTask<Component> {


	private static final long serialVersionUID = 5390349866078646561L;
	private List<Component> comp_list;
	private String idLostComponent;
	
	
	/**
	 * 
	 * @param vidLostComponent
	 * @param vcomp_list
	 */
	public ComponentsFinderTask(String vidLostComponent, List<Component> vcomp_list) 
	{
		super();
		idLostComponent = vidLostComponent;
		comp_list = vcomp_list;
	}
	

	@Override
	protected Component compute() {
		for (Component comp : comp_list) 
		{
			if ( (comp.getId() != null) && (comp.getId().trim().length() > 0) && (idLostComponent.equalsIgnoreCase(comp.getId())) ) 
			{
				return comp;
			}
			else if ( (comp.getId() != null) && (comp.getChildren().size() > 0) )
			{
				List<Component> comp_list2 = comp.getChildren();
				ComponentsFinderTask task = new ComponentsFinderTask(idLostComponent, comp_list2);
				Component result = task.compute();
				if (result != null) {
					return result;
				}
			}
		}
		return null;
	}
	

}
