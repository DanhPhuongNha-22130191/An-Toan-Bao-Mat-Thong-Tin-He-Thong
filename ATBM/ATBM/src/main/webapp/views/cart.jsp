<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Aroma Shop - Cart</title>
    <link rel="icon" href="${pageContext.servletContext.contextPath}/assests/img/Fevicon.png" type="image/png">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/linericon/style.css">
    <link rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/assests/vendors/owl-carousel/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/nice-select/nice-select.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/nouislider/nouislider.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/css/style.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/css/main.css">
    <style>
        /* CSS styles giữ nguyên như trong query */
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
<jsp:include page="header.jsp"/>
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
                <c:when test="${empty cartDTO.items}">
                    <div class="empty-cart">
                        <i class="fas fa-shopping-cart"></i>
                        <h2>Giỏ hàng trống</h2>
                        <p>Bạn chưa có sản phẩm nào trong giỏ hàng</p>
                        <a href="${pageContext.servletContext.contextPath}/product/category">Tiếp tục mua sắm</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- Form cập nhật giỏ hàng -->
                    <form id="updateCartForm" method="post"
                          action="${pageContext.servletContext.contextPath}/user/cart/update">
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
                                <c:forEach var="item" items="${cartDTO.items}">
                                    <tr class="cart-item" data-product-id="${item.productId}">
                                        <td>
                                            <div class="media">
                                                <div class="d-flex">
                                                    <img src="${pageContext.request.contextPath}/admin/productImage?productId=${item.productId}"
                                                         alt="${item.productName}" style="width: 100px;">
                                                </div>
                                                <div class="media-body">
                                                    <p>${item.productName}</p>
                                                </div>
                                            </div>
                                        </td>
                                        <td><h5>${item.productPrice}</h5></td>
                                        <td>
                                            <div class="quantity-control">
                                                <button type="button" onclick="decreaseQuantity(${item.productId})">-
                                                </button>
                                                <label>
                                                    <input type="number" name="quantity-${item.productId}"
                                                           value="${item.quantity}" min="1" max="10000"
                                                           data-product-id="${item.productId}">
                                                </label>
                                                <input type="hidden" name="productId-quantity"
                                                       id="hidden-${item.productId}"
                                                       value="${item.productId}-${item.quantity}">
                                                <button type="button" onclick="increaseQuantity(${item.productId})">+
                                                </button>
                                            </div>
                                        </td>
                                        <td><h5>${item.totalPrice}</h5></td>
                                        <td>
                                            <button type="button" class="remove-item"
                                                    onclick="removeCartItem(${item.productId})">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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
                                <div class="voucher-input">
                                    <form action="${pageContext.servletContext.contextPath}/user/voucher/apply" method="post">
                                        <input type="text" id="voucherCode" name="voucher-code"
                                               placeholder="Nhập mã giảm giá">
                                        <button type="submit">Áp dụng</button>
                                    </form>
                                </div>
                                <c:if test="${not empty cartDTO.voucher}">
                                    <div class="voucher-info" id="voucherInfo">
                                        <form action="${pageContext.servletContext.contextPath}/user/voucher/remove"
                                              method="post" id="removeVoucherForm">
                                            <p>Mã giảm giá: ${cartDTO.voucher.voucherCode}</p>
                                            <p>Giảm giá: ${cartDTO.voucher.percentDecrease}</p>
                                            <button type="button" onclick="removeVoucher()">Xóa mã</button>
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
                                <div class="checkout_btn_inner d-flex align-items-center mt-4">
                                    <a class="gray_btn" href="${pageContext.servletContext.contextPath}/product/category">Tiếp tục mua
                                        sắm</a>
                                    <a class="primary-btn ml-2" href="${pageContext.servletContext.contextPath}/user/checkout">Thanh
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

<script src="${pageContext.servletContext.contextPath}/assests/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/vendors/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/vendors/skrollr.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/vendors/nice-select/jquery.nice-select.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/vendors/jquery.ajaxchimp.min.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/vendors/mail-script.js"></script>
<script src="${pageContext.servletContext.contextPath}/assests/js/main.js"></script>
<script type="text/javascript">
    /**
     * Hiển thị thông báo với màu sắc và nội dung tương ứng
     */
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

    /**
     * Cập nhật số lượng dựa trên delta (+1 hoặc -1)
     */
    function updateQuantity(productId, delta) {
        const input = document.querySelector('input[name="quantity-' + productId + '"]');
        if (!input) return;

        let currentValue = parseInt(input.value);
        const min = parseInt(input.getAttribute('min')) || 1;
        const max = parseInt(input.getAttribute('max')) || 999;

        if (isNaN(currentValue)) currentValue = min;

        input.value = Math.max(min, Math.min(max, currentValue + delta));
        hiddenInput = document.getElementById('hidden-' + productId);
        hiddenInput.value = productId + '-' + input.value;
    }

    /**
     * Giảm số lượng
     */
    function decreaseQuantity(productId) {
        updateQuantity(productId, -1);
    }

    /**
     * Tăng số lượng
     */
    function increaseQuantity(productId) {
        updateQuantity(productId, 1);
    }

    /**
     * Xử lý xóa sản phẩm khỏi giỏ hàng bằng fetch API
     */
    function removeCartItem(productId) {
        if (!confirm('Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng?')) {
            return;
        }

        fetch('${pageContext.servletContext.contextPath}/user/cart/remove', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'productId=' + productId
        })
            .then(response => {
                window.location.reload();
            })
            .catch(error => {
                showNotification('Có lỗi xảy ra khi xóa sản phẩm', 'error');
                console.error('Error:', error);
            });
    }

    function removeVoucher() {
        if (!confirm('Bạn có chắc muốn xóa mã giảm giá này?')) {
            return;
        }
        const removeVoucherForm = document.getElementById("removeVoucherForm");
        if (removeVoucherForm) {
            removeVoucherForm.submit();
        }
    }

    /**
     * Hiển thị thông báo từ phía server nếu có
     */
        <c:if test="${not empty message}">
    const message = '${message}';
    const lower = message.toLowerCase();
    if (lower.includes('lỗi') || lower.includes('thất bại') || lower.includes('không hợp lệ')) {
        showNotification(message, 'error');
    } else if (lower.includes('cảnh báo') || lower.includes('vui lòng') || lower.includes('chỉ còn')) {
        showNotification(message, 'warning');
    } else {
        showNotification(message, 'success');
    }
    </c:if>
</script>
</body>
</html>