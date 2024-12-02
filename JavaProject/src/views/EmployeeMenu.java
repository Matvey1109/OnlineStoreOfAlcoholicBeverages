package views;

import services.BeverageService;
// import services.PromotionService;

import java.util.Scanner;

public class EmployeeMenu {
    private final BeverageService beverageService = new BeverageService();
    // private final PromotionService promotionService = new PromotionService();
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Employee Menu ===");
            System.out.println("1. Manage Beverages");
            System.out.println("2. Manage Promotions");
            System.out.println("3. View Orders");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                // case 1 -> beverageService.manageBeverages();
                // case 2 -> promotionService.managePromotions();
                // case 3 -> beverageService.viewOrders();
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
