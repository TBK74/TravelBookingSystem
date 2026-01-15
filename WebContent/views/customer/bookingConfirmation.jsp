<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác Nhận Đặt Tour</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    
    <div class="container my-5">
        <!-- Success message -->
        <div class="text-center mb-4">
            <div class="d-inline-block p-4 bg-success text-white rounded-circle mb-3">
                <i class="fas fa-check fa-3x"></i>
            </div>
            <h2 class="text-success">Đặt Tour Thành Công!</h2>
            <p class="lead">Cảm ơn bạn đã đặt tour. Chúng tôi sẽ liên hệ với bạn sớm nhất.</p>
        </div>
        
        <!-- Booking info -->
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="fas fa-info-circle"></i> Thông Tin Booking</h5>
                    </div>
                    <div class="card-body">
                        <div class="row mb-3">
                            <div class="col-sm-4 fw-bold">Mã Booking:</div>
                            <div class="col-sm-8">
                                <span class="badge bg-success fs-6">${booking.bookingCode}</span>
                            </div>
                        </div>
                        
                        <div class="row mb-3">
                            <div class="col-sm-4 fw-bold">Tour:</div>
                            <div class="col-sm-8">${tour.title}</div>
                        </div>
                        
                        <div class="row mb-3">
                            <div class="col-sm-4 fw-bold">Ngày khởi hành:</div>
                            <div class="col-sm-8">
                                <fmt:formatDate value="${booking.departureDate}" pattern="dd/MM/yyyy"/>
                            </div>
                        </div>
                        
                        <div class="row mb-3">
                            <div class="col-sm-4 fw-bold">Số người:</div>
                            <div class="col-sm-8">
                                ${booking.numAdults} người lớn
                                <c:if test="${booking.numChildren > 0}">
                                    + ${booking.numChildren} trẻ em
                                </c:if>
                            </div>
                        </div>
                        
                        <div class="row mb-3">
                            <div class="col-sm-4 fw-bold">Tổng tiền:</div>
                            <div class="col-sm-8">
                                <h4 class="text-danger mb-0">
                                    <fmt:formatNumber value="${booking.totalPrice}" pattern="#,###"/>đ
                                </h4>
                            </div>
                        </div>
                        
                        <div class="row mb-3">
                            <div class="col-sm-4 fw-bold">Trạng thái:</div>
                            <div class="col-sm-8">
                                <span class="badge bg-warning">${booking.status}</span>
                            </div>
                        </div>
                        
                        <c:if test="${not empty booking.specialRequests}">
                            <div class="row mb-3">
                                <div class="col-sm-4 fw-bold">Ghi chú:</div>
                                <div class="col-sm-8">${booking.specialRequests}</div>
                            </div>
                        </c:if>
                        
                        <hr>
                        
                        <div class="alert alert-info">
                            <i class="fas fa-info-circle"></i>
                            <strong>Lưu ý:</strong> Vui lòng lưu lại mã booking để tra cứu và liên hệ với chúng tôi.
                        </div>
                        
                        <!-- Actions -->
                        <div class="d-grid gap-2">
                            <a href="${pageContext.request.contextPath}/my-bookings" class="btn btn-primary">
                                <i class="fas fa-list"></i> Xem Tất Cả Đơn Đặt
                            </a>
                            <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary">
                                <i class="fas fa-home"></i> Về Trang Chủ
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <%@ include file="../common/footer.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>