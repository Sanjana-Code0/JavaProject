<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Feedback System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .dashboard-card {
            margin-bottom: 20px;
            transition: transform 0.2s;
        }
        .dashboard-card:hover {
            transform: translateY(-5px);
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="#">Admin Dashboard</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <span class="nav-link">Welcome, ${sessionScope.user.username}</span>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../logout">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h2>Admin Controls</h2>
        
        <div class="row mt-4">
            <div class="col-md-4">
                <div class="card dashboard-card">
                    <div class="card-body">
                        <h5 class="card-title">Manage Courses</h5>
                        <p class="card-text">Add, edit, or remove courses from the system.</p>
                        <a href="manage-courses.jsp" class="btn btn-primary">Manage Courses</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card dashboard-card">
                    <div class="card-body">
                        <h5 class="card-title">Manage Users</h5>
                        <p class="card-text">Add, edit, or remove users from the system.</p>
                        <a href="manage-users.jsp" class="btn btn-primary">Manage Users</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card dashboard-card">
                    <div class="card-body">
                        <h5 class="card-title">View Reports</h5>
                        <p class="card-text">View feedback reports and statistics.</p>
                        <a href="reports.jsp" class="btn btn-primary">View Reports</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 