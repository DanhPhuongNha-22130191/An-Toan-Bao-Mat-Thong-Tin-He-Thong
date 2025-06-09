<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cửa Hàng Aroma - Danh Mục</title>
    <link rel="icon" href="${pageContext.servletContext.contextPath}/assests/img/Fevicon.png" type="image/png">

    <!-- CSS -->
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
<!-- Header -->
<jsp:include page="/views/header.jsp"/>

<section class="section-margin--small mb-5">
    <div class="container">
        <div class="row">

            <!-- Sidebar filter -->
            <div class="col-xl-3 col-lg-4 col-md-5">
                <form id="filterForm" action="category" method="get">

                    <!-- Brand Filter -->
                    <div class="sidebar-categories">
                        <div class="head">Thương hiệu</div>
                        <ul class="main-categories">
                            <li class="common-filter">
                                <ul>
                                    <c:forEach var="brand" items="${brands}">
                                        <li class="filter-list">
                                            <input class="pixel-checkbox" type="checkbox" name="brandId[]"
                                                   id="brand${brand.brandId}" value="${brand.brandId}"
                                                   <c:if test="${fn:contains(paramValues['brandId'], brand.brandId)}">checked</c:if> >
                                            <label for="brand${brand.brandId}">${brand.name}</label>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </ul>
                    </div>

                    <!-- Strap Filter -->
                    <div class="sidebar-filter">
                        <div class="top-filter-head">Bộ lọc sản phẩm</div>
                        <div class="common-filter">
                            <div class="head">Chất liệu dây</div>
                            <ul>
                                <c:forEach var="strap" items="${straps}">
                                    <li class="filter-list">
                                        <input class="pixel-checkbox" type="checkbox" name="strapId[]"
                                               id="strap${strap.strapId}" value="${strap.strapId}"
                                               <c:if test="${fn:contains(paramValues['strapId'], strap.strapId)}">checked</c:if> >
                                        <label for="strap${strap.strapId}">${strap.material}</label>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>

                        <!-- Price Range -->
                        <div class="common-filter">
                            <div class="head">Giá</div>
                            <div class="price-range-area">
                                <div id="price-range"></div>
                                <div class="value-wrapper d-flex">
                                    <div class="price">Giá:</div>
                                    <span>$</span>
                                    <div id="lower-value">0</div>
                                    <div class="to">đến</div>
                                    <span>$</span>
                                    <div id="upper-value">1000</div>
                                </div>
                                <input type="hidden" id="minPriceInput" name="minPrice"
                                       value="${param.minPrice != null ? param.minPrice : minPrice}">
                                <input type="hidden" id="maxPriceInput" name="maxPrice"
                                       value="${param.maxPrice != null ? param.maxPrice : maxPrice}">
                            </div>
                        </div>
                    </div>
                </form>
            </div>

            <!-- Product List -->
            <div class="col-xl-9 col-lg-8 col-md-7">

                <!-- Spinner -->
                <div id="loading-spinner" style="display: none; text-align:center; margin:20px;">
                    <div class="spinner-border text-primary" role="status">
                        <span class="sr-only">Đang tải...</span>
                    </div>
                </div>

                <section class="lattest-product-area pb-40 category-list">
                    <div id="product-list" class="row">
                        <c:forEach var="product" items="${products}">
                            <div class="col-md-6 col-lg-4">
                                <div class="card text-center card-product">
                                    <div class="card-product__img">
                                        <div class="product-image-wrapper">
                                            <img src="${pageContext.request.contextPath}/admin/productImage?productId=${product.productId}"
                                                 alt="${product.name}"
                                                 class="product-image"
                                                 onerror="this.style.display='none'; this.parentElement.querySelector('.product-placeholder').style.display='flex';">
                                            <div class="product-placeholder" style="display: none;">
                                                <i class="fas fa-image"></i>
                                            </div>
                                        </div>

                                        <ul class="card-product__imgOverlay">
                                            <li><button><i class="ti-search"></i></button></li>
                                            <li><button><i class="ti-shopping-cart"></i></button></li>
                                            <li><button><i class="ti-heart"></i></button></li>
                                        </ul>
                                    </div>
                                    <div class="card-body">
                                        <p>${product.description}</p>
                                        <h4 class="card-product__title">
                                            <a href="${pageContext.servletContext.contextPath}/product?action=details&id=${product.productId}">
                                                    ${product.name}
                                            </a>
                                        </h4>
                                        <p class="card-product__price">$${product.price}</p>
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

<!-- Scripts: vendors -->
<script src="${pageContext.request.contextPath}/assests/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/nice-select/jquery.nice-select.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/nouislider/nouislider.min.js"></script>

<!-- Pass contextPath JS biến toàn cục -->
<script>
    window.contextPath = '${pageContext.request.contextPath}';
</script>

<!-- Custom JS file -->
<script src="${pageContext.request.contextPath}/assests/js/filterCategory.js"></script>

</body>
</html>
