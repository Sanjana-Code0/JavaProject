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
import com.feedback.dao.CourseDAO;
import com.feedback.dao.FeedbackDAO;
import com.feedback.model.Course;
import com.feedback.model.Feedback;
import com.feedback.model.User;

@WebServlet("/student/dashboard")
public class StudentDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CourseDAO courseDAO;
    private FeedbackDAO feedbackDAO;
    
    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
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
        if (!"STUDENT".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            return;
        }
        
        try {
            // Check if there are any courses in the database
            boolean hasCourses = courseDAO.hasAnyCourses();
            System.out.println("Are there any courses in the database? " + hasCourses);
            
            // Get enrolled courses
            List<Course> enrolledCourses = courseDAO.getEnrolledCourses(user.getId());
            System.out.println("[DEBUG] Number of enrolled courses: " + enrolledCourses.size());
            for (Course c : enrolledCourses) {
                System.out.println("[DEBUG] Enrolled Course: " + c.getId() + ", " + c.getCode() + ", " + c.getName() + ", " + c.getInstructor());
            }
            
            // Get available courses
            List<Course> availableCourses = courseDAO.getAvailableCourses(user.getId());
            System.out.println("[DEBUG] Number of available courses: " + availableCourses.size());
            for (Course c : availableCourses) {
                System.out.println("[DEBUG] Available Course: " + c.getId() + ", " + c.getCode() + ", " + c.getName() + ", " + c.getInstructor());
            }
            
            // Get submitted feedback
            List<Feedback> submittedFeedback = feedbackDAO.getStudentFeedback(user.getId());
            System.out.println("Number of submitted feedback: " + submittedFeedback.size());
            
            request.setAttribute("enrolledCourses", enrolledCourses);
            request.setAttribute("availableCourses", availableCourses);
            request.setAttribute("submittedFeedback", submittedFeedback);
            request.getRequestDispatcher("/student/dashboard.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/student/dashboard.jsp").forward(request, response);
        }
    }
} 