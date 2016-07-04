package ua.rd.foodorder.web.dto.domain;

public class UserDTO {

    private Long id;

    private String email;

    private String name;

    private boolean isAdmin;

    private boolean isActive;
    
    private String hiperlink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getHiperlink() {
		return hiperlink;
	}

	public void setHiperlink(String hiperlink) {
		this.hiperlink = hiperlink;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
