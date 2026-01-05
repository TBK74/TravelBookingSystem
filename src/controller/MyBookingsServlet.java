package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Booking;
import model.Customer;
import model.User;
import dao.BookingDAO;

import java.io.IOException;
import java.util.List;

/**
 * Servlet hiển thị danh sách bookings của customer
 * URL: /my-bookings
 */
@WebServlet("/my-bookings")
public class MyBookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private BookingDAO bookingDAO = new BookingDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
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
        
        // Lấy danh sách bookings của customer
        List<Booking> bookings = bookingDAO.getBookingsByCustomerId(customer.getId());
        
        // Set attribute
        request.setAttribute("bookings", bookings);
        
        // Forward đến JSP
        request.getRequestDispatcher("/views/customer/myBookings.jsp").forward(request, response);
    }
}