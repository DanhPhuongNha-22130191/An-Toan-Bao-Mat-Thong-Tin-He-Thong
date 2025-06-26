<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- CẤU HÌNH THÔNG TIN TRANG VÀ THƯ VIỆN JSTL -->

<!DOCTYPE html>
<html lang="vi">
<head>
    <!-- THIẾT LẬP THÔNG TIN CƠ BẢN CỦA TRANG WEB -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Quản lý sản phẩm</title>

    <!-- KẾT NỐI TỚI FONT VÀ ICON -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- KẾT NỐI CSS RIÊNG CHO TRANG QUẢN LÝ SẢN PHẨM -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/productAdmin.css">
</head>
<body>

<!-- KHUNG GIAO DIỆN TỔNG THỂ CỦA DASHBOARD -->
<div class="dashboard">

    <!-- THANH SIDEBAR BÊN TRÁI -->
    <div class="sidebar animate-slide-left">
        <!-- TIÊU ĐỀ SIDEBAR -->
        <div class="sidebar-header">
            <div class="logo">
                <i class="fas fa-crown"></i>
            </div>
            <h2>Admin Panel</h2>
            <p>Quản lý hệ thống</p>
        </div>

        <!-- MENU ĐIỀU HƯỚNG CHỨC NĂNG -->
        <nav class="nav-menu">
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/users" class="nav-link">
                    <i class="fas fa-users"></i>
                    Người dùng
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/order" class="nav-link">
                    <i class="fas fa-shopping-cart"></i>
                    Đơn hàng
                    <span class="badge">5</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/product" class="nav-link active">
                    <i class="fas fa-box"></i>
                    Sản phẩm
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/voucher" class="nav-link">
                    <i class="fas fa-ticket-alt"></i>
                    Voucher
                </a>
            </div>
        </nav>
    </div>

    <!-- KHU VỰC NỘI DUNG CHÍNH -->
    <div class="main-content">

        <!-- PHẦN HEADER CỦA TRANG -->
        <div class="content-header animate-fade-up">
            <div class="content-header-top">
                <h1 class="page-title">Quản lý sản phẩm</h1>
                <div class="user-info">
                    <!-- BIỂU TƯỢNG THÔNG BÁO -->
                    <div class="notifications tooltip" data-tooltip="Thông báo">
                        <i class="fas fa-bell"></i>
                        <span class="notification-badge">3</span>
                    </div>
                    <!-- THÔNG TIN NGƯỜI DÙNG ĐĂNG NHẬP -->
                    <div class="user-avatar tooltip" data-tooltip="${sessionScope.userName}">
                        <c:out value="${sessionScope.userInitials}"/>
                    </div>
                </div>
            </div>
        </div>

        <!-- BẢNG HIỂN THỊ DANH SÁCH SẢN PHẨM -->
        <div class="table-container animate-fade-up">
            <div class="table-header">
                <div class="table-controls">
                    <!-- NÚT THÊM SẢN PHẨM MỚI -->
                    <button class="btn btn-primary" onclick="openAddProductModal()">
                        <i class="fas fa-plus"></i>
                        Thêm sản phẩm
                    </button>
                </div>
            </div>

            <!-- BẢNG DỮ LIỆU SẢN PHẨM -->
            <div class="table-wrapper">
                <table id="productTable">
                    <thead>
                    <tr>
                        <th>Hình ảnh</th>
                        <th>ID</th>
                        <th>Tên sản phẩm</th>
                        <th>Thương hiệu</th>
                        <th>Giá (Đ)</th>
                        <th>Số lượng</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody id="productTableBody">
                    <!-- LẶP QUA DANH SÁCH SẢN PHẨM -->
                    <c:forEach var="p" items="${products}">
                        <tr>
                            <!-- CỘT HÌNH ẢNH SẢN PHẨM -->
                            <td>
                                <div class="product-image-wrapper">
                                    <img
                                            src="${pageContext.request.contextPath}/product-image/${p.productId}"
                                            alt="${p.name}"
                                            class="product-image"
                                            onerror="this.style.display='none'; this.parentElement.querySelector('.product-placeholder').style.display='flex';"
                                    />
                                    <div class="product-placeholder" style="display: none;">
                                        <i class="fas fa-image"></i>
                                    </div>
                                </div>
                            </td>
                            <!-- CÁC CỘT THÔNG TIN SẢN PHẨM -->
                            <td>${p.productId}</td>
                            <td>${p.name}</td>
                            <td>${brandMap[p.brandId].name}</td>
                            <td>${p.price} đ</td>
                            <td>${p.stock}</td>
                            <!-- NÚT SỬA VÀ XÓA -->
                            <td>
                                <button class="btn btn-sm btn-warning"
                                        data-product-id="${p.productId}"
                                        data-image="${pageContext.request.contextPath}/admin/productImage?productId=${p.productId}"
                                        data-name="${p.name}"
                                        data-price="${p.price}"
                                        data-stock="${p.stock}"
                                        data-description="${p.description}"
                                        data-brand-id="${p.brand.brandId}"
                                        >
                                    Sửa
                                </button>
                                <button class="btn btn-sm btn-danger"
                                        onclick="confirmDelete(${p.productId})">
                                    Xóa
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

<!-- FORM CHỈNH SỬA SẢN PHẨM -->
<div id="editFormContainer" style="display: none;">
    <form action="/ATBM/admin/product/${editProductId}" method="POST" enctype="multipart/form-data"
          style="max-width: 600px; margin: auto;">
        <!-- ẨN TRƯỜNG PRODUCT ID -->
        <input type="hidden" id="editProductId" name="productId"/>

        <label for="name">Tên sản phẩm:</label>
        <input type="text" id="name" name="name" required>

        <label for="price">Giá (₫):</label>
        <input type="number" id="price" name="price" step="0.01" required>

        <label for="description">Mô tả:</label>
        <textarea id="description" name="description" rows="3"></textarea>

        <label for="stock">Số lượng:</label>
        <input type="number" id="stock" name="stock" required>

        <!-- HIỂN THỊ ẢNH HIỆN TẠI -->
        <label>Ảnh hiện tại:</label><br>
        <img id="currentImagePreview" src="" alt="Ảnh hiện tại"
             style="max-width: 100px; display: none; margin-bottom: 10px;"><br>

        <!-- CHỌN ẢNH MỚI -->
        <label for="image">Chọn ảnh mới:</label>
        <input type="file" id="image" name="image" accept="image/*"><br>

        <!-- PREVIEW ẢNH MỚI -->
        <img id="newImagePreview" src="" alt="Ảnh mới chọn"
             style="max-width: 100px; margin-top: 10px; display: none;"><br><br>

        <!-- THƯƠNG HIỆU -->
        <label for="brandId">Thương hiệu:</label>
        <select id="brandId" name="brandId" required>
            <c:forEach var="brand" items="${brands}">
                <option value="${brand.brandId}">${brand.name}</option>
            </c:forEach>
        </select>

        <!-- NÚT LƯU THAY ĐỔI -->
        <button type="submit">Lưu thay đổi</button>
    </form>
</div>


<!-- FORM THÊM SẢN PHẨM MỚI -->
<div id="addFormContainer" style="display: none;">
    <form action="${pageContext.request.contextPath}/admin/product" method="POST" enctype="multipart/form-data" class="product-form">
        <h2>Thêm sản phẩm mới</h2>

        <label for="add-name">Tên sản phẩm:</label>
        <input type="text" id="add-name" name="name" required>

        <label for="add-price">Giá (₫):</label>
        <input type="number" id="add-price" name="price" step="0.01" required>

        <label for="add-description">Mô tả:</label>
        <textarea id="add-description" name="description" rows="3"></textarea>

        <label for="add-stock">Số lượng:</label>
        <input type="number" id="add-stock" name="stock" required>

        <label for="add-image">Chọn ảnh:</label>
        <input type="file" id="add-image" name="image" accept="image/*"><br>
        <img id="addImagePreview" src="" style="max-width: 100px; display: none; margin-top: 10px;">

        <label for="add-brandId">Thương hiệu:</label>
        <select id="add-brandId" name="brandId" required>
            <c:forEach var="brand" items="${brands}">
                <option value="${brand.brandId}">${brand.name}</option>
            </c:forEach>
        </select>

        <label for="add-strapId">Dây đeo:</label>
        <select id="add-strapId" name="strapId" required>
            <c:forEach var="strap" items="${straps}">
                <option value="${strap.strapId}">${strap.material}</option>
            </c:forEach>
        </select>

        <label for="add-size">Kích thước mặt:</label>
        <input type="number" id="add-size" name="size" step="0.1" required>

<%--        <label for="add-waterResistance">Chống nước:</label>--%>
<%--        <select id="add-waterResistance" name="waterResistance" required>--%>
<%--            <option value="true">Có</option>--%>
<%--            <option value="false">Không</option>--%>
<%--        </select>--%>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">Thêm</button>
            <button type="button" id="cancelAddForm" class="btn btn-secondary">Hủy</button>
        </div>
    </form>
</div>

<!-- KẾT NỐI JAVASCRIPT -->
<script src="${pageContext.request.contextPath}/assests/js/productManager.js"></script>

</body>
</html>