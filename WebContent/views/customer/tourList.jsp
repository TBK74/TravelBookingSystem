<!-- File: WebContent/views/customer/tourList.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Tour</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    
    <div class="container my-5">
        <div class="row">
            <!-- Filters Sidebar -->
            <div class="col-lg-3 mb-4">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0"><i class="fas fa-filter"></i> Bộ Lọc</h5>
                    </div>
                    <div class="card-body">
                        <!-- Search Form -->
                        <form action="${pageContext.request.contextPath}/tours" method="get" class="mb-4">
                            <div class="mb-3">
                                <label class="form-label">Tìm Kiếm</label>
                                <div class="input-group">
                                    <input type="text" name="keyword" class="form-control" 
                                           placeholder="Nhập từ khóa..." value="${searchKeyword}">
                                    <button class="btn btn-primary" type="submit">
                                        <i class="fas fa-search"></i>
                                    </button>
                                </div>
                            </div>
                        </form>
                        
                        <!-- Category Filter -->
                        <div class="mb-4">
                            <h6 class="fw-bold">Danh Mục</h6>
                            <div class="list-group">
                                <a href="${pageContext.request.contextPath}/tours" 
                                   class="list-group-item list-group-item-action ${selectedCategory == null ? 'active' : ''}">
                                    Tất Cả
                                </a>
                                <c:forEach var="category" items="${categories}">
                                    <a href="${pageContext.request.contextPath}/tours?categoryId=${category.id}" 
                                       class="list-group-item list-group-item-action ${selectedCategory != null && selectedCategory.id == category.id ? 'active' : ''}">
                                        ${category.name}
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                        
                        <!-- Location Filter -->
                        <div>
                            <h6 class="fw-bold">Địa Điểm</h6>
                            <div class="list-group">
                                <c:forEach var="location" items="${locations}">
                                    <a href="${pageContext.request.contextPath}/tours?locationId=${location.id}" 
                                       class="list-group-item list-group-item-action ${selectedLocation != null && selectedLocation.id == location.id ? 'active' : ''}">
                                        ${location.name}, ${location.country}
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Tours List -->
            <div class="col-lg-9">
                <div class="mb-4">
                    <h2>Danh Sách Tour
                        <c:if test="${selectedCategory != null}">
                            - ${selectedCategory.name}
                        </c:if>
                        <c:if test="${selectedLocation != null}">
                            - ${selectedLocation.name}
                        </c:if>
                    </h2>
                    <p class="text-muted">Tìm thấy ${tours.size()} tour</p>
                </div>
                
                <div class="row g-4">
                    <c:choose>
                        <c:when test="${tours != null && tours.size() > 0}">
                            <c:forEach var="tour" items="${tours}">
                                <div class="col-md-6">
                                    <div class="card tour-card h-100 shadow-sm">
                                        <img src="${tour.imageUrl != null ? tour.imageUrl : pageContext.request.contextPath.concat('/images/default-tour.jpg')}" 
                                             class="card-img-top" alt="${tour.title}" style="height: 200px; object-fit: cover;">
                                        
                                        <div class="card-body">
                                            <h5 class="card-title">${tour.title}</h5>
                                            <p class="card-text text-muted small">
                                                ${tour.description.length() > 150 ? tour.description.substring(0, 150).concat('...') : tour.description}
                                            </p>
                                            
                                            <div class="tour-info mb-3">
                                                <span class="badge bg-info text-dark me-2">
                                                    <i class="fas fa-clock"></i> ${tour.duration}
                                                </span>
                                                <c:if test="${tour.rating > 0}">
                                                    <span class="badge bg-warning text-dark">
                                                        <i class="fas fa-star"></i> ${tour.rating} (${tour.totalReviews})
                                                    </span>
                                                </c:if>
                                            </div>
                                            
                                            <div class="d-flex justify-content-between align-items-center">
                                                <div>
                                                    <span class="text-danger fw-bold fs-5">
                                                        <fmt:formatNumber value="${tour.priceAdult}" pattern="#,###" />đ
                                                    </span>
                                                    <small class="text-muted d-block">/ người lớn</small>
                                                </div>
                                                <a href="${pageContext.request.contextPath}/tour-detail?id=${tour.id}" 
                                                   class="btn btn-primary">
                                                    Chi Tiết
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="col-12">
                                <div class="alert alert-info text-center">
                                    <i class="fas fa-info-circle"></i> Không tìm thấy tour nào phù hợp.
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    
    <%@ include file="../common/footer.jsp" %>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>