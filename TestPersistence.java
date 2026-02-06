package LeaveManagementSystem;

import java.io.File;

public class TestPersistence {
    public static void main(String[] args) {
        String filename = "employees.dat";
        // 1. Delete existing file to start fresh
        new File(filename).delete();
        
        System.out.println("--- PHASE 1: Create and Save ---");
        LeaveManagementSystem system1 = new LeaveManagementSystem();
        system1.addEmployee(new FullTimeEmployee(2023, "TestEmployee", "password123"));
        
        System.out.println("--- PHASE 2: Reload and Find ---");
        LeaveManagementSystem system2 = new LeaveManagementSystem(); // Should load from file
        Employee e = system2.findEmployeeById(2023);
        
        if (e != null) {
            System.out.println("SUCCESS: Found Employee " + e.getId() + " (" + e.getName() + ")");
        } else {
            System.out.println("FAILURE: Employee 2023 NOT FOUND");
        }
    }
}
