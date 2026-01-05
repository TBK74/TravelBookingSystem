CREATE DATABASE TravelBookingDB;
GO

USE TravelBookingDB;
GO

-- BẢNG USERS - Quản lý tài khoản đăng nhập
CREATE TABLE Users (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    username NVARCHAR(50) UNIQUE NOT NULL,
    password NVARCHAR(255) NOT NULL,          -- Mật khẩu đã mã hóa SHA-256
    role NVARCHAR(20) NOT NULL,               -- ADMIN hoặc CUSTOMER
    email NVARCHAR(100) UNIQUE NOT NULL,
    created_at DATETIME DEFAULT GETDATE(),
    is_active BIT DEFAULT 1                   -- 1: active, 0: inactive
);

-- BẢNG CUSTOMERS - Thông tin chi tiết khách hàng
CREATE TABLE Customers (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    user_id BIGINT UNIQUE,                    -- Liên kết với Users
    full_name NVARCHAR(100) NOT NULL,
    phone NVARCHAR(20),
    address NVARCHAR(255),
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- BẢNG CATEGORIES - Danh mục tour
CREATE TABLE Categories (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(500),
    is_active BIT DEFAULT 1
);

-- BẢNG LOCATIONS - Địa điểm du lịch
CREATE TABLE Locations (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL,
    country NVARCHAR(100) NOT NULL,
    description NVARCHAR(500)
);

-- BẢNG TOURS - Thông tin tour du lịch
CREATE TABLE Tours (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    title NVARCHAR(200) NOT NULL,
    description NVARCHAR(MAX),
    category_id BIGINT,
    location_id BIGINT,
    duration_days INT NOT NULL,               -- Số ngày
    duration_nights INT NOT NULL,             -- Số đêm
    transportation NVARCHAR(100),             -- Phương tiện: Máy bay, Xe khách...
    departure_location NVARCHAR(200),         -- Nơi khởi hành
    price_adult DECIMAL(18,2) NOT NULL,       -- Giá người lớn
    price_child DECIMAL(18,2) NOT NULL,       -- Giá trẻ em
    max_participants INT DEFAULT 30,          -- Số người tối đa
    image_url NVARCHAR(255),                  -- Link hình ảnh
    is_active BIT DEFAULT 1,                  -- Tour còn hoạt động không
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (category_id) REFERENCES Categories(id),
    FOREIGN KEY (location_id) REFERENCES Locations(id)
);

-- BẢNG TOUR_SCHEDULES - Lịch khởi hành của tour
CREATE TABLE TourSchedules (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    tour_id BIGINT NOT NULL,
    departure_date DATE NOT NULL,             -- Ngày khởi hành
    available_seats INT NOT NULL,             -- Số chỗ còn trống
    status NVARCHAR(20) DEFAULT 'AVAILABLE',  -- AVAILABLE, FULL, CANCELLED
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (tour_id) REFERENCES Tours(id) ON DELETE CASCADE
);

-- BẢNG BOOKINGS - Đơn đặt tour
CREATE TABLE Bookings (
    id BIGINT PRIMARY KEY IDENTITY(1,1),
    booking_code NVARCHAR(20) UNIQUE NOT NULL,-- Mã đặt tour
    customer_id BIGINT NOT NULL,
    tour_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    departure_date DATE NOT NULL,             -- Ngày khởi hành đã chọn
    num_adults INT NOT NULL DEFAULT 1,        -- Số người lớn
    num_children INT NOT NULL DEFAULT 0,      -- Số trẻ em
    total_price DECIMAL(18,2) NOT NULL,       -- Tổng tiền
    status NVARCHAR(20) DEFAULT 'PENDING',    -- PENDING, CONFIRMED, CANCELLED
    special_requests NVARCHAR(MAX),           -- Yêu cầu đặc biệt
    booking_date DATETIME DEFAULT GETDATE(),  -- Ngày đặt
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (customer_id) REFERENCES Customers(id),
    FOREIGN KEY (tour_id) REFERENCES Tours(id),
    FOREIGN KEY (schedule_id) REFERENCES TourSchedules(id)
);

-- TẠO INDEX ĐỂ TỐI ƯU TRUY VẤN
CREATE INDEX idx_tours_category ON Tours(category_id);
CREATE INDEX idx_tours_location ON Tours(location_id);
CREATE INDEX idx_bookings_customer ON Bookings(customer_id);
CREATE INDEX idx_bookings_tour ON Bookings(tour_id);

-- THÊM DỮ LIỆU MẪU

-- Tài khoản admin (password: admin123 đã hash SHA-256)
INSERT INTO Users (username, password, role, email) VALUES 
('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN', 'admin@travel.com');

-- Danh mục tour
INSERT INTO Categories (name, description) VALUES 
(N'Du lịch biển', N'Các tour du lịch biển đảo'),
(N'Du lịch núi', N'Các tour leo núi, trekking'),
(N'Du lịch văn hóa', N'Khám phá di sản văn hóa'),
(N'Du lịch sinh thái', N'Tour gần gũi thiên nhiên');

-- Địa điểm
INSERT INTO Locations (name, country, description) VALUES 
(N'Phú Quốc', N'Việt Nam', N'Đảo ngọc Phú Quốc'),
(N'Đà Lạt', N'Việt Nam', N'Thành phố ngàn hoa'),
(N'Hội An', N'Việt Nam', N'Phố cổ Hội An'),
(N'Hạ Long', N'Việt Nam', N'Vịnh Hạ Long'),
(N'Nha Trang', N'Việt Nam', N'Biển Nha Trang');

-- Tour mẫu
INSERT INTO Tours (title, description, category_id, location_id, duration_days, duration_nights, 
                   transportation, departure_location, price_adult, price_child, max_participants, is_active) 
VALUES 
(N'Tour Phú Quốc 3N2Đ', 
 N'Khám phá đảo ngọc Phú Quốc với bãi biển tuyệt đẹp', 
 1, 1, 3, 2, N'Máy bay', N'TP.HCM', 5000000, 3500000, 30, 1),
 
(N'Tour Đà Lạt 2N1Đ', 
 N'Thưởng thức khí hậu mát mẻ tại thành phố ngàn hoa', 
 2, 2, 2, 1, N'Xe khách', N'TP.HCM', 2000000, 1500000, 40, 1),
 
(N'Tour Hội An - Đà Nẵng 3N2Đ', 
 N'Khám phá phố cổ Hội An và thành phố Đà Nẵng', 
 3, 3, 3, 2, N'Máy bay', N'Hà Nội', 4500000, 3000000, 35, 1);

-- Lịch khởi hành
INSERT INTO TourSchedules (tour_id, departure_date, available_seats, status) VALUES
(1, '2025-01-15', 30, 'AVAILABLE'),
(1, '2025-01-22', 30, 'AVAILABLE'),
(2, '2025-01-10', 40, 'AVAILABLE'),
(3, '2025-01-20', 35, 'AVAILABLE');

GO
