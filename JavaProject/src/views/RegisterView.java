package views;

import services.AuthenticationService;

import java.util.Scanner;

public class RegisterView {
    private AuthenticationService authenticationService = new AuthenticationService();
    private Scanner scanner = new Scanner(System.in);

    public void displayRegister() {
        System.out.println("\n=== User Registration ===");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.print("Confirm your password: ");
        String confirmPassword = scanner.nextLine();
        System.out.print("Enter your role ID (e.g., 1 for Admin, 2 for Employee, 3 for Client): ");
        int roleId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match. Please try again.");
            return;
        }

        // Register user
        boolean success = authenticationService.registerUser(email, password, roleId);
        if (success) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Registration failed. Email might already be in use.");
        }
    }
}
