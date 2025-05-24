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


<c:if test="${empty requestScope.cart}">
                                                <div>
                                                    gior hang rong
                                                </div>
                                            </c:if>
<!-- Checkout Page Start -->
        <div class="container-fluid py-5">
            <div class="container py-5">
                <h1 class="mb-4">Billing details</h1>
                    <div class="row g-5">
                        <form:form action="/place-order" method="post" modelAttribute="orderDTO">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

                            <div class="col-md-12 col-lg-6 col-xl-7">
                                <div class="form-item">
                                    <label class="form-label my-3">FullName<sup>*</sup></label>
                                    <form:input path="receiverName" cssClass="form-control" required="true" />
                                </div>

                                <div class="form-item">
                                    <label class="form-label my-3">Address <sup>*</sup></label>
                                    <form:input path="receiverAddress" cssClass="form-control" required="true" />
                                </div>

                                <div class="form-item">
                                    <label class="form-label my-3">Phone<sup>*</sup></label>
                                    <form:input path="receiverPhone" cssClass="form-control" required="true" />
                                </div>

                                <hr>

                                <div class="form-item">
                                    <form:textarea path="receiverNote" cssClass="form-control" spellcheck="false" cols="30" rows="11" />
                                </div>

                                <button type="submit" class="btn btn-success">Send</button>
                            </div>
                        </form:form>

                    </div>


                        <div class="col-md-12 col-lg-6 col-xl-5">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th scope="col">Products</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Price</th>
                                            <th scope="col">Quantity</th>
                                            <th scope="col">Total</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="cartItem" items="${cartItems}">
                                            <tr>
                                                <td>
                                                    <div class="d-flex align-items-center mt-2">
                                                        <img src="/images/product/${cartItem.imageUrl}" 
                                                            class="img-fluid rounded-circle" 
                                                            style="width: 90px; height: 90px;" 
                                                            alt="${cartItem.productName}">
                                                    </div>
                                                </td>
                                                <td class="py-5">${cartItem.productName}</td>
                                                <td class="py-5">
                                                    <fmt:formatNumber type="number" value="${cartItem.price}" /> đ
                                                </td>
                                                <td class="py-5">${cartItem.quantity}</td>
                                                <td class="py-5">
                                                    <fmt:formatNumber type="number" value="${cartItem.price * cartItem.quantity}" /> đ
                                                </td>
                                            </tr>
                                        </c:forEach>
                                       
                                        <tr>
                                            <th scope="row">
                                            </th>
                                            <td class="py-5">
                                                <p class="mb-0 text-dark text-uppercase py-3">TOTAL</p>
                                            </td>
                                            <td class="py-5"></td>
                                            <td class="py-5"></td>
                                            <td class="py-5">
                                                <div class="py-3 border-bottom border-top">
                                                    <p class="mb-0 text-dark">
                                                      <fmt:formatNumber type="number" value="${totalPrice}" /> đ


                                                    </p>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>

                            
                            
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!-- Checkout Page End -->







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