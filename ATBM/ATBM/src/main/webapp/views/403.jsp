<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>403 - Không có quyền truy cập</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #333;
        }

        .error-container {
            text-align: center;
            background: rgba(255, 255, 255, 0.95);
            padding: 40px 30px;
            border-radius: 15px;
            box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
            max-width: 450px;
            width: 90%;
            backdrop-filter: blur(10px);
            animation: slideUp 0.8s ease-out;
        }

        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .error-code {
            font-size: 6rem;
            font-weight: 900;
            color: #2a5298;
            margin-bottom: 15px;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
            animation: pulse 2s infinite;
        }

        @keyframes pulse {
            0%, 100% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.05);
            }
        }

        .error-title {
            font-size: 2rem;
            color: #333;
            margin-bottom: 15px;
            font-weight: 700;
        }

        .error-message {
            font-size: 1rem;
            color: #666;
            margin-bottom: 30px;
            line-height: 1.5;
        }

        .buttons {
            display: flex;
            gap: 20px;
            justify-content: center;
            flex-wrap: wrap;
        }

        .btn {
            padding: 12px 25px;
            font-size: 0.9rem;
            border: none;
            border-radius: 50px;
            cursor: pointer;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            transition: all 0.3s ease;
            font-weight: 600;
        }

        .btn-primary {
            background: linear-gradient(45deg, #1e3c72, #2a5298);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(42, 82, 152, 0.4);
        }

        .btn-secondary {
            background: #f8f9fa;
            color: #333;
            border: 2px solid #ddd;
        }

        .btn-secondary:hover {
            background: #e9ecef;
            transform: translateY(-2px);
        }

        .btn-login {
            background: linear-gradient(45deg, #28a745, #20c997);
            color: white;
        }

        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(40, 167, 69, 0.4);
        }

        .error-icon {
            font-size: 3rem;
            margin-bottom: 15px;
            opacity: 0.7;
        }



        .helpful-links {
            margin-top: 30px;
            padding-top: 25px;
            border-top: 1px solid #eee;
        }

        .helpful-links h3 {
            color: #333;
            margin-bottom: 12px;
            font-size: 1rem;
        }

        .helpful-links ul {
            list-style: none;
            display: flex;
            justify-content: center;
            gap: 30px;
            flex-wrap: wrap;
        }

        .helpful-links a {
            color: #2a5298;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .helpful-links a:hover {
            color: #1e3c72;
            text-decoration: underline;
        }

        @media (max-width: 768px) {
            .error-container {
                padding: 30px 20px;
            }

            .error-code {
                font-size: 5rem;
            }

            .error-title {
                font-size: 1.8rem;
            }

            .error-message {
                font-size: 0.95rem;
            }

            .buttons {
                flex-direction: column;
                align-items: center;
            }

            .btn {
                width: 100%;
                max-width: 250px;
            }

            .helpful-links ul {
                flex-direction: column;
                gap: 15px;
            }
        }
    </style>
</head>
<body>
<div class="error-container">
    <div class="error-icon">🔒</div>
    <div class="error-code">403</div>
    <h1 class="error-title">Truy cập bị từ chối</h1>
    <p class="error-message">
        Bạn không có quyền truy cập vào trang này.
        <br>Vui lòng liên hệ quản trị viên hoặc đăng nhập với tài khoản phù hợp.
    </p>

    <div class="buttons">
        <a href="${pageContext.request.contextPath}/login" class="btn btn-login">
            🔑 Đăng nhập
        </a>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
            🏠 Về trang chủ
        </a>
        <button onclick="history.back()" class="btn btn-secondary">
            ← Quay lại
        </button>
    </div>

    <div class="helpful-links">
        <h3>Liên kết hữu ích</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/">Trang chủ</a></li>
            <li><a href="${pageContext.request.contextPath}/contact">Liên hệ</a></li>
            <li><a href="${pageContext.request.contextPath}/help">Trợ giúp</a></li>
            <li><a href="${pageContext.request.contextPath}/support">Hỗ trợ</a></li>
        </ul>
    </div>
</div>

<script>
    // Hiệu ứng pulse cho error code khi click
    document.querySelector('.error-code').addEventListener('click', function() {
        this.style.animation = 'none';
        setTimeout(() => {
            this.style.animation = 'pulse 2s infinite';
        }, 10);
    });

    // Auto focus nút đăng nhập nếu người dùng chưa đăng nhập
    document.addEventListener('DOMContentLoaded', function() {
        const loginBtn = document.querySelector('.btn-login');
        if (loginBtn) {
            loginBtn.focus();
        }
    });

    // Log thông tin lỗi để debugging
    <c:if test="${not empty requestScope['javax.servlet.error.exception']}">
    console.log('Access Denied Details:', {
        requestURI: '${requestScope["javax.servlet.error.request_uri"]}',
        statusCode: '${requestScope["javax.servlet.error.status_code"]}',
        message: '${requestScope["javax.servlet.error.message"]}',
        userPrincipal: '${pageContext.request.userPrincipal != null ? pageContext.request.userPrincipal.name : "Anonymous"}'
    });
    </c:if>

    <c:if test="${not empty pageContext.request.userPrincipal}">
    console.log('Current user: ${pageContext.request.userPrincipal.name}');
    </c:if>
</script>
</body>
</html>