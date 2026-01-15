package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Booking;
import model.Customer;
import model.Tour;
import model.TourSchedule;
import model.User;
import dao.BookingDAO;
import dao.TourDAO;
import util.BookingCodeGenerator;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * Servlet xử lý đặt tour
 * URL: /booking
 * GET: Hiển thị form đặt tour
 * POST: Xử lý tạo booking
 */
@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private TourDAO tourDAO = new TourDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    
    /**
     * GET - Hiển thị form đặt tour
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Kiểm tra đã đăng nhập chưa
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        
        if (user == null || !user.isCustomer()) {
            // Chưa đăng nhập hoặc không phải customer
            session = request.getSession(true);
            session.setAttribute("errorMessage", "Vui lòng đăng nhập để đặt tour!");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Lấy parameters
        String tourIdStr = request.getParameter("tourId");
        String scheduleIdStr = request.getParameter("scheduleId");
        
        if (tourIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/tours");
            return;
        }
        
        try {
            Long tourId = Long.parseLong(tourIdStr);
            
            // Lấy thông tin tour
            Tour tour = tourDAO.findById(tourId);
            if (tour == null || !tour.isActive()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tour không tồn tại!");
                return;
            }
            
            // Lấy thông tin customer
            Customer customer = (Customer) session.getAttribute("customer");
            
            // Lấy lịch khởi hành
            List<TourSchedule> schedules = tourDAO.getSchedulesByTourId(tourId);
            
            // Set attributes
            request.setAttribute("tour", tour);
            request.setAttribute("schedules", schedules);
            request.setAttribute("customer", customer);
            request.setAttribute("selectedScheduleId", scheduleIdStr);
            
            // Forward đến form đặt tour
            request.getRequestDispatcher("/views/customer/bookingForm.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/tours");
        }
    }
    
    /**
     * POST - Xử lý tạo booking
     */
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
            session = request.getSession(true);
            session.setAttribute("errorMessage", "Vui lòng đăng nhập để đặt tour!");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            // Lấy dữ liệu từ form
            Long tourId = Long.parseLong(request.getParameter("tourId"));
            Long scheduleId = Long.parseLong(request.getParameter("scheduleId"));
            String departureDateStr = request.getParameter("departureDate");
            int numAdults = Integer.parseInt(request.getParameter("numAdults"));
            int numChildren = Integer.parseInt(request.getParameter("numChildren"));
            String specialRequests = request.getParameter("specialRequests");
            
            // Validate
            if (numAdults < 1) {
                session.setAttribute("errorMessage", "Số người lớn phải ít nhất 1!");
                response.sendRedirect(request.getContextPath() + "/booking?tourId=" + tourId);
                return;
            }
            
            // Lấy thông tin tour
            Tour tour = tourDAO.findById(tourId);
            if (tour == null) {
                session.setAttribute("errorMessage", "Tour không tồn tại!");
                response.sendRedirect(request.getContextPath() + "/tours");
                return;
            }
            
            // Tính tổng tiền
            BigDecimal totalPrice = tour.getPriceAdult().multiply(new BigDecimal(numAdults))
                                      .add(tour.getPriceChild().multiply(new BigDecimal(numChildren)));
            
            // Tạo booking
            Booking booking = new Booking();
            booking.setBookingCode(BookingCodeGenerator.generateBookingCode());
            booking.setCustomerId(customer.getId());
            booking.setTourId(tourId);
            booking.setScheduleId(scheduleId);
            booking.setDepartureDate(Date.valueOf(departureDateStr));
            booking.setNumAdults(numAdults);
            booking.setNumChildren(numChildren);
            booking.setTotalPrice(totalPrice);
            booking.setStatus("PENDING");
            booking.setSpecialRequests(specialRequests);
            
            // Lưu vào database
            boolean success = bookingDAO.createBooking(booking);
            
            if (success) {
                // Đặt tour thành công
                response.sendRedirect(request.getContextPath() + "/booking-confirmation?code=" + 
                                    booking.getBookingCode());
            } else {
                session.setAttribute("errorMessage", "Đặt tour không thành công. Vui lòng thử lại!");
                response.sendRedirect(request.getContextPath() + "/booking?tourId=" + tourId);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/tours");
        }
    }
}
