<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                <h3 id="totalProducts">${totalProductStock}</h3>
                <p>Tổng sản phẩm</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-eye"></i>
                <h3 id="activeProducts">${totalProductStock}</h3>
                <p>Đang hiển thị</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-exclamation-triangle"></i>
                <h3 id="lowStock">${countLowStockProducts}</h3>
                <p>Sắp hết hàng</p>
            </div>
            <div class="stat-card">
                <i class="fas fa-tags"></i>
                <h3 id="categories">${totalBrandCount}</h3>
                <p>Thương hiệu</p>
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
                        <option value="">Tất cả thương hiệu</option>
                        <c:forEach var="b" items="${brands}">
                            <option value="Âm thanh">${b.name}</option>
                        </c:forEach>
                    </select>
                    <select id="filterStatus" class="filter-select">
                        <option value="">Tất cả trạng thái</option>
                        <c:forEach var="s" items="${states}">
                            <option value="active">${s.stateName}</option>
                        </c:forEach>

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
                        <th>Thương hiệu</th>
                        <th>Giá (Đ)</th>
                        <th>Số lượng</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody id="productTableBody">
                    <c:forEach var="p" items="${product}">
                        <tr>
                            <td>
                                <div class="product-image-wrapper">
                                    <img src="${pageContext.request.contextPath}/assests/img/product/${p.image}"
                                         alt="${p.name}"
                                         class="product-image"
                                         onerror="this.style.display='none'; this.parentElement.querySelector('.product-placeholder').style.display='flex';">

                                    <div class="product-placeholder" style="display: none;">
                                        <i class="fas fa-image"></i>
                                    </div>
                                </div>
                            </td>
                            <td>${p.productId}</td>
                            <td>${p.name}</td>
                            <td>${brandMap[p.brandId].name}</td>
                            <td>${p.price} đ</td>
                            <td>${p.stock}</td>
                            <td class="status active" style="margin-left: 15px; margin-top: 35px;">
                                    ${stateMap[p.stateId].stateName}
                            </td>
                            <td>
                                <button
                                        class="btn btn-sm btn-warning"
                                        data-image="${p.image}"
                                        data-product-id="${p.productId}"
                                        data-name="${p.name}"
                                        data-price="${p.price}"
                                        data-stock="${p.stock}"
                                        data-description="${p.description}"
                                        data-have-trending="${p.haveTrending}"
                                        data-size="${p.size}"
                                        data-water-resistance="${p.waterResistance}"
                                        data-brand-id="${p.brandId}"
                                        data-strap-id="${p.strapId}"
                                        data-state-id="${p.stateId}"
                                        data-is-deleted="${p.deleted}"
                                >
                                    Sửa
                                </button>

                                <button class="btn btn-sm btn-danger">Xóa</button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>
            </div>
        </div>
    </div>
</div>

<!-- Edit Product Modal -->
<!-- Edit Product Modal -->
<div id="editFormContainer" style="display: none;">
    <form action="/ATBM/admin/editProduct" method="POST" enctype="multipart/form-data" style="max-width: 600px; margin: auto;">
        <label for="productId">ID:</label>
        <input type="number" id="productId" name="productId" readonly>

        <label for="name">Tên sản phẩm:</label>
        <input type="text" id="name" name="name" required>

        <label for="price">Giá (₫):</label>
        <input type="number" id="price" name="price" step="0.01" required>

        <label for="description">Mô tả:</label>
        <textarea id="description" name="description" rows="3"></textarea>

        <label for="stock">Số lượng:</label>
        <input type="number" id="stock" name="stock" required>

        <!-- Ảnh hiện tại -->
        <label>Ảnh hiện tại:</label><br>
        <img id="currentImagePreview" src="" alt="Ảnh hiện tại" style="max-width: 100px; display: none; margin-bottom: 10px;"><br>

        <!-- Input chọn ảnh mới -->
        <label for="image">Chọn ảnh mới:</label>
        <input type="file" id="image" name="image" accept="image/*"><br>

        <!-- Preview ảnh mới -->
        <img id="newImagePreview" src="" alt="Ảnh mới chọn" style="max-width: 100px; margin-top: 10px; display: none;"><br><br>

        <label for="haveTrending">Sản phẩm hot:</label>
        <select id="haveTrending" name="haveTrending">
            <option value="true">Có</option>
            <option value="false">Không</option>
        </select>

        <label for="size">Kích thước mặt (mm):</label>
        <input type="number" step="0.1" id="size" name="size">

        <label for="waterResistance">Chống nước:</label>
        <select id="waterResistance" name="waterResistance">
            <option value="true">Có</option>
            <option value="false">Không</option>
        </select>

        <label for="brandId">ID Thương hiệu:</label>
        <input type="number" id="brandId" name="brandId" required>

        <label for="strapId">ID Dây đeo:</label>
        <input type="number" id="strapId" name="strapId" required>

        <label for="stateId">ID Trạng thái:</label>
        <input type="number" id="stateId" name="stateId" required>

        <label for="isDeleted">Đã xóa:</label>
        <select id="isDeleted" name="isDeleted">
            <option value="false">Chưa</option>
            <option value="true">Đã xóa</option>
        </select>

        <button type="submit">Lưu thay đổi</button>
    </form>
</div>



<script>
    const contextPath = '<%= request.getContextPath() %>';

    document.addEventListener('DOMContentLoaded', () => {
        const editFormContainer = document.getElementById('editFormContainer');

        // Tạo overlay nền mờ
        const modalOverlay = document.createElement('div');
        modalOverlay.className = 'modal-overlay';

        // Tạo nội dung modal
        const modalContent = document.createElement('div');
        modalContent.className = 'modal-content';

        // Nút đóng modal
        const closeButton = document.createElement('button');
        closeButton.className = 'close-modal';
        closeButton.innerHTML = '&times;';

        // Di chuyển form vào nội dung modal
        const form = editFormContainer.querySelector('form');
        modalContent.appendChild(closeButton);
        modalContent.appendChild(form);
        modalOverlay.appendChild(modalContent);
        document.body.appendChild(modalOverlay);

        // Hiển thị modal khi nhấn nút "Sửa"
        document.querySelectorAll('button.btn-warning').forEach(button => {
            button.addEventListener('click', () => {
                const dataset = button.dataset;

                // Gán dữ liệu vào form
                form.querySelector('#productId').value = dataset.productId;
                form.querySelector('#name').value = dataset.name;
                form.querySelector('#price').value = dataset.price;
                form.querySelector('#description').value = dataset.description;
                form.querySelector('#stock').value = dataset.stock;
                form.querySelector('#haveTrending').value = dataset.haveTrending;
                form.querySelector('#size').value = dataset.size;
                form.querySelector('#waterResistance').value = dataset.waterResistance;
                form.querySelector('#brandId').value = dataset.brandId;
                form.querySelector('#strapId').value = dataset.strapId;
                form.querySelector('#stateId').value = dataset.stateId;
                form.querySelector('#isDeleted').value = dataset.isDeleted;

                // Hiển thị ảnh hiện tại
                const preview = form.querySelector('#currentImagePreview');
                if (dataset.image) {
                    preview.src = contextPath + '/assets/img/product/' + dataset.image;
                    preview.style.display = 'block';
                } else {
                    preview.style.display = 'none';
                }

                // Reset preview ảnh mới
                const newPreview = document.getElementById('newImagePreview');
                newPreview.src = '';
                newPreview.style.display = 'none';

                // Hiển thị modal
                modalOverlay.classList.add('active');
            });
        });

        // Đóng modal khi nhấn nút đóng
        closeButton.addEventListener('click', () => {
            modalOverlay.classList.remove('active');
        });

        // Đóng modal khi nhấn ngoài nội dung
        modalOverlay.addEventListener('click', (e) => {
            if (e.target === modalOverlay) {
                modalOverlay.classList.remove('active');
            }
        });

        // Xem trước ảnh mới chọn
        form.querySelector('#image').addEventListener('change', function () {
            const file = this.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    const preview = document.getElementById('newImagePreview');
                    preview.src = e.target.result;
                    preview.style.display = 'block';
                };
                reader.readAsDataURL(file);
            }
        });
    });
</script>



<script src="${pageContext.request.contextPath}/assests/js/productAdminn.js"></script>
</body>
</html>