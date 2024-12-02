package services;

import dao.RoleDAO;
import models.Role;

public class RoleService {
    private RoleDAO roleDAO = new RoleDAO();

    public Role getRole(int roleId) {
        return roleDAO.getRoleById(roleId);
    }
}
