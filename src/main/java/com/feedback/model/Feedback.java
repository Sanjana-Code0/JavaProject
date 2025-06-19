package com.feedback.model;

public class Feedback {
    private int id;
    private int studentId;
    private int courseId;
    private int teachingRating;
    private int clarityRating;
    private int engagementRating;
    private String comments;
    private String createdAt;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getTeachingRating() {
        return teachingRating;
    }

    public void setTeachingRating(int teachingRating) {
        this.teachingRating = teachingRating;
    }

    public int getClarityRating() {
        return clarityRating;
    }

    public void setClarityRating(int clarityRating) {
        this.clarityRating = clarityRating;
    }

    public int getEngagementRating() {
        return engagementRating;
    }

    public void setEngagementRating(int engagementRating) {
        this.engagementRating = engagementRating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
} 