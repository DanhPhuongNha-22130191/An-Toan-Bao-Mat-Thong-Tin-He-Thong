<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Aroma Shop - Checkout</title>
    <link rel="icon"
          href="${pageContext.request.contextPath}/assests/img/Fevicon.png"
          type="image/png">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assests/vendors/bootstrap/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assests/vendors/fontawesome/css/all.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assests/vendors/themify-icons/themify-icons.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assests/vendors/linericon/style.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.theme.default.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assests/vendors/nice-select/nice-select.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assests/vendors/nouislider/nouislider.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assests/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/main.css">
    <style>
        /* Style cho thông báo */
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

        .checkout-section {
            background: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        .checkout-section h4 {
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
        }

        .form-control {
            width: 100%;
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .payment-methods {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
            margin-top: 15px;
        }

        .payment-method {
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 15px;
            cursor: pointer;
            transition: all 0.3s;
        }

        .payment-method:hover {
            border-color: #384aeb;
        }

        .payment-method.selected {
            border-color: #384aeb;
            background: #f8f9ff;
        }

        .payment-method img {
            max-width: 100px;
            height: auto;
        }

        .signature-section {
            margin-top: 20px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .signature-pad {
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-bottom: 10px;
        }

        .signature-actions {
            display: flex;
            gap: 10px;
            margin-top: 10px;
        }

        .signature-actions button {
            padding: 8px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .signature-actions .clear {
            background: #f8f9fa;
            color: #666;
        }

        .signature-actions .save {
            background: #384aeb;
            color: white;
        }

        .order-summary {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
        }

        .order-summary .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .order-summary .total-row {
            font-weight: bold;
            border-top: 1px solid #ddd;
            padding-top: 10px;
            margin-top: 10px;
        }

        .digital-signature-info {
            margin-top: 20px;
            padding: 15px;
            background: #e8f0f2;
            border-radius: 4px;
        }

        .digital-signature-info .hash-display {
            background: #fff;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-top: 10px;
            word-break: break-all;
            position: relative;
        }

        .digital-signature-info .copy-btn {
            position: absolute;
            right: 10px;
            top: 10px;
            background: none;
            border: none;
            color: #384aeb;
            cursor: pointer;
        }

        .digital-signature-info .copy-btn:hover {
            color: #2a3aeb;
        }

        .digital-signature-info .signature-status {
            margin-top: 10px;
            padding: 5px 10px;
            border-radius: 4px;
            display: inline-block;
        }

        .digital-signature-info .signed {
            background: #d4edda;
            color: #155724;
        }

        .digital-signature-info .unsigned {
            background: #fff3cd;
            color: #856404;
        }
    </style>
</head>
<body>
<!-- Phần thông báo -->
<div id="notification" class="notification">
    <span id="notification-message"></span>
</div>

<!-- ================ start banner area ================= -->
<section class="blog-banner-area" id="category">
    <div class="container h-100">
        <div class="blog-banner">
            <div class="text-center">
                <h1>Product Checkout</h1>
                <nav aria-label="breadcrumb" class="banner-breadcrumb">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active" aria-current="page">Checkout</li>
                    </ol>
                </nav>
            </div>
        </div>
    </div>
</section>
<!-- ================ end banner area ================= -->

<div class="container">
    <!--================Checkout Area =================-->
    <section class="checkout_area section-margin--small">
        <div class="billing_details">
            <div class="row">
                <div class="col-lg-8">
                    <h3>Thông tin giao hàng</h3>
                    <form id="checkout-form" action="${pageContext.request.contextPath}/user/checkout" method="post">
                        <div class="form-group">
                            <label for="full-name">Họ và tên</label>
                            <input type="text" class="form-control" id="full-name" name="full-name" required>
                        </div>
                        <div class="form-group">
                            <label for="phone-number">Số điện thoại</label>
                            <input type="text" class="form-control" id="phone-number" name="phone-number"
                                   required pattern="^[0-9]{10}$"
                                   title="Số điện thoại phải có đúng 10 chữ số">
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                        <div class="form-group">
                            <label for="address">Địa chỉ</label>
                            <input type="text" class="form-control" id="address" name="address" required>
                        </div>
                        <div class="form-group">
                            <label for="note">Ghi chú</label>
                            <textarea class="form-control" id="note" name="note" rows="3"></textarea>
                        </div>

                        <div class="payment-methods">
                            <div class="payment-method selected" data-method="COD">
                                <label>
                                    <input type="radio" name="paymentMethod" value="COD" checked style="display: none;">
                                </label>
                                <h5>Thanh toán khi nhận hàng (COD)</h5>
                                <p>Thanh toán trực tiếp khi nhận hàng</p>
                            </div>
                        </div>

                        <div class="text-center mt-4">
                            <button type="submit" class="btn btn-primary">Đặt hàng</button>
                        </div>
                    </form>
                </div>

                <div class="col-lg-4">
                    <div class="order-summary">
                        <h4>Đơn hàng của bạn</h4>
                        <div class="summary-row">
                            <span>Tạm tính:</span>
                            <span>${cartDTO.subTotal}</span>
                        </div>
                        <div class="summary-row">
                            <span>Phí vận chuyển:</span>
                            <span>${cartDTO.shipping}</span>
                        </div>
                        <c:if test="${not empty cartDTO.voucher}">
                            <div class="summary-row">
                                <span>Giảm giá:</span>
                                <span>-${cartDTO.discount}</span>
                            </div>
                        </c:if>
                        <div class="summary-row total-row">
                            <span>Tổng cộng:</span>
                            <span>${cartDTO.totalPrice}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<!--================End Checkout Area =================-->

<jsp:include page="footer.jsp"/>

<script
        src="${pageContext.request.contextPath}/assests/vendors/jquery/jquery-3.2.1.min.js"></script>
<script
        src="${pageContext.request.contextPath}/assests/vendors/bootstrap/bootstrap.bundle.min.js"></script>
<script
        src="${pageContext.request.contextPath}/assests/vendors/skrollr.min.js"></script>
<script
        src="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.js"></script>
<script
        src="${pageContext.request.contextPath}/assests/vendors/nice-select/jquery.nice-select.min.js"></script>
<script
        src="${pageContext.request.contextPath}/assests/vendors/jquery.ajaxchimp.min.js"></script>
<script
        src="${pageContext.request.contextPath}/assests/vendors/mail-script.js"></script>
<script src="${pageContext.request.contextPath}/assests/js/main.js"></script>

<script>
    // Hàm hiển thị thông báo
    function showNotification(message, type = 'success') {
        const notification = document.getElementById('notification');
        const messageElement = document.getElementById('notification-message');

        // Xóa các class cũ
        notification.classList.remove('success', 'error', 'warning');
        // Thêm class mới
        notification.classList.add(type);

        // Set nội dung thông báo
        messageElement.textContent = message;

        // Hiển thị thông báo
        notification.style.display = 'block';

        // Tự động ẩn sau 3 giây
        setTimeout(() => {
            notification.style.animation = 'fadeOut 0.5s ease-out';
            setTimeout(() => {
                notification.style.display = 'none';
                notification.style.animation = 'slideIn 0.5s ease-out';
            }, 500);
        }, 3000);
    }

    // Xử lý form checkout
    document.getElementById('checkout-form').addEventListener('submit', function (e) {
        e.preventDefault();

        // Validate form
        if (!this.checkValidity()) {
            this.reportValidity();
            return;
        }

        // Submit form
        this.submit();
    });

    // Xử lý chọn phương thức thanh toán
    document.querySelectorAll('.payment-method').forEach(method => {
        method.addEventListener('click', function () {
            document.querySelectorAll('.payment-method').forEach(m => m.classList.remove('selected'));
            this.classList.add('selected');
            this.querySelector('input[type="radio"]').checked = true;
        });
    });

    // Hiển thị thông báo từ server
    <c:if test="${not empty message}">
    const message = '${message}';
    const lowerMessage = message.toLowerCase();

    if (lowerMessage.includes('lỗi') || lowerMessage.includes('thất bại') ||
        lowerMessage.includes('không hợp lệ') || lowerMessage.includes('không tồn tại')) {
        showNotification(message, 'error');
    } else if (lowerMessage.includes('cảnh báo') || lowerMessage.includes('chỉ còn') ||
        lowerMessage.includes('vui lòng')) {
        showNotification(message, 'warning');
    } else if (lowerMessage.includes('thành công') || lowerMessage.includes('đã') ||
        lowerMessage.includes('đã xóa') || lowerMessage.includes('đã cập nhật')) {
        showNotification(message, 'success');
    } else {
        showNotification(message, 'success');
    }
    </c:if>
</script>
</body>
</html>