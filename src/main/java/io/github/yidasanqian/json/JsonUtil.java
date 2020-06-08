package io.github.yidasanqian.json;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author yidasanqian
 */
public class JsonUtil {

    private static AbstractJsonMapper mapper = JsonEnum.getJsonMapper();

    /**
     * 解析json字符串到Map
     *
     * @param json 要解析的json字符串
     * @return 返回Map
     */
    public static Map toMap(String json) {
        return mapper.toMap(json);
    }

    /**
     * 解析json字符串到List
     *
     * @param json 要解析的json字符串
     * @return 返回List
     */
    public static List toList(String json) {
        return mapper.toList(json);
    }

    /**
     * 按指定的Type解析json字符串到List
     *
     * @param json 要解析的json字符串
     * @param type {@link Type}
     * @return 返回List
     */
    public static <T> List<T> toList(String json, final Type type) {
        return mapper.toList(json, type);
    }

    /**
     * 解析对象为Json字符串
     *
     * @param object 要转换的对象
     * @return 返回对象的json字符串
     */
    public static String toJsonString(Object object) {
        return mapper.toJsonString(object);
    }

    /**
     * 解析对象为Json字符串
     *
     * @param object            要转换的对象
     * @param dateFormatPattern 日期格式，如"yyyy年MM月dd日 HH时mm分ss秒"
     * @return 返回对象的json字符串
     */
    public static String toJsonWithDateFormat(Object object, String dateFormatPattern) {
        return mapper.toJsonWithDateFormat(object, dateFormatPattern);
    }

    /**
     * 解析json字符串到指定类型的对象
     *
     * @param json      要解析的json字符串
     * @param valueType 类对象class
     * @param <T>       泛型参数类型
     * @return 返回解析后的对象
     */
    public static <T> T toPojo(String json, Class<T> valueType) {
        return mapper.toPojo(json, valueType);
    }

    /**
     * 转换对象到Map
     *
     * @param fromValue 与Map可兼容的对象
     * @return 返回Map对象
     */
    public static Map convertToMap(Object fromValue) {
        return mapper.convertToMap(fromValue);
    }

    /**
     * 从Map转换到对象
     *
     * @param fromMap     Map对象
     * @param toValueType 与Map可兼容的对象类型
     * @param <T>         泛型参数类型
     * @return 返回Map转换得到的对象
     */
    public static <T> T convertFromMap(Map fromMap, Class<T> toValueType) {
        return mapper.convertFromMap(fromMap, toValueType);
    }
}
