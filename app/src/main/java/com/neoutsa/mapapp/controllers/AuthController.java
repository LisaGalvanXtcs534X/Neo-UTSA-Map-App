package com.neoutsa.mapapp.controllers;

import com.neoutsa.mapapp.data.DataStore;
import com.neoutsa.mapapp.models.Student;

/** AuthController per UML class diagram. */
public class AuthController {

    /** Returns true on successful login. Loose validation for MVP. */
    public static boolean login(String emailOrAddress, String password) {
        if (emailOrAddress == null || password == null) return false;

        // 1. Try the seeded student
        for (Student s : DataStore.get().getStudents()) {
            if (s.login(emailOrAddress, password)) {
                DataStore.get().setCurrentStudent(s);
                return true;
            }
        }

        // 2. Permissive demo rule: any *@my.utsa.edu email + 6+ char pwd creates a session
        if (emailOrAddress.toLowerCase().endsWith("@my.utsa.edu") && password.length() >= 6) {
            Student demo = new Student(
                    emailOrAddress.split("@")[0].replace(".", " "),
                    emailOrAddress, password, "abc999");
            DataStore.get().getStudents().add(demo);
            DataStore.get().setCurrentStudent(demo);
            demo.setLoggedIn(true);
            return true;
        }
        return false;
    }

    public static void logout() {
        Student s = DataStore.get().getCurrentStudent();
        if (s != null) s.logout();
        DataStore.get().setCurrentStudent(null);
    }
}
