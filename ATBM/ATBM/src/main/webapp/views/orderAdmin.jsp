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
            <div class="logo">
                <i class="fas fa-crown"></i>
            </div>
            <h2>Admin Panel</h2>
            <p>Quản lý hệ thống</p>
        </div>

        <nav class="nav-menu">
            <div class="nav-item">
                <a href="#" class="nav-link">
                    <i class="fas fa-users"></i>
                    Người dùng
                </a>
            </div>
            <div class="nav-item">
                <a href="#" class="nav-link active">
                    <i class="fas fa-shopping-cart"></i>
                    Đơn hàng
                    <span class="badge">12</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="#" class="nav-link">
                    <i class="fas fa-box"></i>
                    Sản phẩm
                </a>
            </div>
            <div class="nav-item">
                <a href="#" class="nav-link">
                    <i class="fas fa-ticket-alt"></i>
                    Voucher
                </a>
            </div>
        </nav>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="content-header animate-fade-up">
            <div class="content-header-top">
                <h1 class="page-title">Quản lý đơn hàng</h1>
                <div class="user-info">
                    <div class="notifications">
                        <i class="fas fa-bell"></i>
                        <span class="notification-badge">5</span>
                    </div>
                    <div class="user-avatar">AD</div>
                </div>
            </div>
        </div>

        <div class="stats-grid animate-fade-up">
            <div class="stat-card">
                <i class="fas fa-shopping-cart"></i>
                <h3 id="totalOrders">245</h3>
                <p>Tổng đơn hàng</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-clock"></i>
                <h3 id="pendingOrders">18</h3>
                <p>Chờ xử lý</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-check-circle"></i>
                <h3 id="completedOrders">198</h3>
                <p>Hoàn thành</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-dollar-sign"></i>
                <h3 id="totalRevenue">2.4B</h3>
                <p>Doanh thu (₫)</p>
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
                        <option value="PENDING">Chờ xử lý</option>
                        <option value="CONFIRMED">Đã xác nhận</option>
                        <option value="PAID">Đã thanh toán</option>
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
                        <th>Khách hàng</th>
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
                            <td>${order.orderDetail.fullName}</td>
                            <td><fmt:formatDate value="${order.orderDateAsDate}" pattern="dd-MM-yyyy HH:mm" /></td>
                            <td><span class="payment-method">${order.paymentMethod}</span></td>
                            <td><fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫" /></td>
                            <td>
                                <span class="status ${order.status}">
                                    <c:choose>
                                        <c:when test="${order.status == 'PENDING'}">Chờ xử lý</c:when>
                                        <c:when test="${order.status == 'CONFIRMED'}">Đã xác nhận</c:when>
                                        <c:when test="${order.status == 'PAID'}">Đã thanh toán</c:when>
                                        <c:when test="${order.status == 'SHIPPED'}">Đang giao</c:when>
                                        <c:when test="${order.status == 'DELIVERED'}">Đã giao</c:when>
                                        <c:when test="${order.status == 'CANCELLED'}">Đã hủy</c:when>
                                        <c:otherwise>Không rõ</c:otherwise>
                                    </c:choose>
                                </span>
                            </td>
                            <td>
                                <button class="btn btn-sm btn-info" onclick="viewOrder('${order.orderId}')">Xem</button>
                                <button class="btn btn-sm btn-success" onclick="updateOrderStatus('${order.orderId}', 'CONFIRMED')">Xác nhận</button>
                                <button class="btn btn-sm btn-danger" onclick="updateOrderStatus('${order.orderId}', 'CANCELLED')">Hủy</button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>

</html>
