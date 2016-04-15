package eu.ehealth;

import java.util.ArrayList;
import javax.jws.WebService;
import javax.ws.rs.*;
import com.google.gson.Gson;
import eu.ehealth.db.HibernateUtil;
import eu.ehealth.db.wservices.authentication.Auth;
import eu.ehealth.db.wservices.authentication.GetUserType;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.util.Utilities;


/**
 * 
 *
 * @author ATOS
 * @date 18/3/2016
 */
@WebService
public class RESTServices
{
	
	
	@GET
	@Produces("application/json")
	@Path("/test/json/{param}")
	public String testjson(@PathParam("param") String  param)
	{
		RestResponseObject resp = new RestResponseObject("0", param);
	    return new Gson().toJson(resp);
	}
	
	
	@GET
	@Produces("application/xml")
	@Path("/test/xml/{param}")
	public String testxml(@PathParam("param") String  param)
	{
	    return "<response>" + param + "</response>";
	}
	
	
	@GET
	@Produces("text/html")
	@Path("/test/plaintext/{param}")
	public String testplaintext(@PathParam("param") String  param)
	{
	    return "response: " + param;
	}
	
	
	public final static String USERTYPE_ADMIN = "1";
	public final static String USERTYPE_CLINICIAN = "2";
	public final static String USERTYPE_CARER = "3";
	public final static String USERTYPE_PATIENT = "4";
	

	@POST
	@Produces("application/json")
	@Path("/login")
	public String doLogin(@FormParam("userName") String u, @FormParam("password") String  p)
	{
		//System.out.println("---> " + u + " / " + p);
		ArrayList<String> lParams = new ArrayList<String>(2);
		lParams.add(u);
		lParams.add(p);
		
		OperationResult res = new Auth(HibernateUtil.getSessionFactory().openSession()).execute(lParams);
		
		if (Utilities.NumericFunctions.int_isGreaterThan(res.getCode(), 0) == 1) 
		{
			ArrayList<String> lParams2 = new ArrayList<String>(1);
			lParams2.add(res.getCode());
			String usertype = "";
			
			OperationResult restype = new GetUserType(HibernateUtil.getSessionFactory().openSession()).execute(lParams2);
			if (restype.getCode().equals(USERTYPE_ADMIN))
			{
				SystemDictionary.webguiLog("INFO", "Administrator logged in");
				usertype = USERTYPE_ADMIN;
			}
			else if (restype.getCode().equals(USERTYPE_CLINICIAN))
			{
				SystemDictionary.webguiLog("INFO", "Clinician logged in");
				usertype = USERTYPE_CLINICIAN;
			}
			else
			{
				return new Gson().toJson(new RestResponseObject("-1", "Error: not allowed user type"));
			}
			
			//System.out.println("usertype---> " + usertype);
			
			RestResponseObject resp = new RestResponseObject(res.getCode(), "User authenticated succesfully");
			resp.setData1(usertype);
			return new Gson().toJson(resp);
		}
		
		return new Gson().toJson(new RestResponseObject(res.getCode(), "Error"));
	}

	
	@GET
	@Produces("application/json")
	@Path("/url/forum")
	public String getForumUrl()
	{
		String url = System.getProperty("URL_FORUM", "default_value");
		RestResponseObject resp = new RestResponseObject("0", url);
		return new Gson().toJson(resp);
	}

	
	@GET
	@Produces("application/json")
	@Path("/url/mrepo")
	public String getMultimediaUrl()
	{
		String url = System.getProperty("URL_MREPO", "default_value");
		RestResponseObject resp = new RestResponseObject("0", url);
		return new Gson().toJson(resp);
	}
	
	
	@GET
	@Produces("application/json")
	@Path("/url/gui")
	public String getGuiUrl()
	{
		String url = System.getProperty("URL_GUI", "http://localhost:8080/softcare-gui");
		RestResponseObject resp = new RestResponseObject("0", url);
		return new Gson().toJson(resp);
	}

	
	/**
	 * 
	 *
	 * @author ATOS
	 * @date 18/3/2016
	 */
	public class RestResponseObject
	{
		
		private String code;
		private String result;
		private String data1;
		
		
		public RestResponseObject(String c, String r)
		{
			this.code = c;
			this.result = r;
			this.data1 = "";
		}
		
		
		/**
		 * @return the code
		 */
		public String getCode()
		{
			return code;
		}
		
		
		/**
		 * @return the result
		 */
		public String getResult()
		{
			return result;
		}

		
		/**
		 * @return the data1
		 */
		public String getData1()
		{
			return data1;
		}

		
		/**
		 * @param data1 the data1 to set
		 */
		public void setData1(String data1)
		{
			this.data1 = data1;
		}
		
		
	}
	
		
}
