package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Booking;
import model.Customer;
import model.User;
import dao.BookingDAO;

import java.io.IOException;

/**
 * Servlet xử lý hủy booking
 * URL: /cancel-booking
 * Method: POST
 */
@WebServlet("/cancel-booking")
public class CancelBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private BookingDAO bookingDAO = new BookingDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        User user = null;
        Customer customer = null;
        
        if (session != null) {
            user = (User) session.getAttribute("user");
            customer = (Customer) session.getAttribute("customer");
        }
        
        if (user == null || customer == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            // Lấy booking ID
            Long bookingId = Long.parseLong(request.getParameter("bookingId"));
            
            // Lấy thông tin booking
            Booking booking = bookingDAO.findById(bookingId);
            
            if (booking == null) {
                session.setAttribute("errorMessage", "Booking không tồn tại!");
                response.sendRedirect(request.getContextPath() + "/my-bookings");
                return;
            }
            
            // Kiểm tra quyền hủy (chỉ hủy booking của mình)
            if (!booking.getCustomerId().equals(customer.getId())) {
                session.setAttribute("errorMessage", "Bạn không có quyền hủy booking này!");
                response.sendRedirect(request.getContextPath() + "/my-bookings");
                return;
            }
            
            // Kiểm tra trạng thái
            if ("CANCELLED".equals(booking.getStatus())) {
                session.setAttribute("errorMessage", "Booking đã bị hủy trước đó!");
                response.sendRedirect(request.getContextPath() + "/my-bookings");
                return;
            }
            
            // Hủy booking
            boolean success = bookingDAO.cancelBooking(bookingId);
            
            if (success) {
                session.setAttribute("successMessage", "Hủy đặt tour thành công!");
            } else {
                session.setAttribute("errorMessage", "Không thể hủy booking này!");
            }
            
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/my-bookings");
    }
}