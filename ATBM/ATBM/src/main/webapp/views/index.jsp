<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Aroma Shop - Home</title>


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
	href="${pageContext.request.contextPath}/assests/vendors/nice-select/nice-select.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.theme.default.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assests/css/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assests/css/styles.css">
</head>
<body>
	<!--================ Start Header Menu Area =================-->
	<jsp:include page="/views/header.jsp" />
	<!--================ End Header Menu Area =================-->

	<main class="site-main">

		<!--================ Hero banner start =================-->
		<section class="hero-banner">
			<div class="container">
				<div class="row no-gutters align-items-center pt-60px">
					<div class="col-5 d-none d-sm-block">
						<div class="hero-banner__img">
							<img class="img-fluid"
								src="${pageContext.request.contextPath}/assests/img/home/hero-banner.png"
								alt="">
						</div>
					</div>
					<div class="col-sm-7 col-lg-6 offset-lg-1 pl-4 pl-md-5 pl-lg-0">
						<div class="hero-banner__content">
							<h4>Shop is fun</h4>
							<h1>Browse Our Premium Product</h1>
							<p>Us which over of signs divide dominion deep fill bring
								they're meat beho upon own earth without morning over third.
								Their male dry. They are great appear whose land fly grass.</p>
							<a class="button button-hero" href="#">Browse Now</a>
						</div>
					</div>
				</div>
			</div>
		</section>
		<!--================ Hero banner start =================-->

		<!--================ Hero Carousel start =================-->
		<section class="section-margin mt-0">
			<div class="owl-carousel owl-theme hero-carousel">
				<div class="hero-carousel__slide">
					<img
						src="${pageContext.request.contextPath}/assests/img/home/hero-slide1.png"
						alt="" class="img-fluid"> <a href="#"
						class="hero-carousel__slideOverlay">
						<h3>Wireless Headphone</h3>
						<p>Accessories Item</p>
					</a>
				</div>
				<div class="hero-carousel__slide">
					<img
						src="${pageContext.request.contextPath}/assests/img/home/hero-slide2.png"
						alt="" class="img-fluid"> <a href="#"
						class="hero-carousel__slideOverlay">
						<h3>Wireless Headphone</h3>
						<p>Accessories Item</p>
					</a>
				</div>
				<div class="hero-carousel__slide">
					<img
						src="${pageContext.request.contextPath}/assests/img/home/hero-slide3.png"
						alt="" class="img-fluid"> <a href="#"
						class="hero-carousel__slideOverlay">
						<h3>Wireless Headphone</h3>
						<p>Accessories Item</p>
					</a>
				</div>
			</div>
		</section>
		<!--================ Hero Carousel end =================-->

		<!-- ================ trending product section start ================= -->
		<section class="section-margin calc-60px">
			<div class="container">
				<div class="section-intro pb-60px">
					<p>Popular Item in the market</p>
					<h2>
						Trending <span class="section-intro__style">Product</span>
					</h2>
				</div>
				<div class="owl-carousel owl-theme" id="bestSellerCarousel">
					<c:forEach var="product" items="${trendingProducts}">
						<div class="card text-center card-product">
							<div class="card-product__img">
								<img class="img-fluid"
									src="${pageContext.servletContext.contextPath}/assets/img/product/${product.image}"
									alt="${product.name}">
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
									<a href="${pageContext.servletContext.contextPath}/product?action=details&id=${product.productId}">${product.name}</a>
								</h4>
								<p class="card-product__price">${product.price}</p>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</section>
		<!-- ================ trending product section end ================= -->


		<!-- ================ offer section start ================= -->
		<section class="offer" id="parallax-1"
			data-anchor-target="#parallax-1"
			data-300-top="background-position: 20px 30px"
			data-top-bottom="background-position: 0 20px">
			<div class="container">
				<div class="row">
					<div class="col-xl-5">
						<div class="offer__content text-center">
							<h3>Up To 50% Off</h3>
							<h4>Winter Sale</h4>
							<p>Him she'd let them sixth saw light</p>
							<a class="button button--active mt-3 mt-xl-4" href="#">Shop
								Now</a>
						</div>
					</div>
				</div>
			</div>
		</section>
		<!-- ================ offer section end ================= -->
		<!-- ================ new product section start ================= -->
		<section class="section-margin calc-60px">
			<div class="container">
				<div class="section-intro pb-60px">
					<p>Popular Item in the market</p>
					<h2>
						New <span class="section-intro__style">Product</span>
					</h2>
				</div>
				<div class="row">
					<!-- Lặp qua danh sách sản phẩm từ request -->
					<c:forEach var="newProduct" items="${newProducts}">
						<div class="col-md-6 col-lg-4 col-xl-3">
							<div class="card text-center card-product">
								<div class="card-product__img">
									<img class="card-img"
										src="${pageContext.request.contextPath}/assets/img/product/${newProduct.image}"
										alt="${newProduct.name}">
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
									<p>${newProduct.description}</p>
									<h4 class="card-product__title">
										<a href="single-product.html">${newProduct.name}</a>
									</h4>
									<p class="card-product__price">$${newProduct.price}</p>
								</div>
							</div>
						</div>
					</c:forEach>
					<!-- Nếu không có sản phẩm, hiển thị thông báo -->
					<c:if test="${empty newProducts}">
						<p>No products found.</p>
					</c:if>
				</div>
			</div>
		</section>
		<!-- ================ trending product section end ================= -->





		<!-- ================ Subscribe section start ================= -->
		<section class="subscribe-position">
			<div class="container">
				<div class="subscribe text-center">
					<h3 class="subscribe__title">Get Update From Anywhere</h3>
					<p>Bearing Void gathering light light his eavening unto dont
						afraid</p>
					<div id="mc_embed_signup">
						<form target="_blank"
							action="https://spondonit.us12.list-manage.com/subscribe/post?u=1462626880ade1ac87bd9c93a&amp;id=92a4423d01"
							method="get" class="subscribe-form form-inline mt-5 pt-1">
							<div class="form-group ml-sm-auto">
								<input class="form-control mb-1" type="email" name="EMAIL"
									placeholder="Enter your email" onfocus="this.placeholder = ''"
									onblur="this.placeholder = 'Your Email Address '">
								<div class="info"></div>
							</div>
							<button class="button button-subscribe mr-auto mb-1"
								type="submit">Subscribe Now</button>
							<div style="position: absolute; left: -5000px;">
								<input name="b_36c4fd991d266f23781ded980_aefe40901a"
									tabindex="-1" value="" type="text">
							</div>

						</form>
					</div>

				</div>
			</div>
		</section>
		<!-- ================ Subscribe section end ================= -->
	</main>
	<!--================ Start footer Area  =================-->
	<footer class="footer">
		<div class="footer-area">
			<div class="container">
				<div class="row section_gap">
					<div class="col-lg-3 col-md-6 col-sm-6">
						<div class="single-footer-widget tp_widgets">
							<h4 class="footer_title large_title">Our Mission</h4>
							<p>So seed seed green that winged cattle in. Gathering thing
								made fly you're no divided deep moved us lan Gathering thing us
								land years living.</p>
							<p>So seed seed green that winged cattle in. Gathering thing
								made fly you're no divided deep moved</p>
						</div>
					</div>
					<div class="offset-lg-1 col-lg-2 col-md-6 col-sm-6">
						<div class="single-footer-widget tp_widgets">
							<h4 class="footer_title">Quick Links</h4>
							<ul class="list">
								<li><a href="#">Home</a></li>
								<li><a href="#">Shop</a></li>
								<li><a href="#">Blog</a></li>
								<li><a href="#">Product</a></li>
								<li><a href="#">Brand</a></li>
								<li><a href="#">Contact</a></li>
							</ul>
						</div>
					</div>
					<div class="col-lg-2 col-md-6 col-sm-6">
						<div class="single-footer-widget instafeed">
							<h4 class="footer_title">Gallery</h4>
							<ul class="list instafeed d-flex flex-wrap">
								<li><img
									src="${pageContext.request.contextPath}/assests/img/gallery/r1.jpg"
									alt=""></li>
								<li><img
									src="${pageContext.request.contextPath}/assests/img/gallery/r2.jpg"
									alt=""></li>
								<li><img
									src="${pageContext.request.contextPath}/assests/img/gallery/r3.jpg"
									alt=""></li>
								<li><img
									src="${pageContext.request.contextPath}/assests/img/gallery/r5.jpg"
									alt=""></li>
								<li><img
									src="${pageContext.request.contextPath}/assests/img/gallery/r7.jpg"
									alt=""></li>
								<li><img
									src="${pageContext.request.contextPath}/assests/img/gallery/r8.jpg"
									alt=""></li>
							</ul>
						</div>
					</div>
					<div class="offset-lg-1 col-lg-3 col-md-6 col-sm-6">
						<div class="single-footer-widget tp_widgets">
							<h4 class="footer_title">Contact Us</h4>
							<div class="ml-40">
								<p class="sm-head">
									<span class="fa fa-location-arrow"></span> Head Office
								</p>
								<p>123, Main Street, Your City</p>

								<p class="sm-head">
									<span class="fa fa-phone"></span> Phone Number
								</p>
								<p>
									+123 456 7890 <br> +123 456 7890
								</p>

								<p class="sm-head">
									<span class="fa fa-envelope"></span> Email
								</p>
								<p>
									free@infoexample.com <br> www.infoexample.com
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="footer-bottom">
			<div class="container">
				<div class="row d-flex">
					<p class="col-lg-12 footer-text text-center">
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						Copyright &copy;
						<script>document.write(new Date().getFullYear());</script>
						All rights reserved | This template is made with <i
							class="fa fa-heart" aria-hidden="true"></i> by <a
							href="https://colorlib.com" target="_blank">Colorlib</a>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
					</p>
				</div>
			</div>
		</div>
	</footer>
	<!--================ End footer Area  =================-->



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
</body>
</html>