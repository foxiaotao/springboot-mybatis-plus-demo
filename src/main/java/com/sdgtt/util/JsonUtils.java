package com.sdgtt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JSON工具类，默认使用Jackson
 * <p>
 * Created by WeiWei on 2017/7/31.
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 序列化
     *
     * @param data
     * @return
     */
    public static String serialize(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化
     *
     * @param dataString
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String dataString, TypeReference type) {
        try {
            return (T) objectMapper.readValue(dataString, type);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化
     *
     * @param dataString
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String dataString, Class<T> type) {
        try {
            return objectMapper.readValue(dataString, type);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换数据类型
     *
     * @param data
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T convert(Object data, Class<T> type) {
        return deserialize(JsonUtils.serialize(data), type);
    }

    /**
     * 转换数据类型
     *
     * @param data
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T convert(Object data, TypeReference type) {
        return deserialize(JsonUtils.serialize(data), type);
    }

    /**
     * 将对象转成Map
     *
     * @param data
     * @return
     */
    public static Map toMap(Object data) {
        return convert(data, Map.class);
    }

    /**
     * 将Map对象转成URL请求参数
     *
     * @param data
     * @return
     */
    public static String toParameter(Map<String, Object> data) {

        List<NameValuePair> parameters = new ArrayList<>();

        data.keySet().stream().forEach(key -> parameters.add(new BasicNameValuePair(key, String.class.isInstance(data.get(key)) ? String.valueOf(data.get(key)) : JsonUtils.serialize(data.get(key)))));

        return URLEncodedUtils.format(parameters, "UTF-8");

    }

    /**
     * 将Map对象转成URL请求参数
     *
     * @param data
     * @param isEncode
     * @return
     */
    public static String toParameter(Map<String, Object> data, boolean isEncode) {

        String parameter = toParameter(data);

        try {
            return !isEncode ? URLDecoder.decode(parameter, "UTF-8") : parameter;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

}
