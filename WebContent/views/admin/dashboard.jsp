<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .stat-card {
            border-radius: 10px;
            padding: 20px;
            color: white;
            transition: transform 0.3s;
        }
        .stat-card:hover {
            transform: translateY(-5px);
        }
        .stat-card.blue {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        .stat-card.green {
            background: linear-gradient(135deg, #5ee7df 0%, #b490ca 100%);
        }
        .stat-card.orange {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }
        .stat-card.purple {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }
    </style>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    
    <div class="container my-5">
        <h2 class="mb-4"><i class="fas fa-tachometer-alt"></i> Admin Dashboard</h2>
        
        <!-- Statistics Cards -->
        <div class="row mb-4">
            <div class="col-md-3 mb-3">
                <div class="stat-card blue">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-uppercase">Tours</h6>
                            <h2 class="mb-0">${totalTours}</h2>
                        </div>
                        <i class="fas fa-map fa-3x opacity-50"></i>
                    </div>
                </div>
            </div>
            
            <div class="col-md-3 mb-3">
                <div class="stat-card green">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-uppercase">Bookings</h6>
                            <h2 class="mb-0">${totalBookings}</h2>
                        </div>
                        <i class="fas fa-ticket-alt fa-3x opacity-50"></i>
                    </div>
                </div>
            </div>
            
            <div class="col-md-3 mb-3">
                <div class="stat-card orange">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-uppercase">Customers</h6>
                            <h2 class="mb-0">${totalCustomers}</h2>
                        </div>
                        <i class="fas fa-users fa-3x opacity-50"></i>
                    </div>
                </div>
            </div>
            
            <div class="col-md-3 mb-3">
                <div class="stat-card purple">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="text-uppercase">Doanh Thu</h6>
                            <h2 class="mb-0">
                                <fmt:formatNumber value="${totalRevenue}" pattern="#,###" />đ
                            </h2>
                        </div>
                        <i class="fas fa-dollar-sign fa-3x opacity-50"></i>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Quick Actions -->
        <div class="row mb-4">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="fas fa-bolt"></i> Thao Tác Nhanh</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-3 mb-2">
                                <a href="${pageContext.request.contextPath}/admin/tours" class="btn btn-outline-primary w-100">
                                    <i class="fas fa-map"></i> Quản Lý Tours
                                </a>
                            </div>
                            <div class="col-md-3 mb-2">
                                <a href="${pageContext.request.contextPath}/admin/bookings" class="btn btn-outline-success w-100">
                                    <i class="fas fa-ticket-alt"></i> Quản Lý Bookings
                                </a>
                            </div>
                            <div class="col-md-3 mb-2">
                                <a href="${pageContext.request.contextPath}/admin/customers" class="btn btn-outline-info w-100">
                                    <i class="fas fa-users"></i> Quản Lý Khách Hàng
                                </a>
                            </div>
                            <div class="col-md-3 mb-2">
                                <a href="${pageContext.request.contextPath}/admin/reports" class="btn btn-outline-warning w-100">
                                    <i class="fas fa-chart-bar"></i> Báo Cáo
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Recent Bookings -->
        <div class="row">
            <div class="col-md-12">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h5 class="mb-0"><i class="fas fa-clock"></i> Bookings Gần Đây</h5>
                    </div>
                    <div class="card-body">
                        <c:choose>
                            <c:when test="${not empty recentBookings}">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                            <tr>
                                                <th>Mã Booking</th>
                                                <th>Tour</th>
                                                <th>Khách Hàng</th>
                                                <th>Ngày Đặt</th>
                                                <th>Tổng Tiền</th>
                                                <th>Trạng Thái</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="booking" items="${recentBookings}" begin="0" end="9">
                                                <tr>
                                                    <td>${booking.bookingCode}</td>
                                                    <td>${booking.tourTitle}</td>
                                                    <td>${booking.customerName}</td>
                                                    <td><fmt:formatDate value="${booking.bookingDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                                    <td><fmt:formatNumber value="${booking.totalPrice}" pattern="#,###"/>đ</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${booking.status == 'PENDING'}">
                                                                <span class="badge bg-warning">Chờ xác nhận</span>
                                                            </c:when>
                                                            <c:when test="${booking.status == 'CONFIRMED'}">
                                                                <span class="badge bg-success">Đã xác nhận</span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="badge bg-secondary">${booking.status}</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p class="text-center text-muted mb-0">Chưa có booking nào</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <%@ include file="../common/footer.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>