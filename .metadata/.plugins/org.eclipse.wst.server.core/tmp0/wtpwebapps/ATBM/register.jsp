<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aroma Shop - Register</title>
    <link rel="stylesheet" href="assets/vendors/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>

<section class="login_box_area section-margin">
    <div class="container">
        <div class="row">
            <div class="col-lg-6">
                <div class="login_box_img">
                    <div class="hover">
                        <h4>Bạn đã có tài khoản?</h4>
                        <p>Đăng nhập ngay để tiếp tục!</p>
                        <a class="button button-account" href="login.jsp">Đăng nhập ngay</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="login_form_inner register_form_inner">
                    <h3>Tạo tài khoản</h3>
                    <form class="row login_form" action="account" method="post" id="register_form">
                        <input type="hidden" name="action" value="register">
                        
                        <div class="col-md-12 form-group">
                            <input type="text" class="form-control" name="username" placeholder="Tên đăng nhập" required>
                        </div>
                        <div class="col-md-12 form-group">
                            <input type="email" class="form-control" name="email" placeholder="Địa chỉ email" required>
                        </div>
                        <div class="col-md-12 form-group">
                            <input type="password" class="form-control" name="password" placeholder="Mật khẩu" required>
                        </div>
                        <div class="col-md-12 form-group">
                            <input type="password" class="form-control" name="confirmPassword" placeholder="Xác nhận mật khẩu" required>
                        </div>
                        
                        <div class="col-md-12 form-group">
                            <button type="submit" class="button button-register w-100">Đăng ký</button>
                        </div>
                    </form>

                    <c:if test="${not empty error}">
                        <p style="color: red;">${error}</p>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="assets/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="assets/vendors/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>
