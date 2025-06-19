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
import com.feedback.dao.FeedbackDAO;
import com.feedback.model.FeedbackStats;
import com.feedback.model.User;

@WebServlet("/admin/reports")
public class ReportsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FeedbackDAO feedbackDAO;
    
    @Override
    public void init() throws ServletException {
        feedbackDAO = new FeedbackDAO();
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
        
        String reportType = request.getParameter("type");
        if (reportType == null) {
            reportType = "course";
        }
        
        try {
            List<FeedbackStats> stats;
            switch (reportType) {
                case "instructor":
                    stats = feedbackDAO.getInstructorWiseStats();
                    request.setAttribute("reportType", "instructor");
                    break;
                case "detailed":
                    stats = feedbackDAO.getDetailedFeedbackStats();
                    request.setAttribute("reportType", "detailed");
                    break;
                default:
                    stats = feedbackDAO.getCourseWiseStats();
                    request.setAttribute("reportType", "course");
                    break;
            }
            
            request.setAttribute("stats", stats);
            request.getRequestDispatcher("/admin/reports.jsp").forward(request, response);
            
        } catch (SQLException e) {
            request.setAttribute("error", "Error generating reports: " + e.getMessage());
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        }
    }
} 