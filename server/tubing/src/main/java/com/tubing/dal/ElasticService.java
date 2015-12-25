package com.tubing.dal;

import com.fasterxml.jackson.databind.JsonNode;
import com.tubing.common.ObjectMapperUtils;
import com.tubing.common.TubingException;
import com.tubing.dal.model.ElasticEntity;
import com.tubing.rest.RestClientImpl;
import com.tubing.rest.RestClientResponse;
import com.tubing.rest.RestRequest;

import java.io.IOException;

public class ElasticService {

    private static final String ELASTICSEARCH_HOST = "localhost";
    private static final String ELASTICSEARCH_PORT = "9200";
    private static final String INDEX = "tubing";

    public static <TElasticEntity extends ElasticEntity> RestClientResponse<String> insert(TElasticEntity entity) {

        return RestClientImpl.getInstance().post(new RestRequest(getUri(entity.getType()), ObjectMapperUtils.from(entity)));
    }

    public static <TElasticEntity extends ElasticEntity> TElasticEntity search(Class<TElasticEntity> clazz, String entityType, String query) {

        String url = String.format("%s/_search?q=%s", getUri(entityType), query);

        return toEntity(RestClientImpl.getInstance().get(new RestRequest(url)), clazz);
    }

    private static String getUri(String entityType) {

        return String.format("http://%s:%s/%s/%s", ELASTICSEARCH_HOST, ELASTICSEARCH_PORT, INDEX, entityType);
    }

    private static <TElasticEntity extends ElasticEntity> TElasticEntity toEntity(RestClientResponse<String> response, Class<TElasticEntity> clazz) {

        TElasticEntity ret = null;
        try {
            JsonNode node = ObjectMapperUtils.getJsonFactory().createParser(response.getEntity()).readValueAsTree();
            ret = ObjectMapperUtils.to(node.findValue("_source"), clazz);
        } catch (IOException e) {
            throw new TubingException("Failed parsing timestamp from response", e);
        }

        return ret;
    }
}
