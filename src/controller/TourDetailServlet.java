package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Tour;
import model.TourSchedule;
import dao.TourDAO;

import java.io.IOException;
import java.util.List;

/**
 * Servlet hiển thị chi tiết tour
 * URL: /tour-detail?id=xxx
 */
@WebServlet("/tour-detail")
public class TourDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private TourDAO tourDAO = new TourDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy tour ID từ parameter
        String tourIdStr = request.getParameter("id");
        
        if (tourIdStr == null || tourIdStr.trim().isEmpty()) {
            // Không có ID -> chuyển về trang tours
            response.sendRedirect(request.getContextPath() + "/tours");
            return;
        }
        
        try {
            Long tourId = Long.parseLong(tourIdStr);
            
            // Lấy thông tin tour
            Tour tour = tourDAO.findById(tourId);
            
            if (tour == null || !tour.isActive()) {
                // Tour không tồn tại hoặc không hoạt động
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Tour không tồn tại!");
                return;
            }
            
            // Lấy lịch khởi hành của tour
            List<TourSchedule> schedules = tourDAO.getSchedulesByTourId(tourId);
            
            // Set attributes
            request.setAttribute("tour", tour);
            request.setAttribute("schedules", schedules);
            
            // Forward đến JSP
            request.getRequestDispatcher("/views/customer/tourDetail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            // ID không hợp lệ
            response.sendRedirect(request.getContextPath() + "/tours");
        }
    }
}