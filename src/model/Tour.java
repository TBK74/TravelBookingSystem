package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Lớp Tour đại diện cho một chương trình tour du lịch
 */
public class Tour implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String title;
    private String description;
    private Long categoryId;
    private Long locationId;
    private int durationDays;
    private int durationNights;
    private String transportation;
    private String departureLocation;
    private BigDecimal priceAdult;
    private BigDecimal priceChild;
    private int maxParticipants;
    private String imageUrl;
    private boolean isActive;
    private Timestamp createdAt;
    
    // Thông tin liên kết (sẽ được load khi cần)
    private String categoryName;
    private String locationName;

    // Constructor
    public Tour() {
    }

    // Getter và Setter
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }

    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }

    public Long getCategoryId() { 
        return categoryId; 
    }
    
    public void setCategoryId(Long categoryId) { 
        this.categoryId = categoryId; 
    }

    public Long getLocationId() { 
        return locationId; 
    }
    
    public void setLocationId(Long locationId) { 
        this.locationId = locationId; 
    }

    public int getDurationDays() { 
        return durationDays; 
    }
    
    public void setDurationDays(int durationDays) { 
        this.durationDays = durationDays; 
    }

    public int getDurationNights() { 
        return durationNights; 
    }
    
    public void setDurationNights(int durationNights) { 
        this.durationNights = durationNights; 
    }

    public String getTransportation() { 
        return transportation; 
    }
    
    public void setTransportation(String transportation) { 
        this.transportation = transportation; 
    }

    public String getDepartureLocation() { 
        return departureLocation; 
    }
    
    public void setDepartureLocation(String departureLocation) { 
        this.departureLocation = departureLocation; 
    }

    public BigDecimal getPriceAdult() { 
        return priceAdult; 
    }
    
    public void setPriceAdult(BigDecimal priceAdult) { 
        this.priceAdult = priceAdult; 
    }

    public BigDecimal getPriceChild() { 
        return priceChild; 
    }
    
    public void setPriceChild(BigDecimal priceChild) { 
        this.priceChild = priceChild; 
    }

    public int getMaxParticipants() { 
        return maxParticipants; 
    }
    
    public void setMaxParticipants(int maxParticipants) { 
        this.maxParticipants = maxParticipants; 
    }

    public String getImageUrl() { 
        return imageUrl; 
    }
    
    public void setImageUrl(String imageUrl) { 
        this.imageUrl = imageUrl; 
    }

    public boolean isActive() { 
        return isActive; 
    }
    
    public void setActive(boolean active) { 
        isActive = active; 
    }

    public Timestamp getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(Timestamp createdAt) { 
        this.createdAt = createdAt; 
    }

    public String getCategoryName() { 
        return categoryName; 
    }
    
    public void setCategoryName(String categoryName) { 
        this.categoryName = categoryName; 
    }

    public String getLocationName() { 
        return locationName; 
    }
    
    public void setLocationName(String locationName) { 
        this.locationName = locationName; 
    }

    // Phương thức tiện ích
    public String getDuration() {
        return durationDays + " ngày " + durationNights + " đêm";
    }
}
