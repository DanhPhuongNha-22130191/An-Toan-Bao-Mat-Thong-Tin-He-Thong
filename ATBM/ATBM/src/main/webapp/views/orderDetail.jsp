<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="jakarta.tags.core" prefix="c" %>
<%@taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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
            border-radius: 8px;
            color: white;
            font-weight: 500;
            z-index: 1000;
            display: none;
            animation: slideIn 0.5s ease-out;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            backdrop-filter: blur(10px);
        }

        .notification.success {
            background: linear-gradient(135deg, #28a745, #20c997);
        }

        .notification.error {
            background: linear-gradient(135deg, #dc3545, #e74c3c);
        }

        .notification.warning {
            background: linear-gradient(135deg, #ffc107, #f39c12);
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
            background: linear-gradient(135deg, #fff 0%, #f8f9fa 100%);
            padding: 40px;
            border-radius: 16px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
            margin-bottom: 30px;
            margin-top: 30px;
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .confirmation-header {
            text-align: center;
            margin-bottom: 40px;
        }

        .confirmation-header i {
            font-size: 64px;
            background: linear-gradient(135deg, #28a745, #20c997);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            margin-bottom: 20px;
            display: block;
        }

        .confirmation-header h2 {
            background: linear-gradient(135deg, #28a745, #20c997);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
            margin-bottom: 15px;
            font-weight: 700;
        }

        .confirmation-header p {
            color: #6c757d;
            font-size: 1.1em;
        }

        .order-info {
            margin-bottom: 40px;
            background: #fff;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
            border: 1px solid #e9ecef;
        }

        .order-info h4 {
            font-size: 1.25rem;
            margin-bottom: 20px;
            padding-bottom: 12px;
            border-bottom: 2px solid #e9ecef;
            font-weight: 700;
            color: #2c3e50;
            position: relative;
        }

        .order-info h4::after {
            content: '';
            position: absolute;
            bottom: -2px;
            left: 0;
            width: 60px;
            height: 2px;
            background: linear-gradient(135deg, #384aeb, #6c5ce7);
        }

        .info-row {
            display: flex;
            margin-bottom: 12px;
            align-items: center;
            padding: 8px 0;
        }

        .info-label {
            width: 160px;
            font-weight: 600;
            color: #495057;
            font-size: 0.95em;
        }

        .info-value {
            flex: 1;
            color: #2c3e50;
            font-weight: 500;
        }

        .badge {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 0.85em;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        .badge-warning {
            background: linear-gradient(135deg, #ffc107, #f39c12);
            color: #000;
        }

        .badge-primary {
            background: linear-gradient(135deg, #007bff, #0056b3);
            color: white;
        }

        .badge-success {
            background: linear-gradient(135deg, #28a745, #20c997);
            color: white;
        }

        .badge-danger {
            background: linear-gradient(135deg, #dc3545, #c82333);
            color: white;
        }

        .order-items h4 {
            font-size: 1.25rem;
            margin-bottom: 20px;
            font-weight: 700;
            color: #2c3e50;
        }

        .order-items {
            margin-bottom: 40px;
            background: #fff;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
            border: 1px solid #e9ecef;
        }

        .order-item {
            display: flex;
            align-items: center;
            padding: 16px 0;
            border-bottom: 1px solid #f1f3f4;
            transition: all 0.3s ease;
        }

        .order-item:hover {
            background: #f8f9fa;
            border-radius: 8px;
            padding: 16px;
            margin: 0 -16px;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .order-item:last-child {
            border-bottom: none;
        }

        .order-item img {
            width: 80px;
            height: 80px;
            object-fit: cover;
            margin-right: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }

        .order-item:hover img {
            transform: scale(1.05);
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
        }

        .item-details {
            flex: 1;
        }

        .item-name {
            font-weight: 600;
            margin-bottom: 6px;
            color: #2c3e50;
            font-size: 1.05em;
        }

        .item-price,
        .item-quantity {
            color: #6c757d;
            font-size: 0.95em;
            margin-bottom: 2px;
        }

        .item-total {
            font-weight: 700;
            color: #384aeb;
            min-width: 120px;
            text-align: right;
            font-size: 1.1em;
        }

        .order-summary {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            padding: 25px;
            border-radius: 12px;
            margin-bottom: 25px;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
            border: 1px solid #dee2e6;
        }

        .order-summary h4 {
            font-size: 1.2rem;
            margin-bottom: 20px;
            font-weight: 700;
            color: #2c3e50;
        }

        .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 12px;
            padding: 8px 0;
            font-size: 1em;
        }

        .total-row {
            font-weight: 700;
            border-top: 2px solid #dee2e6;
            padding-top: 15px;
            margin-top: 15px;
            font-size: 1.15em;
            color: #2c3e50;
        }

        .digital-signature-section {
            margin-top: 40px;
            padding: 30px;
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
            border: 1px solid #dee2e6;
        }

        .digital-signature-section h4 {
            font-size: 1.2rem;
            font-weight: 700;
            margin-bottom: 20px;
            color: #2c3e50;
        }

        .signature-info {
            margin-bottom: 15px;
        }

        .signature-image,
        .signature-input {
            width: 100%;
            margin: 10px 0 0 0;
            border-radius: 8px;
            border: 2px solid #e9ecef;
            font-family: 'Courier New', monospace;
            font-size: 0.95em;
            padding: 12px;
            background: #fff;
            transition: all 0.3s ease;
        }

        .signature-image {
            min-height: 50px;
            color: #2c3e50;
            background: #f8f9fa;
        }

        .signature-input:focus {
            border-color: #384aeb;
            box-shadow: 0 0 0 3px rgba(56, 74, 235, 0.1);
            outline: none;
        }

        .copy-btn {
            position: absolute;
            right: 12px;
            top: 12px;
            background: rgba(56, 74, 235, 0.1);
            border: none;
            color: #384aeb;
            cursor: pointer;
            transition: all 0.3s ease;
            padding: 8px;
            border-radius: 6px;
            font-size: 1em;
        }

        .copy-btn:hover {
            background: rgba(56, 74, 235, 0.2);
            transform: scale(1.05);
        }

        .copy-btn.copied {
            background: rgba(40, 167, 69, 0.2);
            color: #28a745;
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
            padding: 20px;
            border: 2px solid #e9ecef;
            border-radius: 8px;
            margin-top: 15px;
            word-break: break-all;
            position: relative;
            transition: all 0.3s ease;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
        }

        .hash-display.copied {
            border-color: #28a745;
            box-shadow: 0 0 0 3px rgba(40, 167, 69, 0.1);
        }

        #hash-value {
            transition: all 0.3s ease;
            padding: 12px;
            border-radius: 6px;
            background: #f8f9fa;
            margin-top: 8px;
            font-family: 'Courier New', monospace;
            font-size: 0.9em;
            line-height: 1.5;
            color: #495057;
            white-space: pre-line;
            word-break: break-word;
        }

        #hash-value.copied {
            background: rgba(40, 167, 69, 0.1);
            color: #28a745;
        }

        .signature-status {
            display: inline-flex;
            align-items: center;
            padding: 8px 16px;
            border-radius: 20px;
            margin: 15px 0 10px 0;
            font-size: 0.95em;
            font-weight: 600;
            gap: 8px;
        }

        .signed {
            background: linear-gradient(135deg, #d4edda, #c3e6cb);
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .unsigned {
            background: linear-gradient(135deg, #fff3cd, #ffeaa7);
            color: #856404;
            border: 1px solid #ffeaa7;
        }

        .action-buttons {
            margin-top: 30px;
            text-align: center;
        }

        .action-buttons a {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 12px 24px;
            margin: 0 8px;
            border-radius: 25px;
            text-decoration: none;
            transition: all 0.3s ease;
            font-weight: 600;
            font-size: 0.95em;
        }

        .print-btn {
            background: linear-gradient(135deg, #384aeb, #6c5ce7);
            color: white;
            box-shadow: 0 4px 12px rgba(56, 74, 235, 0.3);
        }

        .continue-btn {
            background: linear-gradient(135deg, #f8f9fa, #e9ecef);
            color: #495057;
            border: 2px solid #dee2e6;
        }

        .print-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(56, 74, 235, 0.4);
        }

        .continue-btn:hover {
            background: linear-gradient(135deg, #e9ecef, #dee2e6);
            transform: translateY(-2px);
        }

        .alert {
            padding: 15px 20px;
            border-radius: 8px;
            margin: 15px 0;
            border: 1px solid transparent;
            font-weight: 500;
        }

        .alert-success {
            background: linear-gradient(135deg, #d4edda, #c3e6cb);
            color: #155724;
            border-color: #c3e6cb;
        }

        .alert-warning {
            background: linear-gradient(135deg, #fff3cd, #ffeaa7);
            color: #856404;
            border-color: #ffeaa7;
        }

        .alert-danger {
            background: linear-gradient(135deg, #f8d7da, #f5c6cb);
            color: #721c24;
            border-color: #f5c6cb;
        }

        .form-text {
            font-size: 0.875em;
            color: #6c757d;
            margin-top: 5px;
        }

        form.signature-form {
            display: flex;
            flex-direction: row;
            gap: 12px;
            margin-top: 15px;
            align-items: flex-end;
        }

        form.signature-form input[type="text"] {
            flex: 1;
        }

        form.signature-form button {
            padding: 12px 20px;
            border: none;
            background: linear-gradient(135deg, #384aeb, #6c5ce7);
            color: white;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(56, 74, 235, 0.3);
        }

        form.signature-form button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(56, 74, 235, 0.4);
        }

        .btn-primary {
            background: linear-gradient(135deg, #384aeb, #6c5ce7) !important;
            border: none !important;
            box-shadow: 0 4px 12px rgba(56, 74, 235, 0.3) !important;
            transition: all 0.3s ease !important;
        }

        .btn-primary:hover {
            transform: translateY(-2px) !important;
            box-shadow: 0 6px 20px rgba(56, 74, 235, 0.4) !important;
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
                margin-bottom: 20px;
            }

            .confirmation-section {
                padding: 25px;
                margin: 20px 0;
            }

            .order-info, .order-items, .digital-signature-section {
                padding: 20px;
            }

            .info-row {
                flex-direction: column;
                align-items: flex-start;
                gap: 4px;
            }

            .info-label {
                width: 100%;
                margin-bottom: 4px;
            }

            .order-item {
                flex-direction: column;
                text-align: center;
                gap: 15px;
            }

            .order-item img {
                margin-right: 0;
            }

            .item-total {
                text-align: center;
                min-width: auto;
            }
        }

        @media (max-width: 576px) {
            .confirmation-header i {
                font-size: 48px;
            }

            .confirmation-header h2 {
                font-size: 1.5rem;
            }

            .action-buttons a {
                display: block;
                margin: 8px 0;
                text-align: center;
            }

            form.signature-form {
                flex-direction: column;
                align-items: stretch;
            }
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"/>
<c:set var="shippingInfo" value="${requestScope.order.shippingInfo}"/>
<c:set var="orderItems" value="${requestScope.order.orderItems}"/>
<c:set var="isDigitallySigned" value="${requestScope.order.isDigitallySigned()}"/>
<c:set var="order" value="${requestScope.order.order}"/>

<c:set var="dateFormatter" value="<%= DateTimeFormatter.ofPattern(\"dd/MM/yyyy\") %>"/>

<section class="confirmation_area">
    <div class="container">
        <div class="confirmation-section">
            <div class="confirmation-header">
                <i class="fas fa-check-circle"></i>
                <h2>Đặt hàng thành công!</h2>
                <p>Cảm ơn bạn đã đặt hàng. Mã đơn hàng của bạn là: #${order.orderId}</p>
            </div>

            <div class="row">
                <div class="col-lg-8">
                    <div class="order-info">
                        <h4>Thông tin đơn hàng</h4>
                        <div class="info-row">
                            <div class="info-label">Mã đơn hàng:</div>
                            <div class="info-value">#${order.orderId}</div>
                        </div>
                        <div class="info-row">
                            <div class="info-label">Ngày đặt:</div>
                            <div class="info-value">
                                ${order.orderAt.format(dateFormatter)}
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="info-label">Trạng thái:</div>
                            <div class="info-value">
                                <c:set var="status" value="${order.status.name()}"/>
                                <span class="badge badge-${status == 'PROCESSING' ? 'warning' : status == 'SHIPPED' ? 'primary' : status == 'DELIVERED' ? 'success' : 'danger'}">
                                    ${status == 'PROCESSING' ? 'ĐANG XỬ LÝ' : status == 'SHIPPED' ? 'ĐANG VẬN CHUYỂN' : status == 'DELIVERED' ? 'ĐÃ GIAO' : 'ĐÃ HỦY'}
                                </span>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="info-label">Phương thức thanh toán:</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${order.paymentMethod.name() == 'CASH'}">Tiền mặt</c:when>
                                    <c:when test="${order.paymentMethod.name() == 'CARD'}">Thẻ</c:when>
                                    <c:when test="${order.paymentMethod.name() == 'PAYPAL'}">PayPal</c:when>
                                    <c:otherwise>Không xác định</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <div class="order-info">
                        <h4>Thông tin giao hàng</h4>
                        <div class="info-row">
                            <div class="info-label">Người nhận:</div>
                            <div class="info-value">${shippingInfo.recipientName}</div>
                        </div>
                        <div class="info-row">
                            <div class="info-label">Số điện thoại:</div>
                            <div class="info-value">${shippingInfo.phoneNumber}</div>
                        </div>
                        <div class="info-row">
                            <div class="info-label">Địa chỉ:</div>
                            <div class="info-value">
                                ${shippingInfo.addressLine}, ${shippingInfo.ward}, ${shippingInfo.district}, ${shippingInfo.province}
                            </div>
                        </div>
                        <c:if test="${not empty shippingInfo.note}">
                            <div class="info-row">
                                <div class="info-label">Ghi chú:</div>
                                <div class="info-value">${shippingInfo.note}</div>
                            </div>
                        </c:if>
                    </div>

                    <div class="order-items">
                        <h4>Chi tiết đơn hàng</h4>
                        <c:forEach var="item" items="${orderItems}">
                            <div class="order-item">
                                <img src="${pageContext.request.contextPath}/admin/productImage?productId=${item.productId}"
                                     alt="${item.nameSnapshot}" style="width: 100px;">
                                <div class="item-details">
                                    <div class="item-name">${item.nameSnapshot}</div>
                                    <div class="item-price">Giá: ${item.priceSnapshot} VNĐ</div>
                                    <div class="item-quantity">Số lượng: ${item.quantity}</div>
                                </div>
                                <div class="item-total">
                                        ${item.priceSnapshot * item.quantity} VNĐ
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="digital-signature-section">
                        <h4>Chữ ký điện tử</h4>

                        <!-- Hiển thị trạng thái ký -->
                        <div class="signature-status ${isDigitallySigned ? 'signed' : 'unsigned'}">
                            <i class="fas fa-${isDigitallySigned ? 'check' : 'exclamation'}-circle"></i>
                            ${isDigitallySigned ? 'Đã ký' : 'Chưa ký'}
                        </div>

                        <!-- Cảnh báo nếu đơn hàng chưa ký -->
                        <c:if test="${!isDigitallySigned}">
                            <div class="alert alert-warning" style="margin-top: 15px;">
                                <i class="fas fa-exclamation-triangle"></i>
                                <strong>Cảnh báo:</strong> Đơn hàng này chưa được ký điện tử. 
                                Vui lòng ký để đảm bảo tính toàn vẹn và xác thực của đơn hàng.
                            </div>
                        </c:if>

                        <!-- Hiển thị thông tin đơn hàng để ký -->
                        <div class="hash-display" style="margin-bottom: 8px;">
                            <div>Thông tin đơn hàng:</div>
                            <div id="hash-value" style="font-size:0.97em; white-space: pre-line;"><c:out value="Ngày đặt: ${order.orderAt.format(dateFormatter)}
Họ tên: ${shippingInfo.recipientName}
SĐT: ${shippingInfo.phoneNumber}
Địa chỉ: ${shippingInfo.addressLine}, ${shippingInfo.ward}, ${shippingInfo.district}, ${shippingInfo.province}
Sản phẩm:"/><c:forEach var="item" items="${orderItems}"><c:out value="
  • ${item.nameSnapshot} x${item.quantity}: ${item.priceSnapshot * item.quantity} VNĐ"/></c:forEach><c:out value="
Tổng tiền: ${order.totalPrice} VNĐ"/></div>
                            <button class="copy-btn" id="copyHashBtn" type="button" style="top:35px;" title="Copy">
                                <i class="fas fa-copy"></i>
                            </button>
                            <button class="copy-btn" id="exportHashBtn" type="button" style="top:65px;" title="Xuất file">
                                <i class="fas fa-file-export"></i>
                            </button>
                        </div>

                        <c:choose>
                            <c:when test="${isDigitallySigned}">
                                <!-- Đã ký - hiển thị thông báo thành công -->
                                <div class="alert alert-success" style="margin-top: 15px;">
                                    <i class="fas fa-check-circle"></i>
                                    <strong>Thành công:</strong> Đơn hàng đã được ký điện tử và đảm bảo tính toàn vẹn.
                                </div>
                            </c:when>
                            <c:otherwise>
                                <!-- Chưa ký - hiển thị form để ký -->
                                <form class="signature-form" id="signature-form" onsubmit="submitSignature(event)">
                                    <input type="hidden" name="orderId" value="${order.orderId}"/>
                                    <div class="mb-3">
                                        <label class="form-label">
                                            <i class="fas fa-edit"></i> Chữ ký số:
                                        </label>
                                        <input type="text" name="signature"
                                               placeholder="Vui lòng nhập chữ ký điện tử để xác nhận đơn hàng"
                                               required class="signature-input" minlength="10"/>
                                        <small class="form-text text-muted">
                                            Chữ ký phải có ít nhất 10 ký tự để đảm bảo tính bảo mật.
                                        </small>
                                    </div>
                                    <button type="submit" class="btn btn-primary btn-sm">
                                        <i class="fas fa-save"></i> Ký đơn hàng
                                    </button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="order-summary">
                        <h4>Tổng đơn hàng</h4>
                        <div class="summary-row">
                            <span>Tổng tiền:</span>
                            <span>${order.totalPrice} VNĐ</span>
                        </div>
                        <div class="summary-row">
                            <span>Phí vận chuyển:</span>
                            <span>${shippingInfo.shippingFee} VNĐ</span>
                        </div>
                        <div class="summary-row total-row">
                            <span>Tổng cộng:</span>
                            <span>${order.totalPrice + shippingInfo.shippingFee} VNĐ</span>
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
    // Xử lý copy thông tin đơn hàng
    document.getElementById('copyHashBtn').addEventListener('click', function () {
        var infoElement = document.getElementById('hash-value');
        var info = infoElement.textContent || infoElement.innerText;
        
        // Loại bỏ khoảng trắng thừa ở đầu và cuối
        info = info.trim();
        
        // Loại bỏ khoảng trắng thừa giữa các dòng
        info = info.replace(/\n\s+/g, '\n');
        
        var btn = this;

        // Thêm hiệu ứng loading
        btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i>';
        btn.disabled = true;

        navigator.clipboard.writeText(info).then(function () {
            // Hiệu ứng thành công
            btn.innerHTML = '<i class="fas fa-check"></i>';
            btn.classList.add('copied');
            showNotification('Đã copy thông tin đơn hàng!', 'success');

            // Reset sau 2 giây
            setTimeout(() => {
                btn.innerHTML = '<i class="fas fa-copy"></i>';
                btn.classList.remove('copied');
                btn.disabled = false;
            }, 2000);
        }).catch(function () {
            // Fallback cho trình duyệt cũ
            var textArea = document.createElement('textarea');
            textArea.value = info;
            document.body.appendChild(textArea);
            textArea.select();
            document.execCommand('copy');
            document.body.removeChild(textArea);

            // Hiệu ứng thành công
            btn.innerHTML = '<i class="fas fa-check"></i>';
            btn.classList.add('copied');
            showNotification('Đã copy thông tin đơn hàng!', 'success');

            // Reset sau 2 giây
            setTimeout(() => {
                btn.innerHTML = '<i class="fas fa-copy"></i>';
                btn.classList.remove('copied');
                btn.disabled = false;
            }, 2000);
        });
    });

    // Xử lý xuất file thông tin đơn hàng
    document.getElementById('exportHashBtn').addEventListener('click', function () {
        var infoElement = document.getElementById('hash-value');
        var info = infoElement.textContent || infoElement.innerText;
        
        // Loại bỏ khoảng trắng thừa ở đầu và cuối
        info = info.trim();
        
        // Loại bỏ khoảng trắng thừa giữa các dòng
        info = info.replace(/\n\s+/g, '\n');
        
        var btn = this;

        // Thêm hiệu ứng loading
        btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i>';
        btn.disabled = true;

        var blob = new Blob([info], {type: 'text/plain;charset=utf-8'});
        var url = URL.createObjectURL(blob);
        var a = document.createElement('a');
        a.href = url;
        a.download = 'order-info-${order.orderId}.txt';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);

        // Hiệu ứng thành công
        btn.innerHTML = '<i class="fas fa-check"></i>';
        btn.classList.add('copied');
        showNotification('Đã xuất file thông tin đơn hàng!', 'success');

        // Reset sau 2 giây
        setTimeout(() => {
            btn.innerHTML = '<i class="fas fa-file-export"></i>';
            btn.classList.remove('copied');
            btn.disabled = false;
        }, 2000);
    });

    // Hàm hiển thị thông báo
    function showNotification(message, type) {
        // Tạo notification element nếu chưa có
        var notification = document.getElementById('notification');
        if (!notification) {
            notification = document.createElement('div');
            notification.id = 'notification';
            notification.className = 'notification';
            notification.style.cssText = `
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
            `;
            document.body.appendChild(notification);
        }

        // Set nội dung và style
        notification.textContent = message;
        notification.className = 'notification ' + type;

        if (type === 'success') {
            notification.style.backgroundColor = '#28a745';
        } else if (type === 'error') {
            notification.style.backgroundColor = '#dc3545';
        } else if (type === 'warning') {
            notification.style.backgroundColor = '#ffc107';
            notification.style.color = '#000';
        }

        // Hiển thị notification
        notification.style.display = 'block';

        // Tự động ẩn sau 3 giây
        setTimeout(function () {
            notification.style.display = 'none';
        }, 3000);
    }

    // Thêm CSS animation cho notification
    var style = document.createElement('style');
    style.textContent = `
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
    `;
    document.head.appendChild(style);

    // Xử lý submit signature form
    function submitSignature(event) {
        event.preventDefault();

        var form = document.getElementById('signature-form');
        var formData = new FormData(form);
        var signature = formData.get('signature');
        var orderId = formData.get('orderId');
        var submitBtn = form.querySelector('button[type="submit"]');

        // Validation
        if (!signature || signature.trim() === '') {
            showNotification('Vui lòng nhập chữ ký số!', 'error');
            return;
        }

        if (signature.trim().length < 10) {
            showNotification('Chữ ký số phải có ít nhất 10 ký tự!', 'warning');
            return;
        }

        // Thêm hiệu ứng loading
        var originalText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang ký...';
        submitBtn.disabled = true;

        // Gửi PATCH request
        fetch('${pageContext.request.contextPath}/user/order/detail/' + orderId, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'signature=' + encodeURIComponent(signature.trim())
        })
            .then(response => {
                if (response.ok) {
                    showNotification('Ký đơn hàng thành công!', 'success');
                    // Reload trang sau 2 giây
                    setTimeout(() => {
                        window.location.reload();
                    }, 2000);
                } else {
                    return response.text().then(text => {
                        throw new Error(text || 'Có lỗi xảy ra khi ký đơn hàng');
                    });
                }
            })
            .catch(error => {
                console.error('Error:', error);
                var errorMessage = error.message || 'Có lỗi xảy ra khi ký đơn hàng!';
                showNotification(errorMessage, 'error');
                // Reset button
                submitBtn.innerHTML = originalText;
                submitBtn.disabled = false;
            });
    }
</script>
</body>
</html>