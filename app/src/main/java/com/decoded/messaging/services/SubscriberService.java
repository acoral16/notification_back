package com.decoded.messaging.services;

import com.decoded.messaging.Bridge.Email;
import com.decoded.messaging.Bridge.PushNotification;
import com.decoded.messaging.Bridge.SMS;
import com.decoded.messaging.data.models.Subscriber;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SubscriberService {


    public List<Subscriber> getSubscriber(){
        List<Subscriber> subscribers = new ArrayList<>();
        subscribers.add(new Subscriber("Mauricio", Arrays.asList("MOVIE", "SPORT"),
                Arrays.asList(SMS.class)));
        subscribers.add(new Subscriber("Manuel", Arrays.asList("FINANCE", "SPORT"),
                Arrays.asList(Email.class, PushNotification.class)));
        subscribers.add(new Subscriber("DArios", Arrays.asList("MOVIE", "SPORT", "FINANCE"),
                Arrays.asList(Email.class, PushNotification.class, SMS.class)));
        return subscribers;
    }

}
