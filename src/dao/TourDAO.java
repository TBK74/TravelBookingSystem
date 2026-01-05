package dao;

import model.Tour;
import model.TourSchedule;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO xử lý các thao tác liên quan đến Tours
 */
public class TourDAO {
    
    /**
     * Lấy tất cả tours đang hoạt động
     * @return Danh sách tours
     */
    public List<Tour> getAllTours() {
        List<Tour> tours = new ArrayList<>();
        // SQL JOIN để lấy luôn tên category và location
        String sql = "SELECT t.*, c.name as category_name, l.name as location_name " +
                    "FROM Tours t " +
                    "LEFT JOIN Categories c ON t.category_id = c.id " +
                    "LEFT JOIN Locations l ON t.location_id = l.id " +
                    "WHERE t.is_active = 1 " +
                    "ORDER BY t.created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                tours.add(extractTourFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAllTours: " + e.getMessage());
        }
        return tours;
    }
    
    /**
     * Tìm tour theo ID
     * @param id Tour ID
     * @return Tour object hoặc null
     */
    public Tour findById(Long id) {
        String sql = "SELECT t.*, c.name as category_name, l.name as location_name " +
                    "FROM Tours t " +
                    "LEFT JOIN Categories c ON t.category_id = c.id " +
                    "LEFT JOIN Locations l ON t.location_id = l.id " +
                    "WHERE t.id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return extractTourFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi findById: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Tìm kiếm tours theo từ khóa
     * @param keyword Từ khóa tìm kiếm
     * @return Danh sách tours
     */
    public List<Tour> searchTours(String keyword) {
        List<Tour> tours = new ArrayList<>();
        String sql = "SELECT t.*, c.name as category_name, l.name as location_name " +
                    "FROM Tours t " +
                    "LEFT JOIN Categories c ON t.category_id = c.id " +
                    "LEFT JOIN Locations l ON t.location_id = l.id " +
                    "WHERE t.is_active = 1 AND (t.title LIKE ? OR t.description LIKE ?) " +
                    "ORDER BY t.created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tours.add(extractTourFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi searchTours: " + e.getMessage());
        }
        return tours;
    }
    
    /**
     * Lấy tours theo category
     * @param categoryId Category ID
     * @return Danh sách tours
     */
    public List<Tour> getToursByCategory(Long categoryId) {
        List<Tour> tours = new ArrayList<>();
        String sql = "SELECT t.*, c.name as category_name, l.name as location_name " +
                    "FROM Tours t " +
                    "LEFT JOIN Categories c ON t.category_id = c.id " +
                    "LEFT JOIN Locations l ON t.location_id = l.id " +
                    "WHERE t.category_id = ? AND t.is_active = 1 " +
                    "ORDER BY t.created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, categoryId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                tours.add(extractTourFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getToursByCategory: " + e.getMessage());
        }
        return tours;
    }
    
    /**
     * Lấy tours theo location
     * @param locationId Location ID
     * @return Danh sách tours
     */
    public List<Tour> getToursByLocation(Long locationId) {
        List<Tour> tours = new ArrayList<>();
        String sql = "SELECT t.*, c.name as category_name, l.name as location_name " +
                    "FROM Tours t " +
                    "LEFT JOIN Categories c ON t.category_id = c.id " +
                    "LEFT JOIN Locations l ON t.location_id = l.id " +
                    "WHERE t.location_id = ? AND t.is_active = 1 " +
                    "ORDER BY t.created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, locationId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                tours.add(extractTourFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getToursByLocation: " + e.getMessage());
        }
        return tours;
    }
    
    /**
     * Lấy lịch khởi hành của tour
     * @param tourId Tour ID
     * @return Danh sách lịch khởi hành
     */
    public List<TourSchedule> getSchedulesByTourId(Long tourId) {
        List<TourSchedule> schedules = new ArrayList<>();
        // Chỉ lấy lịch từ hôm nay trở đi và còn chỗ
        String sql = "SELECT * FROM TourSchedules " +
                    "WHERE tour_id = ? " +
                    "AND departure_date >= CAST(GETDATE() AS DATE) " +
                    "AND status = 'AVAILABLE' " +
                    "ORDER BY departure_date";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, tourId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                TourSchedule schedule = new TourSchedule();
                schedule.setId(rs.getLong("id"));
                schedule.setTourId(rs.getLong("tour_id"));
                schedule.setDepartureDate(rs.getDate("departure_date"));
                schedule.setAvailableSeats(rs.getInt("available_seats"));
                schedule.setStatus(rs.getString("status"));
                schedule.setCreatedAt(rs.getTimestamp("created_at"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getSchedulesByTourId: " + e.getMessage());
        }
        return schedules;
    }
    
    /**
     * Tìm schedule theo ID
     * @param scheduleId Schedule ID
     * @return TourSchedule object hoặc null
     */
    public TourSchedule findScheduleById(Long scheduleId) {
        String sql = "SELECT * FROM TourSchedules WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, scheduleId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                TourSchedule schedule = new TourSchedule();
                schedule.setId(rs.getLong("id"));
                schedule.setTourId(rs.getLong("tour_id"));
                schedule.setDepartureDate(rs.getDate("departure_date"));
                schedule.setAvailableSeats(rs.getInt("available_seats"));
                schedule.setStatus(rs.getString("status"));
                schedule.setCreatedAt(rs.getTimestamp("created_at"));
                return schedule;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi findScheduleById: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Đếm tổng số tours
     * @return Số lượng tours
     */
    public int getTotalTours() {
        String sql = "SELECT COUNT(*) FROM Tours WHERE is_active = 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getTotalTours: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Chuyển đổi ResultSet thành Tour object
     */
    private Tour extractTourFromResultSet(ResultSet rs) throws SQLException {
        Tour tour = new Tour();
        tour.setId(rs.getLong("id"));
        tour.setTitle(rs.getString("title"));
        tour.setDescription(rs.getString("description"));
        tour.setCategoryId(rs.getLong("category_id"));
        tour.setLocationId(rs.getLong("location_id"));
        tour.setDurationDays(rs.getInt("duration_days"));
        tour.setDurationNights(rs.getInt("duration_nights"));
        tour.setTransportation(rs.getString("transportation"));
        tour.setDepartureLocation(rs.getString("departure_location"));
        tour.setPriceAdult(rs.getBigDecimal("price_adult"));
        tour.setPriceChild(rs.getBigDecimal("price_child"));
        tour.setMaxParticipants(rs.getInt("max_participants"));
        tour.setImageUrl(rs.getString("image_url"));
        tour.setActive(rs.getBoolean("is_active"));
        tour.setCreatedAt(rs.getTimestamp("created_at"));
        
        // Lấy tên category và location từ JOIN
        try {
            tour.setCategoryName(rs.getString("category_name"));
            tour.setLocationName(rs.getString("location_name"));
        } catch (SQLException e) {
            // Bỏ qua nếu không có trong ResultSet
        }
        
        return tour;
    }
}