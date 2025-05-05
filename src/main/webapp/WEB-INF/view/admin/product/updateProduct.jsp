<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <!DOCTYPE html>
            <html>

            <head>
                <meta charset="UTF-8">
                <title>Sửa sản phẩm</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            </head>

            <body>
                <div class="container mt-5">
                    <h2>Sửa sản phẩm</h2>
                    <form:form action="/admin/products/update/${product.id}" method="post" modelAttribute="product">
                        <form:hidden path="id" />
                        <div class="mb-3">
                            <label for="name" class="form-label">Tên sản phẩm</label>
                            <form:input path="name" class="form-control" id="name" required="true" />
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">Mô tả</label>
                            <form:textarea path="description" class="form-control" id="description" />
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">Giá</label>
                            <form:input path="price" class="form-control" id="price" type="number" step="0.01"
                                required="true" />
                        </div>
                        <div class="mb-3">
                            <label for="size" class="form-label">Kích cỡ</label>
                            <form:input path="size" class="form-control" id="size" />
                        </div>
                        <div class="mb-3">
                            <label for="color" class="form-label">Màu sắc</label>
                            <form:input path="color" class="form-control" id="color" />
                        </div>
                        <div class="mb-3">
                            <label for="material" class="form-label">Chất liệu</label>
                            <form:input path="material" class="form-control" id="material" />
                        </div>
                        <div class="mb-3">
                            <label for="stock" class="form-label">Tồn kho</label>
                            <form:input path="stock" class="form-control" id="stock" type="number" required="true" />
                        </div>
                        <div class="mb-3">
                            <label for="categoryId" class="form-label">Danh mục</label>
                            <form:select path="categoryId" class="form-control" id="categoryId" required="true">
                                <form:option value="" label="-- Chọn danh mục --" />
                                <form:options items="${categories}" itemValue="id" itemLabel="name" />
                            </form:select>
                        </div>
                        <div class="mb-3">
                            <label for="imageUrl" class="form-label">URL hình ảnh</label>
                            <form:input path="imageUrl" class="form-control" id="imageUrl" />
                        </div>
                        <button type="submit" class="btn btn-primary">Cập nhật</button>
                        <a href="/admin/products" class="btn btn-secondary">Hủy</a>
                    </form:form>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>