package com.twinmask.gps.redis.comm;

import org.apache.commons.io.IOUtils;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 加载Lua脚本
 *
 * @author Leo
 */
public class LoadRedisLua<T> extends DefaultRedisScript<T> {

    static final Charset CHARSET = StandardCharsets.US_ASCII;

    public LoadRedisLua(String scriptClassPath, Class<T> resultType) {
        super();
        try (InputStream is = LoadRedisLua.class.getClassLoader().getResourceAsStream(scriptClassPath)) {
            assert is != null;
            setScriptText(IOUtils.toString(is, CHARSET));
            setResultType(resultType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
