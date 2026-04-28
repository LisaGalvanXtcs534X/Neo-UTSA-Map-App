package com.neoutsa.mapapp.models;

/** Student extends User per UML class diagram. */
public class Student extends User {
    private String abc123;
    private Schedule semesterSchedule;

    public Student(String name, String email, String password, String abc123) {
        super(name, email, password);
        this.abc123 = abc123;
        this.semesterSchedule = new Schedule();
    }

    public String getAbc123() { return abc123; }
    public Schedule getSemesterSchedule() { return semesterSchedule; }

    public void viewSchedule() { /* delegated to ScheduleController */ }
    public void submitSchedule(Course c) { semesterSchedule.addCourse(c); }
}
