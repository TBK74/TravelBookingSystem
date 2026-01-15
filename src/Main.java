// =====================================================
// File: src/main/Main.java
// Main class để chạy và test toàn bộ chương trình
// =====================================================

package main;

import util.DatabaseConnection;
import util.PasswordUtil;
import util.ValidationUtil;
import util.BookingCodeGenerator;

import dao.UserDAO;
import dao.CustomerDAO;
import dao.TourDAO;
import dao.CategoryDAO;
import dao.LocationDAO;
import dao.BookingDAO;

import model.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Main class - Entry point của ứng dụng
 * Chạy class này để:
 * 1. Test kết nối database
 * 2. Test các DAO operations
 * 3. Khởi tạo dữ liệu mẫu
 * 4. Menu test các chức năng
 */
public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    
    // DAOs
    private static UserDAO userDAO = new UserDAO();
    private static CustomerDAO customerDAO = new CustomerDAO();
    private static TourDAO tourDAO = new TourDAO();
    private static CategoryDAO categoryDAO = new CategoryDAO();
    private static LocationDAO locationDAO = new LocationDAO();
    private static BookingDAO bookingDAO = new BookingDAO();
    
    /**
     * Main method - Entry point
     */
    public static void main(String[] args) {
        printWelcomeBanner();
        
        // Bước 1: Test kết nối database
        if (!testDatabaseConnection()) {
            System.err.println("\n❌ Không thể kết nối database. Vui lòng kiểm tra lại cấu hình!");
            System.err.println("Xem hướng dẫn trong DatabaseConnection.java");
            return;
        }
        
        // Bước 2: Test các utilities
        testUtilities();
        
        // Bước 3: Test các DAOs
        testDAOs();
        
        // Bước 4: Hiển thị menu chính
        showMainMenu();
    }
    
    /**
     * In banner chào mừng
     */
    private static void printWelcomeBanner() {
        System.out.println("\n");
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                               ║");
        System.out.println("║        HỆ THỐNG ĐẶT TOUR DU LỊCH TRỰC TUYẾN                   ║");
        System.out.println("║        TRAVEL BOOKING SYSTEM                                  ║");
        System.out.println("║                                                               ║");
        System.out.println("║        Version: 1.0                                           ║");
        System.out.println("║        Java Web Application                                   ║");
        System.out.println("║                                                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println("\n");
    }
    
    /**
     * Test kết nối database
     */
    private static boolean testDatabaseConnection() {
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("  BƯỚC 1: KIỂM TRA KẾT NỐI DATABASE");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        try {
            DatabaseConnection.testConnection();
            System.out.println("\n✅ Kết nối database THÀNH CÔNG!\n");
            return true;
        } catch (Exception e) {
            System.err.println("\n❌ Kết nối database THẤT BẠI!");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Test các utility classes
     */
    private static void testUtilities() {
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("  BƯỚC 2: TEST CÁC UTILITY CLASSES");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        // Test PasswordUtil
        System.out.println("📌 Test PasswordUtil:");
        String password = "admin123";
        String hashed = PasswordUtil.hashPassword(password);
        boolean verified = PasswordUtil.verifyPassword(password, hashed);
        System.out.println("  Password: " + password);
        System.out.println("  Hashed: " + hashed.substring(0, 20) + "...");
        System.out.println("  Verified: " + (verified ? "✅" : "❌"));
        
        // Test ValidationUtil
        System.out.println("\n📌 Test ValidationUtil:");
        String email = "test@example.com";
        String phone = "0123456789";
        System.out.println("  Email '" + email + "': " + (ValidationUtil.isValidEmail(email) ? "✅" : "❌"));
        System.out.println("  Phone '" + phone + "': " + (ValidationUtil.isValidPhone(phone) ? "✅" : "❌"));
        
        // Test BookingCodeGenerator
        System.out.println("\n📌 Test BookingCodeGenerator:");
        String bookingCode = BookingCodeGenerator.generateBookingCode();
        System.out.println("  Generated Code: " + bookingCode);
        
        System.out.println("\n✅ Tất cả Utilities hoạt động tốt!\n");
    }
    
    /**
     * Test các DAO operations
     */
    private static void testDAOs() {
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("  BƯỚC 3: TEST CÁC DAO OPERATIONS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        // Test UserDAO
        System.out.println("📌 Test UserDAO:");
        User adminUser = userDAO.findByUsername("admin");
        if (adminUser != null) {
            System.out.println("  ✅ Tìm thấy admin user: " + adminUser.getUsername());
            System.out.println("     Email: " + adminUser.getEmail());
            System.out.println("     Role: " + adminUser.getRole());
        } else {
            System.out.println("  ❌ Không tìm thấy admin user");
        }
        
        // Test CategoryDAO
        System.out.println("\n📌 Test CategoryDAO:");
        List<Category> categories = categoryDAO.getAllCategories();
        System.out.println("  ✅ Tìm thấy " + categories.size() + " categories:");
        for (Category cat : categories) {
            System.out.println("     - " + cat.getName());
        }
        
        // Test LocationDAO
        System.out.println("\n📌 Test LocationDAO:");
        List<Location> locations = locationDAO.getAllLocations();
        System.out.println("  ✅ Tìm thấy " + locations.size() + " locations:");
        for (Location loc : locations) {
            System.out.println("     - " + loc.getName() + ", " + loc.getCountry());
        }
        
        // Test TourDAO
        System.out.println("\n📌 Test TourDAO:");
        List<Tour> tours = tourDAO.getAllTours();
        System.out.println("  ✅ Tìm thấy " + tours.size() + " tours:");
        for (Tour tour : tours) {
            System.out.println("     - " + tour.getTitle() + " - " + tour.getPriceAdult() + "đ");
        }
        
        System.out.println("\n✅ Tất cả DAOs hoạt động tốt!\n");
    }
    
    /**
     * Hiển thị menu chính
     */
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n═══════════════════════════════════════════════════════════════");
            System.out.println("  MENU CHÍNH");
            System.out.println("═══════════════════════════════════════════════════════════════");
            System.out.println("  1. Xem danh sách Tours");
            System.out.println("  2. Xem chi tiết Tour");
            System.out.println("  3. Tìm kiếm Tours");
            System.out.println("  4. Tạo Booking mẫu");
            System.out.println("  5. Xem danh sách Bookings");
            System.out.println("  6. Tạo Customer mới");
            System.out.println("  7. Khởi tạo dữ liệu mẫu");
            System.out.println("  8. Thống kê hệ thống");
            System.out.println("  9. Chạy Web Server (Tomcat)");
            System.out.println("  0. Thoát");
            System.out.println("═══════════════════════════════════════════════════════════════");
            System.out.print("Chọn chức năng (0-9): ");
            
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Vui lòng nhập số từ 0-9!");
                continue;
            }
            
            switch (choice) {
                case 1:
                    viewAllTours();
                    break;
                case 2:
                    viewTourDetail();
                    break;
                case 3:
                    searchTours();
                    break;
                case 4:
                    createSampleBooking();
                    break;
                case 5:
                    viewAllBookings();
                    break;
                case 6:
                    createNewCustomer();
                    break;
                case 7:
                    initializeSampleData();
                    break;
                case 8:
                    showSystemStatistics();
                    break;
                case 9:
                    runWebServer();
                    break;
                case 0:
                    System.out.println("\n👋 Cảm ơn bạn đã sử dụng hệ thống!");
                    System.out.println("Goodbye!\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }
    
    /**
     * 1. Xem danh sách Tours
     */
    private static void viewAllTours() {
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("  DANH SÁCH TOURS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        List<Tour> tours = tourDAO.getAllTours();
        
        if (tours.isEmpty()) {
            System.out.println("❌ Chưa có tour nào trong hệ thống.");
            System.out.println("💡 Chọn chức năng 7 để khởi tạo dữ liệu mẫu.");
            return;
        }
        
        System.out.printf("%-5s %-40s %-15s %-15s %-20s\n", 
                         "ID", "Tên Tour", "Giá NL", "Giá TE", "Địa điểm");
        System.out.println("───────────────────────────────────────────────────────────────");
        
        for (Tour tour : tours) {
            System.out.printf("%-5d %-40s %,15.0fđ %,15.0fđ %-20s\n",
                             tour.getId(),
                             tour.getTitle().substring(0, Math.min(40, tour.getTitle().length())),
                             tour.getPriceAdult(),
                             tour.getPriceChild(),
                             tour.getLocationName() != null ? tour.getLocationName() : "N/A");
        }
        
        System.out.println("\n✅ Tổng số tours: " + tours.size());
    }
    
    /**
     * 2. Xem chi tiết Tour
     */
    private static void viewTourDetail() {
        System.out.print("\nNhập ID tour: ");
        try {
            Long tourId = Long.parseLong(scanner.nextLine());
            Tour tour = tourDAO.findById(tourId);
            
            if (tour == null) {
                System.out.println("❌ Không tìm thấy tour với ID: " + tourId);
                return;
            }
            
            System.out.println("\n═══════════════════════════════════════════════════════════════");
            System.out.println("  CHI TIẾT TOUR");
            System.out.println("═══════════════════════════════════════════════════════════════");
            System.out.println("ID: " + tour.getId());
            System.out.println("Tên: " + tour.getTitle());
            System.out.println("Mô tả: " + tour.getDescription());
            System.out.println("Thời gian: " + tour.getDuration());
            System.out.println("Phương tiện: " + tour.getTransportation());
            System.out.println("Điểm khởi hành: " + tour.getDepartureLocation());
            System.out.println("Giá người lớn: " + String.format("%,d", tour.getPriceAdult().longValue()) + "đ");
            System.out.println("Giá trẻ em: " + String.format("%,d", tour.getPriceChild().longValue()) + "đ");
            System.out.println("Số người tối đa: " + tour.getMaxParticipants());
            System.out.println("Danh mục: " + tour.getCategoryName());
            System.out.println("Địa điểm: " + tour.getLocationName());
            System.out.println("═══════════════════════════════════════════════════════════════");
            
            // Hiển thị lịch khởi hành
            List<TourSchedule> schedules = tourDAO.getSchedulesByTourId(tourId);
            if (!schedules.isEmpty()) {
                System.out.println("\n📅 LỊCH KHỞI HÀNH:");
                for (TourSchedule schedule : schedules) {
                    System.out.println("  - " + schedule.getDepartureDate() + 
                                     " | Còn " + schedule.getAvailableSeats() + " chỗ");
                }
            }
            
        } catch (NumberFormatException e) {
            System.out.println("❌ ID không hợp lệ!");
        }
    }
    
    /**
     * 3. Tìm kiếm Tours
     */
    private static void searchTours() {
        System.out.print("\nNhập từ khóa tìm kiếm: ");
        String keyword = scanner.nextLine();
        
        List<Tour> tours = tourDAO.searchTours(keyword);
        
        System.out.println("\n🔍 Tìm thấy " + tours.size() + " kết quả:");
        
        if (tours.isEmpty()) {
            System.out.println("❌ Không tìm thấy tour nào.");
            return;
        }
        
        for (Tour tour : tours) {
            System.out.println("\n" + tour.getId() + ". " + tour.getTitle());
            System.out.println("   Giá: " + String.format("%,d", tour.getPriceAdult().longValue()) + "đ/người");
            System.out.println("   " + tour.getDuration());
        }
    }
    
    /**
     * 4. Tạo Booking mẫu
     */
    private static void createSampleBooking() {
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("  TẠO BOOKING MẪU");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        try {
            // Kiểm tra có customer không
            List<Customer> customers = customerDAO.getAllCustomers();
            if (customers.isEmpty()) {
                System.out.println("❌ Chưa có customer nào. Tạo customer trước!");
                return;
            }
            
            // Kiểm tra có tour không
            List<Tour> tours = tourDAO.getAllTours();
            if (tours.isEmpty()) {
                System.out.println("❌ Chưa có tour nào. Khởi tạo dữ liệu mẫu trước!");
                return;
            }
            
            Customer customer = customers.get(0);
            Tour tour = tours.get(0);
            
            // Lấy schedule
            List<TourSchedule> schedules = tourDAO.getSchedulesByTourId(tour.getId());
            if (schedules.isEmpty()) {
                System.out.println("❌ Tour chưa có lịch khởi hành!");
                return;
            }
            
            TourSchedule schedule = schedules.get(0);
            
            // Tạo booking
            Booking booking = new Booking();
            booking.setCustomerId(customer.getId());
            booking.setTourId(tour.getId());
            booking.setScheduleId(schedule.getId());
            booking.setDepartureDate(schedule.getDepartureDate());
            booking.setNumAdults(2);
            booking.setNumChildren(1);
            
            BigDecimal totalPrice = tour.getPriceAdult().multiply(new BigDecimal(2))
                                       .add(tour.getPriceChild());
            booking.setTotalPrice(totalPrice);
            booking.setStatus("PENDING");
            booking.setSpecialRequests("Booking mẫu từ Main.java");
            
            boolean success = bookingDAO.createBooking(booking);
            
            if (success) {
                System.out.println("✅ Tạo booking thành công!");
                System.out.println("   Mã booking: " + booking.getBookingCode());
                System.out.println("   Customer: " + customer.getFullName());
                System.out.println("   Tour: " + tour.getTitle());
                System.out.println("   Ngày đi: " + booking.getDepartureDate());
                System.out.println("   Số người: " + booking.getNumAdults() + " NL + " + 
                                 booking.getNumChildren() + " TE");
                System.out.println("   Tổng tiền: " + String.format("%,d", totalPrice.longValue()) + "đ");
            } else {
                System.out.println("❌ Tạo booking thất bại!");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 5. Xem danh sách Bookings
     */
    private static void viewAllBookings() {
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("  DANH SÁCH BOOKINGS");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        List<Booking> bookings = bookingDAO.getAllBookings();
        
        if (bookings.isEmpty()) {
            System.out.println("❌ Chưa có booking nào.");
            return;
        }
        
        System.out.printf("%-15s %-30s %-20s %-15s %-12s\n",
                         "Mã Booking", "Tour", "Customer", "Tổng tiền", "Trạng thái");
        System.out.println("───────────────────────────────────────────────────────────────");
        
        for (Booking booking : bookings) {
            System.out.printf("%-15s %-30s %-20s %,15dđ %-12s\n",
                             booking.getBookingCode(),
                             booking.getTourTitle() != null ? 
                                 booking.getTourTitle().substring(0, Math.min(30, booking.getTourTitle().length())) : "N/A",
                             booking.getCustomerName() != null ? booking.getCustomerName() : "N/A",
                             booking.getTotalPrice().longValue(),
                             booking.getStatus());
        }
        
        System.out.println("\n✅ Tổng số bookings: " + bookings.size());
    }
    
    /**
     * 6. Tạo Customer mới
     */
    private static void createNewCustomer() {
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("  TẠO CUSTOMER MỚI");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        try {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            
            System.out.print("Password: ");
            String password = scanner.nextLine();
            
            System.out.print("Email: ");
            String email = scanner.nextLine();
            
            System.out.print("Họ tên: ");
            String fullName = scanner.nextLine();
            
            System.out.print("Số điện thoại: ");
            String phone = scanner.nextLine();
            
            System.out.print("Địa chỉ: ");
            String address = scanner.nextLine();
            
            // Validation
            if (!ValidationUtil.isValidEmail(email)) {
                System.out.println("❌ Email không hợp lệ!");
                return;
            }
            
            if (!ValidationUtil.isValidPassword(password)) {
                System.out.println("❌ Password phải có ít nhất 6 ký tự!");
                return;
            }
            
            // Check username tồn tại
            if (userDAO.findByUsername(username) != null) {
                System.out.println("❌ Username đã tồn tại!");
                return;
            }
            
            // Tạo User
            User user = new User();
            user.setUsername(username);
            user.setPassword(PasswordUtil.hashPassword(password));
            user.setRole("CUSTOMER");
            user.setEmail(email);
            user.setActive(true);
            
            if (userDAO.createUser(user)) {
                // Tạo Customer
                Customer customer = new Customer();
                customer.setUserId(user.getId());
                customer.setFullName(fullName);
                customer.setPhone(phone);
                customer.setAddress(address);
                
                if (customerDAO.createCustomer(customer)) {
                    System.out.println("\n✅ Tạo customer thành công!");
                    System.out.println("   Username: " + username);
                    System.out.println("   Email: " + email);
                    System.out.println("   Họ tên: " + fullName);
                } else {
                    System.out.println("❌ Tạo customer profile thất bại!");
                }
            } else {
                System.out.println("❌ Tạo user thất bại!");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 7. Khởi tạo dữ liệu mẫu
     */
    private static void initializeSampleData() {
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("  KHỞI TẠO DỮ LIỆU MẪU");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        System.out.println("⚠️  Chức năng này sẽ chạy script SQL travel_db.sql");
        System.out.println("    Vui lòng chạy script đó trong SQL Server Management Studio.");
        System.out.println("\n    Script location: database/travel_db.sql");
        System.out.println("\n    Script sẽ tạo:");
        System.out.println("    - Admin user (admin/admin123)");
        System.out.println("    - 4 Categories");
        System.out.println("    - 5 Locations");
        System.out.println("    - 3 Tours mẫu");
        System.out.println("    - 4 Tour Schedules");
    }
    
    /**
     * 8. Thống kê hệ thống
     */
    private static void showSystemStatistics() {
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("  THỐNG KÊ HỆ THỐNG");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        int totalTours = tourDAO.getTotalTours();
        int totalCustomers = customerDAO.getTotalCustomers();
        int totalBookings = bookingDAO.getTotalBookings();
        double totalRevenue = bookingDAO.getTotalRevenue();
        
        System.out.println("📊 Tổng quan:");
        System.out.println("   🎯 Tổng số Tours:     " + totalTours);
        System.out.println("   👥 Tổng số Customers: " + totalCustomers);
        System.out.println("   📝 Tổng số Bookings:  " + totalBookings);
        System.out.println("   💰 Tổng doanh thu:    " + String.format("%,d", (long)totalRevenue) + "đ");
        
        System.out.println("\n📈 Chi tiết:");
        System.out.println("   Categories: " + categoryDAO.getAllCategories().size());
        System.out.println("   Locations:  " + locationDAO.getAllLocations().size());
        System.out.println("   Users:      " + userDAO.getAllUsers().size());
    }
    
    /**
     * 9. Chạy Web Server
     */
    private static void runWebServer() {
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println("  CHẠY WEB SERVER");
        System.out.println("═══════════════════════════════════════════════════════════════\n");
        
        System.out.println("ℹ️  Để chạy web server:");
        System.out.println("\n1. Trong Eclipse:");
        System.out.println("   - Right-click project → Run As → Run on Server");
        System.out.println("   - Chọn Tomcat Server");
        System.out.println("   - Click Finish");
        System.out.println("\n2. Hoặc:");
        System.out.println("   - Vào Servers view");
        System.out.println("   - Right-click Tomcat → Start");
        System.out.println("\n3. Sau khi start:");
        System.out.println("   - Truy cập: http://localhost:8080/TravelBookingSystem/");
        System.out.println("\n4. Đăng nhập:");
        System.out.println("   - Admin: admin / admin123");
        System.out.println("   - Hoặc đăng ký tài khoản mới");
        
        System.out.println("\n💡 Lưu ý:");
        System.out.println("   - Đảm bảo Tomcat đã được cấu hình trong Eclipse");
        System.out.println("   - Đảm bảo database đã có dữ liệu mẫu");
        System.out.println("   - Đảm bảo port 8080 không bị chiếm dụng");
    }
}