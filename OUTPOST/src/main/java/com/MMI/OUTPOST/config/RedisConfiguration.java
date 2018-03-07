package com.MMI.OUTPOST.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
*
* @author Sameer Sharma
*/

@EnableCaching
@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {

    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        return new CustomCacheErrorHandler();
    }
    
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
      
	  /* RedisClusterConfiguration configuration = new  RedisClusterConfiguration();
	   RedisNode redisNode1 = new RedisNode("redis-intouch-0001-001.vr0nyh.0001.aps1.cache.amazonaws.com", 6379);
	  
	   RedisNode redisNode2 = new RedisNode("redis-intouch-0001-002.vr0nyh.0001.aps1.cache.amazonaws.com", 6379);
	  
	   configuration.addClusterNode(redisNode1);
	   configuration.addClusterNode(redisNode2);*/
	   
	   JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory (/*configuration*/);
	   redisConnectionFactory.setUsePool(true);
      // Defaults
      redisConnectionFactory.setHostName("127.0.0.1");
      redisConnectionFactory.setPort(6379);
      redisConnectionFactory.getPoolConfig().setMaxIdle(30);
      redisConnectionFactory.getPoolConfig().setMinIdle(10);
      return redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
      RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
      redisTemplate.setConnectionFactory(cf);
      return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
      RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

      // Number of seconds before expiration. Defaults to unlimited (0)
      cacheManager.setDefaultExpiration(300);
      return cacheManager;
    }
}