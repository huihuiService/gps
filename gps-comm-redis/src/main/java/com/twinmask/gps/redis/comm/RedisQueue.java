package com.twinmask.gps.redis.comm;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.support.collections.DefaultRedisList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Leo
 */
public class RedisQueue<E> extends DefaultRedisList<E> {

    @SuppressWarnings("rawtypes")
    static final LoadRedisLua<List> LOAD_REDIS_LUA = new LoadRedisLua<>("com/twinmask/gps/redis/queue/queue_drainto.lua", List.class);

    protected final String PREFIX;

    protected final String KEY;

    private final RedisOperations<String, E> OPERATIONS;

    public RedisQueue(String prefix, String key, RedisOperations<String, E> operations) {
        super(prefix.concat(key), operations);
        this.PREFIX = prefix;
        this.KEY = key;
        this.OPERATIONS = operations;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null || c.isEmpty()) {
            return false;
        }
        BoundListOperations<String, E> listOps = OPERATIONS.boundListOps(PREFIX.concat(KEY));
        E[] array = (E[]) c.toArray(new Object[0]);
        Long count = listOps.rightPushAll(array);
        return count != null && count > 0;
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return this.drainTo(c, 5000);
    }

    @Override
    public int drainTo(Collection<? super E> c, int limit) {
//        final String redisKey = PREFIX.concat(KEY);
//        List<E> list = OPERATIONS.execute(LOAD_REDIS_LUA, new GenericToStringSerializer<Object>(Object.class),
//                (RedisSerializer<List>) OPERATIONS.getValueSerializer(), Collections.singletonList(redisKey), limit);
//        if (list != null && list.size() > 0) {
//            c.addAll(list);
//            return list.size();
//        }
        return 0;
    }
}
