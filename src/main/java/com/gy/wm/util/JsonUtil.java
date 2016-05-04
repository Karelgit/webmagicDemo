package com.gy.wm.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TianyuanPan on 5/4/16.
 */
public class JsonUtil {

    private static ObjectMapper objectMapper;

    /**
     *
     * @param model
     * @return
     */
    public static  String toJson(Object model) {
        String json = null;

        try {
            // ADD ROOT
            // Setting SerializationConfig.Feature.WRAP_ROOT_VALUE at mapper
            // did not read annotated label properly, use withRootName
            json = getObjectMapper().writeValueAsString(model);
            // ApiKey needs to be wrapped in a root node without the array
            // container, hack the standards!
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse model to object",
                    e);
        }

        return json;
    }

    public static <T> List<T> toObjects(
            String body, Class<T> clazz) throws JsonParseException,
            JsonMappingException, IOException {

        List<T> objs;
        CollectionType collectionType = TypeFactory.defaultInstance()
                .constructCollectionType(ArrayList.class, clazz);
        try {
            objs = getObjectMapper().readValue(body, collectionType);
        } catch (IOException e) {
            throw new RuntimeException(String.format(
                    "转换失败 [%s] to %s.", body, clazz), e);
        }

        return objs;
    }

    public static <T> T toObject(String body,
                                 Class<T> clazz) {

        T obj;

        try {
            obj = (T) getObjectMapper().readValue(body, clazz);
        } catch (IOException e) {
            throw new RuntimeException(String.format(
                    "Unable to parse [%s] to %s.", body, clazz), e);
        }

        return obj;
    }

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            ObjectMapper retval = new ObjectMapper();
            retval.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            retval.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            retval.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);
            retval.configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH,false);
            retval.configure(SerializationFeature.EAGER_SERIALIZER_FETCH, false);
            objectMapper = retval;
        }
        return objectMapper;
    }
}
