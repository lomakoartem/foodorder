package ua.rd.foodorder.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "vendors_credentials")
public class VendorCredentials {

	@Id
    @Column(name = "vendor_id")
	private Long id;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private Vendor vendor;

	private String password;
	
	@Version
	protected Integer version = 0;
		
	public VendorCredentials() {
	}

	public VendorCredentials(Vendor vendor, String password) {
		this.id = vendor.getId();
		this.vendor = vendor;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
		
}
