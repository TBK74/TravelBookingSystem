package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Booking;
import dao.BookingDAO;

import java.io.IOException;
import java.util.List;

/**
 * Servlet quản lý bookings (Admin)
 * URL: /admin/bookings
 */
@WebServlet("/admin/bookings")
public class AdminBookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private BookingDAO bookingDAO = new BookingDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy tất cả bookings
        List<Booking> bookings = bookingDAO.getAllBookings();
        
        // Set attribute
        request.setAttribute("bookings", bookings);
        
        // Forward đến JSP
        request.getRequestDispatcher("/views/admin/bookings.jsp").forward(request, response);
    }
}