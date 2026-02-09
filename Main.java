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
            System.out.println("3. Exit");
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
                String hodUsername = scanner.next(); // Use next() for single word
                System.out.print("Enter Password: ");
                String hodPassword = scanner.next(); // Use next() for single word
                scanner.nextLine(); // Consume remaining newline
                
                if (!hod.getUsername().equals(hodUsername) || !hod.authenticate(hodPassword)) {
                    System.out.println("❌ Invalid credentials!");
                    continue;
                }
                
                System.out.println("✓ Login successful! Welcome, HOD");
                hodMenu(system, hod, scanner);
                
                
            } else if (loginChoice == 3) {
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
                    System.out.print("Enter Reason: ");
                    String reason = scanner.nextLine();
                    
                    try {
                        // Calculate days from date range
                        int days = calculateDaysBetweenDates(startDate, endDate);
                        System.out.println("Calculated leave days: " + days);
                        
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
            System.out.println("4. Add New Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Logout");
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
                    // Approve/Reject request - show all pending requests first
                    ArrayList<LeaveRequest> requests = system.getAllPendingRequests();
                    if (requests.isEmpty()) {
                        System.out.println("ℹ No pending leave requests.");
                        break;
                    }
                    
                    System.out.println("\n--- Select a Leave Request ---");
                    for (int i = 0; i < requests.size(); i++) {
                        System.out.println((i + 1) + ". " + requests.get(i));
                    }
                    System.out.print("Enter request number (0 to cancel): ");
                    int reqNum = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    
                    if (reqNum == 0 || reqNum > requests.size()) {
                        System.out.println("Cancelled.");
                        break;
                    }
                    
                    LeaveRequest selectedRequest = requests.get(reqNum - 1);
                    Employee emp = system.findEmployeeById(selectedRequest.getEmployeeId());
                    
                    if (emp == null) {
                        System.out.println("❌ Employee not found in system!");
                        break;
                    }
                    
                    System.out.println("\n--- Request Details ---");
                    System.out.println(selectedRequest);
                    System.out.println("\n1. Approve");
                    System.out.println("2. Reject");
                    System.out.print("Choose action: ");
                    int action = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    
                    if (action == 1) {
                        try {
                            hod.approveLeaveRequest(selectedRequest, emp);
                            system.removePendingRequest(selectedRequest);
                        } catch (IllegalStateException e) {
                            System.out.println("❌ " + e.getMessage());
                        }
                    } else if (action == 2) {
                        hod.rejectLeaveRequest(selectedRequest, emp);
                        system.removePendingRequest(selectedRequest);
                    } else {
                        System.out.println("Invalid action.");
                    }
                    break;
                    
                case 3:
                    // View all employees
                    system.displayAllEmployees();
                    break;
                    
                case 4:
                    // Add new employee
                    addEmployeeMenu(system, scanner);
                    break;
                    
                case 5:
                    // Delete employee
                    system.displayAllEmployees();
                    System.out.print("\nEnter Employee ID to delete (0 to cancel): ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    
                    if (deleteId == 0) {
                        System.out.println("Cancelled.");
                        break;
                    }
                    
                    Employee empToDelete = system.findEmployeeById(deleteId);
                    if (empToDelete == null) {
                        System.out.println("❌ Employee not found!");
                        break;
                    }
                    
                    System.out.println("\n⚠ Are you sure you want to delete: " + empToDelete.getName() + "?");
                    System.out.println("1. Yes, Delete");
                    System.out.println("2. No, Cancel");
                    System.out.print("Choose: ");
                    int confirm = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    
                    if (confirm == 1) {
                        if (system.removeEmployee(deleteId)) {
                            System.out.println("✓ Employee deleted successfully.");
                        }
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                    break;
                    
                case 6:
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
    
    // HELPER METHOD: Calculate days between two dates (inclusive)
    private static int calculateDaysBetweenDates(String startDateStr, String endDateStr) {
        try {
            // Parse dates in YYYY-MM-DD format
            String[] startParts = startDateStr.split("-");
            String[] endParts = endDateStr.split("-");
            
            int startYear = Integer.parseInt(startParts[0]);
            int startMonth = Integer.parseInt(startParts[1]);
            int startDay = Integer.parseInt(startParts[2]);
            
            int endYear = Integer.parseInt(endParts[0]);
            int endMonth = Integer.parseInt(endParts[1]);
            int endDay = Integer.parseInt(endParts[2]);
            
            // Simple calculation using epoch days
            // This works for dates after 1970 and is accurate enough for leave management
            long startEpochDays = dateToEpochDays(startYear, startMonth, startDay);
            long endEpochDays = dateToEpochDays(endYear, endMonth, endDay);
            
            long daysDiff = endEpochDays - startEpochDays + 1; // +1 because both days are inclusive
            
            if (daysDiff <= 0) {
                throw new IllegalArgumentException("End date must be on or after start date!");
            }
            
            return (int) daysDiff;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Use YYYY-MM-DD (e.g., 2026-02-10)");
        }
    }
    
    // Helper to convert date to epoch days (days since 1970-01-01)
    private static long dateToEpochDays(int year, int month, int day) {
        // Simple approximation - works well enough for typical date ranges
        long days = 0;
        
        // Add days for complete years since 1970
        for (int y = 1970; y < year; y++) {
            days += isLeapYear(y) ? 366 : 365;
        }
        
        // Add days for complete months in current year
        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (isLeapYear(year)) {
            monthDays[1] = 29; // February has 29 days in leap year
        }
        
        for (int m = 1; m < month; m++) {
            days += monthDays[m - 1];
        }
        
        // Add the days in current month
        days += day;
        
        return days;
    }
    
    // Check if year is a leap year
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}
