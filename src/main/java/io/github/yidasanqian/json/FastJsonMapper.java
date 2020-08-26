package io.github.yidasanqian.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class FastJsonMapper extends AbstractJsonMapper {

    public FastJsonMapper() {
    }

    @Override
    public Map toMap(String json) {
        return JSON.parseObject(json, Map.class);
    }

    @Override
    public <K, V> Map<K, V> toMap(String json, Type type) {
        TypeReference<Map<K, V>> typeReference = new TypeReference<Map<K, V>>() {
            @Override
            public Type getType() {
                return type;
            }
        };
        return JSON.parseObject(json, typeReference.getType());
    }

    @Override
    public List toList(String json) {
        return JSON.parseObject(json, List.class);
    }

    @Override
    public <T> List<T> toList(String json, Type type) {
        TypeReference<T> typeReference = new TypeReference<T>() {
            @Override
            public Type getType() {
                return type;
            }
        };
        return JSON.parseObject(json, typeReference.getType());
    }

    @Override
    public String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    @Override
    public String toJsonString(Object object, String dateFormatPattern) {
        return JSON.toJSONStringWithDateFormat(object, dateFormatPattern, SerializerFeature.WriteDateUseDateFormat);
    }

    @Override
    public <T> T toObject(String json, Class<T> valueType) {
        return JSON.parseObject(json, valueType);
    }

    @Override
    public <K, V> Map<K, V> objectToMap(Object fromValue) {
        String json = JSON.toJSONString(fromValue);
        return toMap(json);
    }

    @Override
    public <T> T mapToObject(Map fromMap, Class<T> toValueType) {
        String json = JSON.toJSONString(fromMap);
        return toObject(json, toValueType);
    }
}
