package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Customer;
import model.User;
import dao.UserDAO;
import dao.CustomerDAO;
import util.PasswordUtil;

import java.io.IOException;

/**
 * Servlet xử lý đăng nhập
 * URL: /login
 * Method: GET hiển thị form, POST xử lý đăng nhập
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserDAO userDAO = new UserDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    
    /**
     * Xử lý GET request - Hiển thị trang đăng nhập
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Kiểm tra đã đăng nhập chưa
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            // Nếu đã login, chuyển đến trang tương ứng
            if (user.isAdmin()) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
            return;
        }
        
        // Chưa login -> hiển thị form đăng nhập
        request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
    }
    
    /**
     * Xử lý POST request - Xử lý đăng nhập
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy parameters từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Tìm user trong database
        User user = userDAO.findByUsername(username);
        
        // Kiểm tra user tồn tại và password đúng
        if (user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {
            // Đăng nhập thành công
            
            // Tạo session mới
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(3600); // Timeout 1 giờ
            
            // Nếu là customer, lấy thông tin customer
            if (user.isCustomer()) {
                Customer customer = customerDAO.findByUserId(user.getId());
                session.setAttribute("customer", customer);
                
                // Chuyển về trang chủ
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                // Admin chuyển đến dashboard
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            }
        } else {
            // Đăng nhập thất bại
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("/views/auth/login.jsp").forward(request, response);
        }
    }
}