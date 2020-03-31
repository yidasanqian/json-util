package io.github.yidasanqian.base;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FastJsonMapper extends AbstractJsonMapper {

    @Override
    public Map toMap(String json) {
        Map result = null;
        try {
             result = objectMapper.readValue(json, LinkedHashMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public List toList(String json) {
        return null;
    }

    @Override
    public <T> List<T> toList(String json, Type type) {
        return null;
    }

    @Override
    public String toJsonString(Object object) {
        return null;
    }

    @Override
    public String toJsonWithDateFormat(Object object, String dateFormatPattern) {
        return null;
    }

    @Override
    public <T> T toPojo(String json, Class<T> valueType) {
        return null;
    }

    @Override
    public Map convertToMap(Object fromValue) {
        return null;
    }

    @Override
    public <T> T convertFromMap(Map fromMap, Class<T> toValueType) {
        return null;
    }
}
