package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Booking;
import model.Customer;
import model.Tour;
import model.User;
import dao.BookingDAO;
import dao.TourDAO;

import java.io.IOException;

/**
 * Servlet hiển thị xác nhận booking
 * URL: /booking-confirmation?code=xxx
 */
@WebServlet("/booking-confirmation")
public class BookingConfirmationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private BookingDAO bookingDAO = new BookingDAO();
    private TourDAO tourDAO = new TourDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Kiểm tra đăng nhập
        HttpSession session = request.getSession(false);
        User user = null;
        
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Lấy booking code
        String bookingCode = request.getParameter("code");
        if (bookingCode == null || bookingCode.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        // Tìm booking
        Booking booking = bookingDAO.findByBookingCode(bookingCode);
        
        if (booking == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy booking!");
            return;
        }
        
        // Kiểm tra quyền xem (customer chỉ xem booking của mình)
        if (user.isCustomer()) {
            Customer customer = (Customer) session.getAttribute("customer");
            if (customer == null || !booking.getCustomerId().equals(customer.getId())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, 
                                 "Bạn không có quyền xem booking này!");
                return;
            }
        }
        
        // Lấy thông tin tour
        Tour tour = tourDAO.findById(booking.getTourId());
        
        // Set attributes
        request.setAttribute("booking", booking);
        request.setAttribute("tour", tour);
        
        // Forward đến JSP
        request.getRequestDispatcher("/views/customer/bookingConfirmation.jsp")
               .forward(request, response);
    }
}