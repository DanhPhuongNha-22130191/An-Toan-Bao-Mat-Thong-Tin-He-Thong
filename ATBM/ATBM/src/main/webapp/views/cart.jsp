<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Aroma Shop - Cart</title>
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
        .cart-item {
            position: relative;
            padding: 15px;
            border-bottom: 1px solid #eee;
        }

        .cart-item .remove-item {
            position: absolute;
            right: 15px;
            top: 15px;
            color: #ff4444;
            cursor: pointer;
        }

        .cart-item .stock-warning {
            color: #ff4444;
            font-size: 0.9em;
            margin-top: 5px;
        }

        .cart-item .quantity-control {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .cart-item .quantity-control button {
            background: #f1f1f1;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
        }

        .cart-item .quantity-control input {
            width: 50px;
            text-align: center;
        }

        .cart-summary {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
        }

        .cart-summary .summary-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .cart-summary .total-row {
            font-weight: bold;
            border-top: 1px solid #ddd;
            padding-top: 10px;
            margin-top: 10px;
        }

        .voucher-section {
            margin: 20px 0;
            padding: 15px;
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .voucher-section .voucher-input {
            display: flex;
            gap: 10px;
        }

        .voucher-section .voucher-input input {
            flex: 1;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .voucher-section .voucher-input button {
            padding: 8px 20px;
            background: #384aeb;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .voucher-section .voucher-info {
            margin-top: 10px;
            padding: 10px;
            background: #e8f0f2;
            border-radius: 4px;
            display: none;
        }

        .empty-cart {
            text-align: center;
            padding: 50px 0;
        }

        .empty-cart i {
            font-size: 48px;
            color: #ddd;
            margin-bottom: 20px;
        }

        .empty-cart p {
            color: #666;
            margin-bottom: 20px;
        }

        .empty-cart a {
            display: inline-block;
            padding: 10px 20px;
            background: #384aeb;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }

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
    </style>
</head>
<body>
<!--================Cart Area =================-->
<section class="cart_area">
    <div class="container">
        <div class="cart_inner">
            <!-- Phần thông báo -->
            <div id="notification" class="notification">
                <span id="notification-message"></span>
            </div>

            <!-- Phần chính của giỏ hàng -->
            <c:choose>
                <!-- Hiển thị khi giỏ hàng trống -->
                <c:when test="${empty cartDTO.items}">
                    <div class="empty-cart">
                        <i class="fas fa-shopping-cart"></i>
                        <h2>Giỏ hàng trống</h2>
                        <p>Bạn chưa có sản phẩm nào trong giỏ hàng</p>
                        <a href="${pageContext.request.contextPath}/products">Tiếp tục mua sắm</a>
                    </div>
                </c:when>
                <!-- Hiển thị khi có sản phẩm trong giỏ hàng -->
                <c:otherwise>
                    <!-- Form cập nhật giỏ hàng -->
                    <form id="updateCartForm" action="${pageContext.request.contextPath}/user/cart/update"
                          method="POST">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th scope="col">Sản phẩm</th>
                                    <th scope="col">Giá</th>
                                    <th scope="col">Số lượng</th>
                                    <th scope="col">Tổng</th>
                                    <th scope="col"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <!-- Danh sách sản phẩm trong giỏ hàng -->
                                <c:forEach var="item" items="${cartDTO.items}">
                                    <tr class="cart-item" data-product-id="${item.productId}">
                                        <!-- Thông tin sản phẩm -->
                                        <td>
                                            <div class="media">
                                                <div class="d-flex">
                                                    <img src="${pageContext.request.contextPath}/assests/img/${item.productImg}"
                                                         alt="${item.productName}" style="width: 100px;">
                                                </div>
                                                <div class="media-body">
                                                    <p>${item.productName}</p>
                                                </div>
                                            </div>
                                        </td>
                                        <td><h5>${item.productPrice}</h5></td>

                                        <!-- Điều chỉnh số lượng -->
                                        <td>
                                            <div class="quantity-control">
                                                <button type="button" onclick="decreaseQuantity(${item.productId})">-
                                                </button>
                                                <input type="number" name="cartItem-quantity" value="${item.quantity}"
                                                       min="1" max="${item.availableStock}"
                                                       data-product-id="${item.productId}">
                                                <button type="button" onclick="increaseQuantity(${item.productId})">+
                                                </button>
                                            </div>
                                        </td>
                                        <td><h5>${item.totalPrice}</h5></td>

                                        <!-- Nút xóa sản phẩm -->
                                        <td>
                                            <form action="${pageContext.request.contextPath}/user/cart/remove"
                                                  method="POST" style="display: inline;">
                                                <input type="hidden" name="productId" value="${item.productId}">
                                                <button type="submit" class="remove-item">
                                                    <i class="fas fa-trash"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <!-- Nút cập nhật giỏ hàng -->
                            <div class="text-right mb-3">
                                <button type="submit" class="btn btn-primary">Cập nhật giỏ hàng</button>
                            </div>
                        </div>
                    </form>

                    <!-- Phần tổng kết và thanh toán -->
                    <div class="row">
                        <!-- Mã giảm giá -->
                        <div class="col-lg-8">
                            <div class="voucher-section">
                                <h4>Mã giảm giá</h4>
                                <form action="${pageContext.request.contextPath}/user/voucher" method="POST"
                                      class="voucher-input">
                                    <input type="text" name="voucher-code" placeholder="Nhập mã giảm giá">
                                    <button type="submit">Áp dụng</button>
                                </form>
                                <!-- Thông tin voucher đã áp dụng -->
                                <c:if test="${not empty cartDTO.voucher}">
                                    <div class="voucher-info">
                                        <p>Mã giảm giá: ${cartDTO.voucher.voucherCode}</p>
                                        <p>Giảm giá: ${cartDTO.voucher.percentDecrease}</p>
                                        <form action="${pageContext.request.contextPath}/user/voucher/remove"
                                              method="POST" style="display: inline;">
                                            <button type="submit">Xóa mã</button>
                                        </form>
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <!-- Tổng kết đơn hàng -->
                        <div class="col-lg-4">
                            <div class="cart-summary">
                                <h4>Tổng đơn hàng</h4>
                                <div class="summary-row">
                                    <span>Tạm tính:</span>
                                    <span>${cartDTO.subTotal}</span>
                                </div>
                                <div class="summary-row">
                                    <span>Phí vận chuyển:</span>
                                    <span>${cartDTO.shipping}</span>
                                </div>
                                <div class="summary-row">
                                    <span>Giảm giá:</span>
                                    <span>-${cartDTO.discount}</span>
                                </div>
                                <div class="summary-row total-row">
                                    <span>Tổng cộng:</span>
                                    <span>${cartDTO.totalPrice}</span>
                                </div>
                                <!-- Nút điều hướng -->
                                <div class="checkout_btn_inner d-flex align-items-center mt-4">
                                    <a class="gray_btn" href="${pageContext.request.contextPath}/products">Tiếp tục mua
                                        sắm</a>
                                    <a class="primary-btn ml-2" href="${pageContext.request.contextPath}/user/checkout">Thanh
                                        toán</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</section>
<!--================End Cart Area =================-->

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
<script type="text/javascript">
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

    // Giảm số lượng
    function decreaseQuantity(productId) {
        const input = document.querySelector(`input[data-product-id="${productId}"]`);
        const currentValue = parseInt(input.value);
        if (currentValue > 1) {
            input.value = currentValue - 1;
        }
    }

    // Tăng số lượng
    function increaseQuantity(productId) {
        const input = document.querySelector(`input[data-product-id="${productId}"]`);
        const currentValue = parseInt(input.value);
        const maxValue = parseInt(input.getAttribute('max'));
        if (currentValue < maxValue) {
            input.value = currentValue + 1;
        }
    }


    // Xử lý form xóa sản phẩm
    document.querySelectorAll('form[action*="/cart/remove"]').forEach(form => {
        form.addEventListener('submit', function (e) {
            e.preventDefault();
            if (confirm('Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng?')) {
                this.submit();
            }
        });
    });

    // Xử lý form voucher
    document.querySelector('form[action*="/voucher"]').addEventListener('submit', function (e) {
        e.preventDefault();
        const voucherInput = this.querySelector('input[name="voucher-code"]');
        if (!voucherInput.value.trim()) {
            showNotification('Vui lòng nhập mã giảm giá', 'warning');
            return;
        }
        this.submit();
    });

    // Xử lý form xóa voucher
    document.querySelector('form[action*="/voucher/remove"]').addEventListener('submit', function (e) {
        e.preventDefault();
        if (confirm('Bạn có chắc muốn xóa mã giảm giá này?')) {
            this.submit();
        }
    });

    // Hiển thị thông báo từ server
    <c:if test="${not empty message}">
    // Phân loại thông báo dựa vào nội dung
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
        // Mặc định hiển thị dạng success
        showNotification(message, 'success');
    }
    </c:if>

    // Xử lý lỗi khi nhập số lượng không hợp lệ
    document.querySelectorAll('input[name="cartItem-quantity"]').forEach(input => {
        input.addEventListener('change', function () {
            const quantity = parseInt(this.value);

            if (isNaN(quantity) || quantity < 1) {
                showNotification('Số lượng sản phẩm không hợp lệ', 'error');
                this.value = 1;
            }
        });
    });
</script>
</body>
</html>