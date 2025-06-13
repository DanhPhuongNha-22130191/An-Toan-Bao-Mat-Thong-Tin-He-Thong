<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác nhận đơn hàng - Watch Shop</title>
    <link rel="icon" href="${pageContext.request.contextPath}/assests/img/Fevicon.png" type="image/png">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/linericon/style.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/nice-select/nice-select.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/nouislider/nouislider.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/main.css">
    <style>
        .notification {
            position: fixed;
            top: 20px;
            right: 20px;
            padding: 15px 25px;
            border-radius: 4px;
            color: white;
            font-weight: 500;
            z-index: 1000;
            display: none;
            animation: slideIn 0.5s ease-out;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        .notification.success {
            background-color: #28a745;
        }

        .notification.error {
            background-color: #dc3545;
        }

        .notification.warning {
            background-color: #ffc107;
            color: #000;
        }

        @keyframes slideIn {
            from {
                transform: translateX(100%);
                opacity: 0;
            }
            to {
                transform: translateX(0);
                opacity: 1;
            }
        }

        @keyframes fadeOut {
            from {
                opacity: 1;
            }
            to {
                opacity: 0;
            }
        }

        .confirmation-section {
            background: #fff;
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-bottom: 24px;
            margin-top: 24px;
        }

        .confirmation-header {
            text-align: center;
            margin-bottom: 32px;
        }

        .confirmation-header i {
            font-size: 48px;
            color: #28a745;
            margin-bottom: 15px;
        }

        .confirmation-header h2 {
            color: #28a745;
            margin-bottom: 10px;
        }

        .order-info {
            margin-bottom: 32px;
        }

        .order-info h4 {
            font-size: 1.15rem;
            margin-bottom: 18px;
            padding-bottom: 8px;
            border-bottom: 1px solid #eee;
            font-weight: 600;
        }

        .info-row {
            display: flex;
            margin-bottom: 10px;
        }

        .info-label {
            width: 140px;
            font-weight: 500;
            color: #666;
        }

        .info-value {
            flex: 1;
        }

        .order-items h4 {
            font-size: 1.15rem;
            margin-bottom: 14px;
            font-weight: 600;
        }

        .order-items {
            margin-bottom: 32px;
        }

        .order-item {
            display: flex;
            align-items: center;
            padding: 12px 0;
            border-bottom: 1px solid #eee;
        }

        .order-item:last-child {
            border-bottom: none;
        }

        .order-item img {
            width: 72px;
            height: 72px;
            object-fit: cover;
            margin-right: 15px;
            border-radius: 4px;
        }

        .item-details {
            flex: 1;
        }

        .item-name {
            font-weight: 500;
            margin-bottom: 4px;
        }

        .item-price,
        .item-quantity {
            color: #666;
            font-size: 0.98em;
        }

        .item-total {
            font-weight: 500;
            color: #384aeb;
            min-width: 90px;
            text-align: right;
        }

        .order-summary {
            background: #f8f9fa;
            padding: 20px 22px;
            border-radius: 5px;
            margin-bottom: 18px;
        }

        .order-summary h4 {
            font-size: 1.12rem;
            margin-bottom: 16px;
            font-weight: 600;
        }

        .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .total-row {
            font-weight: bold;
            border-top: 1px solid #ddd;
            padding-top: 10px;
            margin-top: 10px;
            font-size: 1.07em;
        }

        .digital-signature-section {
            margin-top: 38px;
            padding: 22px;
            background: #f8f9fa;
            border-radius: 5px;
        }

        .digital-signature-section h4 {
            font-size: 1.11rem;
            font-weight: 600;
            margin-bottom: 12px;
        }

        .signature-info {
            margin-bottom: 10px;
        }

        .signature-image,
        .signature-input {
            width: 100%;
            margin: 10px 0 0 0;
            border-radius: 4px;
            border: 1px solid #ddd;
            font-family: monospace;
            font-size: 0.98em;
            padding: 8px;
            background: #fff;
        }

        .signature-image {
            min-height: 38px;
            color: #333;
        }

        .copy-btn {
            position: absolute;
            right: 10px;
            top: 10px;
            background: none;
            border: none;
            color: #384aeb;
            cursor: pointer;
            transition: all 0.3s ease;
            padding: 5px;
            border-radius: 4px;
        }

        .copy-btn:hover {
            color: #2a3aeb;
            background-color: rgba(56, 74, 235, 0.1);
        }

        .copy-btn.copied {
            color: #28a745;
            background-color: rgba(40, 167, 69, 0.1);
            animation: pulse 0.5s ease;
        }

        .copy-btn i {
            transition: transform 0.3s ease;
        }

        .copy-btn.copied i {
            transform: scale(1.2);
        }

        @keyframes pulse {
            0% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.1);
            }
            100% {
                transform: scale(1);
            }
        }

        .hash-display {
            background: #fff;
            padding: 14px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-top: 10px;
            word-break: break-all;
            position: relative;
            transition: all 0.3s ease;
        }

        .hash-display.copied {
            border-color: #28a745;
            box-shadow: 0 0 0 2px rgba(40, 167, 69, 0.2);
        }

        #hash-value {
            transition: all 0.3s ease;
            padding: 8px;
            border-radius: 4px;
            background: #f8f9fa;
            margin-top: 5px;
        }

        #hash-value.copied {
            background: rgba(40, 167, 69, 0.1);
            color: #28a745;
        }

        .signature-status {
            display: inline-block;
            padding: 4px 9px;
            border-radius: 4px;
            margin: 10px 0 6px 0;
            font-size: 0.99em;
        }

        .signed {
            background: #d4edda;
            color: #155724;
        }

        .unsigned {
            background: #fff3cd;
            color: #856404;
        }

        .action-buttons {
            margin-top: 30px;
            text-align: center;
        }

        .action-buttons a {
            display: inline-block;
            padding: 10px 20px;
            margin: 0 10px;
            border-radius: 4px;
            text-decoration: none;
            transition: background 0.2s;
        }

        .print-btn {
            background: #384aeb;
            color: white;
        }

        .continue-btn {
            background: #f8f9fa;
            color: #666;
            border: 1px solid #ddd;
        }

        .print-btn:hover {
            background: #2632a7;
        }

        .continue-btn:hover {
            background: #e9ecef;
        }

        form.signature-form {
            display: flex;
            flex-direction: row;
            gap: 10px;
            margin-top: 10px;
            align-items: flex-end;
        }

        form.signature-form input[type="text"] {
            flex: 1;
        }

        form.signature-form button {
            padding: 8px 16px;
            border: none;
            background: #384aeb;
            color: white;
            border-radius: 4px;
            font-weight: 500;
            cursor: pointer;
        }

        form.signature-form button:hover {
            background: #2632a7;
        }

        @media (max-width: 991px) {
            .row {
                flex-direction: column;
            }

            .col-lg-8, .col-lg-4 {
                width: 100% !important;
                max-width: 100%;
            }

            .action-buttons {
                margin-bottom: 18px;
            }
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>

<div id="notification" class="notification">
    <span id="notification-message"></span>
</div>

<section class="confirmation_area">
    <div class="container">
        <div class="confirmation-section">
            <div class="confirmation-header">
                <i class="fas fa-check-circle"></i>
                <h2>Đặt hàng thành công!</h2>
                <p>Cảm ơn bạn đã đặt hàng. Mã đơn hàng của bạn là: #${order.orderId}</p>
            </div>
            <div class="row" style="display: flex; flex-wrap: wrap;">
                <div class="col-lg-8" style="flex: 1 1 60%; max-width: 66.6667%;">
                    <div class="order-info">
                        <h4>Thông tin đơn hàng</h4>
                        <div class="info-row">
                            <div class="info-label">Mã đơn hàng:</div>
                            <div class="info-value">#${order.orderId}</div>
                        </div>
                        <div class="info-row">
                            <div class="info-label">Ngày đặt:</div>
                            <div class="info-value">${order.orderDate.format(dateFormatter)}</div>
                        </div>
                        <div class="info-row">
                            <div class="info-label">Trạng thái:</div>
                            <div class="info-value">
                                <span class="badge badge-${order.status == 'ĐANG XỬ LÝ' ? 'warning' :
                                                order.status == 'ĐÃ XÁC NHẬN' ? 'info' :
                                                order.status == 'ĐANG VẬN CHUYỂN' ? 'primary' :
                                                order.status == 'ĐÃ GIAO' ? 'success' : 'danger'}">
                                    ${order.status}
                                </span>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="info-label">Phương thức thanh toán:</div>
                            <div class="info-value">${order.paymentMethod}</div>
                        </div>
                    </div>
                    <div class="order-info">
                        <h4>Thông tin giao hàng</h4>
                        <div class="info-row">
                            <div class="info-label">Người nhận:</div>
                            <div class="info-value" id="full-name">${orderDetail.fullName}</div>
                        </div>
                        <div class="info-row">
                            <div class="info-label">Địa chỉ:</div>
                            <div class="info-value" id="address">${orderDetail.address}</div>
                        </div>
                        <div class="info-row">
                            <div class="info-label">Số điện thoại:</div>
                            <div class="info-value" id="phone-number">${orderDetail.phone}</div>
                        </div>
                        <div class="info-row">
                            <div class="info-label">Email:</div>
                            <div class="info-value" id="email">${orderDetail.email}</div>
                        </div>
                        <c:if test="${not empty orderDetail.orderNote}">
                            <div class="info-row">
                                <div class="info-label">Ghi chú:</div>
                                <div class="info-value">${orderDetail.orderNote}</div>
                            </div>
                        </c:if>
                    </div>
                    <div class="order-items">
                        <h4>Chi tiết đơn hàng</h4>
                        <c:forEach var="item" items="${cartDTO.items}">
                            <div class="order-item">
                                <img src="${pageContext.request.contextPath}/assests/img/${item.productImg}"
                                     alt="${item.productName}">
                                <div class="item-details">
                                    <div class="item-name">${item.productName}</div>
                                    <div class="item-price">Giá: ${item.productPrice}</div>
                                    <div class="item-quantity">Số lượng: ${item.quantity}</div>
                                </div>
                                <div class="item-total">${item.totalPrice}</div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="digital-signature-section">
                        <h4>Chữ ký điện tử</h4>
                        <div class="signature-info">
                            <div class="signature-status ${not empty orderSecurity ? 'signed' : 'unsigned'}">
                                <i class="fas fa-${not empty orderSecurity ? 'check' : 'exclamation'}-circle"></i>
                                ${not empty orderSecurity ? 'Đã ký' : 'Chưa ký'}
                            </div>
                            <c:choose>
                                <c:when test="${not empty orderSecurity}">
                                    <div>Chữ ký số:</div>
                                    <input type="text" value="${orderSecurity.signature}" readonly class="signature-image"/>
                                </c:when>
                                <c:otherwise>
                                    <div class="hash-display">
                                        <button class="copy-btn" onclick="copyHash()" type="button">
                                            <i class="fas fa-copy"></i>
                                        </button>
                                        <div>Thông tin đơn hàng:</div>
                                        <div id="hash-value">Đang tạo thông tin đơn...</div>
                                    </div>
                                    <div>Nhập chữ ký để xác nhận:</div>
                                    <form class="signature-form"
                                          action="${pageContext.servletContext.contextPath}/user/order/confirmation"
                                          method="post">
                                        <input type="hidden" name="orderId" value="${order.orderId}">
                                        <input type="text" name="signature" class="signature-input"
                                               placeholder="Nhập chữ ký số..." required/>
                                        <button type="submit"><i class="fas fa-pen-nib"></i> Xác nhận</button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <!-- Thêm cảnh báo đơn hàng bị thay đổi -->
                        <c:if test="${isTampered}">
                            <div class="alert alert-danger" style="margin-top: 15px;">
                                <i class="fas fa-exclamation-triangle"></i> Đơn hàng đã bị thay đổi so với chữ ký đã ký!
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="col-lg-4" style="flex: 1 1 33%; max-width: 33.3333%;">
                    <div class="order-summary">
                        <h4>Tổng đơn hàng</h4>
                        <div class="summary-row">
                            <span>Tạm tính:</span>
                            <span>${cartDTO.subTotal}</span>
                        </div>
                        <div class="summary-row">
                            <span>Phí vận chuyển:</span>
                            <span>${order.shipping}</span>
                        </div>
                        <c:if test="${not empty voucher}">
                            <div class="summary-row">
                                <span>Giảm giá (${voucher.code}):</span>
                                <span>-${cartDTO.discount}</span>
                            </div>
                        </c:if>
                        <div class="summary-row total-row">
                            <span>Tổng cộng:</span>
                            <span>${cartDTO.totalPrice}</span>
                        </div>
                    </div>
                    <div class="action-buttons">
                        <a href="#" class="print-btn" onclick="window.print()">
                            <i class="fas fa-print"></i> In đơn hàng
                        </a>
                        <a href="${pageContext.request.contextPath}/product/category" class="continue-btn">
                            Tiếp tục mua sắm
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<jsp:include page="footer.jsp"/>

<script src="${pageContext.servletContext.contextPath}/assests/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/vendors/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/vendors/skrollr.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/vendors/nice-select/jquery.nice-select.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/vendors/jquery.ajaxchimp.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/vendors/mail-script.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/js/main.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", () => {
        // Function to generate hash from order details
        const productInfo = '${cartDTO != null ? cartDTO.productInfo : ""}';

        async function generateOrderHash() {
            try {
                const paymentMethod = "COD";
                const name = document.getElementById('full-name').textContent.trim();
                const phone = document.getElementById('phone-number').textContent.trim();
                const email = document.getElementById('email').textContent.trim();
                const address = document.getElementById('address').textContent.trim();
                
                if (!name || !phone || !email || !address) {
                    throw new Error('Thiếu thông tin đơn hàng');
                }
                return paymentMethod + "|"
                    + name + "|"
                    + phone + "|"
                    + email + "|"
                    + address + "|"
                    + productInfo;
            } catch (error) {
                console.error('Error generating hash:', error);
                throw error;
            }
        }

        async function updateHashDisplay() {
            try {
                const hashValueDiv = document.getElementById('hash-value');
                if (!hashValueDiv) return;

                hashValueDiv.textContent = "Đang tạo thông tin đơn...";
                const hash = await generateOrderHash();
                hashValueDiv.textContent = hash;
            } catch (error) {
                console.error('Error updating hash display:', error);
                const hashValueDiv = document.getElementById('hash-value');
                if (hashValueDiv) {
                    hashValueDiv.textContent = "Lỗi tạo hash";
                }
                showNotification('Lỗi khi tạo thông tin đơn ', 'error');
            }
        }

        // Gọi updateHashDisplay khi trang load
        updateHashDisplay();

        // Định nghĩa hàm copyHash
        window.copyHash = function() {
            const hashValueDiv = document.getElementById('hash-value');
            const copyBtn = document.querySelector('.copy-btn');
            const hashDisplay = document.querySelector('.hash-display');
            
            if (!hashValueDiv) {
                showNotification("Không tìm thấy mã !", "error");
                return;
            }

            const hash = hashValueDiv.textContent.trim();
            if (!hash || hash === "Đang tạo thông tin đơn ..." || hash === "Lỗi tạo thông tin đơn hàng") {
                showNotification("Không thể sao chép thông tin!", "warning");
                return;
            }

            // Thêm hiệu ứng trước khi copy
            if (copyBtn) {
                copyBtn.classList.add('copied');
                copyBtn.querySelector('i').classList.remove('fa-copy');
                copyBtn.querySelector('i').classList.add('fa-check');
            }
            if (hashDisplay) {
                hashDisplay.classList.add('copied');
            }
            if (hashValueDiv) {
                hashValueDiv.classList.add('copied');
            }

            navigator.clipboard.writeText(hash)
                .then(() => {
                    showNotification('Đã sao chép mã hash!', 'success');
                    
                    // Giữ hiệu ứng trong 2 giây
                    setTimeout(() => {
                        if (copyBtn) {
                            copyBtn.classList.remove('copied');
                            copyBtn.querySelector('i').classList.remove('fa-check');
                            copyBtn.querySelector('i').classList.add('fa-copy');
                        }
                        if (hashDisplay) {
                            hashDisplay.classList.remove('copied');
                        }
                        if (hashValueDiv) {
                            hashValueDiv.classList.remove('copied');
                        }
                    }, 2000);
                })
                .catch(err => {
                    console.error('Lỗi khi sao chép:', err);
                    showNotification('Lỗi khi sao chép mã hash', 'error');
                    
                    // Xóa hiệu ứng nếu có lỗi
                    if (copyBtn) {
                        copyBtn.classList.remove('copied');
                        copyBtn.querySelector('i').classList.remove('fa-check');
                        copyBtn.querySelector('i').classList.add('fa-copy');
                    }
                    if (hashDisplay) {
                        hashDisplay.classList.remove('copied');
                    }
                    if (hashValueDiv) {
                        hashValueDiv.classList.remove('copied');
                    }
                });
        };

        // Định nghĩa hàm showNotification
        window.showNotification = function(message, type = 'success') {
            const notification = document.getElementById('notification');
            const messageElement = document.getElementById('notification-message');
            if (!notification || !messageElement) return;

            notification.classList.remove('success', 'error', 'warning');
            notification.classList.add(type);
            messageElement.textContent = message;
            notification.style.display = 'block';
            
            setTimeout(() => {
                notification.style.animation = 'fadeOut 0.5s ease-out';
                setTimeout(() => {
                    notification.style.display = 'none';
                    notification.style.animation = 'slideIn 0.5s ease-out';
                }, 500);
            }, 3000);
        };
    });

    // Xử lý message từ server
    <c:if test="${not empty message}">
        const message = '${message}';
        const lowerMessage = message.toLowerCase();
        if (lowerMessage.includes('lỗi') || lowerMessage.includes('thất bại') ||
            lowerMessage.includes('không hợp lệ') || lowerMessage.includes('không tồn tại')) {
            window.showNotification(message, 'error');
        } else if (lowerMessage.includes('cảnh báo') || lowerMessage.includes('chỉ còn') ||
            lowerMessage.includes('vui lòng')) {
            window.showNotification(message, 'warning');
        } else {
            window.showNotification(message, 'success');
        }
    </c:if>
</script>
</body>
</html>