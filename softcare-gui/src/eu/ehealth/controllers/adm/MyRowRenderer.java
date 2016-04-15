package eu.ehealth.controllers.adm;

import org.zkoss.zul.*;


/**
 * 
 * @author a572832
 *
 */
public class MyRowRenderer implements RowRenderer {
	
	
	/**
	 * 
	 */
	public void render(final Row row, final java.lang.Object data, int ind) 
	{
		String[] ary = (String[]) data;
		new Label(ary[0]).setParent(row);
		new Label(ary[1]).setParent(row);
		new Label(ary[2]).setParent(row);
		new Label(ary[3]).setParent(row);
		new Label(ary[4]).setParent(row);
		new Label(ary[5]).setParent(row);
		new Label(ary[6]).setParent(row);
		new Label(ary[7]).setParent(row);
		new Label(ary[8]).setParent(row);
	}
	
	
}
