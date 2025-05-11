<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Aroma Shop - Category</title>


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
</head>
<body>




	<!-- ================ category section start ================= -->
	<section class="section-margin--small mb-5">
		<div class="container">
			<div class="row">
				<div class="col-xl-3 col-lg-4 col-md-5">
					<div class="sidebar-categories">
						<div class="head">Browse Brands</div>
						<ul class="main-categories">
							<li class="common-filter">
								<form id="brandForm"
									action="${pageContext.request.contextPath}/CategoryController"
									method="get">
									<ul>
										<c:forEach var="brand" items="${brands}">
											<li class="filter-list"><input class="pixel-radio"
												type="radio" id="brand${brand.brandId}" name="brandId"
												value="${brand.brandId}"
												onchange="document.getElementById('brandForm').submit();">
												<label for="brand${brand.brandId}">${brand.name}</label></li>
										</c:forEach>
									</ul>
								</form>

							</li>
						</ul>
					</div>
					<div class="sidebar-filter">
						<div class="top-filter-head">Product Filters</div>
						<div class="common-filter">
							<div class="head">Watch Strap Color</div>
							<form action="#">
								<ul>
									<c:forEach var="strap" items="${straps}">
										<li class="filter-list"><input class="pixel-radio"
											type="radio" id="${strap.strapId}" name="material">${strap.material}</li>
									</c:forEach>
								</ul>
							</form>
						</div>
						<div class="common-filter">
							<div class="head">Price</div>
							<div class="price-range-area">
								<div id="price-range"></div>
								<div class="value-wrapper d-flex">
									<%-- <c:forEach var="product" items="${products}"> --%>
									<div class="price">Price:</div>
									<span>$</span>
									<div id="lower-value"></div>
									<div class="to">to</div>
									<span>$</span>
									<div id="upper-value"></div>
									<%-- </c:forEach> --%>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-xl-9 col-lg-8 col-md-7">
					<!-- Start Best Seller -->
					<section class="lattest-product-area pb-40 category-list">
						<div class="row">
							<c:forEach var="product" items="${products}">
								<div class="col-md-6 col-lg-4">
									<div class="card text-center card-product">
										<div class="card-product__img">
											<img class="card-img"
												src="https://empireluxury.vn/wp-content/uploads/2022/06/dong-ho-rolex-datejust-31-278273-0032-mat-so-hoa-tiet-hoa-xanh-olive-day-deo-jubilee-thep-vang-vang-4-1.jpg"
												alt="Not unvailable">
											<ul class="card-product__imgOverlay">
												<li><button>
														<i class="ti-search"></i>
													</button></li>
												<li><button>
														<i class="ti-shopping-cart"></i>
													</button></li>
												<li><button>
														<i class="ti-heart"></i>
													</button></li>
											</ul>
										</div>
										<div class="card-body">
											<p>${product.description}</p>
											<h4 class="card-product__title">
												<a href="#">${product.name}</a>
											</h4>
											<p class="card-product__price">$${product.price}</p>
										</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</section>
					<!-- End Best Seller -->
				</div>
			</div>
		</div>
	</section>
	<!-- ================ category section end ================= -->



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
		src="${pageContext.request.contextPath}/assests/vendors/nouislider/nouislider.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assests/vendors/jquery.ajaxchimp.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assests/vendors/mail-script.js"></script>
	<script src="${pageContext.request.contextPath}/assests/js/main.js"></script>
	<script src="${pageContext.request.contextPath}/assests/js/category.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</body>
</html>