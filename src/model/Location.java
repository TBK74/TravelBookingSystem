package model;

import java.io.Serializable;

/**
 * Lớp Location đại diện cho địa điểm du lịch
 */
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String name;
    private String country;
    private String description;

    // Constructor
    public Location() {
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
    
    public String getCountry() { 
        return country; 
    }
    
    public void setCountry(String country) { 
        this.country = country; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
}
