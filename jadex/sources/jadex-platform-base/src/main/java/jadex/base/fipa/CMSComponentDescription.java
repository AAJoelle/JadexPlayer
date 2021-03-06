package jadex.base.fipa;

import jadex.bridge.ComponentIdentifier;
import jadex.bridge.IComponentDescription;
import jadex.bridge.IComponentIdentifier;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 *  Java class for concept CMSComponentDescription
 *  of beanynizer_beans_fipa_new ontology.
 */
public class CMSComponentDescription implements IComponentDescription, Cloneable, Serializable
{
	//-------- attributes ----------

	/** Attribute for slot state. */
	protected String state;

	/** Attribute for slot processing state. */
	protected String processingstate;

	/** Attribute for slot component identifier. */
	protected IComponentIdentifier name;

	/** Attribute for slot parent. */
	protected IComponentIdentifier parent;
	
	/** Attribute for children. */
	protected Set children;

	/** Attribute for slot ownership. */
	protected String ownership;

	/** The component type. */
	protected String type;

	/** The breakpoints. */
	protected String[] breakpoints;
	
	/** The master flag. */
	protected Boolean master;
	
	/** The daemon flag. */
	protected Boolean daemon;
	
	/** The auto shutdown flag. */
	protected Boolean autoshutdown;
	
	/** The model name. */
	protected String modelname;
	
	//-------- constructors --------

	/**
	 *  Create a new CESComponentDescription.
	 */
	public CMSComponentDescription()
	{
	}

	/**
	 *  Create a new CESComponentDescription.
	 */
	public CMSComponentDescription(IComponentIdentifier cid, String type, IComponentIdentifier parent, 
		Boolean master, Boolean daemon, Boolean autoshutdown, String modelname)
	{
		this();
		setName(cid);
		setType(type);
		setParent(parent);
		setState(IComponentDescription.STATE_ACTIVE);
		setProcessingState(IComponentDescription.PROCESSINGSTATE_IDLE);
		setMaster(master);
		setDaemon(daemon);
		setAutoShutdown(autoshutdown);
		setModelName(modelname);
	}

	//-------- accessor methods --------

	/**
	 *  Get the state of this CESComponentDescription.
	 * @return state
	 */
	public String getState()
	{
		return this.state;
	}

	/**
	 *  Set the state of this CESComponentDescription.
	 * @param state the value to be set
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 *  Get the processing state of the component.
	 *  I.e. ready, running or blocked.
	 *  @return The processing state.
	 */
	public String getProcessingState()
	{
		return processingstate;
	}

	/**
	 *  Set the processing state of the component.
	 * @param processingstate the value to be set
	 */
	public void setProcessingState(String processingstate)
	{
		this.processingstate = processingstate;
	}
	
	/**
	 *  Get the componentidentifier of this CESComponentDescription.
	 * @return componentidentifier
	 */
	public IComponentIdentifier getName()
	{
		return this.name;
	}

	/**
	 *  Set the componentidentifier of this CESComponentDescription.
	 * @param name the value to be set
	 */
	public void setName(IComponentIdentifier name)
	{
		this.name = name;
	}

	/**
	 *  Get the identifier of the parent component (if any).
	 *  @return The parent component identifier.
	 */
	public IComponentIdentifier getParent()
	{
		return this.parent;
	}
	
	/**
	 *  Set the parent of this component description.
	 * @param parent the value to be set
	 */
	public void setParent(IComponentIdentifier parent)
	{
		this.parent = parent;
	}
	
	// CMS / external access / component may access description concurrently?!
	Object childmon = new Object();
	/**
	 *  Add a child component.
	 *  @param child The child component.
	 */
	public void addChild(IComponentIdentifier child)
	{
		synchronized(childmon)
		{
			if(children==null)
				children = new LinkedHashSet();
			children.add(child);
		}
	}
	
	/**
	 *  Remove a child component.
	 *  @param child The child component.
	 */
	public void removeChild(IComponentIdentifier child)
	{
		synchronized(childmon)
		{
			if(children!=null)
				children.remove(child);
		}
	}
	
	/**
	 *  Get the children.
	 *  @return The children.
	 */
	public IComponentIdentifier[] getChildren()
	{
		synchronized(childmon)
		{
			return children==null? new IComponentIdentifier[0]: (IComponentIdentifier[])children.toArray(new IComponentIdentifier[children.size()]);
		}
	}
	
	/**
	 *  Get the ownership of this CESComponentDescription.
	 * @return ownership
	 */
	public String getOwnership()
	{
		return this.ownership;
	}

	/**
	 *  Set the ownership of this CESComponentDescription.
	 * @param ownership the value to be set
	 */
	public void setOwnership(String ownership)
	{
		this.ownership = ownership;
	}

	/**
	 *  Get the component type.
	 *  @return The component type name (e.g. 'BDI Agent').
	 */
	public String getType()
	{
		return type;
	}
	
	/**
	 *  Set the component type.
	 *  @param type	The component type name (e.g. 'BDI Agent').
	 */
	public void setType(String type)
	{
		this.type	= type;
	}

	/**
	 *  Get the enabled breakpoints (if any).
	 *  @return The enabled breakpoints.
	 */
	public String[]	getBreakpoints()
	{
		return breakpoints!=null ? breakpoints : new String[0];
	}

	/**
	 *  Set the enabled breakpoints (if any).
	 *  @param breakpoints The enabled breakpoints.
	 */
	public void	setBreakpoints(String[] breakpoints)
	{
		this.breakpoints	= breakpoints;
	}
	
	/**
	 *  Get the master.
	 *  @return the master.
	 */
	public Boolean getMaster()
	{
		return master;
	}

	/**
	 *  Set the master.
	 *  @param master The master to set.
	 */
	public void setMaster(Boolean master)
	{
		this.master = master;
	}

	/**
	 *  Get the daemon.
	 *  @return the daemon.
	 */
	public Boolean getDaemon()
	{
		return daemon;
	}

	/**
	 *  Set the daemon.
	 *  @param daemon The daemon to set.
	 */
	public void setDaemon(Boolean daemon)
	{
		this.daemon = daemon;
	}

	/**
	 *  Get the autoshutdown.
	 *  @return the autoshutdown.
	 */
	public Boolean getAutoShutdown()
	{
		return autoshutdown;
	}

	/**
	 *  Set the autoshutdown.
	 *  @param autoshutdown The autoshutdown to set.
	 */
	public void setAutoShutdown(Boolean autoshutdown)
	{
		this.autoshutdown = autoshutdown;
	}

	/**
	 *  Get the model name.
	 *  @return The name.
	 */
	public String getModelName()
	{
		return modelname;
	}

	/**
	 *  Set the model name.
	 *  @param modelname The model name.
	 */
	public void setModelName(String modelname)
	{
		this.modelname = modelname;
	}
		
	//-------- methods --------

	/**
	 *  Test if this description equals another description.
	 */
	public boolean equals(Object o)
	{
		return o == this || o instanceof CMSComponentDescription && getName() != null && getName().equals(((CMSComponentDescription)o).getName());
	}

	/**
	 *  Get the hash code of this description.
	 */
	public int hashCode()
	{
		return getName() != null ? getName().hashCode() : 0;
	}

	/**
	 *  Get a string representation of this description.
	 */
	public String toString()
	{
		return "CMSComponentDescription(name=" + getName() + ", state=" + getState() + ", ownership=" + getOwnership() + ", parent=" + getParent() + ")";
	}

	/**
	 *  Clone a component description.
	 */
	public Object clone()
	{
		try
		{
			CMSComponentDescription ret = (CMSComponentDescription)super.clone();
			ret.setName((ComponentIdentifier)((ComponentIdentifier)name).clone());
			if(children!=null)
			{
				ret.children = new LinkedHashSet(); 
				ret.children.addAll(children);
			}
			return ret;
		}
		catch(CloneNotSupportedException e)
		{
			throw new RuntimeException("Cannot clone: " + this);
		}
	}
}
