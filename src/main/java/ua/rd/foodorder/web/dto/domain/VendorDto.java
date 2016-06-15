package ua.rd.foodorder.web.dto.domain;

import java.util.ArrayList;
import java.util.List;


public class VendorDto {
	
	public static class VendorLocations{
		private String locations;
		
		private List<Long> locationsId = new ArrayList<Long>();
				
		public VendorLocations() {
		}

		public VendorLocations(String locations, List<Long> locationsId) {
			this.locations = locations;
			this.locationsId = locationsId;
		}

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
	
	private String additionalInfo;
	
	private boolean isActive;
	
	private VendorLocations locations;


	public VendorLocations getLocations() {
		return locations;
	}

	public void setLocations(VendorLocations locations) {
		this.locations = locations;
	}

	public VendorDto() {
	}

	public VendorDto(Long id, String name, String email, String additionalInfo, boolean isActive, VendorLocations locations) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.additionalInfo = additionalInfo;
		this.isActive = isActive;
		this.locations = locations;
	}

	@Override
	public String toString() {
		return "VendorDto [id=" + id + ", name=" + name + ", email=" + email + ", additionalInfo=" + additionalInfo + ", isActive="
				+ isActive + ", locations=" + locations + "]";
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

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
	
}
