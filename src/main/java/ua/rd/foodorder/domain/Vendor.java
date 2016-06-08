package ua.rd.foodorder.domain;


import javax.persistence.*;
import java.util.List;

/**
 * Created by Artem on 06.06.2016.
 */
@Entity
@Table(name = "vendors")
public class Vendor {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long id;


    @Column(name = "vendor_name")
    private String name;

    @Column(name = "vendor_phone")
    private String phone;

    @Column(name = "vendor_email")
    private String email;

    @Column(name = "vendor_isActive")
    private boolean isActive;

    @Version
    private Integer version = 0;


    @ManyToMany
    @JoinTable(name="locations_vendors",
            joinColumns=@JoinColumn(name="location_id"),
            inverseJoinColumns=@JoinColumn(name="vendor_id"))
    private List<Location> locations;



    public Vendor() {
        this.isActive = true;
    }

    public Vendor(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isActive = true;
    }

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
   

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendor)) return false;

        Vendor vendor = (Vendor) o;

        if (isActive != vendor.isActive) return false;
        if (id != null ? !id.equals(vendor.id) : vendor.id != null) return false;
        if (name != null ? !name.equals(vendor.name) : vendor.name != null) return false;
        if (phone != null ? !phone.equals(vendor.phone) : vendor.phone != null) return false;
        return !(email != null ? !email.equals(vendor.email) : vendor.email != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }
}