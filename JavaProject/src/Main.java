import views.BeverageCatalogView;
import views.LoginView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Online Store ===");
            System.out.println("1. Login");
            System.out.println("2. View Beverage Catalog");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> new LoginView().displayLogin();
                case 2 -> new BeverageCatalogView().displayCatalog();
                case 3 -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}