package LeaveManagementSystem;

import java.io.Serializable;

/**
 * Model Class for Leave Requests.
 * 
 * WHY WE USE THIS:
 * 1. Encapsulation: All leave request data is bundled in one object
 * 2. Serializable: Can be saved to file and loaded later
 * 3. Separation of Concerns: Request data is separate from Employee business logic
 * 4. Easier to manage: HOD can review a list of LeaveRequest objects
 */
public class LeaveRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int employeeId;
    private String employeeName;
    private String startDate;      // Format: YYYY-MM-DD
    private String endDate;        // Format: YYYY-MM-DD
    private int totalDays;
    private String reason;
    private LeaveStatus status;
    private long submittedTimestamp;  // For ordering requests
    
    // Constructor
    public LeaveRequest(int employeeId, String employeeName, String startDate, 
                       String endDate, int totalDays, String reason) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalDays = totalDays;
        this.reason = reason;
        this.status = LeaveStatus.PENDING;  // Default status
        this.submittedTimestamp = System.currentTimeMillis();
    }
    
    // Getters
    public int getEmployeeId() { return employeeId; }
    public String getEmployeeName() { return employeeName; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public int getTotalDays() { return totalDays; }
    public String getReason() { return reason; }
    public LeaveStatus getStatus() { return status; }
    public long getSubmittedTimestamp() { return submittedTimestamp; }
    
    // Setters (only for status, other fields are immutable after creation)
    public void setStatus(LeaveStatus status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return String.format(
            "Employee: %s (ID: %d) | Dates: %s to %s (%d days) | Reason: %s | Status: %s",
            employeeName, employeeId, startDate, endDate, totalDays, reason, status
        );
    }
}
