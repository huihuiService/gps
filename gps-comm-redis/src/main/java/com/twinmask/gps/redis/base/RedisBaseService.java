package com.twinmask.gps.redis.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Leo
 */
@SuppressWarnings({"unused", "unchecked"})
@Service
public class RedisBaseService {

    static final Logger logger = LoggerFactory.getLogger(RedisBaseService.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 写入缓存
     *
     * @param key   键
     * @param value 值
     * @return 是否写入成功
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) redisTemplate.getConnectionFactory();

            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            redisTemplate.opsForList();
            operations.set(key, value);
            result = true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存并设置时效
     *
     * @param key        键
     * @param value      值
     * @param expireTime 失效时间
     * @return 是否写入成功
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            setExpire(key, expireTime);
            result = true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key 键
     * @return 返回值
     */
    public Object get(final String key) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            return operations.get(key);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置过期时间
     *
     * @param key        键
     * @param expireTime 过期时间(秒)
     * @return 是否设置成功
     */
    public boolean setExpire(final String key, long expireTime) {
        Boolean result = null;
        try {
            result = redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result == null ? false : result;
    }

    /**
     * 获得过期时间
     *
     * @param key 键
     * @return 返回过期时间(秒)
     */
    public Long getExpire(final String key) {
        Long result = null;
        try {
            result = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的键值
     *
     * @param keys 多个键
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 删除单个的键值
     *
     * @param key 键
     */
    public Boolean remove(final String key) {
        Boolean result = null;
        try {
            if (exists(key)) {
                result = redisTemplate.delete(key);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result == null ? false : result;
    }

    /**
     * 判断缓存中是否有对应的键值
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean exists(final String key) {
        Boolean result = null;
        try {
            result = redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? false : result;
    }

    /**
     * 添加哈希数据
     *
     * @param key     主key
     * @param hashKey HashKey
     * @param value   值
     */
    public void hmSet(final String key, Object hashKey, Object value) {
        try {
            HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
            hash.put(key, hashKey, value);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取哈希数据
     *
     * @param key     主key
     * @param hashKey HashKey
     * @return HashValue
     */
    public Object hmGet(final String key, Object hashKey) {
        try {
            HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
            return hash.get(key, hashKey);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加到List
     *
     * @param key   键
     * @param value 值
     * @return 受影响的行数
     */
    public long lPush(final String key, Object value) {
        Long result = null;
        try {
            ListOperations<String, Object> list = redisTemplate.opsForList();
            result = list.rightPush(key, value);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result == null ? 0 : result;
    }

    /**
     * 获取List长度
     *
     * @param key 键
     * @return 列表长度
     */
    public long lSize(final String key) {
        Long result = null;
        try {
            ListOperations<String, Object> list = redisTemplate.opsForList();
            result = list.size(key);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result == null ? 0 : result;
    }

    /**
     * 列表List
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @return 集合列表
     */
    public List<Object> lRange(final String key, long start, long end) {
        try {
            ListOperations<String, Object> list = redisTemplate.opsForList();
            return list.range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加Set集合
     *
     * @param key   键
     * @param value 值
     * @return 受影响的行数
     */
    public long setAdd(final String key, Object value) {
        Long result = null;
        try {
            SetOperations<String, Object> set = redisTemplate.opsForSet();
            result = set.add(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? 0 : result;
    }

    /**
     * 获取Set集合
     *
     * @param key 键
     * @return 返回Set集合
     */
    public Set<Object> setGet(final String key) {
        try {
            SetOperations<String, Object> set = redisTemplate.opsForSet();
            return set.members(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加有序Set集合
     *
     * @param key   键
     * @param value 值
     * @param score 分数值(可用作比较获得区间数据)
     * @return 返回添加结果
     */
    public boolean zSetAdd(final String key, Object value, double score) {
        Boolean result = null;
        try {
            ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
            result = zSetOperations.add(key, value, score);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == null ? false : result;
    }

    /**
     * 获取有序Set集合
     *
     * @param key        键
     * @param startScore 开始分数值
     * @param endScore   结束分数值
     * @return 返回有序Set集合
     */
    public Set<Object> zSetRangeByScore(final String key, double startScore, double endScore) {
        try {
            ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
            return zSetOperations.rangeByScore(key, startScore, endScore);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取有序Set集合的Score
     *
     * @param key   健
     * @param value 值
     * @return score 分数值
     */
    public Double zSetGetScore(final String key, Object value) {
        try {
            ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
            return zSetOperations.score(key, value);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 增加分数到有序Set集合
     *
     * @param key   键
     * @param value 值
     * @param score 分数值
     */
    public Double zSetIncrementScore(final String key, Object value, double score) {
        try {
            ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
            return zSetOperations.incrementScore(key, value, score);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过Lua脚本获取数据
     *
     * @param key      键
     * @param fileName Lua脚本文件名(需要文件后缀)
     * @return 0:cursor 1:List
     */
    public List<Object> setGetByLua(final String fileName, final String key, Object... objects) {
        DefaultRedisScript<List> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(fileName)));
        redisScript.setResultType(List.class);

        List<Object> keyList = new ArrayList<>();
        keyList.add(key);
        return (List<Object>) redisTemplate.execute(redisScript, keyList, objects);
    }
}
