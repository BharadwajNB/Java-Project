package LeaveManagementSystem;

/**
 * Custom Exception for Leave Management.
 * 
 * WHY WE USE THIS:
 * Standard Java exceptions (like IllegalArgumentException) are generic.
 * By creating our own exception, we can specifically catch errors related 
 * to leave balance (e.g., requesting 10 days when only 5 are left)
 * without confusing them with other system errors.
 * 
 * IMPLEMENTATION:
 * It extends 'Exception' making it a "Checked Exception".
 * This forces the programmer to handle it (try-catch) whenever they request leave.
 */
public class InsufficientLeaveException extends Exception {
    
    // Constructor that accepts a custom error message
    public InsufficientLeaveException(String message) {
        super(message); // Passes the message to the parent Exception class
    }
}
