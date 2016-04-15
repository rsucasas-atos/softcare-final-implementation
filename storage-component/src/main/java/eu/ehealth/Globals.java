package eu.ehealth;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import eu.ehealth.db.xsd.OperationResult;


/**
 * 
 * @author a572832
 *
 */
public class Globals
{
	
	
	public static final String strLOGIN_ATTEMPTS = "LOGIN_ATTEMPTS";
	public static final int LOGIN_ATTEMPTS = 5;
	public static final String strWEIGHT_MIN = "WEIGHT_MIN";
	public static final int WEIGHT_MIN = 30;
	public static final String strWEIGHT_MAX = "WEIGHT_MAX";
	public static final int WEIGHT_MAX = 180;
	public static final String strBLOOD_SISTOLIC_MIN = "BLOOD_SISTOLIC_MIN";
	public static final int BLOOD_SISTOLIC_MIN = 60;
	public static final String strBLOOD_SISTOLIC_MAX = "BLOOD_SISTOLIC_MAX";
	public static final int BLOOD_SISTOLIC_MAX = 250;
	public static final String strBLOOD_DIASTOLIC_MIN = "BLOOD_DIASTOLIC_MIN";
	public static final int BLOOD_DIASTOLIC_MIN = 30;
	public static final String strBLOOD_DIASTOLIC_MAX = "BLOOD_DIASTOLIC_MAX";
	public static final int BLOOD_DIASTOLIC_MAX = 140;
	
	
	/**
	 * 
	 */
	public static HashMap<String, String> EHEALTH_PARAMETERS;
	
	
	/**
	 * 
	 * @param lList
	 */
	public static void addValuesEHEALTH_PARAMETERS(List<String[]> lList) {
		try
		{
			if (EHEALTH_PARAMETERS == null) {
				EHEALTH_PARAMETERS = new HashMap<String, String>();
			}
			
			Iterator<String[]> iterator = lList.iterator();
			while (iterator.hasNext()) {
				String[] obj = iterator.next();
				EHEALTH_PARAMETERS.put(obj[0], obj[1]);
			}
		}
		catch (Exception ex) {
			StorageComponentMain.logException(ex);
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static int getIntValueEHEALTH_PARAMETERS(String key) {
		try
		{
			return Integer.parseInt(EHEALTH_PARAMETERS.get(key));
		}
		catch (Exception ex) {
			StorageComponentMain.logException(ex);
			return -1;
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * LOGIN variables
	 */

	/**
	 * 
	 * @author a572832
	 *
	 */
	public enum LoginResponseStatus {
		
		OK ((short) 1),
		ERROR ((short) 0);
		
		
		private final short value;       
		
		
		private LoginResponseStatus(short s) 
	    {
			value = s;
	    }

		
		public short getValue()
	    {
	       return value;
	    }
		
	}
	
	
	/**
	 * 
	 * @author a572832
	 *
	 */
	public enum ResponseCode {
		
		// > 0 ... all is ok, it's a valid user
		// others:
		INVALID_USER 			("0", 	"Invalid user - incorrect password"),
		DATABASE_ERROR_1 		("-2", 	"Database error (-2) : HibernateException"),
		DATABASE_ERROR_2 		("-4", 	"Database error (-4)"),
		USER_ALREADY_EXISTS		("-5", 	"Username already exists"),
	    USER_LOCKED 			("-10", "User locked - contact with administrator"),
	    INVALID_PASSWORD 		("-11", "Invalid password"),
	    UNKNOWN_ERROR 			("-12", "Unknown error while processing an action"),
	    PERMISSION_ERROR 		("-13", "Permission denied"),
	    INVALID_PASSWORD_FORMAT ("-14", "Invalid password value : letters 'a-zA-Z' and at least one digit 0-9. Length 8-20"),
	    RESPONSE_OK 			("-99", "Operation executed succesfully");

	    
	    private final String code;       
	    private final String desc;  
	    private static final Map<String, ResponseCode> table = new HashMap<String, ResponseCode>();

	    
	    static {
	    	for (ResponseCode d : ResponseCode.values())
            {
            	table.put(d.getCode(), d);
            }
        }
	    
	    
	    private ResponseCode(String s, String txt) 
	    {
	    	code = s;
	    	desc = txt;
	    }

	    
	    public boolean equalsName(String otherName)
	    {
	        return (otherName == null)? false : code.equals(otherName);
	    }

	    
	    public String toString()
	    {
	    	return code + " : " + desc + " ";
	    }
	    
	    
	    public String[] toStringArray()
	    {
	    	return toStringArray("");
	    }
	    
	    
	    public String[] toStringArray(String txtDesc)
	    {
	    	String[] response = new String[2];
			response[0] = code;
			response[1] = desc + txtDesc;
			
	    	return response;
	    }
	    
	    
	    public static String[] getStringFromCode(String vcode)
	    {
	    	String[] response = new String[2];
			response[0] = vcode;
			response[1] = table.get(vcode).getDesc();
			
	    	return response;
	    }
	    
	    
	    public String getCode()
	    {
	       return code;
	    }
	    
	    
	    public String getDesc()
	    {
	       return desc;
	    }
	    
	    
	    public int getValue()
	    {
	       return Integer.parseInt(code);
	    }
		
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @author a572832
	 *
	 */
	public enum CommunicationType {
		
	    PHONE ("phone"),
	    EMAIL ("email"),
	    OTHER ("other");

	    
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
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * Meassurements variables
	 */
	public static String ClientAppValue_SystolicBloodPressure = "11";
	public static String ClientAppValue_DiastolicBloodPressure = "12";
	
	public static String ServerAppValue_BloodPressure = "1";
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * Static functions
	 */
	
	/**
	 * 
	 * @param code
	 * @param txtDesc
	 * @return
	 */
	public static OperationResult getOpResult(String code, String txtDesc) 
	{
		OperationResult res = new OperationResult();
		
		try {
			int valorCode = -100;
			try {
				valorCode = Integer.parseInt(code);
			}
			catch (Exception ex) {
				StorageComponentMain.scLog("ERROR", "Setting OperationResult values ERROR (1) : " + ex.getMessage());
			}
			
			if (valorCode <= 0) {
				if (code.equals(ResponseCode.USER_LOCKED.getCode())) {
					res.setCode(Globals.ResponseCode.USER_LOCKED.getCode());
					res.setDescription(Globals.ResponseCode.USER_LOCKED.getDesc() + txtDesc);
				}
				else if (code.equals(ResponseCode.DATABASE_ERROR_2.getCode())) {
					res.setCode(Globals.ResponseCode.DATABASE_ERROR_2.getCode());
					res.setDescription(Globals.ResponseCode.DATABASE_ERROR_2.getDesc() + txtDesc);
				}
				else if (code.equals(ResponseCode.INVALID_USER.getCode())) {
					res.setCode(Globals.ResponseCode.INVALID_USER.getCode());
					res.setDescription(Globals.ResponseCode.INVALID_USER.getDesc() + txtDesc);
				}
				else if (code.equals(ResponseCode.DATABASE_ERROR_1.getCode())) {
					res.setCode(Globals.ResponseCode.DATABASE_ERROR_1.getCode());
					res.setDescription(Globals.ResponseCode.DATABASE_ERROR_1.getDesc() + txtDesc);
				}
				else if (code.equals(ResponseCode.INVALID_PASSWORD.getCode())) {
					res.setCode(Globals.ResponseCode.INVALID_PASSWORD.getCode());
					res.setDescription(Globals.ResponseCode.INVALID_PASSWORD.getDesc() + txtDesc);
				}
				
				res.setStatus(Globals.LoginResponseStatus.ERROR.getValue());
			}
			else {
				res.setCode(code);
				res.setDescription("ok");
				res.setStatus(Globals.LoginResponseStatus.OK.getValue());
			}
			
			/*
			StorageComponentMain.scLog("DEBUG", "RESPONSE - OperationResult --------------");
			StorageComponentMain.scLog("DEBUG", "	C: " + res.getCode());
			StorageComponentMain.scLog("DEBUG", "	D: " + res.getDescription());
			StorageComponentMain.scLog("DEBUG", "	S: " + res.getStatus());
			*/
		}
		catch (Exception ex) {
			StorageComponentMain.scLog("ERROR", "Setting OperationResult values ERROR (2) : " + ex.getMessage());
		}
		
		return res;
	}
	
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public static OperationResult getOpResult(String code) 
	{
		return getOpResult(code, "");
	}
	

}
 