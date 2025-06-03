<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Quản lý sản phẩm</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/productAdmin.css">
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
                <a href="${pageContext.request.contextPath}/views/userAdmin.jsp" class="nav-link">
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
                <a href="${pageContext.request.contextPath}/views/productAdmin.jsp" class="nav-link active">
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
        <!-- Content Header -->
        <div class="content-header animate-fade-up">
            <div class="content-header-top">
                <h1 class="page-title">Quản lý sản phẩm</h1>
                <div class="user-info">
                    <div class="notifications tooltip" data-tooltip="Thông báo">
                        <i class="fas fa-bell"></i>
                        <span class="notification-badge">3</span>
                    </div>
                    <div class="user-avatar tooltip" data-tooltip="${sessionScope.userName}">
                        <c:out value="${sessionScope.userInitials}"/>
                    </div>
                </div>
            </div>
        </div>

        <!-- Stats Cards -->
        <div class="stats-grid animate-fade-up">
            <div class="stat-card">
                <i class="fas fa-box"></i>
                <h3 id="totalProducts">${totalProducts}</h3>
                <p>Tổng sản phẩm</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-eye"></i>
                <h3 id="activeProducts">${activeProducts}</h3>
                <p>Đang hiển thị</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-exclamation-triangle"></i>
                <h3 id="lowStock">${lowStock}</h3>
                <p>Sắp hết hàng</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-tags"></i>
                <h3 id="categories">${categories}</h3>
                <p>Danh mục</p>
            </div>
        </div>

        <!-- Table Container -->
        <div class="table-container animate-fade-up">
            <div class="table-header">
                <div class="table-controls">
                    <div class="search-box">
                        <i class="fas fa-search"></i>
                        <input type="text" id="searchInput" placeholder="Tìm kiếm sản phẩm...">
                    </div>
                    <select id="filterCategory" class="filter-select">
                        <option value="">Tất cả danh mục</option>
                        <option value="Điện thoại">Điện thoại</option>
                        <option value="Laptop">Laptop</option>
                        <option value="Tablet">Tablet</option>
                        <option value="Phụ kiện">Phụ kiện</option>
                        <option value="Âm thanh">Âm thanh</option>
                    </select>
                    <select id="filterStatus" class="filter-select">
                        <option value="">Tất cả trạng thái</option>
                        <option value="active">Đang bán</option>
                        <option value="inactive">Tạm dừng</option>
                        <option value="out-of-stock">Hết hàng</option>
                    </select>
                    <button class="btn btn-primary" onclick="openAddProductModal()">
                        <i class="fas fa-plus"></i>
                        Thêm sản phẩm
                    </button>
                </div>
            </div>

            <div class="table-wrapper">
                <table id="productTable">
                    <thead>
                    <tr>
                        <th>Hình ảnh</th>
                        <th>ID</th>
                        <th>Tên sản phẩm</th>
                        <th>Danh mục</th>
                        <th>Giá</th>
                        <th>Số lượng</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody id="productTableBody">
                    <!-- Table rows will be populated by JavaScript -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Add Product Modal -->
<div class="modal-overlay" id="addProductModal">
    <div class="modal">
        <div class="modal-header">
            <h2 class="modal-title">Thêm sản phẩm mới</h2>
            <button class="modal-close" onclick="closeAddProductModal()">
                <i class="fas fa-times"></i>
            </button>
        </div>
        <div class="modal-body">
            <form id="addProductForm" action="${pageContext.request.contextPath}/addProduct" method="post">
                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label" for="productName">
                            <i class="fas fa-tag"></i> Tên sản phẩm
                        </label>
                        <input type="text" id="productName" name="productName" class="form-input" placeholder="Nhập tên sản phẩm" required>
                        <div class="form-error" id="productNameError">Tên sản phẩm không được để trống</div>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="category">
                            <i class="fas fa-list"></i> Danh mục
                        </label>
                        <select id="category" name="category" class="form-input" required>
                            <option value="">Chọn danh mục</option>
                            <option value="Điện thoại">Điện thoại</option>
                            <option value="Laptop">Laptop</option>
                            <option value="Tablet">Tablet</option>
                            <option value="Phụ kiện">Phụ kiện</option>
                            <option value="Âm thanh">Âm thanh</option>
                        </select>
                        <div class="form-error" id="categoryError">Vui lòng chọn danh mục</div>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label class="form-label" for="price">
                            <i class="fas fa-dollar-sign"></i> Giá (VNĐ)
                        </label>
                        <input type="number" id="price" name="price" class="form-input" placeholder="Nhập giá sản phẩm" min="0" required>
                        <div class="form-error" id="priceError">Giá sản phẩm phải lớn hơn 0</div>
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="quantity">
                            <i class="fas fa-boxes"></i> Số lượng
                        </label>
                        <input type="number" id="quantity" name="quantity" class="form-input" placeholder="Nhập số lượng" min="0" required>
                        <div class="form-error" id="quantityError">Số lượng phải lớn hơn hoặc bằng 0</div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label" for="description">
                        <i class="fas fa-file-text"></i> Mô tả
                    </label>
                    <textarea id="description" name="description" class="form-input" placeholder="Nhập mô tả sản phẩm" rows="3"></textarea>
                </div>

                <div class="form-group">
                    <label class="form-label" for="image">
                        <i class="fas fa-image"></i> Hình ảnh
                    </label>
                    <input type="url" id="image" name="image" class="form-input" placeholder="Nhập URL hình ảnh">
                    <div class="image-preview" id="imagePreview" style="display: none;">
                        <img id="previewImg" src="" alt="Preview">
                    </div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-secondary" onclick="closeAddProductModal()">
                <i class="fas fa-times"></i>
                Hủy
            </button>
            <button type="button" class="btn btn-primary" onclick="addProduct()">
                <i class="fas fa-plus"></i>
                Thêm sản phẩm
            </button>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assests/js/productAdmin.js"></script>
</body>
</html>