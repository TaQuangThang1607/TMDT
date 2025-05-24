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
    <jsp:include page="layout/header.jsp" />

    <!-- Order History Section -->
    <div class="container-fluid py-5">
        <div class="container py-5">
            <div class="row">
                <div class="col-12">
                    <div class="card shadow-sm">
                        <div class="card-header bg-primary text-white">
                            <h4 class="mb-0">
                                <i class="fas fa-shopping-bag me-2"></i>
                                Lịch sử đơn hàng
                            </h4>
                        </div>
                        <div class="card-body p-0">
                            <div class="table-responsive">
                                <table class="table table-hover table-striped mb-0">
                                    <thead class="table-dark">
                                        <tr>
                                            <th scope="col" class="text-center">
                                                <i class="fas fa-hashtag me-1"></i>
                                                Order ID
                                            </th>
                                            <th scope="col" class="text-center">
                                                <i class="fas fa-calendar-alt me-1"></i>
                                                Ngày đặt
                                            </th>
                                            <th scope="col" class="text-center">
                                                <i class="fas fa-info-circle me-1"></i>
                                                Trạng thái
                                            </th>
                                            <th scope="col" class="text-center">
                                                <i class="fas fa-dollar-sign me-1"></i>
                                                Tổng tiền
                                            </th>
                                            <th scope="col" class="text-center">
                                                <i class="fas fa-eye me-1"></i>
                                                Chi tiết
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="order" items="${orderHitory}">
                                            <tr>
                                                <td class="text-center align-middle">
                                                    <span class="badge bg-secondary">#${order.id}</span>
                                                </td>
                                                <td class="text-center align-middle">
                                                    ${order.createdDate}
                                                </td>
                                                <td class="text-center align-middle">
                                                    <c:choose>
                                                        <c:when test="${order.status == 'PENDING'}">
                                                            <span class="badge bg-warning text-dark">
                                                                <i class="fas fa-clock me-1"></i>
                                                                Chờ xử lý
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${order.status == 'CONFIRMED'}">
                                                            <span class="badge bg-info">
                                                                <i class="fas fa-check me-1"></i>
                                                                Đã xác nhận
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${order.status == 'SHIPPING'}">
                                                            <span class="badge bg-primary">
                                                                <i class="fas fa-truck me-1"></i>
                                                                Đang giao
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${order.status == 'DELIVERED'}">
                                                            <span class="badge bg-success">
                                                                <i class="fas fa-check-circle me-1"></i>
                                                                Đã giao
                                                            </span>
                                                        </c:when>
                                                        <c:when test="${order.status == 'CANCELLED'}">
                                                            <span class="badge bg-danger">
                                                                <i class="fas fa-times me-1"></i>
                                                                Đã hủy
                                                            </span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge bg-secondary">${order.status}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="text-center align-middle">
                                                    <strong class="text-success">
                                                        <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫" groupingUsed="true" />
                                                    </strong>
                                                </td>
                                                <td class="text-center align-middle">
                                                    


                                                    <button type="button" class="btn btn-primary view-detail-btn"
                                                            data-toggle="modal" data-target="#orderDetailModal"
                                                            data-order-id="${order.id}">
                                                        Xem chi tiết
                                                    </button>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                        
                                        <c:if test="${empty orderHitory}">
                                            <tr>
                                                <td colspan="5" class="text-center py-5">
                                                    <div class="text-muted">
                                                        <i class="fas fa-shopping-cart fa-3x mb-3"></i>
                                                        <h5>Chưa có đơn hàng nào</h5>
                                                        <p>Bạn chưa có đơn hàng nào. Hãy bắt đầu mua sắm ngay!</p>
                                                        <a href="/products" class="btn btn-primary">
                                                            <i class="fas fa-shopping-bag me-1"></i>
                                                            Mua sắm ngay
                                                        </a>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="layout/footer.jsp" />





    <div class="modal fade" id="orderDetailModal" tabindex="-1" aria-labelledby="orderDetailModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="orderDetailModalLabel">Chi tiết đơn hàng #<span id="modalOrderId"></span></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
      </div>
      <div class="modal-body">
        <table class="table table-bordered">
          <thead>
            <tr>
              <th>Sản phẩm</th>
              <th>Số lượng</th>
              <th>Giá</th>
              <th>Tổng</th>
            </tr>
          </thead>
          <tbody id="modalOrderItems">
            <!-- Dynamic rows go here -->
          </tbody>
        </table>
        <div class="text-end">
          <strong>Tổng cộng: <span id="modalOrderTotal" class="text-success"></span></strong>
        </div>
      </div>
    </div>
  </div>
</div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

<script>
    $('.view-detail-btn').on('click', function(e) {
    e.preventDefault();

    const orderId = $(this).attr('data-order-id');

    $.ajax({
        url: '/api/order/' + orderId,
        method: 'GET',
        success: function(data) {
            $('#orderDetailModal .modal-title').text('Chi tiết đơn hàng #' + data.id);
            $('#orderDetailModal .modal-body').html('');

            var html = '<ul>';
            data.details.forEach(function(item) {
                html += '<li>' + item.productName + ' - ' + item.quantity + ' x ' + item.price + '</li>';
            });
            html += '</ul>';
            html += '<p>Tổng tiền: ' + data.totalPrice + '</p>';

            $('#orderDetailModal .modal-body').html(html);
        },
        error: function(err) {
            alert('Không thể tải chi tiết đơn hàng');
        }
    });
});


</script>

</body>
</html>