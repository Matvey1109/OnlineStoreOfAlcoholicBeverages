package services;

import dao.BeverageDAO;
import dao.BeverageDiscountDAO;
import dao.DiscountDAO;
import dao.OrderDAO;
import models.Beverage;
import models.BeverageDiscount;
import models.Discount;
import models.Order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class EmployeeService {
    private final OrderDAO orderDAO = new OrderDAO();
    private final BeverageDAO beverageDAO = new BeverageDAO();
    private final DiscountDAO discountDAO = new DiscountDAO();
    private final BeverageDiscountDAO beverageDiscountDAO = new BeverageDiscountDAO();

    public void addBeverage(String name, String description, double price, int quantity, String brand,
            double alcoholPercentage, int categoryId) {
        if (name == null || description == null || price <= 0 || quantity < 0 || brand == null || alcoholPercentage < 0
                || categoryId <= 0) {
            throw new IllegalArgumentException("Invalid input for adding a beverage.");
        }

        // Create a new Beverage object with the provided data
        Beverage beverage = new Beverage();
        beverage.setName(name);
        beverage.setDescription(description);
        beverage.setPrice(BigDecimal.valueOf(price));
        beverage.setAvailableQuantity(quantity);
        beverage.setBrand(brand);
        beverage.setAlcoholPercentage(BigDecimal.valueOf(alcoholPercentage));
        beverage.setCategoryId(categoryId);
        beverage.setCreatedAt(Timestamp.from(Instant.now()));
        beverage.setUpdatedAt(Timestamp.from(Instant.now()));

        // Call the DAO method to add the beverage
        beverageDAO.addBeverage(beverage);
    }

    // Update beverage price and quantity
    public void updateBeverage(int id, double price, int quantity) {
        if (id <= 0 || price <= 0 || quantity < 0) {
            throw new IllegalArgumentException("Invalid input for updating a beverage.");
        }

        Beverage beverage = new Beverage();
        beverage.setId(id);
        beverage.setPrice(BigDecimal.valueOf(price));
        beverage.setAvailableQuantity(quantity);
        beverage.setUpdatedAt(Timestamp.from(Instant.now()));

        beverageDAO.updateBeverage(beverage);
    }

    // Delete a beverage by ID
    public void deleteBeverage(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid beverage ID for deletion.");
        }
        beverageDAO.deleteBeverage(id);
    }

    // Add discount
    public void addDiscount(String name, String description, double percent, int beverageId) {
        Discount discount = new Discount();
        discount.setName(name);
        discount.setDescription(description);
        discount.setPercent(percent);
        discount.setIsActive(true);
        discount.setCreatedAt(Timestamp.from(java.time.Instant.now()));

        // Add discount and get generated ID
        int discountId = discountDAO.addDiscount(discount);

        BeverageDiscount beverageDiscount = new BeverageDiscount();
        beverageDiscount.setBeverageId(beverageId);
        beverageDiscount.setDiscountId(discountId);
        beverageDiscount.setCreatedAt(Timestamp.from(java.time.Instant.now()));
        beverageDiscount.setUpdatedAt(Timestamp.from(java.time.Instant.now()));

        beverageDiscountDAO.addBeverageDiscount(beverageDiscount);
    }

    // Update discount
    public void updateDiscount(int discountId, double newPercent, boolean isActive) {
        Discount discount = new Discount();
        discount.setId(discountId);
        discount.setPercent(newPercent);
        discount.setIsActive(isActive);
        discount.setCreatedAt(Timestamp.from(java.time.Instant.now()));

        discountDAO.updateDiscount(discount);
    }

    // Delete discount
    public void deleteDiscountByBeverageId(int beverageId) {
        try {
            // Step 1: Find all discount IDs associated with the given beverage ID
            List<Integer> discountIds = beverageDiscountDAO.findDiscountIdsByBeverageId(beverageId);

            // Step 2: Delete entries from the BeverageDiscount table
            beverageDiscountDAO.deleteByBeverageId(beverageId);

            // Step 3: For each discount ID, check if it is orphaned and delete it
            for (int discountId : discountIds) {
                if (beverageDiscountDAO.isDiscountOrphaned(discountId)) {
                    discountDAO.deleteDiscountById(discountId);
                }
            }

            System.out.println("Successfully deleted discounts for beverage ID " + beverageId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete discounts for the beverage.");
        }
    }

    public void printDiscounts() {
        for (Discount discount : discountDAO.getAllDiscounts()) {
            System.out.println("ID: " + discount.getId() + ", Name: " + discount.getName() +
                    ", Percent: " + discount.getPercent() + ", Active: " + discount.getIsActive());
        }
    }

    // View orders
    public List<Order> getAllOrders() {
        try {
            return orderDAO.getAllOrders();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch orders.");
        }
    }
}
