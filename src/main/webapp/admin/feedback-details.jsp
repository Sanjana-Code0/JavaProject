<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Feedback Details - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .feedback-card {
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
            <a class="navbar-brand" href="#">Admin Dashboard</a>
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

        <div class="d-flex justify-content-between align-items-center">
            <h2 class="section-title">Feedback Details</h2>
            <a href="${pageContext.request.contextPath}/admin/dashboard.jsp" class="btn btn-secondary">Back to Dashboard</a>
        </div>

        <div class="row">
            <c:forEach items="${feedbacks}" var="feedback">
                <div class="col-md-6">
                    <div class="card feedback-card">
                        <div class="card-body">
                            <h5 class="card-title">Student: ${feedback.studentName}</h5>
                            <div class="ratings">
                                <p>
                                    <strong>Teaching Rating:</strong>
                                    <span class="rating">${feedback.teachingRating}</span>
                                </p>
                                <p>
                                    <strong>Clarity Rating:</strong>
                                    <span class="rating">${feedback.clarityRating}</span>
                                </p>
                                <p>
                                    <strong>Engagement Rating:</strong>
                                    <span class="rating">${feedback.engagementRating}</span>
                                </p>
                            </div>
                            <div class="comments mt-3">
                                <strong>Comments:</strong>
                                <p class="mt-2">${feedback.comments}</p>
                            </div>
                            <div class="text-muted mt-2">
                                <small>Submitted on: ${feedback.createdAt}</small>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <c:if test="${empty feedbacks}">
            <div class="alert alert-info">
                No feedback has been submitted for this course yet.
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 