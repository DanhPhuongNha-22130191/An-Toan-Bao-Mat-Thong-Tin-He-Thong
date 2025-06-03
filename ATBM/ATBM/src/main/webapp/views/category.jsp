<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Aroma Shop - Category</title>
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
<!-- ================ category section start ================= -->
<!--================ Start Header Menu Area =================-->
<jsp:include page="/views/header.jsp" />
<!--================ End Header Menu Area =================-->
<section class="section-margin--small mb-5">
    <div class="container">
        <div class="row">
            <div class="col-xl-3 col-lg-4 col-md-5">
                <form id="filterForm" action="category" method="get">
                    <!-- Brand Filter -->
                    <div class="sidebar-categories">
                        <div class="head">Browse Brands</div>
                        <ul class="main-categories">
                            <li class="common-filter">
                                <ul>
                                    <c:forEach var="brand" items="${brands}">
                                        <li class="filter-list">
                                            <input class="pixel-checkbox" type="checkbox" name="brandId[]"
                                                   id="brand${brand.brandId}" value="${brand.brandId}"
                                                   onchange="applyFilters();"
                                                   <c:if test="${fn:contains(paramValues['brandId[]'], brand.brandId)}">checked</c:if>>
                                            <label for="brand${brand.brandId}">${brand.name}</label>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                        </ul>
                    </div>

                    <!-- Strap Filter -->
                    <div class="sidebar-filter">
                        <div class="top-filter-head">Product Filters</div>
                        <div class="common-filter">
                            <div class="head">Strap Material</div>
                            <ul>
                                <c:forEach var="strap" items="${straps}">
                                    <li class="filter-list">
                                        <input class="pixel-checkbox" type="checkbox" name="strapId[]"
                                               id="strap${strap.strapId}" value="${strap.strapId}"
                                               onchange="applyFilters();"
                                               <c:if test="${fn:contains(paramValues['strapId[]'], strap.strapId)}">checked</c:if>>
                                        <label for="strap${strap.strapId}">${strap.material}</label>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>

                        <!-- Price Range -->
                        <div class="common-filter">
                            <div class="head">Price</div>
                            <div class="price-range-area">
                                <div id="price-range"></div>
                                <div class="value-wrapper d-flex">
                                    <div class="price">Price:</div>
                                    <span>$</span>
                                    <div id="lower-value">0</div>
                                    <div class="to">to</div>
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

            <div class="col-xl-9 col-lg-8 col-md-7">
                <!-- Spinner -->
                <div id="loading-spinner" style="display: none; text-align:center; margin:20px;">
                    <div class="spinner-border text-primary" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                </div>

                <!-- Product List -->
                <section class="lattest-product-area pb-40 category-list">
                    <div id="product-list" class="row">
                        <c:forEach var="product" items="${products}">
                            <div class="col-md-6 col-lg-4">
                                <div class="card text-center card-product">
                                    <div class="card-product__img">
                                        <img class="card-img" src="${product.image}" alt="${product.name}">
                                        <ul class="card-product__imgOverlay">
                                            <li>
                                                <button><i class="ti-search"></i></button>
                                            </li>
                                            <li>
                                                <button><i class="ti-shopping-cart"></i></button>
                                            </li>
                                            <li>
                                                <button><i class="ti-heart"></i></button>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="card-body">
                                        <p>${product.description}</p>
                                        <h4 class="card-product__title"><a href="${pageContext.servletContext.contextPath}/product?action=details&id=${product.productId}">${product.name}</a></h4>
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
<!-- ================ category section end ================= -->

<script src="${pageContext.request.contextPath}/assests/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/nice-select/jquery.nice-select.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/nouislider/nouislider.min.js"></script>
<script>
    async function applyFilters() {
        const data = $('#filterForm').serialize();
        $('#loading-spinner').show();

        const contextPath = '${pageContext.request.contextPath}';

        try {
            const response = await $.ajax({
                url: `${pageContext.request.contextPath}/product/filter`,
                method: 'GET',
                data: data,
                dataType: 'json'
            });

            console.log("Response data:", response);

            const productList = document.getElementById('product-list');
            productList.innerHTML = '';

            if (response.length === 0) {
                const noProductsMessage = document.createElement('div');
                noProductsMessage.classList.add('col-12');
                noProductsMessage.innerHTML = '<p class="text-center">No products found.</p>';
                productList.appendChild(noProductsMessage);
                return;
            }

            response.forEach(function (product) {
                const productCard = document.createElement('div');
                productCard.classList.add('col-md-6', 'col-lg-4');

                const card = document.createElement('div');
                card.classList.add('card', 'text-center', 'card-product');

                const imgContainer = document.createElement('div');
                imgContainer.classList.add('card-product__img');

                const img = document.createElement('img');
                img.classList.add('card-img');
                img.src = `${contextPath}/assests/img/${product.image}`;
                img.alt = product.name;
                imgContainer.appendChild(img);

                const overlay = document.createElement('ul');
                overlay.classList.add('card-product__imgOverlay');

                ['ti-search', 'ti-shopping-cart', 'ti-heart'].forEach(icon => {
                    const li = document.createElement('li');
                    const btn = document.createElement('button');
                    const i = document.createElement('i');
                    i.classList.add(icon);
                    btn.appendChild(i);
                    li.appendChild(btn);
                    overlay.appendChild(li);
                });

                imgContainer.appendChild(overlay);
                card.appendChild(imgContainer);

                const body = document.createElement('div');
                body.classList.add('card-body');

                const desc = document.createElement('p');
                desc.textContent = product.description;

                const title = document.createElement('h4');
                title.classList.add('card-product__title');
                const link = document.createElement('a');
                link.href = '#';
                link.textContent = product.name;
                title.appendChild(link);

                const price = document.createElement('p');
                price.classList.add('card-product__price');
                price.textContent = product.price !== undefined && product.price !== null ? `$`+product.price : '$0';
                // console.log("Product price:", product.price);

                body.appendChild(title);
                body.appendChild(desc);
                body.appendChild(price);
                card.appendChild(body);
                productCard.appendChild(card);
                productList.appendChild(productCard);
            });
        } catch (error) {
            console.error("Error applying filters", error);
        } finally {
            $('#loading-spinner').hide();
        }
    }

    $(function () {
        const slider = document.getElementById('price-range');
        if (slider) {
            noUiSlider.create(slider, {
                start: [
                    ${param.minPrice != null ? param.minPrice : minPrice},
                    ${param.maxPrice != null ? param.maxPrice : maxPrice}
                ],
                range: {
                    'min': ${minPrice},
                    'max': ${maxPrice}
                }
            });

            slider.noUiSlider.on('update', function (values) {
                $('#lower-value').text(Math.round(values[0]));
                $('#upper-value').text(Math.round(values[1]));
            });

            slider.noUiSlider.on('change', function (values) {
                $('#minPriceInput').val(Math.round(values[0]));
                $('#maxPriceInput').val(Math.round(values[1]));
                applyFilters();
            });
        }

        // Handle checkbox changes
        document.querySelectorAll('input[type="checkbox"]').forEach(function (checkbox) {
            checkbox.addEventListener('change', function () {
                applyFilters(); // Update products on any checkbox change
            });
        });
    });
</script>
</body>
</html>