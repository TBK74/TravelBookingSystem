<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt Tour - ${tour.title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    
    <div class="container my-5">
        <h2 class="mb-4"><i class="fas fa-ticket-alt"></i> Đặt Tour</h2>
        
        <div class="row">
            <!-- Thông tin tour -->
            <div class="col-lg-5 mb-4">
                <div class="card">
                    <img src="${tour.imageUrl != null ? tour.imageUrl : pageContext.request.contextPath.concat('/images/default-tour.jpg')}" 
                         class="card-img-top" alt="${tour.title}">
                    <div class="card-body">
                        <h5 class="card-title">${tour.title}</h5>
                        <p class="card-text">${tour.description}</p>
                        
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">
                                <i class="fas fa-clock text-primary"></i>
                                Thời gian: ${tour.duration}
                            </li>
                            <li class="list-group-item">
                                <i class="fas fa-bus text-primary"></i>
                                Phương tiện: ${tour.transportation}
                            </li>
                            <li class="list-group-item">
                                <i class="fas fa-map-marker-alt text-primary"></i>
                                Khởi hành: ${tour.departureLocation}
                            </li>
                        </ul>
                        
                        <div class="mt-3">
                            <div class="d-flex justify-content-between">
                                <span>Giá người lớn:</span>
                                <strong class="text-danger">
                                    <fmt:formatNumber value="${tour.priceAdult}" pattern="#,###" />đ
                                </strong>
                            </div>
                            <div class="d-flex justify-content-between">
                                <span>Giá trẻ em:</span>
                                <strong class="text-danger">
                                    <fmt:formatNumber value="${tour.priceChild}" pattern="#,###" />đ
                                </strong>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Form đặt tour -->
            <div class="col-lg-7">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="fas fa-edit"></i> Thông Tin Đặt Tour</h5>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/booking" method="post" id="bookingForm">
                            <input type="hidden" name="tourId" value="${tour.id}">
                            
                            <!-- Thông tin khách hàng -->
                            <h6 class="mb-3">Thông Tin Khách Hàng</h6>
                            
                            <div class="mb-3">
                                <label class="form-label">Họ tên</label>
                                <input type="text" class="form-control" value="${customer.fullName}" readonly>
                            </div>
                            
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Email</label>
                                    <input type="email" class="form-control" value="${customer.user.email}" readonly>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Số điện thoại</label>
                                    <input type="tel" class="form-control" value="${customer.phone}" readonly>
                                </div>
                            </div>
                            
                            <hr>
                            
                            <!-- Thông tin đặt tour -->
                            <h6 class="mb-3">Chi Tiết Đặt Tour</h6>
                            
                            <div class="mb-3">
                                <label class="form-label">Chọn ngày khởi hành <span class="text-danger">*</span></label>
                                <select class="form-select" name="scheduleId" id="scheduleId" required>
                                    <option value="">-- Chọn ngày --</option>
                                    <c:forEach var="schedule" items="${schedules}">
                                        <option value="${schedule.id}" 
                                                data-date="${schedule.departureDate}"
                                                ${selectedScheduleId != null && selectedScheduleId == schedule.id ? 'selected' : ''}>
                                            ${schedule.departureDate} - Còn ${schedule.availableSeats} chỗ
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            
                            <input type="hidden" name="departureDate" id="departureDate">
                            
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label class="form-label">Số người lớn <span class="text-danger">*</span></label>
                                    <input type="number" class="form-control" name="numAdults" id="numAdults" 
                                           min="1" max="20" value="1" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Số trẻ em</label>
                                    <input type="number" class="form-control" name="numChildren" id="numChildren" 
                                           min="0" max="20" value="0">
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label">Ghi chú / Yêu cầu đặc biệt</label>
                                <textarea class="form-control" name="specialRequests" rows="3" 
                                          placeholder="Ví dụ: Cần ghế ngồi gần cửa sổ, có trẻ nhỏ dưới 2 tuổi..."></textarea>
                            </div>
                            
                            <!-- Tổng tiền -->
                            <div class="alert alert-info">
                                <div class="d-flex justify-content-between align-items-center">
                                    <strong>TỔNG TIỀN:</strong>
                                    <h4 class="mb-0 text-danger" id="totalPrice">0đ</h4>
                                </div>
                            </div>
                            
                            <!-- Buttons -->
                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="fas fa-check"></i> Xác Nhận Đặt Tour
                                </button>
                                <a href="${pageContext.request.contextPath}/tour-detail?id=${tour.id}" 
                                   class="btn btn-outline-secondary">
                                    <i class="fas fa-arrow-left"></i> Quay Lại
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <%@ include file="../common/footer.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Tính tổng tiền
        const priceAdult = ${tour.priceAdult};
        const priceChild = ${tour.priceChild};
        
        function calculateTotal() {
            const numAdults = parseInt(document.getElementById('numAdults').value) || 0;
            const numChildren = parseInt(document.getElementById('numChildren').value) || 0;
            
            const total = (numAdults * priceAdult) + (numChildren * priceChild);
            document.getElementById('totalPrice').textContent = total.toLocaleString('vi-VN') + 'đ';
        }
        
        // Update departure date when schedule selected
        document.getElementById('scheduleId').addEventListener('change', function() {
            const selectedOption = this.options[this.selectedIndex];
            const date = selectedOption.getAttribute('data-date');
            document.getElementById('departureDate').value = date;
        });
        
        // Calculate on change
        document.getElementById('numAdults').addEventListener('input', calculateTotal);
        document.getElementById('numChildren').addEventListener('input', calculateTotal);
        
        // Initial calculation
        calculateTotal();
        
        // Form validation
        document.getElementById('bookingForm').addEventListener('submit', function(e) {
            const scheduleId = document.getElementById('scheduleId').value;
            const numAdults = parseInt(document.getElementById('numAdults').value);
            
            if (!scheduleId) {
                e.preventDefault();
                alert('Vui lòng chọn ngày khởi hành!');
                return false;
            }
            
            if (numAdults < 1) {
                e.preventDefault();
                alert('Số người lớn phải ít nhất 1!');
                return false;
            }
        });
    </script>
</body>
</html>