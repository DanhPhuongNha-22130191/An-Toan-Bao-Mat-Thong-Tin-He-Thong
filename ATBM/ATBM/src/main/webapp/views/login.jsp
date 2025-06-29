<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Aroma Shop - Login</title>
	<link rel="icon" href="${pageContext.request.contextPath}/img/Fevicon.png" type="image/png">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/fontawesome/css/all.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
	<style>
		.modal {
			position: fixed;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
			width: 350px;
			max-height: max-content;
			background: white;
			padding: 25px;
			box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
			border-radius: 10px;
			z-index: 1000;
			display: none;
		}
		.modal h4 {
			margin-bottom: 15px;
			text-align: center;
		}
		.modal form {
			display: flex;
			flex-direction: column;
			gap: 12px;
		}
		.modal input[type="email"] {
			padding: 10px;
			border: 1px solid #ccc;
			border-radius: 5px;
			font-size: 16px;
		}
		.button-reset {
			background: #007bff;
			color: white;
			padding: 10px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
			font-size: 16px;
			transition: background 0.3s ease-in-out;
		}
		.button-reset:hover {
			background: #0056b3;
		}
		.close {
			float: right;
			font-size: 20px;
			font-weight: bold;
			cursor: pointer;
		}
	</style>
	<script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body>
<section class="login_box_area section-margin">
	<div class="container">
		<div class="row">
			<div class="col-lg-6">
				<div class="login_box_img">
					<div class="hover">
						<h4>Mới sử dụng trang web của chúng tôi?</h4>
						<p>Hãy đăng ký ngay để khám phá nhiều tính năng hấp dẫn!</p>
						<a href="${pageContext.request.contextPath}/register" style="color: whitesmoke">Tạo tài khoản</a>
					</div>
				</div>
			</div>
			<div class="col-lg-6">
				<div class="login_form_inner">
					<h3>Đăng nhập để tiếp tục</h3>
					<c:if test="${not empty error}">
						<p style="color: red;">${error}</p>
					</c:if>
					<c:if test="${param.success eq 'registered'}">
						<p style="color: green;">Đăng ký thành công! Vui lòng đăng nhập.</p>
					</c:if>
					<form class="row login_form" action="${pageContext.request.contextPath}/login" method="post">
						<div class="col-md-12 form-group">
							<input type="text" class="form-control" name="username" placeholder="Tên đăng nhập" required>
						</div>
						<div class="col-md-12 form-group">
							<input type="password" class="form-control" name="password" placeholder="Mật khẩu" required class="form-control" required>
						</div>
						<div class="col-md-12 form-group">
							<div class="g-recaptcha" data-sitekey="6LcooTwrAAAAAEudofsBFa634LNkfxLdH3804Pqa"></div>
						</div>
						<div class="col-md-12 form-group">
							<button type="submit" class="button button-login w-100">Đăng nhập</button>
						</div>
						<div class="col-md-12 form-group text-center">
							<a href="#" id="forgotPasswordLink">Quên mật khẩu?</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>
<div id="forgotPasswordModal" class="modal">
	<span class="close">×</span>
	<h4>Khôi phục mật khẩu</h4>
	<p>Nhập email của bạn để nhận hướng dẫn đặt lại mật khẩu:</p>
	<form id="forgotPasswordForm" action="${pageContext.request.contextPath}/forgot-password" method="post">
		<input type="email" name="email" placeholder="Nhập email của bạn" required class="form-control">
		<div class="col-md-12 form-group">
			<div class="g-recaptcha" data-sitekey="6LcooTwrAAAAAEudofsBFa634LNkfxLdH3804Pqa"></div>
		</div>
		<button type="submit" class="button-reset">Gửi</button>
	</form>
	<p id="forgotPasswordMessage" style="color: green; display: none;">✅ Yêu cầu đã được gửi!</p>
</div>
<script>
	document.getElementById("forgotPasswordLink").addEventListener("click", function() {
		document.getElementById("forgotPasswordModal").style.display = "block";
	});
	document.querySelector(".close").addEventListener("click", function() {
		document.getElementById("forgotPasswordModal").style.display = "none";
	});
</script>
<script src="${pageContext.request.contextPath}/assets/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>