package com.tubing.common;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class ObjectMapperUtils {

    private static final ObjectMapper _mapper = new ObjectMapper();

    static {
        _mapper.setDateFormat(new SimpleDateFormat(TubingConstants.DATE_FORMAT));
        _mapper.setVisibility(_mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        _mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public static String from(Object entities) {

        String ret = null;
        try {
            ret = _mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(entities);
        } catch (IOException e) {
            throw new TubingException("Failed to serialize entities", e);
        }

        return ret;
    }

    public static <T> T to(String json, Class<T> clazz) {

        T ret = null;
        if (json != null && !json.isEmpty()) {
            try {
                ret = _mapper.readValue(json, clazz);
            } catch (IOException e) {
                throw new TubingException(String.format("Failed to de-serialize entity from <%s> to <%s>", json, clazz), e);
            }
        }

        return ret;
    }

    public static <T> T to(JsonNode node, Class<T> clazz) {

        T ret = null;
        if (node != null) {
            try {
                ret = _mapper.treeToValue(node, clazz);
            } catch (IOException e) {
                throw new TubingException(String.format("Failed to de-serialize entity from <%s> to <%s>", node.asText(), clazz), e);
            }
        }

        return ret;
    }
}
