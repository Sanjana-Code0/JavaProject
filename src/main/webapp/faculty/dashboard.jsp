<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Faculty Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .course-card {
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .rating {
            font-size: 1.2em;
            color: #ffc107;
        }
        .section-title {
            margin: 30px 0 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #eee;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">Faculty Dashboard</a>
            <div class="navbar-text text-light">
                Welcome, ${user.username}!
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm ms-3">Logout</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <c:if test="${not empty message}">
            <div class="alert alert-${messageType} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <h2 class="section-title">My Courses</h2>
        <div class="row">
            <c:forEach items="${courses}" var="course">
                <div class="col-md-4">
                    <div class="card course-card">
                        <div class="card-body">
                            <h5 class="card-title">${course.name}</h5>
                            <p class="card-text">
                                <strong>Code:</strong> ${course.code}
                            </p>
                            <div class="ratings">
                                <p>
                                    <strong>Teaching Rating:</strong>
                                    <span class="rating">${course.avgTeachingRating}</span>
                                </p>
                                <p>
                                    <strong>Clarity Rating:</strong>
                                    <span class="rating">${course.avgClarityRating}</span>
                                </p>
                                <p>
                                    <strong>Engagement Rating:</strong>
                                    <span class="rating">${course.avgEngagementRating}</span>
                                </p>
                            </div>
                            <a href="${pageContext.request.contextPath}/feedback?courseId=${course.id}" 
                               class="btn btn-primary">View Detailed Feedback</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <c:if test="${empty courses}">
            <div class="alert alert-info">
                You are not assigned to any courses yet.
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 