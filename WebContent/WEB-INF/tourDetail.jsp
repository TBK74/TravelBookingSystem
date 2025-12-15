<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi Tiết Tour</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="home">TourBooking</a>
        </div>
    </nav>

    <div class="container mt-4">
        <h1>Chi Tiết Tour: ${tour.tourName}</h1>
        <p>Mô tả: ${tour.description}</p>
        <p>Giá: ${tour.price} USD</p>
        <p>Địa điểm: ${tour.location}</p>
        <p>Thời gian bắt đầu: ${tour.startDate}</p>
        <p>Thời gian kết thúc: ${tour.endDate}</p>
        <c:if test="${sessionScope.user != null}">
            <form action="book" method="post">
                <input type="hidden" name="tourID" value="${tour.tourID}">
                <button type="submit" class="btn btn-success">Đặt Tour Ngay</button>
            </form>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <p>Vui lòng <a href="login.jsp">đăng nhập</a> để đặt tour.</p>
        </c:if>
    </div>
</body>
</html>