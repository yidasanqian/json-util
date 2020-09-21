# json-util
[![Maven Central](https://img.shields.io/badge/maven--central-2.0.0-blue.svg)](http://search.maven.org/#artifactdetails%7Cio.github.yidasanqian%7Cjson-util%7C2.0.0%7Cjar)

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


# Usage
maven
```
<dependency>
    <groupId>io.github.yidasanqian</groupId>
    <artifactId>json-util</artifactId>
    <version>2.0.0</version>          
</dependency>
```
选择 jackson、gson and fastjson 其中之一引入 pom.xml：
```

<properties>
    <jackson.version>specify version</jackson.version>
    <gson.version>specify version</gson.version>
    <fastjson.version>specify version</fastjson.version>
</properties>
<dependencies>
        <!-- use jackson lib-->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind-jsr310</artifactId>
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

初始化时可以指定使用的Json库：
```
JsonUtil.initJson(JsonEnum.FASTJSON);
```
JsonEnum的值有`JACKSON`, `FASTJSON`, `GSON`

若未指定则自动查找用户引入的Json库(jackson, gson, fastjson)，若存在多个则默认使用Jackson作为json解析库。


解析到`List`的例子：
```
 String json = "[1, 2, 4, 5]";
 List result = JsonUtil.toList(json);
 
 ...
 json = "[{"id": 1,"username": "yidasanqian"},{"id": 2,"username": "yidasanqian2"}]"
 TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
 List<User> result = JsonUtil.toList(json, typeReference.getType());
```
解析到`Map`的例子：
```
String json = "{"id":1, "username":"yidasanqian"}"
TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {};
Map result = JsonUtil.toMap(json, typeReference.getType());
```
