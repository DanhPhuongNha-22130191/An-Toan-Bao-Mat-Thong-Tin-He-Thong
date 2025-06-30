<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Aroma Shop - Header</title>
	<link rel="icon" href="img/Fevicon.png" type="image/png">
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
		.header_area {
			height: 50px;
			background: #fff;
			box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
			transition: height 0.3s ease, padding 0.3s ease;
			padding: 10px 0;
			will-change: height, padding;
		}
		.header_area.scrolled {
			height: 40px;
			padding: 5px 0;
		}
		.sticky-header {
			position: fixed;
			top: 0;
			width: 100%;
			z-index: 1000;
		}
		body {
			padding-top: 50px;
			transition: padding-top 0.3s ease;
		}
		body.header-scrolled {
			padding-top: 40px;
		}
		.main_menu {
			height: 100%;
		}
		.navbar {
			padding: 0 15px;
		}
		.nav-link {
			padding: 10px 15px !important;
			font-size: 14px;
			transition: font-size 0.3s ease, padding 0.3s ease;
			will-change: font-size, padding;
		}
		.header_area.scrolled .nav-link {
			font-size: 12px !important;
			padding: 8px 10px !important;
		}
		.submenu .dropdown-menu .nav-link {
			font-size: 14px;
			padding: 8px 20px;
			transition: font-size 0.3s ease, padding 0.3s ease;
			will-change: font-size, padding;
		}
		.header_area.scrolled .submenu .dropdown-menu .nav-link {
			font-size: 12px !important;
			padding: 4px 10px !important;
		}
		.navbar-brand img {
			max-height: 50px;
			transition: max-height 0.3s ease;
			will-change: max-height;
		}
		.header_area.scrolled .navbar-brand img {
			max-height: 30px;
		}
		@media (max-width: 991px) {
			.header_area {
				height: auto;
				padding: 10px 0;
			}
			.header_area.scrolled {
				height: auto;
				padding: 10px 0;
			}
			body {
				padding-top: 10px;
			}
		}
	</style>
</head>
<body>
<header class="header_area sticky-header">
	<div class="main_menu">
		<nav class="navbar navbar-expand-lg navbar-light">
			<div class="container">
				<a class="navbar-brand logo_h" href="${pageContext.request.contextPath}/home"><img src="${pageContext.request.contextPath}/img/logo.png" alt=""></a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#navbarSupportedContent"
						aria-controls="navbarSupportedContent" aria-expanded="false"
						aria-label="Toggle navigation">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<div class="collapse navbar-collapse offset" id="navbarSupportedContent">
					<ul class="nav navbar-nav menu_nav ml-auto mr-auto">
						<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
						<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/shop/product">Lọc</a></li>
						<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/views/introduce.jsp">Tool mã hóa</a></li>
						<li class="nav-item d-flex align-items-center">
							<a href="${pageContext.request.contextPath}/user/cart" class="cart-link">
								<i class="ti-shopping-cart"></i> <span class="nav-shop__circle"></span>
							</a>
						</li>
						<c:choose>
							<c:when test="${empty sessionScope.user}">
								<li class="nav-item">
									<a class="nav-link" href="${pageContext.request.contextPath}/login">
										<i class="ti-lock"></i> Đăng nhập
									</a>
								</li>
								<li class="nav-item">
									<a class="nav-link" href="${pageContext.request.contextPath}/register">
										<i class="ti-user"></i> Đăng ký
									</a>
								</li>
							</c:when>
							<c:otherwise>
								<li class="nav-item submenu dropdown">
									<a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
										<i class="ti-user"></i> ${sessionScope.user.username}
									</a>
									<ul class="dropdown-menu">
										<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/user/info">Xem thông tin</a></li>
										<li class="nav-item">
											<form action="${pageContext.request.contextPath}/user/logout" method="post" style="margin: 0;">
												<button type="submit" class="nav-link" style="background: none; border: none; padding: 0; color: inherit;">Đăng xuất</button>
											</form>
										</li>
									</ul>
								</li>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
		</nav>
	</div>
</header>
<script src="${pageContext.request.contextPath}/assets/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/bootstrap/bootstrap.bundle.min.js"></script>
<script>
	document.addEventListener('DOMContentLoaded', function () {
		const header = document.querySelector('.header_area');
		const bodyTag = document.body;
		const scrollThreshold = 50;
		window.addEventListener('scroll', function () {
			const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
			if (scrollTop > scrollThreshold) {
				if (!header.classList.contains('scrolled')) {
					header.classList.add('scrolled');
					bodyTag.classList.add('header-scrolled');
				}
			} else {
				if (header.classList.contains('scrolled')) {
					header.classList.remove('scrolled');
					bodyTag.classList.remove('header-scrolled');
				}
			}
		});
	});
</script>
</body>
</html>