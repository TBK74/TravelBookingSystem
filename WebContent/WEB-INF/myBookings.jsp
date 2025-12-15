<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đặt Chỗ Của Tôi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="home">TourBooking</a>
        </div>
    </nav>

    <div class="container mt-4">
        <h1>Đặt Chỗ Của Tôi</h1>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tour</th>
                    <th>Ngày Đặt</th>
                    <th>Trạng Thái</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="booking" items="${bookings}">
                    <c:set var="tour" value="${requestScope['tour_' + booking.bookingID]}" />
                    <tr>
                        <td>${booking.bookingID}</td>
                        <td>${tour.tourName}</td>
                        <td>${booking.bookingDate}</td>
                        <td>${booking.status}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <c:if test="${empty bookings}">
            <p>Bạn chưa có đặt chỗ nào.</p>
        </c:if>
    </div>
</body>
</html>