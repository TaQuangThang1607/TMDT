<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All Products</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
    <link href="/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
    <link href="/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <style>
        .filter-section {
            transition: max-height 0.3s ease-in-out;
            overflow: hidden;
        }
        .filter-section.collapsed {
            max-height: 0;
        }
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
<body class="bg-gray-100">
    <jsp:include page="layout/header.jsp" />

    <div class="container mx-auto p-4 content-container">
        <h1 class="text-3xl font-bold mb-4">All Products</h1>

        <!-- Filter Form -->
        <div class="bg-white p-4 rounded-lg shadow-md mb-6">
            <button class="btn btn-primary mb-3" type="button" data-bs-toggle="collapse" data-bs-target="#filterCollapse" aria-expanded="false" aria-controls="filterCollapse">
                <i class="fas fa-filter mr-2"></i> Filter Products
            </button>
            <div class="collapse filter-section" id="filterCollapse">
                <form action="/products" method="get" class="row g-3">
                    <div class="col-md-4">
                        <label for="name" class="form-label">Product Name</label>
                        <input type="text" class="form-control" id="name" name="name" value="${filterParams.name}" placeholder="Enter product name">
                    </div>
                    <div class="col-md-4">
                        <label for="minPrice" class="form-label">Min Price</label>
                        <input type="number" class="form-control" id="minPrice" name="minPrice" value="${filterParams.minPrice}" step="0.01" placeholder="Min price">
                    </div>
                    <div class="col-md-4">
                        <label for="maxPrice" class="form-label">Max Price</label>
                        <input type="number" class="form-control" id="maxPrice" name="maxPrice" value="${filterParams.maxPrice}" step="0.01" placeholder="Max price">
                    </div>
                    <div class="col-md-4">
                        <label for="categoryId" class="form-label">Category</label>
                        <select class="form-select" id="categoryId" name="categoryId">
                            <option value="">All Categories</option>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category.id}" ${filterParams.categoryId == category.id ? 'selected' : ''}>${category.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label for="sizeFilter" class="form-label">Size</label>
                        <select class="form-select" id="sizeFilter" name="sizeFilter">
                            <option value="">All Sizes</option>
                            <c:forEach var="size" items="${sizes}">
                                <option value="${size}" ${filterParams.size == size ? 'selected' : ''}>${size}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label for="color" class="form-label">Color</label>
                        <select class="form-select" id="color" name="color">
                            <option value="">All Colors</option>
                            <c:forEach var="color" items="${colors}">
                                <option value="${color}" ${filterParams.color == color ? 'selected' : ''}>${color}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label for="material" class="form-label">Material</label>
                        <select class="form-select" id="material" name="material">
                            <option value="">All Materials</option>
                            <c:forEach var="material" items="${materials}">
                                <option value="${material}" ${filterParams.material == material ? 'selected' : ''}>${material}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4">
                        <label for="minStock" class="form-label">Min Stock</label>
                        <input type="number" class="form-control" id="minStock" name="minStock" value="${filterParams.minStock}" placeholder="Min stock">
                    </div>
                    <div class="col-md-4">
                        <label for="minSold" class="form-label">Min Sold</label>
                        <input type="number" class="form-control" id="minSold" name="minSold" value="${filterParams.minSold}" placeholder="Min sold">
                    </div>
                    <div class="col-12">
                        <button type="submit" class="btn btn-primary"><i class="fas fa-search mr-2"></i> Apply Filters</button>
                        <a href="/products" class="btn btn-secondary"><i class="fas fa-undo mr-2"></i> Reset Filters</a>
                    </div>
                </form>
            </div>
        </div>

        <!-- Product List -->
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 row-cols-xl-4 g-4">
            <c:forEach var="product" items="${products}">
                <div class="col">
                    <div class="card h-100 shadow-sm">
                        <a href="/product/${product.id}">
                            <img src="/images/product/${product.imageUrl}" class="card-img-top" alt="${product.name}" style="height: 200px; object-fit: contain;">
                        </a>
                        <div class="card-body">
                            <h5 class="card-title"><a href="/product/${product.id}" class="text-dark">${product.name}</a></h5>
                            <p class="card-text text-primary"><fmt:formatNumber value="${product.price}" pattern="#,##0"/> đ</p>
                            <div class="d-flex justify-content-between align-items-center">
                                <form action="/add-product-to-cart/${product.id}" method="post">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <button type="submit" class="btn btn-outline-primary btn-sm">
                                        <i class="fa fa-shopping-bag mr-1"></i> Add to Cart
                                    </button>
                                </form>
                                <a href="/product/${product.id}" class="btn btn-outline-info btn-sm">
                                    <i class="fa fa-eye mr-1"></i> View
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Pagination -->
        <c:if test="${totalPages > 1}">
            <nav aria-label="Page navigation" class="mt-4">
                <ul class="pagination justify-content-center">
                    <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
                        <a class="page-link" href="/products?page=${currentPage - 1}&size=12&name=${filterParams.name}&minPrice=${filterParams.minPrice}&maxPrice=${filterParams.maxPrice}&categoryId=${filterParams.categoryId}&sizeFilter=${filterParams.size}&color=${filterParams.color}&material=${filterParams.material}&minStock=${filterParams.minStock}&minSold=${filterParams.minSold}">Previous</a>
                    </li>
                    <c:forEach begin="0" end="${totalPages - 1}" var="i">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <a class="page-link" href="/products?page=${i}&size=12&name=${filterParams.name}&minPrice=${filterParams.minPrice}&maxPrice=${filterParams.maxPrice}&categoryId=${filterParams.categoryId}&sizeFilter=${filterParams.size}&color=${filterParams.color}&material=${filterParams.material}&minStock=${filterParams.minStock}&minSold=${filterParams.minSold}">${i + 1}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item ${currentPage == totalPages - 1 ? 'disabled' : ''}">
                        <a class="page-link" href="/products?page=${currentPage + 1}&size=12&name=${filterParams.name}&minPrice=${filterParams.minPrice}&maxPrice=${filterParams.maxPrice}&categoryId=${filterParams.categoryId}&sizeFilter=${filterParams.size}&color=${filterParams.color}&material=${filterParams.material}&minStock=${filterParams.minStock}&minSold=${filterParams.minSold}">Next</a>
                    </li>
                </ul>
            </nav>
        </c:if>
    </div>

    <jsp:include page="layout/footer.jsp" />

    <!-- JavaScript Libraries -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="/lib/easing/easing.min.js"></script>
    <script src="/lib/waypoints/waypoints.min.js"></script>
    <script src="/lib/lightbox/js/lightbox.min.js"></script>
    <script src="/lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="/js/main.js"></script>
</body>
</html>
