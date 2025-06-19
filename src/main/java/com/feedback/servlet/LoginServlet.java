package com.feedback.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.feedback.dao.UserDAO;
import com.feedback.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
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
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        try {
            User user = userDAO.validateUser(username, password, role);
            
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                
                switch (user.getRole()) {
                    case "ADMIN":
                        response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
                        break;
                    case "FACULTY":
                        response.sendRedirect(request.getContextPath() + "/faculty/dashboard.jsp");
                        break;
                    case "STUDENT":
                        response.sendRedirect(request.getContextPath() + "/student/dashboard.jsp");
                        break;
                    default:
                        request.setAttribute("message", "Invalid user role");
                        request.setAttribute("messageType", "danger");
                        request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("message", "Invalid username, password, or role");
                request.setAttribute("messageType", "danger");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("message", "Database error: " + e.getMessage());
            request.setAttribute("messageType", "danger");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
} 