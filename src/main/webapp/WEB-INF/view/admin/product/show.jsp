<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>

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
                        <h2>Danh sách sản phẩm</h2>
                        <div class="d-flex justify-content-end">

                            <a href="/admin/products/create" class="btn btn-success m-3">Create</a>
                        </div>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Tên</th>
                                    <th>Giá</th>
                                    <th>Kích cỡ</th>
                                    <th>Màu sắc</th>
                                    <th>Chất liệu</th>
                                    <th>Tồn kho</th>
                                    <th>Danh mục</th>
                                    <th>Hình ảnh</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="product" items="${products}">
                                    <tr>
                                        <td>${product.id}</td>
                                        <td>${product.name}</td>
                                        <td>${product.price}</td>
                                        <td>${product.size}</td>
                                        <td>${product.color}</td>
                                        <td>${product.material}</td>
                                        <td>${product.stock}</td>
                                        <td>${product.categoryName}</td>
                                        <td><img src="${product.imageUrl}" alt="${product.name}" width="50"></td>
                                        <td>
                                            <a href="/admin/products/update/${product.id}"
                                                class="btn btn-warning">Update</a>
                                            <a href="/admin/products/remove/${product.id}"
                                                class="btn btn-danger">Remove</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
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