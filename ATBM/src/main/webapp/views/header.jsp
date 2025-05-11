<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Aroma Shop - Header</title>
<link rel="icon" href="img/Fevicon.png" type="image/png">

<link rel="stylesheet" href="../assets/vendors/bootstrap/bootstrap.min.css">
<link rel="stylesheet" href="../assets/vendors/fontawesome/css/all.min.css">
<link rel="stylesheet"
	href="../assets/vendors/themify-icons/themify-icons.css">
<link rel="stylesheet" href="../assets/vendors/linericon/style.css">
<link rel="stylesheet"
	href="../assets/vendors/owl-carousel/owl.theme.default.min.css">
<link rel="stylesheet"
	href="../assets/vendors/owl-carousel/owl.carousel.min.css">
<link rel="stylesheet" href="../assets/vendors/nice-select/nice-select.css">
<link rel="stylesheet"
	href="../assets/vendors/nouislider/nouislider.min.css">
<link rel="stylesheet" href="../assets/css/style.css">
</head>
<body>
	<!--================ Start Header Menu Area =================-->
	<header class="header_area">
		<div class="main_menu">
			<nav class="navbar navbar-expand-lg navbar-light">
				<div class="container">
					<a class="navbar-brand logo_h" href="index.html"><img
						src="img/logo.png" alt=""></a>
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#navbarSupportedContent"
						aria-controls="navbarSupportedContent" aria-expanded="false"
						aria-label="Toggle navigation">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<div class="collapse navbar-collapse offset"
						id="navbarSupportedContent">
						<ul class="nav navbar-nav menu_nav ml-auto mr-auto">
							<li class="nav-item"><a class="nav-link" href="home.jsp">Trang
									chủ</a></li>
							<li class="nav-item"><a class="nav-link" href="shop.jsp">Cửa
									hàng</a></li>
							<li class="nav-item"><a class="nav-link" href="Blog.jsp">Blog</a></li>
							<li class="nav-item"><a class="nav-link" href="contact.html">Liên
									hệ</a></li>


							<li class="nav-item d-flex align-items-center"><a
								href="Cart.jsp" class="cart-link"> <i
									class="ti-shopping-cart"></i> <span class="nav-shop__circle"></span>
							</a></li>


							<li class="nav-item submenu dropdown"><a href="#"
								class="nav-link dropdown-toggle" data-toggle="dropdown"
								role="button" aria-haspopup="true" aria-expanded="false"><i
									class="ti-user"></i></a>
								<ul class="dropdown-menu">
									<li class="nav-item"><a class="nav-link"
										href="profile.jsp">Xem thông tin</a></li>
									<li class="nav-item"><a class="nav-link" href="account?action=logout">Đăng xuất</a></li>
								</ul></li>
						</ul>

					</div>
				</div>
			</nav>
		</div>
	</header>
	<!--================ End Header Menu Area =================-->


</body>
</html> 