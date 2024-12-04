package services;

import dao.CartDAO;
import dao.ClientDAO;
import dao.OrderDAO;
import dao.ReviewDAO;
import models.CartItem;
import models.Client;
import models.Order;
import models.Review;

import java.util.List;

public class ClientService {
    private final ClientDAO clientDAO = new ClientDAO();
    private final CartDAO cartDAO = new CartDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final ReviewDAO reviewDAO = new ReviewDAO();

    public Client getClientByUserEmail(String userEmail) {
        return clientDAO.getClientByUserEmail(userEmail);
    }

    public List<CartItem> getCartItemsByUserEmail(String userEmail) {
        if (userEmail == null || userEmail.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank.");
        }
        return cartDAO.getCartItemsByUserEmail(userEmail);
    }

    public boolean addBeverageToCart(String userEmail, int beverageId, int quantity) {
        Client client = getClientByUserEmail(userEmail);
        if (client == null) {
            System.out.println("Client not found for email: " + userEmail);
            return false;
        }

        // Delegate to DAO
        return cartDAO.addItemToCart(client.getId(), beverageId, quantity);
    }

    public List<Order> getOrdersByUserEmail(String userEmail) {
        Client client = getClientByUserEmail(userEmail);
        return orderDAO.getOrdersByClientId(client.getId());
    }

    public boolean createOrderFromCart(String userEmail) {
        Client client = getClientByUserEmail(userEmail);
        return orderDAO.createOrderFromCart(client.getId());
    }

    public boolean addReview(String userEmail, int beverageId, int rating, String content) {
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Please enter a value between 1 and 5.");
            return false;
        }

        Client client = getClientByUserEmail(userEmail);
        return reviewDAO.addReview(client.getId(), beverageId, rating, content);
    }

    public List<Review> getAllReviews() {
        return reviewDAO.fetchAllReviews();
    }

}
