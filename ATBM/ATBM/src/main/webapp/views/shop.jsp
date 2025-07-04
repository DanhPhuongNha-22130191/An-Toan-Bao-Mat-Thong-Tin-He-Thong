<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Aroma Shop - Shop</title>
	<link rel="icon" href="${pageContext.request.contextPath}/assets/img/Fevicon.png" type="image/png">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/fontawesome/css/all.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/themify-icons/themify-icons.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/linericon/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/owl-carousel/owl.theme.default.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/owl-carousel/owl.carousel.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/nice-select/nice-select.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/nouislider/nouislider.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="/views/header.jsp" />


<!-- ================ category section start ================= -->
<section class="section-margin--small mb-5">
	<div class="container">
		<div class="row">
			<div class="col-xl-3 col-lg-4 col-md-5">
				<div class="sidebar-categories">
					<div class="head">Browse Categories</div>
					<ul class="main-categories">
						<li class="common-filter">
							<form action="#">
								<ul>
									<li class="filter-list"><input class="pixel-radio" type="radio" id="men" name="brand"><label for="men">Men<span> (3600)</span></label></li>
									<li class="filter-list"><input class="pixel-radio" type="radio" id="women" name="brand"><label for="women">Women<span> (3600)</span></label></li>
									<li class="filter-list"><input class="pixel-radio" type="radio" id="accessories" name="brand"><label for="accessories">Accessories<span> (3600)</span></label></li>
									<li class="filter-list"><input class="pixel-radio" type="radio" id="footwear" name="brand"><label for="footwear">Footwear<span> (3600)</span></label></li>
									<li class="filter-list"><input class="pixel-radio" type="radio" id="bayItem" name="brand"><label for="bayItem">Bay item<span> (3600)</span></label></li>
									<li class="filter-list"><input class="pixel-radio" type="radio" id="electronics" name="brand"><label for="electronics">Electronics<span> (3600)</span></label></li>
									<li class="filter-list"><input class="pixel-radio" type="radio" id="food" name="brand"><label for="food">Food<span> (3600)</span></label></li>
								</ul>
							</form>
						</li>
					</ul>
				</div>
				<div class="sidebar-filter">
					<div class="top-filter-head">Product Filters</div>
					<div class="common-filter">
						<div class="head">Brands</div>
						<form action="#">
							<ul>
								<li class="filter-list"><input class="pixel-radio" type="radio" id="apple" name="brand"><label for="apple">Apple<span>(29)</span></label></li>
								<li class="filter-list"><input class="pixel-radio" type="radio" id="asus" name="brand"><label for="asus">Asus<span>(29)</span></label></li>
								<li class="filter-list"><input class="pixel-radio" type="radio" id="gionee" name="brand"><label for="gionee">Gionee<span>(19)</span></label></li>
								<li class="filter-list"><input class="pixel-radio" type="radio" id="micromax" name="brand"><label for="micromax">Micromax<span>(19)</span></label></li>
								<li class="filter-list"><input class="pixel-radio" type="radio" id="samsung" name="brand"><label for="samsung">Samsung<span>(19)</span></label></li>
							</ul>
						</form>
					</div>
					<div class="common-filter">
						<div class="head">Color</div>
						<form action="#">
							<ul>
								<li class="filter-list"><input class="pixel-radio" type="radio" id="black" name="color"><label for="black">Black<span>(29)</span></label></li>
								<li class="filter-list"><input class="pixel-radio" type="radio" id="balckleather" name="color"><label for="balckleather">Black Leather<span>(29)</span></label></li>
								<li class="filter-list"><input class="pixel-radio" type="radio" id="blackred" name="color"><label for="blackred">Black with red<span>(19)</span></label></li>
								<li class="filter-list"><input class="pixel-radio" type="radio" id="gold" name="color"><label for="gold">Gold<span>(19)</span></label></li>
								<li class="filter-list"><input class="pixel-radio" type="radio" id="spacegrey" name="color"><label for="spacegrey">Spacegrey<span>(19)</span></label></li>
							</ul>
						</form>
					</div>
					<div class="common-filter">
						<div class="head">Price</div>
						<div class="price-range-area">
							<div id="price-range"></div>
							<div class="value-wrapper d-flex">
								<div class="price">Price:</div>
								<span>$</span>
								<div id="lower-value"></div>
								<div class="to">to</div>
								<span>$</span>
								<div id="upper-value"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xl-9 col-lg-8 col-md-7">
				<!-- Start Filter Bar -->
				<div class="filter-bar d-flex flex-wrap align-items-center">
					<div class="sorting">
						<select>
							<option value="1">Default sorting</option>
							<option value="1">Default sorting</option>
							<option value="1">Default sorting</option>
						</select>
					</div>
					<div class="sorting mr-auto">
						<select>
							<option value="1">Show 12</option>
							<option value="1">Show 12</option>
							<option value="1">Show 12</option>
						</select>
					</div>
					<div>
						<div class="input-group filter-bar-search">
							<input type="text" placeholder="Search">
							<div class="input-group-append">
								<button type="button">
									<i class="ti-search"></i>
								</button>
							</div>
						</div>
					</div>
				</div>
				<!-- End Filter Bar -->
				<c:if test="${not empty sessionScope.message}">
					<div class="alert alert-success" role="alert">
							${sessionScope.message}
					</div>
					<c:remove var="message" scope="session" />
				</c:if>
				<!-- Start Best Seller -->
				<section class="lattest-product-area pb-40 category-list">
					<div class="row">
						<c:forEach var="product" items="${products}">
							<div class="col-md-6 col-lg-4">
								<div class="card text-center card-product">
									<div class="card-product__img">
										<img class="card-img" src="${pageContext.request.contextPath}/${product.image}" alt="${product.name}">
										<ul class="card-product__imgOverlay">
											<li><button><i class="ti-search"></i></button></li>
											<li>
												<form action="${pageContext.request.contextPath}/user/cart" method="POST">
													<input type="hidden" name="productId" value="${product.productId}" />
													<input type="hidden" name="quantity" value="1" />
													<button type="submit">
														<i class="ti-shopping-cart"></i>
													</button>
												</form>
											</li>
											<li><button><i class="ti-heart"></i></button></li>
										</ul>
									</div>
									<div class="card-body">
										<h4 class="card-product__title">
											<a href="${pageContext.request.contextPath}/shop/product/${product.productId}">${product.name}</a>
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

<!-- ================ top product area start ================= -->
<section class="related-product-area">
	<div class="container">
		<div class="section-intro pb-60px">
			<p>Popular Item in the market</p>
			<h2>Top <span class="section-intro__style">Product</span></h2>
		</div>
		<div class="row mt-30">
			<div class="col-sm-6 col-xl-3 mb-4 mb-xl-0">
				<div class="single-search-product-wrapper">
					<div class="single-search-product d-flex">
						<a href="#"><img src="${pageContext.request.contextPath}/assets/img/product/product-sm-1.png" alt=""></a>
						<div class="desc">
							<a href="#" class="title">Gray Coffee Cup</a>
							<div class="price">$170.00</div>
						</div>
					</div>
					<div class="single-search-product d-flex">
						<a href="#"><img src="${pageContext.request.contextPath}/assets/img/product/product-sm-2.png" alt=""></a>
						<div class="desc">
							<a href="#" class="title">Gray Coffee Cup</a>
							<div class="price">$170.00</div>
						</div>
					</div>
					<div class="single-search-product d-flex">
						<a href="#"><img src="${pageContext.request.contextPath}/assets/img/product/product-sm-3.png" alt=""></a>
						<div class="desc">
							<a href="#" class="title">Gray Coffee Cup</a>
							<div class="price">$170.00</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- ================ top product area end ================= -->

<!-- ================ Subscribe section start ================= -->
<section class="subscribe-position">
	<div class="container">
		<div class="subscribe text-center">
			<h3 class="subscribe__title">Get Update From Anywhere</h3>
			<p>Bearing Void gathering light light his eavening unto dont afraid</p>
			<div id="mc_embed_signup">
				<form target="_blank" action="https://spondonit.us12.list-manage.com/subscribe/post?u=1462626880ade1ac87bd9c93a&id=92a4423d01" method="get" class="subscribe-form form-inline mt-5 pt-1">
					<div class="form-group ml-sm-auto">
						<input class="form-control mb-1" type="email" name="EMAIL" placeholder="Enter your email" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Your Email Address '">
						<div class="info"></div>
					</div>
					<button class="button button-subscribe mr-auto mb-1" type="submit">Subscribe Now</button>
					<div style="position: absolute; left: -5000px;">
						<input name="b_36c4fd991d266f23781ded980_aefe40901a" tabindex="-1" value="" type="text">
					</div>
				</form>
			</div>
		</div>
	</div>
</section>
<!-- ================ Subscribe section end ================= -->

<jsp:include page="/views/footer.jsp" />

<script src="${pageContext.request.contextPath}/assets/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/skrollr.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/owl-carousel/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/nice-select/jquery.nice-select.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/nouislider/nouislider.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/jquery.ajaxchimp.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/mail-script.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>