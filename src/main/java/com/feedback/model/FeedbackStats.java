package com.feedback.model;

public class FeedbackStats {
    private String courseName;
    private String instructor;
    private double teachingRating;
    private double clarityRating;
    private double engagementRating;
    private double overallAverage;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public double getTeachingRating() {
        return teachingRating;
    }

    public void setTeachingRating(double teachingRating) {
        this.teachingRating = teachingRating;
    }

    public double getClarityRating() {
        return clarityRating;
    }

    public void setClarityRating(double clarityRating) {
        this.clarityRating = clarityRating;
    }

    public double getEngagementRating() {
        return engagementRating;
    }

    public void setEngagementRating(double engagementRating) {
        this.engagementRating = engagementRating;
    }

    public double getOverallAverage() {
        return overallAverage;
    }

    public void setOverallAverage(double overallAverage) {
        this.overallAverage = overallAverage;
    }
} 