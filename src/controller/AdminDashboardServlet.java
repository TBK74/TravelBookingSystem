package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import dao.BookingDAO;
import dao.CustomerDAO;
import dao.TourDAO;

import java.io.IOException;

/**
 * Servlet hiển thị dashboard admin
 * URL: /admin/dashboard
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private TourDAO tourDAO = new TourDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy thống kê
        int totalTours = tourDAO.getTotalTours();
        int totalBookings = bookingDAO.getTotalBookings();
        int totalCustomers = customerDAO.getTotalCustomers();
        double totalRevenue = bookingDAO.getTotalRevenue();
        
        // Set attributes
        request.setAttribute("totalTours", totalTours);
        request.setAttribute("totalBookings", totalBookings);
        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("totalRevenue", totalRevenue);
        
        // Forward đến JSP
        request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
    }
}