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
        body { background-color: #f8f9fa; font-family: 'Arial', sans-serif; }
        .container { margin-top: 30px; }
        .sidebar { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        .sidebar .list-group-item { cursor: pointer; transition: background 0.3s; }
        .sidebar .list-group-item:hover, .sidebar .list-group-item.active { background: #007bff; color: white; }
        .content { display: none; animation: fadeIn 0.3s ease-in-out; }
        .active-content { display: block !important; }
        @keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }
        .order-link { cursor: pointer; color: #007bff; }
        .order-link:hover { text-decoration: underline; }
    </style>
</head>
<body>
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

        <div class="col-md-9">
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

            <div id="order-history" class="content card p-4">
                <h3 class="text-primary">Lịch sử mua hàng</h3>
                <table class="table table-striped">
                    <thead class="table-dark">
                    <tr>
                        <th>Mã đơn hàng</th>
                        <th>Ngày mua</th>
                        <th>Tổng tiền</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>
                                <span class="order-link" data-bs-toggle="modal" data-bs-target="#orderDetailModal"
                                      onclick="showOrderDetails('${order.orderId}', '${order.orderDate}',
                                              '${order.totalAmount}', '${requestScope['order_' + order.orderId + '_isTampered']}',
                                              '${requestScope['order_' + order.orderId + '_hash']}')">
                                        ${order.orderId}
                                </span>
                            </td>
                            <td>${order.orderDate}</td>
                            <td>${order.totalAmount}đ</td>
                            <td>
                                <c:if test="${requestScope['order_' + order.orderId + '_isTampered']}">
                                    <span class="badge bg-danger">Đã bị thay đổi</span>
                                </c:if>
                                <c:if test="${!requestScope['order_' + order.orderId + '_isTampered']}">
                                    <span class="badge bg-success">An toàn</span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${requestScope['order_' + order.orderId + '_isTampered']}">
                                    <button class="btn btn-warning btn-sm" onclick="reportOrder('${order.orderId}')">
                                        Báo cáo
                                    </button>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

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
                <hr>
                <h4 class="text-primary">Quản lý Public Key</h4>
                <div class="mb-3">
                    <label class="form-label">Public Key hiện tại:</label>
                    <div class="input-group">
                        <c:if test="${not empty user.publicKeyActive}">
                            <input type="text" class="form-control" id="publicKeyInput" value="${user.publicKeyActive}" readonly>
                            <button class="btn btn-outline-secondary" type="button" onclick="copyPublicKey()">
                                <i class="bi bi-clipboard"></i> Sao chép
                            </button>
                            <form action="${pageContext.request.contextPath}/user/account" method="post" class="mt-2">
                                <input type="hidden" name="action" value="revokePublicKey">
                                <button type="submit" class="btn btn-danger">Thu hồi Public Key</button>
                            </form>
                        </c:if>
                        <c:if test="${empty user.publicKeyActive}">
                            <p>Chưa có Public Key.</p>
                            <form action="${pageContext.request.contextPath}/user/account" method="post">
                                <input type="hidden" name="action" value="generatePublicKey">
                                <button type="submit" class="btn btn-primary">Tạo Public Key mới</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Modal chi tiết đơn hàng -->
<div class="modal fade" id="orderDetailModal" tabindex="-1" aria-labelledby="orderDetailModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="orderDetailModalLabel">Chi tiết đơn hàng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p><strong>Mã đơn hàng:</strong> <span id="modalOrderId"></span></p>
                <p><strong>Ngày mua:</strong> <span id="modalOrderDate"></span></p>
                <p><strong>Tổng tiền:</strong> <span id="modalTotalAmount"></span>đ</p>
                <p><strong>Trạng thái:</strong> <span id="modalStatus"></span></p>
                <p><strong>Mã Hash:</strong> <span id="modalOrderHash"></span></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
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

    function copyPublicKey() {
        let publicKeyInput = document.getElementById("publicKeyInput");
        publicKeyInput.select();
        document.execCommand("copy");
        alert("Public Key đã được sao chép vào clipboard!");
    }

    function showOrderDetails(orderId, orderDate, totalAmount, isTampered, orderHash) {
        document.getElementById("modalOrderId").innerText = orderId;
        document.getElementById("modalOrderDate").innerText = orderDate;
        document.getElementById("modalTotalAmount").innerText = totalAmount;
        document.getElementById("modalStatus").innerHTML = isTampered === 'true' ?
            '<span class="badge bg-danger">Đã bị thay đổi</span>' :
            '<span class="badge bg-success">An toàn</span>';
        document.getElementById("modalOrderHash").innerText = orderHash;
    }

    function reportOrder(orderId) {
        if (confirm("Bạn muốn báo cáo đơn hàng " + orderId + " bị thay đổi?")) {
            alert("Đã gửi báo cáo cho quản trị viên về đơn hàng " + orderId);
        }
    }
</script>
</body>
</html>