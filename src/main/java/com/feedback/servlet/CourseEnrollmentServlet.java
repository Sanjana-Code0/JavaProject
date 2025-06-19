package com.feedback.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.feedback.dao.CourseDAO;
import com.feedback.model.User;

@WebServlet("/enroll-course")
public class CourseEnrollmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CourseDAO courseDAO;
    
    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
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
            int studentId = user.getId();
            
            if (courseDAO.enrollStudent(studentId, courseId)) {
                session.setAttribute("message", "Successfully enrolled in the course");
                session.setAttribute("messageType", "success");
            } else {
                session.setAttribute("message", "Failed to enroll in the course");
                session.setAttribute("messageType", "danger");
            }
            
            response.sendRedirect(request.getContextPath() + "/student/dashboard");
            
        } catch (SQLException e) {
            session.setAttribute("message", "Error enrolling in course: " + e.getMessage());
            session.setAttribute("messageType", "danger");
            response.sendRedirect(request.getContextPath() + "/student/dashboard");
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Invalid course ID provided");
            session.setAttribute("messageType", "danger");
            response.sendRedirect(request.getContextPath() + "/student/dashboard");
        }
    }
} 