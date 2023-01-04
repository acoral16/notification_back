package com.decoded.messaging.data.redis;

import java.io.Serializable;
import java.util.List;

import com.decoded.messaging.data.entities.MessageStatus;
import com.decoded.messaging.data.entities.MessageType;

import org.springframework.data.redis.core.RedisHash;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@RedisHash("messages")
@Getter(value = AccessLevel.PUBLIC)
@Setter(value = AccessLevel.PUBLIC)
@ToString
public class MessageRedisDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> categories;
    private String msn;
   
}
