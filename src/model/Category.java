package model;

import java.io.Serializable;

/**
 * Lớp Category đại diện cho danh mục tour
 */
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String name;
    private String description;
    private boolean isActive;

    // Constructor
    public Category() {
    }
    
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.isActive = true;
    }

    // Getter và Setter
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
    
    public boolean isActive() { 
        return isActive; 
    }
    
    public void setActive(boolean active) { 
        isActive = active; 
    }
}