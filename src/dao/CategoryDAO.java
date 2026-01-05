package dao;

import model.Category;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO xử lý các thao tác liên quan đến Categories
 */
public class CategoryDAO {
    
    /**
     * Lấy tất cả categories đang hoạt động
     * @return Danh sách categories
     */
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories WHERE is_active = 1 ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setActive(rs.getBoolean("is_active"));
                categories.add(category);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAllCategories: " + e.getMessage());
        }
        return categories;
    }
    
    /**
     * Tìm category theo ID
     * @param id Category ID
     * @return Category object hoặc null
     */
    public Category findById(Long id) {
        String sql = "SELECT * FROM Categories WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Category category = new Category();
                category.setId(rs.getLong("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setActive(rs.getBoolean("is_active"));
                return category;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi findById: " + e.getMessage());
        }
        return null;
    }
}