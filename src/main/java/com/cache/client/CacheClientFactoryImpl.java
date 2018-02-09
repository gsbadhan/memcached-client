package com.cache.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

/**
 * cache connection factory;
 * 
 *
 */
public class CacheClientFactoryImpl implements CacheClientFactory {
	private static final Logger log = LoggerFactory.getLogger(CacheClientFactoryImpl.class);
	private static MemcachedClient client;
	private String configFileName;

	public CacheClientFactoryImpl(String configFile) {
		super();
		this.configFileName = configFile;
	}

	@SuppressWarnings("unchecked")
	public void init() {
		LinkedTreeMap<String, Object> cacheConfig;
		InputStream fileStream = null;
		try {
			if (configFileName.contains(File.separator)) {
				log.info("cache-config path:{} ", configFileName);
				fileStream = new FileInputStream(new File(configFileName));
				cacheConfig = new Gson().fromJson(new InputStreamReader(fileStream), LinkedTreeMap.class);
			} else {
				cacheConfig = new Gson().fromJson(
						new InputStreamReader(
								CacheClientFactoryImpl.class.getClassLoader().getResourceAsStream(configFileName)),
						LinkedTreeMap.class);
			}

			AuthDescriptor authDescriptor = null;
			if (Boolean.valueOf(cacheConfig.get("auth.enable").toString()))
				authDescriptor = new AuthDescriptor(cacheConfig.get("auth").toString().split(","),
						new PlainCallbackHandler(cacheConfig.get("user").toString(),
								cacheConfig.get("password").toString()));

			ConnectionFactoryBuilder cf = new ConnectionFactoryBuilder().setAuthDescriptor(authDescriptor)
					.setProtocol(Protocol.BINARY);
			String[] servers = cacheConfig.get("servers").toString().split(",");
			List<InetSocketAddress> cacheIPs = AddrUtil.getAddresses(Arrays.asList(servers));
			client = new MemcachedClient(cf.build(), cacheIPs);
		} catch (IOException e) {
			log.error("error occured while loading cache-config..!! ", e);
		} finally {
			if (fileStream != null)
				try {
					fileStream.close();
				} catch (IOException e) {
					log.error("error occured while loading cache-config..!!", e);
				}
		}

	}

	public synchronized MemcachedClient getInstance() {
		if (client != null) {
			return client;
		} else {
			init();
			return client;
		}
	}

	public void shutdown() {
		client.shutdown();
	}

}
