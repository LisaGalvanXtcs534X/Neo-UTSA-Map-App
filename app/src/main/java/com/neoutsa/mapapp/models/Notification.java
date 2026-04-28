package com.neoutsa.mapapp.models;

import java.io.Serializable;

/** Notification class from UML class diagram. */
public class Notification implements Serializable {
    private static int nextId = 1;
    private final int notificationId;
    private final String title;
    private final String message;
    private final String severity; // "Info" / "Warning" / "Critical"
    private final String time;     // simple display string

    public Notification(String title, String message, String severity, String time) {
        this.notificationId = nextId++;
        this.title = title;
        this.message = message;
        this.severity = severity;
        this.time = time;
    }

    public int getNotificationId() { return notificationId; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getSeverity() { return severity; }
    public String getTime() { return time; }
}
