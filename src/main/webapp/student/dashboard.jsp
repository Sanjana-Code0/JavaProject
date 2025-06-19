<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        :root {
            --primary-color: #2c3e50;    /* 60% - Main color */
            --secondary-color: #34495e;  /* 30% - Secondary color */
            --accent-color: #27ae60;     /* 10% - Accent color */
            --text-light: #ecf0f1;
            --text-dark: #2c3e50;
            --shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            --transition: all 0.3s ease;
        }

        body {
            background-color: #f8f9fa;
            color: var(--text-dark);
        }

        .navbar {
            background-color: var(--primary-color) !important;
            box-shadow: var(--shadow);
            padding: 1rem 0;
        }

        .navbar-brand {
            font-weight: 600;
            font-size: 1.5rem;
            color: var(--text-light) !important;
        }

        .navbar-text {
            font-size: 1.1rem;
        }

        .btn-enroll {
            background-color: var(--accent-color);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 8px;
            cursor: pointer;
            transition: var(--transition);
            font-weight: 500;
            box-shadow: var(--shadow);
        }

        .btn-enroll:hover {
            background-color: #219a52;
            transform: translateY(-2px);
            box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15);
        }

        .section-title {
            margin: 40px 0 25px;
            padding-bottom: 15px;
            border-bottom: 3px solid var(--accent-color);
            color: var(--primary-color);
            font-weight: 600;
            position: relative;
        }

        .section-title::after {
            content: '';
            position: absolute;
            bottom: -3px;
            left: 0;
            width: 60px;
            height: 3px;
            background-color: var(--accent-color);
        }

        .course-card {
            margin-bottom: 25px;
            border: none;
            border-radius: 12px;
            box-shadow: var(--shadow);
            transition: var(--transition);
            background-color: white;
        }

        .course-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 15px rgba(0, 0, 0, 0.1);
        }

        .card-body {
            padding: 1.5rem;
        }

        .card-title {
            color: var(--primary-color);
            font-weight: 600;
            margin-bottom: 1rem;
        }

        .card-text {
            color: var(--secondary-color);
            margin-bottom: 1.5rem;
        }

        .feedback-status {
            display: inline-block;
            padding: 8px 16px;
            border-radius: 8px;
            margin-top: 10px;
            font-weight: 500;
            transition: var(--transition);
        }

        .status-submitted {
            background-color: var(--accent-color);
            color: white;
        }

        .alert {
            border-radius: 10px;
            border: none;
            box-shadow: var(--shadow);
            padding: 1rem 1.5rem;
        }

        .alert-info {
            background-color: #e8f4f8;
            color: var(--primary-color);
            border-left: 4px solid #3498db;
        }

        .btn-outline-light {
            border-width: 2px;
            font-weight: 500;
            transition: var(--transition);
        }

        .btn-outline-light:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .btn-primary {
            background-color: var(--accent-color);
            border-color: var(--accent-color);
            transition: var(--transition);
        }

        .btn-primary:hover {
            background-color: #219a52;
            border-color: #219a52;
            transform: translateY(-2px);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        /* Add icons to buttons */
        .btn-enroll::before {
            content: '\f067';
            font-family: 'Font Awesome 5 Free';
            font-weight: 900;
            margin-right: 8px;
        }

        .btn-outline-light::after {
            content: '\f2f5';
            font-family: 'Font Awesome 5 Free';
            font-weight: 900;
            margin-left: 8px;
        }

        /* Responsive adjustments */
        @media (max-width: 768px) {
            .navbar-text {
                font-size: 1rem;
            }
            
            .course-card {
                margin-bottom: 20px;
            }
            
            .section-title {
                margin: 30px 0 20px;
            }
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="#">
                <i class="fas fa-graduation-cap me-2"></i>Student Feedback System
            </a>
            <div class="navbar-text text-light d-flex align-items-center">
                <i class="fas fa-user me-2"></i>Welcome, ${user.username}!
                <a href="${pageContext.request.contextPath}/student/dashboard#available-courses" class="btn btn-enroll btn-sm ms-3">Enroll Now</a>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm ms-2">Logout</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <c:if test="${not empty sessionScope.message}">
            <div class="alert alert-${sessionScope.messageType} alert-dismissible fade show" role="alert">
                <i class="fas fa-info-circle me-2"></i>${sessionScope.message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <c:remove var="message" scope="session"/>
            <c:remove var="messageType" scope="session"/>
        </c:if>

        <h2 class="section-title">
            <i class="fas fa-book me-2"></i>My Enrolled Courses
        </h2>
        <div class="row">
            <c:forEach items="${enrolledCourses}" var="course">
                <div class="col-md-4">
                    <div class="course-card">
                        <div class="card-body">
                            <h5 class="card-title">${course.name}</h5>
                            <p class="card-text">
                                <i class="fas fa-chalkboard-teacher me-2"></i><strong>Instructor:</strong> ${course.instructor}<br>
                                <i class="fas fa-hashtag me-2"></i><strong>Code:</strong> ${course.code}
                            </p>
                            <c:choose>
                                <c:when test="${course.feedbackSubmitted}">
                                    <span class="feedback-status status-submitted">
                                        <i class="fas fa-check-circle me-2"></i>Feedback Submitted
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <a href="feedback-form.jsp?courseId=${course.id}" class="btn btn-primary">
                                        <i class="fas fa-comment-alt me-2"></i>Submit Feedback
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <c:if test="${empty enrolledCourses}">
            <div class="alert alert-info">
                <i class="fas fa-info-circle me-2"></i>You are not enrolled in any courses yet.
            </div>
        </c:if>

        <h2 id="available-courses" class="section-title">
            <i class="fas fa-plus-circle me-2"></i>Available Courses
        </h2>
        <div class="row">
            <c:forEach items="${availableCourses}" var="course">
                <div class="col-md-4">
                    <div class="course-card">
                        <div class="card-body">
                            <h5 class="card-title">${course.name}</h5>
                            <p class="card-text">
                                <i class="fas fa-chalkboard-teacher me-2"></i><strong>Instructor:</strong> ${course.instructor}<br>
                                <i class="fas fa-hashtag me-2"></i><strong>Code:</strong> ${course.code}
                            </p>
                            <form action="${pageContext.request.contextPath}/enroll-course" method="post" style="margin-top: 10px;">
                                <input type="hidden" name="courseId" value="${course.id}">
                                <button type="submit" class="btn btn-enroll">Enroll Now</button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <c:if test="${empty availableCourses}">
            <div class="alert alert-info">
                <i class="fas fa-info-circle me-2"></i>No more courses available for enrollment.
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 