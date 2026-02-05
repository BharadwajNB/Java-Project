package LeaveManagementSystem;

/**
 * Concrete Subclass: Contract Employee.
 * INHERITANCE: Extends Employee.
 */
public class ContractEmployee extends Employee {
    
    public ContractEmployee(int id, String name, String password) {
        super(id, name, password); // Calls the parent constructor with password
        this.type = "Contract";
        calculateAnnualLeave();
    }

    @Override
    public void calculateAnnualLeave() {
        // Contract employees get 10 days
        this.leaveBalance = 10;
    }
}
