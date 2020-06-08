package io.github.yidasanqian.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public abstract class AbstractJsonMapper implements JsonMapper {
    private static final Logger LOGGER = Logger.getLogger("AbstractJsonMapper");


    public static final String JACKSON_CLASS_TYPE = "jackson";
    public static final String GSON_CLASS_TYPE = "gson";
    public static final String FASTJSON_CLASS_TYPE = "fastjson";
    private static final String CLASS_TYPE_OBJECT_MAPPER = "com.fasterxml.jackson.databind.ObjectMapper";
    private static final String CLASS_TYPE_GSON = "com.google.gson.Gson";
    private static final String CLASS_TYPE_FASTJSON = "com.alibaba.fastjson.JSON";
    public static String JSON_CLASS_TYPE = "json.class.type";

    public static String classType;
    public static ObjectMapper objectMapper;
    public static Gson gson;

    public static JsonEnum jsonEnum;

    static {
        try {
            String jsonClassType = null;
            Properties properties = new Properties();
            InputStream in = JsonUtil.class.getClassLoader().getResourceAsStream("application.properties");
            if (in != null) {
                properties.load(in);
                jsonClassType = properties.getProperty(JSON_CLASS_TYPE);
            } else {
                LOGGER.warning("未找到application.properties");
                in = JsonUtil.class.getClassLoader().getResourceAsStream("application.yml");
                if (in != null) {
                    Yaml yaml = new Yaml();
                    Map<String, Object> propsMap = yaml.loadAs(in, LinkedHashMap.class);
                    propsMap = (Map<String, Object>) propsMap.get("json");
                    if (propsMap != null) {
                        jsonClassType = String.valueOf(propsMap.get("class-type"));
                    } else {
                        LOGGER.warning("application.yml中未配置json.class-type");
                    }
                } else {
                    LOGGER.warning("未找到application.yml");
                }
            }

            if (jsonClassType != null && jsonClassType.trim().length() > 0) {
                classType = jsonClassType;
            }

            if (classType != null && classType.length() > 0) {
                if (Class.forName(CLASS_TYPE_OBJECT_MAPPER) != null && classType.equals(JACKSON_CLASS_TYPE)) {
                    LOGGER.info("use jackson lib");
                } else if (Class.forName(CLASS_TYPE_GSON) != null && classType.equals(GSON_CLASS_TYPE)) {
                    LOGGER.info("use gson lib");
                } else if (Class.forName(CLASS_TYPE_FASTJSON) != null && classType.equals(FASTJSON_CLASS_TYPE)) {
                    LOGGER.info("use fastjson lib");
                } else {
                    LOGGER.severe("未找到jackson、gson或fastjson的依赖");
                    throw new RuntimeException("未找到jackson、gson或fastjson的依赖");
                }
            } else if (Class.forName(CLASS_TYPE_OBJECT_MAPPER) != null) {
                LOGGER.info("use jackson lib");
                classType = "jackson";
            } else if (Class.forName(CLASS_TYPE_GSON) != null) {
                LOGGER.info("use gson lib");
                classType = "gson";
            } else if (Class.forName(CLASS_TYPE_FASTJSON) != null) {
                LOGGER.info("use fastjson lib");
                classType = "fastjson";
            } else {
                LOGGER.severe("未找到jackson、gson或fastjson的依赖");
                throw new RuntimeException("未找到jackson、gson或fastjson的依赖");
            }

            // 禁止时间格式序列化为时间戳
            if (objectMapper == null) {
                objectMapper = new ObjectMapper()
                        .findAndRegisterModules()
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            }
            jsonEnum = JsonEnum.getJsonMappeEnumr(classType);
            if (gson == null) {
                gson = new GsonBuilder()
                        .setLenient()
                        // 解决gson序列化时出现整型变为浮点型的问题
                        .registerTypeAdapter(new TypeToken<Map<Object, Object>>() {
                                }
                                        .getType(), (JsonDeserializer<Map<Object, Object>>) (jsonElement, type, jsonDeserializationContext) -> {
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
                                }
                                        .getType(), (JsonDeserializer<List<Object>>) (jsonElement, type, jsonDeserializationContext) -> {
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
                        )
                        .create();
            }
        } catch (ClassNotFoundException | IOException e) {
            LOGGER.severe(e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
