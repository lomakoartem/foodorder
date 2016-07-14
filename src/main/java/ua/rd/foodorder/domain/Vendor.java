package ua.rd.foodorder.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vendors")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "vendor_id"))
})
public class Vendor extends GenericEntity<Long> {

    @Column(name = "vendor_name")
    private String name;

    @Column(name = "vendor_additional_info")
    private String additionalInfo;

    @Column(name = "vendor_email")
    private String email;

    @Column(name = "vendor_isActive")
    private boolean isActive;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH } )
    @JoinTable(name="locations_vendors",
            joinColumns=@JoinColumn(name="vendor_id"),
            inverseJoinColumns=@JoinColumn(name="location_id"))
    private List<Location> locations;

    public Vendor() {
        this.isActive = true;
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


    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
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
        if (additionalInfo != null ? !additionalInfo.equals(vendor.additionalInfo) : vendor.additionalInfo != null) return false;
        return !(email != null ? !email.equals(vendor.email) : vendor.email != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (additionalInfo != null ? additionalInfo.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }
}