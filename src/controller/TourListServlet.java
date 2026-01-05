package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Category;
import model.Location;
import model.Tour;
import dao.CategoryDAO;
import dao.LocationDAO;
import dao.TourDAO;

import java.io.IOException;
import java.util.List;

/**
 * Servlet hiển thị danh sách tours
 * Hỗ trợ tìm kiếm, lọc theo category và location
 * URL: /tours
 */
@WebServlet("/tours")
public class TourListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private TourDAO tourDAO = new TourDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private LocationDAO locationDAO = new LocationDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy parameters từ URL
        String keyword = request.getParameter("keyword");
        String categoryIdStr = request.getParameter("categoryId");
        String locationIdStr = request.getParameter("locationId");
        
        List<Tour> tours;
        
        // Xử lý tìm kiếm/lọc dựa trên parameters
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Tìm kiếm theo từ khóa
            tours = tourDAO.searchTours(keyword);
            request.setAttribute("searchKeyword", keyword);
            
        } else if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
            // Lọc theo category
            try {
                Long categoryId = Long.parseLong(categoryIdStr);
                tours = tourDAO.getToursByCategory(categoryId);
                
                Category category = categoryDAO.findById(categoryId);
                request.setAttribute("selectedCategory", category);
            } catch (NumberFormatException e) {
                tours = tourDAO.getAllTours();
            }
            
        } else if (locationIdStr != null && !locationIdStr.trim().isEmpty()) {
            // Lọc theo location
            try {
                Long locationId = Long.parseLong(locationIdStr);
                tours = tourDAO.getToursByLocation(locationId);
                
                Location location = locationDAO.findById(locationId);
                request.setAttribute("selectedLocation", location);
            } catch (NumberFormatException e) {
                tours = tourDAO.getAllTours();
            }
            
        } else {
            // Không có filter -> lấy tất cả
            tours = tourDAO.getAllTours();
        }
        
        // Lấy danh sách categories và locations cho bộ lọc
        List<Category> categories = categoryDAO.getAllCategories();
        List<Location> locations = locationDAO.getAllLocations();
        
        // Set attributes
        request.setAttribute("tours", tours);
        request.setAttribute("categories", categories);
        request.setAttribute("locations", locations);
        
        // Forward đến JSP
        request.getRequestDispatcher("/views/customer/tourList.jsp").forward(request, response);
    }
}
