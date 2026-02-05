package LeaveManagementSystem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LeaveManagementSystem system = new LeaveManagementSystem();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== EMPLOYEE LEAVE SYSTEM ===");
            System.out.println("1. Add Full-Time Employee");
            System.out.println("2. Add Contract Employee");
            System.out.println("3. View All Employees");
            System.out.println("4. Request Leave");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int ftId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter Name: ");
                    String ftName = scanner.nextLine();
                    system.addEmployee(new FullTimeEmployee(ftId, ftName));
                    break;
                    
                case 2:
                    System.out.print("Enter ID: ");
                    int cId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter Name: ");
                    String cName = scanner.nextLine();
                    system.addEmployee(new ContractEmployee(cId, cName));
                    break;
                    
                case 3:
                    system.displayAllEmployees();
                    break;
                    
                case 4:
                    System.out.print("Enter Employee ID: ");
                    int empId = scanner.nextInt();
                    Employee emp = system.findEmployeeById(empId);
                    
                    if (emp != null) {
                        System.out.println("Current Balance: " + emp.getLeaveBalance());
                        System.out.print("Days to request: ");
                        int days = scanner.nextInt();
                        
                        try {
                            emp.requestLeave(days);
                        } catch (InsufficientLeaveException e) {
                            System.out.println("XXX LEAVE DENIED: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                             System.out.println("XXX INVALID INPUT: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Employee found!");
                    }
                    break;
                    
                case 5:
                    System.out.println("Exiting System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
