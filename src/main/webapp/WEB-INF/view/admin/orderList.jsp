<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h2 style="text-align:center; color:#1a237e; margin:32px 0;">Quản lý đơn hàng</h2>
<table border="1" cellpadding="8" style="width:90%; margin:auto; background:#fffbe7;">
    <tr style="background:#1a237e; color:#fff;">
        <th>ID</th>
        <th>Khách hàng</th>
        <th>Ngày đặt</th>
        <th>Trạng thái</th>
        <th>Tổng tiền</th>
        <th>Cập nhật trạng thái</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.id}</td>
            <td>${order.user.fullName}</td>
            <td>${order.createdDate}</td>
            <td>${order.status}</td>
            <td>${order.totalPrice}</td>
            <td>
                <form action="/admin/order/update-status" method="post" style="margin:0;">
                    <input type="hidden" name="orderId" value="${order.id}" />
                    <select name="status">
                        <option value="PENDING" ${order.status == 'PENDING' ? 'selected' : ''}>PENDING</option>
                        <option value="PROCESSING" ${order.status == 'PROCESSING' ? 'selected' : ''}>PROCESSING</option>
                        <option value="SHIPPED" ${order.status == 'SHIPPED' ? 'selected' : ''}>SHIPPED</option>
                        <option value="COMPLETED" ${order.status == 'COMPLETED' ? 'selected' : ''}>COMPLETED</option>
                        <option value="CANCELLED" ${order.status == 'CANCELLED' ? 'selected' : ''}>CANCELLED</option>
                    </select>
                    <button type="submit" style="padding:2px 8px;">Lưu</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>