package LeaveManagementSystem;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

/**
 * The Management Logic.
 * 
 * Uses COLLECTIONS (ArrayList) to store data in memory.
 * Uses FILE I/O (Serialization) to persist data across sessions.
 * NOW MANAGES: Employees AND Leave Requests
 */
public class LeaveManagementSystem {
    // Encapsulated list of employees
    private ArrayList<Employee> employees;
    
    // NEW: Encapsulated list of all pending leave requests
    private ArrayList<LeaveRequest> allPendingRequests;
    
    // File paths for persistent storage
    private static final String EMPLOYEES_FILE = "employees.dat";
    private static final String REQUESTS_FILE = "leave_requests.dat";

    public LeaveManagementSystem() {
        this.employees = new ArrayList<>();
        this.allPendingRequests = new ArrayList<>();
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
    
    // NEW: Add pending request to the system-wide list
    public void addPendingRequest(LeaveRequest request) {
        allPendingRequests.add(request);
        saveData(); // Auto-save after adding request
    }
    
    // NEW: Get all pending leave requests
    public ArrayList<LeaveRequest> getAllPendingRequests() {
        return allPendingRequests;
    }
    
    // NEW: Remove a request from pending list (after approval/rejection)
    public void removePendingRequest(LeaveRequest request) {
        allPendingRequests.remove(request);
        saveData(); // Auto-save after removal
    }
    
    // MODIFIED: Save both employees and pending requests
    private void saveData() {
        // Save employees
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EMPLOYEES_FILE))) {
            oos.writeObject(employees);
            System.out.println("[Employee data saved to file]");
        } catch (IOException e) {
            System.out.println("Error saving employee data: " + e.getMessage());
        }
        
        // Save pending requests
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REQUESTS_FILE))) {
            oos.writeObject(allPendingRequests);
            System.out.println("[Leave request data saved to file]");
        } catch (IOException e) {
            System.out.println("Error saving request data: " + e.getMessage());
        }
    }
    
    // MODIFIED: Load both employees and pending requests
    @SuppressWarnings("unchecked")
    private void loadData() {
        // Load employees
        File empFile = new File(EMPLOYEES_FILE);
        if (!empFile.exists()) {
            System.out.println("[No existing employee data file found. Starting fresh.]");
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EMPLOYEES_FILE))) {
                employees = (ArrayList<Employee>) ois.readObject();
                System.out.println("[Employee data loaded: " + employees.size() + " employee(s) found]");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading employee data: " + e.getMessage());
                employees = new ArrayList<>();
            }
        }
        
        // Load pending requests
        File reqFile = new File(REQUESTS_FILE);
        if (!reqFile.exists()) {
            System.out.println("[No existing leave request data file found.]");
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(REQUESTS_FILE))) {
                allPendingRequests = (ArrayList<LeaveRequest>) ois.readObject();
                System.out.println("[Leave request data loaded: " + allPendingRequests.size() + " pending request(s)]");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading request data: " + e.getMessage());
                allPendingRequests = new ArrayList<>();
            }
        }
    }
}
