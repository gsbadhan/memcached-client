package com.cache.repository;

/**
 * Basic CRUD repository.
 * 
 * @author gsingh
 *
 * @param <K>
 * @param <V>
 * @param <R>
 */
public interface CrudRepository<K, V> {

	/**
	 * check key exist
	 * 
	 * @param key
	 * @return
	 */
	public boolean isKeyExist(K key);

	/**
	 * find value by key
	 * 
	 * @param key
	 * @return
	 */
	public V getValue(K key);

	/**
	 * save and update key and value
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public V saveUpdate(K key, V value);

	/**
	 * save and update key and value with expire time in milliseconds.
	 * 
	 * @param key
	 * @param value
	 * @param expireTime
	 * @return
	 */
	public V saveUpdate(K key, V value, int expireTime);

	/**
	 * remove key and its value
	 * 
	 * @param key
	 * @return
	 */
	public Boolean delete(K key);

	/**
	 * Increment value
	 * 
	 * @param key
	 * @param intVal
	 * @return
	 */
	public Long increment(K key, long intVal);

	/**
	 * Decrement value
	 * 
	 * @param key
	 * @param intVal
	 * @return
	 */
	public Long decrement(K key, long intVal);
}
