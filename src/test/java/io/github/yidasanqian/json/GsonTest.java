package io.github.yidasanqian.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import io.github.yidasanqian.json.domain.Order;
import io.github.yidasanqian.json.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GsonTest {

    Gson gson;
    FileReader userUrl, listUrl, arrayUrl, userAddressUrl, userOrdersUrl;

    @Before
    public void setup() throws FileNotFoundException {
        gson = new GsonBuilder()
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
                )
                .create();

        userUrl = new FileReader(getClass().getClassLoader().getResource("json/user.json").getFile());
        listUrl = new FileReader(getClass().getClassLoader().getResource("json/list.json").getFile());
        arrayUrl = new FileReader(getClass().getClassLoader().getResource("json/array.json").getFile());
        userAddressUrl = new FileReader(getClass().getClassLoader().getResource("json/user-with-address.json").getFile());
        userOrdersUrl = new FileReader(getClass().getClassLoader().getResource("json/user-with-orders.json").getFile());
    }

    @Test
    public void testJsonToMap() {
        TypeToken<Map<Object, Object>> typeToken = new TypeToken<Map<Object, Object>>() {
        };
        Map<String, Object> result = gson.fromJson(userUrl, typeToken.getType());
        System.out.println("GsonTest.testParseJson: result ==> " + result);
        Assert.assertNotNull(result);
        Assert.assertEquals("1", result.get("id"));
    }

    @Test
    public void testJsonToList() {
        List result = gson.fromJson(arrayUrl, List.class);
        System.out.println("GsonTest.testJsonToList: result ==> " + result);
        Assert.assertNotNull(result);
    }

    /**
     * gson解析数值类型会解析成小数，需要使用TypeToken指定泛型解决
     */
    @Test
    public void testJsonToList2() {
        TypeToken<List<Integer>> typeToken = new TypeToken<List<Integer>>() {
        };
        List<Integer> result = gson.fromJson(arrayUrl, typeToken.getType());
        System.out.println("GsonTest.testJsonToList2: result ==> " + result);
        Assert.assertNotNull(result);
    }

    @Test
    public void testJsonToArray() {
        int[] result = gson.fromJson(arrayUrl, int[].class);
        System.out.println("GsonTest.testJsonToArray: result ==> " + Arrays.toString(result));
        Assert.assertNotNull(result);
    }

    @Test
    public void testJsonToGenerifyList() {
        TypeToken<List<User>> typeToken = new TypeToken<List<User>>() {
        };
        List<User> result = gson.fromJson(listUrl, typeToken.getType());
        System.out.println("GsonTest.testJsonToGenerifyList: result ==> " + result);
        Assert.assertEquals(2, result.size());
        for (int i = 0; i < result.size(); i++) {
            User user = result.get(i);
            System.out.println("JacksonTest.testJsonToList: user == > " + user);
        }
    }

    @Test
    public void testJsonToPojo() {
        User user = gson.fromJson(userUrl, User.class);
        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getId());
    }

    @Test
    public void testPojoToJson() {
        User user = new User();
        user.setId(1);
        user.setUsername("yidasanqian");
        String userJson = gson.toJson(user);
        System.out.println("GsonTest.testPojoToJson: userJson ==> " + userJson);
        Assert.assertNotNull(userJson);
    }

    @Test
    public void testJsonToPojoWithReference() {
        User user = gson.fromJson(userAddressUrl, User.class);
        System.out.println("GsonTest.testJsonToPojoWithReference: user ==> " + user);
        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getId());
    }

    @Test
    public void testJsonToPojoWithList() {
        User user = gson.fromJson(userOrdersUrl, User.class);
        System.out.println("GsonTest.testJsonToPojoWithReference: orders ==> " + user.getOrders());
        Assert.assertNotNull(user.getOrders());
    }

    @Test
    public void testOrderToJson() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:dd");
        LocalDateTime now = LocalDateTime.now();
        String createAt = formatter.format(now);
        System.out.println("GsonTest.testOrderToJson: createAt ==> " + createAt);
        Order order = new Order();
        order.setId(1);
        order.setTraceNo(110);
        order.setCreateAt(createAt);
        String orderJson = gson.toJson(order);
        System.out.println("GsonTest.testOrderToJson: orderJson ==> " + orderJson);
        Assert.assertNotNull(orderJson);
    }

    @Test
    public void testOrderToJson2() {
        Date updateAt = new Date();
        System.out.println("GsonTest.testOrderToJson2: updateAt ==> " + updateAt);
        Order order = new Order();
        order.setId(1);
        order.setTraceNo(110);
        order.setUpdateAt(updateAt);
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:dd")
                .create();
        String orderJson = gson.toJson(order);
        System.out.println("GsonTest.testOrderToJson2: orderJson ==> " + orderJson);
        Assert.assertNotNull(orderJson);
    }
}
