package ua.rd.foodorder.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "locations")
public class Location {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_id")
	private Integer id;
	
	
	@Column(name = "location_name")
	private String name;
	
	@Column(name = "location_address")
	private String address;
	
	@Column(name = "location_info")
	private String info;
	
	@Column(name = "location_isActive")
	private boolean isActive;
	
	@Version
	private Integer version;
	
	public Location(){
		this.isActive = true;
	}
	
	
	
	public Location(String name, String address, String info) {
		super();
		this.name = name;
		this.address = address;
		this.info = info;
		this.isActive = true;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
}
