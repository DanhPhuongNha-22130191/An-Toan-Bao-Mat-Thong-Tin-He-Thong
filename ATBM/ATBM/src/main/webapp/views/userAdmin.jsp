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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/userAdmin.css">
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
                <a href="${pageContext.request.contextPath}/admin/users" class="nav-link active">
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
                <a href="${pageContext.request.contextPath}/admin/product" class="nav-link ">
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
                    <c:forEach var="user" items="${accounts}">
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
        document.getElementById("searchInput").addEventListener("input", searchUsers);
    });

    let currentPage = 1;
    const itemsPerPage = 5;
    let allRows = [];
    let filteredRows = [];

    function initializePagination() {
        allRows = Array.from(document.querySelectorAll("#userTableBody tr"));
        filteredRows = [...allRows];
        currentPage = 1;
        updatePagination();
        showPage(currentPage);
    }

    function showPage(page) {
        const startIndex = (page - 1) * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;

        allRows.forEach(row => row.style.display = 'none');
        filteredRows.forEach((row, index) => {
            row.style.display = (index >= startIndex && index < endIndex) ? '' : 'none';
        });

        updatePaginationInfo(page);
    }

    function updatePagination() {
        const totalPages = Math.ceil(filteredRows.length / itemsPerPage);
        const pageNumbers = document.getElementById('pageNumbers');
        pageNumbers.innerHTML = '';

        document.getElementById('prevPage').disabled = currentPage === 1;
        document.getElementById('nextPage').disabled = currentPage === totalPages || totalPages === 0;

        for (let i = 1; i <= totalPages; i++) {
            const btn = document.createElement('button');
            btn.className = 'btn btn-outline-secondary btn-sm' + (i === currentPage ? ' active' : '');
            btn.textContent = i;
            btn.addEventListener('click', () => goToPage(i));
            pageNumbers.appendChild(btn);
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

    function openEditUserModal(userId, username, email, role) {
        document.getElementById("editUserId").value = userId;
        document.getElementById("editUsername").value = username;
        document.getElementById("editEmail").value = email || '';
        document.getElementById("editRole").value = role || 'USER';
    }

    function showPublicKey(key) {
        const keyText = key && key !== 'null' ? key : 'Chưa có khóa công khai';
        document.getElementById("publicKeyText").value = keyText;
    }

    function copyPublicKey() {
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
        if (confirm("Bạn có chắc chắn muốn xóa người dùng này?")) {
            window.location.href = `/admin/users?action=delete&userId=${userId}`;
        }
    }

    function searchUsers() {
        const input = document.getElementById("searchInput").value.toLowerCase();

        filteredRows = allRows.filter(row => {
            return Array.from(row.querySelectorAll("td")).some(cell =>
                cell.textContent.toLowerCase().includes(input)
            );
        });

        currentPage = 1;
        updatePagination();
        showPage(currentPage);
    }
</script>
</body>
</html>