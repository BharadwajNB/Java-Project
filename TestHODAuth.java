package LeaveManagementSystem;

/**
 * Quick test to debug HOD authentication
 */
public class TestHODAuth {
    public static void main(String[] args) {
        HOD hod = HOD.getInstance();
        
        System.out.println("HOD Username: " + hod.getUsername());
        System.out.println("Expected: hod");
        System.out.println();
        
        // Test password authentication
        String testPassword = "admin123";
        boolean result = hod.authenticate(testPassword);
        
        System.out.println("Testing password: " + testPassword);
        System.out.println("Authentication result: " + result);
        System.out.println();
        
        // Test the hash
        String hash1 = User.hashPassword("admin123");
        String hash2 = User.hashPassword("admin123");
        System.out.println("Hash 1: " + hash1);
        System.out.println("Hash 2: " + hash2);
        System.out.println("Hashes match: " + hash1.equals(hash2));
    }
}
