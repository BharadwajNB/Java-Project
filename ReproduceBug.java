package LeaveManagementSystem;

import java.io.*;
import java.util.ArrayList;

public class ReproduceBug {
    public static void main(String[] args) {
        // Setup
        String empFile = "employees.dat";
        String reqFile = "leave_requests.dat";
        new File(empFile).delete();
        new File(reqFile).delete();

        LeaveManagementSystem system = new LeaveManagementSystem();
        Employee emp = new FullTimeEmployee(101, "TestUser", "pass");
        system.addEmployee(emp);

        // Submit Request
        System.out.println("Submitting request...");
        LeaveRequest req = emp.submitLeaveRequest("2026-02-06", "2026-02-07", 2, "Test");
        system.addPendingRequest(req); // Save triggered

        // Simulate Restart (Reload Data)
        System.out.println("Reloading data...");
        system = new LeaveManagementSystem(); // Loads from files
        
        Employee loadedEmp = system.findEmployeeById(101);
        LeaveRequest loadedReqFromEmp = loadedEmp.getPendingRequest();
        
        ArrayList<LeaveRequest> systemPending = system.getAllPendingRequests();
        LeaveRequest loadedReqFromSystem = systemPending.get(0);

        System.out.println("Pending in System: " + systemPending.size());
        System.out.println("Request from Emp is equal to Request from System? " + 
                          (loadedReqFromEmp == loadedReqFromSystem));
        
        // Attempt Removal using Emp's reference (like HOD menu does)
        boolean removed = systemPending.remove(loadedReqFromEmp);
        System.out.println("Removed successfully? " + removed);
        
        if (!removed) {
            System.out.println("BUG CONFIRMED: Cannot remove request because object references differ and no equals() method.");
        } else {
            System.out.println("No bug found.");
        }
    }
}
