package com.neoutsa.mapapp.models;

import java.io.Serializable;

/** Course class from UML class diagram. */
public class Course implements Serializable {
    private String className;       // e.g. "CS 3773 001"
    private String teachingHour;    // e.g. "MW 10:00-11:15"
    private String roomNumber;      // e.g. "1.220"
    private String buildingName;    // e.g. "NPB"
    private String campusName;      // "Main" / "Downtown"
    private String professor;

    public Course(String className, String teachingHour, String roomNumber,
                  String buildingName, String campusName, String professor) {
        this.className = className;
        this.teachingHour = teachingHour;
        this.roomNumber = roomNumber;
        this.buildingName = buildingName;
        this.campusName = campusName;
        this.professor = professor;
    }

    public String getClassName() { return className; }
    public String getTeachingHour() { return teachingHour; }
    public String getRoomNumber() { return roomNumber; }
    public String getBuildingName() { return buildingName; }
    public String getCampusName() { return campusName; }
    public String getProfessor() { return professor; }

    public String displayCourseInfo() {
        return className + "  |  " + buildingName + " " + roomNumber +
                "  |  " + teachingHour + "  |  Prof. " + professor +
                "  |  " + campusName + " Campus";
    }
}
