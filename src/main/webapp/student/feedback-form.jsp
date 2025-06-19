<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Submit Feedback - Feedback System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .rating {
            display: flex;
            flex-direction: row-reverse;
            justify-content: flex-end;
        }
        .rating input {
            display: none;
        }
        .rating label {
            cursor: pointer;
            font-size: 25px;
            color: #ddd;
            padding: 5px;
        }
        .rating input:checked ~ label,
        .rating label:hover,
        .rating label:hover ~ label {
            color: #ffd700;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="#">Student Feedback System</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="dashboard.jsp">Dashboard</a>
                <a class="nav-link" href="../logout">Logout</a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">Submit Course Feedback</h3>
                    </div>
                    <div class="card-body">
                        <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger" role="alert">
                                <%= request.getAttribute("error") %>
                            </div>
                        <% } %>

                        <form action="../feedback" method="post">
                            <input type="hidden" name="courseId" value="${param.courseId}">
                            
                            <div class="mb-4">
                                <label class="form-label">Teaching Quality</label>
                                <div class="rating">
                                    <input type="radio" name="teachingRating" value="5" id="teaching5" required>
                                    <label for="teaching5">★</label>
                                    <input type="radio" name="teachingRating" value="4" id="teaching4">
                                    <label for="teaching4">★</label>
                                    <input type="radio" name="teachingRating" value="3" id="teaching3">
                                    <label for="teaching3">★</label>
                                    <input type="radio" name="teachingRating" value="2" id="teaching2">
                                    <label for="teaching2">★</label>
                                    <input type="radio" name="teachingRating" value="1" id="teaching1">
                                    <label for="teaching1">★</label>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label class="form-label">Content Clarity</label>
                                <div class="rating">
                                    <input type="radio" name="clarityRating" value="5" id="clarity5" required>
                                    <label for="clarity5">★</label>
                                    <input type="radio" name="clarityRating" value="4" id="clarity4">
                                    <label for="clarity4">★</label>
                                    <input type="radio" name="clarityRating" value="3" id="clarity3">
                                    <label for="clarity3">★</label>
                                    <input type="radio" name="clarityRating" value="2" id="clarity2">
                                    <label for="clarity2">★</label>
                                    <input type="radio" name="clarityRating" value="1" id="clarity1">
                                    <label for="clarity1">★</label>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label class="form-label">Student Engagement</label>
                                <div class="rating">
                                    <input type="radio" name="engagementRating" value="5" id="engagement5" required>
                                    <label for="engagement5">★</label>
                                    <input type="radio" name="engagementRating" value="4" id="engagement4">
                                    <label for="engagement4">★</label>
                                    <input type="radio" name="engagementRating" value="3" id="engagement3">
                                    <label for="engagement3">★</label>
                                    <input type="radio" name="engagementRating" value="2" id="engagement2">
                                    <label for="engagement2">★</label>
                                    <input type="radio" name="engagementRating" value="1" id="engagement1">
                                    <label for="engagement1">★</label>
                                </div>
                            </div>

                            <div class="mb-4">
                                <label for="comments" class="form-label">Additional Comments</label>
                                <textarea class="form-control" id="comments" name="comments" rows="4"></textarea>
                            </div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Submit Feedback</button>
                                <a href="dashboard.jsp" class="btn btn-secondary">Cancel</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 