package io.github.yidasanqian.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GsonMapper extends AbstractJsonMapper {
    public static Gson gson;
    public static GsonBuilder gsonBuilder;


    public GsonMapper() {
        gsonBuilder = new GsonBuilder()
                // 解决Gson序列化时出现整型变为浮点型的问题
                .registerTypeAdapter(new TypeToken<Map<Object, Object>>() {
                        }.getType(),
                        (JsonDeserializer<Map<Object, Object>>) (jsonElement, type, jsonDeserializationContext) -> {
                            Map<Object, Object> map = new LinkedHashMap<>();
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                            for (Map.Entry<String, JsonElement> entry : entrySet) {
                                Object obj = entry.getValue();
                                if (obj instanceof JsonPrimitive) {
                                    map.put(entry.getKey(), ((JsonPrimitive) obj).getAsString());
                                } else {
                                    map.put(entry.getKey(), obj);
                                }
                            }
                            return map;
                        }
                )
                .registerTypeAdapter(new TypeToken<List<Object>>() {
                        }.getType(),
                        (JsonDeserializer<List<Object>>) (jsonElement, type, jsonDeserializationContext) -> {
                            List<Object> list = new LinkedList<>();
                            JsonArray jsonArray = jsonElement.getAsJsonArray();
                            for (int i = 0; i < jsonArray.size(); i++) {
                                if (jsonArray.get(i).isJsonObject()) {
                                    JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                                    Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                                    list.addAll(entrySet);
                                } else if (jsonArray.get(i).isJsonPrimitive()) {
                                    list.add(jsonArray.get(i));
                                }
                            }
                            return list;
                        }
                );
        gson = gsonBuilder.create();
    }

    @Override
    public Map toMap(String json) {
        TypeToken<Map<Object, Object>> typeToken = new TypeToken<Map<Object, Object>>() {
        };
        return gson.fromJson(json, typeToken.getType());
    }

    @Override
    public <K, V> Map<K, V> toMap(String json, Type type) {
        TypeToken<Map<K, V>> typeToken = new TypeToken<Map<K, V>>() {
        };
        return gson.fromJson(json, typeToken.getType());
    }

    @Override
    public <T> List<T> toList(String json) {
        TypeToken<List<T>> typeToken = new TypeToken<List<T>>() {
        };
        return gson.fromJson(json, typeToken.getType());
    }

    @Override
    public <T> List<T> toList(String json, Type type) {
        return gson.fromJson(json, type);
    }

    @Override
    public String toJsonString(Object object) {
        return gson.toJson(object);
    }

    @Override
    public String toJsonString(Object object, String dateFormatPattern) {
        gson = gsonBuilder.setDateFormat(dateFormatPattern).create();
        return gson.toJson(object);
    }

    @Override
    public <T> T toObject(String json, Class<T> valueType) {
        return gson.fromJson(json, valueType);
    }

    @Override
    public <K, V> Map<K, V> objectToMap(Object fromValue) {
        TypeToken<Map<Object, Object>> typeToken = new TypeToken<Map<Object, Object>>() {
        };
        String json = gson.toJson(fromValue);
        return gson.fromJson(json, typeToken.getType());
    }

    @Override
    public <T> T mapToObject(Map fromMap, Class<T> toValueType) {
        String json = gson.toJson(fromMap);
        return toObject(json, toValueType);
    }
}
