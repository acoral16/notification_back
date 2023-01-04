package com.decoded.messaging.controllers.redis;

import com.decoded.messaging.services.SubscriberService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisFinanceSubscriber implements MessageListener {

    private String category;
    private SubscriberService subscriberService;

    public RedisFinanceSubscriber(){}

    public RedisFinanceSubscriber(String cat, SubscriberService subscriberService){
        this.category = cat;
        this.subscriberService = subscriberService;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        subscriberService.getSubscriber().stream()
                .filter(t -> t.getSubscribed().stream().anyMatch(w -> w.equals(category)))
                .forEach((sub) ->{
                    System.out.println("Message received : " + message.toString()+ " - " + category + " - " + sub.getName());
                });
    }

}

