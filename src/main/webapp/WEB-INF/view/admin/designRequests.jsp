<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    body {
        background: #f5f7fa;
        font-family: 'Segoe UI', Arial, sans-serif;
    }
    .design-table {
        width: 90%;
        margin: 32px auto;
        border-collapse: collapse;
        background: #fffbe7;
        border-radius: 12px;
        box-shadow: 0 4px 24px rgba(26,35,126,0.08), 0 1.5px 6px rgba(26,35,126,0.05);
        overflow: hidden;
    }
    .design-table th {
        background: #1a237e;
        color: #fff;
        padding: 12px 8px;
        font-size: 16px;
        letter-spacing: 1px;
    }
    .design-table td {
        padding: 10px 8px;
        text-align: center;
        font-size: 15px;
        color: #222;
        border-bottom: 1px solid #e0e0e0;
    }
    .design-table tr:nth-child(even) {
        background: #fffde7;
    }
    .design-table img {
        max-width: 80px;
        border-radius: 6px;
        border: 1px solid #c5cae9;
        background: #fff;
        padding: 2px;
    }
    .design-table tr:hover {
        background: #ffe082;
        transition: background 0.2s;
    }
    h2 {
        text-align: center;
        color: #1a237e;
        margin-top: 32px;
        margin-bottom: 16px;
        letter-spacing: 1px;
    }
</style>
<h2>Danh sách yêu cầu thiết kế Áo Dài</h2>
<table class="design-table">
    <tr>
        <th>ID</th>
        <th>Size</th>
        <th>Màu</th>
        <th>Ảnh thiết kế</th>
        <th>Mô tả</th>
        <th>Thời gian gửi</th>
        <th>Trạng thái</th>
    </tr>
    <c:forEach var="req" items="${requests}">
        <tr>
            <td>${req.id}</td>
            <td>${req.size}</td>
            <td>${req.color}</td>
<td>
    <a href="${req.imagePath}" download>
        <img src="${req.imagePath}" alt="Thiết kế" />
    </a>
    <br>
    <a href="${req.imagePath}" download style="font-size:13px; color:#3949ab;">Tải về</a>
</td>
            <td>${req.description}</td>
            <td>${req.createdAt}</td>
            <td>
    <form action="/admin/design/update-status" method="post" style="margin:0;">
        <input type="hidden" name="id" value="${req.id}" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <select name="status">
            <option value="PENDING" ${req.status == 'PENDING' ? 'selected' : ''}>PENDING</option>
            <option value="APPROVED" ${req.status == 'APPROVED' ? 'selected' : ''}>APPROVED</option>
            <option value="REJECTED" ${req.status == 'REJECTED' ? 'selected' : ''}>REJECTED</option>
            <option value="DONE" ${req.status == 'DONE' ? 'selected' : ''}>DONE</option>
        </select>
        <button type="submit" style="padding:2px 8px;">Lưu</button>
    </form>
</td>
        </tr>
    </c:forEach>
</table>