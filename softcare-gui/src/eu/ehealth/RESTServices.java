package eu.ehealth;

import javax.jws.WebService;
import javax.ws.rs.*;
import com.google.gson.Gson;


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

	
	@GET
	@Produces("application/json")
	@Path("/url/forum")
	public String getForumUrl()
	{
		try
		{
			return new Gson().toJson(new RestResponseObject("0", SystemDictionary.getForumURL()));
		}
		catch (Exception e)
		{
			return new Gson().toJson(new RestResponseObject("-1", "Error"));
		}
	}

	
	@GET
	@Produces("application/json")
	@Path("/url/mrepo")
	public String getMultimediaUrl()
	{
		try
		{
			return new Gson().toJson(new RestResponseObject("0", SystemDictionary.getMultimediaURL()));
		}
		catch (Exception e)
		{
			return new Gson().toJson(new RestResponseObject("-1", "Error"));
		}
	}
	
	
	@GET
	@Produces("application/json")
	@Path("/url/ws")
	public String getWSUrl()
	{
		try
		{
			String s = SystemDictionary.getWebServicesURL();
			return new Gson().toJson(new RestResponseObject("0", s.substring(0, s.lastIndexOf("/"))));
		}
		catch (Exception e)
		{
			return new Gson().toJson(new RestResponseObject("-1", "Error"));
		}
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
		
		
		public RestResponseObject(String c, String r)
		{
			this.code = c;
			this.result = r;
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
		
		
	}
	
		
}
