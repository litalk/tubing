package com.tubing.dal;

import com.tubing.common.ObjectMapperUtils;
import com.tubing.dal.model.Entity;
import com.tubing.dal.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by kornfeld on 24/04/2016.
 */
@Component
public class EntityPersister {

    @Autowired
    RedisClient _client;

    public void insert(Entity entity) {

        Map<String, String> fields = getFields(entity);
        _client.hmset(entity.getUniqueId(), fields);
    }

    private Map<String, String> getFields(Entity entity) {

        return ObjectMapperUtils.to(ObjectMapperUtils.from(entity), Map.class);
    }
}
