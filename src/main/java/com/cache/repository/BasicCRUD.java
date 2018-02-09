package com.cache.repository;

import static com.google.common.base.Preconditions.checkNotNull;

import com.cache.client.CacheClientFactory;
import com.cache.client.CacheClientFactoryImpl;
import com.cache.core.Constants;

import net.spy.memcached.MemcachedClient;

public class BasicCRUD implements CacheKey<String, String> {
	protected static volatile CacheClientFactory clientFactory;
	private final String namespace;

	public BasicCRUD(final String namespace, final String configFile) {
		this.namespace = checkNotNull(namespace);
		initConfig(configFile);
	}

	public String getCacheKey(String... args) {
		StringBuilder keybuf = new StringBuilder(namespace);
		for (int i = 0; i < args.length; i++) {
			keybuf.append(Constants.AT_RATE).append(args[i]);
		}
		return keybuf.toString();
	}

	public MemcachedClient getSession() {
		return clientFactory.getInstance();
	}

	private static void initConfig(String configFile) {
		synchronized (BasicCRUD.class) {
			if (clientFactory == null) {
				clientFactory = new CacheClientFactoryImpl(configFile);
				clientFactory.init();
			}
		}
	}

}
