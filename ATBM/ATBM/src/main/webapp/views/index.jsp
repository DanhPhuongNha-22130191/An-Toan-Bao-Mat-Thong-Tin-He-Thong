<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Cửa Hàng Đồng Hồ - Trang Chủ</title>
	<link rel="icon" href="${pageContext.request.contextPath}/assests/img/Fevicon.png" type="image/png">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/fontawesome/css/all.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/themify-icons/themify-icons.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/nice-select/nice-select.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.theme.default.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/style.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assests/css/styles.css">
	<style>
		.card-product__img img {
			width: 100%;
			height: 250px;
			object-fit: cover;
			object-position: center;
			border-radius: 5px;
		}
	</style>
</head>
<body>

<!--================ Start Header Menu Area =================-->
<jsp:include page="/views/header.jsp"/>
<!--================ End Header Menu Area =================-->

<main class="site-main">

	<!--================ Hero banner start =================-->
	<section class="hero-banner">
		<div class="container">
			<div class="row no-gutters align-items-center pt-60px">
				<div class="col-5 d-none d-sm-block">
					<div class="hero-banner__img">
						<img class="img-fluid" src="${pageContext.request.contextPath}/assests/img/home/hero-banner.png" alt="">
					</div>
				</div>
				<div class="col-sm-7 col-lg-6 offset-lg-1 pl-4 pl-md-5 pl-lg-0">
					<div class="hero-banner__content">
						<h4>Mua sắm thật thú vị</h4>
						<h1>Khám phá các sản phẩm cao cấp</h1>
						<p>Chúng tôi mang đến những sản phẩm chất lượng, kiểu dáng sang trọng và hiện đại. Mua sắm dễ dàng, giao hàng tận nơi.</p>
						<a class="button button-hero" href="#">Xem ngay</a>
					</div>
				</div>
			</div>
		</div>
	</section>
	<!--================ Hero banner end =================-->

	<!--================ Hero Carousel start =================-->
	<section class="section-margin mt-0">
		<div class="owl-carousel owl-theme hero-carousel">
			<div class="hero-carousel__slide">
				<img src="${pageContext.request.contextPath}/assests/img/home/hero-slide1.png" alt="" class="img-fluid">
				<a href="#" class="hero-carousel__slideOverlay">
					<h3>Tai nghe không dây</h3>
					<p>Phụ kiện công nghệ</p>
				</a>
			</div>
			<div class="hero-carousel__slide">
				<img src="${pageContext.request.contextPath}/assests/img/home/hero-slide2.png" alt="" class="img-fluid">
				<a href="#" class="hero-carousel__slideOverlay">
					<h3>Tai nghe không dây</h3>
					<p>Phụ kiện công nghệ</p>
				</a>
			</div>
			<div class="hero-carousel__slide">
				<img src="${pageContext.request.contextPath}/assests/img/home/hero-slide3.png" alt="" class="img-fluid">
				<a href="#" class="hero-carousel__slideOverlay">
					<h3>Tai nghe không dây</h3>
					<p>Phụ kiện công nghệ</p>
				</a>
			</div>
		</div>
	</section>
	<!--================ Hero Carousel end =================-->

	<!--================ Trending product start =================-->
	<section class="section-margin calc-60px">
		<div class="container">
			<div class="section-intro pb-60px">
				<p>Sản phẩm phổ biến trên thị trường</p>
				<h2>
					Sản phẩm <span class="section-intro__style">Xu hướng</span>
				</h2>
			</div>
			<div class="owl-carousel owl-theme" id="bestSellerCarousel">
				<c:forEach var="product" items="${trendingProducts}">
					<div class="card text-center card-product">
						<div class="card-product__img">
							<img src="${pageContext.request.contextPath}/admin/productImage?productId=${product.productId}" alt="${product.name}">
							<ul class="card-product__imgOverlay">
								<li>
									<button onclick="addToCart(${product.productId})"><i
											class="ti-shopping-cart"></i></button>
								</li>							</ul>
						</div>
						<div class="card-body">
							<p>${product.description}</p>
							<h4 class="card-product__title">
								<a href="${pageContext.servletContext.contextPath}/product?action=details&id=${product.productId}">${product.name}</a>
							</h4>
							<p class="card-product__price">${product.price} ₫</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</section>
	<!--================ Trending product end =================-->

	<!--================ New product start =================-->
	<section class="section-margin calc-60px">
		<div class="container">
			<div class="section-intro pb-60px">
				<p>Sản phẩm mới có mặt</p>
				<h2>
					Sản phẩm <span class="section-intro__style">Mới</span>
				</h2>
			</div>
			<div class="row">
				<c:forEach var="newProduct" items="${newProducts}">
					<div class="col-md-6 col-lg-4 col-xl-3">
						<div class="card text-center card-product">
							<div class="card-product__img">
								<img src="${pageContext.request.contextPath}/admin/productImage?productId=${newProduct.productId}" alt="${newProduct.name}">
								<ul class="card-product__imgOverlay">
									<li>
										<button onclick="addToCart(${product.productId})"><i
												class="ti-shopping-cart"></i></button>
									</li>
								</ul>
							</div>
							<div class="card-body">
								<p>${newProduct.description}</p>
								<h4 class="card-product__title">
									<a href="single-product.html">${newProduct.name}</a>
								</h4>
								<p class="card-product__price">${newProduct.price} ₫</p>
							</div>
						</div>
					</div>
				</c:forEach>
				<c:if test="${empty newProducts}">
					<p>Không có sản phẩm nào được tìm thấy.</p>
				</c:if>
			</div>
		</div>
	</section>
	<!--================ New product end =================-->

	<!--================ Subscribe section start =================-->
	<section class="subscribe-position">
		<div class="container">
			<div class="subscribe text-center">
				<h3 class="subscribe__title">Nhận cập nhật từ bất cứ đâu</h3>
				<p>Luôn nhận được thông báo về sản phẩm và ưu đãi mới nhất từ chúng tôi.</p>
				<div id="mc_embed_signup">
					<form target="_blank" action="https://spondonit.us12.list-manage.com/subscribe/post?u=1462626880ade1ac87bd9c93a&amp;id=92a4423d01" method="get" class="subscribe-form form-inline mt-5 pt-1">
						<div class="form-group ml-sm-auto">
							<input class="form-control mb-1" type="email" name="EMAIL" placeholder="Nhập email của bạn" onfocus="this.placeholder = ''" onblur="this.placeholder = 'Nhập email của bạn'">
							<div class="info"></div>
						</div>
						<button class="button button-subscribe mr-auto mb-1" type="submit">Đăng ký ngay</button>
						<div style="position: absolute; left: -5000px;">
							<input name="b_36c4fd991d266f23781ded980_aefe40901a" tabindex="-1" value="" type="text">
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<!--================ Subscribe section end =================-->

</main>

<!--================ Start footer Area =================-->
<footer class="footer">
	<div class="footer-area">
		<div class="container">
			<div class="row section_gap">
				<div class="col-lg-3 col-md-6 col-sm-6">
					<div class="single-footer-widget tp_widgets">
						<h4 class="footer_title large_title">Sứ mệnh của chúng tôi</h4>
						<p>Chúng tôi mong muốn mang đến những sản phẩm chất lượng cao, phù hợp với phong cách sống hiện đại.</p>
						<p>Uy tín - Chất lượng - Khách hàng là trung tâm.</p>
					</div>
				</div>
				<div class="offset-lg-1 col-lg-2 col-md-6 col-sm-6">
					<div class="single-footer-widget tp_widgets">
						<h4 class="footer_title">Liên kết nhanh</h4>
						<ul class="list">
							<li><a href="#">Trang chủ</a></li>
							<li><a href="#">Cửa hàng</a></li>
							<li><a href="#">Blog</a></li>
							<li><a href="#">Sản phẩm</a></li>
							<li><a href="#">Thương hiệu</a></li>
							<li><a href="#">Liên hệ</a></li>
						</ul>
					</div>
				</div>
				<div class="col-lg-2 col-md-6 col-sm-6">
					<div class="single-footer-widget instafeed">
						<h4 class="footer_title">Thư viện ảnh</h4>
						<ul class="list instafeed d-flex flex-wrap">
							<li><img src="${pageContext.request.contextPath}/assests/img/gallery/r1.jpg" alt=""></li>
							<li><img src="${pageContext.request.contextPath}/assests/img/gallery/r2.jpg" alt=""></li>
							<li><img src="${pageContext.request.contextPath}/assests/img/gallery/r3.jpg" alt=""></li>
							<li><img src="${pageContext.request.contextPath}/assests/img/gallery/r5.jpg" alt=""></li>
							<li><img src="${pageContext.request.contextPath}/assests/img/gallery/r7.jpg" alt=""></li>
							<li><img src="${pageContext.request.contextPath}/assests/img/gallery/r8.jpg" alt=""></li>
						</ul>
					</div>
				</div>
				<div class="offset-lg-1 col-lg-3 col-md-6 col-sm-6">
					<div class="single-footer-widget tp_widgets">
						<h4 class="footer_title">Liên hệ với chúng tôi</h4>
						<div class="ml-40">
							<p class="sm-head"><span class="fa fa-location-arrow"></span> Trụ sở chính</p>
							<p>123, Đường Chính, Thành phố của bạn</p>

							<p class="sm-head"><span class="fa fa-phone"></span> Số điện thoại</p>
							<p>+123 456 7890 <br> +123 456 7890</p>

							<p class="sm-head"><span class="fa fa-envelope"></span> Email</p>
							<p>free@infoexample.com <br> www.infoexample.com</p>
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
					&copy; <script>document.write(new Date().getFullYear());</script> Bản quyền thuộc về Watch Shop |
					Thiết kế bởi <a href="https://colorlib.com" target="_blank">Colorlib</a>
				</p>
			</div>
		</div>
	</div>
</footer>
<!--================ End footer Area =================-->

<script src="${pageContext.request.contextPath}/assests/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/skrollr.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/owl-carousel/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/nice-select/jquery.nice-select.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/jquery.ajaxchimp.min.js"></script>
<script src="${pageContext.request.contextPath}/assests/vendors/mail-script.js"></script>
<script src="${pageContext.request.contextPath}/assests/js/main.js"></script>
<script>
	function addToCart(productId) {
		fetch('/ATBM/user/cart/add', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			body: new URLSearchParams({
				productId: productId,
				quantity: 1 // mặc định thêm 1 sản phẩm
			}),
			credentials: 'same-origin'  // <=== thêm dòng này để gửi cookie session
		})
				.then(response => {
					if (response.redirected) {
						// Nếu controller redirect (đúng như bạn làm), thì thực hiện redirect
						window.location.href = response.url;
					} else {
						return response.text();
					}
				})
				.catch(error => {
					console.error('Lỗi khi thêm vào giỏ hàng:', error);
					alert("Có lỗi xảy ra. Vui lòng thử lại.");
				});
	}
</script>

</body>
</html>
