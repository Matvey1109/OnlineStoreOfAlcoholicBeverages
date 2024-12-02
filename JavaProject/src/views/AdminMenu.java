package views;

import models.User;
import services.UserService;
import utils.Logger;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final UserService userService = new UserService();
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. View Users");
            System.out.println("2. View Logs");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> displayAllUsers();
                case 2 -> viewLogs();
                case 3 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayAllUsers() {
        System.out.println("\n=== User List ===");
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.printf("%-10s %-20s %-20s %-20s\n", "ID", "Email", "Role", "Created At");
        System.out.println("=".repeat(90));
        for (User user : users) {
            System.out.printf(
                    "%-10d %-20s %-20s %-20s\n",
                    user.getId(),
                    user.getEmail(),
                    user.getRole().getName(), // Access the role name
                    user.getCreatedAt());
        }
    }

    private void viewLogs() {
        System.out.println("=== System Logs ===");
        String logs = Logger.retrieveLogs();
        System.out.println(logs);
    }
}
