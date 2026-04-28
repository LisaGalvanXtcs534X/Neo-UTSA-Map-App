package com.neoutsa.mapapp.data;

import com.neoutsa.mapapp.models.Building;
import com.neoutsa.mapapp.models.Course;
import com.neoutsa.mapapp.models.Notification;
import com.neoutsa.mapapp.models.Student;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory singleton acting as the app's database.
 * Seeded with demo data so the MVP runs without a backend.
 */
public class DataStore {
    private static DataStore instance;

    private final List<Student> students = new ArrayList<>();
    private final List<Notification> notifications = new ArrayList<>();
    private final Map<String, Building> mainCampus = new LinkedHashMap<>();
    private final Map<String, Building> downtownCampus = new LinkedHashMap<>();

    private Student currentStudent;

    // Settings
    private boolean notificationsEnabled = true;
    private boolean notificationSoundEnabled = true;
    private boolean gpsEnabled = true;
    private String currentCampus = "Main";

    private DataStore() { seed(); }

    public static synchronized DataStore get() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

    // -------- Auth helpers --------
    public List<Student> getStudents() { return students; }
    public Student getCurrentStudent() { return currentStudent; }
    public void setCurrentStudent(Student s) { this.currentStudent = s; }

    // -------- Notifications --------
    public List<Notification> getNotifications() { return notifications; }
    public void addNotification(Notification n) { notifications.add(0, n); }

    // -------- Maps --------
    public Map<String, Building> getMainCampus() { return mainCampus; }
    public Map<String, Building> getDowntownCampus() { return downtownCampus; }

    public Building findBuilding(String name) {
        for (Building b : mainCampus.values()) if (b.getName().equalsIgnoreCase(name)) return b;
        for (Building b : downtownCampus.values()) if (b.getName().equalsIgnoreCase(name)) return b;
        return null;
    }

    // -------- Settings --------
    public boolean isNotificationsEnabled() { return notificationsEnabled; }
    public void setNotificationsEnabled(boolean v) { this.notificationsEnabled = v; }
    public boolean isNotificationSoundEnabled() { return notificationSoundEnabled; }
    public void setNotificationSoundEnabled(boolean v) { this.notificationSoundEnabled = v; }
    public boolean isGpsEnabled() { return gpsEnabled; }
    public void setGpsEnabled(boolean v) { this.gpsEnabled = v; }
    public String getCurrentCampus() { return currentCampus; }
    public void setCurrentCampus(String c) { this.currentCampus = c; }

    // ------------- Seed -------------
    private void seed() {
        // Demo student matches the GUI mockup ("lisa.galvan@my.utsa.edu")
        Student lisa = new Student("Lisa Galvan", "lisa.galvan@my.utsa.edu",
                "utsa1234", "abc123");
        lisa.submitSchedule(new Course("ANT 4953 002", "TR 09:30-10:45",
                "0.404", "MH", "Main", "Dr. Rivera"));
        lisa.submitSchedule(new Course("CS 3333 001", "MW 11:00-12:15",
                "1.220", "NPB", "Main", "Dr. Patel"));
        lisa.submitSchedule(new Course("ART 1103 009", "F 13:00-15:50",
                "1.18", "AB", "Main", "Prof. Garza"));
        lisa.submitSchedule(new Course("CS 2233 003", "TR 14:00-15:15",
                "2.108", "NPB", "Main", "Dr. Kim"));
        students.add(lisa);

        // ---- Main campus buildings (normalized 0..1 coords) ----
        Building ssc = new Building("Student Success Center", "SSC", "Main",
                0.06f, 0.10f, 0.42f, 0.40f);
        Building mh = new Building("McKinney Humanities Building", "MH", "Main",
                0.46f, 0.10f, 0.92f, 0.42f);
        Building su = new Building("Student Union", "SU", "Main",
                0.18f, 0.50f, 0.86f, 0.92f);
        Building npb = new Building("North Paseo Building", "NPB", "Main",
                0.10f, 0.46f, 0.18f, 0.62f);
        seedStudentUnionRooms(su);
        seedHumanitiesRooms(mh);
        seedSscRooms(ssc);
        seedNpbRooms(npb);
        mainCampus.put(ssc.getShortCode(), ssc);
        mainCampus.put(mh.getShortCode(), mh);
        mainCampus.put(su.getShortCode(), su);
        mainCampus.put(npb.getShortCode(), npb);

        // ---- Downtown campus ----
        Building dt = new Building("UTSA Downtown Campus", "DT", "Downtown",
                0.20f, 0.40f, 0.80f, 0.75f);
        Building dev = new Building("Devonshire Condominiums", "DEV", "Downtown",
                0.30f, 0.10f, 0.55f, 0.22f);
        downtownCampus.put(dt.getShortCode(), dt);
        downtownCampus.put(dev.getShortCode(), dev);

        // ---- Seed past notifications ----
        notifications.add(new Notification(
                "(April 2nd) SafeZone",
                "Monthly Test of the UTSA Alerts System...",
                "Info", "Apr 2  10:00 AM"));
        notifications.add(new Notification(
                "(March 28th) Maintenance",
                "NPB chiller maintenance 10pm-2am. Building access limited.",
                "Warning", "Mar 28  6:15 PM"));
        notifications.add(new Notification(
                "(March 15th) Weather",
                "Severe thunderstorm warning until 7pm. Seek shelter indoors.",
                "Critical", "Mar 15  4:42 PM"));
    }

    private void seedStudentUnionRooms(Building b) {
        // Matches the GUI Student Union 2D map mockup
        b.addRoom(new Building.IndoorRoom("1.02.02", "Bookstore",
                0.18f, 0.10f, 0.78f, 0.55f));
        b.addRoom(new Building.IndoorRoom("1.00.46", "Campus Technology Store",
                0.02f, 0.30f, 0.18f, 0.55f));
        b.addRoom(new Building.IndoorRoom("1.02.01", "Union Perk",
                0.30f, 0.55f, 0.78f, 0.70f));
        b.addRoom(new Building.IndoorRoom("1.00.40", "Public Safety Community Support Center",
                0.30f, 0.70f, 0.55f, 0.92f));
        b.addRoom(new Building.IndoorRoom("1.00.38", "Rowdy Mart",
                0.55f, 0.70f, 0.78f, 0.92f));
    }

    private void seedHumanitiesRooms(Building b) {
        b.addRoom(new Building.IndoorRoom("0.404", "ANT 4953 Lecture Hall",
                0.10f, 0.10f, 0.55f, 0.40f));
        b.addRoom(new Building.IndoorRoom("0.420", "Faculty Offices",
                0.55f, 0.10f, 0.92f, 0.40f));
        b.addRoom(new Building.IndoorRoom("0.108", "Restrooms",
                0.10f, 0.50f, 0.30f, 0.70f));
        b.addRoom(new Building.IndoorRoom("0.110", "Elevators",
                0.30f, 0.50f, 0.50f, 0.70f));
        b.addRoom(new Building.IndoorRoom("0.220", "MH Auditorium",
                0.50f, 0.50f, 0.92f, 0.92f));
    }

    private void seedSscRooms(Building b) {
        b.addRoom(new Building.IndoorRoom("1.10", "Tutoring Services",
                0.10f, 0.10f, 0.55f, 0.45f));
        b.addRoom(new Building.IndoorRoom("1.20", "Advising Center",
                0.55f, 0.10f, 0.92f, 0.45f));
        b.addRoom(new Building.IndoorRoom("1.30", "Career Center",
                0.10f, 0.50f, 0.92f, 0.92f));
    }

    private void seedNpbRooms(Building b) {
        b.addRoom(new Building.IndoorRoom("1.220", "CS 3333 Classroom",
                0.10f, 0.10f, 0.92f, 0.45f));
        b.addRoom(new Building.IndoorRoom("2.108", "CS 2233 Classroom",
                0.10f, 0.50f, 0.92f, 0.92f));
    }
}
