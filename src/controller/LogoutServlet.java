package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Servlet xử lý đăng xuất
 * URL: /logout
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy session hiện tại (không tạo mới)
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Hủy session
            session.invalidate();
        }
        
        // Chuyển về trang chủ
        response.sendRedirect(request.getContextPath() + "/");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}