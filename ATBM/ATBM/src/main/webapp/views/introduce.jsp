<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Hướng Dẫn Sử Dụng Website Bán Đồng Hồ</title>
<%--  <style>--%>
<%--    body {--%>
<%--      font-family: 'Georgia', serif;--%>
<%--      line-height: 1.6;--%>
<%--      margin: 0;--%>
<%--      padding: 20px;--%>
<%--      background-color: #FFFFFF;--%>
<%--      color: #333333;--%>
<%--    }--%>
<%--    .container {--%>
<%--      max-width: 800px;--%>
<%--      margin: auto;--%>
<%--      background: #F8F8F8;--%>
<%--      padding: 20px;--%>
<%--      border-radius: 10px;--%>
<%--      box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);--%>
<%--      border: 1px solid #D4AF37;--%>
<%--    }--%>
<%--    h1, h2 {--%>
<%--      color: #D4AF37;--%>
<%--      font-weight: bold;--%>
<%--    }--%>
<%--    h1 {--%>
<%--      text-align: center;--%>
<%--      font-size: 2.2em;--%>
<%--      border-bottom: 2px solid #D4AF37;--%>
<%--      padding-bottom: 10px;--%>
<%--    }--%>
<%--    h2 {--%>
<%--      font-size: 1.5em;--%>
<%--      margin-top: 20px;--%>
<%--    }--%>
<%--    ol {--%>
<%--      margin: 20px 0;--%>
<%--      padding-left: 20px;--%>
<%--    }--%>
<%--    li {--%>
<%--      margin-bottom: 10px;--%>
<%--    }--%>
<%--    .note {--%>
<%--      background-color: #FFF8E7;--%>
<%--      border-left: 6px solid #D4AF37;--%>
<%--      padding: 10px;--%>
<%--      margin: 10px 0;--%>
<%--      color: #333333;--%>
<%--    }--%>
<%--    .footer {--%>
<%--      text-align: center;--%>
<%--      margin-top: 20px;--%>
<%--      font-size: 0.9em;--%>
<%--      color: #D4AF37;--%>
<%--    }--%>
<%--    a {--%>
<%--      color: #D4AF37;--%>
<%--      text-decoration: none;--%>
<%--    }--%>
<%--    a:hover {--%>
<%--      text-decoration: underline;--%>
<%--      color: #B8860B;--%>
<%--    }--%>
<%--  </style>--%>
</head>
<body>
<!--================ Start Header Menu Area =================-->
<jsp:include page="/views/header.jsp" />
<!--================ End Header Menu Area =================-->

<div class="container">
  <h1>Hướng Dẫn Sử Dụng Website Bán Đồng Hồ</h1>
  <p>Chào mừng bạn đến với website bán đồng hồ của chúng tôi! Dưới đây là hướng dẫn chi tiết để bạn dễ dàng sử dụng các tính năng trên website.</p>

  <h2>1. Duyệt và Tìm Kiếm Sản Phẩm</h2>
  <ol>
    <li><strong>Truy cập trang chủ:</strong> Mở trình duyệt và nhập địa chỉ website của chúng tôi (ví dụ: www.donghoabc.com).</li>
    <li><strong>Duyệt danh mục:</strong> Sử dụng thanh menu ở đầu trang để chọn các loại đồng hồ (Đồng hồ nam, Đồng hồ nữ, Đồng hồ đôi, v.v.).</li>
    <li><strong>Tìm kiếm sản phẩm:</strong> Nhập tên thương hiệu, mẫu mã hoặc từ khóa vào thanh tìm kiếm (thường nằm ở góc trên bên phải) và nhấn Enter để xem kết quả.</li>
    <li><strong>Lọc sản phẩm:</strong> Sử dụng bộ lọc (giá, thương hiệu, chất liệu) để thu hẹp danh sách sản phẩm theo nhu cầu.</li>
  </ol>

  <h2>2. Xem Chi Tiết Sản Phẩm</h2>
  <ol>
    <li>Nhấn vào hình ảnh hoặc tên sản phẩm để xem thông tin chi tiết (mô tả, thông số kỹ thuật, giá cả, đánh giá).</li>
    <li>Kiểm tra hình ảnh sản phẩm bằng cách phóng to hoặc xem các góc chụp khác.</li>
    <li>Đọc đánh giá từ khách hàng khác để có thêm thông tin trước khi mua.</li>
  </ol>

  <h2>3. Thêm Sản Phẩm Vào Giỏ Hàng</h2>
  <ol>
    <li>Trên trang chi tiết sản phẩm, chọn số lượng và nhấn nút <strong>"Thêm vào giỏ hàng"</strong>.</li>
    <li>Xem giỏ hàng bằng cách nhấn vào biểu tượng giỏ hàng (thường ở góc trên bên phải).</li>
    <li>Kiểm tra lại các sản phẩm trong giỏ hàng, điều chỉnh số lượng hoặc xóa sản phẩm nếu cần.</li>
  </ol>

  <h2>4. Thanh Toán</h2>
  <ol>
    <li>Trong giỏ hàng, nhấn nút <strong>"Thanh toán"</strong> để bắt đầu quy trình đặt hàng.</li>
    <li>Đăng nhập vào tài khoản của bạn hoặc tiếp tục với tư cách khách (nếu có).</li>
    <li>Nhập thông tin giao hàng: Họ tên, địa chỉ, số điện thoại, email.</li>
    <li>Chọn phương thức thanh toán (Thanh toán khi nhận hàng, Thẻ tín dụng, Chuyển khoản ngân hàng, Ví điện tử).</li>
    <li>Xác nhận đơn hàng và chờ email xác nhận từ chúng tôi.</li>
  </ol>
  <div class="note">
    <strong>Lưu ý:</strong> Vui lòng kiểm tra kỹ thông tin giao hàng và thanh toán để tránh sai sót. Nếu gặp vấn đề, liên hệ ngay với chúng tôi qua hotline hoặc email.
  </div>

  <h2>5. Theo Dõi Đơn Hàng</h2>
  <ol>
    <li>Đăng nhập vào tài khoản của bạn và vào mục <strong>"Đơn hàng của tôi"</strong>.</li>
    <li>Xem trạng thái đơn hàng (Đang xử lý, Đang giao, Hoàn thành).</li>
    <li>Liên hệ bộ phận hỗ trợ nếu cần cập nhật hoặc thay đổi thông tin đơn hàng.</li>
  </ol>

  <h2>6. Liên Hệ Hỗ Trợ</h2>
  <p>Nếu bạn gặp bất kỳ khó khăn nào khi sử dụng website, hãy liên hệ với chúng tôi qua:</p>
  <ul>
    <li><strong>Email:</strong> <a href="mailto:hotro@donghoabc.com">hotro@donghoabc.com</a></li>
    <li><strong>Hotline:</strong> 0123 456 789 (8:00 - 17:00, Thứ 2 - Thứ 7)</li>
    <li><strong>Chat trực tuyến:</strong> Nhấn vào biểu tượng chat ở góc dưới bên phải website.</li>
  </ul>

  <div class="footer">
    <p>Cảm ơn bạn đã tin tưởng và mua sắm tại website của chúng tôi!</p>
  </div>
</div>
</body>
</html>
