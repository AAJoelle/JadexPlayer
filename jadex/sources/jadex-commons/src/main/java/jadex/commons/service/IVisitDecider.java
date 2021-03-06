package jadex.commons.service;

import java.util.Collection;

/**
 *  Interface for deciding if a specific target service provider should be searched
 *  in a given search context.
 */
public interface IVisitDecider
{
	/**
	 *  Test if a specific node should be searched.
	 *  @param source The source data provider.
	 *  @param target The target data provider.
	 *  @param results The collection of preliminary results.
	 */
	public boolean searchNode(IServiceProvider source, IServiceProvider target, Collection results);
	
	/**
	 *  Get the cache key.
	 *  Needs to identify this element with respect to its important features so that
	 *  two equal elements should return the same key.
	 */
	public Object getCacheKey();
}
