package com.twinmask.gps.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimeZone;

public class JSON {

    private final static Logger logger = LoggerFactory.getLogger(JSON.class);

    public final static ObjectMapper MAPPER = new ObjectMapper();

    public final static boolean isDebug = false;

    static {
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.setTimeZone(TimeZone.getDefault());
        if (isDebug) {
            MAPPER.configure(SerializationFeature.INDENT_OUTPUT, true);
        }
    }

    private JSON() {

    }

    public static String toJSONString(Object object) {
        String result = null;
        try {
            result = MAPPER.writeValueAsString(object);

        } catch (Exception ex) {
            logger.error("toJSONString", ex);
        }
        return result;
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        T result = null;
        try {
            result = MAPPER.readValue(jsonStr, clazz);
        } catch (Exception ex) {
            logger.error("parseObject", ex);
        }
        return result;
    }

    public static <T> T parseObject(String jsonStr, TypeReference<T> typeReference) {
        T result = null;
        try {
            result = MAPPER.readValue(jsonStr, typeReference);
        } catch (Exception ex) {
            logger.error("parseObject", ex);
        }
        return result;
    }

    public static <T> T parseObject(String jsonStr, JavaType javaType) {
        T result = null;
        try {
            result = MAPPER.readValue(jsonStr, javaType);

        } catch (Exception ex) {
            logger.error("parseObject", ex);
        }
        return result;
    }

    public static JsonNode getNodeValue(String jsonStr,String fieldName){
        JsonNode result = null;
        try {
            JsonNode node = MAPPER.readValue(jsonStr, JsonNode.class);
            result = node.get(fieldName);
        } catch (Exception ex) {
            logger.error("parseObject", ex);
        }
        return result;
    }
}
