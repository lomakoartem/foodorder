package ua.rd.foodorder.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "locations")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "location_id"))
})
public class Location extends GenericEntity<Long> {

	@Column(name = "location_name")
	private String name;

	@Column(name = "location_address")
	private String address;

	@Column(name = "location_floor")
	private Integer floor;

	@Column(name = "location_info")
	private String info;

	@Column(name = "location_isActive")
	private boolean isActive;

	public Location() {
		this.isActive = true;
	}

	public Location(String name, String address, Integer floor, String info) {
		super();
		this.name = name;
		this.address = address;
		this.floor = floor;
		this.info = info;
		this.isActive = true;
	}
	
	public Location(Long id, String name, String address, Integer floor, String info) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.floor = floor;
		this.info = info;
		this.isActive = true;
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

	public boolean getActive() {
		return isActive;
	}

	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((floor == null) ? 0 : floor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		
		if (id == null || other.id == null) {
			
			if (address == null) {
				if (other.address != null)
					return false;
			} else if (!address.equals(other.address))
				return false;
			
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;

			if (floor == null) {
				if (other.floor != null)
					return false;
			} else if (!floor.equals(other.floor))
				return false;
			
		} else if (!id.equals(other.id))
			return false;
		
		return true;
	}
}
