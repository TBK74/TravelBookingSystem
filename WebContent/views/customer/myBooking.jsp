<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đơn Đặt Tour Của Tôi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    
    <div class="container my-5">
        <h2 class="mb-4"><i class="fas fa-ticket-alt"></i> Đơn Đặt Tour Của Tôi</h2>
        
        <c:choose>
            <c:when test="${not empty bookings}">
                <div class="row">
                    <c:forEach var="booking" items="${bookings}">
                        <div class="col-md-12 mb-3">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-8">
                                            <h5 class="card-title">${booking.tourTitle}</h5>
                                            <p class="mb-2">
                                                <i class="fas fa-barcode"></i> Mã: <strong>${booking.bookingCode}</strong>
                                            </p>
                                            <p class="mb-2">
                                                <i class="fas fa-calendar"></i> Ngày đi: 
                                                <fmt:formatDate value="${booking.departureDate}" pattern="dd/MM/yyyy"/>
                                            </p>
                                            <p class="mb-2">
                                                <i class="fas fa-users"></i> 
                                                ${booking.numAdults} người lớn
                                                <c:if test="${booking.numChildren > 0}">
                                                    + ${booking.numChildren} trẻ em
                                                </c:if>
                                            </p>
                                        </div>
                                        <div class="col-md-4 text-end">
                                            <h4 class="text-danger">
                                                <fmt:formatNumber value="${booking.totalPrice}" pattern="#,###"/>đ
                                            </h4>
                                            <p>
                                                <c:choose>
                                                    <c:when test="${booking.status == 'PENDING'}">
                                                        <span class="badge bg-warning">Chờ xác nhận</span>
                                                    </c:when>
                                                    <c:when test="${booking.status == 'CONFIRMED'}">
                                                        <span class="badge bg-success">Đã xác nhận</span>
                                                    </c:when>
                                                    <c:when test="${booking.status == 'CANCELLED'}">
                                                        <span class="badge bg-danger">Đã hủy</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">${booking.status}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </p>
                                            <div class="mt-3">
                                                <c:if test="${booking.status != 'CANCELLED'}">
                                                    <button type="button" class="btn btn-sm btn-danger" 
                                                            data-bs-toggle="modal" 
                                                            data-bs-target="#cancelModal${booking.id}">
                                                        <i class="fas fa-times"></i> Hủy
                                                    </button>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Cancel Modal -->
                        <div class="modal fade" id="cancelModal${booking.id}" tabindex="-1">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Xác Nhận Hủy Booking</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                    </div>
                                    <form action="${pageContext.request.contextPath}/cancel-booking" method="post">
                                        <div class="modal-body">
                                            <input type="hidden" name="bookingId" value="${booking.id}">
                                            <p>Bạn có chắc muốn hủy booking <strong>${booking.bookingCode}</strong>?</p>
                                            <div class="mb-3">
                                                <label class="form-label">Lý do hủy:</label>
                                                <textarea class="form-control" name="reason" rows="3" required></textarea>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                            <button type="submit" class="btn btn-danger">Xác Nhận Hủy</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info text-center">
                    <i class="fas fa-info-circle fa-3x mb-3"></i>
                    <h5>Bạn chưa có đơn đặt tour nào</h5>
                    <p>Hãy khám phá và đặt tour yêu thích của bạn!</p>
                    <a href="${pageContext.request.contextPath}/tours" class="btn btn-primary">
                        <i class="fas fa-search"></i> Tìm Tour
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    
    <%@ include file="../common/footer.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>