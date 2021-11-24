package com.nia.assessment.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ObjectUtils {

    public static <T> String getSerializedObjectOrNull(T object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            ObjectWriter objectWriter = mapper.writer();
            return objectWriter.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
