package com.twinmask.gps.redis.gps;

import com.twinmask.gps.GpsInfo;
import com.twinmask.gps.redis.comm.AbstractRedisQueueService;
import com.twinmask.gps.redis.comm.RedisQueue;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author Leo
 */
public class RedisGpsQueueService extends AbstractRedisQueueService {

    final RedisConnectionFactory redisConnectionFactory;

    public RedisGpsQueueService(RedisConnectionFactory connectionFactory) {
        this.redisConnectionFactory = connectionFactory;
    }

    /**
     * GPS队列
     */
    public RedisQueue<GpsInfo> createOrGetVerifyDBQueue(String prefix) {
        return createOrGetQueue(prefix, redisConnectionFactory);
    }
}
