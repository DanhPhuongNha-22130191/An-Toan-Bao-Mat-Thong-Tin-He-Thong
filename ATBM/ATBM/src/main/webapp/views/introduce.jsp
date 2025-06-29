<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Hướng Dẫn Sử Dụng Website Bán Đồng Hồ</title>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
      line-height: 1.7;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: #2c3e50;
      min-height: 100vh;
    }

    .hero-section {
      background: linear-gradient(135deg, rgba(102, 126, 234, 0.9), rgba(118, 75, 162, 0.9));
      color: white;
      padding: 60px 0;
      text-align: center;
      position: relative;
      overflow: hidden;
    }

    .hero-section::before {
      content: '';
      position: absolute;
      top: -50%;
      left: -50%;
      width: 200%;
      height: 200%;
      background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><circle cx="50" cy="50" r="2" fill="rgba(255,255,255,0.1)"/></svg>') repeat;
      animation: float 20s infinite linear;
      z-index: 0;
    }

    @keyframes float {
      0% { transform: translate(-50%, -50%) rotate(0deg); }
      100% { transform: translate(-50%, -50%) rotate(360deg); }
    }

    .hero-content {
      position: relative;
      z-index: 1;
      max-width: 800px;
      margin: 0 auto;
      padding: 0 20px;
    }

    .hero-title {
      font-size: 3.5rem;
      font-weight: 700;
      margin-bottom: 20px;
      text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
      animation: slideInDown 1s ease-out;
    }

    .hero-subtitle {
      font-size: 1.3rem;
      opacity: 0.9;
      margin-bottom: 30px;
      animation: slideInUp 1s ease-out 0.3s both;
    }

    @keyframes slideInDown {
      from { transform: translateY(-50px); opacity: 0; }
      to { transform: translateY(0); opacity: 1; }
    }

    @keyframes slideInUp {
      from { transform: translateY(50px); opacity: 0; }
      to { transform: translateY(0); opacity: 1; }
    }

    .container {
      max-width: 1200px;
      margin: -50px auto 0;
      padding: 0 20px;
      position: relative;
      z-index: 2;
    }

    .content-card {
      background: white;
      border-radius: 20px;
      box-shadow: 0 20px 60px rgba(0,0,0,0.1);
      overflow: hidden;
      margin-bottom: 40px;
    }

    .card-header {
      background: linear-gradient(135deg, #f093fb 0%, #f5576c 50%, #4facfe 100%);
      padding: 30px;
      color: white;
      text-align: center;
    }

    .card-header h2 {
      font-size: 2rem;
      font-weight: 600;
      margin-bottom: 10px;
    }

    .card-content {
      padding: 40px;
    }

    .guide-section {
      margin-bottom: 50px;
      padding: 30px;
      background: #f8fafc;
      border-radius: 15px;
      border-left: 5px solid #667eea;
      transition: all 0.3s ease;
      position: relative;
      overflow: hidden;
    }

    .guide-section::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      width: 5px;
      height: 100%;
      background: linear-gradient(45deg, #667eea, #764ba2);
      transition: width 0.3s ease;
    }

    .guide-section:hover::before {
      width: 100%;
      opacity: 0.05;
    }

    .guide-section:hover {
      transform: translateY(-5px);
      box-shadow: 0 15px 40px rgba(102, 126, 234, 0.15);
    }

    .section-title {
      font-size: 1.5rem;
      font-weight: 700;
      color: #2c3e50;
      margin-bottom: 20px;
      display: flex;
      align-items: center;
      gap: 15px;
    }

    .section-icon {
      width: 50px;
      height: 50px;
      background: linear-gradient(135deg, #667eea, #764ba2);
      border-radius: 12px;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      font-size: 1.2rem;
    }

    .steps-list {
      list-style: none;
      counter-reset: step-counter;
    }

    .step-item {
      counter-increment: step-counter;
      margin-bottom: 20px;
      padding: 20px;
      background: white;
      border-radius: 12px;
      box-shadow: 0 5px 15px rgba(0,0,0,0.08);
      position: relative;
      padding-left: 80px;
      transition: all 0.3s ease;
    }

    .step-item:hover {
      transform: translateX(10px);
      box-shadow: 0 8px 25px rgba(102, 126, 234, 0.15);
    }

    .step-item::before {
      content: counter(step-counter);
      position: absolute;
      left: 20px;
      top: 50%;
      transform: translateY(-50%);
      width: 40px;
      height: 40px;
      background: linear-gradient(135deg, #667eea, #764ba2);
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      font-weight: 700;
      font-size: 1.1rem;
    }

    .highlight {
      background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%);
      padding: 25px;
      border-radius: 15px;
      margin: 30px 0;
      border-left: 5px solid #ff6b6b;
      position: relative;
    }

    .highlight::before {
      content: '\f06a';
      font-family: 'Font Awesome 6 Free';
      font-weight: 900;
      position: absolute;
      left: -15px;
      top: 25px;
      width: 30px;
      height: 30px;
      background: #ff6b6b;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      font-size: 0.9rem;
    }

    .contact-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 25px;
      margin-top: 30px;
    }

    .contact-item {
      background: white;
      padding: 25px;
      border-radius: 15px;
      box-shadow: 0 10px 30px rgba(0,0,0,0.1);
      text-align: center;
      transition: all 0.3s ease;
      border: 2px solid transparent;
    }

    .contact-item:hover {
      transform: translateY(-8px);
      border-color: #667eea;
      box-shadow: 0 15px 40px rgba(102, 126, 234, 0.2);
    }

    .contact-icon {
      width: 60px;
      height: 60px;
      background: linear-gradient(135deg, #667eea, #764ba2);
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      margin: 0 auto 15px;
      color: white;
      font-size: 1.5rem;
    }

    .footer-section {
      background: linear-gradient(135deg, #2c3e50, #34495e);
      color: white;
      text-align: center;
      padding: 40px 20px;
      margin-top: 60px;
    }

    .footer-content {
      max-width: 800px;
      margin: 0 auto;
    }

    .footer-title {
      font-size: 1.8rem;
      font-weight: 600;
      margin-bottom: 15px;
    }

    .btn-primary {
      background: linear-gradient(135deg, #667eea, #764ba2);
      color: white;
      padding: 12px 30px;
      border: none;
      border-radius: 25px;
      font-weight: 600;
      text-decoration: none;
      display: inline-block;
      transition: all 0.3s ease;
      margin-top: 20px;
    }

    .btn-primary:hover {
      transform: translateY(-2px);
      box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
      color: white;
      text-decoration: none;
    }

    @media (max-width: 768px) {
      .hero-title {
        font-size: 2.5rem;
      }

      .hero-subtitle {
        font-size: 1.1rem;
      }

      .section-title {
        font-size: 1.3rem;
      }

      .step-item {
        padding-left: 60px;
      }

      .step-item::before {
        width: 30px;
        height: 30px;
        font-size: 0.9rem;
      }

      .contact-grid {
        grid-template-columns: 1fr;
      }
    }
  </style>
</head>
<body>
<!--================ Start Header Menu Area =================-->
<jsp:include page="/views/header.jsp" />
<!--================ End Header Menu Area =================-->

<div class="hero-section">
  <div class="hero-content">
    <h1 class="hero-title">
      <i class="fas fa-clock"></i>
      Hướng Dẫn Sử Dụng
    </h1>
    <p class="hero-subtitle">
      Khám phá cách sử dụng website một cách dễ dàng và hiệu quả nhất
    </p>
  </div>
</div>

<div class="container">
  <div class="content-card">
    <div class="card-header">
      <h2>Chào mừng bạn đến với cửa hàng đồng hồ của chúng tôi</h2>
      <p>Hướng dẫn chi tiết từng bước để có trải nghiệm mua sắm tuyệt vời</p>
    </div>

    <div class="card-content">
      <div class="guide-section">
        <h3 class="section-title">
          <div class="section-icon">
            <i class="fas fa-search"></i>
          </div>
          Duyệt và Tìm Kiếm Sản Phẩm
        </h3>
        <ol class="steps-list">
          <li class="step-item">
            <strong>Truy cập trang chủ:</strong> Mở trình duyệt và nhập địa chỉ website của chúng tôi để bắt đầu khám phá các sản phẩm đồng hồ chất lượng cao.
          </li>
          <li class="step-item">
            <strong>Duyệt danh mục:</strong> Sử dụng thanh menu ở đầu trang để chọn các loại đồng hồ phù hợp (Đồng hồ nam, Đồng hồ nữ, Đồng hồ đôi, v.v.).
          </li>
          <li class="step-item">
            <strong>Tìm kiếm thông minh:</strong> Nhập tên thương hiệu, mẫu mã hoặc từ khóa vào thanh tìm kiếm để tìm chính xác sản phẩm bạn mong muốn.
          </li>
          <li class="step-item">
            <strong>Lọc sản phẩm:</strong> Sử dụng bộ lọc thông minh theo giá, thương hiệu, chất liệu để thu hẹp kết quả tìm kiếm.
          </li>
        </ol>
      </div>

      <div class="guide-section">
        <h3 class="section-title">
          <div class="section-icon">
            <i class="fas fa-eye"></i>
          </div>
          Xem Chi Tiết Sản Phẩm
        </h3>
        <ol class="steps-list">
          <li class="step-item">
            <strong>Xem thông tin chi tiết:</strong> Nhấp vào hình ảnh hoặc tên sản phẩm để khám phá đầy đủ thông tin về mô tả, thông số kỹ thuật, giá cả và đánh giá.
          </li>
          <li class="step-item">
            <strong>Khám phá hình ảnh:</strong> Sử dụng tính năng phóng to và xem các góc chụp khác nhau để có cái nhìn toàn diện về sản phẩm.
          </li>
          <li class="step-item">
            <strong>Đọc đánh giá:</strong> Tham khảo ý kiến từ khách hàng khác để có quyết định mua hàng thông minh nhất.
          </li>
        </ol>
      </div>

      <div class="guide-section">
        <h3 class="section-title">
          <div class="section-icon">
            <i class="fas fa-shopping-cart"></i>
          </div>
          Thêm Sản Phẩm Vào Giỏ Hàng
        </h3>
        <ol class="steps-list">
          <li class="step-item">
            <strong>Chọn và thêm:</strong> Trên trang chi tiết sản phẩm, chọn số lượng mong muốn và nhấn nút "Thêm vào giỏ hàng".
          </li>
          <li class="step-item">
            <strong>Kiểm tra giỏ hàng:</strong> Nhấp vào biểu tượng giỏ hàng để xem tất cả sản phẩm đã chọn.
          </li>
          <li class="step-item">
            <strong>Điều chỉnh đơn hàng:</strong> Thay đổi số lượng hoặc xóa sản phẩm theo nhu cầu trước khi thanh toán.
          </li>
        </ol>
      </div>

      <div class="guide-section">
        <h3 class="section-title">
          <div class="section-icon">
            <i class="fas fa-credit-card"></i>
          </div>
          Thanh Toán An Toàn
        </h3>
        <ol class="steps-list">
          <li class="step-item">
            <strong>Bắt đầu thanh toán:</strong> Trong giỏ hàng, nhấn nút "Thanh toán" để bắt đầu quy trình đặt hàng an toàn.
          </li>
          <li class="step-item">
            <strong>Đăng nhập:</strong> Đăng nhập vào tài khoản hoặc tiếp tục với tư cách khách hàng mới.
          </li>
          <li class="step-item">
            <strong>Nhập thông tin giao hàng:</strong> Cung cấp đầy đủ thông tin: Họ tên, địa chỉ, số điện thoại, email để đảm bảo giao hàng chính xác.
          </li>
          <li class="step-item">
            <strong>Chọn phương thức thanh toán:</strong> Lựa chọn phương thức phù hợp: COD, Thẻ tín dụng, Chuyển khoản ngân hàng, hoặc Ví điện tử.
          </li>
          <li class="step-item">
            <strong>Xác nhận đơn hàng:</strong> Kiểm tra lại thông tin và chờ email xác nhận từ chúng tôi.
          </li>
        </ol>
      </div>

      <div class="highlight">
        <strong>💡 Lưu ý quan trọng:</strong> Vui lòng kiểm tra kỹ thông tin giao hàng và thanh toán để tránh sai sót. Nếu gặp bất kỳ vấn đề nào, hãy liên hệ ngay với đội ngũ hỗ trợ 24/7 của chúng tôi.
      </div>

      <div class="guide-section">
        <h3 class="section-title">
          <div class="section-icon">
            <i class="fas fa-truck"></i>
          </div>
          Theo Dõi Đơn Hàng
        </h3>
        <ol class="steps-list">
          <li class="step-item">
            <strong>Truy cập tài khoản:</strong> Đăng nhập và vào mục "Đơn hàng của tôi" để theo dõi tình trạng đơn hàng.
          </li>
          <li class="step-item">
            <strong>Kiểm tra trạng thái:</strong> Xem chi tiết trạng thái đơn hàng (Đang xử lý, Đang giao, Hoàn thành).
          </li>
          <li class="step-item">
            <strong>Liên hệ hỗ trợ:</strong> Liên hệ đội ngũ chăm sóc khách hàng nếu cần cập nhật hoặc thay đổi thông tin.
          </li>
        </ol>
      </div>

      <div class="guide-section">
        <h3 class="section-title">
          <div class="section-icon">
            <i class="fas fa-headset"></i>
          </div>
          Liên Hệ Hỗ Trợ 24/7
        </h3>
        <p style="margin-bottom: 30px; font-size: 1.1rem; color: #555;">
          Đội ngũ chăm sóc khách hàng của chúng tôi luôn sẵn sàng hỗ trợ bạn mọi lúc, mọi nơi:
        </p>

        <div class="contact-grid">
          <div class="contact-item">
            <div class="contact-icon">
              <i class="fas fa-envelope"></i>
            </div>
            <h4>Email Hỗ Trợ</h4>
            <p><a href="mailto:hotro@donghoabc.com" style="color: #667eea; font-weight: 600;">hotro@donghoabc.com</a></p>
            <p style="font-size: 0.9rem; color: #666;">Phản hồi trong vòng 2 giờ</p>
          </div>

          <div class="contact-item">
            <div class="contact-icon">
              <i class="fas fa-phone"></i>
            </div>
            <h4>Hotline</h4>
            <p style="font-weight: 600; color: #667eea; font-size: 1.2rem;">0123 456 789</p>
            <p style="font-size: 0.9rem; color: #666;">8:00 - 17:00, Thứ 2 - Thứ 7</p>
          </div>

          <div class="contact-item">
            <div class="contact-icon">
              <i class="fas fa-comments"></i>
            </div>
            <h4>Chat Trực Tuyến</h4>
            <p>Nhấn vào biểu tượng chat</p>
            <p style="font-size: 0.9rem; color: #666;">Hỗ trợ tức thời 24/7</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="footer-section">
  <div class="footer-content">
    <h3 class="footer-title">
      <i class="fas fa-heart" style="color: #ff6b6b;"></i>
      Cảm ơn bạn đã tin tưởng
    </h3>
    <p style="font-size: 1.1rem; margin-bottom: 20px;">
      Chúng tôi cam kết mang đến trải nghiệm mua sắm tuyệt vời nhất cho bạn!
    </p>
    <a href="#" class="btn-primary">
      <i class="fas fa-shopping-bag"></i>
      Bắt Đầu Mua Sắm
    </a>
  </div>
</div>

<script>
  // Smooth scrolling animation
  document.addEventListener('DOMContentLoaded', function() {
    // Animate sections on scroll
    const sections = document.querySelectorAll('.guide-section');
    const observer = new IntersectionObserver((entries) => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          entry.target.style.opacity = '1';
          entry.target.style.transform = 'translateY(0)';
        }
      });
    }, { threshold: 0.1 });

    sections.forEach(section => {
      section.style.opacity = '0';
      section.style.transform = 'translateY(30px)';
      section.style.transition = 'all 0.6s ease';
      observer.observe(section);
    });

    // Add hover effect to step items
    const stepItems = document.querySelectorAll('.step-item');
    stepItems.forEach(item => {
      item.addEventListener('mouseenter', function() {
        this.style.background = 'linear-gradient(135deg, #f8fafc, #e2e8f0)';
      });

      item.addEventListener('mouseleave', function() {
        this.style.background = 'white';
      });
    });
  });
</script>
</body>
</html>