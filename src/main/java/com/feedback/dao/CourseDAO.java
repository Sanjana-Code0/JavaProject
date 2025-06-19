package com.feedback.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.feedback.model.Course;
import com.feedback.util.DatabaseUtil;

public class CourseDAO {
    
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setCode(rs.getString("code"));
                course.setName(rs.getString("name"));
                course.setInstructor(rs.getString("instructor"));
                courses.add(course);
            }
        }
        return courses;
    }
    
    public boolean addCourse(Course course) throws SQLException {
        String query = "INSERT INTO courses (code, name, instructor) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, course.getCode());
            stmt.setString(2, course.getName());
            stmt.setString(3, course.getInstructor());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteCourse(int courseId) throws SQLException {
        String query = "DELETE FROM courses WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, courseId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Course> getEnrolledCourses(int studentId) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.*, " +
                      "COALESCE(AVG(f.teaching_rating), 0) as avg_teaching_rating, " +
                      "COALESCE(AVG(f.clarity_rating), 0) as avg_clarity_rating, " +
                      "COALESCE(AVG(f.engagement_rating), 0) as avg_engagement_rating " +
                      "FROM courses c " +
                      "LEFT JOIN feedback f ON c.id = f.course_id " +
                      "WHERE c.id IN (SELECT course_id FROM enrollments WHERE student_id = ?) " +
                      "GROUP BY c.id";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course();
                    course.setId(rs.getInt("id"));
                    course.setCode(rs.getString("code"));
                    course.setName(rs.getString("name"));
                    course.setInstructor(rs.getString("instructor"));
                    course.setAvgTeachingRating(rs.getDouble("avg_teaching_rating"));
                    course.setAvgClarityRating(rs.getDouble("avg_clarity_rating"));
                    course.setAvgEngagementRating(rs.getDouble("avg_engagement_rating"));
                    courses.add(course);
                }
            }
        }
        return courses;
    }
    
    public Course getCourseById(int courseId) throws SQLException {
        String query = "SELECT * FROM courses WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Course course = new Course();
                    course.setId(rs.getInt("id"));
                    course.setCode(rs.getString("code"));
                    course.setName(rs.getString("name"));
                    course.setInstructor(rs.getString("instructor"));
                    return course;
                }
            }
        }
        return null;
    }

    public boolean enrollStudent(int studentId, int courseId) throws SQLException {
        String query = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Course> getAvailableCourses(int studentId) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT c.* FROM courses c " +
                      "WHERE c.id NOT IN (SELECT course_id FROM enrollments WHERE student_id = ?)";
        
        System.out.println("Executing query for available courses for student ID: " + studentId);
        System.out.println("Query: " + query);
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Course course = new Course();
                    course.setId(rs.getInt("id"));
                    course.setCode(rs.getString("code"));
                    course.setName(rs.getString("name"));
                    course.setInstructor(rs.getString("instructor"));
                    System.out.println("Found available course: " + course.getName() + " (ID: " + course.getId() + ")");
                    courses.add(course);
                }
            }
        }
        System.out.println("Total available courses found: " + courses.size());
        return courses;
    }

    public boolean hasAnyCourses() throws SQLException {
        String query = "SELECT COUNT(*) FROM courses";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Total courses in database: " + count);
                return count > 0;
            }
        }
        return false;
    }
} 