package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import model.User;

import java.io.IOException;

/**
 * Filter kiểm tra quyền Admin
 * Bảo vệ các trang quản trị
 * Apply cho tất cả URLs bắt đầu bằng /admin/*
 */
@WebFilter("/admin/*")
public class AdminAuthorizationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AdminAuthorizationFilter đã được khởi tạo");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Lấy session
        HttpSession session = httpRequest.getSession(false);
        
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        
        // Kiểm tra chưa đăng nhập
        if (user == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }
        
        // Kiểm tra không phải admin
        if (!user.isAdmin()) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, 
                                  "Bạn không có quyền truy cập trang này!");
            return;
        }
        
        // Đã đăng nhập và là admin -> cho phép tiếp tục
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        System.out.println("AdminAuthorizationFilter đã bị hủy");
    }
}