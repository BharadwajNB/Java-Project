package LeaveManagementSystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Management Logic.
 * 
 * Uses COLLECTIONS (ArrayList) to store data in memory.
 */
public class LeaveManagementSystem {
    // Encapsulated list of employees
    private ArrayList<Employee> employees;

    public LeaveManagementSystem() {
        this.employees = new ArrayList<>();
    }

    // Add employee to the list
    public void addEmployee(Employee e) {
        employees.add(e);
        System.out.println("Employee Added Successfully: " + e.getName());
    }

    // Display all employees
    public void displayAllEmployees() {
        System.out.println("\n--- Employee List ---");
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee e : employees) {
                System.out.println(e); // Calls the toString() method we overrode
            }
        }
    }

    // Helper to find employee by ID
    public Employee findEmployeeById(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }
}
