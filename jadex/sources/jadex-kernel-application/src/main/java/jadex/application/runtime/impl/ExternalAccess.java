package jadex.application.runtime.impl;

import jadex.application.runtime.IApplicationExternalAccess;
import jadex.application.runtime.ISpace;
import jadex.bridge.ComponentResultListener;
import jadex.bridge.IComponentAdapter;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IComponentStep;
import jadex.bridge.IModelInfo;
import jadex.commons.Future;
import jadex.commons.IFuture;
import jadex.commons.concurrent.DelegationResultListener;
import jadex.commons.concurrent.IResultListener;
import jadex.commons.service.IServiceProvider;

/**
 *  External access for applications.
 */
public class ExternalAccess implements IApplicationExternalAccess
{
	//-------- attributes --------

	/** The application component. */
	protected ApplicationInterpreter application;

	/** The component adapter. */
	protected IComponentAdapter adapter;
	
	/** The toString value. */
	protected String tostring;
	
	/** The provider. */
	protected IServiceProvider provider;
	
	// -------- constructors --------

	/**
	 *	Create an external access.
	 */
	public ExternalAccess(ApplicationInterpreter application)
	{
		this.application = application;
		this.adapter = application.getComponentAdapter();
		this.tostring = application.getComponentIdentifier().getLocalName();
		this.provider = application.getServiceProvider();
	}

	//-------- methods --------
	
	/**
	 *  Get the model.
	 *  @return The model.
	 */
	public IModelInfo getModel()
	{
		return application.getModel();
	}
	
	/**
	 *  Get the component identifier.
	 */
	public IComponentIdentifier	getComponentIdentifier()
	{
		return adapter.getComponentIdentifier();
	}
	
	/**
	 *  Get a space of the application.
	 *  @param name	The name of the space.
	 *  @return	The space.
	 */
	public ISpace getSpace(final String name)
	{
		// Application getSpace() is synchronized
		return application.getSpace(name);
		
//		final Future ret = new Future();
//		
//		if(adapter.isExternalThread())
//		{
//			adapter.invokeLater(new Runnable() 
//			{
//				public void run() 
//				{
//					ret.setResult(application.getSpace(name));
//				}
//			});
//		}
//		else
//		{
//			ret.setResult(application.getSpace(name));
//		}
//		
//		return ret;
	}
	
	/**
	 *  Get the parent.
	 */
	public IComponentIdentifier getParent()
	{
		return application.getParent()!=null ? application.getParent().getComponentIdentifier() : null;
	}
	
	/**
	 *  Get the children (if any).
	 *  @return The children.
	 */
	public IFuture getChildren()
	{
		return adapter.getChildrenIdentifiers();
	}
	
	/**
	 *  Get the application component.
	 */
	public IServiceProvider getServiceProvider()
	{
		return provider;
	}

	/**
	 *  Kill the component.
	 */
	public IFuture killComponent()
	{
		final Future ret = new Future();
		
		if(adapter.isExternalThread())
		{
			try
			{
				adapter.invokeLater(new Runnable() 
				{
					public void run() 
					{
						application.killComponent().addResultListener(new DelegationResultListener(ret));
					}
				});
			}
			catch(Exception e)
			{
				ret.setException(e);
			}
		}
		else
		{
			application.killComponent().addResultListener(new DelegationResultListener(ret));
		}
		
		return ret;
	}
	
	/**
	 *  Create a result listener that will be 
	 *  executed on the component thread.
	 *  @param listener The result listener.
	 *  @return A result listener that is called on component thread.
	 */
	public IResultListener createResultListener(IResultListener listener)
	{
		return new ComponentResultListener(listener, adapter);
	}
	
	/**
	 *  Get the children (if any).
	 *  @return The children.
	 */
	public IFuture getChildren(final String type)
	{
		final Future ret = new Future();
		
		if(adapter.isExternalThread())
		{
			try
			{
				adapter.invokeLater(new Runnable() 
				{
					public void run() 
					{
						ret.setResult(application.getChildren(type));
					}
				});
			}
			catch(Exception e)
			{
				ret.setException(e);
			}
		}
		else
		{
			ret.setResult(application.getChildren(type));
		}
		
		return ret;
	}
	
	/**
	 *  Get the file name of a component type.
	 *  @param ctype The component type.
	 *  @return The file name of this component type.
	 */
	public IFuture getFileName(final String ctype)
	{
		final Future ret = new Future();
		
		if(adapter.isExternalThread())
		{
			try
			{
				adapter.invokeLater(new Runnable() 
				{
					public void run() 
					{
						ret.setResult(application.getFileName(ctype));
					}
				});
			}
			catch(Exception e)
			{
				ret.setException(e);
			}
		}
		else
		{
			ret.setResult(application.getFileName(ctype));
		}
		
		return ret;
	}
	
	/**
	 *  Schedule a step of the agent.
	 *  May safely be called from external threads.
	 *  @param step	Code to be executed as a step of the agent.
	 *  @return The result of the step.
	 */
	public IFuture scheduleStep(final IComponentStep step)
	{
		final Future ret = new Future();
		
		if(adapter.isExternalThread())
		{
			try
			{
				adapter.invokeLater(new Runnable() 
				{
					public void run() 
					{
						application.scheduleStep(step).addResultListener(new DelegationResultListener(ret));
					}
				});
			}
			catch(Exception e)
			{
				ret.setException(e);
			}
		}
		else
		{
			application.scheduleStep(step).addResultListener(new DelegationResultListener(ret));
		}
		
		return ret;
	}
	
	/**
	 *  Get the string representation.
	 */
	public String toString()
	{
		return "ExternalAccess(comp=" + tostring + ")";
	}

}
