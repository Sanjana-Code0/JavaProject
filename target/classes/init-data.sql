-- Insert default admin user
INSERT INTO users (username, password, email, role) 
VALUES ('admin', 'admin123', 'admin@example.com', 'ADMIN');

-- Insert a sample faculty user
INSERT INTO users (username, password, email, role)
VALUES ('faculty', 'faculty123', 'faculty@example.com', 'FACULTY');

-- Insert a sample student user
INSERT INTO users (username, password, email, role)
VALUES ('student', 'student123', 'student@example.com', 'STUDENT'); 