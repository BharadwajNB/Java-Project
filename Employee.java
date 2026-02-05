package LeaveManagementSystem;

/**
 * The Abstract Base Class.
 * 
 * WHY WE USE THIS:
 * 1. Abstraction: We don't want anyone to create just a generic "Employee". 
 *    They must create a specific type (FullTime or Contract).
 * 2. Encapsulation: Fields like 'leaveBalance' are private/protected 
 *    to prevent direct unauthorized access.
 * 3. Inheritance: Subclasses will inherit common fields (name, id) 
 *    so we don't write them twice.
 */
public abstract class Employee {
    // Encapsulated fields (protected allows subclasses to access them directly)
    protected int id;
    protected String name;
    protected int leaveBalance;
    protected String type; // To display "Full Time" or "Contract" easily

    // Constructor
    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
        this.leaveBalance = 0; // Default, will be set by subclasses
    }

    // ABSTRACT METHOD (Polymorphism)
    // Every employee has leave, but the formatting/rules differ.
    // We force subclasses to implement this their own way.
    public abstract void calculateAnnualLeave();

    // CONCRETE METHOD (Shared Logic)
    // The logic to deduct leave is the same for everyone, so we write it once here.
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

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Type: " + type + " | Leave Balance: " + leaveBalance;
    }
}
