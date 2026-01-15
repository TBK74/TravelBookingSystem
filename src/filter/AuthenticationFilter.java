package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import model.User;

import java.io.IOException;

/**
 * Filter kiểm tra đăng nhập
 * Bảo vệ các trang yêu cầu đăng nhập
 * Apply cho: /booking, /my-bookings, /cancel-booking
 */
@WebFilter(urlPatterns = {"/booking", "/my-bookings", "/cancel-booking", 
                          "/booking-confirmation"})
public class AuthenticationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("AuthenticationFilter đã được khởi tạo");
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        // Cast sang HttpServlet types
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Lấy session (không tạo mới)
        HttpSession session = httpRequest.getSession(false);
        
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        
        // Kiểm tra user đã đăng nhập chưa
        if (user == null) {
            // Chưa đăng nhập -> lưu URL hiện tại và chuyển đến login
            session = httpRequest.getSession(true);
            session.setAttribute("redirectAfterLogin", httpRequest.getRequestURI());
            session.setAttribute("errorMessage", "Vui lòng đăng nhập để tiếp tục!");
            
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }
        
        // Đã đăng nhập -> cho phép tiếp tục
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        System.out.println("AuthenticationFilter đã bị hủy");
    }
}