package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import model.Customer;
import dao.UserDAO;
import dao.CustomerDAO;
import util.PasswordUtil;
import util.ValidationUtil;

import java.io.IOException;

/**
 * Servlet xử lý đăng ký tài khoản
 * URL: /register
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserDAO userDAO = new UserDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    
    /**
     * GET - Hiển thị form đăng ký
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
    }
    
    /**
     * POST - Xử lý đăng ký
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy dữ liệu từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        
        // === VALIDATION ===
        
        // Kiểm tra các trường bắt buộc
        if (ValidationUtil.isNullOrEmpty(username) || 
            ValidationUtil.isNullOrEmpty(password) ||
            ValidationUtil.isNullOrEmpty(email) ||
            ValidationUtil.isNullOrEmpty(fullName)) {
            
            request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin bắt buộc!");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return;
        }
        
        // Kiểm tra password khớp
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return;
        }
        
        // Kiểm tra email hợp lệ
        if (!ValidationUtil.isValidEmail(email)) {
            request.setAttribute("errorMessage", "Email không hợp lệ!");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return;
        }
        
        // Kiểm tra password đủ mạnh
        if (!ValidationUtil.isValidPassword(password)) {
            request.setAttribute("errorMessage", "Mật khẩu phải có ít nhất 6 ký tự!");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return;
        }
        
        // Kiểm tra số điện thoại (nếu có)
        if (phone != null && !phone.trim().isEmpty() && !ValidationUtil.isValidPhone(phone)) {
            request.setAttribute("errorMessage", "Số điện thoại không hợp lệ!");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return;
        }
        
        // Kiểm tra username đã tồn tại
        if (userDAO.findByUsername(username) != null) {
            request.setAttribute("errorMessage", "Tên đăng nhập đã tồn tại!");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return;
        }
        
        // Kiểm tra email đã tồn tại
        if (userDAO.findByEmail(email) != null) {
            request.setAttribute("errorMessage", "Email đã được sử dụng!");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            return;
        }
        
        // === TẠO TÀI KHOẢN ===
        
        // Tạo User
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordUtil.hashPassword(password)); // Hash password
        user.setRole("CUSTOMER");
        user.setEmail(email);
        user.setActive(true);
        
        boolean userCreated = userDAO.createUser(user);
        
        if (userCreated) {
            // Tạo Customer profile
            Customer customer = new Customer();
            customer.setUserId(user.getId());
            customer.setFullName(fullName);
            customer.setPhone(phone);
            customer.setAddress(address);
            
            boolean customerCreated = customerDAO.createCustomer(customer);
            
            if (customerCreated) {
                // Đăng ký thành công
                HttpSession session = request.getSession();
                session.setAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                request.setAttribute("errorMessage", "Lỗi tạo thông tin khách hàng!");
                request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Lỗi tạo tài khoản!");
            request.getRequestDispatcher("/views/auth/register.jsp").forward(request, response);
        }
    }
}