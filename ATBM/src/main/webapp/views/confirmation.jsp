<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Aroma Shop</title>
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
	<!--================Order Details Area =================-->
	<section class="order_details section-margin--small">
		<div class="container">
			<p class="text-center billing-alert">Cảm ơn bạn. Đơn hàng bạn sẽ
				sớm được đưa cho đơn vị vận chuyển</p>
			<div class="row mb-5">
				<div class="col-md-6 col-xl-6 mb-4 mb-xl-0">
					<div class="confirmation-card">
						<h3 class="billing-title">Thông tin đơn hàng</h3>
						<table class="order-rable">
							<tr>
								<td>Mã đơn hàng</td>
								<td>: ${orderDTO.orderId}</td>
							</tr>
							<tr>
								<td>Tổng tiền</td>
								<td>: ${orderDTO.totalPrice}</td>
							</tr>
							<tr>
								<td>Phương thức thanh toán</td>
								<td>: ${orderDTO.paymentMethod}</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="col-md-6 col-xl-6 mb-4 mb-xl-0">
					<div class="confirmation-card">
						<h3 class="billing-title">Địa chỉ giao hàng</h3>
						<table class="order-rable">
							<tr>
								<td>Địa chỉ</td>
								<td>: ${orderDTO.address }</td>
							</tr>
							<tr>
								<td>Ghi chú</td>
								<td>: ${orderDTO.orderNote}</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="order_details_table">
				<h2>Chi tiết đơn hàng</h2>
				<div class="table-responsive">
					<table class="table">
						<thead>
							<tr>
								<th scope="col">Sản phẩm</th>
								<th scope="col">Số lượng</th>
								<th scope="col">Tổng tiền</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${orderDTO.items}" var="item">
								<tr>
									<td>
										<p>${item.productName}</p>
									</td>
									<td>
										<h5>x ${item.quantity}</h5>
									</td>
									<td>
										<p>${item.productPrice}</p>
									</td>
								</tr>
							</c:forEach>
							<tr>
								<td>
									<h4>Tiền hàng</h4>
								</td>
								<td>
									<h5></h5>
								</td>
								<td>
									<p>${orderDTO.subTotal}</p>
								</td>
							</tr>
							<tr>
								<td>
									<h4>Phí giao hàng</h4>
								</td>
								<td>
									<h5></h5>
								</td>
								<td>
									<p>${ orderDTO.shipping}</p>
								</td>
							</tr>
							<tr>
								<td>
									<h4>Tổng tiền</h4>
								</td>
								<td>
									<h5></h5>
								</td>
								<td>
									<h4>${orderDTO.totalPrice}</h4>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</section>
	<!--================End Order Details Area =================-->


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