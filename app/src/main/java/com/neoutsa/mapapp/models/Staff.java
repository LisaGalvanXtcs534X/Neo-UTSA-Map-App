package com.neoutsa.mapapp.models;

/** Staff extends User per UML class diagram. */
public class Staff extends User {
    private String employeeId;

    public Staff(String name, String email, String password, String employeeId) {
        super(name, email, password);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() { return employeeId; }
}
