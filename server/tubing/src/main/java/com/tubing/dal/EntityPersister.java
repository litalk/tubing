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

        _client.hmset(entity.getUniqueId(), getFields(entity));
    }

    public boolean delete(String key) {

        return _client.delete(key);
    }

    private Map<String, String> getFields(Entity entity) {

        return ObjectMapperUtils.to(ObjectMapperUtils.from(entity), Map.class);
    }
}
