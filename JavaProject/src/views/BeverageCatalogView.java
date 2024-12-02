package views;

import models.Beverage;
import services.BeverageService;

import java.util.List;
import java.util.Scanner;

public class BeverageCatalogView {
    private final BeverageService beverageService = new BeverageService();
    private final Scanner scanner = new Scanner(System.in);

    public void displayCatalog() {
        System.out.println("\n=== Beverage Catalog ===");
        List<Beverage> beverages = beverageService.getAllBeverages();
        if (beverages.isEmpty()) {
            System.out.println("No beverages found.");
        } else {
            printBeverageList(beverages);
        }

        System.out.println("\n1. Filter by Category");
        System.out.println("2. Return to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1 -> filterCatalog();
            case 2 -> System.out.println("Returning to main menu.");
            default -> System.out.println("Invalid choice.");
        }
    }

    private void filterCatalog() {
        System.out.print("Enter category to filter by: ");
        String category = scanner.nextLine();

        List<Beverage> filteredBeverages = beverageService.getBeveragesByCategoryName(category);
        if (filteredBeverages.isEmpty()) {
            System.out.println("No beverages found in this category.");
        } else {
            printBeverageList(filteredBeverages);
        }
    }

    private void printBeverageList(List<Beverage> beverages) {
        System.out.printf("%-10s %-15s %-25s %-15s %-20s %-20s %-15s\n",
                "ID", "Name", "Category", "Price", "Brand", "Available Quantity", "Alcohol Percentage");
        System.out.println("=".repeat(130));

        // Without description
        for (Beverage beverage : beverages) {
            System.out.printf("%-10s %-15s %-25s %-15s %-20s %-20d %-15s\n",
                    beverage.getId(),
                    beverage.getName(),
                    beverage.getCategory().getName(),
                    beverage.getPrice(),
                    beverage.getBrand(),
                    beverage.getAvailableQuantity(),
                    beverage.getAlcoholPercentage());
        }
    }
}
