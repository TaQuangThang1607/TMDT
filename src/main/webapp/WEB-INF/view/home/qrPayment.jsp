<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Thanh toán thiết kế Áo Dài</title>
    <style>
        body { background: #f8fafc; font-family: 'Segoe UI', Arial, sans-serif; }
        .qr-container {
            max-width: 400px; margin: 40px auto; background: #fffbe7; border-radius: 16px;
            box-shadow: 0 4px 24px rgba(26,35,126,0.08), 0 1.5px 6px rgba(26,35,126,0.05);
            padding: 32px 28px 28px 28px; text-align: center;
        }
        img { max-width: 260px; margin-bottom: 18px; }
        .info { font-size: 17px; margin: 12px 0; color: #1a237e; }
        .content { font-size: 15px; color: #3949ab; margin-bottom: 18px; }
        .back { display: inline-block; margin-top: 18px; color: #fff; background: #1a237e; padding: 8px 18px; border-radius: 6px; text-decoration: none; }
        .back:hover { background: #ffd600; color: #1a237e; }
    </style>
</head>
<body>
    <div class="qr-container">
        <h2>Quét mã QR để thanh toán thiết kế</h2>
        <img src="/images/myQR.jpg" alt="QR Thanh toán" />
        <div class="info">Số tiền: <b>${amount}</b> VNĐ</div>
        <div class="content">Nội dung chuyển khoản: <b>${transferContent}</b></div>
        <p style="color:#616161; font-size:14px;">
            Vui lòng chuyển khoản đúng <b>số tiền</b> và <b>nội dung</b> trên.<br>
            Sau khi thanh toán, đơn thiết kế sẽ được xử lý.
        </p>
        <a href="/" class="back">Quay về trang chủ</a>
    </div>
</body>
</html>