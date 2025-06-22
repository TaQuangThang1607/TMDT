<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin - Comment Management</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
    <link href="/css/stylesAdmin.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>>
    <style>
        .content-container {
            padding-top: 100px;
        }
        @media (max-width: 768px) {
            .content-container {
                padding-top: 80px;
            }
        }
    </style>
</head>
<body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />


    <div id="layoutSidenav">

        <jsp:include page="../layout/sidebar.jsp" />

        <div id="layoutSidenav_content">
            <!-- ----------------------------------------------------------------- -->


            <main>

    <div class="container mx-auto p-4 content-container">
        <h1 class="text-3xl font-bold mb-4">Comment Management</h1>

        <!-- Filter Form -->
        <div class="bg-white p-4 rounded-lg shadow-md mb-6">
            <form action="/admin/comments" method="get" class="row g-3">
                <div class="col-md-4">
                    <label for="productId" class="form-label">Id sản phẩm</label>
                    <input type="number" class="form-control" id="productId" name="productId" value="${param.productId}" placeholder="Enter Mã sản phẩm">
                </div>
                <div class="col-md-4">
                    <label for="userId" class="form-label">Id người dùng</label>
                    <input type="number" class="form-control" id="userId" name="userId" value="${param.userId}" placeholder="Enter user ID">
                </div>
                <div class="col-md-4">
                    <label for="parentCommentId" class="form-label">ID bình luận</label>
                    <input type="number" class="form-control" id="parentCommentId" name="parentCommentId" value="${param.parentCommentId}" placeholder="Enter parent comment ID">
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary"><i class="fas fa-search mr-2"></i> Áp dụng bộ lọc</button>
                    <a href="/admin/comments" class="btn btn-secondary"><i class="fas fa-undo mr-2"></i> Đặt lại bộ lọc</a>
                </div>
            </form>
        </div>

        <!-- Comment List -->
        <div class="bg-white p-4 rounded-lg shadow-md">
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Product</th>
                        <th>User</th>
                        <th>Content</th>
                        <th>Created At</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="comment" items="${comments}">
                        <tr>
                            <td>${comment.id}</td>
                            <td>${comment.product.name}</td>
                            <td>${comment.user.email}</td>
                            <td>
                                ${comment.content}
                                <c:if test="${not empty comment.parentComment}">
                                    <p class="text-sm text-muted">Reply to: ${comment.parentComment.content}</p>
                                </c:if>
                            </td>
                            <td><fmt:formatDate value="${comment.createdAtAsDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                            <td>
                                <form action="/admin/comments/${comment.id}/update" method="post" style="display:inline;">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="text" name="content" value="${comment.content}" class="form-control mb-2" required>
                                    <button type="submit" class="btn btn-warning btn-sm">Update</button>
                                </form>
                                <form action="/admin/comments/${comment.id}/delete" method="post" style="display:inline;" onsubmit="return confirm('Are you sure?');">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                </form>
                                <form action="/admin/comments/${comment.id}/reply" method="post" style="display:inline;">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="text" name="content" placeholder="Reply..." class="form-control mb-2" required>
                                    <button type="submit" class="btn btn-info btn-sm">Reply</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <!-- Pagination -->
            <c:if test="${totalPages > 1}">
                <nav aria-label="Page navigation" class="mt-4">
                    <ul class="pagination justify-content-center">
                        <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
                            <a class="page-link" href="/admin/comments?page=${currentPage - 1}&size=${size}&productId=${param.productId}&userId=${param.userId}&parentCommentId=${param.parentCommentId}">Previous</a>
                        </li>
                        <c:forEach begin="0" end="${totalPages - 1}" var="i">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="/admin/comments?page=${i}&size=${size}&productId=${param.productId}&userId=${param.userId}&parentCommentId=${param.parentCommentId}">${i + 1}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item ${currentPage == totalPages - 1 ? 'disabled' : ''}">
                            <a class="page-link" href="/admin/comments?page=${currentPage + 1}&size=${size}&productId=${param.productId}&userId=${param.userId}&parentCommentId=${param.parentCommentId}">Next</a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>

    </main>



            <!-- ----------------------------------------------------------------- -->
            <jsp:include page="../layout/footer.jsp" />


        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
    <script src="/js/scripts.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
    <script src="/assets/demo/chart-area-demo.js"></script>
    <script src="/assets/demo/chart-bar-demo.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"
        crossorigin="anonymous"></script>
    <script src="/js/datatables-simple-demo.js"></script>
</body>
</html>
