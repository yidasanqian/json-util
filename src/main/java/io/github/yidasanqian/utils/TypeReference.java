package io.github.yidasanqian.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class TypeReference<T> implements Comparable<TypeReference<T>> {

    private final Type type;
    private String jsonClassType;

  /*  private com.fasterxml.jackson.core.type.TypeReference jacksonTypeReference;
    private TypeToken<T> gsonTypeToken;
    private com.alibaba.fastjson.TypeReference<T> fastjsonTypeReference;*/

    public TypeReference() {
        this.jsonClassType = JsonUtil.classTypeCache.get(JsonUtil.JSON_CLASS_TYPE);
        Type superClass = getClass().getGenericSuperclass();
        // sanity check, should never happen
        if (superClass instanceof Class<?>) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        }
        /* 22-Dec-2008, tatu: Not sure if this case is safe -- I suspect
         *   it is possible to make it fail?
         *   But let's deal with specific
         *   case when we know an actual use case, and thereby suitable
         *   workarounds for valid case(s) and/or error to throw
         *   on invalid one(s).
         */
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];

        //setType();
    }

    /*private void setType() {
        if (JsonUtil.JACKSON_CLASS_TYPE.equals(jsonClassType)) {
            jacksonTypeReference = new com.fasterxml.jackson.core.type.TypeReference() {
                @Override
                public Type getType() {
                    return type;
                }
            };

        } else if (JsonUtil.GSON_CLASS_TYPE.equals(jsonClassType)) {
            gsonTypeToken = new TypeToken<T>(){};
        } else if (JsonUtil.FASTJSON_CLASS_TYPE.equals(jsonClassType)) {
            fastjsonTypeReference = new com.alibaba.fastjson.TypeReference<T>(){
                @Override
                public Type getType() {
                    return TypeReference.this.type;
                }
            };
        } else {
            throw new RuntimeException("未指定泛型参数");
        }
    }*/

    public Type getType() {
        return type;
    }

    @Override
    public int compareTo(TypeReference<T> o) {
        return 0;
    }
}
