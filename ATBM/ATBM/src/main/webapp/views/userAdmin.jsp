<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Quản lý người dùng</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f4f6f9;
        }
        .dashboard {
            display: flex;
            min-height: 100vh;
        }
        .sidebar {
            width: 250px;
            background-color: #2c3e50;
            color: white;
            padding: 20px 0;
            position: fixed;
            height: 100%;
        }
        .sidebar-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .logo i {
            font-size: 2rem;
            margin-bottom: 10px;
        }
        .sidebar-header h2 {
            font-size: 1.5rem;
            margin-bottom: 5px;
        }
        .sidebar-header p {
            font-size: 0.9rem;
            opacity: 0.7;
        }
        .nav-menu {
            padding: 0 10px;
        }
        .nav-item {
            margin-bottom: 10px;
        }
        .nav-link {
            display: flex;
            align-items: center;
            padding: 10px 15px;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.2s;
        }
        .nav-link:hover, .nav-link.active {
            background-color: #34495e;
        }
        .nav-link i {
            margin-right: 10px;
            width: 20px;
            text-align: center;
        }
        .badge {
            background-color: #e74c3c;
            color: white;
            padding: 2px 8px;
            border-radius: 10px;
            font-size: 0.8rem;
            margin-left: auto;
        }
        .main-content {
            flex: 1;
            padding: 20px;
            margin-left: 250px;
            background-color: #f4f6f9;
        }
        .table-container {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            padding: 20px;
        }
        .table-fixed {
            table-layout: fixed;
            width: 100%;
            min-width: 800px;
        }
        .table-fixed th, .table-fixed td {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            padding: 12px;
        }
        .table-fixed th:nth-child(1), .table-fixed td:nth-child(1) { width: 10%; }
        .table-fixed th:nth-child(2), .table-fixed td:nth-child(2) { width: 20%; }
        .table-fixed th:nth-child(3), .table-fixed td:nth-child(3) { width: 25%; }
        .table-fixed th:nth-child(4), .table-fixed td:nth-child(4) { width: 15%; }
        .table-fixed th:nth-child(5), .table-fixed td:nth-child(5) { width: 30%; }
        .pagination-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
            padding: 15px 0;
            border-top: 1px solid #e9ecef;
        }
        .animate-slide-left {
            animation: slide-left 0.3s ease;
        }
        .animate-fade-up {
            animation: fade-up 0.3s ease;
        }
        @keyframes slide-left {
            from { transform: translateX(-100%); }
            to { transform: translateX(0); }
        }
        @keyframes fade-up {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
        .notification-badge {
            position: absolute;
            top: -5px;
            right: -5px;
            background-color: #e74c3c;
            color: white;
            border-radius: 50%;
            padding: 2px 6px;
            font-size: 0.7rem;
        }
        .user-avatar {
            width: 40px;
            height: 40px;
            background-color: #3498db;
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            font-weight: 600;
        }
    </style>
</head>
<body>
<div class="dashboard">
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
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link">
                    <i class="fas fa-tachometer-alt"></i> Dashboard
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/users" class="nav-link active">
                    <i class="fas fa-users"></i> Người dùng
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/orders" class="nav-link">
                    <i class="fas fa-shopping-cart"></i> Đơn hàng
                    <span class="badge">5</span>
                </a>
            </div>
            <div class="nav-item">
                <a href="${pageContext.request.contextPath}/admin/products" class="nav-link">
                    <i class="fas fa-box"></i> Sản phẩm
                </a>
            </div>
        </nav>
    </div>

    <div class="main-content">
        <div class="content-header animate-fade-up">
            <div class="d-flex justify-content-between align-items-center">
                <h1 class="h3 mb-0">Quản lý người dùng</h1>
                <div class="d-flex align-items-center gap-3">
                    <div class="position-relative">
                        <i class="fas fa-bell fs-5"></i>
                        <span class="notification-badge">3</span>
                    </div>
                    <div class="user-avatar">TD</div>
                </div>
            </div>
        </div>

        <c:if test="${not empty message}">
            <div class="alert alert-success d-flex align-items-center animate-fade-up">
                <i class="fas fa-check-circle me-2"></i> ${message}
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-danger d-flex align-items-center animate-fade-up">
                <i class="fas fa-exclamation-circle me-2"></i> ${error}
            </div>
        </c:if>

        <div class="table-container animate-fade-up">
            <div class="d-flex justify-content-between mb-3">
                <div class="input-group w-25">
                    <span class="input-group-text"><i class="fas fa-search"></i></span>
                    <input type="text" id="searchInput" class="form-control" placeholder="Tìm kiếm người dùng..." oninput="searchUsers()">
                </div>
                <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addUserModal">
                    <i class="fas fa-plus"></i> Thêm người dùng
                </button>
            </div>

            <div class="table-responsive">
                <table id="userTable" class="table table-fixed table-hover">
                    <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Tên đăng nhập</th>
                        <th>Email</th>
                        <th>Khóa công khai</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody id="userTableBody">
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.accountId}</td>
                            <td><c:out value="${user.username}"/></td>
                            <td><c:out value="${user.email != null ? user.email : 'Chưa cập nhật'}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${user.publicKeyActive != null}">
                                        <button class="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#publicKeyModal" onclick="showPublicKey('<c:out value="${user.publicKeyActive}"/>')">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">Chưa có</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#editUserModal" onclick="openEditUserModal('${user.accountId}', '<c:out value="${user.username}"/>', '<c:out value="${user.email != null ? user.email : ''}"/>')">
                                    <i class="fas fa-edit"></i> Sửa
                                </button>
                                <button class="btn btn-sm btn-danger" onclick="confirmDelete('${user.accountId}')">
                                    <i class="fas fa-trash"></i> Xóa
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="pagination-container">
                <div class="pagination-info">
                    Hiển thị <span id="currentPageInfo">1-5</span> của <span id="totalRecords">0</span> người dùng
                </div>
                <div class="pagination">
                    <button id="prevPage" class="btn btn-outline-secondary btn-sm" onclick="previousPage()">
                        <i class="fas fa-chevron-left"></i>
                    </button>
                    <div id="pageNumbers" class="d-flex gap-1"></div>
                    <button id="nextPage" class="btn btn-outline-secondary btn-sm" onclick="nextPage()">
                        <i class="fas fa-chevron-right"></i>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Add User Modal -->
<div class="modal fade" id="addUserModal" tabindex="-1" aria-labelledby="addUserModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addUserModalLabel">Thêm người dùng mới</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="addUserForm" action="${pageContext.request.contextPath}/admin/users" method="post">
                <div class="modal-body">
                    <input type="hidden" name="action" value="add">
                    <div class="mb-3">
                        <label for="username" class="form-label"><i class="fas fa-user me-2"></i>Tên đăng nhập *</label>
                        <input type="text" id="username" name="username" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label"><i class="fas fa-lock me-2"></i>Mật khẩu *</label>
                        <input type="password" id="password" name="password" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label"><i class="fas fa-envelope me-2"></i>Email</label>
                        <input type="email" id="email" name="email" class="form-control">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary">Thêm</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Edit User Modal -->
<div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editUserModalLabel">Sửa thông tin người dùng</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="editUserForm" action="${pageContext.request.contextPath}/admin/users" method="post">
                <div class="modal-body">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" id="editUserId" name="userId">
                    <div class="mb-3">
                        <label for="editUsername" class="form-label"><i class="fas fa-user me-2"></i>Tên đăng nhập *</label>
                        <input type="text" id="editUsername" name="username" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="editEmail" class="form-label"><i class="fas fa-envelope me-2"></i>Email</label>
                        <input type="email" id="editEmail" name="email" class="form-control">
                    </div>
                    <div class="alert alert-info d-flex align-items-center">
                        <i class="fas fa-info-circle me-2"></i>
                        Lưu ý: Mật khẩu không thể thay đổi từ trang quản trị
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Public Key Modal -->
<div class="modal fade" id="publicKeyModal" tabindex="-1" aria-labelledby="publicKeyModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="publicKeyModalLabel">Khóa công khai</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="position-relative">
                    <textarea id="publicKeyText" class="form-control" readonly style="height: 150px; font-family: 'Courier New', monospace;"></textarea>
                    <button class="btn btn-sm btn-secondary position-absolute top-0 end-0 m-2" onclick="copyPublicKey()">
                        <i class="fas fa-copy"></i> Sao chép
                    </button>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS and Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        initializePagination();
    });

    let currentPage = 1;
    const itemsPerPage = 5;
    let totalItems = 0;
    let allRows = [];
    let filteredRows = [];

    function initializePagination() {
        allRows = Array.from(document.querySelectorAll("#userTableBody tr"));
        filteredRows = [...allRows];
        totalItems = filteredRows.length;
        currentPage = 1;
        updatePagination();
        showPage(currentPage);
        console.log("Total users:", totalItems);
    }

    function showPage(page) {
        const startIndex = (page - 1) * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;

        allRows.forEach(row => row.style.display = 'none');
        for (let i = startIndex; i < endIndex && i < filteredRows.length; i++) {
            filteredRows[i].style.display = '';
        }

        updatePaginationInfo(page);
    }

    function updatePagination() {
        const totalPages = Math.ceil(filteredRows.length / itemsPerPage);
        const pageNumbers = document.getElementById('pageNumbers');
        pageNumbers.innerHTML = '';

        document.getElementById('prevPage').disabled = currentPage === 1;
        document.getElementById('nextPage').disabled = currentPage === totalPages || totalPages === 0;

        for (let i = 1; i <= totalPages; i++) {
            const pageBtn = document.createElement('button');
            pageBtn.className = 'btn btn-outline-secondary btn-sm' + (i === currentPage ? ' active' : '');
            pageBtn.textContent = i;
            pageBtn.onclick = () => goToPage(i);
            pageNumbers.appendChild(pageBtn);
        }
    }

    function updatePaginationInfo(page) {
        const startItem = (page - 1) * itemsPerPage + 1;
        const endItem = Math.min(page * itemsPerPage, filteredRows.length);
        const totalRecords = filteredRows.length;

        document.getElementById('currentPageInfo').textContent = `${startItem}-${endItem}`;
        document.getElementById('totalRecords').textContent = totalRecords;
    }

    function goToPage(page) {
        currentPage = page;
        showPage(currentPage);
        updatePagination();
    }

    function previousPage() {
        if (currentPage > 1) {
            goToPage(currentPage - 1);
        }
    }

    function nextPage() {
        const totalPages = Math.ceil(filteredRows.length / itemsPerPage);
        if (currentPage < totalPages) {
            goToPage(currentPage + 1);
        }
    }

    function openEditUserModal(userId, username, email) {
        console.log("Opening Edit User Modal with:", userId, username, email);
        document.getElementById("editUserId").value = userId;
        document.getElementById("editUsername").value = username;
        document.getElementById("editEmail").value = email || '';
    }

    function showPublicKey(key) {
        console.log("Showing Public Key:", key);
        const keyText = key && key !== 'null' ? key : 'Chưa có khóa công khai';
        document.getElementById("publicKeyText").value = keyText;
    }

    function copyPublicKey() {
        console.log("Copying Public Key");
        const textarea = document.getElementById("publicKeyText");
        textarea.select();
        try {
            document.execCommand('copy');
            alert('Đã sao chép khóa công khai!');
        } catch (err) {
            console.error("Copy failed:", err);
            alert('Không thể sao chép khóa công khai.');
        }
    }

    function confirmDelete(userId) {
        console.log("Confirming Delete for userId:", userId);
        if (confirm("Bạn có chắc chắn muốn xóa người dùng này?")) {
            window.location.href = "${pageContext.request.contextPath}/admin/users?action=delete&userId=" + userId;
        }
    }

    function searchUsers() {
        const input = document.getElementById("searchInput").value.toLowerCase();
        console.log("Searching users with:", input);
        filteredRows = allRows.filter(row => {
            const cells = row.querySelectorAll("td");
            return Array.from(cells).some(cell => cell.textContent.toLowerCase().includes(input));
        });
        totalItems = filteredRows.length;
        currentPage = 1;
        updatePagination();
        showPage(currentPage);
    }
</script>
</body>
</html>