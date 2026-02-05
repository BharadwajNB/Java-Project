package LeaveManagementSystem;

/**
 * Concrete Subclass: Full Time Employee.
 * INHERITANCE: Extends Employee.
 * POLYMORPHISM: Implements calculateAnnualLeave() specifically for full-timers.
 */
public class FullTimeEmployee extends Employee {
    
    public FullTimeEmployee(int id, String name, String password) {
        super(id, name, password); // Calls the parent constructor with password
        this.type = "Full Time";
        calculateAnnualLeave(); // Set the balance immediately on creation
    }

    @Override
    public void calculateAnnualLeave() {
        // Full time employees get 20 days
        this.leaveBalance = 20;
    }
}
