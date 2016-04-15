package eu.ehealth.dataprocessing;

import java.io.FileReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;
import eu.ehealth.StorageComponentMain;


/**
 * 
 * @author
 *
 */
public class MTableJsonReader
{
	
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public HashMap<String, MTableElement> getDataResources(String file) {
        try 
        {
        	ClassLoader loader = this.getClass().getClassLoader();
        	String filePath = loader.getResource(file).getFile();
        	String encodeType = StorageComponentMain.ENCODING;
        	filePath = URLDecoder.decode(filePath, encodeType);
        	
        	if (filePath == null)
        	{
        		StorageComponentMain.scLog("ERROR", "Table file path is NULL / can't be found : " + file);
        		return null;
        	}
        	
            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<ArrayList<MTableElement>>(){}.getType();
            Gson gson = new Gson();
            // json content to ArrayList
            ArrayList<MTableElement> lres = gson.fromJson(new FileReader(filePath), type);
            
            // create ImmutableMap
            HashMap<String, MTableElement> result = new HashMap<String, MTableElement>(lres.size());
            for (MTableElement elem : lres)
			{
            	result.put(elem.getLevel(), elem);
			}

            return result;
        }
        catch (Exception ex) {
        	StorageComponentMain.logException(ex);
            return null;
        }
    }
	

}
