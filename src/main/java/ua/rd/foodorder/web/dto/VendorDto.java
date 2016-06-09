package ua.rd.foodorder.web.dto;

import java.util.List;



public class VendorDto {
	
	public static class VendorLocations{
		private String locations;
		
		private List<Long> locationsId;

		public String getLocations() {
			return locations;
		}

		public void setLocations(String locations) {
			this.locations = locations;
		}

		public List<Long> getLocationsId() {
			return locationsId;
		}

		public void setLocationsId(List<Long> locationsId) {
			this.locationsId = locationsId;
		}

		@Override
		public String toString() {
			return "VendorLocations [locations=" + locations + ", locationsId=" + locationsId + "]";
		}
		
		
	}
	
	private Long id;
	
	private String name;
	
	private String email;
	
	private String phone;
	
	private boolean isActive;
	
	private VendorLocations locations;


	public VendorLocations getLocations() {
		return locations;
	}

	public void setLocations(VendorLocations locations) {
		this.locations = locations;
	}

	

	@Override
	public String toString() {
		return "VendorDto [locations=" + locations + "]";
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
	
}
