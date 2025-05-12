<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Aroma Shop - Category</title>
	<link rel="icon" href="${pageContext.request.contextPath}/assests/img/Fevicon.png" type="image/png">

	<!-- CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/fontawesome/css/all.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/themify-icons/themify-icons.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/linericon/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.theme.default.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/nice-select/nice-select.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/nouislider/nouislider.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/style.css">
</head>

<body>

<!-- ================ category section start ================= -->
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
											<input class="pixel-radio" type="radio" name="brandId"
												   id="brand${brand.brandId}" value="${brand.brandId}"
												   onchange="document.getElementById('filterForm').submit();"
												   <c:if test="${param.brandId == brand.brandId}">checked</c:if>>
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
										<input class="pixel-radio" type="radio" name="strapId"
											   id="strap${strap.strapId}" value="${strap.strapId}"
											   onchange="document.getElementById('filterForm').submit();"
											   <c:if test="${param.strapId == strap.strapId}">checked</c:if>>
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
									<span>$</span><div id="lower-value">0</div>
									<div class="to">to</div>
									<span>$</span><div id="upper-value">1000</div>
								</div>
								<input type="hidden" id="minPriceInput" name="minPrice" value="${param.minPrice != null ? param.minPrice : 0}">
								<input type="hidden" id="maxPriceInput" name="maxPrice" value="${param.maxPrice != null ? param.maxPrice : 1000}">
							</div>
						</div>
					</div>
				</form>
			</div>

			<div class="col-xl-9 col-lg-8 col-md-7">
				<!-- Product List -->
				<section class="lattest-product-area pb-40 category-list">
					<div class="row">
						<c:forEach var="product" items="${products}">
							<div class="col-md-6 col-lg-4">
								<div class="card text-center card-product">
									<div class="card-product__img">
										<img class="card-img"
											 src="${product.image}"
											 alt="${product.name}">
										<ul class="card-product__imgOverlay">
											<li><button><i class="ti-search"></i></button></li>
											<li><button><i class="ti-shopping-cart"></i></button></li>
											<li><button><i class="ti-heart"></i></button></li>
										</ul>
									</div>
									<div class="card-body">
										<p>${product.description}</p>
										<h4 class="card-product__title"><a href="#">${product.name}</a></h4>
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

<!-- Scripts -->
<script src="${pageContext.request.contextPath}/assests/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/nice-select/jquery.nice-select.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/nouislider/nouislider.min.js"></script>
<script>
	$(function () {
		const slider = document.getElementById('price-range');
		if (slider) {
			noUiSlider.create(slider, {
				start: [
					${param.minPrice != null ? param.minPrice : 0},
					${param.maxPrice != null ? param.maxPrice : 1000}
				],
				connect: true,
				range: {
					'min': 0,
					'max': 1000
				}
			});

			const lowerValue = document.getElementById('lower-value');
			const upperValue = document.getElementById('upper-value');
			const minInput = document.getElementById('minPriceInput');
			const maxInput = document.getElementById('maxPriceInput');

			slider.noUiSlider.on('update', function (values) {
				let min = Math.round(values[0]);
				let max = Math.round(values[1]);
				lowerValue.textContent = min;
				upperValue.textContent = max;
			});

			slider.noUiSlider.on('change', function (values) {
				let min = Math.round(values[0]);
				let max = Math.round(values[1]);
				minInput.value = min;
				maxInput.value = max;
				document.getElementById('filterForm').submit();
			});
		}
	});
</script>
</body>
</html>
