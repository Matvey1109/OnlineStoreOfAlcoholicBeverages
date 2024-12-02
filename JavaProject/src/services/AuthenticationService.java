package services;

import dao.UserDAO;
import models.User;
import utils.PasswordUtil;

public class AuthenticationService {
    private UserDAO userDAO = new UserDAO();

    public User authenticate(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            return user; // Authentication successful
        }
        return null; // Authentication failed
    }
}
