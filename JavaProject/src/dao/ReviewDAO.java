package dao;

import config.DatabaseConfig;
import models.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    public boolean addReview(int clientId, int beverageId, int rating, String content) {
        String query = """
                    INSERT INTO "Review" (content, rating, beverage_id, client_id)
                    VALUES (?, ?, ?, ?);
                """;

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, content);
            stmt.setInt(2, rating);
            stmt.setInt(3, beverageId);
            stmt.setInt(4, clientId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Review> fetchAllReviews() {
        String query = """
                    SELECT r.content, r.rating, r.created_at,
                           b.name AS beverage_name,
                           c.name AS client_name
                    FROM "Review" r
                    JOIN "Beverage" b ON r.beverage_id = b.id
                    JOIN "Client" c ON r.client_id = c.id
                    ORDER BY r.created_at DESC;
                """;

        List<Review> reviews = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Review review = new Review();
                review.setContent(rs.getString("content"));
                review.setRating(rs.getInt("rating"));
                review.setCreatedAt(rs.getTimestamp("created_at").toString());
                review.setBeverageName(rs.getString("beverage_name"));
                review.setClientName(rs.getString("client_name"));
                reviews.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }
}
