package views;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import models.CartItem;
import models.Order;
import models.OrderItem;
import services.ClientService;

public class ClientMenu {
    private final ClientService clientService = new ClientService();
    // private final CartService cartService = new CartService();
    // private final OrderService orderService = new OrderService();
    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu(String userEmail) {
        while (true) {
            System.out.println("\n=== Client Menu ===");
            System.out.println("1. View Beverage Catalog");
            System.out.println("2. View Cart");
            System.out.println("3. Add Beverage to Cart"); // New option
            System.out.println("4. View Orders");
            System.out.println("5. Place Order");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> new BeverageCatalogView().displayCatalog();
                case 2 -> printCartItems(userEmail);
                case 3 -> addBeverageToCart(userEmail); // New logic
                case 4 -> printOrders(userEmail);
                case 5 -> placeOrder(userEmail);
                case 6 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void printCartItems(String userEmail) {
        List<CartItem> cartItems = clientService.getCartItemsByUserEmail(userEmail);

        System.out.printf("%-25s %-15s %-15s %-15s\n",
                "Beverage Name", "Quantity", "Price per unit", "Total Cost");
        System.out.println("=".repeat(80));

        BigDecimal grandTotal = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            BigDecimal totalCost = item.getBeveragePrice().multiply(new BigDecimal(item.getBeverageQuantity()));
            grandTotal = grandTotal.add(totalCost);

            System.out.printf("%-25s %-15d %-15.2f %-15.2f\n",
                    item.getBeverageName(),
                    item.getBeverageQuantity(),
                    item.getBeveragePrice(),
                    totalCost);
        }

        System.out.println("=".repeat(80));
        System.out.printf("%-55s %-25.2f\n", "Grand Total:", grandTotal);
    }

    private void addBeverageToCart(String userEmail) {
        System.out.println("\n=== Add Beverage to Cart ===");

        // Prompt user for beverage ID
        System.out.print("Enter the ID of the beverage to add: ");
        int beverageId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Prompt user for quantity
        System.out.print("Enter the quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Call the service to add the beverage to the cart
        boolean success = clientService.addBeverageToCart(userEmail, beverageId, quantity);

        if (success) {
            System.out.println("Beverage successfully added to the cart!");
        } else {
            System.out.println("Failed to add the beverage to the cart. Please try again.");
        }
    }

    private void printOrders(String userEmail) {
        List<Order> orders = clientService.getOrdersByUserEmail(userEmail);

        for (Order order : orders) {
            System.out.println("\nOrder ID: " + order.getId());
            System.out.println("Order Price: " + order.getPrice());
            System.out.println("Order Date: " + order.getCreatedAt());
            System.out.println("Order Items:");
            for (OrderItem item : order.getOrderItems()) {
                System.out.printf("  - %s (x%d) - $%.2f\n",
                        item.getBeverageName(),
                        item.getBeverageQuantity(),
                        item.getBeveragePrice());
            }
            System.out.println("=".repeat(40));
        }
    }

    private void placeOrder(String userEmail) {
        if (clientService.createOrderFromCart(userEmail)) {
            System.out.println("Order placed successfully!");
        } else {
            System.out.println("Failed to place the order. Please try again.");
        }
    }
}
