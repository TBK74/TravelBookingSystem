<!-- File: WebContent/views/customer/home.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>	
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ - Hệ thống đặt tour du lịch</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    
    <!-- Hero Section -->
    <section class="hero-section">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-lg-6">
                    <h1 class="display-4 fw-bold mb-4">Khám Phá Thế Giới Cùng Chúng Tôi</h1>
                    <p class="lead mb-4">Đặt tour du lịch dễ dàng, trải nghiệm tuyệt vời</p>
                    <a href="${pageContext.request.contextPath}/tours" class="btn btn-primary btn-lg">
                        <i class="fas fa-search"></i> Tìm Tour Ngay
                    </a>
                </div>
                <div class="col-lg-6">
                    <img src="${pageContext.request.contextPath}/images/hero-image.jpg" alt="Travel" class="img-fluid rounded">
                </div>
            </div>
        </div>
    </section>

    <!-- Featured Tours Section -->
    <section class="featured-tours py-5">
        <div class="container">
            <div class="text-center mb-5">
                <h2 class="section-title">Tour Nổi Bật</h2>
                <p class="text-muted">Những tour du lịch được yêu thích nhất</p>
            </div>
            
            <div class="row g-4">
                <c:forEach var="tour" items="${tours}">
                    <div class="col-lg-4 col-md-6">
                        <div class="card tour-card h-100 shadow-sm">
                            <img src="${tour.imageUrl != null ? tour.imageUrl : pageContext.request.contextPath.concat('/images/default-tour.jpg')}" 
                                 class="card-img-top" alt="${tour.title}" style="height: 250px; object-fit: cover;">
                            
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-start mb-2">
                                    <h5 class="card-title">${tour.title}</h5>
                                    <c:if test="${tour.rating > 0}">
                                        <span class="badge bg-warning text-dark">
                                            <i class="fas fa-star"></i> ${tour.rating}
                                        </span>
                                    </c:if>
                                </div>
                                
                                <p class="card-text text-muted small">
                                    <c:choose>
    									<c:when test="${not empty tour.description and tour.description.length() > 100}">
        									${tour.description.substring(0, 100)}...
    									</c:when>
    									<c:otherwise>
        									${tour.description}
    									</c:otherwise>
									</c:choose>
                                </p>
                                
                                <div class="tour-info mb-3">
                                    <span class="badge bg-info text-dark me-2">
                                        <i class="fas fa-clock"></i> ${tour.durationDays} ngày ${tour.durationNights} đêm
                                    </span>
                                    <span class="badge bg-secondary">
    									<i class="fas fa-map-marker-alt"></i> ${tour.locationName}
									</span>
                                </div>
                                
                                <div class="d-flex justify-content-between align-items-center">
                                    <div>
                                        <span class="text-danger fw-bold fs-5">
                                            <fmt:formatNumber value="${tour.priceAdult}" pattern="#,###" />đ
                                        </span>
                                        <small class="text-muted d-block">/ người</small>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/tour-detail?id=${tour.id}" 
                                       class="btn btn-primary">
                                        Xem Chi Tiết
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            
            <div class="text-center mt-4">
                <a href="${pageContext.request.contextPath}/tours" class="btn btn-outline-primary">
                    Xem Tất Cả Tour <i class="fas fa-arrow-right"></i>
                </a>
            </div>
        </div>
    </section>

    <!-- Categories Section -->
    <section class="categories-section py-5 bg-light">
        <div class="container">
            <div class="text-center mb-5">
                <h2 class="section-title">Danh Mục Tour</h2>
                <p class="text-muted">Chọn loại hình du lịch phù hợp với bạn</p>
            </div>
            
            <div class="row g-3">
                <c:forEach var="category" items="${categories}">
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <a href="${pageContext.request.contextPath}/tours?categoryId=${category.id}" 
                           class="text-decoration-none">
                            <div class="card category-card h-100 text-center">
                                <div class="card-body">
                                    <i class="fas fa-map-signs fa-3x text-primary mb-3"></i>
                                    <h5 class="card-title">${category.name}</h5>
                                    <p class="card-text text-muted small">${category.description}</p>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>

    <%@ include file="../common/footer.jsp" %>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>