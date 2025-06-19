-- Drop existing tables if they exist
DROP TABLE IF EXISTS feedback;
DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
use feedback_system;
-- Create courses table
CREATE TABLE courses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE,
    instructor VARCHAR(100) NOT NULL
);

-- Create enrollments table
CREATE TABLE enrollments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- Create feedback table
CREATE TABLE feedback (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    teaching_rating INT NOT NULL,
    clarity_rating INT NOT NULL,
    engagement_rating INT NOT NULL,
    comments TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

-- Insert sample data for testing
-- Insert admin user
INSERT INTO users (username, password, email, role) 
VALUES ('admin', 'admin123', 'admin@example.com', 'ADMIN');

-- Insert faculty user
INSERT INTO users (username, password, email, role) 
VALUES ('faculty', 'faculty123', 'faculty@example.com', 'FACULTY');

-- Insert sample courses
INSERT INTO courses (name, code, instructor) VALUES 
('Java Programming', 'CS101', 'Prof.Aarti Karande'),
('Database Systems', 'CS102', 'Prof.Harshil kanakia'),
('Web Development', 'CS103', 'Prof.Pallavi Thakur');

-- Insert sample student
INSERT INTO users (username, password, email, role) 
VALUES ('student', 'student123', 'student@example.com', 'STUDENT');

-- Enroll student in courses
INSERT INTO enrollments (student_id, course_id) 
SELECT 
    (SELECT id FROM users WHERE username = 'student'),
    id 
FROM courses;