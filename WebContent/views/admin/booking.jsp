%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Lý Bookings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    
    <div class="container my-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="fas fa-ticket-alt"></i> Quản Lý Bookings</h2>
            <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> Quay Lại Dashboard
            </a>
        </div>
        
        <!-- Filter -->
        <div class="card mb-4">
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/admin/bookings" method="get" class="row g-3">
                    <div class="col-md-4">
                        <label class="form-label">Lọc theo trạng thái</label>
                        <select class="form-select" name="status" onchange="this.form.submit()">
                            <option value="">Tất cả</option>
                            <option value="PENDING" ${selectedStatus == 'PENDING' ? 'selected' : ''}>Chờ xác nhận</option>
                            <option value="CONFIRMED" ${selectedStatus == 'CONFIRMED' ? 'selected' : ''}>Đã xác nhận</option>
                            <option value="CANCELLED" ${selectedStatus == 'CANCELLED' ? 'selected' : ''}>Đã hủy</option>
                        </select>
                    </div>
                </form>
            </div>
        </div>
        
        <!-- Bookings Table -->
        <div class="card">
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty bookings}">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead class="table-light">
                                    <tr>
                                        <th>Mã Booking</th>
                                        <th>Tour</th>
                                        <th>Khách Hàng</th>
                                        <th>Ngày Đi</th>
                                        <th>Số Người</th>
                                        <th>Tổng Tiền</th>
                                        <th>Trạng Thái</th>
                                        <th>Thao Tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="booking" items="${bookings}">
                                        <tr>
                                            <td><strong>${booking.bookingCode}</strong></td>
                                            <td>${booking.tourTitle}</td>
                                            <td>${booking.customerName}</td>
                                            <td><fmt:formatDate value="${booking.departureDate}" pattern="dd/MM/yyyy"/></td>
                                            <td>${booking.numAdults + booking.numChildren}</td>
                                            <td><fmt:formatNumber value="${booking.totalPrice}" pattern="#,###"/>đ</td>
                                            <td>
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
                                            </td>
                                            <td>
                                                <button class="btn btn-sm btn-info" 
                                                        data-bs-toggle="modal" 
                                                        data-bs-target="#detailModal${booking.id}">
                                                    <i class="fas fa-eye"></i>
                                                </button>
                                            </td>
                                        </tr>
                                        
                                        <!-- Detail Modal -->
                                        <div class="modal fade" id="detailModal${booking.id}" tabindex="-1">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">Chi Tiết Booking</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <p><strong>Mã:</strong> ${booking.bookingCode}</p>
                                                        <p><strong>Tour:</strong> ${booking.tourTitle}</p>
                                                        <p><strong>Khách hàng:</strong> ${booking.customerName}</p>
                                                        <p><strong>Ngày đi:</strong> <fmt:formatDate value="${booking.departureDate}" pattern="dd/MM/yyyy"/></p>
                                                        <p><strong>Số người lớn:</strong> ${booking.numAdults}</p>
                                                        <p><strong>Số trẻ em:</strong> ${booking.numChildren}</p>
                                                        <p><strong>Tổng tiền:</strong> <fmt:formatNumber value="${booking.totalPrice}" pattern="#,###"/>đ</p>
                                                        <p><strong>Trạng thái:</strong> ${booking.status}</p>
                                                        <c:if test="${not empty booking.specialRequests}">
                                                            <p><strong>Yêu cầu:</strong> ${booking.specialRequests}</p>
                                                        </c:if>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        
                        <div class="mt-3">
                            <p class="text-muted mb-0">Tổng số: ${bookings.size()} bookings</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info text-center">
                            <i class="fas fa-info-circle"></i> Không có booking nào.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    
    <%@ include file="../common/footer.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>