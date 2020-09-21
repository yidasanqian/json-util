package io.github.yidasanqian.json;

import io.github.yidasanqian.json.domain.Order;
import io.github.yidasanqian.json.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JsonUtilTest {


    @Before
    public void setup() {
        // JsonUtil.initJson(JsonEnum.GSON);
    }

    public String parseJson(String name) {
        String json = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(getClass()
                    .getClassLoader()
                    .getResource(name)
                    .getFile()
            ));
            StringBuilder builder = new StringBuilder();
            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                builder.append(tmp);
            }
            json = builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Test
    public void testToMap() {
        String name = "json/user-with-address.json";
        String json = parseJson(name);
        Map<String, Object> result = JsonUtil.toMap(json);
        System.out.println("JsonUtilTest.testToMap: result ==> " + result);
    }

    @Test
    public void testToMapWithType() {
        String name = "json/user-with-address.json";
        String json = parseJson(name);
        TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {
        };
        Map result = JsonUtil.toMap(json, typeReference.getType());
        System.out.println("JsonUtilTest.testToMapWithType: result ==> " + result);
    }

    @Test
    public void testToList() {
        String name = "json/array.json";
        String json = parseJson(name);
        List result = JsonUtil.toList(json);
        System.out.println("JsonUtilTest.testToList: result ==> " + result);
    }

    @Test
    public void testToListWithType() {
        String name = "json/list.json";
        String json = parseJson(name);
        TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {
        };
        List<User> result = JsonUtil.toList(json, typeReference.getType());
        for (int i = 0; i < result.size(); i++) {
            User user = result.get(i);
            System.out.println("JsonUtilTest.testToListWithType: user ==> " + user);
        }
    }

    @Test
    public void testToJsonString() {
        User user = new User();
        user.setId(1);
        user.setUsername("yidasanqian");
        String result = JsonUtil.toJsonString(user);
        System.out.println("JsonUtilTest.testToJsonString: result ==> " + result);
    }

    @Test
    public void testToPojo() {
        String json = parseJson("json/user.json");
        User user = JsonUtil.toPojo(json, User.class);
        System.out.println("JsonUtilTest.testToPojo: user ==> " + user);
    }

    @Test
    public void testConvertToMap() {
        String json = parseJson("json/user.json");
        User pojoValue = JsonUtil.toPojo(json, User.class);
        Map<String, Object> propertyMap = JsonUtil.convertToMap(pojoValue);
        System.out.println("JsonUtilTest.testConvertToMap: propertyMap ==> " + propertyMap);
    }

    @Test
    public void testConvertFromMap() {
        String json = parseJson("json/user.json");
        User pojoValue = JsonUtil.toPojo(json, User.class);
        Map<String, Object> propertyMap = JsonUtil.convertToMap(pojoValue);
        User user = JsonUtil.convertFromMap(propertyMap, User.class);
        System.out.println("JsonUtilTest.testConvertFromMap: user ==> " + user);
    }

    @Test
    public void testDateFormat() {
        Date updateAt = new Date();
        System.out.println("JacksonTest.testDateFormat: updateAt ==> " + updateAt);
        Order order = new Order();
        order.setId(1);
        order.setTraceNo(110);
        order.setUpdateAt(updateAt);
        String orderJson = JsonUtil.toJsonString(order, "yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println("JacksonTest.testDateFormat: orderJson ==> " + orderJson);
        Assert.assertNotNull(orderJson);
    }

    @Test
    public void testLocalDateFormat() {
        Date updateAt = new Date();
        LocalDateTime modifiedAt = LocalDateTime.now();
        System.out.println("JacksonTest.testLocalDateFormat: updateAt ==> " + updateAt);
        System.out.println("JacksonTest.testLocalDateFormat: modifiedAt ==> " + modifiedAt);
        Order order = new Order();
        order.setId(1);
        order.setTraceNo(110);
        order.setUpdateAt(updateAt);
        order.setModifiedAt(modifiedAt);
        String orderJson = JsonUtil.toJsonString(order, "yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println("JacksonTest.testLocalDateFormat: orderJson ==> " + orderJson);
    }
}
