<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Quản lý người dùng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/userAdmin.css">
</head>
<body>
<div class="dashboard">
    <div class="sidebar animate-slide-left">
        <!-- Sidebar content giống như cũ -->
        <div class="sidebar-header">
            <div class="logo"><i class="fas fa-crown"></i></div>
            <h2>Admin Panel</h2>
            <p>Quản lý hệ thống</p>
        </div>
        <nav class="nav-menu">
            <div class="nav-item"><a href="${pageContext.request.contextPath}/admin/users" class="nav-link active"><i class="fas fa-users"></i> Người dùng</a></div>
            <div class="nav-item"><a href="${pageContext.request.contextPath}/admin/order" class="nav-link"><i class="fas fa-shopping-cart"></i> Đơn hàng <span class="badge">5</span></a></div>
            <div class="nav-item"><a href="${pageContext.request.contextPath}/admin/product" class="nav-link"><i class="fas fa-box"></i> Sản phẩm</a></div>
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
                <button class="btn btn-primary" onclick="openAddUserModal()">
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
                                        <button class="btn btn-sm btn-info" onclick="showPublicKey('${fn:escapeXml(user.publicKeyActive)}')">
                                            <i class="fas fa-eye"></i>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">Chưa có</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <button class="btn btn-sm btn-warning"
                                        onclick="openEditUserModal('${user.accountId}', '${fn:escapeXml(user.username)}', '${fn:escapeXml(user.email != null ? user.email : "")}')">
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
        </div>
    </div>
</div>

<!-- Modal Thêm người dùng -->
<div class="modal fade" id="addUserModal" tabindex="-1" aria-labelledby="addUserModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <form id="addUserForm" action="${pageContext.request.contextPath}/admin/users" method="post">
                <div class="modal-header">
                    <h5 class="modal-title" id="addUserModalLabel">Thêm người dùng mới</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
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
                    <div class="mb-3">
                        <label for="role" class="form-label"><i class="fas fa-user-tag me-2"></i>Vai trò</label>
                        <select id="role" name="role" class="form-select">
                            <option value="">-- Chọn vai trò --</option>
                            <option value="ADMIN">Admin</option>
                            <option value="USER">User</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="submit" class="btn btn-primary">Thêm</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modal Sửa người dùng -->
<div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <form id="editUserForm" action="${pageContext.request.contextPath}/admin/users/edit" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" id="editUserId" name="userId">
                <div class="modal-header">
                    <h5 class="modal-title" id="editUserModalLabel">Sửa thông tin người dùng</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="editUsername" class="form-label"><i class="fas fa-user me-2"></i>Tên đăng nhập *</label>
                        <input type="text" id="editUsername" name="username" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="editEmail" class="form-label"><i class="fas fa-envelope me-2"></i>Email</label>
                        <input type="email" id="editEmail" name="email" class="form-control">
                    </div>
                    <div class="mb-3">
                        <label for="editRole" class="form-label"><i class="fas fa-user-tag me-2"></i>Vai trò</label>
                        <select id="editRole" name="role" class="form-select">
                            <option value="">-- Chọn vai trò --</option>
                            <option value="ADMIN">Admin</option>
                            <option value="USER">User</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <button type="submit" class="btn btn-warning">Cập nhật</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Modal Hiển thị Public Key -->
<div class="modal fade" id="publicKeyModal" tabindex="-1" aria-labelledby="publicKeyModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="publicKeyModalLabel">Khóa công khai</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <pre id="publicKeyContent" style="white-space: pre-wrap; word-wrap: break-word;"></pre>
            </div>
        </div>
    </div>
</div>

<!-- Form ẩn xóa user -->
<form id="deleteUserForm" action="${pageContext.request.contextPath}/admin/users/edit" method="post" style="display:none;">
    <input type="hidden" name="action" value="delete">
    <input type="hidden" id="deleteUserId" name="userId">
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Khởi tạo modal Bootstrap
    const editUserModal = new bootstrap.Modal(document.getElementById('editUserModal'));
    const addUserModal = new bootstrap.Modal(document.getElementById('addUserModal'));
    const publicKeyModal = new bootstrap.Modal(document.getElementById('publicKeyModal'));

    // Mở modal sửa user và điền dữ liệu
    function openEditUserModal(userId, username, email) {
        document.getElementById('editUserId').value = userId;
        document.getElementById('editUsername').value = username;
        document.getElementById('editEmail').value = email;
        // Set vai trò (nếu bạn muốn, cần truyền thêm role vào hàm)
        // Ví dụ: document.getElementById('editRole').value = role;
        editUserModal.show();
    }

    // Mở modal thêm user
    function openAddUserModal() {
        document.getElementById('addUserForm').reset();
        addUserModal.show();
    }

    // Hiển thị public key trong modal
    function showPublicKey(publicKey) {
        document.getElementById('publicKeyContent').textContent = publicKey;
        publicKeyModal.show();
    }

    // Xác nhận xóa user rồi submit form ẩn
    function confirmDelete(userId) {
        if (confirm("Bạn có chắc chắn muốn xóa người dùng này?")) {
            document.getElementById('deleteUserId').value = userId;
            document.getElementById('deleteUserForm').submit();
        }
    }

    // Tìm kiếm user trong bảng theo tên đăng nhập
    function searchUsers() {
        const input = document.getElementById('searchInput').value.toLowerCase();
        const rows = document.querySelectorAll('#userTableBody tr');

        rows.forEach(row => {
            const username = row.cells[1].textContent.toLowerCase();
            row.style.display = username.includes(input) ? '' : 'none';
        });
    }

</script>
</body>
</html>
