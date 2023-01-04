package com.decoded.messaging.services;

import com.decoded.messaging.controllers.redis.RedisMessagePublisher;
import com.decoded.messaging.data.redis.MessageRedisDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    @Autowired
    private RedisMessagePublisher redisMessagePublisher;

    /**
     * @param messageRequest
     * @return
     */
    public MessageRedisDto logMessage(MessageRedisDto messageRequest) {

        messageRequest.getCategories().forEach((p) -> {
            redisMessagePublisher.publish(p, messageRequest.getMsn());
        });
        return messageRequest;
    }


}
