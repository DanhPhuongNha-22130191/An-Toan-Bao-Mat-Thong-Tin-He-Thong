<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Aroma Shop - Checkout</title>
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

	<!-- ================ start banner area ================= -->
	<section class="blog-banner-area" id="category">
		<div class="container h-100">
			<div class="blog-banner">
				<div class="text-center">
					<h1>Product Checkout</h1>
					<nav aria-label="breadcrumb" class="banner-breadcrumb">
						<ol class="breadcrumb">
							<li class="breadcrumb-item"><a href="#">Home</a></li>
							<li class="breadcrumb-item active" aria-current="page">Checkout</li>
						</ol>
					</nav>
				</div>
			</div>
		</div>
	</section>
	<!-- ================ end banner area ================= -->

	<div class="container">
		<!--================Checkout Area =================-->
		<section class="checkout_area section-margin--small">
			<div class="billing_details">
				<div class="row">
					<div class="col-lg-8">
						<h3>Thông tin giao hàng</h3>
						<form class="row contact_form" action="#" method="post"
							novalidate="novalidate">
							<div class="col-md-12 form-group p_star">
								<input type="text" class="form-control" id="full" name="name"
									placeholder="Họ và tên"> <span class="placeholder"
									data-placeholder="Full name"></span>
							</div>
							<div class="col-md-6 form-group p_star">
								<input type="text" class="form-control" id="number"
									name="number" placeholder="Số điện thoại"> <span
									class="placeholder" data-placeholder="Phone number"></span>
							</div>
							<div class="col-md-6 form-group p_star">
								<input type="text" class="form-control" id="email"
									placeholder="Email" name="compemailany"> <span
									class="placeholder" data-placeholder="Email Address"></span>
							</div>
							<div class="col-md-12 form-group p_star">
								<input type="text" class="form-control" id="add" name="add"
									placeholder="Địa chỉ"> <span class="placeholder"
									data-placeholder="Address"></span>
							</div>
							<div class="col-md-12 form-group mb-0">
								<textarea class="form-control" name="message" id="message"
									rows="1" placeholder="Ghi chú"></textarea>
							</div>
						</form>
					</div>
					<div class="col-lg-4">
						<div class="order_box">
							<h2>Đơn đặt hàng</h2>
							<ul class="list">
								<li><a href="#"><h4>
											Sản phẩm <span>Tổng</span>
										</h4></a></li>
								<c:forEach var="cartItem" values="${cartDTO.items }">
									<li><a href="#">${cartItem.productName}<span
											class="middle">x {cartItem.quantity}</span> <span
											class="last">>${cartItem.productPrice}</span></a></li>
								</c:forEach>
							</ul>
							<ul class="list list_2">
								<li><a href="#">Tổng tiền hàng <span>${cartDTO.subTotal}</span></a></li>
								<li><a href="#">Phí giao hàng <span>0</span></a></li>
								<li><a href="#">Phiếu giảm giá <span>-${cartDTO.discount }</span></a></li>
								<li><a href="#">Tổng cộng <span>${cartDTO.totalPrice}</span></a></li>
							</ul>
							<div class="payment_item active">
								<div class="radion_btn">
									<input type="radio" id="f-option6" name="selector"> <label
										for="f-option6">Thanh toán khi nhận hàng </label>
									<div class="check"></div>
								</div>
								<p>Phương thức thanh toán được chọn là Thanh toán khi nhận
									hàng (COD - Cash on Delivery). Khách hàng sẽ thanh toán trực
									tiếp cho nhân viên giao hàng khi nhận được sản phẩm.</p>
							</div>
							<div class="text-center">
								<a class="button button-paypal" href="#">Xác nhận đặt đơn</a>
							</div>
						</div>
					</div>
				</div>
			</div>
	</div>
	</section>
	</div>
	<!--================End Checkout Area =================-->


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