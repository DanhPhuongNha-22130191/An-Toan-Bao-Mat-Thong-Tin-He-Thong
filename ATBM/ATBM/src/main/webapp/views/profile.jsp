<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${empty sessionScope.user}">
    <c:redirect url="login.jsp" />
</c:if>

<c:set var="user" value="${sessionScope.user}" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Trang Cá Nhân</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', sans-serif;
        }
        .container {
            margin-top: 30px;
        }
        .sidebar {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .sidebar .list-group-item {
            cursor: pointer;
            transition: background 0.3s;
        }
        .sidebar .list-group-item:hover, .sidebar .list-group-item.active {
            background: #007bff;
            color: white;
        }
        .content {
            display: none;
            animation: fadeIn 0.3s ease-in-out;
        }
        .active-content {
            display: block !important;
        }
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="#">Trang Cá Nhân</a>
            <button class="btn btn-light" onclick="goBack()">
                <i class="bi bi-arrow-left"></i> Quay lại
            </button>
        </div>
    </nav>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>
<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

    <div class="container">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3 sidebar">
                <h4 class="text-center text-primary">Menu</h4>
                <div class="list-group">
                    <a class="list-group-item list-group-item-action active tab-link" data-target="#profile">
                        <i class="bi bi-person-circle"></i> Thông tin cá nhân
                    </a>
                    <a class="list-group-item list-group-item-action tab-link" data-target="#order-history">
                        <i class="bi bi-receipt"></i> Lịch sử mua hàng
                    </a>
                    <a class="list-group-item list-group-item-action tab-link" data-target="#account-settings">
                        <i class="bi bi-gear"></i> Cài đặt tài khoản
                    </a>
                </div>
            </div>

            <!-- Nội dung chính -->
            <div class="col-md-9">
                <!-- Thông tin cá nhân -->
                <div id="profile" class="content active-content card p-4">
                    <h3 class="text-primary">Thông tin cá nhân</h3>
                    <form action="${pageContext.request.contextPath}/AccountController" method="post">
                        <input type="hidden" name="action" value="updateProfile">
                        <div class="mb-3">
                            <label class="form-label">Họ và tên:</label>
                            <input type="text" name="username" class="form-control" value="${user.username}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email:</label>
                            <input type="email" name="email" class="form-control" value="${user.email}" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                    </form>
                </div>

                <!-- Lịch sử mua hàng -->
                <div id="order-history" class="content card p-4">
                    <h3 class="text-primary">Lịch sử mua hàng</h3>
                    <table class="table table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th>Mã đơn hàng</th>
                                <th>Ngày mua</th>
                                <th>Tổng tiền</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Dữ liệu đơn hàng sẽ hiển thị ở đây -->
                            <%--<c:forEach var="order" items="${orders.rows}">
                            <tr>
                                <td>${order.order_id}</td>
                                <td>${order.order_date}</td>
                                <td>${order.total_amount}đ</td>
                            </tr>
                        </c:forEach>--%>
                        </tbody>
                    </table>
                </div>

                <!-- Cài đặt tài khoản -->
                <div id="account-settings" class="content card p-4">
                    <h3 class="text-primary">Cài đặt tài khoản</h3>
                    <form action="${pageContext.request.contextPath}/AccountController" method="post">
                        <input type="hidden" name="action" value="changePassword">
                        <div class="mb-3">
                            <label class="form-label">Mật khẩu cũ:</label>
                            <input type="password" name="oldPassword" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mật khẩu mới:</label>
                            <input type="password" name="newPassword" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-success">Đổi mật khẩu</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            let tabs = document.querySelectorAll(".tab-link");
            let contents = document.querySelectorAll(".content");

            tabs.forEach(tab => {
                tab.addEventListener("click", function () {
                    // Xóa active cũ
                    tabs.forEach(t => t.classList.remove("active"));
                    contents.forEach(c => c.classList.remove("active-content"));

                    // Thêm active mới
                    this.classList.add("active");
                    document.querySelector(this.getAttribute("data-target")).classList.add("active-content");
                });
            });
        });

        function goBack() {
            window.history.back();
        }
    </script>

</body>
</html>
