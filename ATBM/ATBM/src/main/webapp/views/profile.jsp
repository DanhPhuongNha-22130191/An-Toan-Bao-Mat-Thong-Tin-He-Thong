<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<c:if test="${empty sessionScope.user}">
    <c:redirect url="/views/login.jsp" />
</c:if>
<c:set var="user" value="${sessionScope.user}" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang Cá Nhân</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    <style>
        .order-item {
            cursor: pointer;
            transition: background-color 0.2s;
        }
        .order-item:hover {
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/product/category">Trang Chủ</a>
        <button class="btn btn-light" onclick="goBack()">
            <i class="bi bi-arrow-left"></i> Quay lại
        </button>
    </div>
</nav>

<c:if test="${not empty error}">
    <div class="alert alert-danger mt-3 mx-auto" style="max-width: 600px;">${error}</div>
</c:if>
<c:if test="${not empty message}">
    <div class="alert alert-success mt-3 mx-auto" style="max-width: 600px;">${message}</div>
</c:if>

<div class="container mt-4">
    <div class="row">
        <!-- Sidebar menu -->
        <div class="col-md-3">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title text-primary text-center">Menu</h5>
                    <div class="list-group">
                        <a href="${pageContext.request.contextPath}/user/info"
                           class="list-group-item list-group-item-action ${activeTab == 'profile' ? 'active' : ''}">
                            <i class="bi bi-person-circle"></i> Thông tin cá nhân
                        </a>
                        <a href="${pageContext.request.contextPath}/user/history-orders"
                           class="list-group-item list-group-item-action ${activeTab == 'order-history' ? 'active' : ''}">
                            <i class="bi bi-receipt"></i> Lịch sử mua hàng
                        </a>
                        <a href="${pageContext.request.contextPath}/user/update"
                           class="list-group-item list-group-item-action ${activeTab == 'account-settings' ? 'active' : ''}">
                            <i class="bi bi-gear"></i> Cài đặt tài khoản
                        </a>
                        <a href="${pageContext.request.contextPath}/user/logout"
                           class="list-group-item list-group-item-action">
                            <i class="bi bi-box-arrow-right"></i> Đăng xuất
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Nội dung chính -->
        <div class="col-md-9">
            <!-- Thông tin cá nhân -->
            <div id="profile" class="tab-content card shadow-sm p-4 ${activeTab == 'profile' ? '' : 'd-none'}">
                <h4 class="text-primary mb-4">Thông tin cá nhân</h4>
                <form action="${pageContext.request.contextPath}/user/update" method="post">
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
            <div id="order-history" class="tab-content card shadow-sm p-4 ${activeTab == 'order-history' ? '' : 'd-none'}">
                <h4 class="text-primary mb-4">Lịch sử mua hàng</h4>
                <c:choose>
                    <c:when test="${orders != null && not empty orders}">
                        <c:forEach var="order" items="${orders}" varStatus="status">
                            <div class="order-item mb-3 p-3 border rounded">
                                <h5>
                                    <a href="${pageContext.request.contextPath}/user/order/${order.orderId}">
                                        Đơn hàng #${order.orderId}
                                    </a>
                                    <c:if test="${tamperStatuses[status.index]}">
                            <span class="badge bg-danger ms-2">
                                <i class="fas fa-exclamation-triangle"></i> Đã bị thay đổi
                            </span>
                                    </c:if>
                                </h5>
                                <p><strong>Ngày đặt:</strong> ${order.orderDate}</p>
                                <p><strong>Tổng tiền:</strong> ${order.totalAmount}đ</p>
                                <p><strong>Trạng thái:</strong> ${order.status}</p>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">
                            Bạn chưa có đơn hàng nào.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Cài đặt tài khoản -->
            <div id="account-settings" class="tab-content card shadow-sm p-4 ${activeTab == 'account-settings' ? '' : 'd-none'}">
                <h4 class="text-primary mb-4">Cài đặt tài khoản</h4>

                <!-- Form đổi mật khẩu -->
                <form action="${pageContext.request.contextPath}/user/update" method="post" class="mb-4">
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

                <!-- Quản lý Public Key -->
                <h5 class="text-primary mb-3">Quản lý Public Key</h5>

                <!-- Hiển thị Public Key hiện tại -->
                <c:if test="${not empty user.publicKeyActive}">
                    <div class="mb-3">
                        <label class="form-label">Public Key hiện tại:</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="publicKeyInput" value="${user.publicKeyActive}" readonly>
                            <button class="btn btn-outline-secondary" type="button" onclick="copyPublicKey()">
                                <i class="bi bi-clipboard"></i> Sao chép
                            </button>
                        </div>
                        <div class="mt-2">
                            <form action="${pageContext.request.contextPath}/user/update" method="post" style="display: inline;">
                                <input type="hidden" name="action" value="revokePublicKey">
                                <button type="submit" class="btn btn-danger btn-sm">Thu hồi Public Key</button>
                            </form>
                        </div>
                    </div>
                </c:if>

                <c:if test="${empty user.publicKeyActive}">
                    <div class="mb-3">
                        <p class="text-muted">Chưa có Public Key.</p>
                    </div>
                </c:if>

                <!-- Form upload/nhập Public Key mới -->
                <h6 class="text-primary">Tải lên hoặc nhập Public Key mới</h6>
                <form action="${pageContext.request.contextPath}/user/update" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="uploadPublicKey">
                    <div class="mb-3">
                        <label class="form-label" for="publicKeyFile">Tải lên file Public Key:</label>
                        <input type="file" name="publicKeyFile" id="publicKeyFile" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label class="form-label" for="publicKeyText">Hoặc nhập Public Key:</label>
                        <textarea name="publicKeyText" id="publicKeyText" class="form-control" rows="3" placeholder="Nhập public key của bạn vào đây..."></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/bootstrap/bootstrap.bundle.min.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", () => {
        // Kiểm tra Bootstrap có tải đúng không
        if (typeof bootstrap === 'undefined') {
            console.error("Bootstrap JavaScript không được tải. Kiểm tra đường dẫn bootstrap.bundle.min.js.");
        }
    });

    function goBack() {
        window.history.back();
    }

    function copyPublicKey() {
        const publicKeyInput = document.getElementById("publicKeyInput");
        publicKeyInput.select();
        document.execCommand("copy");
        alert("Public Key đã được sao chép vào clipboard!");
    }

    function showOrderDetails(orderId, orderDate, totalAmount, paymentMethod, status, fullName, phone, email, address, orderNote) {
        console.log("Showing details for Order ID: " + orderId);
        document.getElementById("modalOrderId").innerText = orderId || "N/A";
        document.getElementById("modalOrderDate").innerText = orderDate || "N/A";
        document.getElementById("modalTotalAmount").innerText = totalAmount || "0";
        document.getElementById("modalPaymentMethod").innerText = paymentMethod || "N/A";
        document.getElementById("modalStatus").innerText = status || "N/A";
        document.getElementById("modalFullName").innerText = fullName || "N/A";
        document.getElementById("modalPhone").innerText = phone || "N/A";
        document.getElementById("modalEmail").innerText = email || "N/A";
        document.getElementById("modalAddress").innerText = address || "N/A";
        document.getElementById("modalOrderNote").innerText = orderNote || "Không có";

        // Khởi tạo modal
        const modalElement = document.getElementById("orderDetailModal");
        const modal = new bootstrap.Modal(modalElement);
        modal.show();
    }

    function closeModal() {
        const modalElement = document.getElementById("orderDetailModal");
        const modal = bootstrap.Modal.getInstance(modalElement);
        if (modal) {
            modal.hide();
        } else {
            console.warn("Modal instance not found, hiding manually");
            modalElement.classList.remove("show");
            modalElement.style.display = "none";
            document.body.classList.remove("modal-open");
            const backdrop = document.querySelector(".modal-backdrop");
            if (backdrop) backdrop.remove();
        }
    }

    function reportOrder(orderId) {
        if (confirm("Bạn muốn báo cáo đơn hàng " + orderId + " bị thay đổi?")) {
            alert("Đã gửi báo cáo cho quản trị viên về đơn hàng " + orderId);
        }
    }
</script>

</body>
</html>