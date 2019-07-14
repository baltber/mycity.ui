package ru.mycity.ui.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MarshallerHelper<T> {

    public JsonNode convertToJson(T obj){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.convertValue(obj, JsonNode.class);
    }
}
