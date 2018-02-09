package com.cache.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.spy.memcached.MemcachedClient;

public class CacheClientFactoryImplTest {
	private MemcachedClient cacheClient;

	@Before
	public void setUp() throws Exception {
		cacheClient = new CacheClientFactoryImpl("cache-config.json").getInstance();
	}

	@After
	public void tearDown() throws Exception {
		cacheClient.shutdown();
	}

	@Test
	public void testSetAndGet() throws Exception {
		String key = "1002";
		String value = "cache test";
		int ttl = 60000;
		cacheClient.add(key, ttl, value);
		Object out = cacheClient.get(key);
		assertNotNull(out);
		assertTrue(value.equals(out.toString()));
	}
}
