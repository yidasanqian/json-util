package io.github.yidasanqian.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class JacksonJsonMapper extends AbstractJsonMapper {

    public static ObjectMapper objectMapper;

    public JacksonJsonMapper() {
        // 禁止时间格式序列化为时间戳
        if (objectMapper == null) {
            objectMapper = new ObjectMapper()
                    .findAndRegisterModules()
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
    }

    @Override
    public Map toMap(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <K, V> Map<K, V> toMap(String json, Type type) {
        TypeReference<Map<K, V>> typeReference = new TypeReference<Map<K, V>>() {
            @Override
            public Type getType() {
                return type;
            }
        };
        try {
            return (Map<K, V>) objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List toList(String json) {
        try {
            return objectMapper.readValue(json, List.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> toList(String json, Type type) {
        TypeReference<T> typeReference = new TypeReference<T>() {
            @Override
            public Type getType() {
                return type;
            }
        };
        try {
            return (List<T>) objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toJsonString(Object object, String dateFormatPattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern);
        try {
            return objectMapper.writer(sdf).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T toObject(String json, Class<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public <K, V> Map<K, V> objectToMap(Object fromValue) {
        return objectMapper.convertValue(fromValue, Map.class);
    }

    @Override
    public <T> T mapToObject(Map fromMap, Class<T> toValueType) {
        return objectMapper.convertValue(fromMap, toValueType);
    }
}
