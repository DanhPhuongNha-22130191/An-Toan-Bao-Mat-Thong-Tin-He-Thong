<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Quản lý voucher</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/voucherAdmin.css">
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
                <a href="index.jsp" class="nav-link">
                    <i class="fas fa-tachometer-alt"></i>
                    Dashboard
                    <span class="badge">2</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/views/userAdmin.jsp" class="nav-link ">
                    <i class="fas fa-users"></i>
                    Người dùng
                </a>
            </div>
            <div class="nav-item">
                <a href="orders.jsp" class="nav-link">
                    <i class="fas fa-shopping-cart"></i>
                    Đơn hàng
                    <span class="badge">5</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/views/productAdmin.jsp" class="nav-link ">
                    <i class="fas fa-box"></i>
                    Sản phẩm
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/views/voucherAdmin.jsp" class="nav-link active">
                    <i class="fas fa-ticket-alt"></i>
                    Voucher
                </a>
            </div>
            <div class="nav-item">
                <a href="statistics.jsp" class="nav-link">
                    <i class="fas fa-chart-bar"></i>
                    Thống kê
                </a>
            </div>
            <div class="nav-item">
                <a href="settings.jsp" class="nav-link">
                    <i class="fas fa-cog"></i>
                    Cài đặt
                </a>
            </div>
        </nav>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <!-- Content Header -->
        <div class="content-header animate-fade-up">
            <div class="content-header-top">
                <h1 class="page-title">Quản lý voucher</h1>
                <div class="user-info">
                    <div class="notifications tooltip" data-tooltip="Thông báo">
                        <i class="fas fa-bell"></i>
                        <span class="notification-badge">3</span>
                    </div>
                    <div class="user-avatar tooltip" data-tooltip="${sessionScope.userName}">
                        ${sessionScope.userInitials}
                    </div>
                </div>
            </div>
        </div>

        <!-- Stats Cards -->
        <div class="stats-grid animate-fade-up">
            <div class="stat-card">
                <i class="fas fa-ticket-alt"></i>
                <h3 id="totalVouchers">${totalVouchers}</h3>
                <p>Tổng voucher</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-check-circle"></i>
                <h3 id="activeVouchers">${activeVouchers}</h3>
                <p>Voucher đang hoạt động</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-clock"></i>
                <h3 id="expiredVouchers">${expiredVouchers}</h3>
                <p>Voucher hết hạn</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-chart-line"></i>
                <h3 id="usedVouchers">${usedVouchers}</h3>
                <p>Lượt sử dụng</p>
            </div>
        </div>

        <!-- Table Container -->
        <div class="table-container animate-fade-up">
            <div class="table-header">
                <div class="table-controls">
                    <div class="search-box">
                        <i class="fas fa-search"></i>
                        <input type="text" id="searchInput" placeholder="Tìm kiếm voucher...">
                    </div>
                    <select id="filterStatus" class="filter-select">
                        <option value="">Tất cả trạng thái</option>
                        <option value="active">Đang hoạt động</option>
                        <option value="expired">Hết hạn</option>
                        <option value="inactive">Không hoạt động</option>
                    </select>
                    <select id="filterType" class="filter-select">
                        <option value="">Tất cả loại</option>
                        <option value="percentage">Giảm theo %</option>
                        <option value="fixed">Giảm cố định</option>
                    </select>
                    <button class="btn btn-primary" onclick="openAddVoucherModal()">
                        <i class="fas fa-plus"></i>
                        Thêm voucher
                    </button>
                </div>
            </div>

            <div class="table-wrapper">
                <table id="voucherTable">
                    <thead>
                    <tr>
                        <th>Mã voucher</th>
                        <th>Tên</th>
                        <th>Loại</th>
                        <th>Giá trị</th>
                        <th>Đơn tối thiểu</th>
                        <th>Số lượng</th>
                        <th>Đã sử dụng</th>
                        <th>Ngày hết hạn</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody id="voucherTableBody">
                    <c:forEach var="voucher" items="${voucherList}">
                        <tr>
                            <td>${voucher.code}</td>
                            <td>${voucher.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${voucher.discountType == 'percentage'}">Giảm theo %</c:when>
                                    <c:otherwise>Giảm cố định</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${voucher.discountType == 'percentage'}">${voucher.discountValue}%</c:when>
                                    <c:otherwise><fmt:formatNumber value="${voucher.discountValue}" type="currency" currencySymbol="VNĐ"/></c:otherwise>
                                </c:choose>
                            </td>
                            <td><fmt:formatNumber value="${voucher.minOrderValue}" type="currency" currencySymbol="VNĐ"/></td>
                            <td>${voucher.quantity}</td>
                            <td>${voucher.usedCount}</td>
                            <td><fmt:formatDate value="${voucher.expiryDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${voucher.status == 'active'}">
                                        <span class="status status-active">Đang hoạt động</span>
                                    </c:when>
                                    <c:when test="${voucher.status == 'expired'}">
                                        <span class="status status-expired">Hết hạn</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="status status-inactive">Không hoạt động</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <button class="btn btn-small btn-edit" onclick="editVoucher('${voucher.code}')">
                                    <i class="fas fa-edit"></i>
                                </button>
                                <button class="btn btn-small btn-delete" onclick="deleteVoucher('${voucher.code}')">
                                    <i class="fas fa-trash"></i>
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

<!-- Add/Edit Voucher Modal -->
<div class="modal-overlay" id="voucherModal">
    <div class="modal">
        <div class="modal-header">
            <h2 class="modal-title" id="modalTitle">Thêm voucher mới</h2>
            <button class="modal-close" onclick="closeVoucherModal()">
                <i class="fas fa-times"></i>
            </button>
        </div>
        <div class="modal-body">
            <form id="voucherForm" action="voucherAdmin" method="post">
                <input type="hidden" id="action" name="action" value="add">
                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label" for="voucherCode">
                            <i class="fas fa-ticket-alt"></i> Mã voucher
                        </label>
                        <input type="text" id="voucherCode" name="voucherCode" class="form-input" placeholder="Nhập mã voucher" required>
                        <div class="form-error" id="voucherCodeError">Mã voucher không được để trống</div>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="voucherName">
                            <i class="fas fa-tag"></i> Tên voucher
                        </label>
                        <input type="text" id="voucherName" name="voucherName" class="form-input" placeholder="Nhập tên voucher" required>
                        <div class="form-error" id="voucherNameError">Tên voucher không được để trống</div>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label" for="discountType">
                            <i class="fas fa-percent"></i> Loại giảm giá
                        </label>
                        <select id="discountType" name="discountType" class="form-input" required>
                            <option value="">Chọn loại giảm giá</option>
                            <option value="percentage">Giảm theo phần trăm (%)</option>
                            <option value="fixed">Giảm cố định (VNĐ)</option>
                        </select>
                        <div class="form-error" id="discountTypeError">Vui lòng chọn loại giảm giá</div>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="discountValue">
                            <i class="fas fa-money-bill"></i> Giá trị giảm
                        </label>
                        <input type="number" id="discountValue" name="discountValue" class="form-input" placeholder="Nhập giá trị" required min="0">
                        <div class="form-error" id="discountValueError">Giá trị giảm phải lớn hơn 0</div>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label" for="minOrderValue">
                            <i class="fas fa-shopping-cart"></i> Đơn hàng tối thiểu
                        </label>
                        <input type="number" id="minOrderValue" name="minOrderValue" class="form-input" placeholder="Nhập giá trị tối thiểu" required min="0">
                        <div class="form-error" id="minOrderValueError">Giá trị đơn hàng tối thiểu không hợp lệ</div>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="maxDiscount">
                            <i class="fas fa-hand-holding-usd"></i> Giảm tối đa (VNĐ)
                        </label>
                        <input type="number" id="maxDiscount" name="maxDiscount" class="form-input" placeholder="Không giới hạn" min="0">
                        <div class="form-error" id="maxDiscountError">Giá trị giảm tối đa không hợp lệ</div>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label" for="quantity">
                            <i class="fas fa-sort-numeric-up"></i> Số lượng
                        </label>
                        <input type="number" id="quantity" name="quantity" class="form-input" placeholder="Nhập số lượng" required min="1">
                        <div class="form-error" id="quantityError">Số lượng phải lớn hơn 0</div>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="expiryDate">
                            <i class="fas fa-calendar-alt"></i> Ngày hết hạn
                        </label>
                        <input type="datetime-local" id="expiryDate" name="expiryDate" class="form-input" required>
                        <div class="form-error" id="expiryDateError">Vui lòng chọn ngày hết hạn</div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label" for="description">
                        <i class="fas fa-align-left"></i> Mô tả
                    </label>
                    <textarea id="description" name="description" class="form-input" rows="3" placeholder="Nhập mô tả voucher (tùy chọn)"></textarea>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" onclick="closeVoucherModal()">
                <i class="fas fa-times"></i>
                Hủy
            </button>
            <button type="button" class="btn btn-primary" onclick="document.getElementById('voucherForm').submit()">
                <i class="fas fa-save"></i>
                Lưu voucher
            </button>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assests/js/voucherAdmin.js"></script>
</body>
</html>