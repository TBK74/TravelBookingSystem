package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Filter xử lý encoding UTF-8 cho toàn bộ ứng dụng
 * Tránh lỗi tiếng Việt
 * Chạy cho tất cả requests (/*) 
 */
@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo filter (nếu cần)
        System.out.println("CharacterEncodingFilter đã được khởi tạo");
    }
    
    /**
     * Phương thức chính của Filter
     * Chạy trước mỗi request
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        // Set encoding UTF-8 cho request và response
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        // Cho phép request tiếp tục đến servlet
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Dọn dẹp khi filter bị hủy
        System.out.println("CharacterEncodingFilter đã bị hủy");
    }
}
