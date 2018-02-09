package com.cache.repository;

import java.util.concurrent.ExecutionException;

/**
 * string CRUD repository
 *
 */
public class StringCrudRepositoryImpl extends BasicCRUD implements CrudRepository<String, String> {

	public StringCrudRepositoryImpl(String namespace, String configFile) {
		super(namespace, configFile);
	}

	public boolean isKeyExist(String key) {
		return (getSession().get(key) != null ? true : false);
	}

	public String getValue(String key) {
		return (String) getSession().get(key);
	}

	public String saveUpdate(String key, String value) {
		getSession().set(key, 5000, value);
		return value;
	}

	public String saveUpdate(String key, String value, int expireTime) {
		getSession().set(key, expireTime, value);
		return value;
	}

	public Boolean delete(String key) {
		try {
			return getSession().delete(key).get().booleanValue();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Long increment(String key, long intVal) {
		return getSession().incr(key, intVal);
	}

	public Long decrement(String key, long intVal) {
		return getSession().decr(key, intVal);
	}

}
