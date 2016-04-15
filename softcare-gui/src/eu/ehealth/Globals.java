package eu.ehealth;



public class Globals
{
	
	
	/**
	 * 
	 * @author a572832
	 *
	 */
	public enum CommunicationType {
		
		
	    phone ("phone"),
	    email ("email"),
	    other ("other");

	    
	    private final String name;       

	    
	    private CommunicationType(String s) 
	    {
	        name = s;
	    }

	    
	    public boolean equalsName(String otherName)
	    {
	        return (otherName == null)? false : name.equals(otherName);
	    }

	    
	    public String toString()
	    {
	       return name;
	    }
	    
	    
	}
	

}
