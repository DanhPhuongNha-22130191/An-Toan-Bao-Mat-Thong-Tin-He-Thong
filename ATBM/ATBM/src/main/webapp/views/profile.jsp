<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>

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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/order-security.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/shop/product">Trang Chủ</a>
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
                <h4 class="text-primary mb-4">
                    <i class="fas fa-history"></i> Lịch sử mua hàng
                </h4>
                
                <!-- Thống kê đơn hàng bị thay đổi -->
                <c:if test="${orders != null && not empty orders}">
                    <c:set var="tamperedCount" value="0" />
                    <c:forEach var="tamperStatus" items="${tamperStatuses}">
                        <c:if test="${tamperStatus}">
                            <c:set var="tamperedCount" value="${tamperedCount + 1}" />
                        </c:if>
                    </c:forEach>
                    
                    <c:if test="${tamperedCount > 0}">
                        <div class="tamper-count">
                            <i class="fas fa-exclamation-triangle"></i>
                            <span>Cảnh báo: ${tamperedCount} đơn hàng đã bị phát hiện thay đổi!</span>
                            <button onclick="showTamperInfo()" class="btn btn-sm btn-outline-light ms-auto">
                                <i class="fas fa-info-circle"></i> Tìm hiểu thêm
                            </button>
                        </div>
                        
                        <div class="tamper-warning">
                            <h6><i class="fas fa-shield-alt"></i> Thông tin bảo mật</h6>
                            <p class="mb-0">
                                Hệ thống đã phát hiện ${tamperedCount} đơn hàng có dấu hiệu bị thay đổi sau khi ký số. 
                                Điều này có thể do lỗi hệ thống hoặc có sự can thiệp không mong muốn. 
                                Vui lòng kiểm tra chi tiết và liên hệ hỗ trợ nếu cần thiết.
                            </p>
                        </div>
                    </c:if>
                </c:if>
                
                <c:choose>
                    <c:when test="${orders != null && not empty orders}">
                        <c:forEach var="order" items="${orders}" varStatus="status">
                            <div class="order-item mb-3 p-3 rounded ${tamperStatuses[status.index] ? 'tampered' : ''}">
                                <div class="order-summary">
                                    <div class="order-info">
                                        <h5 class="mb-2">
                                            <a href="${pageContext.request.contextPath}/user/order/detail/${order.order.orderId}" 
                                               class="text-decoration-none text-dark">
                                                <i class="fas fa-shopping-bag"></i> Đơn hàng #${order.order.orderId}
                                            </a>
                                            <c:if test="${tamperStatuses[status.index]}">
                                                <span class="tamper-badge ms-2">
                                                    <i class="fas fa-exclamation-triangle tamper-icon"></i>
                                                    Đã bị thay đổi
                                                </span>
                                            </c:if>
                                        </h5>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <p class="mb-1"><strong><i class="fas fa-calendar"></i> Ngày đặt:</strong> 
                                                    ${formattedDates[status.index]}
                                                </p>
                                                <p class="mb-1"><strong><i class="fas fa-money-bill-wave"></i> Tổng tiền:</strong> 
                                                    ${order.order.totalPrice != null ? order.order.totalPrice : 0} VNĐ
                                                </p>
                                            </div>
                                            <div class="col-md-6">
                                                <p class="mb-1"><strong><i class="fas fa-credit-card"></i> Thanh toán:</strong> 
                                                    ${order.order.paymentMethod != null ? order.order.paymentMethod : 'N/A'}
                                                </p>
                                                <p class="mb-1"><strong><i class="fas fa-info-circle"></i> Trạng thái:</strong> 
                                                    <span class="order-status status-${order.order.status != null ? order.order.status.toString().toLowerCase() : 'pending'}">
                                                        ${order.order.status != null ? order.order.status : 'Chờ xử lý'}
                                                    </span>
                                                </p>
                                            </div>
                                        </div>
                                        <c:if test="${order.shippingInfo != null}">
                                            <p class="mb-1"><strong><i class="fas fa-user"></i> Người nhận:</strong> 
                                                ${order.shippingInfo.recipientName}
                                            </p>
                                        </c:if>
                                    </div>
                                    <div class="order-actions">
                                        <a href="${pageContext.request.contextPath}/user/order/detail/${order.order.orderId}"
                                           class="btn-view-details">
                                            <i class="fas fa-eye"></i> Xem chi tiết
                                        </a>
                                        <c:if test="${tamperStatuses[status.index]}">
                                            <a href="${pageContext.request.contextPath}/user/order/detail/${order.order.orderId}"
                                               class="btn-resign">
                                                <i class="fas fa-edit"></i> Cập nhật chữ ký
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">
                            <i class="fas fa-info-circle"></i> Bạn chưa có đơn hàng nào.
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
                            <textarea class="form-control" id="publicKeyInput" readonly rows="8" style="resize: none; font-family: monospace; font-size: 12px;">${user.publicKeyActive}</textarea>
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
                    <button type="submit" class="btn btn-primary">Cập nhật</button> </form>
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

        // Sử dụng modern clipboard API nếu có
        if (navigator.clipboard && window.isSecureContext) {
            navigator.clipboard.writeText(publicKeyInput.value).then(function() {
                alert("Public Key đã được sao chép vào clipboard!");
            }).catch(function(err) {
                console.error('Lỗi khi sao chép: ', err);
                // Fallback nếu clipboard API thất bại
                fallbackCopy();
            });
        } else {
            // Fallback cho browser cũ
            fallbackCopy();
        }

        function fallbackCopy() {
            publicKeyInput.select();
            publicKeyInput.setSelectionRange(0, 99999); // For mobile devices
            try {
                document.execCommand("copy");
                alert("Public Key đã được sao chép vào clipboard!");
            } catch (err) {
                console.error('Fallback copy failed: ', err);
                alert("Không thể sao chép tự động. Vui lòng select và copy thủ công.");
            }
        }
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
    function showTamperInfo() {
        const infoModal = new bootstrap.Modal(document.getElementById("tamperInfoModal"));
        infoModal.show();
    }
</script>

<!-- Modal Báo cáo đơn hàng -->
<div class="modal fade" id="reportModal" tabindex="-1" aria-labelledby="reportModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-warning text-dark">
                <h5 class="modal-title" id="reportModalLabel">
                    <i class="fas fa-flag"></i> Báo cáo đơn hàng bị thay đổi
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p class="text-muted">
                    <i class="fas fa-info-circle"></i> 
                    Hệ thống đã phát hiện đơn hàng này có dấu hiệu bị thay đổi sau khi ký số. 
                    Vui lòng mô tả chi tiết nếu bạn nhận thấy sự khác biệt.
                </p>
                <input type="hidden" id="reportOrderId">
                <div class="mb-3">
                    <label for="reportReason" class="form-label">Lý do báo cáo (tùy chọn):</label>
                    <textarea class="form-control" id="reportReason" rows="3" 
                              placeholder="Mô tả chi tiết về những thay đổi bạn nhận thấy..."></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn btn-warning" onclick="submitReport()">
                    <i class="fas fa-paper-plane"></i> Gửi báo cáo
                </button>
            </div>
        </div>
    </div>
</div>

<!-- Modal Thông tin về chữ ký số -->
<div class="modal fade" id="tamperInfoModal" tabindex="-1" aria-labelledby="tamperInfoModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-info text-white">
                <h5 class="modal-title" id="tamperInfoModalLabel">
                    <i class="fas fa-shield-alt"></i> Thông tin về chữ ký số và bảo mật đơn hàng
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-6">
                        <h6><i class="fas fa-check-circle text-success"></i> Đơn hàng an toàn</h6>
                        <p class="text-muted small">
                            Đơn hàng có chữ ký số hợp lệ, đảm bảo tính toàn vẹn dữ liệu và không bị thay đổi sau khi ký.
                        </p>
                    </div>
                    <div class="col-md-6">
                        <h6><i class="fas fa-exclamation-triangle text-danger"></i> Đơn hàng bị thay đổi</h6>
                        <p class="text-muted small">
                            Đơn hàng có dấu hiệu bị thay đổi sau khi ký số. Có thể do lỗi hệ thống hoặc can thiệp không mong muốn.
                        </p>
                    </div>
                </div>
                <hr>
                <h6><i class="fas fa-cogs"></i> Cách thức hoạt động</h6>
                <ul class="text-muted small">
                    <li>Khi đặt hàng, hệ thống tạo chữ ký số cho toàn bộ thông tin đơn hàng</li>
                    <li>Chữ ký số được tạo bằng private key của hệ thống</li>
                    <li>Khi xem đơn hàng, hệ thống xác minh chữ ký bằng public key</li>
                    <li>Nếu chữ ký không hợp lệ, đơn hàng được đánh dấu là "bị thay đổi"</li>
                </ul>
                <hr>
                <h6><i class="fas fa-question-circle"></i> Cần làm gì khi đơn hàng bị thay đổi?</h6>
                <ul class="text-muted small">
                    <li>Kiểm tra lại thông tin đơn hàng có chính xác không</li>
                    <li>Liên hệ hỗ trợ khách hàng nếu có nghi ngờ</li>
                    <li>Sử dụng nút "Báo cáo" để thông báo cho quản trị viên</li>
                    <li>Lưu ý: Đơn hàng vẫn có thể được xử lý bình thường</li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Đã hiểu</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>