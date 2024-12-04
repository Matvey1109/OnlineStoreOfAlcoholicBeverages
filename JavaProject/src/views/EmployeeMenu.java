package views;

import services.EmployeeService;

import java.util.List;
import java.util.Scanner;

import models.Order;
import models.OrderItem;

public class EmployeeMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final EmployeeService employeeService = new EmployeeService();

    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Employee Menu ===");
            System.out.println("1. Manage Beverages");
            System.out.println("2. Manage Discounts");
            System.out.println("3. View Orders");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> manageBeverages();
                case 2 -> manageDiscounts();
                case 3 -> viewOrders();
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void manageBeverages() {
        System.out.println("\n=== Manage Beverages ===");
        System.out.println("1. Add Beverage");
        System.out.println("2. Update Beverage");
        System.out.println("3. Delete Beverage");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter Beverage Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Description: ");
                String description = scanner.nextLine();
                System.out.print("Enter Price: ");
                double price = scanner.nextDouble();
                System.out.print("Enter Available Quantity: ");
                int quantity = scanner.nextInt();
                System.out.print("Enter Alcohol Percentage (as a decimal, e.g. 0.05 for 5%): ");
                double alcoholPercentage = scanner.nextDouble();
                System.out.print("Enter Category ID: ");
                int categoryId = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                System.out.print("Enter Brand: ");
                String brand = scanner.nextLine();

                // Call the service method to add the beverage with all the new fields
                employeeService.addBeverage(name, description, price, quantity, brand, alcoholPercentage, categoryId);
            }
            case 2 -> {
                System.out.print("Enter Beverage ID: ");
                int id = scanner.nextInt();
                System.out.print("Enter New Price: ");
                double price = scanner.nextDouble();
                System.out.print("Enter New Available Quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();

                employeeService.updateBeverage(id, price, quantity);
            }
            case 3 -> {
                System.out.print("Enter Beverage ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                employeeService.deleteBeverage(id);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    public void manageDiscounts() {
        System.out.println("\n=== Manage Discounts ===");
        System.out.println("1. Add Discount");
        System.out.println("2. Update Discount");
        System.out.println("3. Delete Discount by Beverage ID");
        System.out.println("4. Print Discounts");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter Discount Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Description: ");
                String description = scanner.nextLine();
                System.out.print("Enter Discount Percentage: ");
                double percent = scanner.nextDouble();
                System.out.print("Enter Beverage ID: ");
                int beverageId = scanner.nextInt();

                employeeService.addDiscount(name, description, percent, beverageId);
            }
            case 2 -> {
                System.out.print("Enter Discount ID: ");
                int discountId = scanner.nextInt();
                System.out.print("Enter New Discount Percentage: ");
                double newPercent = scanner.nextDouble();
                System.out.print("Is Discount Active (true/false): ");
                boolean isActive = scanner.nextBoolean();

                employeeService.updateDiscount(discountId, newPercent, isActive);
            }
            case 3 -> {
                System.out.print("Enter Beverage ID to Remove Discount: ");
                int beverageId = scanner.nextInt();

                employeeService.deleteDiscountByBeverageId(beverageId);
            }
            case 4 -> employeeService.printDiscounts();
            default -> System.out.println("Invalid choice.");
        }
    }

    private void viewOrders() {
        List<Order> orders = employeeService.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("\n=== Orders ===");
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getId());
            System.out.println("Client ID: " + order.getClientId());
            System.out.println("Total Price: " + order.getPrice());
            System.out.println("Created At: " + order.getCreatedAt());
            System.out.println("Updated At: " + order.getUpdatedAt());

            System.out.println("Order Items:");
            for (OrderItem item : order.getOrderItems()) {
                System.out.printf("  - %s (ID: %d), Quantity: %d, Price: %.2f%n",
                        item.getBeverageName(),
                        item.getBeverageId(),
                        item.getBeverageQuantity(),
                        item.getBeveragePrice());
            }
            System.out.println("------------------------");
        }
    }

}
