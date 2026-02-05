package LeaveManagementSystem;

/**
 * Enum for Leave Request Status.
 * 
 * WHY WE USE THIS:
 * 1. Type Safety: Only valid statuses can be assigned (prevents typos like "PENDNG")
 * 2. Readability: More meaningful than using integers (0, 1, 2)
 * 3. Easy to extend: Can add more statuses like CANCELLED later
 */
public enum LeaveStatus {
    PENDING,    // Request submitted, awaiting HOD review
    APPROVED,   // HOD approved, leave balance deducted
    REJECTED    // HOD rejected, employee can submit new request
}
