package jadex.commons.service;


/**
 *  Service identifier for uniquely identifying a service.
 *  Is composed of the container id and the service name.
 */
public class ServiceIdentifier implements IServiceIdentifier
{
	//-------- attributes --------
	
	/** The provider identifier. */
	protected Object providerid;
	
	/** The service type. */
	protected Class type;
	
	/** The service name. */
	protected String servicename;
	
	//-------- constructors --------
	
	/**
	 *  Create a new service identifier.
	 */
	public ServiceIdentifier()
	{
	}
	
	/**
	 *  Create a new service identifier.
	 */
	public ServiceIdentifier(Object providerid, Class type, String servicename)
	{
		this.providerid = providerid;
		this.type	= type;
		this.servicename = servicename;
	}
	
	//-------- methods --------
	
	/**
	 *  Get the service provider identifier.
	 *  @return The provider id.
	 */
	public Object	getProviderId()
	{
		return providerid;
	}
	
	/**
	 *  Set the providerid.
	 *  @param providerid The providerid to set.
	 */
	public void setProviderId(Object providerid)
	{
		this.providerid = providerid;
	}
	
	/**
	 *  Get the service type.
	 *  @return The service type.
	 */
	public Class	getServiceType()
	{
		return type;
	}

	
	/**
	 *  Set the service type.
	 *  @param type The service type.
	 */
	public void	setServiceType(Class type)
	{
		this.type	= type;
	}

	/**
	 *  Get the service name.
	 *  @return The service name.
	 */
	public String	getServiceName()
	{
		return servicename;
	}
	
	/**
	 *  Set the servicename.
	 *  @param servicename The servicename to set.
	 */
	public void setServiceName(String servicename)
	{
		this.servicename = servicename;
	}

	/**
	 *  Get the hashcode.
	 *  @return The hashcode.
	 */
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((providerid == null) ? 0 : providerid.hashCode());
		result = prime * result + ((servicename == null) ? 0 : servicename.hashCode());
		return result;
	}

	/**
	 *  Test if an object is equal to this one.
	 *  @param obj The object.
	 *  @return True, if equal.
	 */
	public boolean equals(Object obj)
	{
		boolean ret = false;
		if(obj instanceof IServiceIdentifier)
		{
			IServiceIdentifier sid = (IServiceIdentifier)obj;
			ret = sid.getProviderId().equals(getProviderId()) && sid.getServiceName().equals(getServiceName());
		}
		return ret;
	}

	/**
	 *  Get the string representation.
	 */
	public String toString()
	{
		return "ServiceIdentifier(providerid=" + providerid + ", type=" + type
				+ ", servicename=" + servicename + ")";
	}
	
	
	
}
