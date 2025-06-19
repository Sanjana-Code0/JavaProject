package com.feedback.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.feedback.dao.UserDAO;
import com.feedback.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validate input
        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("message", "All fields are required");
            request.setAttribute("messageType", "danger");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            request.setAttribute("message", "Passwords do not match");
            request.setAttribute("messageType", "danger");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        
        try {
            // Check if username or email already exists
            if (userDAO.isUserExists(username, email)) {
                request.setAttribute("message", "Username or email already exists");
                request.setAttribute("messageType", "danger");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                return;
            }
            
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole("STUDENT"); // Default role for registration
            
            if (userDAO.register(user)) {
                request.setAttribute("message", "Registration successful. Please login.");
                request.setAttribute("messageType", "success");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Registration failed. Please try again.");
                request.setAttribute("messageType", "danger");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("message", "Database error: " + e.getMessage());
            request.setAttribute("messageType", "danger");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
} 