package com.feedback.model;

public class Course {
    private int id;
    private String name;
    private String code;
    private String instructor;
    private boolean feedbackSubmitted;
    private double avgTeachingRating;
    private double avgClarityRating;
    private double avgEngagementRating;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getInstructor() {
        return instructor;
    }
    
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    
    public boolean isFeedbackSubmitted() {
        return feedbackSubmitted;
    }
    
    public void setFeedbackSubmitted(boolean feedbackSubmitted) {
        this.feedbackSubmitted = feedbackSubmitted;
    }
    
    public double getAvgTeachingRating() {
        return avgTeachingRating;
    }
    
    public void setAvgTeachingRating(double avgTeachingRating) {
        this.avgTeachingRating = avgTeachingRating;
    }
    
    public double getAvgClarityRating() {
        return avgClarityRating;
    }
    
    public void setAvgClarityRating(double avgClarityRating) {
        this.avgClarityRating = avgClarityRating;
    }
    
    public double getAvgEngagementRating() {
        return avgEngagementRating;
    }
    
    public void setAvgEngagementRating(double avgEngagementRating) {
        this.avgEngagementRating = avgEngagementRating;
    }
} 