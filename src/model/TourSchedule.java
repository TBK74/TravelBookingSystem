package model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Lớp TourSchedule đại diện cho lịch khởi hành của tour
 */
public class TourSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Long tourId;
    private Date departureDate;
    private int availableSeats;
    private String status;  // AVAILABLE, FULL, CANCELLED
    private Timestamp createdAt;

    // Constructor
    public TourSchedule() {
    }

    // Getter và Setter
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Long getTourId() { 
        return tourId; 
    }
    
    public void setTourId(Long tourId) { 
        this.tourId = tourId; 
    }
    
    public Date getDepartureDate() { 
        return departureDate; 
    }
    
    public void setDepartureDate(Date departureDate) { 
        this.departureDate = departureDate; 
    }
    
    public int getAvailableSeats() { 
        return availableSeats; 
    }
    
    public void setAvailableSeats(int availableSeats) { 
        this.availableSeats = availableSeats; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public Timestamp getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(Timestamp createdAt) { 
        this.createdAt = createdAt; 
    }
}