package utils;

public class PasswordUtil {

    // Hash password
    public static String hashPassword(String plainPassword) {
        return "hashed_" + plainPassword; // Simply prefix with "hashed_"
    }

    // Verify password
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        String newHash = hashPassword(plainPassword);
        return newHash.equals(hashedPassword);
    }
}
