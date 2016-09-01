/** 
 * 上海普景信息科技有限公司
 * 地址：上海市浦东新区祖冲之路899号 	
 * Copyright © 2013-2016 Puscene,Inc.All Rights Reserved.
 */
package cn.mwee.auto.auth.shiro.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/**
 * @author mengfanyuan
 * 2016年7月1日下午2:31:30
 */
public class MongDBCache<K, V> implements Cache<K, V> {
	@Override
	public V get(K key) throws CacheException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V put(K key, V value) throws CacheException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V remove(K key) throws CacheException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() throws CacheException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

}
