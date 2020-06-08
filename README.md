# json-util
Json Util Integrate Jackson、Gson And Fastjson

json-util工具类整合了Jackson、Gson和Fastjson库并统一API。

# Env
- JDK8+

# Feature
统一调用API，形如`toXXX`。目前支持的转换的列表：
- List
- Map
- JsonString
- Pojo

List支持泛型，JsonString支持指定日期格式化，支持对象和Map的互相转换。

支持从`application.properties`或`application.yml`中设置解析Json的库(jackson, gson, fastjson)。

application.properties:
```
json.class.type=jackson
```
application.yml:
```
json:
  class-type: gson
```

支持自动查找用户引入的Json库(jackson, gson, fastjson)用来解析Json

# Usage
pom.xml
```
<properties>
    <jackson.version>2.10.1</jackson.version>
    <gson.version>2.8.6</gson.version>
    <fastjson.version>1.2.62</fastjson.version>
</properties>
<dependencies>
        <!-- use jackson lib-->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson.version}</version>          
        </dependency>
         <!-- or use gson lib-->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>${gson.version}</version>     
        </dependency>
         <!-- or use fastjson lib-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>${fastjson.version}</version>      
        </dependency>
    </dependencies>
```




默认从`application.properties`或`application.yml`中读取配置的Json库，

若未配置则自动查找用户引入的Json库(jackson, gson, fastjson)，若存在多个则默认使用Jackson作为json解析库。


解析到`List`的例子：
```
 String json = "[1, 2, 4, 5]";
 List result = JsonUtil.toList(json);
 
 ...
 json = "[{"id": 1,"username": "yidasanqian"},{"id": 2,"username": "yidasanqian2"}]"
 TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
 List<User> result = JsonUtil.toList(json, typeReference.getType());
```
