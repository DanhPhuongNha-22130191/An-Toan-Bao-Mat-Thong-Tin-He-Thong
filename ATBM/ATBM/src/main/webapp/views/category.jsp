<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cửa Hàng Aroma - Danh Mục</title>

    <!-- ICON TRÊN TAB TRÌNH DUYỆT -->
    <link rel="icon" href="${pageContext.servletContext.contextPath}/assests/img/Fevicon.png" type="image/png">

    <!-- LINK CSS TỪ THƯ VIỆN VÀ CÁ NHÂN -->
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/linericon/style.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/owl-carousel/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/nice-select/nice-select.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/vendors/nouislider/nouislider.min.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/assests/css/style.css">
</head>
<body>

<!-- CHÈN HEADER -->
<jsp:include page="/views/header.jsp"/>

<!-- KHU VỰC DANH MỤC SẢN PHẨM -->
<section class="section-margin--small mb-5">
    <div class="container">
        <div class="row">

            <!-- THANH BÊN LỌC SẢN PHẨM -->
            <div class="col-xl-3 col-lg-4 col-md-5">
                <form id="filterForm" method="post">

                    <!-- BỘ LỌC THƯƠNG HIỆU -->
                    <div class="sidebar-categories">
                        <div class="head">Thương hiệu</div>
                        <ul class="main-categories">
                            <li class="common-filter">
                                <ul>
                                    <!-- LẶP QUA CÁC THƯƠNG HIỆU -->
                                    <c:forEach var="brand" items="${brands}">
                                        <li class="filter-list">
                                            <!-- CHECKBOX THƯƠNG HIỆU -->
                                            <input class="pixel-checkbox" type="checkbox" name="brandId[]"
                                                   id="brand${brand.brandId}" value="${brand.brandId}"
                                                   <c:if test="${fn:contains(paramValues['brandId[]'], brand.brandId)}">checked</c:if> >
                                            <label for="brand${brand.brandId}">${brand.name}</label>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </ul>
                    </div>

                    <!-- BỘ LỌC DÂY ĐEO -->
                    <div class="sidebar-filter">
                        <div class="top-filter-head">Bộ lọc sản phẩm</div>
                        <div class="common-filter">
                            <div class="head">Chất liệu dây</div>
                            <ul>
                                <!-- LẶP QUA CÁC LOẠI DÂY -->
                                <c:forEach var="strap" items="${straps}">
                                    <li class="filter-list">
                                        <!-- CHECKBOX CHẤT LIỆU DÂY -->
                                        <input class="pixel-checkbox" type="checkbox" name="strapId[]"
                                               id="strap${strap.strapId}" value="${strap.strapId}"
                                               <c:if test="${fn:contains(paramValues['strapId[]'], strap.strapId)}">checked</c:if> >
                                        <label for="strap${strap.strapId}">${strap.material}</label>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>

                        <!-- BỘ LỌC GIÁ TIỀN -->
                        <div class="common-filter">
                            <div class="head">Giá</div>
                            <div class="price-range-area">
                                <div id="price-range"></div>
                                <div class="value-wrapper d-flex">
                                    <div class="price">Giá:</div>
                                    <div id="lower-value">${param.minPrice != null ? param.minPrice : minPrice}</div>
                                    <span> ₫</span>
                                    <div class="to">đến</div>
                                    <div id="upper-value">${param.maxPrice != null ? param.maxPrice : maxPrice}</div>
                                    <span> ₫</span>
                                </div>
                                <!-- INPUT ẨN GIÁ TỐI THIỂU / TỐI ĐA -->
                                <input type="hidden" id="minPriceInput" name="minPrice"
                                       value="${param.minPrice != null ? param.minPrice : minPrice}"
                                       min="${minPrice}" max="${maxPrice}">
                                <input type="hidden" id="maxPriceInput" name="maxPrice"
                                       value="${param.maxPrice != null ? param.maxPrice : maxPrice}"
                                       min="${minPrice}" max="${maxPrice}">
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <!-- DANH SÁCH SẢN PHẨM -->
            <div class="col-xl-9 col-lg-8 col-md-7">

                <!-- HIỂN THỊ SPINNER KHI ĐANG TẢI -->
                <div id="loading-spinner" style="display: none; text-align:center; margin:20px;">
                    <div class="spinner-border text-primary" role="status">
                        <span class="sr-only">Đang tải...</span>
                    </div>
                </div>

                <!-- KHU VỰC HIỂN THỊ SẢN PHẨM -->
                <section class="lattest-product-area pb-40 category-list">
                    <div id="product-list" class="row">
                        <!-- LẶP QUA CÁC SẢN PHẨM -->
                        <c:forEach var="product" items="${products}">
                            <div class="col-md-6 col-lg-4">
                                <div class="card text-center card-product">
                                    <div class="card-product__img">
                                        <div class="product-image-wrapper">
                                            <!-- HÌNH ẢNH SẢN PHẨM -->
                                            <img src="${pageContext.request.contextPath}/product-image/${product.productId}"
                                                 alt="${product.name}"
                                                 class="product-image"
                                                 onerror="this.style.display='none'; this.parentElement.querySelector('.product-placeholder').style.display='flex';">
                                            <div class="product-placeholder" style="display: none;">
                                                <i class="fas fa-image"></i>
                                            </div>
                                        </div>
                                        <ul class="card-product__imgOverlay">
                                            <li>
                                                <!-- NÚT THÊM VÀO GIỎ -->
                                                <button onclick="addToCart(${product.productId})"><i class="ti-shopping-cart"></i></button>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="card-body">
                                        <p>${product.description}</p>
                                        <h4 class="card-product__title">
                                            <!-- LINK CHI TIẾT SẢN PHẨM -->
                                            <a href="${pageContext.servletContext.contextPath}/product?action=details&id=${product.productId}">
                                                    ${product.name}
                                            </a>
                                        </h4>
                                        <!-- GIÁ SẢN PHẨM -->
                                        <p class="card-product__price">${product.price} ₫</p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </section>

            </div>
        </div>
    </div>
</section>

<!-- SCRIPT CÁC THƯ VIỆN -->
<script src="${pageContext.request.contextPath}/assests/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/nice-select/jquery.nice-select.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/nouislider/nouislider.min.js"></script>

<!-- TRUYỀN BIẾN CONTEXTPATH SANG JAVASCRIPT -->
<script>
    window.contextPath = '${pageContext.request.contextPath}';
</script>

<!-- HÀM THÊM SẢN PHẨM VÀO GIỎ HÀNG BẰNG FETCH -->
<script>
    function addToCart(productId) {
        fetch(`${window.contextPath}/user/cart/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                productId: productId,
                quantity: 1 // MẶC ĐỊNH SỐ LƯỢNG LÀ 1
            }),
            credentials: 'same-origin' // ĐỂ GỬI COOKIE PHIÊN
        })
            .then(response => {
                if (response.redirected) {
                    // CHUYỂN HƯỚNG KHI CẦN
                    window.location.href = response.url;
                } else {
                    return response.text();
                }
            })
            .then(data => {
                if (data) {
                    alert('Sản phẩm đã được thêm vào giỏ hàng!');
                }
            })
            .catch(error => {
                console.error('LỖI KHI THÊM VÀO GIỎ HÀNG:', error);
                alert("Có lỗi xảy ra. Vui lòng thử lại.");
            });
    }
</script>

<!-- SCRIPT CUSTOM XỬ LÝ FILTER -->
<script src="${pageContext.request.contextPath}/assests/js/filterCategory.js"></script>

</body>
</html>