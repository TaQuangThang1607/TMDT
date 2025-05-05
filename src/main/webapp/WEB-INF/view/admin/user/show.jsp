<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="utf-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
            <meta name="description" content="" />
            <meta name="author" content="" />
            <title>Dashboard - SB Admin</title>
            <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
            <link href="/css/stylesAdmin.css" rel="stylesheet" />
            <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        </head>

        <body class="sb-nav-fixed">
            <jsp:include page="../layout/header.jsp" />


            <div id="layoutSidenav">

                <jsp:include page="../layout/sidebar.jsp" />

                <div id="layoutSidenav_content">
                    <!-- ----------------------------------------------------------------- -->


                    <div class="container mt-5">
                        <h2 class="mb-4">User Table</h2>
                        <div class="d-flex justify-content-end mb-3">
                            <a href="/admin/create/user" class="btn btn-primary">Create User</a>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered table-hover align-middle">
                                <thead class="table-dark text-center">
                                    <tr>
                                        <th>Id</th>
                                        <th>Email</th>
                                        <th>Phone</th>
                                        <th>Address</th>
                                        <th>Role</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="o" items="${showUser}">
                                        <tr>
                                            <td>${o.id}</td>
                                            <td>${o.email}</td>
                                            <td>${o.phone}</td>
                                            <td>${o.address}</td>
                                            <td>${o.role.name}</td>
                                            <td class="text-center">
                                                <a href="#" class="btn btn-sm btn-info">View</a>
                                                <a href="/admin/user/update/${o.id}"
                                                    class="btn btn-sm btn-warning">Update</a>
                                                <a href="/admin/users/remove/${o.id}"
                                                    class="btn btn-sm btn-danger">Delete</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>



                    <!-- ----------------------------------------------------------------- -->
                    <jsp:include page="../layout/footer.jsp" />


                </div>
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                crossorigin="anonymous"></script>
            <script src="/js/scripts.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js"
                crossorigin="anonymous"></script>
            <script src="/assets/demo/chart-area-demo.js"></script>
            <script src="/assets/demo/chart-bar-demo.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"
                crossorigin="anonymous"></script>
            <script src="/js/datatables-simple-demo.js"></script>
        </body>

        </html>