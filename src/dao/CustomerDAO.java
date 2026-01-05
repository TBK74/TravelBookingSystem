package dao;

import model.Customer;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO xử lý các thao tác liên quan đến Customers
 */
public class CustomerDAO {
    
    /**
     * Tạo customer mới
     * @param customer Customer cần tạo
     * @return true nếu thành công
     */
    public boolean createCustomer(Customer customer) {
        String sql = "INSERT INTO Customers (user_id, full_name, phone, address) " +
                    "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setLong(1, customer.getUserId());
            ps.setString(2, customer.getFullName());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getAddress());
            
            int affectedRows = ps.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getLong(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi createCustomer: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Tìm customer theo user ID
     * @param userId User ID
     * @return Customer object hoặc null
     */
    public Customer findByUserId(Long userId) {
        String sql = "SELECT * FROM Customers WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return extractCustomerFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi findByUserId: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Tìm customer theo ID
     * @param id Customer ID
     * @return Customer object hoặc null
     */
    public Customer findById(Long id) {
        String sql = "SELECT * FROM Customers WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return extractCustomerFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi findById: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Lấy tất cả customers
     * @return Danh sách customers
     */
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                customers.add(extractCustomerFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAllCustomers: " + e.getMessage());
        }
        return customers;
    }
    
    /**
     * Đếm tổng số customers
     * @return Số lượng customers
     */
    public int getTotalCustomers() {
        String sql = "SELECT COUNT(*) FROM Customers";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getTotalCustomers: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Chuyển đổi ResultSet thành Customer object
     */
    private Customer extractCustomerFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getLong("id"));
        customer.setUserId(rs.getLong("user_id"));
        customer.setFullName(rs.getString("full_name"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setCreatedAt(rs.getTimestamp("created_at"));
        return customer;
    }
}