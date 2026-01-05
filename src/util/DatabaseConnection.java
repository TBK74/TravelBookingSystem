package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Lớp quản lý kết nối đến Database
 * Sử dụng JDBC để kết nối SQL Server
 */
public class DatabaseConnection {
    // Thông tin kết nối database
    private static final String URL = "jdbc:sqlserver://localhost:1433;" +
                                     "databaseName=TravelBookingDB;" +
                                     "encrypt=true;" +
                                     "trustServerCertificate=true";
    private static final String USERNAME = "sa";  // Thay đổi theo SQL Server của bạn
    private static final String PASSWORD = "123"; // Thay đổi password của bạn
    
    // Static block để load JDBC driver khi class được load
    static {
        try {
            // Load SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy SQL Server JDBC Driver!");
            e.printStackTrace();
        }
    }

    /**
     * Lấy kết nối đến database
     * @return Connection object
     * @throws SQLException nếu không kết nối được
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * Đóng kết nối database
     * @param conn Connection cần đóng
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Test kết nối database
     */
    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✓ Kết nối database thành công!");
            }
        } catch (SQLException e) {
            System.err.println("✗ Lỗi kết nối database:");
            e.printStackTrace();
        }
    }
}