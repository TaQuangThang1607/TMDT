<%@ page contentType="text/html; charset=UTF-8" %>
<div id="layoutSidenav_nav">
    <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu">
            <div class="nav">

                <!-- Thống kê -->
                <div class="sb-sidenav-menu-heading">Thống kê</div>
                <a class="nav-link" href="/admin/dashboard">
                    <div class="sb-nav-link-icon"><i class="fas fa-chart-line"></i></div>
                    Biểu đồ
                </a>

                <!-- Quản lý -->
                <div class="sb-sidenav-menu-heading">Quản lý</div>
                <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseManage" aria-expanded="false" aria-controls="collapseManage">
                    <div class="sb-nav-link-icon"><i class="fas fa-cogs"></i></div>
                    Quản lý
                    <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                </a>
                <div class="collapse" id="collapseManage" data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav">
                        <a class="nav-link" href="/admin/users">Người dùng</a>
                        <a class="nav-link" href="/admin/products">Sản phẩm</a>
                        <a class="nav-link" href="/admin/order">Đơn hàng</a>
                        <a class="nav-link" href="/admin/comments">Bình luận</a>
                        <a class="nav-link" href="/admin/design-requests">Thiết kế theo yêu cầu</a>
                    </nav>
                </div>

                <!-- Thiết kế trang -->
                <div class="sb-sidenav-menu-heading">Thiết kế giao diện</div>
                <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseDesign" aria-expanded="false" aria-controls="collapseDesign">
                    <div class="sb-nav-link-icon"><i class="fas fa-pencil-ruler"></i></div>
                    Thiết kế
                    <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                </a>
                <div class="collapse" id="collapseDesign" data-bs-parent="#sidenavAccordion">
                    <nav class="sb-sidenav-menu-nested nav">
                        <a class="nav-link" href="/admin/edit_home">Chỉnh sửa trang chủ</a>
                        
                    </nav>
                </div>

            </div>
        </div>
        <div class="sb-sidenav-footer">
            <div class="small">Thiết kế bởi</div>
            Ta Quang Thang
        </div>
    </nav>
</div>
