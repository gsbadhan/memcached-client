package com.cache.client;

import net.spy.memcached.MemcachedClient;

/**
 * 
 *
 */
public interface CacheClientFactory {

	/**
	 * initialize connection pool.
	 */
	public void init();

	/*
	 * Retrieves the instance
	 */
	public MemcachedClient getInstance();
	
	/**
	 * shutdown all connections.
	 */
	public void shutdown();

}
