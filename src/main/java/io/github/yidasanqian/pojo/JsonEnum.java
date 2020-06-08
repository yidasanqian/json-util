package io.github.yidasanqian.pojo;

import io.github.yidasanqian.base.AbstractJsonMapper;
import io.github.yidasanqian.base.FastJsonMapper;
import io.github.yidasanqian.base.GsonMapper;
import io.github.yidasanqian.base.JacksonJsonMapper;

public enum  JsonEnum {
    FAST_JSON("fastjson"),
    GSON("gson"),
    JACKSON("jackson");
    private String classType;

    JsonEnum() {
    }

    JsonEnum(String classType) {
        this.classType = classType;
    }
    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
    public static AbstractJsonMapper getJsonMapper() {
        for (JsonEnum item : JsonEnum.values()) {
            if (item.getClassType().equals("jackson")) {
                return new JacksonJsonMapper();
            }
            if (item.getClassType().equals("gson")) {
                return new GsonMapper();
            }
            if (item.getClassType().equals("fastjson")) {
                return new FastJsonMapper();
            }
        }
        return null;
    }
    public static JsonEnum getJsonMappeEnumr(String jsonName) {
        for (JsonEnum item : JsonEnum.values()) {
            if (jsonName.equals(FAST_JSON.classType)) {
                return FAST_JSON;
            }
            if (jsonName.equals(GSON.getClassType())) {
                return GSON;
            }
            if (jsonName.equals(JACKSON.classType)) {
                return JACKSON;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
