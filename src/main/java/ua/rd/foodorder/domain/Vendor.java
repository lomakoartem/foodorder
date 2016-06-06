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

    @Column(name = "vendor_isActive")
    private boolean isActive;

    @Column(name = "vendor_email")
    private String email;

    @Column(name = "vendor_info")
    private String info;

 /*
    @Version
    private Integer version = 0;

    @Column(name = "vendor_phone")
    private String phone;
    @ManyToMany
    @JoinTable(name="locations_vendors",
            joinColumns=@JoinColumn(name="location_id"),
            inverseJoinColumns=@JoinColumn(name="vendor_id"))
    private List<Location> locations;
*/
    public Vendor() {
        this.isActive = true;
    }

    public Vendor(Long id, String name, String email, String info) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.info = info;
        this.isActive=true;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

   /* public String getPhone() {
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
    }*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vendor)) return false;

        Vendor vendor = (Vendor) o;

        if (isActive != vendor.isActive) return false;
        if (!id.equals(vendor.id)) return false;
        if (!name.equals(vendor.name)) return false;
        if (!email.equals(vendor.email)) return false;
        return info.equals(vendor.info);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (isActive ? 1 : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + info.hashCode();
        return result;
    }
}
