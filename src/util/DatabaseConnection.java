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
    private static final String SERVER = "DESKTOP-MKDV03B\\TBKKK";  // Hoặc: 127.0.0.1\\SQLEXPRESS
    private static final String DATABASE = "TravelBookingDB";
    private static final int PORT = 1433;
    
    // Connection String đầy đủ với tất cả options cần thiết
    private static final String URL = String.format(
        "jdbc:sqlserver://%s:%d;" +
        "databaseName=%s;" +
        "encrypt=false;" +                    // Tắt encryption cho development
        "trustServerCertificate=true;" +      // Trust certificate
        "loginTimeout=30;" +                  // Timeout 30 giây
        "integratedSecurity=false",           // Không dùng Windows Auth
        SERVER, PORT, DATABASE
    );
    
    // Thông tin đăng nhập - Dùng app_login thay vì sa
    private static final String USERNAME = "app_login";
    private static final String PASSWORD = "070405";
    
    // JDBC Driver class name
    private static final String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // Thay đổi password của bạn
    
    // Static block để load JDBC driver khi class được load
    static {
        try {
            // Load SQL Server JDBC driver
            Class.forName(DRIVER_CLASS);
            System.out.println("✓ SQL Server JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("✗ FATAL ERROR: SQL Server JDBC Driver not found!");
            System.err.println("  Hãy đảm bảo file mssql-jdbc-xx.jar đã được thêm vào project");
            System.err.println("  Location: WebContent/WEB-INF/lib/");
            e.printStackTrace();
            throw new RuntimeException("Cannot load SQL Server JDBC Driver", e);
        }
    }
    
    /**
     * Lấy kết nối đến database
     * @return Connection object
     * @throws SQLException nếu không kết nối được
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            if (conn != null) {
                System.out.println("✓ Connected to database successfully");
                System.out.println("  Database: " + DATABASE);
                System.out.println("  Server: " + SERVER);
            }
            
            return conn;
            
        } catch (SQLException e) {
            System.err.println("✗ Failed to connect to database");
            System.err.println("  Connection URL: " + URL);
            System.err.println("  Username: " + USERNAME);
            printDetailedError(e);
            throw e;
        }
    }
    
    /**
     * Đóng kết nối database
     * @param conn Connection cần đóng
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✓ Connection closed");
            } catch (SQLException e) {
                System.err.println("✗ Error closing connection");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Test kết nối database và hiển thị thông tin chi tiết
     */
    public static void testConnection() {
        System.out.println("\n========================================");
        System.out.println("TESTING DATABASE CONNECTION");
        System.out.println("========================================");
        System.out.println("Server: " + SERVER);
        System.out.println("Database: " + DATABASE);
        System.out.println("Port: " + PORT);
        System.out.println("Username: " + USERNAME);
        System.out.println("Driver: " + DRIVER_CLASS);
        System.out.println("========================================\n");
        
        Connection conn = null;
        try {
            conn = getConnection();
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("\n✓✓✓ DATABASE CONNECTION SUCCESSFUL! ✓✓✓\n");
                
                // Hiển thị metadata
                var metaData = conn.getMetaData();
                System.out.println("Database Info:");
                System.out.println("  Product Name: " + metaData.getDatabaseProductName());
                System.out.println("  Product Version: " + metaData.getDatabaseProductVersion());
                System.out.println("  Driver Name: " + metaData.getDriverName());
                System.out.println("  Driver Version: " + metaData.getDriverVersion());
                System.out.println("  URL: " + metaData.getURL());
                System.out.println("  Username: " + metaData.getUserName());
            }
            
        } catch (SQLException e) {
            System.err.println("\n✗✗✗ DATABASE CONNECTION FAILED! ✗✗✗\n");
            printDetailedError(e);
            printTroubleshootingGuide();
            
        } finally {
            closeConnection(conn);
        }
    }
    
    /**
     * In ra thông tin lỗi chi tiết
     */
    private static void printDetailedError(SQLException e) {
        System.err.println("Lỗi kết nối database:");
        System.err.println("  Message: " + e.getMessage());
        System.err.println("  SQL State: " + e.getSQLState());
        System.err.println("  Error Code: " + e.getErrorCode());
    }
    
    /**
     * Hướng dẫn khắc phục lỗi
     */
    private static void printTroubleshootingGuide() {
        System.err.println("\nHướng dẫn khắc phục chung:");
        System.err.println("  1. Đảm bảo SQL Server đang chạy");
        System.err.println("     - Mở Services (services.msc)");
        System.err.println("     - Tìm 'SQL Server (SQLEXPRESS)' hoặc 'SQL Server (MSSQLSERVER)'");
        System.err.println("     - Status phải là 'Running'");
        System.err.println();
        System.err.println("  2. Enable TCP/IP Protocol:");
        System.err.println("     - Mở SQL Server Configuration Manager");
        System.err.println("     - SQL Server Network Configuration → Protocols for [Instance]");
        System.err.println("     - Enable TCP/IP");
        System.err.println("     - Restart SQL Server service");
        System.err.println();
        System.err.println("  3. Kiểm tra SQL Server Browser service đang chạy (cần cho named instance)");
        System.err.println();
        System.err.println("  4. Kiểm tra SQL Server đã được cấu hình để chấp nhận Windows Authentication");
        System.err.println();
        System.err.println("  5. Kiểm tra Firewall:");
        System.err.println("     - Allow port 1433 (SQL Server)");
        System.err.println("     - Allow port 1434 (SQL Browser)");
        System.err.println();
        System.err.println("  6. Kiểm tra login app_login đã tồn tại:");
        System.err.println("     - Mở SSMS");
        System.err.println("     - Security → Logins → app_login");
        System.err.println("     - Nếu chưa có, chạy:");
        System.err.println("       CREATE LOGIN app_login WITH PASSWORD = '070405';");
        System.err.println("       USE TravelBookingDB;");
        System.err.println("       CREATE USER app_user FOR LOGIN app_login;");
        System.err.println("       ALTER ROLE db_datareader ADD MEMBER app_user;");
        System.err.println("       ALTER ROLE db_datawriter ADD MEMBER app_user;");
        System.err.println();
    }
}