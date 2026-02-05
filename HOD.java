package LeaveManagementSystem;

/**
 * HOD (Head of Department) Class.
 * 
 * WHY WE USE THIS:
 * 1. Role Separation: HOD has different privileges than Employee
 * 2. Inheritance: Extends User to get authentication capabilities
 * 3. Singleton Pattern: Only one HOD account exists (hardcoded)
 * 4. Business Logic: Contains approve/reject methods for leave requests
 */
public class HOD extends User {
    private static final long serialVersionUID = 1L;
    
    // Singleton instance - only one HOD exists
    private static HOD instance = null;
    
    // Private constructor to prevent external instantiation
    private HOD(String username, String password) {
        super(username, password);
    }
    
    /**
     * Get the singleton HOD instance.
     * Hardcoded credentials: username="hod", password="admin123"
     */
    public static HOD getInstance() {
        if (instance == null) {
            instance = new HOD("hod", "admin123");
        }
        return instance;
    }
    
    /**
     * Approve a leave request and deduct employee's leave balance.
     */
    public void approveLeaveRequest(LeaveRequest request, Employee employee) {
        // Defensive check: ensure employee has enough balance
        if (employee.getLeaveBalance() < request.getTotalDays()) {
            throw new IllegalStateException(
                "Cannot approve: Employee " + employee.getName() + 
                " has insufficient balance. Available: " + employee.getLeaveBalance() +
                ", Required: " + request.getTotalDays()
            );
        }
        
        // Update request status
        request.setStatus(LeaveStatus.APPROVED);
        
        // Deduct leave balance from employee
        employee.deductLeaveBalance(request.getTotalDays());
        
        // Clear pending request from employee
        employee.clearPendingRequest();
        
        System.out.println("✓ Approved: " + request.getEmployeeName() + "'s leave request for " + 
                          request.getTotalDays() + " days.");
    }
    
    /**
     * Reject a leave request.
     */
    public void rejectLeaveRequest(LeaveRequest request, Employee employee) {
        // Update request status
        request.setStatus(LeaveStatus.REJECTED);
        
        // Clear pending request so employee can submit a new one
        employee.clearPendingRequest();
        
        System.out.println("✗ Rejected: " + request.getEmployeeName() + "'s leave request.");
    }
}
