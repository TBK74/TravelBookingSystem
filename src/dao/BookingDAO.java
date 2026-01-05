package dao;

import model.Booking;
import util.DatabaseConnection;
import util.BookingCodeGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO xử lý các thao tác liên quan đến Bookings
 */
public class BookingDAO {
    
    /**
     * Tạo booking mới
     * Sử dụng Transaction để đảm bảo tính toàn vẹn dữ liệu
     * 
     * @param booking Booking cần tạo
     * @return true nếu thành công
     */
    public boolean createBooking(Booking booking) {
        Connection conn = null;
        
        try {
            // Lấy connection và tắt auto-commit để sử dụng transaction
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Tạo mã booking nếu chưa có
            if (booking.getBookingCode() == null || booking.getBookingCode().isEmpty()) {
                booking.setBookingCode(BookingCodeGenerator.generateBookingCode());
            }
            
            // SQL INSERT booking
            String sqlInsert = "INSERT INTO Bookings (booking_code, customer_id, tour_id, schedule_id, " +
                              "departure_date, num_adults, num_children, total_price, status, special_requests) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            
            psInsert.setString(1, booking.getBookingCode());
            psInsert.setLong(2, booking.getCustomerId());
            psInsert.setLong(3, booking.getTourId());
            psInsert.setLong(4, booking.getScheduleId());
            psInsert.setDate(5, booking.getDepartureDate());
            psInsert.setInt(6, booking.getNumAdults());
            psInsert.setInt(7, booking.getNumChildren());
            psInsert.setBigDecimal(8, booking.getTotalPrice());
            psInsert.setString(9, booking.getStatus() != null ? booking.getStatus() : "PENDING");
            psInsert.setString(10, booking.getSpecialRequests());
            
            int affectedRows = psInsert.executeUpdate();
            
            if (affectedRows > 0) {
                // Lấy ID vừa tạo
                ResultSet generatedKeys = psInsert.getGeneratedKeys();
                if (generatedKeys.next()) {
                    booking.setId(generatedKeys.getLong(1));
                }
                
                // Cập nhật số chỗ trống trong schedule
                String sqlUpdate = "UPDATE TourSchedules " +
                                  "SET available_seats = available_seats - ? " +
                                  "WHERE id = ?";
                
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setInt(1, booking.getTotalParticipants());
                psUpdate.setLong(2, booking.getScheduleId());
                psUpdate.executeUpdate();
                
                // Commit transaction
                conn.commit();
                return true;
            }
            
            // Rollback nếu có lỗi
            conn.rollback();
            return false;
            
        } catch (SQLException e) {
            // Rollback nếu có exception
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Lỗi rollback: " + ex.getMessage());
                }
            }
            System.err.println("Lỗi createBooking: " + e.getMessage());
            return false;
            
        } finally {
            // Restore auto-commit và đóng connection
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi đóng connection: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Tìm booking theo mã booking
     * @param bookingCode Mã booking
     * @return Booking object hoặc null
     */
    public Booking findByBookingCode(String bookingCode) {
        String sql = "SELECT b.*, c.full_name as customer_name, t.title as tour_title " +
                    "FROM Bookings b " +
                    "LEFT JOIN Customers c ON b.customer_id = c.id " +
                    "LEFT JOIN Tours t ON b.tour_id = t.id " +
                    "WHERE b.booking_code = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, bookingCode);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return extractBookingFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi findByBookingCode: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Tìm booking theo ID
     * @param id Booking ID
     * @return Booking object hoặc null
     */
    public Booking findById(Long id) {
        String sql = "SELECT b.*, c.full_name as customer_name, t.title as tour_title " +
                    "FROM Bookings b " +
                    "LEFT JOIN Customers c ON b.customer_id = c.id " +
                    "LEFT JOIN Tours t ON b.tour_id = t.id " +
                    "WHERE b.id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return extractBookingFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi findById: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Lấy danh sách bookings của một customer
     * @param customerId Customer ID
     * @return Danh sách bookings
     */
    public List<Booking> getBookingsByCustomerId(Long customerId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, t.title as tour_title " +
                    "FROM Bookings b " +
                    "LEFT JOIN Tours t ON b.tour_id = t.id " +
                    "WHERE b.customer_id = ? " +
                    "ORDER BY b.booking_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, customerId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                bookings.add(extractBookingFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getBookingsByCustomerId: " + e.getMessage());
        }
        return bookings;
    }
    
    /**
     * Lấy tất cả bookings (cho admin)
     * @return Danh sách bookings
     */
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT b.*, c.full_name as customer_name, t.title as tour_title " +
                    "FROM Bookings b " +
                    "LEFT JOIN Customers c ON b.customer_id = c.id " +
                    "LEFT JOIN Tours t ON b.tour_id = t.id " +
                    "ORDER BY b.booking_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                bookings.add(extractBookingFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAllBookings: " + e.getMessage());
        }
        return bookings;
    }
    
    /**
     * Cập nhật trạng thái booking
     * @param bookingId Booking ID
     * @param newStatus Trạng thái mới
     * @return true nếu thành công
     */
    public boolean updateBookingStatus(Long bookingId, String newStatus) {
        String sql = "UPDATE Bookings SET status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, newStatus);
            ps.setLong(2, bookingId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi updateBookingStatus: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Hủy booking và hoàn trả số chỗ
     * @param bookingId Booking ID
     * @return true nếu thành công
     */
    public boolean cancelBooking(Long bookingId) {
        Connection conn = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Lấy thông tin booking
            Booking booking = findById(bookingId);
            if (booking == null) {
                return false;
            }
            
            // Cập nhật status thành CANCELLED
            String sqlUpdate = "UPDATE Bookings SET status = 'CANCELLED' WHERE id = ?";
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setLong(1, bookingId);
            psUpdate.executeUpdate();
            
            // Hoàn trả số chỗ
            String sqlRestore = "UPDATE TourSchedules " +
                               "SET available_seats = available_seats + ? " +
                               "WHERE id = ?";
            PreparedStatement psRestore = conn.prepareStatement(sqlRestore);
            psRestore.setInt(1, booking.getTotalParticipants());
            psRestore.setLong(2, booking.getScheduleId());
            psRestore.executeUpdate();
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Lỗi rollback: " + ex.getMessage());
                }
            }
            System.err.println("Lỗi cancelBooking: " + e.getMessage());
            return false;
            
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Lỗi đóng connection: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Đếm tổng số bookings
     * @return Số lượng bookings
     */
    public int getTotalBookings() {
        String sql = "SELECT COUNT(*) FROM Bookings";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getTotalBookings: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Tính tổng doanh thu
     * @return Tổng doanh thu
     */
    public double getTotalRevenue() {
        String sql = "SELECT SUM(total_price) FROM Bookings WHERE status IN ('CONFIRMED', 'PENDING')";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getTotalRevenue: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Chuyển đổi ResultSet thành Booking object
     */
    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setId(rs.getLong("id"));
        booking.setBookingCode(rs.getString("booking_code"));
        booking.setCustomerId(rs.getLong("customer_id"));
        booking.setTourId(rs.getLong("tour_id"));
        booking.setScheduleId(rs.getLong("schedule_id"));
        booking.setDepartureDate(rs.getDate("departure_date"));
        booking.setNumAdults(rs.getInt("num_adults"));
        booking.setNumChildren(rs.getInt("num_children"));
        booking.setTotalPrice(rs.getBigDecimal("total_price"));
        booking.setStatus(rs.getString("status"));
        booking.setSpecialRequests(rs.getString("special_requests"));
        booking.setBookingDate(rs.getTimestamp("booking_date"));
        booking.setCreatedAt(rs.getTimestamp("created_at"));
        
        // Lấy thông tin liên kết nếu có
        try {
            booking.setCustomerName(rs.getString("customer_name"));
            booking.setTourTitle(rs.getString("tour_title"));
        } catch (SQLException e) {
            // Bỏ qua nếu không có trong ResultSet
        }
        
        return booking;
    }
}