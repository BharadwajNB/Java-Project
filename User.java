package LeaveManagementSystem;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Abstract Base Class for Authentication.
 * 
 * WHY WE USE THIS:
 * 1. Inheritance: Both Employee and HOD need login credentials
 * 2. Encapsulation: Password hashing logic is centralized here
 * 3. Security: Passwords are stored as SHA-256 hashes, not plain text
 * 4. DRY Principle: Don't repeat authentication code in multiple classes
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String username;
    protected String passwordHash;  // Stored as SHA-256 hash
    
    public User(String username, String password) {
        this.username = username;
        this.passwordHash = hashPassword(password);
    }
    
    /**
     * Hash a password using SHA-256 algorithm.
     * This is a one-way function - you cannot reverse it to get the original password.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            
            // Convert byte array to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    
    /**
     * Authenticate user by comparing hashed passwords.
     */
    public boolean authenticate(String password) {
        String inputHash = hashPassword(password);
        return this.passwordHash.equals(inputHash);
    }
    
    public String getUsername() {
        return username;
    }
}
