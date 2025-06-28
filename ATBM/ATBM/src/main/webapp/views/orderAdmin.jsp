<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Quản lý đơn hàng</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/orderAdmin.css">
</head>

<body>
<div class="dashboard">
    <!-- Sidebar -->
    <div class="sidebar animate-slide-left">
        <div class="sidebar-header">
            <div class="logo"><i class="fas fa-crown"></i></div>
            <h2>Admin Panel</h2>
            <p>Quản lý hệ thống</p>
        </div>
        <nav class="nav-menu">
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/users" class="nav-link"><i class="fas fa-users"></i>Người dùng</a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/order" class="nav-link active">
                    <i class="fas fa-shopping-cart"></i>Đơn hàng <span class="badge">5</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/product" class="nav-link"><i class="fas fa-box"></i>Sản phẩm</a>
            </div>
        </nav>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="content-header animate-fade-up">
            <div class="content-header-top">
                <h1 class="page-title">Quản lý đơn hàng</h1>
                <div class="user-info">
                    <div class="notifications"><i class="fas fa-bell"></i><span class="notification-badge">5</span></div>
                    <div class="user-avatar">AD</div>
                </div>
            </div>
        </div>

        <div class="table-container animate-fade-up">
            <div class="table-header">
                <div class="table-controls">
                    <div class="search-box">
                        <i class="fas fa-search"></i>
                        <input type="text" id="searchInput" placeholder="Tìm kiếm đơn hàng...">
                    </div>
                    <select id="filterStatus" class="filter-select">
                        <option value="">Tất cả trạng thái</option>
                        <option value="PROCESSING">Đang xử lý</option>
                        <option value="SHIPPED">Đang giao</option>
                        <option value="DELIVERED">Đã giao</option>
                        <option value="CANCELLED">Đã hủy</option>
                    </select>
                    <select id="filterPayment" class="filter-select">
                        <option value="">Phương thức thanh toán</option>
                        <option value="COD">COD</option>
                        <option value="BANK_TRANSFER">Chuyển khoản</option>
                        <option value="CREDIT_CARD">Thẻ tín dụng</option>
                        <option value="E_WALLET">Ví điện tử</option>
                    </select>
                    <input type="date" id="filterDate" class="filter-select">
                </div>
            </div>

            <div class="table-wrapper">
                <table id="orderTable">
                    <thead>
                    <tr>
                        <th>Mã đơn hàng</th>
                        <th>Mã bảo mật đơn hàng</th>
                        <th>Ngày đặt</th>
                        <th>Phương thức TT</th>
                        <th>Tổng tiền (₫)</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody id="orderTableBody">
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${order.orderSecurityId}</td>
                            <td>${order.orderAtFormatted}</td>
                            <td><span class="payment-method">${order.paymentMethod}</span></td>
                            <td><fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫"/></td>
                            <td>
                                <span class="status ${order.status}">
                                    <c:choose>
                                        <c:when test="${order.status == 'PROCESSING'}">Đang xử lý</c:when>
                                        <c:when test="${order.status == 'SHIPPED'}">Đang giao</c:when>
                                        <c:when test="${order.status == 'DELIVERED'}">Đã giao</c:when>
                                        <c:when test="${order.status == 'CANCELLED'}">Đã hủy</c:when>
                                        <c:otherwise>Không rõ</c:otherwise>
                                    </c:choose>
                                </span>
                            </td>
                            <td>
                                <button class="btn btn-sm btn-info"
                                        onclick="openStatusModal('${order.orderId}', '${order.status}')">
                                    Cập nhật trạng thái
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Modal cập nhật trạng thái -->
<div id="statusModal" class="modal" style="display:none;">
    <div class="modal-content">
        <span class="close" onclick="closeStatusModal()">&times;</span>
        <h3>Cập nhật trạng thái đơn hàng</h3>
        <form action="${pageContext.request.contextPath}/admin/order" method="post">
            <input type="hidden" name="orderId" id="modalOrderId"/>
            <label for="statusSelect">Trạng thái:</label>
            <select name="status" id="statusSelect" required>
                <option value="PROCESSING">Đang xử lý</option>
                <option value="SHIPPED">Đang giao</option>
                <option value="DELIVERED">Đã giao</option>
                <option value="CANCELLED">Đã hủy</option>
            </select>
            <br><br>
            <button type="submit" class="btn btn-success">Lưu thay đổi</button>
        </form>
    </div>
</div>

<style>
    .modal {
        position: fixed;
        top: 0; left: 0; right: 0; bottom: 0;
        background-color: rgba(0, 0, 0, 0.4);
        z-index: 9999;
        display: flex;
        align-items: center;
        justify-content: center;
    }
    .modal-content {
        background: #fff;
        padding: 20px;
        border-radius: 8px;
        width: 300px;
        position: relative;
    }
    .close {
        position: absolute;
        top: 10px;
        right: 15px;
        cursor: pointer;
        font-size: 20px;
    }
    .btn-success {
        background-color: #28a745;
        color: white;
        padding: 8px 16px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }
</style>

<script>
    function openStatusModal(orderId, currentStatus) {
        document.getElementById("modalOrderId").value = orderId;
        document.getElementById("statusSelect").value = currentStatus;
        document.getElementById("statusModal").style.display = "flex";
    }

    function closeStatusModal() {
        document.getElementById("statusModal").style.display = "none";
    }

    window.onclick = function (event) {
        const modal = document.getElementById("statusModal");
        if (event.target === modal) {
            closeStatusModal();
        }
    };
</script>
</body>
</html>
