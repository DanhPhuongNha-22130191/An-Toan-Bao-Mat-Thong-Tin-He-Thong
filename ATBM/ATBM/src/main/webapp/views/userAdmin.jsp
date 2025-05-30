<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Quản lý người dùng</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/userAdmin.css">
</head>

<body>
<div class="dashboard">
    <!-- Sidebar -->
    <div class="sidebar animate-slide-left">
        <div class="sidebar-header">
            <div class="logo">
                <i class="fas fa-crown"></i>
            </div>
            <h2>Admin Panel</h2>
            <p>Quản lý hệ thống</p>
        </div>

        <nav class="nav-menu">
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/index.jsp" class="nav-link">
                    <i class="fas fa-tachometer-alt"></i>
                    Dashboard
                    <span class="badge">2</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/views/userAdmin.jsp" class="nav-link active">
                    <i class="fas fa-users"></i>
                    Người dùng
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/orders.jsp" class="nav-link">
                    <i class="fas fa-shopping-cart"></i>
                    Đơn hàng
                    <span class="badge">5</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/views/productAmin.jsp" class="nav-link ">
                    <i class="fas fa-box"></i>
                    Sản phẩm
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/views/voucherAdmin.jsp" class="nav-link">
                    <i class="fas fa-ticket-alt"></i>
                    Voucher
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/statistics.jsp" class="nav-link">
                    <i class="fas fa-chart-bar"></i>
                    Thống kê
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/settings.jsp" class="nav-link">
                    <i class="fas fa-cog"></i>
                    Cài đặt
                </a>
            </div>
        </nav>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="content-header animate-fade-up">
            <div class="content-header-top">
                <h1 class="page-title">Quản lý người dùng</h1>
                <div class="user-info">
                    <div class="notifications tooltip" data-tooltip="Thông báo">
                        <i class="fas fa-bell"></i>
                        <span class="notification-badge">3</span>
                    </div>
                    <div class="user-avatar tooltip" data-tooltip="Trí Đức">
                        TD
                    </div>
                </div>
            </div>
        </div>

        <!-- Stats Cards -->
        <div class="stats-grid animate-fade-up">
            <div class="stat-card">
                <i class="fas fa-users"></i>
                <h3 id="totalUsers">14</h3>
                <p>Tổng người dùng</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-user-check"></i>
                <h3 id="activeUsers">12</h3>
                <p>Người dùng hoạt động</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-user-plus"></i>
                <h3 id="newUsers">3</h3>
                <p>Người dùng mới hôm nay</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-map-marker-alt"></i>
                <h3 id="locations">8</h3>
                <p>Tỉnh thành</p>
            </div>
        </div>

        <!-- Table Container -->
        <div class="table-container animate-fade-up">
            <div class="table-header">
                <div class="table-controls">
                    <div class="search-box">
                        <i class="fas fa-search"></i>
                        <input type="text" id="searchInput" placeholder="Tìm kiếm người dùng...">
                    </div>
                    <select id="filterType" class="filter-select">
                        <option value="name">Tên đăng nhập</option>
                        <option value="address">Địa chỉ</option>
                        <option value="phone">Số điện thoại</option>
                    </select>
                    <button class="btn btn-primary" onclick="openAddUserModal()">
                        <i class="fas fa-plus"></i>
                        Thêm người dùng
                    </button>
                </div>
            </div>

            <div class="table-wrapper">
                <table id="userTable">
                    <thead>
                    <tr>
                        <th>Avatar</th>
                        <th>ID</th>
                        <th>Tên đăng nhập</th>
                        <th>Ngày sinh</th>
                        <th>Địa chỉ</th>
                        <th>Số điện thoại</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody id="userTableBody">
                    <!-- Table rows will be populated by JavaScript -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Add User Modal -->
<div class="modal-overlay" id="addUserModal">
    <div class="modal">
        <div class="modal-header">
            <h2 class="modal-title">Thêm người dùng mới</h2>
            <button class="modal-close" onclick="closeAddUserModal()">
                <i class="fas fa-times"></i>
            </button>
        </div>
        <div class="modal-body">
            <form id="addUserForm">
                <div class="form-group">
                    <label class="form-label" for="username">
                        <i class="fas fa-user"></i> Tên đăng nhập
                    </label>
                    <input type="text" id="username" class="form-input" placeholder="Nhập tên đăng nhập" required>
                    <div class="form-error" id="usernameError">Tên đăng nhập không được để trống</div>
                </div>

                <div class="form-group">
                    <label class="form-label" for="password">
                        <i class="fas fa-lock"></i> Mật khẩu
                    </label>
                    <input type="password" id="password" class="form-input" placeholder="Nhập mật khẩu" required>
                    <div class="form-error" id="passwordError">Mật khẩu phải có ít nhất 6 ký tự</div>
                </div>

                <div class="form-group">
                    <label class="form-label" for="birthday">
                        <i class="fas fa-calendar"></i> Ngày sinh
                    </label>
                    <input type="date" id="birthday" class="form-input" required>
                    <div class="form-error" id="birthdayError">Vui lòng chọn ngày sinh</div>
                </div>

                <div class="form-group">
                    <label class="form-label" for="address">
                        <i class="fas fa-map-marker-alt"></i> Địa chỉ
                    </label>
                    <select id="address" class="form-input" required>
                        <option value="">Chọn tỉnh thành</option>
                        <option value="Hà Nội">Hà Nội</option>
                        <option value="Hồ Chí Minh">Hồ Chí Minh</option>
                        <option value="Đà Nẵng">Đà Nẵng</option>
                        <option value="Hải Phòng">Hải Phòng</option>
                        <option value="Huế">Huế</option>
                        <option value="Đồng Nai">Đồng Nai</option>
                        <option value="Bình Dương">Bình Dương</option>
                        <option value="Long An">Long An</option>
                        <option value="Đồng Tháp">Đồng Tháp</option>
                        <option value="Tiền Giang">Tiền Giang</option>
                        <option value="An Giang">An Giang</option>
                        <option value="Quảng Nam">Quảng Nam</option>
                    </select>
                    <div class="form-error" id="addressError">Vui lòng chọn địa chỉ</div>
                </div>

                <div class="form-group">
                    <label class="form-label" for="phone">
                        <i class="fas fa-phone"></i> Số điện thoại
                    </label>
                    <input type="tel" id="phone" class="form-input" placeholder="Nhập số điện thoại" required>
                    <div class="form-error" id="phoneError">Số điện thoại không hợp lệ</div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" onclick="closeAddUserModal()">
                <i class="fas fa-times"></i>
                Hủy
            </button>
            <button type="button" class="btn btn-primary" onclick="addUser()">
                <i class="fas fa-plus"></i>
                Thêm người dùng
            </button>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assests/js/userAdmin.js"></script>
</body>

</html>
