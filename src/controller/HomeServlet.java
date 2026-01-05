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
 * Servlet hiển thị trang chủ
 * URL: / hoặc /home
 */
@WebServlet(urlPatterns = {"/", "/home"})
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private TourDAO tourDAO = new TourDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private LocationDAO locationDAO = new LocationDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Lấy danh sách tours
        List<Tour> tours = tourDAO.getAllTours();
        
        // Lấy danh sách categories
        List<Category> categories = categoryDAO.getAllCategories();
        
        // Lấy danh sách locations
        List<Location> locations = locationDAO.getAllLocations();
        
        // Set attributes để sử dụng trong JSP
        request.setAttribute("tours", tours);
        request.setAttribute("categories", categories);
        request.setAttribute("locations", locations);
        
        // Forward đến trang home.jsp
        request.getRequestDispatcher("/views/customer/home.jsp").forward(request, response);
    }
}
