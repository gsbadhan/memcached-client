package com.cache.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StringCrudRepositoryImplTest {
	private StringCrudRepositoryImpl stringCrudRepository;
	private final String namespace = "STR";
	private final String configFile = "cache-config.json";

	@Before
	public void setUp() throws Exception {
		stringCrudRepository = new StringCrudRepositoryImpl(namespace, configFile);
	}

	@SuppressWarnings("static-access")
	@After
	public void tearDown() throws Exception {
		stringCrudRepository.clientFactory.shutdown();
	}

	@Test
	public void testAllMethods() throws Exception {
		String key = "1709";
		String value1 = "inserting";
		String value2 = "updating";

		// check key exist
		Boolean isExist = stringCrudRepository.isKeyExist(key);
		assertFalse(isExist);

		// insert
		stringCrudRepository.saveUpdate(key, value1);
		String value1Out = stringCrudRepository.getValue(key);
		assertTrue(value1.equals(value1Out));

		// update with TTL 5000
		String value2out = stringCrudRepository.saveUpdate(key, value2, 5);
		assertTrue(value2.equals(value2out));

		// check TTL expire
		Thread.sleep(6000);
		value2out = stringCrudRepository.getValue(key);
		assertNull(value2out);

		// increment
		String key2 = "counter";
		int increment = 1;
		int decrement = 1;
		stringCrudRepository.saveUpdate(key2, "0");
		Long counterValue = stringCrudRepository.increment(key2, increment);
		assertTrue(counterValue == 1);
		counterValue = stringCrudRepository.increment(key2, increment);
		assertTrue(counterValue == 2);
		counterValue = stringCrudRepository.increment(key2, increment);
		assertTrue(counterValue == 3);
		// decrement
		counterValue = stringCrudRepository.decrement(key2, decrement);
		assertTrue(counterValue == 2);
		counterValue = stringCrudRepository.decrement(key2, decrement);
		assertTrue(counterValue == 1);

		// delete key2
		boolean deleted = stringCrudRepository.delete(key2);
		assertTrue(deleted);

	}

}
