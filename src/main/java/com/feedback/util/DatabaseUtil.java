package com.feedback.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    // Update these values to match your MySQL root credentials
    private static final String URL = "jdbc:mysql://localhost:3306/feedback_system";
    private static final String USERNAME = "root";  // Your MySQL root username
    private static final String PASSWORD = "Root@123";      // Your MySQL root password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
} 