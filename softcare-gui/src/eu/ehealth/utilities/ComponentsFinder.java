package eu.ehealth.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.zkoss.zk.ui.Component;


/**
 * 
 * @author a572832
 *
 */
public class ComponentsFinder {

	
	
	/**
	 * 
	 * @param comp_collection
	 * @param idLostComponent
	 * @return
	 */
	public static Component getUIComponent(List<Component> comp_list, String idLostComponent) {
		try 
		{	
			return getComponentFromLists(idLostComponent, comp_list);
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
		try 
		{	
			List<Component> comp_list = null;
			if (col instanceof List) {
				comp_list = (List<Component>) col;
			}
			else {
				comp_list = new ArrayList<Component>(col);
			}
			
			return getComponentFromLists(idLostComponent, comp_list);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param idLostComponent
	 * @param comp_list
	 * @return
	 */
	private static Component getComponentFromLists(String idLostComponent, List<Component> comp_list) {
		for (Component comp : comp_list) 
		{
			if ( (comp.getId() != null) && (comp.getId().trim().length() > 0) && (idLostComponent.equalsIgnoreCase(comp.getId())) ) 
			{
				return comp;
			}
			else if ( (comp.getId() != null) && (comp.getChildren().size() > 0) )
			{
				List<Component> comp_list2 = comp.getChildren();
				Component result = getComponentFromLists(idLostComponent, comp_list2);
				if (result != null) {
					return result;
				}
			}
		}

		return null;
	}
	
	
}
