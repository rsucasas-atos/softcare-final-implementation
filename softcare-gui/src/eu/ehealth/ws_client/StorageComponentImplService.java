package eu.ehealth.ws_client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;


/**
 * 
 * @author a572832
 *
 */
@WebServiceClient(name = "StorageComponentImplService", wsdlLocation = "file:/C:/Users/A572832/workspace/cxf-hibernate/services.wsdl", targetNamespace = "http://ehealth.eu/")
public class StorageComponentImplService extends Service
{


	public final static URL WSDL_LOCATION;
	public final static QName SERVICE = new QName("http://ehealth.eu/", "StorageComponentImplService");
	public final static QName StorageComponentImplPort = new QName("http://ehealth.eu/", "StorageComponentImplPort");
	
	static
	{
		URL url = null;
		try
		{
			url = new URL("file:/C:/Users/A572832/workspace/cxf-hibernate/services.wsdl");
		}
		catch (MalformedURLException e)
		{
			java.util.logging.Logger.getLogger(StorageComponentImplService.class.getName())
					.log(java.util.logging.Level.INFO, "Can not initialize the default wsdl from {0}", 
							"file:/C:/Users/A572832/workspace/cxf-hibernate/services.wsdl");
		}
		WSDL_LOCATION = url;
	}


	public StorageComponentImplService(URL wsdlLocation)
	{
		super(wsdlLocation, SERVICE);
	}


	public StorageComponentImplService(URL wsdlLocation, QName serviceName)
	{
		super(wsdlLocation, serviceName);
	}


	public StorageComponentImplService()
	{
		super(WSDL_LOCATION, SERVICE);
	}


	/**
	 * 
	 * @return returns StorageComponent
	 */
	@WebEndpoint(name = "StorageComponentImplPort")
	public StorageComponent getStorageComponentImplPort()
	{
		return super.getPort(StorageComponentImplPort, StorageComponent.class);
	}


	/**
	 * 
	 * @param features A list of {@link javax.xml.ws.WebServiceFeature} to
	 *            configure on the proxy. Supported features not in the
	 *            <code>features</code> parameter will have their default
	 *            values.
	 * @return returns StorageComponent
	 */
	@WebEndpoint(name = "StorageComponentImplPort")
	public StorageComponent getStorageComponentImplPort(WebServiceFeature... features)
	{
		return super.getPort(StorageComponentImplPort, StorageComponent.class, features);
	}
	

}
