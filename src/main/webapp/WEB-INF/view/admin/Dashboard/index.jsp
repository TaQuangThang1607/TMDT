<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="/css/stylesAdmin.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.3/dist/chart.umd.min.js"></script>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    <style>
        .chart-container {
            background: #fff;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }
        .filter-section {
            margin-bottom: 20px;
        }
    </style>
</head>
<body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
        <jsp:include page="../layout/sidebar.jsp" />
        <div id="layoutSidenav_content">
            <div class="container">
                <h1 class="text-center my-4">Thống kê Doanh thu</h1>

                <!-- Bộ lọc cho biểu đồ doanh thu theo thời gian -->
                <div class="filter-section">
                    <form id="revenueFilterForm" action="/admin/dashboard" method="get" class="row g-3 align-items-center">
                        <div class="col-md-3">
                            <label class="form-label">Ngày bắt đầu:</label>
                            <input type="date" name="startDate" value="${startDate}" class="form-control" required>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Ngày kết thúc:</label>
                            <input type="date" name="endDate" value="${endDate}" class="form-control" required>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Đơn vị thời gian:</label>
                            <select name="timeUnit" class="form-select">
                                <option value="day" ${timeUnit == 'day' ? 'selected' : ''}>Ngày</option>
                                <option value="month" ${timeUnit == 'month' ? 'selected' : ''}>Tháng</option>
                                <option value="year" ${timeUnit == 'year' ? 'selected' : ''}>Năm</option>
                            </select>
                        </div>
                        <div class="col-md-3 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary w-100">Lọc</button>
                        </div>
                    </form>
                </div>

                <!-- Biểu đồ cột doanh thu theo thời gian -->
                <div class="chart-container">
                    <h2 class="text-center">Doanh thu theo thời gian</h2>
                    <canvas id="revenueChart"></canvas>
                    <script>
                        const revenueCtx = document.getElementById('revenueChart').getContext('2d');
                        new Chart(revenueCtx, {
                            type: 'bar',
                            data: {
                                labels: [<c:forEach items="${revenueData}" var="entry">"${entry.key}",</c:forEach>],
                                datasets: [{
                                    label: 'Doanh thu (VND)',
                                    data: [<c:forEach items="${revenueData}" var="entry">${entry.value},</c:forEach>],
                                    backgroundColor: 'rgba(54, 162, 235, 0.6)',
                                    borderColor: 'rgba(54, 162, 235, 1)',
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                responsive: true,
                                scales: {
                                    y: {
                                        beginAtZero: true,
                                        title: { display: true, text: 'Doanh thu (VND)' }
                                    },
                                    x: {
                                        title: { display: true, text: '${timeUnit == "day" ? "Ngày" : timeUnit == "month" ? "Tháng" : "Năm"}' }
                                    }
                                },
                                plugins: {
                                    legend: { display: true, position: 'top' }
                                }
                            }
                        });
                    </script>
                </div>

                <!-- Bộ lọc cho biểu đồ doanh thu theo danh mục -->
                <div class="filter-section">
                    <form id="categoryFilterForm" action="/admin/dashboard" method="get" class="row g-3 align-items-center">
                        <div class="col-md-4" >
                            <label class="form-label">Tháng:</label>
                            <input type="month" name="month" value="${month}" class="form-control" required>
                        </div>
                        <div class="col-md-2 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary w-100">Xem theo tháng</button>
                        </div>
                    </form>
                </div>

                <!-- Biểu đồ tròn doanh thu theo danh mục -->
                <div class="chart-container" style="width: 60%;">
                    <h2 class="text-center">Doanh thu theo danh mục</h2>
                    <c:if test="${empty categoryRevenue}">
                        <div class="alert alert-info text-center">Không có dữ liệu doanh thu cho tháng đã chọn.</div>
                    </c:if>
                    <canvas id="categoryChart"></canvas>
                    <script>
                        const categoryCtx = document.getElementById('categoryChart').getContext('2d');
                        new Chart(categoryCtx, {
                            type: 'pie',
                            data: {
                                labels: [<c:forEach items="${categoryRevenue}" var="entry">"${entry.key}",</c:forEach>],
                                datasets: [{
                                    label: 'Doanh thu theo danh mục',
                                    data: [<c:forEach items="${categoryRevenue}" var="entry">${entry.value},</c:forEach>],
                                    backgroundColor: [
                                        'rgba(255, 99, 132, 0.6)',
                                        'rgba(54, 162, 235, 0.6)',
                                        'rgba(255, 206, 86, 0.6)',
                                        'rgba(75, 192, 192, 0.6)',
                                        'rgba(153, 102, 255, 0.6)',
                                        'rgba(255, 159, 64, 0.6)'
                                    ],
                                    borderColor: [
                                        'rgba(255, 99, 132, 1)',
                                        'rgba(54, 162, 235, 1)',
                                        'rgba(255, 206, 86, 1)',
                                        'rgba(75, 192, 192, 1)',
                                        'rgba(153, 102, 255, 1)',
                                        'rgba(255, 159, 64, 1)'
                                    ],
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                responsive: true,
                                plugins: {
                                    legend: { position: 'right' },
                                    tooltip: {
                                        callbacks: {
                                            label: function(context) {
                                                let label = context.label || '';
                                                let value = context.raw || 0;
                                                let total = context.dataset.data.reduce((a, b) => a + b, 0);
                                                let percentage = ((value / total) * 100).toFixed(2);
                                                return `${label}: ${value.toLocaleString()} VND (${percentage}%)`;
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    </script>
                </div>
            </div>
            <jsp:include page="../layout/footer.jsp" />
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="/js/scripts.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
    <script src="/js/datatables-simple-demo.js"></script>
</body>
</html>
