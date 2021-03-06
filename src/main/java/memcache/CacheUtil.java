package memcache;

import net.rubyeye.xmemcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheUtil {

	private static MemcachedClient client = initalize();

	private static final Logger logger = LoggerFactory
			.getLogger(CacheUtil.class);

	private static MemcachedClient initalize() {
		return MemcacheWorkBench.getMemCachedClient();
	}

	/**
	 * 获取缓存,失败时返回null
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		try {
			return client.get(key);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 缓存固定时间，cacheTime 为秒
	 * 
	 * @param key
	 * @param value
	 * @param cacheTime
	 */
	public static void set(String key, Object value, int cacheTime) {
		try {
			client.set(key, cacheTime, value);
		} catch (Exception e) {
			logger.error("存缓存出错!", e);
		}
	}

	/**
	 * 默认存储，有效1个月
	 * 
	 * @param key
	 * @param value
	 */
	public static void set(String key, Object value) {
		try {
			client.set(key, 0, value);
		} catch (Exception e) {
			logger.error("存缓存出错!", e);
		}
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 */
	public static void delete(String key) {
		try {
			client.delete(key);
		} catch (Exception e) {
			logger.error("删缓存出错!", e);
		}
	}

	/**
	 * 清空缓存
	 */
	public static void clear() {
		try {
			client.flushAll();
		} catch (Exception e) {
			logger.error("清缓存出错!", e);
		}
	}
}
