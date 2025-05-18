<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8">
                    <title>Fruitables - Vegetable Website Template</title>
                    <meta content="width=device-width, initial-scale=1.0" name="viewport">
                    <meta content="" name="keywords">
                    <meta content="" name="description">

                    <!-- Google Web Fonts -->
                    <link rel="preconnect" href="https://fonts.googleapis.com">
                    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                    <link
                        href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                        rel="stylesheet">

                    <!-- Icon Font Stylesheet -->
                    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                        rel="stylesheet">

                    <!-- Libraries Stylesheet -->
                    <link href="/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                    <link href="/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


                    <!-- Customized Bootstrap Stylesheet -->
                    <link href="/css/bootstrap.min.css" rel="stylesheet">

                    <!-- Template Stylesheet -->
                    <link href="/css/style.css" rel="stylesheet">
                </head>

                <body>

                    <!-- Spinner Start -->
                    <div id="spinner"
                        class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                    <!-- Spinner End -->



                    <jsp:include page="layout/header.jsp" />



                    <!-- Hero Start -->
                    <div class="container-fluid py-5 mb-5 hero-header">
                        <div id="carouselExample" class="carousel slide">
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img src="/images/background/bg1.png" class="d-block w-100" alt="...">
                                </div>
                                <div class="carousel-item">
                                    <img src="/images/background/bg2.png" class="d-block w-100" alt="...">
                                </div>
                                <div class="carousel-item">
                                    <img src="/images/background/bg3.png" class="d-block w-100" alt="...">
                                </div>
                            </div>
                            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample"
                                data-bs-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                            <button class="carousel-control-next" type="button" data-bs-target="#carouselExample"
                                data-bs-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Next</span>
                            </button>
                        </div>
                    </div>
                    <!-- Hero End -->


                    <!-- Featurs Section Start -->
                    <div class="container-fluid featurs py-5">
                        <div class="container py-5">
                            <div class="row g-4">
                                <div class="col-md-6 col-lg-3">
                                    <div class="featurs-item text-center rounded bg-light p-4">
                                        <div class="featurs-icon btn-square rounded-circle bg-secondary mb-5 mx-auto">
                                            <i class="fas fa-car-side fa-3x text-white"></i>
                                        </div>
                                        <div class="featurs-content text-center">
                                            <h5>Free Shipping</h5>
                                            <p class="mb-0">Free on order over $300</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 col-lg-3">
                                    <div class="featurs-item text-center rounded bg-light p-4">
                                        <div class="featurs-icon btn-square rounded-circle bg-secondary mb-5 mx-auto">
                                            <i class="fas fa-user-shield fa-3x text-white"></i>
                                        </div>
                                        <div class="featurs-content text-center">
                                            <h5>Security Payment</h5>
                                            <p class="mb-0">100% security payment</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 col-lg-3">
                                    <div class="featurs-item text-center rounded bg-light p-4">
                                        <div class="featurs-icon btn-square rounded-circle bg-secondary mb-5 mx-auto">
                                            <i class="fas fa-exchange-alt fa-3x text-white"></i>
                                        </div>
                                        <div class="featurs-content text-center">
                                            <h5>30 Day Return</h5>
                                            <p class="mb-0">30 day money guarantee</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 col-lg-3">
                                    <div class="featurs-item text-center rounded bg-light p-4">
                                        <div class="featurs-icon btn-square rounded-circle bg-secondary mb-5 mx-auto">
                                            <i class="fa fa-phone-alt fa-3x text-white"></i>
                                        </div>
                                        <div class="featurs-content text-center">
                                            <h5>24/7 Support</h5>
                                            <p class="mb-0">Support every time fast</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Featurs Section End -->


                    <!-- Fruits Shop Start-->
                    <div class="container-fluid fruite py-5">
                        <div class="container py-5">
                            <div class="tab-class text-center">
                                <div class="row g-4">
                                    <div class="col-lg-4 text-start">
                                        <h1>Products</h1>
                                    </div>

                                </div>
                                <div class="tab-content">
                                    <div id="tab-1" class="tab-pane fade show p-0 active">
                                        <div class="row g-4">
                                            <div class="col-lg-12">
                                                <div class="row g-4">
                                                    <c:forEach var="product" items="${product}">

                                                        <div class="col-md-6 col-lg-4 col-xl-3">
                                                            <div class="rounded position-relative fruite-item">
                                                                <div class="fruite-img">
                                                                    <img src="/images/product/${product.imageUrl}"
                                                                        class="img-fluid w-100 rounded-top" alt="">
                                                                </div>

                                                                <div
                                                                    class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                    <h4>${product.name}</h4>

                                                                    <div
                                                                        class="d-flex justify-content-between flex-lg-wrap">
                                                                        <p class="text-dark fs-5 fw-bold mb-0">
                                                                            ${product.price} đ</p>
                                                                        <form action="/add-product-to-cart/${product.id}" method="post">
                                                                                <input type="hidden"
                                                                                name="${_csrf.parameterName}"
                                                                                value="${_csrf.token}" />
                                                                            <button type="submit"
                                                                                class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                                    class="fa fa-shopping-bag me-2 text-primary"></i>
                                                                                Thêm vào giỏ hàng</button>

                                                                        </form>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>


                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Fruits Shop End-->


                    <!-- Featurs Start -->
                    <div class="container-fluid service py-5">
                        <div class="container py-5">
                            <div class="row g-4 justify-content-center">
                                <div class="col-md-6 col-lg-4">
                                    <a href="#">
                                        <div class="service-item bg-secondary rounded border border-secondary">
                                            <img src="img/featur-1.jpg" class="img-fluid rounded-top w-100" alt="">
                                            <div class="px-4 rounded-bottom">
                                                <div class="service-content bg-primary text-center p-4 rounded">
                                                    <h5 class="text-white">Fresh Apples</h5>
                                                    <h3 class="mb-0">20% OFF</h3>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <div class="col-md-6 col-lg-4">
                                    <a href="#">
                                        <div class="service-item bg-dark rounded border border-dark">
                                            <img src="img/featur-2.jpg" class="img-fluid rounded-top w-100" alt="">
                                            <div class="px-4 rounded-bottom">
                                                <div class="service-content bg-light text-center p-4 rounded">
                                                    <h5 class="text-primary">Tasty Fruits</h5>
                                                    <h3 class="mb-0">Free delivery</h3>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                                <div class="col-md-6 col-lg-4">
                                    <a href="#">
                                        <div class="service-item bg-primary rounded border border-primary">
                                            <img src="img/featur-3.jpg" class="img-fluid rounded-top w-100" alt="">
                                            <div class="px-4 rounded-bottom">
                                                <div class="service-content bg-secondary text-center p-4 rounded">
                                                    <h5 class="text-white">Exotic Vegitable</h5>
                                                    <h3 class="mb-0">Discount 30$</h3>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Featurs End -->

                    <jsp:include page="layout/footer.jsp" />


                    <!-- JavaScript Libraries -->
                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                    <script src="/lib/easing/easing.min.js"></script>
                    <script src="/lib/waypoints/waypoints.min.js"></script>
                    <script src="/lib/lightbox/js/lightbox.min.js"></script>
                    <script src="/lib/owlcarousel/owl.carousel.min.js"></script>

                    <!-- Template Javascript -->
                    <script src="/js/main.js"></script>
                </body>

                </html>