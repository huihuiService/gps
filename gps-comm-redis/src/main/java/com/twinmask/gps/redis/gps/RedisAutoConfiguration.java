package com.twinmask.gps.redis.gps;

import com.twinmask.gps.redis.comm.AbstractRedisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisAutoConfiguration extends AbstractRedisAutoConfiguration {

    @Autowired(required = false)
    @Qualifier("gpsRedisConnectionFactory")
    RedisConnectionFactory gpsRedisConnectionFactory;

    @Bean("gpsVerifyQueueService")
    public RedisGpsQueueService redisGpsQueueService() {
        check(gpsRedisConnectionFactory, "gpsRedisConnectionFactory");
        return new RedisGpsQueueService(gpsRedisConnectionFactory);
    }
}
