package model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Lớp User đại diện cho tài khoản đăng nhập
 * Implements Serializable để lưu trong Session
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Các thuộc tính
    private Long id;
    private String username;
    private String password;
    private String role;        // ADMIN hoặc CUSTOMER
    private String email;
    private Timestamp createdAt;
    private boolean isActive;

    // Constructor mặc định
    public User() {
    }

    // Constructor có tham số
    public User(String username, String password, String role, String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.isActive = true;
    }

    // Getter và Setter
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getUsername() { 
        return username; 
    }
    
    public void setUsername(String username) { 
        this.username = username; 
    }

    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getRole() { 
        return role; 
    }
    
    public void setRole(String role) { 
        this.role = role; 
    }

    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }

    public Timestamp getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(Timestamp createdAt) { 
        this.createdAt = createdAt; 
    }

    public boolean isActive() { 
        return isActive; 
    }
    
    public void setActive(boolean active) { 
        isActive = active; 
    }

    // Phương thức tiện ích - Kiểm tra role
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role);
    }

    public boolean isCustomer() {
        return "CUSTOMER".equalsIgnoreCase(this.role);
    }
}