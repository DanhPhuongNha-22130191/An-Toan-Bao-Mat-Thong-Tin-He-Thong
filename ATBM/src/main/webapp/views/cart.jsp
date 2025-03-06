<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Aroma Shop - Cart</title>
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
	<!--================Cart Area =================-->
	<section class="cart_area">
		<div class="container">
			<div class="cart_inner">
				<div class="table-responsive">
					<table class="table">
						<thead>
							<tr>
								<th scope="col">Tên sản phẩm</th>
								<th scope="col">Giá sản phẩm</th>
								<th scope="col">Số lượng</th>
								<th scope="col">Tổng giá</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="cart" items="${cartDTO.items}">
								<tr class="cart-item" data-id="${cart.productId}">
									<td>
										<div class="media">
											<div class="d-flex">
												<img src="${cart.productImg}" alt="">
											</div>
											<div class="media-body">
												<p>${cart.productName}</p>
											</div>
										</div>
									</td>
									<td>
										<h5>${cart.productPrice}</h5>
									</td>
									<td>
										<div class="product_count">
											<input type="text" name="qty" id="sst-${cart.productId}"
												maxlength="12" value="${cart.quantity}" title="Quantity:"
												class="input-text qty">
											<button
												onclick="var result = document.getElementById('sst-${cart.productId}'); var sst = result.value; if( !isNaN( sst )) result.value++;return false;"
												class="increase items-count" type="button">
												<i class="lnr lnr-chevron-up"></i>
											</button>
											<button
												onclick="var result = document.getElementById('sst-${cart.productId}'); var sst = result.value; if( !isNaN( sst ) && sst > 0 ) result.value--;return false;"
												class="reduced items-count" type="button">
												<i class="lnr lnr-chevron-down"></i>
											</button>
										</div>
									</td>
									<td>
										<h5>${cart.totalPrice}</h5>
									</td>
								</tr>
							</c:forEach>
							<tr class="bottom_button">
								<td><button id="update-cart" class="button">Cập
										nhật giỏ hàng</button></td>
								<td></td>
								<td></td>
								<td>
									<div class="cupon_text d-flex align-items-center">
										<input type="text" placeholder="Coupon Code"> <a
											class="primary-btn" href="#">Áp dụng</a>
									</div>
								</td>
							</tr>
							<tr>
								<td></td>
								<td></td>
								<td>
									<h5>Tổng tiền hàng</h5>
								</td>
								<td>
									<h5>$2160.00</h5>
								</td>
							</tr>
							<tr class="shipping_area">
								<td class="d-none d-md-block"></td>
								<td></td>
								<td>
									<h5>Phí giao hàng</h5>
								</td>
								<td>
									<div class="shipping_box">
										<ul class="list">
											<li>Miễn phí giao hàng</li>
										</ul>
									</div>
								</td>
							</tr>
							<tr class="out_button_area">
								<td class="d-none-l"></td>
								<td class=""></td>
								<td></td>
								<td>
									<div class="checkout_btn_inner d-flex align-items-center">
										<a class="gray_btn" href="#">Tiếp tục mua sắm</a> <a
											class="primary-btn ml-2" href="#">Thanh toán</a>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</section>
	<!--================End Cart Area =================-->


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
	<script type="text/javascript">
	document.getElementById("update-cart").addEventListener("click", function () {
	    let items = document.querySelectorAll(".cart-item");
	    let params = new URLSearchParams();
	    items.forEach(item => {
	        let id = item.getAttribute("data-id");
	        let quantity = document.getElementById("sst-"+id).value;
	        params.append("product-quantity", id+'-'+quantity); 
	    });

	    fetch("/ATBM/user/cart", { // Đảm bảo đường dẫn đúng
	        method: "POST",
	        headers: {
	            "Content-Type": "application/x-www-form-urlencoded"
	        },
	        body: params.toString()
	    })
	    .then(response => response.text())
	    .then(data => {
	        alert("Cập nhật giỏ hàng thành công");
	        location.reload();
	    })
	    .catch(error => console.error("Lỗi cập nhật:", error));
	});

	</script>
</body>
</html>