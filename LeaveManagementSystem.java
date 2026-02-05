package LeaveManagementSystem;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * The Management Logic.
 * 
 * Uses COLLECTIONS (ArrayList) to store data in memory.
 * Uses FILE I/O (Serialization) to persist data across sessions.
 */
public class LeaveManagementSystem {
    // Encapsulated list of employees
    private ArrayList<Employee> employees;
    
    // File path for persistent storage
    private static final String DATA_FILE = "employees.dat";

    public LeaveManagementSystem() {
        this.employees = new ArrayList<>();
        loadData(); // Load existing data when system starts
    }

    // Add employee to the list
    public void addEmployee(Employee e) {
        employees.add(e);
        System.out.println("Employee Added Successfully: " + e.getName());
        saveData(); // Auto-save after adding
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
    
    // Save data to file
    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(employees);
            System.out.println("[Data saved to file]");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    
    // Load data from file
    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("[No existing data file found. Starting fresh.]");
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            employees = (ArrayList<Employee>) ois.readObject();
            System.out.println("[Data loaded from file: " + employees.size() + " employee(s) found]");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            employees = new ArrayList<>();
        }
    }
}
