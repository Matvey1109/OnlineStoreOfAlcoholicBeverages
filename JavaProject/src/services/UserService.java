package services;

import dao.UserDAO;
import models.User;

import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void addUser(User user) {
        userDAO.addUser(user);
    }
}
