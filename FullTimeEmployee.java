package LeaveManagementSystem;

/**
 * Concrete Subclass: Full Time Employee.
 * INHERITANCE: Extends Employee.
 * POLYMORPHISM: Implements calculateAnnualLeave() specifically for full-timers.
 */
public class FullTimeEmployee extends Employee {
    
    public FullTimeEmployee(int id, String name) {
        super(id, name); // Calls the parent constructor
        this.type = "Full Time";
        calculateAnnualLeave(); // Set the balance immediately on creation
    }

    @Override
    public void calculateAnnualLeave() {
        // Full time employees get 20 days
        this.leaveBalance = 20;
    }
}
