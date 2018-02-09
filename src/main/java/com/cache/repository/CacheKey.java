package com.cache.repository;

/**
 * 
 * @author gsingh
 *
 * @param <NS>
 * @param <K>
 * @param <RET>
 */
public interface CacheKey<K, RET> {
	public RET getCacheKey(K... args);
}
