package com.decoded.messaging.controllers.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMessagePublisher  {
 
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

 
    public void publish(String topic, String message) { redisTemplate.convertAndSend(topic, message); }

}
