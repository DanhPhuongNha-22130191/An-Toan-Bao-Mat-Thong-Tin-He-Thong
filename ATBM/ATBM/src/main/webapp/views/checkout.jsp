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
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          crossorigin="anonymous">
    <!-- FontAwesome 6 CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <!-- Themify Icons CDN -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/themify-icons@1.0.1/css/themify-icons.css">
    <!-- Owl Carousel CSS CDN -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.carousel.min.css"
          crossorigin="anonymous"/>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/assets/owl.theme.default.min.css"
          crossorigin="anonymous"/>
    <!-- Nice Select CSS CDN -->
    <!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/css/nice-select.min.css" crossorigin="anonymous" /> -->
    <!-- Custom CSS -->
    <%--    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/css/style.css">--%>
    <%--    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/css/main.css">--%>
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
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"/>
<c:set var="checkoutUrl" value="${contextPath}/user/checkout"/>
<c:set var="locationUrl" value="${contextPath}/locations"/>

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
                    <form id="checkout-form" action="${checkoutUrl}" method="post"
                          autocomplete="on">
                        <div class="form-group mb-2">
                            <label for="full-name">Họ và tên</label>
                            <input type="text" class="form-control form-control-sm" id="full-name" name="recipientName"
                                   required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="phone-number">Số điện thoại</label>
                            <input type="text" class="form-control form-control-sm" id="phone-number"
                                   name="phoneNumber"
                                   required pattern="^[0-9]{10}$"
                                   title="Số điện thoại phải có đúng 10 chữ số">
                        </div>
                        <div class="form-group mb-2">
                            <label for="email">Email</label>
                            <input type="email" class="form-control form-control-sm" id="email" name="email" required>
                        </div>
                        <div class="form-group mb-3">
                            <label for="province">Tỉnh / Thành phố</label>
                            <select id="province" name="provinceId" class="form-control" required>
                                <option value="">-- Chọn tỉnh/thành --</option>
                            </select>
                        </div>
                        <div class="form-group mb-3">
                            <label for="district">Quận / Huyện</label>
                            <select id="district" name="districtId" class="form-control" required disabled>
                                <option value="">-- Chọn quận/huyện --</option>
                            </select>
                        </div>
                        <div class="form-group mb-3">
                            <label for="ward">Phường / Xã</label>
                            <select id="ward" name="wardId" class="form-control" required disabled>
                                <option value="">-- Chọn phường/xã --</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <button type="button" id="calc-shipping-btn" class="btn btn-outline-primary btn-sm">
                                Tính phí vận chuyển
                            </button>
                            <span id="shipping-calc-status" class="ms-2 text-success"></span>
                        </div>
                        <div class="form-group mb-2">
                            <label for="addressLine">Địa chỉ chi tiết (số nhà, tên đường,...)</label>
                            <input type="text" class="form-control form-control-sm" id="addressLine" name="addressLine"
                                   required>
                        </div>
                        <div class="form-group mb-2">
                            <label for="note">Ghi chú</label>
                            <textarea class="form-control form-control-sm" id="note" name="note" rows="2"></textarea>
                        </div>
                        <div class="form-group mb-3">
                            <label class="mb-2 fw-bold">Phương thức giao hàng</label>
                            <div class="d-flex gap-3">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="shippingMethod" id="shippingFast" value="FAST" checked>
                                    <label class="form-check-label" for="shippingFast">
                                        Giao hàng nhanh
                                    </label>
                                </div>
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="shippingMethod" id="shippingExpress" value="EXPRESS">
                                    <label class="form-check-label" for="shippingExpress">
                                        Giao hỏa tốc
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group mb-3">
                            <label class="mb-2 fw-bold">Phương thức thanh toán</label>
                            <div class="d-flex gap-3">
                                <div class="form-check">
                                    <input class="form-check-input" type="radio" name="paymentMethod" id="paymentCOD" value="CASH" checked>
                                    <label class="form-check-label" for="paymentCOD">
                                        Thanh toán khi nhận hàng (COD)
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="signature-section mt-3">
                            <h5>Thông tin ký đơn</h5>
                            <div class="hash-display" style="margin-bottom: 8px;">
                                <div>Thông tin đơn hàng:</div>
                                <div id="hash-value" style="font-size:0.97em;">Chưa tạo thông tin</div>
                                <button class="copy-btn" id="copyHashBtn" type="button" style="top:35px;" title="Copy" disabled><i class="fas fa-copy"></i></button>
                                <button class="copy-btn" id="exportHashBtn" type="button" style="top:65px;" title="Xuất file" disabled><i class="fas fa-file-export"></i></button>
                            </div>
                            <button type="button" id="generateHashBtn" class="btn btn-outline-primary btn-sm"
                                    style="margin-bottom:10px;">Tạo thông tin đơn hàng
                            </button>
                            <div id="hash-warning" class="text-danger" style="font-size: 0.96em; display:none;">Vui lòng
                                nhập đầy đủ thông tin để tạo thông tin đơn hàng!
                            </div>

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
                            <c:forEach var="item" items="${cart.items}">
                                <tr>
                                    <td>${item.nameSnapshot}</td>
                                    <td>${item.quantity}</td>
                                    <td>${item.priceSnapshot}</td>
                                    <td>${item.totalPrice}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="summary-row">
                            <span>Tạm tính:</span>
                            <span>${cart.totalPriceStringWithCurrency}</span>
                        </div>
                        <div class="summary-row">
                            <span>Phí vận chuyển:</span>
                            <span id="shipping-fee">Vui lòng chọn địa chỉ</span>
                        </div>
                        <div class="summary-row total-row">
                            <span>Tổng cộng:</span>
                            <span id="grand-total" data-cart-total="${cart.totalPrice}">
                                ${cart.totalPriceStringWithCurrency}
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="footer.jsp"/>

<!-- jQuery 3.6.4 (tương thích tốt với plugin cũ) -->
<script src="https://code.jquery.com/jquery-3.6.4.min.js" crossorigin="anonymous"></script>
<!-- Nice Select plugin -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-nice-select/1.1.0/js/jquery.nice-select.min.js" crossorigin="anonymous"></script> -->
<!-- Owl Carousel plugin -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.3.4/owl.carousel.min.js"
        crossorigin="anonymous"></script>
<!-- Skrollr plugin -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/skrollr/0.6.30/skrollr.min.js" crossorigin="anonymous"></script>
<!-- AjaxChimp plugin -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-ajaxchimp/1.3.0/jquery.ajaxchimp.min.js"
        crossorigin="anonymous"></script>
<!-- Bootstrap 5 JS Bundle (gồm Popper) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
<!-- Custom JS -->
<%--<script src="${pageContext.servletContext.contextPath}/assests/js/main.js"></script>--%>
<script>
    // JS location và các hàm chính
    (function () {
        const provinceSelect = document.getElementById("province");
        const districtSelect = document.getElementById("district");
        const wardSelect = document.getElementById("ward");
        let locationData = [];

        function renderOptions(select, items, placeholder) {
            select.innerHTML = `<option value="">${placeholder}</option>`;
            if (!Array.isArray(items) || items.length === 0) {
                select.innerHTML += `<option value="">Không có dữ liệu</option>`;
                select.disabled = true;
                return;
            }
            items.forEach(item => {
                if (item.id && item.name) {
                    const option = document.createElement("option");
                    option.value = item.id;
                    option.textContent = item.name;
                    select.appendChild(option);
                }
            });
            select.disabled = false;
        }

        async function loadProvinces() {
            try {
                provinceSelect.disabled = true;
                provinceSelect.innerHTML = `<option>Đang tải...</option>`;
                const response = await fetch("${locationUrl}");
                const text = await response.text();
                let raw;
                try {
                    raw = JSON.parse(text);
                } catch (e) {
                    console.error('Không parse được JSON:', e, text);
                    renderOptions(provinceSelect, [], "Lỗi dữ liệu location");
                    return;
                }
                // Xử lý nhiều trường hợp trả về
                if (Array.isArray(raw)) {
                    locationData = raw;
                } else if (raw && Array.isArray(raw.data)) {
                    locationData = raw.data;
                } else if (raw && Array.isArray(raw.provinces)) {
                    locationData = raw.provinces;
                } else {
                    locationData = [];
                }
                renderOptions(provinceSelect, locationData, "-- Chọn tỉnh/thành --");
                districtSelect.disabled = true;
                wardSelect.disabled = true;
            } catch (e) {
                console.error('Lỗi khi load location:', e);
                renderOptions(provinceSelect, [], "Không thể tải tỉnh/thành");
            }
        }

        function loadDistricts(provinceId) {
            const province = locationData.find(p => p.id == provinceId);
            renderOptions(districtSelect, province?.districts, "-- Chọn quận/huyện --");
            wardSelect.innerHTML = `<option value="">-- Chọn phường/xã --</option>`;
            wardSelect.disabled = true;
        }

        function loadWards(provinceId, districtId) {
            const province = locationData.find(p => p.id == provinceId);
            const district = province?.districts.find(d => d.id == districtId);
            renderOptions(wardSelect, district?.wards, "-- Chọn phường/xã --");
        }

        provinceSelect.addEventListener("change", function () {
            loadDistricts(this.value);
        });
        districtSelect.addEventListener("change", function () {
            loadWards(provinceSelect.value, this.value);
        });

        document.getElementById('calc-shipping-btn').addEventListener('click', function () {
            document.getElementById('shipping-calc-status').textContent = '';
            calculateShippingFee().then(() => {
                document.getElementById('shipping-calc-status').textContent = 'Đã tính phí!';
                setTimeout(() => {
                    document.getElementById('shipping-calc-status').textContent = '';
                }, 2000);
            });
        });

        // Payment method UI
        document.querySelectorAll('.payment-method').forEach(method => {
            method.addEventListener('click', function () {
                document.querySelectorAll('.payment-method').forEach(m => m.classList.remove('selected'));
                this.classList.add('selected');
                this.querySelector('input[type="radio"]').checked = true;
            });
        });

        // Shipping fee calculation
        async function calculateShippingFee() {
            const provinceId = provinceSelect.value;
            const districtId = districtSelect.value;
            const wardId = wardSelect.value;
            const shippingMethod = document.querySelector('input[name="shippingMethod"]:checked')?.value || 'COD';
            if (!provinceId || !districtId || !wardId) {
                document.getElementById("shipping-fee").textContent = "Vui lòng chọn địa chỉ";
                return;
            }
            document.getElementById("shipping-fee").textContent = "Đang tính...";
            try {
                const response = await fetch("${locationUrl}", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body: new URLSearchParams({provinceId, districtId, wardId, shippingMethod})
                });
                const data = await response.json();
                const fee = data.shippingFee || 0;
                document.getElementById("shipping-fee").textContent = fee.toLocaleString("vi-VN", {
                    style: "currency",
                    currency: "VND"
                });
                const cartTotal = parseFloat(document.getElementById("grand-total").getAttribute("data-cart-total"));
                const grandTotal = cartTotal + fee;
                document.getElementById("grand-total").textContent = grandTotal.toLocaleString("vi-VN", {
                    style: "currency",
                    currency: "VND"
                });
            } catch (error) {
                console.error(error);
                document.getElementById("shipping-fee").textContent = "Không thể tính phí";
            }
        }

        // Initial load
        loadProvinces();
    })();

    // Hiển thị ngày đặt và thông tin người dùng khi nhập
    document.addEventListener('DOMContentLoaded', function() {
        // Ngày đặt
        const now = new Date();
        const dateStr = now.toLocaleDateString('vi-VN');
        document.getElementById('order-date').textContent = dateStr;
        // Cập nhật thông tin người dùng khi nhập
        const nameInput = document.getElementById('full-name');
        const emailInput = document.getElementById('email');
        const phoneInput = document.getElementById('phone-number');
        const orderRecipient = document.getElementById('order-recipient');
        const orderEmail = document.getElementById('order-email');
        const orderPhone = document.getElementById('order-phone');
        function syncOrderInfo() {
            orderRecipient.textContent = nameInput.value;
            orderEmail.textContent = emailInput.value;
            orderPhone.textContent = phoneInput.value;
        }
        nameInput.addEventListener('input', syncOrderInfo);
        emailInput.addEventListener('input', syncOrderInfo);
        phoneInput.addEventListener('input', syncOrderInfo);
        syncOrderInfo();
    });
    // Xuất dữ liệu sản phẩm từ JSP sang JS
    var orderItems = [
        <c:forEach var="item" items="${cart.items}" varStatus="loop">
            {
                name: '<c:out value="${item.nameSnapshot}"/>',
                quantity: <c:out value="${item.quantity}"/>,
                totalPrice: '<c:out value="${item.totalPrice}"/>'
            }<c:if test="${!loop.last}">,</c:if>
        </c:forEach>
    ];
    var orderTotal = '<c:out value="${cart.totalPrice}"/>';

    // Hàm tạo thông tin đơn hàng để ký số
    function getOrderInfoString() {
        var now = new Date();
        var dateStr = formatDateVN(now); // chuẩn dd/MM/yyyy

        var name = document.getElementById('full-name').value.trim();
        var phone = document.getElementById('phone-number').value.trim();
        var address = document.getElementById('addressLine').value.trim();
        var province = document.getElementById('province');
        var district = document.getElementById('district');
        var ward = document.getElementById('ward');

        var provinceName = province.options[province.selectedIndex]?.text || '';
        var districtName = district.options[district.selectedIndex]?.text || '';
        var wardName = ward.options[ward.selectedIndex]?.text || '';

        var items = '';
        for (var i = 0; i < orderItems.length; i++) {
            var item = orderItems[i];
            items += '• ' + item.name + ' x' + item.quantity + ': ' + formatCurrency(item.totalPrice) + '\n';
        }

        var total = formatCurrency(orderTotal);

        return 'Ngày đặt: ' + dateStr + '\n' +
            'Họ tên: ' + name + '\n' +
            'SĐT: ' + phone + '\n' +
            'Địa chỉ: ' + address + ', ' + wardName + ', ' + districtName + ', ' + provinceName + '\n' +
            'Sản phẩm:\n' + items +
            'Tổng tiền: ' + total;
    }

    function formatDateVN(date) {
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();
        return day+'/'+month+'/'+year;
    }

    function formatCurrency(amount) {
        return parseFloat(amount).toFixed(1) + ' VNĐ';
    }
    // Nút tạo thông tin đơn hàng
    document.getElementById('generateHashBtn').addEventListener('click', function() {
        var name = document.getElementById('full-name').value.trim();
        var phone = document.getElementById('phone-number').value.trim();
        var address = document.getElementById('addressLine').value.trim();
        var province = document.getElementById('province').value;
        var district = document.getElementById('district').value;
        var ward = document.getElementById('ward').value;

        if (!name || !phone || !address || !province || !district || !ward) {
            document.getElementById('hash-warning').style.display = 'block';
            document.getElementById('hash-warning').textContent = 'Vui lòng nhập đầy đủ thông tin để tạo thông tin đơn hàng!';
            return;
        }
        document.getElementById('hash-warning').style.display = 'none';

        var info = getOrderInfoString();
        document.getElementById('hash-value').textContent = info;

        // Cho phép copy, xuất file, ký đơn
        document.getElementById('copyHashBtn').disabled = false;
        document.getElementById('exportHashBtn').disabled = false;
        document.getElementById('signature-area').style.display = 'block';
    });
    // Nút copy
    document.getElementById('copyHashBtn').addEventListener('click', function() {
        var info = document.getElementById('hash-value').textContent;
        navigator.clipboard.writeText(info).then(function() {
            alert('Đã copy thông tin đơn hàng!');
        });
    });
    // Nút xuất file
    document.getElementById('exportHashBtn').addEventListener('click', function() {
        var info = document.getElementById('hash-value').textContent;
        var blob = new Blob([info], { type: 'text/plain' });
        var url = URL.createObjectURL(blob);
        var a = document.createElement('a');
        a.href = url;
        a.download = 'order-info.txt';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
    });

    // Reset trạng thái khi thay đổi thông tin
    function resetOrderInfoState() {
        document.getElementById('hash-value').textContent = 'Chưa tạo thông tin';
        document.getElementById('copyHashBtn').disabled = true;
        document.getElementById('exportHashBtn').disabled = true;
        document.getElementById('signature-area').style.display = 'none';
        document.getElementById('hash-warning').style.display = 'none';
    }
    // Gắn sự kiện reset cho các trường thông tin
    ['full-name', 'phone-number', 'addressLine', 'province', 'district', 'ward'].forEach(function(id) {
        var el = document.getElementById(id);
        if (el) {
            el.addEventListener('input', resetOrderInfoState);
            el.addEventListener('change', resetOrderInfoState);
        }
    });
</script>
</body>
</html>