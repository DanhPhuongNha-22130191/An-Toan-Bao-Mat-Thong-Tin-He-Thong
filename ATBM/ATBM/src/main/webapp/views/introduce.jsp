<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Hướng Dẫn Sử Dụng Công Cụ Chữ Ký Số</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; }
    body { font-family: 'Segoe UI', sans-serif; background: #f8f9fa; min-height: 100vh; color: #333; }
    .container { max-width: 1200px; margin: 0 auto; padding: 20px; }
    .header { text-align: center; margin-bottom: 30px; padding: 30px 0; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 15px; color: white; }
    .header h1 { font-size: 2.5rem; font-weight: 700; margin-bottom: 10px; }
    .header p { font-size: 1.1rem; opacity: 0.9; }
    .download-link { display: inline-flex; align-items: center; background: linear-gradient(45deg, #4CAF50, #45a049); color: white; padding: 12px 25px; text-decoration: none; border-radius: 25px; font-weight: 600; margin-bottom: 30px; }
    .download-link:hover { transform: translateY(-2px); }
    .download-link i { margin-right: 8px; }
    .card { background: white; padding: 25px; border-radius: 15px; box-shadow: 0 10px 30px rgba(0,0,0,0.05); margin-bottom: 25px; }
    .card-header { display: flex; align-items: center; margin-bottom: 15px; }
    .card-icon { width: 40px; height: 40px; background: #667eea; border-radius: 10px; display: flex; align-items: center; justify-content: center; color: white; margin-right: 12px; }
    .card h2 { font-size: 1.4rem; color: #333; }
    .steps-list, .feature-list, .info-list { list-style: none; padding-left: 0; }
    .steps-list li, .feature-list li, .info-list li { margin-bottom: 10px; padding-left: 25px; position: relative; }
    .steps-list li::before, .info-list li::before { content: "•"; position: absolute; left: 0; color: #667eea; font-weight: bold; }
    .note { background: #fff3cd; border-left: 5px solid #ffeeba; padding: 15px; border-radius: 8px; margin-top: 20px; }
    .note h3 { margin-bottom: 10px; font-size: 1.1rem; color: #856404; }
    @media (max-width: 768px) {
      .container { padding: 15px; }
    }
  </style>
</head>
<body>

<jsp:include page="/views/header.jsp" />

<div class="container">
  <div class="header">
    <h1><i class="fas fa-shield-alt"></i> Công Cụ Chữ Ký Số</h1>
    <p>Đảm bảo an toàn, toàn vẹn và xác thực cho dữ liệu điện tử</p>
  </div>

  <div class="center">
    <a href="${pageContext.request.contextPath}/download-zip" class="download-link">
      <i class="fas fa-download"></i> Tải về Công Cụ (ZIP)
    </a>
  </div>

  <!-- I. Hướng Dẫn Sử Dụng Chi Tiết -->
  <div class="card">
    <div class="card-header">
      <div class="card-icon"><i class="fas fa-book-open"></i></div>
      <h2>I. Hướng Dẫn Sử Dụng Chi Tiết</h2>
    </div>
    <ul class="info-list">
      <li><strong>1. Mục Đích:</strong> Hỗ trợ tạo, quản lý và xác thực chữ ký số nhằm đảm bảo tính bảo mật, toàn vẹn và xác thực cho tài liệu.</li>
      <li><strong>2. Giao Diện Chính:</strong>
        <ul class="feature-list">
          <li>Tiêu đề: "Công Cụ Chữ Ký Số"</li>
          <li>Tab "Quản Lý Khóa": Tạo và quản lý cặp khóa RSA</li>
          <li>Tab "Ký & Xác Thực": Ký dữ liệu và xác thực chữ ký</li>
          <li>Các nút: Tải, Lưu, Xóa, Sao Chép, Ký Định Liệu, Xác Thực</li>
        </ul>
      </li>
      <li><strong>3.1. Quản Lý Khóa:</strong>
        <ol class="steps-list">
          <li>Chọn Tab "Quản Lý Khóa"</li>
          <li>Chọn "Keysize" và "Thuật toán ký"</li>
          <li>Nhấn "Tạo Cặp Khóa Mới"</li>
          <li>Khóa xuất hiện ở 2 ô: khóa riêng và khóa công khai</li>
          <li>Sử dụng nút Tải / Lưu / Xóa / Sao chép để thao tác</li>
        </ol>
      </li>
      <li><strong>3.2. Ký & Xác Thực:</strong>
        <ol class="steps-list">
          <li>Chọn Tab "Ký & Xác Thực"</li>
          <li>Nhập dữ liệu vào ô "Định Liệu"</li>
          <li>Nhấn "Ký Định Liệu" → sinh chữ ký số</li>
          <li>Để xác thực: Nhập định liệu + chữ ký, nhấn "Xác Thực"</li>
        </ol>
      </li>
      <li><strong>4. Chức Năng Nút:</strong>
        <ul class="feature-list">
          <li>Tải: Nhập khóa hoặc dữ liệu từ file</li>
          <li>Lưu: Lưu dữ liệu hoặc khóa ra file</li>
          <li>Xóa: Xóa nội dung nhập</li>
          <li>Sao chép: Copy nội dung hoặc khóa</li>
          <li>Tạo Cặp Khóa Mới: Sinh khóa RSA</li>
          <li>Ký Định Liệu: Tạo chữ ký số</li>
          <li>Xác Thực Chữ Ký: Kiểm tra chữ ký</li>
        </ul>
      </li>
      <li><strong>5. Lưu Ý Quan Trọng:</strong>
        <div class="note">
          <ul class="info-list">
            <li>Giữ bí mật khóa riêng tư</li>
            <li>Sử dụng thuật toán SHA256withRSA và khóa 2048</li>
            <li>Kiểm tra chính xác nội dung trước khi ký</li>
            <li>Không nhập thông tin nhạy cảm nếu không cần thiết</li>
          </ul>
        </div>
      </li>
      <li><strong>6. Ví Dụ Sử Dụng:</strong>
        <div class="card">
          <h3><i class="fas fa-pen-nib"></i> Tạo Chữ Ký</h3>
          <ol class="steps-list">
            <li>Chọn "Quản Lý Khóa" → chọn 2048, SHA256withRSA</li>
            <li>Nhấn "Tạo Cặp Khóa Mới"</li>
            <li>Chuyển sang "Ký & Xác Thực" → nhập "Xin chào"</li>
            <li>Nhấn "Ký Định Liệu"</li>
          </ol>
          <h3><i class="fas fa-check-circle"></i> Xác Thực Chữ Ký</h3>
          <ol class="steps-list">
            <li>Nhập lại "Xin chào"</li>
            <li>Dán chữ ký vào ô "Chữ Ký Số"</li>
            <li>Nhấn "Xác Thực Chữ Ký"</li>
          </ol>
        </div>
      </li>
    </ul>
  </div>

  <!-- II. Quy Trình Mua Hàng -->
  <div class="card">
    <div class="card-header">
      <div class="card-icon"><i class="fas fa-shopping-cart"></i></div>
      <h2>II. Quy Trình Mua Hàng</h2>
    </div>
    <ol class="steps-list">
      <li>Thêm sản phẩm vào giỏ hàng → từ trang chi tiết hoặc hình ảnh</li>
      <li>Nhập thông tin đơn hàng → hệ thống tạo dữ liệu</li>
      <li>Ký đơn hàng bằng ứng dụng chữ ký số</li>
      <li>Cung cấp chữ ký để hoàn tất xác nhận đơn hàng</li>
      <li>Hệ thống chuyển đến trang chi tiết đơn hàng</li>
    </ol>
  </div>

  <!-- III. Quy Trình Ký Lại Đơn -->
  <div class="card">
    <div class="card-header">
      <div class="card-icon"><i class="fas fa-sync-alt"></i></div>
      <h2>III. Quy Trình Ký Lại Đơn Hàng</h2>
    </div>
    <ol class="steps-list">
      <li>Vào mục “Lịch sử đơn hàng”</li>
      <li>Chọn đơn cần ký lại → vào chi tiết đơn hàng</li>
      <li>Lấy thông tin đơn hàng, ký lại và nộp chữ ký</li>
    </ol>
  </div>

  <!-- IV. Quy Trình Đổi Public Key -->
  <div class="card">
    <div class="card-header">
      <div class="card-icon"><i class="fas fa-key"></i></div>
      <h2>IV. Quy Trình Đổi Public Key</h2>
    </div>
    <ol class="steps-list">
      <li>Vào mục “Quản lý Public Key” trong hồ sơ cá nhân</li>
      <li>Cập nhật khóa công khai:
        <ul>
          <li>Nhập trực tiếp theo định dạng PEM</li>
          <li>Hoặc tải lên file chứa khóa</li>
        </ul>
      </li>
      <li>Hệ thống chuẩn hóa, kiểm tra và lưu khóa</li>
      <li>Thu hồi khóa cũ, hiển thị tùy chọn mới</li>
      <li>Cập nhật thông tin đăng nhập với khóa mới</li>
    </ol>
  </div>
</div>

<jsp:include page="/views/footer.jsp" />

</body>
</html>
