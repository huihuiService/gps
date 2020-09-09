package com.twinmask.gps.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Pool;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

/**
 * @author Leo
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RedisLettuceConnectionFactoryConfiguration {

    static final Logger logger = LoggerFactory.getLogger(RedisLettuceConnectionFactoryConfiguration.class);

    /**
     * 默认库
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisProperties redisProperties() {
        return new RedisProperties();
    }

    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        return newConnectionFactory(redisProperties);
    }

    /**
     * 默认ConnectionFactory
     */
    @Bean("baseRedisConnectionFactory")
    public RedisConnectionFactory baseRedisConnectionFactory(RedisProperties redisProperties) {
        return newConnectionFactory(redisProperties);
    }

    @Bean("gpsRedisProperties")
    @ConfigurationProperties(prefix = "spring.redis.gps")
    @ConditionalOnProperty(prefix = "spring.redis.gps", name = "host")
    public RedisProperties gpsRedisProperties() {
        return new RedisProperties();
    }

    @Bean("gpsRedisConnectionFactory")
    @ConditionalOnBean(name = "gpsRedisProperties")
    public RedisConnectionFactory gpsRedisConnectionFactory(@Qualifier("gpsRedisProperties") RedisProperties redisProperties) {
        return newConnectionFactory(redisProperties);
    }

    RedisConnectionFactory newConnectionFactory(RedisProperties redisProperties) {
        return newConnectionFactory(redisProperties, null);
    }

    RedisConnectionFactory newConnectionFactory(RedisProperties redisProperties, Integer database) {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
        standaloneConfiguration.setDatabase(database != null ? database.intValue() : redisProperties.getDatabase());
        standaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        Pool pool = redisProperties.getLettuce().getPool();
        if (pool != null) {
            poolConfig.setMinIdle(pool.getMinIdle());
            poolConfig.setMaxIdle(pool.getMaxIdle());
            poolConfig.setMaxTotal(pool.getMaxActive());
            if (pool.getMaxWait() != null) {
                poolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
            }
        }
        LettucePoolingClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(standaloneConfiguration, clientConfiguration);
        return connectionFactory;
    }

}
