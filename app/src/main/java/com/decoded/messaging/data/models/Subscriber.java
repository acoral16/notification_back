package com.decoded.messaging.data.models;

import com.decoded.messaging.Bridge.NotificationSender;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Subscriber {

    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<String> subscribed;
    private List<Class<? extends NotificationSender>> channels;

    public Subscriber(String name, List<String> subscribed, List<Class<? extends NotificationSender>> notificationChanels){

        this.name = name;
        this.subscribed = subscribed;
        this.channels = notificationChanels;
    }

    public void sendNotification(String msn){

        getChannels().forEach((channel) -> {
            try {
                Class<NotificationSender> clazz = (Class<NotificationSender>) Class.forName(channel.getName());
                NotificationSender sender = clazz.getDeclaredConstructor().newInstance();
                sender.sendNotification(msn);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(List<String> subscribed) {
        this.subscribed = subscribed;
    }

    public List<Class<? extends NotificationSender>> getChannels() {
        return channels;
    }

    public void setChannels(List<Class<? extends NotificationSender>> channels) {
        this.channels = channels;
    }
}
