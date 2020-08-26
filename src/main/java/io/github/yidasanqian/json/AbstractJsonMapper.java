package io.github.yidasanqian.json;

import java.util.logging.Logger;

public abstract class AbstractJsonMapper implements JsonMapper {

    private static final Logger log = Logger.getLogger("AbstractJsonMapper");

    private static final String CLASS_TYPE_JACKSON = "com.fasterxml.jackson.databind.ObjectMapper";
    private static final String CLASS_TYPE_FASTJSON = "com.alibaba.fastjson.JSON";
    private static final String CLASS_TYPE_GSON = "com.google.gson.Gson";

    public static AbstractJsonMapper initJsonMapper(JsonEnum jsonEnum) {
        switch (jsonEnum) {
            case JACKSON:
                if (isPresent(CLASS_TYPE_JACKSON)) {
                    log.info("used jackson");
                    return new JacksonJsonMapper();
                } else {
                    log.info("jackson not found");
                }
            case FASTJSON:
                if (isPresent(CLASS_TYPE_FASTJSON)) {
                    log.info("used fastjson");
                    return new FastJsonMapper();
                } else {
                    log.info("fastjson not found");
                }
            case GSON:
                if (isPresent(CLASS_TYPE_GSON)) {
                    log.info("used gson");
                    return new GsonMapper();
                } else {
                    log.info("gson not found");
                }
            default:
                log.severe("未找到jackson、gson或fastjson的依赖");
                throw new RuntimeException("未找到jackson、gson或fastjson的依赖");
        }
    }

    private static boolean isPresent(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (IllegalAccessError err) {
            throw new IllegalStateException("Readability mismatch in inheritance hierarchy of class [" +
                    className + "]: " + err.getMessage(), err);
        } catch (Throwable ex) {
            // Typically ClassNotFoundException or NoClassDefFoundError...
            return false;
        }
    }
}
