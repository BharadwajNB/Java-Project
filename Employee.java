package LeaveManagementSystem;

import java.io.Serializable;

/**
 * The Abstract Base Class for Employees.
 * NOW EXTENDS USER for authentication capabilities.
 * 
 * WHY WE USE THIS:
 * 1. Abstraction: We don't want anyone to create just a generic "Employee". 
 *    They must create a specific type (FullTime or Contract).
 * 2. Encapsulation: Fields like 'leaveBalance' are private/protected 
 *    to prevent direct unauthorized access.
 * 3. Inheritance: Extends User for login capabilities, subclasses inherit common fields
 * 4. Serializable: Allows objects to be saved to file and loaded later.
 */
public abstract class Employee extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Encapsulated fields (protected allows subclasses to access them directly)
    protected int id;
    protected String name;
    protected int leaveBalance;
    protected String type; // To display "Full Time" or "Contract" easily
    
    // NEW: Track pending leave request (null if no pending request)
    protected LeaveRequest pendingRequest;

    // Modified constructor to include password for authentication
    public Employee(int id, String name, String password) {
        super(String.valueOf(id), password);  // Use ID as username
        this.id = id;
        this.name = name;
        this.leaveBalance = 0; // Default, will be set by subclasses
        this.pendingRequest = null;
    }

    // ABSTRACT METHOD (Polymorphism)
    // Every employee has leave, but the formatting/rules differ.
    // We force subclasses to implement this their own way.
    public abstract void calculateAnnualLeave();

    // NEW METHOD: Submit leave request (does NOT deduct balance yet)
    public LeaveRequest submitLeaveRequest(String startDate, String endDate, int days, String reason) 
            throws IllegalStateException {
        // Validation 1: Check if employee already has a pending request
        if (pendingRequest != null) {
            throw new IllegalStateException(
                "Error: You already have a pending leave request. " +
                "Wait for HOD approval/rejection before submitting a new one."
            );
        }
        
        // Validation 2: Check if employee would have enough balance IF approved
        if (days > leaveBalance) {
            throw new IllegalStateException(
                "Error: Insufficient leave balance. " +
                "Requested: " + days + " days, Available: " + leaveBalance + " days"
            );
        }
        
        // Validation 3: Basic input validation
        if (days <= 0) {
            throw new IllegalArgumentException("Leave days must be positive.");
        }
        
        // Create new leave request with PENDING status
        pendingRequest = new LeaveRequest(id, name, startDate, endDate, days, reason);
        
        System.out.println("✓ Leave request submitted successfully!");
        System.out.println("  Status: PENDING (awaiting HOD approval)");
        
        return pendingRequest;
    }
    
    // NEW METHOD: Check status of pending request
    public void checkLeaveStatus() {
        if (pendingRequest == null) {
            System.out.println("ℹ No pending leave requests.");
        } else {
            System.out.println("\n--- Your Leave Request Status ---");
            System.out.println(pendingRequest);
        }
    }
    
    // NEW METHOD: Clear pending request (called by HOD after approval/rejection)
    public void clearPendingRequest() {
        this.pendingRequest = null;
    }
    
    // NEW METHOD: Deduct leave balance (called by HOD on approval)
    public void deductLeaveBalance(int days) {
        this.leaveBalance -= days;
        System.out.println("  New leave balance for " + name + ": " + leaveBalance + " days");
    }

    // MODIFIED: Old requestLeave method - KEEPING FOR BACKWARD COMPATIBILITY
    // This is now deprecated but won't break existing code
    @Deprecated
    public void requestLeave(int days) throws InsufficientLeaveException {
        if (days <= 0) {
            throw new IllegalArgumentException("Leave days must be positive.");
        }
        
        if (days > leaveBalance) {
            // Throw our custom exception if balance is too low
            throw new InsufficientLeaveException(
                "Error: Employee " + name + " has Insufficient Leave. " +
                "Requested: " + days + ", Available: " + leaveBalance
            );
        }

        // If validated, update variable
        leaveBalance -= days;
        System.out.println("Success! Leave approved for " + name + ". New Balance: " + leaveBalance);
    }

    // Getters for displaying info
    public int getId() { return id; }
    public String getName() { return name; }
    public int getLeaveBalance() { return leaveBalance; }
    public String getType() { return type; }
    public LeaveRequest getPendingRequest() { return pendingRequest; }

    @Override
    public String toString() {
        String requestInfo = (pendingRequest != null) ? " | Pending Request: YES" : "";
        return "ID: " + id + " | Name: " + name + " | Type: " + type + 
               " | Leave Balance: " + leaveBalance + requestInfo;
    }
}
