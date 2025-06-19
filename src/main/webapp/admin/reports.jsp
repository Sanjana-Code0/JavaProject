<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback Reports - Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .rating {
            font-size: 1.2em;
            color: #ffc107;
        }
        .table th {
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/dashboard.jsp">Admin Dashboard</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/manage-courses.jsp">Manage Courses</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/admin/manage-users.jsp">Manage Users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/admin/reports.jsp">Reports</a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h2>Feedback Reports</h2>
        
        <div class="btn-group mb-4" role="group">
            <a href="${pageContext.request.contextPath}/admin/reports?type=course" 
               class="btn btn-outline-primary ${reportType == 'course' ? 'active' : ''}">
                Course-wise Reports
            </a>
            <a href="${pageContext.request.contextPath}/admin/reports?type=instructor" 
               class="btn btn-outline-primary ${reportType == 'instructor' ? 'active' : ''}">
                Instructor-wise Reports
            </a>
            <a href="${pageContext.request.contextPath}/admin/reports?type=detailed" 
               class="btn btn-outline-primary ${reportType == 'detailed' ? 'active' : ''}">
                Detailed Analysis
            </a>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <c:choose>
                                    <c:when test="${reportType == 'course'}">
                                        <th>Course Name</th>
                                    </c:when>
                                    <c:when test="${reportType == 'instructor'}">
                                        <th>Instructor</th>
                                    </c:when>
                                    <c:otherwise>
                                        <th>Course Name</th>
                                        <th>Instructor</th>
                                    </c:otherwise>
                                </c:choose>
                                <th>Teaching Rating</th>
                                <th>Clarity Rating</th>
                                <th>Engagement Rating</th>
                                <th>Overall Average</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${stats}" var="stat">
                                <tr>
                                    <c:if test="${reportType != 'instructor'}">
                                        <td>${stat.courseName}</td>
                                    </c:if>
                                    <c:if test="${reportType != 'course'}">
                                        <td>${stat.instructor}</td>
                                    </c:if>
                                    <td>
                                        <div class="rating">
                                            <c:forEach begin="1" end="5" var="i">
                                                <i class="bi bi-star${i <= stat.teachingRating ? '-fill' : ''}"></i>
                                            </c:forEach>
                                            <span class="ms-2">(${String.format("%.1f", stat.teachingRating)})</span>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="rating">
                                            <c:forEach begin="1" end="5" var="i">
                                                <i class="bi bi-star${i <= stat.clarityRating ? '-fill' : ''}"></i>
                                            </c:forEach>
                                            <span class="ms-2">(${String.format("%.1f", stat.clarityRating)})</span>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="rating">
                                            <c:forEach begin="1" end="5" var="i">
                                                <i class="bi bi-star${i <= stat.engagementRating ? '-fill' : ''}"></i>
                                            </c:forEach>
                                            <span class="ms-2">(${String.format("%.1f", stat.engagementRating)})</span>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="rating">
                                            <c:forEach begin="1" end="5" var="i">
                                                <i class="bi bi-star${i <= stat.overallAverage ? '-fill' : ''}"></i>
                                            </c:forEach>
                                            <span class="ms-2">(${String.format("%.1f", stat.overallAverage)})</span>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 