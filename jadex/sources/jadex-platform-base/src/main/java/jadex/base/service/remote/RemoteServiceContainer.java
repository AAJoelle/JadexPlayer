package jadex.base.service.remote;

import jadex.bridge.IComponentAdapter;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IRemoteServiceManagementService;
import jadex.commons.Future;
import jadex.commons.IFuture;
import jadex.commons.concurrent.IResultListener;
import jadex.commons.service.BasicServiceContainer;
import jadex.commons.service.IResultSelector;
import jadex.commons.service.ISearchManager;
import jadex.commons.service.IVisitDecider;
import jadex.commons.service.SServiceProvider;

import java.util.Collection;
import java.util.Iterator;

/**
 *  Remote service container for searching at a remote platform
 *  in the same way as on a the local one.
 */
public class RemoteServiceContainer extends BasicServiceContainer
{
	//-------- attributes --------
	
	/** The remote component id. */
	protected IComponentIdentifier componentid;
	
	/** The component adapter. */
	protected IComponentAdapter adapter;
	
	/** The local rms service. */
	protected IRemoteServiceManagementService rms;
	
	//-------- constructors --------

	/**
	 *  Create a new service container.
	 */
	public RemoteServiceContainer(IComponentIdentifier componentid, IComponentAdapter adapter)
	{
		super(adapter.getComponentIdentifier());
		this.adapter = adapter;
		this.componentid = componentid;
	}
	
	//-------- interface methods --------
	
	/**
	 *  Get all services of a type.
	 *  @param type The class.
	 *  @return The corresponding services.
	 */
	public IFuture	getServices(final ISearchManager manager, final IVisitDecider decider, final IResultSelector selector, final Collection results)
	{
		final Future ret = new Future();
		
		super.getServices(manager, decider, selector, results).addResultListener(new IResultListener()
		{
			public void resultAvailable(Object source, Object result)
			{
				// If should not search remotely or not inited (no rms)
				
				// Problem that the container calls itself the decider, could already
				// be done in search manager when this call is part of a search
				// But could also be called directly :-(
				if(!decider.searchNode(null, RemoteServiceContainer.this, results)
					|| rms==null || componentid==null)// || selector instanceof ComponentFactorySelector)
				{
					ret.setResult(selector.getResult(results));
				}
				else
				{
					// Hack! Use user search manager.
					rms.getServiceProxies(componentid, SServiceProvider.sequentialmanager, decider, selector)
						.addResultListener(new IResultListener()
					{
						public void resultAvailable(Object source, Object res)
						{
							if(res instanceof Collection)
							{
								for(Iterator it=((Collection)res).iterator(); it.hasNext(); )
								{
									Object next = it.next();
//									System.out.println("add rem: "+next);
									if(!results.contains(next))
										results.add(next);
								}
							}
							else if(res!=null)
							{
//								System.out.println("add rem: "+res);
								if(!results.contains(res))
									results.add(res);
							}
							ret.setResult(selector.getResult(results));
						}
						
						public void exceptionOccurred(Object source, Exception exception)
						{
							ret.setException(exception);
						}
					});
				}
			}
			
			public void exceptionOccurred(Object source, Exception exception)
			{
				ret.setException(exception);
			}
		});
			
		return ret;
	}
	
	/**
	 *  Get the parent service container.
	 *  @return The parent container.
	 */
	public IFuture	getParent()
	{
		final Future ret = new Future();
		
		ret.setResult(adapter.getParent()!=null ? adapter.getParent().getServiceProvider() : null);
		
		return ret;
	}
	
	/**
	 *  Get the children service containers.
	 *  @return The children containers.
	 */
	public IFuture	getChildren()
	{
		// no children to ensure that search stops here
		final Future ret = new Future(null);
		
//		adapter.getChildren().addResultListener(new IResultListener()
//		{
//			public void resultAvailable(Object source, Object result)
//			{
//				Collection	children	= null;
//				if(result!=null)
//				{
//					children	= new ArrayList();
//					for(Iterator it=((Collection)result).iterator(); it.hasNext(); )
//					{
//						children.add(((IExternalAccess)it.next()).getServiceProvider());
//					}
//				}
//				ret.setResult(children);
//			}
//			public void exceptionOccurred(Object source, Exception exception)
//			{
//				ret.setException(exception);
//			}
//		});
		
		return ret;
	}
	
	/**
	 *  Start the service.
	 *  @return A future that is done when the service has completed starting.  
	 */
	public IFuture start()
	{
		final Future ret = new Future();
		
//		System.out.println("Searching rms: "+getId());
		SServiceProvider.getService(this, IRemoteServiceManagementService.class)
			.addResultListener(new IResultListener()
		{
			public void resultAvailable(Object source, Object result)
			{
//				System.out.println("Found rms: "+getId()+result);
				rms = (IRemoteServiceManagementService)result;	
				ret.setResult(null);
			}
			public void exceptionOccurred(Object source, Exception exception) 
			{
				ret.setException(exception);
			}
		});
		
		return ret;
	}
	
	/**
	 *  Shutdown the service.
	 *  @return A future that is done when the service has completed its shutdown.  
	 */
	public IFuture shutdown()
	{
		return new Future(null);
	}
	
	/**
	 *  Add a service to the platform.
	 *  If under the same name and type a service was contained,
	 *  the old one is removed and shutdowned.
	 *  @param name The name.
	 *  @param service The service.
	 */
	public void addService(Class type, Object service)
	{
		throw new UnsupportedOperationException("Unsupported operation on remote container.");
	}

	/**
	 *  Removes a service from the platform (shutdowns also the service).
	 *  @param name The name.
	 *  @param service The service.
	 */
	public void removeService(Class type, Object service)
	{
		throw new UnsupportedOperationException("Unsupported operation on remote container.");
	}
		
	/**
	 *  Get the string representation.
	 *  @return The string representation.
	 */
	public String toString()
	{
		return "RemoteServiceProvider(id="+getId()+")";
	}
}

