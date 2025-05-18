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


                    <div class="container px-3 my-5 clearfix">
    <!-- Shopping cart table -->
    <div class="card">
        <div class="card-header">
            <h2>Shopping Cart</h2>
        </div>
        <div class="card-body">
            <div class="table-responsive">
              <table class="table table-bordered m-0">
                <thead>
                  <tr>
                    <!-- Set columns width -->
                    <th class="text-center py-3 px-4" style="min-width: 400px;">Product Name &amp; Details</th>
                    <th class="text-right py-3 px-4" style="width: 100px;">Price</th>
                    <th class="text-center py-3 px-4" style="width: 120px;">Quantity</th>
                    <th class="text-right py-3 px-4" style="width: 100px;">Total</th>
                    <th class="text-center align-middle py-3 px-0" style="width: 40px;"><a href="#" class="shop-tooltip float-none text-light" title="" data-original-title="Clear cart"><i class="ino ion-md-trash"></i></a></th>
                  </tr>
                </thead>
                <tbody>
                <c:forEach var="cartItems" items="${cartItems}">
                  <tr>
                      <td class="p-4">
                          <div class="media align-items-center">
                              <div>
                                  <img src="/images/product/${cartItems.product.imageUrl}"
                                      class="product-thumb"
                                      alt="Product Image"
                                      width="60"
                                      onclick="zoomImage(this.src)">
                              </div>
                              <div class="media-body">
                                  <a href="#" class="d-block text-dark">${cartItems.product.name}</a>
                                  <small>
                                      <span class="text-muted">${cartItems.product.color}</span>
                                      <span class="ui-product-color ui-product-color-sm align-text-bottom" style="background:#e81e2c;"></span>  
                                      <span class="text-muted">Size: </span> ${cartItems.product.size}  
                                  </small>
                              </div>
                          </div>
                      </td>
                      <td class="text-right font-weight-semibold align-middle p-4">${cartItems.product.price} đ</td>
                      <td class="align-middle p-4">
                          <form action="/update-cart-quantity/${cartItems.id}" method="post">
                              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                              <input type="number" name="quantity" class="form-control text-center" 
                                    value="${cartItems.quantity}" min="1" onchange="this.form.submit()">
                          </form>
                      </td>
                      <td class="text-right font-weight-semibold align-middle p-4">${cartItems.quantity * cartItems.product.price} đ</td>
                      <td class="text-center align-middle px-0">
                          <form action="/delete-cart-product/${cartItems.id}" method="post">
                              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                              <button type="submit" class="shop-tooltip close float-none text-danger" 
                                      title="" data-original-title="Remove">×</button>
                          </form>
                      </td>
                  </tr>
              </c:forEach>
              <tr>
                  <td colspan="3" class="text-right font-weight-semibold p-4">Tổng cộng:</td>
                  <td class="text-right font-weight-semibold p-4">${totalCartPrice} đ</td>
                  <td></td>
              </tr>

                </tbody>
              </table>
            </div>
            <!-- / Shopping cart table -->
        
            <div class="d-flex flex-wrap justify-content-between align-items-center pb-4">
              <div class="mt-4">
                <label class="text-muted font-weight-normal">Promocode</label>
                <input type="text" placeholder="ABC" class="form-control">
              </div>
              <div class="d-flex">
                <div class="text-right mt-4 mr-5">
                  <label class="text-muted font-weight-normal m-0">Discount</label>
                  <div class="text-large"><strong>$20</strong></div>
                </div>
                <div class="text-right mt-4">
                  <label class="text-muted font-weight-normal m-0">Total price</label>
                  <div class="text-large"><strong>$1164.65</strong></div>
                </div>
              </div>
            </div>
        
            <div class="float-right">
              <button type="button" class="btn btn-lg btn-default md-btn-flat mt-2 mr-3">Back to shopping</button>
              <button type="button" class="btn btn-lg btn-primary mt-2">Checkout</button>
            </div>
        
          </div>
      </div>
  </div>


  <!-- Zoom modal -->
<div id="zoom-modal" onclick="closeZoom()">
  <img id="zoomed-img" src="" alt="Zoomed Image">
</div>

                    <jsp:include page="layout/footer.jsp" />



                      <script>
                      function zoomImage(src) {
                        const modal = document.getElementById("zoom-modal");
                        const zoomImg = document.getElementById("zoomed-img");
                        zoomImg.src = src;
                        modal.style.display = "flex";
                      }

                      function closeZoom() {
                        document.getElementById("zoom-modal").style.display = "none";
                      }
                      </script>


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