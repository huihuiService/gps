package com.twinmask.gps.redis.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.Arrays;

@Order(Ordered.HIGHEST_PRECEDENCE + 10000)
public class AbstractRedisAutoConfiguration {

	static final Logger logger = LoggerFactory.getLogger(AbstractRedisAutoConfiguration.class);

	@Autowired
	protected ApplicationContext ctx;

    protected void check(RedisConnectionFactory redisConnectionFactory, String beanName) {
    	if (redisConnectionFactory == null) {
    		String[] names = ctx.getBeanDefinitionNames();
    		logger.info("Can't found: {}, beans: {}", beanName, Arrays.asList(names));
    	}
	}
}
