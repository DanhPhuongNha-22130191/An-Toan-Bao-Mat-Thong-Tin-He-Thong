<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Aroma Shop - Product Details</title>
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
	<style>
		/* Quantity buttons styling */
		.product_count {
			margin-bottom: 30px;
		}

		.product_count .d-flex {
			border: 1px solid #e5e5e5;
			border-radius: 25px;
			display: inline-flex !important;
			align-items: center;
			padding: 0 5px;
			max-width: 150px;
			height: 45px;
			overflow: hidden;
		}
		.product_count .reduced {
			right: 80px;
		}
		.product_count .input-text.qty {
			width: 50px;
			height: 100%;
			padding: 0;
			text-align: center;
			border: none;
			background: transparent;
			font-size: 16px;
			font-weight: 500;
		}
		.product_count .items-count {
			background: transparent;
			border: none;
			cursor: pointer;
			width: 35px;
			height: 35px;
			display: flex;
			align-items: center;
			justify-content: center;
			transition: all 0.3s ease;
			border-radius: 50%;
		}
		.product_count .items-count:hover {
			background-color: #f8f8f8;
		}
		.product_count .items-count i {
			color: #888;
			font-size: 12px;
		}
		.product_count .items-count:hover i {
			color: #555;
		}

		/* Add to Cart button styling */
		.button.primary-btn {
			background: #384aeb;
			border: none;
			color: white;
			border-radius: 30px;
			padding: 12px 30px;
			font-weight: 500;
			font-size: 14px;
			text-transform: uppercase;
			letter-spacing: 1px;
			display: inline-block;
			position: relative;
			overflow: hidden;
			transition: all 0.3s ease;
			box-shadow: 0 5px 15px rgba(56, 74, 235, 0.2);
			cursor: pointer;
			outline: none;
		}
		.button.primary-btn::before {
			content: "";
			position: absolute;
			top: 0;
			left: -100%;
			width: 100%;
			height: 100%;
			background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.2), transparent);
			transition: all 0.6s ease;
		}
		.button.primary-btn:hover {
			background: #2a3ad0;
			transform: translateY(-3px);
			box-shadow: 0 8px 20px rgba(56, 74, 235, 0.3);
		}
		.button.primary-btn:hover::before {
			left: 100%;
		}
		.button.primary-btn:active {
			transform: translateY(-1px);
			box-shadow: 0 5px 15px rgba(56, 74, 235, 0.2);
		}
	</style>
</head>
<body>
<jsp:include page="/views/header.jsp" />

<!-- ================ start banner area ================= -->
<section class="blog-banner-area" id="blog">
	<div class="container h-100">
		<div class="blog-banner">
			<div class="text-center">
				<h1>Product Details</h1>
				<nav aria-label="breadcrumb" class="banner-breadcrumb">
					<ol class="breadcrumb">
						<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
						<li class="breadcrumb-item active" aria-current="page">Shop Single</li>
					</ol>
				</nav>
			</div>
		</div>
	</div>
</section>
<!-- ================ end banner area ================= -->

<!-- Hiển thị thông báo thành công (nếu có) -->
<c:if test="${not empty sessionScope.message}">
	<div class="container mt-3">
		<div class="alert alert-success" role="alert">
				${sessionScope.message}
		</div>
		<c:remove var="message" scope="session" />
	</div>
</c:if>

<!--================Single Product Area =================-->
<div class="product_image_area">
	<div class="container">
		<div class="row s_product_inner">
			<!-- Ảnh sản phẩm -->
			<div class="col-lg-6">
				<div class="single-prd-item">
					<img class="img-fluid" src="${product.image}" alt="${product.name}">
				</div>
			</div>
			<!-- Thông tin sản phẩm -->
			<div class="col-lg-5 offset-lg-1">
				<div class="s_product_text">
					<h3>${product.name}</h3>
					<h2>$${product.price}</h2>

					<p>${product.description}</p>
					<div class="product_count">
						<!-- Sửa lại form action để giống như shop.jsp -->
						<form action="${pageContext.request.contextPath}/user/cart" method="post">
							<input type="hidden" name="productId" value="${product.productId}">
							<div class="mb-3">
								<label for="qty">Quantity:</label>
								<div class="d-flex align-items-center">
									<button
											type="button"
											class="reduced items-count"
											onclick="decreaseQuantity()"
											style="top:8px;">
										<i class="ti-angle-left"></i>
									</button>
									<input
											type="text"
											name="quantity"
											id="sst"
											size="2"
											maxlength="12"
											value="1"
											title="Quantity:"
											class="input-text qty">
									<button
											type="button"
											class="increase items-count"
											onclick="increaseQuantity()"
											style="top:8px;">
										<i class="ti-angle-right"></i>
									</button>
								</div>
							</div>
							<button type="submit" class="button primary-btn">Add to Cart</button>
						</form>
					</div>
					<div class="card_area d-flex align-items-center">
						<a class="icon_btn" href="#"><i class="lnr lnr-diamond"></i></a>
						<a class="icon_btn" href="#"><i class="lnr lnr-heart"></i></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!--================End Single Product Area =================-->

<!--================Product Description Area =================-->
<section class="product_description_area">
	<div class="container">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item">
				<a class="nav-link" id="home-tab" data-toggle="tab" href="#home" role="tab"
				   aria-controls="home" aria-selected="true">Description</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab"
				   aria-controls="profile" aria-selected="false">Specification</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" id="contact-tab" data-toggle="tab" href="#contact" role="tab"
				   aria-controls="contact" aria-selected="false">Comments</a>
			</li>
			<li class="nav-item">
				<a class="nav-link active" id="review-tab" data-toggle="tab" href="#review" role="tab"
				   aria-controls="review" aria-selected="false">Reviews</a>
			</li>
		</ul>
		<div class="tab-content" id="myTabContent">
			<!-- Tab "Description" -->
			<div class="tab-pane fade" id="home" role="tabpanel" aria-labelledby="home-tab">
				<p>Beryl Cook is one of Britain's most talented and amusing artists. Beryl's pictures feature women of all shapes
					and sizes enjoying themselves. Born between the two world wars, Beryl Cook eventually left Kendrick School in
					Reading at the age of 15, where she went to secretarial school and then into an insurance office. After moving to
					London and then Hampton, she eventually married her next door neighbour from Reading, John Cook. He was an
					officer in the Merchant Navy and after he left the sea in 1956, they bought a pub for a year before John took a
					job in Southern Rhodesia with a motor company. Beryl bought their young son a box of watercolours, and when
					showing him how to use it, she decided that she herself quite enjoyed painting. John subsequently bought her a
					child's painting set for her birthday and it was with this that she produced her first significant work, a
					half-length portrait of a dark-skinned lady with a vacant expression and large drooping breasts. It was aptly
					named 'Hangover' by Beryl's husband.</p>
				<p>It is often frustrating to attempt to plan meals that are designed for one. Despite this fact, we are seeing
					more and more recipe books and Internet websites that are dedicated to the act of cooking for one. Divorce and
					the death of spouses or grown children leaving for college are all reasons that someone accustomed to cooking for
					more than one would suddenly need to learn how to adjust all the cooking practices utilized before into a
					streamlined plan of cooking that is more efficient for one person creating less waste.</p>
			</div>

			<!-- Tab "Specification" -->
			<div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">
				<div class="table-responsive">
					<table class="table">
						<tbody>
						<tr>
							<td><h5>Width</h5></td>
							<td><h5>128mm</h5></td>
						</tr>
						<tr>
							<td><h5>Height</h5></td>
							<td><h5>508mm</h5></td>
						</tr>
						<tr>
							<td><h5>Depth</h5></td>
							<td><h5>85mm</h5></td>
						</tr>
						<tr>
							<td><h5>Weight</h5></td>
							<td><h5>52gm</h5></td>
						</tr>
						<tr>
							<td><h5>Quality checking</h5></td>
							<td><h5>yes</h5></td>
						</tr>
						<tr>
							<td><h5>Freshness Duration</h5></td>
							<td><h5>03days</h5></td>
						</tr>
						<tr>
							<td><h5>When packing</h5></td>
							<td><h5>Without touch of hand</h5></td>
						</tr>
						<tr>
							<td><h5>Each Box contains</h5></td>
							<td><h5>60pcs</h5></td>
						</tr>
						</tbody>
					</table>
				</div>
			</div>

			<!-- Tab "Comments" -->
			<div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="contact-tab">
				<div class="row">
					<div class="col-lg-6">
						<div class="comment_list">
							<div class="review_item">
								<div class="media">
									<div class="d-flex">
										<img src="${pageContext.request.contextPath}/assets/img/product/review-1.png" alt="">
									</div>
									<div class="media-body">
										<h4>Blake Ruiz</h4>
										<h5>12th Feb, 2018 at 05:56 pm</h5>
										<a class="reply_btn" href="#">Reply</a>
									</div>
								</div>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et
									dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									commodo</p>
							</div>
							<div class="review_item reply">
								<div class="media">
									<div class="d-flex">
										<img src="${pageContext.request.contextPath}/assets/img/product/review-2.png" alt="">
									</div>
									<div class="media-body">
										<h4>Blake Ruiz</h4>
										<h5>12th Feb, 2018 at 05:56 pm</h5>
										<a class="reply_btn" href="#">Reply</a>
									</div>
								</div>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et
									dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									commodo</p>
							</div>
							<div class="review_item">
								<div class="media">
									<div class="d-flex">
										<img src="${pageContext.request.contextPath}/assets/img/product/review-3.png" alt="">
									</div>
									<div class="media-body">
										<h4>Blake Ruiz</h4>
										<h5>12th Feb, 2018 at 05:56 pm</h5>
										<a class="reply_btn" href="#">Reply</a>
									</div>
								</div>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et
									dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									commodo</p>
							</div>
						</div>
					</div>
					<div class="col-lg-6">
						<div class="review_box">
							<h4>Post a comment</h4>
							<form class="row contact_form" action="contact_process.php" method="post" id="contactForm" novalidate="novalidate">
								<div class="col-md-12">
									<div class="form-group">
										<input type="text" class="form-control" id="name" name="name" placeholder="Your Full name" required>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<input type="email" class="form-control" id="email" name="email" placeholder="Email Address" required>
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<input type="text" class="form-control" id="number" name="number" placeholder="Phone Number">
									</div>
								</div>
								<div class="col-md-12">
									<div class="form-group">
										<textarea class="form-control" name="message" id="message" rows="1" placeholder="Message"></textarea>
									</div>
								</div>
								<div class="col-md-12 text-right">
									<button type="submit" value="submit" class="btn primary-btn">Submit Now</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>

			<!-- Tab "Reviews" -->
			<div class="tab-pane fade show active" id="review" role="tabpanel" aria-labelledby="review-tab">
				<div class="row">
					<div class="col-lg-6">
						<div class="row total_rate">
							<div class="col-6">
								<div class="box_total">
									<h5>Overall</h5>
									<h4>4.0</h4>
									<h6>(03 Reviews)</h6>
								</div>
							</div>
							<div class="col-6">
								<div class="rating_list">
									<h3>Based on 3 Reviews</h3>
									<ul class="list">
										<li><a href="#">5 Star <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i
												class="fa fa-star"></i><i class="fa fa-star"></i> 01</a></li>
										<li><a href="#">4 Star <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i
												class="fa fa-star"></i><i class="fa fa-star"></i> 01</a></li>
										<li><a href="#">3 Star <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i
												class="fa fa-star"></i><i class="fa fa-star"></i> 01</a></li>
										<li><a href="#">2 Star <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i
												class="fa fa-star"></i><i class="fa fa-star"></i> 01</a></li>
										<li><a href="#">1 Star <i class="fa fa-star"></i><i class="fa fa-star"></i><i class="fa fa-star"></i><i
												class="fa fa-star"></i><i class="fa fa-star"></i> 01</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="review_list">
							<div class="review_item">
								<div class="media">
									<div class="d-flex">
										<img src="${pageContext.request.contextPath}/assets/img/product/review-1.png" alt="">
									</div>
									<div class="media-body">
										<h4>Blake Ruiz</h4>
										<i class="fa fa-star"></i>
										<i class="fa fa-star"></i>
										<i class="fa fa-star"></i>
										<i class="fa fa-star"></i>
										<i class="fa fa-star"></i>
									</div>
								</div>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et
									dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									commodo</p>
							</div>
							<div class="review_item">
								<div class="media">
									<div class="d-flex">
										<img src="${pageContext.request.contextPath}/assets/img/product/review-2.png" alt="">
									</div>
									<div class="media-body">
										<h4>Blake Ruiz</h4>
										<i class="fa fa-star"></i>
										<i class="fa fa-star"></i>
										<i class="fa fa-star"></i>
										<i class="fa fa-star"></i>
										<i class="fa fa-star"></i>
									</div>
								</div>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et
									dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									commodo</p>
							</div>
							<div class="review_item">
								<div class="media">
									<div class="d-flex">
										<img src="${pageContext.request.contextPath}/assets/img/product/review-3.png" alt="">
									</div>
									<div class="media-body">
										<h4>Blake Ruiz</h4>
										<i class="fa fa-star"></i>
										<i class="fa fa-star"></i>
										<i class="fa fa-star"></i>
										<i class="fa fa-star"></i>
										<i class="fa fa-star"></i>
									</div>
								</div>
								<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et
									dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									commodo</p>
							</div>
						</div>
					</div>
					<div class="col-lg-6">
						<div class="review_box">
							<h4>Add a Review</h4>
							<p>Your Rating:</p>
							<ul class="list">
								<li><a href="#"><i class="fa fa-star"></i></a></li>
								<li><a href="#"><i class="fa fa-star"></i></a></li>
								<li><a href="#"><i class="fa fa-star"></i></a></li>
								<li><a href="#"><i class="fa fa-star"></i></a></li>
								<li><a href="#"><i class="fa fa-star"></i></a></li>
							</ul>
							<p>Outstanding</p>
							<form action="#/" class="form-contact form-review mt-3">
								<div class="form-group">
									<input class="form-control" name="name" type="text" placeholder="Enter your name" required>
								</div>
								<div class="form-group">
									<input class="form-control" name="email" type="email" placeholder="Enter email address" required>
								</div>
								<div class="form-group">
									<input class="form-control" name="subject" type="text" placeholder="Enter Subject">
								</div>
								<div class="form-group">
									<textarea class="form-control different-control w-100" name="textarea" id="textarea" cols="30" rows="5" placeholder="Enter Message"></textarea>
								</div>
								<div class="form-group text-center text-md-right mt-3">
									<button type="submit" class="button button--active button-review">Submit Now</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!--================End Product Description Area =================-->

<!--================ Start related Product area =================-->
<section class="related-product-area section-margin--small mt-0">
	<div class="container">
		<div class="section-intro pb-60px">
			<p>Popular Item in the market</p>
			<h2>Top <span class="section-intro__style">Product</span></h2>
		</div>
		<div class="row mt-30">
			<c:forEach var="item" items="${relatedProducts}">
				<div class="col-sm-6 col-xl-3 mb-4 mb-xl-0">
					<div class="single-search-product-wrapper">
						<c:forEach var="img" items="${item.smallImages}">
							<div class="single-search-product d-flex">
								<a href="${pageContext.request.contextPath}/product?action=details&id=${item.productId}">
									<img src="${img}" alt="${item.name}">
								</a>
								<div class="desc">
									<a href="${pageContext.request.contextPath}/product?action=details&id=${item.productId}" class="title">${item.name}</a>
									<div class="price">$${item.price}</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>
<!--================ end related Product area =================-->

<jsp:include page="/views/footer.jsp" />

<script src="${pageContext.request.contextPath}/assets/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/skrollr.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/owl-carousel/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/nice-select/jquery.nice-select.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/jquery.ajaxchimp.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/mail-script.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
<script>
	function increaseQuantity() {
		var result = document.getElementById('sst');
		var sst = parseInt(result.value);
		if (!isNaN(sst)) {
			result.value = sst + 1;
		}
		return false;
	}

	function decreaseQuantity() {
		var result = document.getElementById('sst');
		var sst = parseInt(result.value);
		if (!isNaN(sst) && sst > 1) {
			result.value = sst - 1;
		}
		return false;
	}
</script>
</body>
</html>