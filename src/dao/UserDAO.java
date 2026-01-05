package dao;

import model.User;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO xử lý các thao tác liên quan đến Users
 * Sử dụng JDBC để thực hiện CRUD operations
 */
public class UserDAO {
    
    /**
     * Tìm user theo username
     * @param username Username cần tìm
     * @return User object hoặc null nếu không tìm thấy
     */
    public User findByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Set parameter cho PreparedStatement
            ps.setString(1, username);
            
            // Thực thi query
            ResultSet rs = ps.executeQuery();
            
            // Nếu tìm thấy, tạo User object từ ResultSet
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi findByUsername: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Tìm user theo email
     * @param email Email cần tìm
     * @return User object hoặc null
     */
    public User findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi findByEmail: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Tìm user theo ID
     * @param id ID cần tìm
     * @return User object hoặc null
     */
    public User findById(Long id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi findById: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Tạo user mới
     * @param user User cần tạo
     * @return true nếu tạo thành công
     */
    public boolean createUser(User user) {
        String sql = "INSERT INTO Users (username, password, role, email, is_active) " +
                    "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Set các parameters
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getEmail());
            ps.setBoolean(5, user.isActive());
            
            // Thực thi INSERT
            int affectedRows = ps.executeUpdate();
            
            // Nếu insert thành công, lấy ID vừa tạo
            if (affectedRows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi createUser: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Lấy tất cả users
     * @return Danh sách users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Duyệt qua tất cả kết quả
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAllUsers: " + e.getMessage());
        }
        return users;
    }
    
    /**
     * Chuyển đổi ResultSet thành User object
     * @param rs ResultSet từ query
     * @return User object
     * @throws SQLException
     */
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        user.setEmail(rs.getString("email"));
        user.setActive(rs.getBoolean("is_active"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}