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

<!-- Thông báo -->
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
                <a class="list-group-item list-group-item-action tab-link" data-target="#shipping-addresses">
                    <i class="bi bi-house-door"></i> Địa chỉ giao hàng
                </a>
                <a class="list-group-item list-group-item-action tab-link" data-target="#wishlist">
                    <i class="bi bi-heart"></i> Danh sách yêu thích
                </a>
            </div>
        </div>

        <!-- Nội dung chính -->
        <div class="col-md-9">
            <!-- Thông tin cá nhân -->
            <div id="profile" class="content active-content card p-4">
                <h3 class="text-primary">Thông tin cá nhân</h3>
                <form action="${pageContext.request.contextPath}/user/account" method="post">
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
                        <th>Trạng thái</th>
                        <th>Thay đổi</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td><a href="${pageContext.request.contextPath}/order/details?orderId=${order.orderId}">${order.orderId}</a></td>
                            <td>${order.orderDetail.orderDate}</td>
                            <td>${order.orderDetail.totalAmount}đ</td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.orderDetail.confirmed}">
                                        <span class="badge bg-success">Đã xác nhận</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-danger">Chưa xác nhận</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${order.orderDetail.modified}">
                                    <span class="badge bg-warning">Đã thay đổi</span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${not order.orderDetail.confirmed}">
                                    <form action="${pageContext.request.contextPath}/order/cancel" method="post" style="display:inline;">
                                        <input type="hidden" name="orderId" value="${order.orderId}">
                                        <button type="submit" class="btn btn-danger btn-sm">Hủy đơn</button>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Cài đặt tài khoản -->
            <div id="account-settings" class="content card p-4">
                <h3 class="text-primary">Cài đặt tài khoản</h3>
                <form action="${pageContext.request.contextPath}/user/account" method="post">
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

            <!-- Địa chỉ giao hàng -->
            <div id="shipping-addresses" class="content card p-4">
                <h3 class="text-primary">Địa chỉ giao hàng</h3>
                <table class="table table-striped">
                    <thead class="table-dark">
                    <tr>
                        <th>Địa chỉ</th>
                        <th>Thành phố</th>
                        <th>Quốc gia</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="address" items="${addresses}">
                        <tr>
                            <td>${address.street}</td>
                            <td>${address.city}</td>
                            <td>${address.country}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/user/address?action=edit&addressId=${address.addressId}" class="btn btn-warning btn-sm">Sửa</a>
                                <form action="${pageContext.request.contextPath}/user/address" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="addressId" value="${address.addressId}">
                                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <a href="${pageContext.request.contextPath}/user/address?action=add" class="btn btn-primary">Thêm địa chỉ mới</a>
            </div>

            <!-- Danh sách yêu thích -->
            <div id="wishlist" class="content card p-4">
                <h3 class="text-primary">Danh sách yêu thích</h3>
                <table class="table table-striped">
                    <thead class="table-dark">
                    <tr>
                        <th>Tên sản phẩm</th>
                        <th>Giá</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${wishlist}">
                        <tr>
                            <td>${item.productName}</td>
                            <td>${item.price}đ</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/wishlist/remove" method="post" style="display:inline;">
                                    <input type="hidden" name="productId" value="${item.productId}">
                                    <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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
                tabs.forEach(t => t.classList.remove("active"));
                contents.forEach(c => c.classList.remove("active-content"));

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