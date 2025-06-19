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
import com.feedback.model.Course;
import com.feedback.model.User;

@WebServlet("/admin/course")
public class CourseManagementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CourseDAO courseDAO;

    @Override
    public void init() throws ServletException {
        courseDAO = new CourseDAO();
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
                int courseId = Integer.parseInt(request.getParameter("id"));
                if (courseDAO.deleteCourse(courseId)) {
                    request.setAttribute("message", "Course deleted successfully");
                    request.setAttribute("messageType", "success");
                } else {
                    request.setAttribute("message", "Failed to delete course");
                    request.setAttribute("messageType", "danger");
                }
            }
            
            // Get all courses for display
            List<Course> courses = courseDAO.getAllCourses();
            System.out.println("[DEBUG] Number of courses fetched: " + courses.size());
            for (Course c : courses) {
                System.out.println("[DEBUG] Course: " + c.getId() + ", " + c.getCode() + ", " + c.getName() + ", " + c.getInstructor());
            }
            request.setAttribute("courses", courses);
            request.getRequestDispatcher("/admin/manage-courses.jsp").forward(request, response);
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
            String code = request.getParameter("code");
            String name = request.getParameter("name");
            String instructor = request.getParameter("instructor");

            Course course = new Course();
            course.setCode(code);
            course.setName(name);
            course.setInstructor(instructor);

            if (courseDAO.addCourse(course)) {
                request.setAttribute("message", "Course added successfully");
                request.setAttribute("messageType", "success");
            } else {
                request.setAttribute("message", "Failed to add course");
                request.setAttribute("messageType", "danger");
            }

            // Get all courses for display
            List<Course> courses = courseDAO.getAllCourses();
            System.out.println("[DEBUG] Number of courses fetched: " + courses.size());
            for (Course c : courses) {
                System.out.println("[DEBUG] Course: " + c.getId() + ", " + c.getCode() + ", " + c.getName() + ", " + c.getInstructor());
            }
            request.setAttribute("courses", courses);
            request.getRequestDispatcher("/admin/manage-courses.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("message", "Database error: " + e.getMessage());
            request.setAttribute("messageType", "danger");
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        }
    }
} 