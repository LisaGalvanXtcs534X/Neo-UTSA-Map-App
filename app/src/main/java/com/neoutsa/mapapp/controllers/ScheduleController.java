package com.neoutsa.mapapp.controllers;

import com.neoutsa.mapapp.models.Course;
import com.neoutsa.mapapp.models.Student;

import java.util.Collections;
import java.util.List;

/** ScheduleController per UML class diagram. */
public class ScheduleController {

    public static boolean submitSchedule(Student s, Course c) {
        if (s == null || c == null) return false;
        if (c.getClassName() == null || c.getClassName().trim().isEmpty()) return false;
        if (c.getBuildingName() == null || c.getBuildingName().trim().isEmpty()) return false;
        s.submitSchedule(c);
        return true;
    }

    public static List<Course> viewSchedule(Student s) {
        if (s == null) return Collections.emptyList();
        return s.getSemesterSchedule().getCourses();
    }

    public static boolean removeFromSchedule(Student s, Course c) {
        if (s == null || c == null) return false;
        s.getSemesterSchedule().removeCourse(c);
        return true;
    }
}
