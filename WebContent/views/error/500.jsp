<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>500 - Lỗi Server</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
        }
        .error-container {
            text-align: center;
            padding: 40px;
        }
        .error-code {
            font-size: 120px;
            font-weight: bold;
            margin: 0;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1 class="error-code">500</h1>
        <h2>Lỗi Máy Chủ</h2>
        <p class="lead">Đã xảy ra lỗi khi xử lý yêu cầu của bạn.</p>
        <a href="${pageContext.request.contextPath}/" class="btn btn-light btn-lg mt-4">
            Về Trang Chủ
        </a>
    </div>
</body>
</html>