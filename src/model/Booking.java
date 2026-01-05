package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Lớp Booking đại diện cho một yêu cầu đặt tour
 */
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String bookingCode;
    private Long customerId;
    private Long tourId;
    private Long scheduleId;
    private Date departureDate;
    private int numAdults;
    private int numChildren;
    private BigDecimal totalPrice;
    private String status;  // PENDING, CONFIRMED, CANCELLED
    private String specialRequests;
    private Timestamp bookingDate;
    private Timestamp createdAt;
    
    // Thông tin liên kết
    private String customerName;
    private String tourTitle;

    // Constructor
    public Booking() {
    }

    // Getter và Setter
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getBookingCode() { 
        return bookingCode; 
    }
    
    public void setBookingCode(String bookingCode) { 
        this.bookingCode = bookingCode; 
    }

    public Long getCustomerId() { 
        return customerId; 
    }
    
    public void setCustomerId(Long customerId) { 
        this.customerId = customerId; 
    }

    public Long getTourId() { 
        return tourId; 
    }
    
    public void setTourId(Long tourId) { 
        this.tourId = tourId; 
    }

    public Long getScheduleId() { 
        return scheduleId; 
    }
    
    public void setScheduleId(Long scheduleId) { 
        this.scheduleId = scheduleId; 
    }

    public Date getDepartureDate() { 
        return departureDate; 
    }
    
    public void setDepartureDate(Date departureDate) { 
        this.departureDate = departureDate; 
    }

    public int getNumAdults() { 
        return numAdults; 
    }
    
    public void setNumAdults(int numAdults) { 
        this.numAdults = numAdults; 
    }

    public int getNumChildren() { 
        return numChildren; 
    }
    
    public void setNumChildren(int numChildren) { 
        this.numChildren = numChildren; 
    }

    public BigDecimal getTotalPrice() { 
        return totalPrice; 
    }
    
    public void setTotalPrice(BigDecimal totalPrice) { 
        this.totalPrice = totalPrice; 
    }

    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }

    public String getSpecialRequests() { 
        return specialRequests; 
    }
    
    public void setSpecialRequests(String specialRequests) { 
        this.specialRequests = specialRequests; 
    }

    public Timestamp getBookingDate() { 
        return bookingDate; 
    }
    
    public void setBookingDate(Timestamp bookingDate) { 
        this.bookingDate = bookingDate; 
    }

    public Timestamp getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(Timestamp createdAt) { 
        this.createdAt = createdAt; 
    }

    public String getCustomerName() { 
        return customerName; 
    }
    
    public void setCustomerName(String customerName) { 
        this.customerName = customerName; 
    }

    public String getTourTitle() { 
        return tourTitle; 
    }
    
    public void setTourTitle(String tourTitle) { 
        this.tourTitle = tourTitle; 
    }

    // Phương thức tiện ích
    public int getTotalParticipants() {
        return numAdults + numChildren;
    }
}