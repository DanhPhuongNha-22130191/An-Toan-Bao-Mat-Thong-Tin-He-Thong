<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aroma Shop - Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/vendors/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
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
                        <a href="${pageContext.request.contextPath}/login" style="color: whitesmoke">Đăng nhập ngay</a>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="login_form_inner register_form_inner">
                    <h3>Tạo tài khoản</h3>
                    <form class="row login_form" action="${pageContext.request.contextPath}/register" method="post"
                          id="register_form">
                        <div class="col-md-12 form-group">
                            <input type="text" class="form-control" name="username" placeholder="Tên đăng nhập"
                                   required>
                        </div>
                        <div class="col-md-12 form-group">
                            <input type="email" class="form-control" name="email" placeholder="Địa chỉ email" required>
                        </div>
                        <div class="col-md-12 form-group">
                            <input type="password" class="form-control" name="password" placeholder="Mật khẩu" required>
                        </div>
                        <div class="col-md-12 form-group">
                            <input type="password" class="form-control" name="confirmPassword"
                                   placeholder="Xác nhận mật khẩu" required>
                        </div>
                        <div class="col-md-12 form-group">
                            <div class="g-recaptcha" data-sitekey="6LcooTwrAAAAAEudofsBFa634LNkfxLdH3804Pqa"></div>
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
<script src="${pageContext.request.contextPath}/assets/vendors/jquery/jquery-3.2.1.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendors/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>