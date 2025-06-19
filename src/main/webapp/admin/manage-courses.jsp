<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Courses - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="dashboard.jsp">Admin Dashboard</a>
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
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Manage Courses</h2>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addCourseModal">
                Add New Course
            </button>
        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-${messageType} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>

        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Name</th>
                        <th>Instructor</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${courses}" var="course">
                        <tr>
                            <td>${course.code}</td>
                            <td>${course.name}</td>
                            <td>${course.instructor}</td>
                            <td>
                                <button class="btn btn-sm btn-warning" onclick="editCourse(${course.id})">Edit</button>
                                <button class="btn btn-sm btn-danger" onclick="deleteCourse(${course.id})">Delete</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Add Course Modal -->
    <div class="modal fade" id="addCourseModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Add New Course</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form action="../admin/course" method="post">
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="code" class="form-label">Course Code</label>
                            <input type="text" class="form-control" id="code" name="code" required>
                        </div>
                        <div class="mb-3">
                            <label for="name" class="form-label">Course Name</label>
                            <input type="text" class="form-control" id="name" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="instructor" class="form-label">Instructor</label>
                            <input type="text" class="form-control" id="instructor" name="instructor" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Add Course</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function editCourse(courseId) {
            // Implement edit functionality
            window.location.href = 'edit-course.jsp?id=' + courseId;
        }

        function deleteCourse(courseId) {
            if (confirm('Are you sure you want to delete this course?')) {
                window.location.href = '../admin/course?action=delete&id=' + courseId;
            }
        }
    </script>
</body>
</html> 