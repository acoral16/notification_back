package com.decoded.messaging.Bridge;

public class PushNotification implements NotificationSender {
    @Override
    public void sendNotification(String msn) {
        System.out.println(this.getClass().getName() + " Mensaje: " + msn);
    }
}
