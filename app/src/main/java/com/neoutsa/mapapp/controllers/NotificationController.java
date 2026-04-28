package com.neoutsa.mapapp.controllers;

import com.neoutsa.mapapp.data.DataStore;
import com.neoutsa.mapapp.models.Notification;

import java.util.List;

/** NotificationController per UML class diagram. */
public class NotificationController {

    public static void modifyNotifications(boolean permission, boolean soundEnabled) {
        DataStore.get().setNotificationsEnabled(permission);
        DataStore.get().setNotificationSoundEnabled(soundEnabled);
    }

    public static void sendNotification(String title, String message, String severity, String time) {
        if (!DataStore.get().isNotificationsEnabled()) return;
        DataStore.get().addNotification(new Notification(title, message, severity, time));
    }

    public static List<Notification> viewPastNotifications() {
        return DataStore.get().getNotifications();
    }
}
