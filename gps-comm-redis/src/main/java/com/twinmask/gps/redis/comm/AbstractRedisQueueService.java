package com.twinmask.gps.redis.comm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRedisQueueService {

    final ConcurrentHashMap<String, RedisQueue<?>> queueMap = new ConcurrentHashMap<>();

    protected <E> RedisQueue<E> createOrGetQueue(String prefix, RedisConnectionFactory redisConnectionFactory) {
        return createOrGetQueue(prefix, StringUtils.EMPTY, redisConnectionFactory);
    }

    @SuppressWarnings("unchecked")
    protected <E> RedisQueue<E> createOrGetQueue(String prefix, String key, RedisConnectionFactory redisConnectionFactory) {
        final String cacheKey = prefix.concat(key);
        RedisQueue<E> queue = (RedisQueue<E>) queueMap.get(cacheKey);
        if (queue == null) {
            queue = createQueue(prefix, key, redisConnectionFactory);
            RedisQueue<E> oldQueue = (RedisQueue<E>) queueMap.putIfAbsent(cacheKey, queue);
            if (oldQueue != null) {
                queue = oldQueue;
            }
        }
        return queue;
    }

    private <E> RedisQueue<E> createQueue(String prefix, String key, RedisConnectionFactory redisConnectionFactory) {
        return new RedisQueue<E>(prefix, key, getRedisTemplate(redisConnectionFactory));
    }

    private <E> RedisTemplate<String, E> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, E> redisTemplate = new RedisTemplate<String, E>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
