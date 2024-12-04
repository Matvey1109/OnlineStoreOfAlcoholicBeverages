package services;

import dao.EmployeeDAO;
import dao.ClientDAO;
import dao.UserDAO;
import models.User;
import utils.PasswordUtil;

public class AuthenticationService {
    private final UserDAO userDAO = new UserDAO();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    private final ClientDAO clientDAO = new ClientDAO();

    public User authenticate(String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
            return user; // Authentication successful
        }
        return null; // Authentication failed
    }

    public boolean registerUser(String email, String plainPassword, int roleId) {
        if (userDAO.getUserByEmail(email) != null) {
            return false;
        }

        User user = new User();
        user.setEmail(email);
        // user.setPasswordHash(PasswordUtil.hashPassword(plainPassword));
        user.setPasswordHash(plainPassword);
        user.setRoleId(roleId);

        // Add user to the database
        boolean userCreated = userDAO.addUser(user);
        if (!userCreated) {
            return false; // Failed to create the user
        }

        // Add to the corresponding table based on roleId
        boolean roleSpecificInsert = false;
        switch (roleId) {
            case 2 -> roleSpecificInsert = employeeDAO.addEmployee(user); // Add employee
            case 3 -> roleSpecificInsert = clientDAO.addClient(user); // Add client
            default -> roleSpecificInsert = true; // No additional action for admin
        }

        return roleSpecificInsert;
    }
}
