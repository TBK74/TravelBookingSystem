<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hệ Thống Đặt Tour Du Lịch</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="home">TourBooking</a>
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="myBookings">Đặt chỗ của tôi</a></li>
            </ul>
            <c:if test="${sessionScope.user == null}">
                <a href="login.jsp" class="btn btn-primary me-2">Đăng nhập</a>
                <a href="register.jsp" class="btn btn-secondary">Đăng ký</a>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <span class="navbar-text me-2">Xin chào, ${sessionScope.user.username}</span>
                <a href="logout" class="btn btn-danger">Đăng xuất</a>
            </c:if>
        </div>
    </nav>

    <div class="container mt-4">
        <h1>Danh Sách Tour Du Lịch</h1>
        <c:if test="${not empty param.message}">
            <div class="alert alert-info">${param.message}</div>
        </c:if>
        <div class="row">
            <c:forEach var="tour" items="${tours}">
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${tour.tourName}</h5>
                            <p class="card-text">${tour.description}</p>
                            <p>Giá: ${tour.price} USD</p>
                            <p>Địa điểm: ${tour.location}</p>
                            <p>Thời gian: ${tour.startDate} đến ${tour.endDate}</p>
                            <a href="tourDetail?tourID=${tour.tourID}" class="btn btn-info">Chi tiết</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>