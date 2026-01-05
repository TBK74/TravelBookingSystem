package dao;

import model.Location;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO xử lý các thao tác liên quan đến Locations
 */
public class LocationDAO {
    
    /**
     * Lấy tất cả locations
     * @return Danh sách locations
     */
    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM Locations ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Location location = new Location();
                location.setId(rs.getLong("id"));
                location.setName(rs.getString("name"));
                location.setCountry(rs.getString("country"));
                location.setDescription(rs.getString("description"));
                locations.add(location);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAllLocations: " + e.getMessage());
        }
        return locations;
    }
    
    /**
     * Tìm location theo ID
     * @param id Location ID
     * @return Location object hoặc null
     */
    public Location findById(Long id) {
        String sql = "SELECT * FROM Locations WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Location location = new Location();
                location.setId(rs.getLong("id"));
                location.setName(rs.getString("name"));
                location.setCountry(rs.getString("country"));
                location.setDescription(rs.getString("description"));
                return location;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi findById: " + e.getMessage());
        }
        return null;
    }
}