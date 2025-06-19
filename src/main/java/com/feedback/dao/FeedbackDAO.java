package com.feedback.dao;

import com.feedback.model.Feedback;
import com.feedback.model.FeedbackStats;
import com.feedback.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {
    
    public List<FeedbackStats> getCourseWiseStats() throws SQLException {
        List<FeedbackStats> stats = new ArrayList<>();
        String query = "SELECT c.name as course_name, " +
                      "AVG(f.teaching_rating) as teaching_rating, " +
                      "AVG(f.clarity_rating) as clarity_rating, " +
                      "AVG(f.engagement_rating) as engagement_rating, " +
                      "(AVG(f.teaching_rating) + AVG(f.clarity_rating) + AVG(f.engagement_rating)) / 3 as overall_average " +
                      "FROM courses c " +
                      "LEFT JOIN feedback f ON c.id = f.course_id " +
                      "GROUP BY c.id, c.name";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                FeedbackStats stat = new FeedbackStats();
                stat.setCourseName(rs.getString("course_name"));
                stat.setTeachingRating(rs.getDouble("teaching_rating"));
                stat.setClarityRating(rs.getDouble("clarity_rating"));
                stat.setEngagementRating(rs.getDouble("engagement_rating"));
                stat.setOverallAverage(rs.getDouble("overall_average"));
                stats.add(stat);
            }
        }
        return stats;
    }
    
    public List<FeedbackStats> getInstructorWiseStats() throws SQLException {
        List<FeedbackStats> stats = new ArrayList<>();
        String query = "SELECT c.instructor, " +
                      "AVG(f.teaching_rating) as teaching_rating, " +
                      "AVG(f.clarity_rating) as clarity_rating, " +
                      "AVG(f.engagement_rating) as engagement_rating, " +
                      "(AVG(f.teaching_rating) + AVG(f.clarity_rating) + AVG(f.engagement_rating)) / 3 as overall_average " +
                      "FROM courses c " +
                      "LEFT JOIN feedback f ON c.id = f.course_id " +
                      "GROUP BY c.instructor";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                FeedbackStats stat = new FeedbackStats();
                stat.setInstructor(rs.getString("instructor"));
                stat.setTeachingRating(rs.getDouble("teaching_rating"));
                stat.setClarityRating(rs.getDouble("clarity_rating"));
                stat.setEngagementRating(rs.getDouble("engagement_rating"));
                stat.setOverallAverage(rs.getDouble("overall_average"));
                stats.add(stat);
            }
        }
        return stats;
    }
    
    public List<FeedbackStats> getDetailedFeedbackStats() throws SQLException {
        List<FeedbackStats> stats = new ArrayList<>();
        String query = "SELECT c.name as course_name, c.instructor, " +
                      "AVG(f.teaching_rating) as teaching_rating, " +
                      "AVG(f.clarity_rating) as clarity_rating, " +
                      "AVG(f.engagement_rating) as engagement_rating, " +
                      "(AVG(f.teaching_rating) + AVG(f.clarity_rating) + AVG(f.engagement_rating)) / 3 as overall_average " +
                      "FROM courses c " +
                      "LEFT JOIN feedback f ON c.id = f.course_id " +
                      "GROUP BY c.id, c.name, c.instructor";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                FeedbackStats stat = new FeedbackStats();
                stat.setCourseName(rs.getString("course_name"));
                stat.setInstructor(rs.getString("instructor"));
                stat.setTeachingRating(rs.getDouble("teaching_rating"));
                stat.setClarityRating(rs.getDouble("clarity_rating"));
                stat.setEngagementRating(rs.getDouble("engagement_rating"));
                stat.setOverallAverage(rs.getDouble("overall_average"));
                stats.add(stat);
            }
        }
        return stats;
    }
    
    public boolean submitFeedback(Feedback feedback) throws SQLException {
        String query = "INSERT INTO feedback (student_id, course_id, teaching_rating, clarity_rating, engagement_rating, comments) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, feedback.getStudentId());
            stmt.setInt(2, feedback.getCourseId());
            stmt.setInt(3, feedback.getTeachingRating());
            stmt.setInt(4, feedback.getClarityRating());
            stmt.setInt(5, feedback.getEngagementRating());
            stmt.setString(6, feedback.getComments());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Feedback> getFeedbackByCourse(int courseId) throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT f.*, u.username as student_name " +
                      "FROM feedback f " +
                      "JOIN users u ON f.student_id = u.id " +
                      "WHERE f.course_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setId(rs.getInt("id"));
                    feedback.setStudentId(rs.getInt("student_id"));
                    feedback.setCourseId(rs.getInt("course_id"));
                    feedback.setTeachingRating(rs.getInt("teaching_rating"));
                    feedback.setClarityRating(rs.getInt("clarity_rating"));
                    feedback.setEngagementRating(rs.getInt("engagement_rating"));
                    feedback.setComments(rs.getString("comments"));
                    feedback.setCreatedAt(rs.getString("created_at"));
                    feedbacks.add(feedback);
                }
            }
        }
        return feedbacks;
    }

    public List<Feedback> getStudentFeedback(int studentId) throws SQLException {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM feedback WHERE student_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, studentId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setId(rs.getInt("id"));
                    feedback.setStudentId(rs.getInt("student_id"));
                    feedback.setCourseId(rs.getInt("course_id"));
                    feedback.setTeachingRating(rs.getInt("teaching_rating"));
                    feedback.setClarityRating(rs.getInt("clarity_rating"));
                    feedback.setEngagementRating(rs.getInt("engagement_rating"));
                    feedback.setComments(rs.getString("comments"));
                    feedback.setCreatedAt(rs.getString("created_at"));
                    feedbackList.add(feedback);
                }
            }
        }
        return feedbackList;
    }

    public List<Feedback> getCourseFeedback(int courseId) throws SQLException {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM feedback WHERE course_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, courseId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Feedback feedback = new Feedback();
                    feedback.setId(rs.getInt("id"));
                    feedback.setStudentId(rs.getInt("student_id"));
                    feedback.setCourseId(rs.getInt("course_id"));
                    feedback.setTeachingRating(rs.getInt("teaching_rating"));
                    feedback.setClarityRating(rs.getInt("clarity_rating"));
                    feedback.setEngagementRating(rs.getInt("engagement_rating"));
                    feedback.setComments(rs.getString("comments"));
                    feedback.setCreatedAt(rs.getString("created_at"));
                    feedbackList.add(feedback);
                }
            }
        }
        return feedbackList;
    }

    public double[] getAverageRatings(int courseId) {
        String query = "SELECT AVG(teaching_rating) as avg_teaching, " +
                      "AVG(clarity_rating) as avg_clarity, " +
                      "AVG(engagement_rating) as avg_engagement " +
                      "FROM feedback WHERE course_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, courseId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new double[] {
                        rs.getDouble("avg_teaching"),
                        rs.getDouble("avg_clarity"),
                        rs.getDouble("avg_engagement")
                    };
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new double[]{0.0, 0.0, 0.0};
    }
} 