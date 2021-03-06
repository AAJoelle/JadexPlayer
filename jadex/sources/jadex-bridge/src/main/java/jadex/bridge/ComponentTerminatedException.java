package jadex.bridge;


/**
 *  Thrown when operations are invoked after an component has been terminated.
 */
public class ComponentTerminatedException	extends RuntimeException
{
	//-------- attributes --------
	
	/** The component identifier. */
	protected IComponentIdentifier cid;
	
	//-------- constructors --------
	
	/**
	 *	Create an component termination exception.  
	 */
	public ComponentTerminatedException(IComponentIdentifier cid)
	{
		super(cid.getName());
		this.cid = cid;
	}

	//-------- methods --------
	
	/**
	 *  Get the component identifier.
	 *  @return The component identifier.
	 */
	public IComponentIdentifier getComponentIdentifier()
	{
		return cid;
	}
}
