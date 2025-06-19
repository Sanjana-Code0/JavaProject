package com.feedback.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.feedback.dao.UserDAO;
import com.feedback.model.User;

@WebServlet("/admin/user")
public class UserManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (!"ADMIN".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            return;
        }

        try {
            String action = request.getParameter("action");
            
            if ("delete".equals(action)) {
                int userId = Integer.parseInt(request.getParameter("id"));
                if (userDAO.deleteUser(userId)) {
                    request.setAttribute("message", "User deleted successfully");
                    request.setAttribute("messageType", "success");
                } else {
                    request.setAttribute("message", "Failed to delete user");
                    request.setAttribute("messageType", "danger");
                }
            }
            
            // Get all users for display
            List<User> users = userDAO.getAllUsers();
            System.out.println("[DEBUG] Number of users fetched: " + users.size());
            for (User u : users) {
                System.out.println("[DEBUG] User: " + u.getId() + ", " + u.getUsername() + ", " + u.getEmail() + ", " + u.getRole());
            }
            request.setAttribute("users", users);
            request.getRequestDispatcher("/admin/manage-users.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("message", "Database error: " + e.getMessage());
            request.setAttribute("messageType", "danger");
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (!"ADMIN".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            return;
        }

        try {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setRole(role);

            if (userDAO.register(newUser)) {
                request.setAttribute("message", "User added successfully");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "Failed to add user");
                request.setAttribute("messageType", "danger");
            }

            // Get all users for display
            List<User> users = userDAO.getAllUsers();
            System.out.println("[DEBUG] Number of users fetched: " + users.size());
            for (User u : users) {
                System.out.println("[DEBUG] User: " + u.getId() + ", " + u.getUsername() + ", " + u.getEmail() + ", " + u.getRole());
            }
            request.setAttribute("users", users);
            request.getRequestDispatcher("/admin/manage-users.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("message", "Database error: " + e.getMessage());
            request.setAttribute("messageType", "danger");
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        }
    }
} 