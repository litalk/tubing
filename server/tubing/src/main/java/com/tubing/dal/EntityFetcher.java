package com.tubing.dal;

import com.tubing.common.ObjectMapperUtils;
import com.tubing.dal.model.Entity;
import com.tubing.dal.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by kornfeld on 25/04/2016.
 */
@Component
public class EntityFetcher {

    @Autowired
    RedisClient _client;

    public <TEntity extends Entity> TEntity get(String uniqueId, Class<TEntity> entityType) {

        Map<String, String> fields = _client.hgetAll(uniqueId);
        
        return ObjectMapperUtils.to(ObjectMapperUtils.from(fields), entityType);
    }
}
