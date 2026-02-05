package LeaveManagementSystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main Application Entry Point.
 * NOW WITH DUAL-ROLE LOGIN SYSTEM.
 * 
 * Two modes:
 * 1. EMPLOYEE MODE: Submit requests, check status, view balance
 * 2. HOD MODE: Review pending requests, approve/reject
 */
public class Main {
    public static void main(String[] args) {
        LeaveManagementSystem system = new LeaveManagementSystem();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            // LOGIN SCREEN
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║  LEAVE MANAGEMENT SYSTEM - LOGIN ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.println("1. Login as Employee");
            System.out.println("2. Login as HOD");
            System.out.println("3. Add New Employee (Setup)");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            
            int loginChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            if (loginChoice == 1) {
                // EMPLOYEE LOGIN
                System.out.print("Enter Employee ID: ");
                int empId = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                Employee employee = system.findEmployeeById(empId);
                if (employee == null) {
                    System.out.println("❌ Employee not found!");
                    continue;
                }
                
                System.out.print("Enter Password: ");
                String empPassword = scanner.nextLine();
                
                if (!employee.authenticate(empPassword)) {
                    System.out.println("❌ Invalid password!");
                    continue;
                }
                
                System.out.println("✓ Login successful! Welcome, " + employee.getName());
                employeeMenu(system, employee, scanner);
                
            } else if (loginChoice == 2) {
                // HOD LOGIN
                HOD hod = HOD.getInstance();
                
                System.out.print("Enter Username: ");
                String hodUsername = scanner.nextLine();
                System.out.print("Enter Password: ");
                String hodPassword = scanner.nextLine();
                
                if (!hod.getUsername().equals(hodUsername) || !hod.authenticate(hodPassword)) {
                    System.out.println("❌ Invalid credentials!");
                    continue;
                }
                
                System.out.println("✓ Login successful! Welcome, HOD");
                hodMenu(system, hod, scanner);
                
            } else if (loginChoice == 3) {
                // ADD NEW EMPLOYEE (SETUP)
                addEmployeeMenu(system, scanner);
                
            } else if (loginChoice == 4) {
                System.out.println("Exiting System. Goodbye!");
                scanner.close();
                System.exit(0);
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    // EMPLOYEE MENU
    private static void employeeMenu(LeaveManagementSystem system, Employee employee, Scanner scanner) {
        while (true) {
            System.out.println("\n=== EMPLOYEE MENU (" + employee.getName() + ") ===");
            System.out.println("1. View My Leave Balance");
            System.out.println("2. Submit Leave Request");
            System.out.println("3. Check Leave Request Status");
            System.out.println("4. Logout");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    // View balance
                    System.out.println("\n" + employee);
                    break;
                    
                case 2:
                    // Submit leave request
                    System.out.print("Enter Start Date (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine();
                    System.out.print("Enter End Date (YYYY-MM-DD): ");
                    String endDate = scanner.nextLine();
                    System.out.print("Enter Number of Days: ");
                    int days = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter Reason: ");
                    String reason = scanner.nextLine();
                    
                    try {
                        LeaveRequest request = employee.submitLeaveRequest(startDate, endDate, days, reason);
                        system.addPendingRequest(request);
                    } catch (IllegalStateException | IllegalArgumentException e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                    break;
                    
                case 3:
                    // Check status
                    employee.checkLeaveStatus();
                    break;
                    
                case 4:
                    // Logout
                    System.out.println("Logging out...");
                    return;
                    
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    // HOD MENU
    private static void hodMenu(LeaveManagementSystem system, HOD hod, Scanner scanner) {
        while (true) {
            System.out.println("\n=== HOD MENU ===");
            System.out.println("1. View All Pending Requests");
            System.out.println("2. Approve/Reject Leave Request");
            System.out.println("3. View All Employees");
            System.out.println("4. Logout");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    // View all pending requests
                    ArrayList<LeaveRequest> pending = system.getAllPendingRequests();
                    if (pending.isEmpty()) {
                        System.out.println("ℹ No pending leave requests.");
                    } else {
                        System.out.println("\n--- Pending Leave Requests ---");
                        for (LeaveRequest req : pending) {
                            System.out.println(req);
                        }
                    }
                    break;
                    
                case 2:
                    // Approve/Reject request
                    System.out.print("Enter Employee ID: ");
                    int empId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    
                    Employee emp = system.findEmployeeById(empId);
                    if (emp == null) {
                        System.out.println("❌ Employee not found!");
                        break;
                    }
                    
                    LeaveRequest request = emp.getPendingRequest();
                    if (request == null) {
                        System.out.println("ℹ No pending request for this employee.");
                        break;
                    }
                    
                    System.out.println("\nRequest Details:");
                    System.out.println(request);
                    System.out.println("\n1. Approve");
                    System.out.println("2. Reject");
                    System.out.print("Choose action: ");
                    int action = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    
                    if (action == 1) {
                        try {
                            hod.approveLeaveRequest(request, emp);
                            system.removePendingRequest(request);
                        } catch (IllegalStateException e) {
                            System.out.println("❌ " + e.getMessage());
                        }
                    } else if (action == 2) {
                        hod.rejectLeaveRequest(request, emp);
                        system.removePendingRequest(request);
                    } else {
                        System.out.println("Invalid action.");
                    }
                    break;
                    
                case 3:
                    // View all employees
                    system.displayAllEmployees();
                    break;
                    
                case 4:
                    // Logout
                    System.out.println("Logging out...");
                    return;
                    
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    // ADD EMPLOYEE MENU (SETUP)
    private static void addEmployeeMenu(LeaveManagementSystem system, Scanner scanner) {
        System.out.println("\n--- Add New Employee ---");
        System.out.println("1. Add Full-Time Employee");
        System.out.println("2. Add Contract Employee");
        System.out.print("Choose type: ");
        
        int type = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Set Password: ");
        String password = scanner.nextLine();
        
        if (type == 1) {
            system.addEmployee(new FullTimeEmployee(id, name, password));
        } else if (type == 2) {
            system.addEmployee(new ContractEmployee(id, name, password));
        } else {
            System.out.println("Invalid type.");
        }
    }
}
