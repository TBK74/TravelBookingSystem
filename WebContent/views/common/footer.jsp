<!-- File: WebContent/views/common/footer.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<footer class="bg-dark text-light pt-5 pb-3 mt-5">
    <div class="container">
        <div class="row">
            <!-- About Section -->
            <div class="col-lg-4 col-md-6 mb-4">
                <h5 class="fw-bold mb-3">
                    <i class="fas fa-plane"></i> Travel Booking
                </h5>
                <p class="text-muted">
                    Hệ thống đặt tour du lịch trực tuyến uy tín, chuyên nghiệp.
                    Đồng hành cùng bạn trên mọi chuyến đi.
                </p>
                <div class="social-links">
                    <a href="#" class="text-light me-3"><i class="fab fa-facebook fa-lg"></i></a>
                    <a href="#" class="text-light me-3"><i class="fab fa-instagram fa-lg"></i></a>
                    <a href="#" class="text-light me-3"><i class="fab fa-youtube fa-lg"></i></a>
                    <a href="#" class="text-light"><i class="fab fa-twitter fa-lg"></i></a>
                </div>
            </div>
            
            <!-- Quick Links -->
            <div class="col-lg-2 col-md-6 mb-4">
                <h6 class="fw-bold mb-3">Liên Kết</h6>
                <ul class="list-unstyled">
                    <li class="mb-2">
                        <a href="${pageContext.request.contextPath}/" class="text-muted text-decoration-none">
                            <i class="fas fa-angle-right"></i> Trang Chủ
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="${pageContext.request.contextPath}/tours" class="text-muted text-decoration-none">
                            <i class="fas fa-angle-right"></i> Tour Du Lịch
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="${pageContext.request.contextPath}/about" class="text-muted text-decoration-none">
                            <i class="fas fa-angle-right"></i> Giới Thiệu
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="${pageContext.request.contextPath}/contact" class="text-muted text-decoration-none">
                            <i class="fas fa-angle-right"></i> Liên Hệ
                        </a>
                    </li>
                </ul>
            </div>
            
            <!-- Support -->
            <div class="col-lg-3 col-md-6 mb-4">
                <h6 class="fw-bold mb-3">Hỗ Trợ</h6>
                <ul class="list-unstyled">
                    <li class="mb-2">
                        <a href="${pageContext.request.contextPath}/terms" class="text-muted text-decoration-none">
                            <i class="fas fa-angle-right"></i> Điều Khoản
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="${pageContext.request.contextPath}/privacy" class="text-muted text-decoration-none">
                            <i class="fas fa-angle-right"></i> Chính Sách
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="${pageContext.request.contextPath}/faq" class="text-muted text-decoration-none">
                            <i class="fas fa-angle-right"></i> Câu Hỏi
                        </a>
                    </li>
                </ul>
            </div>
            
            <!-- Contact Info -->
            <div class="col-lg-3 col-md-6 mb-4">
                <h6 class="fw-bold mb-3">Liên Hệ</h6>
                <ul class="list-unstyled text-muted">
                    <li class="mb-2">
                        <i class="fas fa-map-marker-alt me-2"></i>
                        Hồ Chí Minh, Việt Nam
                    </li>
                    <li class="mb-2">
                        <i class="fas fa-phone me-2"></i>
                        <a href="tel:0123456789" class="text-muted text-decoration-none">
                            0123-456-789
                        </a>
                    </li>
                    <li class="mb-2">
                        <i class="fas fa-envelope me-2"></i>
                        <a href="mailto:info@travel.com" class="text-muted text-decoration-none">
                            info@travel.com
                        </a>
                    </li>
                    <li class="mb-2">
                        <i class="fas fa-clock me-2"></i>
                        8:00 - 20:00 (Hằng ngày)
                    </li>
                </ul>
            </div>
        </div>
        
        <hr class="border-secondary">
        
        <div class="row">
            <div class="col-md-6 text-center text-md-start">
                <p class="text-muted small mb-0">
                    &copy; 2024 Travel Booking System. All rights reserved.
                </p>
            </div>
            <div class="col-md-6 text-center text-md-end">
                <p class="text-muted small mb-0">
                    Designed by <a href="#" class="text-decoration-none">TBK74</a>
                </p>
            </div>
        </div>
    </div>
</footer>