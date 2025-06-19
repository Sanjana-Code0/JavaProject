package com.feedback.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.feedback.dao.FeedbackDAO;
import com.feedback.model.Feedback;
import com.feedback.model.User;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FeedbackDAO feedbackDAO;
    
    @Override
    public void init() throws ServletException {
        feedbackDAO = new FeedbackDAO();
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
        if (!"STUDENT".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            return;
        }
        
        try {
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            int teachingRating = Integer.parseInt(request.getParameter("teachingRating"));
            int clarityRating = Integer.parseInt(request.getParameter("clarityRating"));
            int engagementRating = Integer.parseInt(request.getParameter("engagementRating"));
            String comments = request.getParameter("comments");
            
            Feedback feedback = new Feedback();
            feedback.setStudentId(user.getId());
            feedback.setCourseId(courseId);
            feedback.setTeachingRating(teachingRating);
            feedback.setClarityRating(clarityRating);
            feedback.setEngagementRating(engagementRating);
            feedback.setComments(comments);
            
            if (feedbackDAO.submitFeedback(feedback)) {
                request.setAttribute("message", "Feedback submitted successfully");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "Failed to submit feedback");
                request.setAttribute("messageType", "danger");
            }
            
            response.sendRedirect(request.getContextPath() + "/student/dashboard.jsp");
            
        } catch (SQLException e) {
            request.setAttribute("message", "Error submitting feedback: " + e.getMessage());
            request.setAttribute("messageType", "danger");
            request.getRequestDispatcher("/student/dashboard.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid rating values provided");
            request.setAttribute("messageType", "danger");
            request.getRequestDispatcher("/student/dashboard.jsp").forward(request, response);
        }
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
        String role = user.getRole();
        
        if ("STUDENT".equals(role)) {
            try {
                // Get student's submitted feedback
                request.setAttribute("feedbacks", feedbackDAO.getStudentFeedback(user.getId()));
                request.getRequestDispatcher("/student/feedback-history.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("message", "Error retrieving feedback: " + e.getMessage());
                request.setAttribute("messageType", "danger");
                request.getRequestDispatcher("/student/feedback-history.jsp").forward(request, response);
            }
        } else if ("FACULTY".equals(role)) {
            try {
                // Get course feedback for faculty
                int courseId = Integer.parseInt(request.getParameter("courseId"));
                request.setAttribute("feedbacks", feedbackDAO.getCourseFeedback(courseId));
                request.getRequestDispatcher("/faculty/course-feedback.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("message", "Error retrieving feedback: " + e.getMessage());
                request.setAttribute("messageType", "danger");
                request.getRequestDispatcher("/faculty/dashboard.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid course ID provided");
                request.setAttribute("messageType", "danger");
                request.getRequestDispatcher("/faculty/dashboard.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
        }
    }
} 