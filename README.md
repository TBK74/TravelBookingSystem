# HỆ THỐNG ĐẶT TOUR DU LỊCH TRỰC TUYẾN
Xây dựng một ứng dụng web quản lý và đặt tour du lịch trực tuyến
MỤC TIÊU:
- Xây dựng website đặt tour du lịch trực tuyến 
- Quản lý tours, bookings, khách hàng 
- Phân quyền: Admin và Customer

## Thành viên
- Trần Bá Khôi - 23130158
- Nguyễn Tiên Sinh - 23130274

## Sử dụng
- Java Servlet + JSP
- SQL Server
- Eclipse + Tomcat 10

##TÍNH NĂNG CHÍNH
Khách Hàng:
- Xem danh sách tours
- Tìm kiếm, lọc tours
- Xem chi tiết tour
- Đăng ký tài khoản
- Đăng nhập/Đăng xuất
- Đặt tour
- Xem đơn đặt tour của mình
- Hủy đơn đặt tour

Quản Trị:
- Dashboard thống kê
- Xem danh sách bookings
- Xem danh sách tours

## Cấu trúc thư mục
README.md
TravelBookingSystem/
│
├── src/
│   ├── controller/
│   │   ├── LoginServlet.java
│   │   ├── RegisterServlet.java
│   │   ├── LogoutServlet.java
│   │   ├── HomeServlet.java
│   │   ├── TourListServlet.java
│   │   ├── TourDetailServlet.java
│   │   ├── BookingServlet.java
│   │   ├── BookingConfirmationServlet.java
│   │   ├── MyBookingsServlet.java
│   │   ├── CancelBookingServlet.java
│   │   └── admin/
│   │       ├── AdminDashboardServlet.java
│   │       └── AdminBookingsServlet.java
│   │
│   ├── model/
│   │   ├── User.java
│   │   ├── Customer.java
│   │   ├── Tour.java
│   │   ├── TourSchedule.java
│   │   ├── Booking.java
│   │   ├── Category.java
│   │   └── Location.java
│   │
│   ├── dao/
│   │   ├── UserDAO.java
│   │   ├── CustomerDAO.java
│   │   ├── TourDAO.java
│   │   ├── BookingDAO.java
│   │   ├── CategoryDAO.java
│   │   └── LocationDAO.java
│   │
│   ├── util/
│   │   ├── DatabaseConnection.java
│   │   ├── PasswordUtil.java
│   │   ├── ValidationUtil.java
│   │   └── BookingCodeGenerator.java
│   │
│   └── filter/
│       ├── CharacterEncodingFilter.java
│       ├── AuthenticationFilter.java
│       └── AdminAuthorizationFilter.java
│
├── WebContent/
│   ├── WEB-INF/
│   │   ├── web.xml
│   │   └── lib/
│   │       ├── mssql-jdbc-12.4.0.jre11.jar
│   │       ├── jakarta.servlet.jsp.jstl-3.0.1.jar
│   │       └── jakarta.servlet.jsp.jstl-api-3.0.0.jar
│   │
│   ├── views/
│   │   ├── customer/
│   │   │   ├── home.jsp
│   │   │   ├── tourList.jsp
│   │   │   ├── tourDetail.jsp
│   │   │   ├── bookingForm.jsp
│   │   │   ├── bookingConfirmation.jsp
│   │   │   └── myBookings.jsp
│   │   │
│   │   ├── auth/       
│   │   │   ├── login.jsp
│   │   │   └── register.jsp
│   │   │
│   │   ├── admin/           
│   │   │   ├── dashboard.jsp
│   │   │   └── bookings.jsp
│   │   │
│   │   ├── common/           
│   │       ├── header.jsp
│   │       └── footer.jsp
│   │
│   ├── css/
│   │   └── style.css
│   │
│   └── index.jsp
│
└── database/
    └── travel_db.sql


