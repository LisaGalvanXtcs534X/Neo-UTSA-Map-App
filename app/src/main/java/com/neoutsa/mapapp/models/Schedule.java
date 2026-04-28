package com.neoutsa.mapapp.models;

import java.util.ArrayList;
import java.util.List;

/** Schedule class from UML class diagram. */
public class Schedule {
    private static int nextId = 1;
    private final int scheduleId;
    private final List<Course> courses = new ArrayList<>();

    public Schedule() { this.scheduleId = nextId++; }

    public int getScheduleId() { return scheduleId; }
    public List<Course> getCourses() { return courses; }

    public void addCourse(Course c) { courses.add(c); }
    public void removeCourse(Course c) { courses.remove(c); }
    public List<Course> viewSchedule() { return courses; }
}
