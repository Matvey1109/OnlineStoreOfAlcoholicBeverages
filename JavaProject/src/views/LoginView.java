package views;

import models.User;
import services.AuthenticationService;
import utils.Logger;

import java.util.Scanner;

public class LoginView {
    private final AuthenticationService authService = new AuthenticationService();
    private final Scanner scanner = new Scanner(System.in);

    public void displayLogin() {
        Logger.info("Login view accessed.");

        System.out.println("\n=== Login ===");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = authService.authenticate(email, password);
        if (user != null) {
            Logger.info("User authenticated: " + email);
            System.out.println("Login successful!");
            navigateToMenu(user);
        } else {
            Logger.warn("Login failed for email: " + email);
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void navigateToMenu(User user) {
        switch (user.getRoleId()) {
            case 1 -> new AdminMenu().displayMenu();
            case 2 -> new EmployeeMenu().displayMenu();
            case 3 -> new ClientMenu().displayMenu(user.getEmail());
            default -> System.out.println("Unknown role. Please contact the administrator.");
        }
    }
}
