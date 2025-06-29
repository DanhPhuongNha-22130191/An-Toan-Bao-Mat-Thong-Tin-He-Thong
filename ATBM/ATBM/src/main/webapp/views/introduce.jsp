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
    .header h1 { font-size: 2.5rem; font-weight: 700; margin-bottom: 10px; text-shadow: 2px 2px 4px rgba(0,0,0,0.3); }
    .header p { font-size: 1.1rem; opacity: 0.9; }
    .download-link { display: inline-flex; align-items: center; background: linear-gradient(45deg, #4CAF50, #45a049); color: white; padding: 12px 25px; text-decoration: none; border-radius: 25px; font-weight: 600; box-shadow: 0 5px 15px rgba(76, 175, 80, 0.3); transition: all 0.3s ease; margin-bottom: 30px; }
    .download-link:hover { transform: translateY(-2px); box-shadow: 0 8px 25px rgba(76, 175, 80, 0.4); }
    .download-link i { margin-right: 8px; }
    .grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 20px; }
    .card { background: rgba(255, 255, 255, 0.95); backdrop-filter: blur(10px); border-radius: 15px; padding: 25px; box-shadow: 0 10px 30px rgba(0,0,0,0.1); transition: all 0.3s ease; }
    .card:hover { transform: translateY(-5px); box-shadow: 0 15px 35px rgba(0,0,0,0.15); }
    .card-header { display: flex; align-items: center; margin-bottom: 20px; }
    .card-icon { width: 40px; height: 40px; background: linear-gradient(45deg, #667eea, #764ba2); border-radius: 10px; display: flex; align-items: center; justify-content: center; color: white; margin-right: 12px; }
    .card h2 { color: #2c3e50; font-size: 1.3rem; font-weight: 600; }
    .card p { color: #666; margin-bottom: 15px; }
    .feature-list { list-style: none; }
    .feature-list li { padding: 8px 0; display: flex; align-items: center; border-bottom: 1px solid #f0f0f0; }
    .feature-list li:last-child { border-bottom: none; }
    .feature-list li i { color: #4CAF50; margin-right: 10px; width: 16px; }
    .steps-list { counter-reset: step; list-style: none; }
    .steps-list li { counter-increment: step; margin-bottom: 12px; padding: 12px 12px 12px 35px; background: #f8f9ff; border-radius: 8px; border-left: 3px solid #667eea; position: relative; }
    .steps-list li::before { content: counter(step); position: absolute; left: -12px; top: 12px; background: #667eea; color: white; width: 24px; height: 24px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 0.8rem; font-weight: bold; }
    .note { background: linear-gradient(135deg, #fff3cd, #ffeaa7); padding: 20px; border-radius: 12px; border-left: 4px solid #ffd93d; margin: 25px 0; }
    .note h3 { color: #856404; margin-bottom: 12px; font-size: 1.1rem; }
    .note ul { list-style: none; }
    .note li { padding: 5px 0; display: flex; align-items: flex-start; }
    .note li::before { content: '✓'; color: #856404; margin-right: 8px; font-weight: bold; }
    .example-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 20px; margin-top: 20px; }
    .example-card { background: #f8f9ff; padding: 20px; border-radius: 12px; border: 2px solid #e3e8ff; }
    .example-card h4 { color: #667eea; margin-bottom: 12px; display: flex; align-items: center; }
    .example-card h4 i { margin-right: 8px; }
    .center { text-align: center; }
    @media (max-width: 768px) {
      .container { padding: 15px; }
      .header h1 { font-size: 2rem; }
      .grid { grid-template-columns: 1fr; gap: 15px; }
      .card { padding: 20px; }
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
    <a href="${pageContext.request.contextPath}/download" class="download-link">
      <i class="fas fa-download"></i> Tải về Công Cụ (ZIP)
    </a>
  </div>

  <div class="grid">
    <div class="card">
      <div class="card-header">
        <div class="card-icon"><i class="fas fa-bullseye"></i></div>
        <h2>Mục Đích</h2>
      </div>
      <p>Công cụ hỗ trợ tạo, quản lý và xác thực chữ ký số nhằm đảm bảo an toàn, toàn vẹn và xác thực cho dữ liệu điện tử.</p>
    </div>

    <div class="card">
      <div class="card-header">
        <div class="card-icon"><i class="fas fa-desktop"></i></div>
        <h2>Giao Diện Chính</h2>
      </div>
      <ul class="feature-list">
        <li><i class="fas fa-heading"></i><strong>Tiêu đề:</strong> "Công Cụ Chữ Ký Số"</li>
        <li><i class="fas fa-key"></i><strong>Tab Quản Lý Khóa:</strong> Tạo và quản lý cặp khóa RSA</li>
        <li><i class="fas fa-signature"></i><strong>Tab Ký & Xác Thực:</strong> Ký dữ liệu và kiểm tra chữ ký</li>
        <li><i class="fas fa-mouse-pointer"></i><strong>Các nút:</strong> Tải, Lưu, Xóa, Sao Chép, Ký, Xác Thực</li>
      </ul>
    </div>

    <div class="card">
      <div class="card-header">
        <div class="card-icon"><i class="fas fa-key"></i></div>
        <h2>Quản Lý Khóa</h2>
      </div>
      <ol class="steps-list">
        <li>Chuyển sang tab <strong>"Quản Lý Khóa"</strong></li>
        <li>Chọn kích thước khóa (2048 bit) và thuật toán (SHA256withRSA)</li>
        <li>Nhấn <strong>"Tạo Cặp Khóa Mới"</strong></li>
        <li>Khóa sẽ hiển thị ở 2 ô tương ứng</li>
        <li>Có thể Tải, Lưu, Xóa hoặc Sao Chép nội dung</li>
      </ol>
    </div>

    <div class="card">
      <div class="card-header">
        <div class="card-icon"><i class="fas fa-signature"></i></div>
        <h2>Ký & Xác Thực</h2>
      </div>
      <ol class="steps-list">
        <li>Chuyển sang tab <strong>"Ký & Xác Thực"</strong></li>
        <li>Nhập nội dung cần ký vào ô "Định Liệu"</li>
        <li>Nhấn <strong>"Ký Định Liệu"</strong> để tạo chữ ký</li>
        <li>Chữ ký hiển thị trong ô "Chữ Ký Số"</li>
        <li>Để xác thực: nhập nội dung và chữ ký, nhấn <strong>"Xác Thực"</strong></li>
      </ol>
    </div>

    <div class="card">
      <div class="card-header">
        <div class="card-icon"><i class="fas fa-cogs"></i></div>
        <h2>Chức Năng Nút</h2>
      </div>
      <ul class="feature-list">
        <li><i class="fas fa-upload"></i><strong>Tải:</strong> Nhập dữ liệu từ file</li>
        <li><i class="fas fa-save"></i><strong>Lưu:</strong> Lưu khóa hoặc dữ liệu</li>
        <li><i class="fas fa-trash"></i><strong>Xóa:</strong> Xóa nội dung</li>
        <li><i class="fas fa-copy"></i><strong>Sao Chép:</strong> Copy ra clipboard</li>
        <li><i class="fas fa-plus-circle"></i><strong>Tạo Khóa:</strong> Sinh khóa RSA mới</li>
        <li><i class="fas fa-pen-nib"></i><strong>Ký:</strong> Tạo chữ ký số</li>
        <li><i class="fas fa-check-circle"></i><strong>Xác Thực:</strong> Kiểm tra chữ ký</li>
      </ul>
    </div>
  </div>

  <div class="note">
    <h3><i class="fas fa-exclamation-triangle"></i> Lưu Ý Quan Trọng</h3>
    <ul>
      <li><strong>Giữ bí mật khóa riêng tư.</strong> Không chia sẻ với ai.</li>
      <li><strong>Chọn thuật toán phù hợp:</strong> SHA256withRSA + 2048 bit là tiêu chuẩn.</li>
      <li><strong>Đảm bảo dữ liệu chính xác</strong> trước khi ký hoặc xác thực.</li>
      <li><strong>Không nhập thông tin nhạy cảm</strong> nếu không cần thiết.</li>
    </ul>
  </div>

  <div class="card">
    <div class="card-header">
      <div class="card-icon"><i class="fas fa-lightbulb"></i></div>
      <h2>Ví Dụ Nhanh</h2>
    </div>
    <div class="example-grid">
      <div class="example-card">
        <h4><i class="fas fa-pen-nib"></i>Tạo Chữ Ký</h4>
        <ol class="steps-list">
          <li>Chọn "Quản Lý Khóa" → Tạo khóa 2048</li>
          <li>Sang "Ký & Xác Thực", nhập "Xin chào"</li>
          <li>Nhấn "Ký Định Liệu"</li>
        </ol>
      </div>
      <div class="example-card">
        <h4><i class="fas fa-check-circle"></i>Xác Thực</h4>
        <ol class="steps-list">
          <li>Nhập lại "Xin chào"</li>
          <li>Dán chữ ký đã tạo</li>
          <li>Nhấn "Xác Thực Chữ Ký"</li>
        </ol>
      </div>
    </div>
  </div>
</div>

<jsp:include page="/views/footer.jsp" />
</body>
</html>