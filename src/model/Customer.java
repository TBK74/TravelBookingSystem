package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Lớp Customer đại diện cho thông tin khách hàng
 */
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Long userId;
    private String fullName;
    private String phone;
    private String address;
    private Timestamp createdAt;

    // Constructor
    public Customer() {
    }

    public Customer(String fullName, String phone, String address) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

    // Getter và Setter
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public Long getUserId() { 
        return userId; 
    }
    
    public void setUserId(Long userId) { 
        this.userId = userId; 
    }

    public String getFullName() { 
        return fullName; 
    }
    
    public void setFullName(String fullName) { 
        this.fullName = fullName; 
    }

    public String getPhone() { 
        return phone; 
    }
    
    public void setPhone(String phone) { 
        this.phone = phone; 
    }

    public String getAddress() { 
        return address; 
    }
    
    public void setAddress(String address) { 
        this.address = address; 
    }

    public Timestamp getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(Timestamp createdAt) { 
        this.createdAt = createdAt; 
    }
}
