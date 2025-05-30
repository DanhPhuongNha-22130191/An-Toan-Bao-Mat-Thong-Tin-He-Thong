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
          href="${pageContext.servletContext.contextPath}/assests/img/Fevicon.png"
          type="image/png">
    <link rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/assests/vendors/bootstrap/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/assests/vendors/fontawesome/css/all.min.css">
    <link rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/assests/vendors/themify-icons/themify-icons.css">
    <link rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/assests/vendors/linericon/style.css">
    <link rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/assests/vendors/owl-carousel/owl.theme.default.min.css">
    <link rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.css">
    <link rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/assests/vendors/nice-select/nice-select.css">
    <link rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/assests/vendors/nouislider/nouislider.min.css">
    <link rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/assests/css/style.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/css/main.css">
    <style>
        .signature-section {
            margin-top: 22px;
            padding: 18px;
            border: 1px solid #ddd;
            border-radius: 4px;
            background: #f8f9fa;
        }
        .signature-section h5 {
            font-size: 1.1rem;
            font-weight: 600;
            margin-bottom: 14px;
        }
        .hash-display {
            background: #fff;
            padding: 12px 14px 10px 14px;
            border: 1px solid #ddd;
            border-radius: 4px;
            margin-bottom: 10px;
            word-break: break-all;
            position: relative;
        }
        .copy-btn {
            position: absolute;
            right: 10px;
            top: 10px;
            background: none;
            border: none;
            color: #384aeb;
            cursor: pointer;
            font-size: 1em;
        }
        .copy-btn:hover {
            color: #2a3aeb;
        }
        .signature-input {
            width: 100%;
            padding: 8px 12px;
            border: 1px solid #bbb;
            border-radius: 4px;
            background: #fff;
            font-family: monospace;
            font-size: 1em;
            margin-bottom: 8px;
            transition: border-color 0.2s;
        }
        .signature-input:focus {
            border-color: #384aeb;
            outline: none;
        }
        .signature-status {
            margin-top: 10px;
            padding: 5px 12px;
            border-radius: 4px;
            display: inline-block;
            background: #fff3cd;
            color: #856404;
            font-size: 0.98em;
        }
        .signed {
            background: #d4edda !important;
            color: #155724 !important;
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
        .form-group label {
            font-weight: 500;
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
            background: #fff;
        }
        .payment-method.selected {
            border-color: #384aeb;
            background: #f8f9ff;
        }
        .payment-method h5 {
            margin-bottom: 4px;
        }
        .btn.btn-primary {
            background: #384aeb;
            border-color: #384aeb;
            padding: 10px 30px;
            font-size: 1.1em;
            border-radius: 25px;
        }
        .btn.btn-primary:hover {
            background: #2632a7;
            border-color: #2632a7;
        }
        @media (max-width: 991px) {
            .row {
                flex-direction: column;
            }
            .col-lg-6, .col-lg-4 {
                width: 100% !important;
                max-width: 100%;
            }
        }
        /* notification style giữ nguyên */
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
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
        }
        .notification.success { background-color: #28a745; }
        .notification.error { background-color: #dc3545; }
        .notification.warning { background-color: #ffc107; color: #000; }
        @keyframes slideIn {
            from { transform: translateX(100%); opacity: 0; }
            to { transform: translateX(0); opacity: 1; }
        }
        @keyframes fadeOut {
            from { opacity: 1; }
            to { opacity: 0; }
        }
    </style>
</head>
<body>
<jsp:include page="/views/header.jsp"/>
<!-- Phần thông báo -->
<div id="notification" class="notification">
    <span id="notification-message"></span>
</div>

<!-- ================ start banner area ================= -->
<section class="blog-banner-area" id="category">
    <div class="container h-50">
        <div class="blog-banner">
            <div class="text-center">
                <h1>Product Checkout</h1>
            </div>
        </div>
    </div>
</section>
<!-- ================ end banner area ================= -->


<div class="container">
    <section class="checkout_area section-margin--small">
        <div class="billing_details">
            <div class="row">
                <div class="col-lg-6 col-md-12">
                    <h3 style="margin-bottom:22px;">Thông tin giao hàng</h3>
                    <form id="checkout-form" action="${pageContext.request.contextPath}/user/checkout" method="post" autocomplete="off">
                        <div class="form-group mb-2">
                            <label for="full-name">Họ và tên</label>
                            <input type="text" class="form-control form-control-sm" id="full-name" name="full-name" required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="phone-number">Số điện thoại</label>
                            <input type="text" class="form-control form-control-sm" id="phone-number" name="phone-number"
                                   required pattern="^[0-9]{10}$"
                                   title="Số điện thoại phải có đúng 10 chữ số">
                        </div>
                        <div class="form-group mb-2">
                            <label for="email">Email</label>
                            <input type="email" class="form-control form-control-sm" id="email" name="email" required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="address">Địa chỉ</label>
                            <input type="text" class="form-control form-control-sm" id="address" name="address" required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="note">Ghi chú</label>
                            <textarea class="form-control form-control-sm" id="note" name="note" rows="2"></textarea>
                        </div>
                        <div class="payment-methods mb-2">
                            <div class="payment-method selected" data-method="COD">
                                <label>
                                    <input type="radio" name="paymentMethod" value="COD" checked style="display: none;">
                                </label>
                                <h5>Thanh toán khi nhận hàng (COD)</h5>
                                <p>Thanh toán trực tiếp khi nhận hàng</p>
                            </div>
                        </div>

                        <!-- BẮT ĐẦU HASH và CHỮ KÝ SỐ -->
                        <div class="signature-section mt-3">
                            <h5>Hash đơn hàng</h5>
                            <div class="hash-display" style="margin-bottom: 8px;">
                                <div>Mã hash đơn hàng:</div>
                                <div id="hash-value" style="font-size:0.97em;">Chưa tạo hash</div>
                                <button class="copy-btn" id="copyHashBtn" type="button" style="top:35px;"><i class="fas fa-copy"></i></button>
                            </div>
                            <button type="button" id="generateHashBtn" class="btn btn-outline-primary btn-sm" style="margin-bottom:10px;">Tạo hash</button>
                            <div id="hash-warning" class="text-danger" style="font-size: 0.96em; display:none;">Vui lòng nhập đầy đủ thông tin để tạo hash!</div>

                            <!-- Chỗ nhập chữ ký điện tử: chỉ hiện khi đã có hash -->
                            <div id="signature-area" style="margin-top:10px; display:none;">
                                <label for="signature" style="font-weight:500;">Chữ ký điện tử</label>
                                <input type="text" class="signature-input" name="signature" id="signature"
                                       placeholder="Nhập hoặc dán chữ ký số của bạn..." required>
                            </div>
                        </div>
                        <!-- KẾT THÚC HASH và CHỮ KÝ SỐ -->

                        <div class="text-center mt-4">
                            <button type="submit" class="btn btn-primary">Đặt hàng</button>
                        </div>
                    </form>
                </div>

                <div class="col-lg-4 col-md-12">
                    <div class="order-summary">
                        <h4>Đơn hàng của bạn</h4>
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Sản phẩm</th>
                                <th>Số lượng</th>
                                <th>Giá</th>
                                <th>Tổng</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${cartDTO.items}">
                                <tr>
                                    <td>${item.productName}</td>
                                    <td>${item.quantity}</td>
                                    <td>${item.productPrice}</td>
                                    <td>${item.totalPrice}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
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
    // Helper: validate all info required for hash
    function isAllOrderInfoFilled() {
        const name = document.getElementById('full-name').value.trim();
        const phone = document.getElementById('phone-number').value.trim();
        const email = document.getElementById('email').value.trim();
        const address = document.getElementById('address').value.trim();
        return name && phone && email && address;
    }

    // Generate hash từ thông tin đơn hàng và user
    async function generateOrderHash() {
        const name = document.getElementById('full-name').value.trim();
        const phone = document.getElementById('phone-number').value.trim();
        const email = document.getElementById('email').value.trim();
        const address = document.getElementById('address').value.trim();
        const note = document.getElementById('note').value.trim();
        const items = [
                <c:forEach var="item" items="${cartDTO.items}" varStatus="status">{
                productId: '${item.productId}',
                productName: '${item.productName}',
                quantity: ${item.quantity},
                price: '${item.productPrice}',
                total: '${item.totalPrice}'
            }${!status.last ? ',' : ''}</c:forEach>
        ];
        const orderData = {
            customer: { name, phone, email, address, note },
            items: items,
            summary: {
                subTotal: '${cartDTO.subTotal}',
                shipping: '${cartDTO.shipping}',
                discount: '${cartDTO.discount}',
                totalPrice: '${cartDTO.totalPrice}'
            }
        };
        const orderString = JSON.stringify(orderData);
        const encoder = new TextEncoder();
        const data = encoder.encode(orderString);
        const hashBuffer = await crypto.subtle.digest('SHA-256', data);
        const hashArray = Array.from(new Uint8Array(hashBuffer));
        return hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
    }

    let hashCreated = false; // trạng thái đã tạo hash hay chưa

    document.getElementById('generateHashBtn').addEventListener('click', async function () {
        const warning = document.getElementById('hash-warning');
        const hashValueDiv = document.getElementById('hash-value');
        if (!isAllOrderInfoFilled()) {
            warning.style.display = '';
            hashValueDiv.textContent = "Chưa tạo hash";
            document.getElementById('signature-area').style.display = 'none';
            hashCreated = false;
            return;
        }
        warning.style.display = 'none';
        hashValueDiv.textContent = "Đang tạo hash...";
        const hash = await generateOrderHash();
        hashValueDiv.textContent = hash;
        // Hiện input chữ ký điện tử khi đã có hash
        document.getElementById('signature-area').style.display = '';
        document.getElementById('signature').focus();
        hashCreated = true;
    });

    document.getElementById('copyHashBtn').addEventListener('click', function () {
        const hash = document.getElementById('hash-value').textContent;
        if (!hash || hash === "Chưa tạo hash" || hash === "Đang tạo hash...") {
            showNotification("Bạn cần tạo hash trước!", "warning");
            return;
        }
        navigator.clipboard.writeText(hash)
            .then(() => showNotification('Đã sao chép mã hash!', 'success'))
            .catch(() => showNotification('Lỗi khi sao chép mã hash', 'error'));
    });

    // Xử lý thông báo
    function showNotification(message, type = 'success') {
        const notification = document.getElementById('notification');
        const messageElement = document.getElementById('notification-message');
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
    }

    // Đặt hàng: kiểm tra bắt buộc tạo hash và ký đơn
    document.getElementById('checkout-form').addEventListener('submit', function (e) {
        const hashValue = document.getElementById('hash-value').textContent;
        const signatureDisplay = document.getElementById('signature-area').style.display;
        const signatureValue = document.getElementById('signature').value.trim();

        // Chưa tạo hash (chưa bấm nút hoặc chưa đủ thông tin)
        if (!hashValue || hashValue === "Chưa tạo hash" || hashValue === "Đang tạo hash...") {
            showNotification("Bạn cần tạo hash cho đơn hàng trước khi đặt hàng!", "warning");
            e.preventDefault();
            return;
        }
        // Đã tạo hash nhưng chưa ký đơn
        if (signatureDisplay === 'none' || !signatureValue) {
            showNotification("Bạn cần ký xác nhận đơn bằng chữ ký điện tử trước khi đặt hàng!", "warning");
            e.preventDefault();
            return;
        }
    });

    // Chọn phương thức thanh toán
    document.querySelectorAll('.payment-method').forEach(method => {
        method.addEventListener('click', function () {
            document.querySelectorAll('.payment-method').forEach(m => m.classList.remove('selected'));
            this.classList.add('selected');
            this.querySelector('input[type="radio"]').checked = true;
        });
    });

    <c:if test="${not empty message}">
    const message = '${message}';
    const lowerMessage = message.toLowerCase();
    if (lowerMessage.includes('lỗi') || lowerMessage.includes('thất bại') ||
        lowerMessage.includes('không hợp lệ') || lowerMessage.includes('không tồn tại')) {
        showNotification(message, 'error');
    } else if (lowerMessage.includes('cảnh báo') || lowerMessage.includes('chỉ còn') ||
        lowerMessage.includes('vui lòng')) {
        showNotification(message, 'warning');
    } else {
        showNotification(message, 'success');
    }
    </c:if>
</script>
</body>
</html>