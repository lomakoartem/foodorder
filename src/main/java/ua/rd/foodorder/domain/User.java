package ua.rd.foodorder.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "user_id"))
})
public class User extends GenericEntity<Long>{

	@Column(name = "user_email")
	private String email;

	@Column(name = "user_name")
	private String name;

	@Column(name = "user_isadmin")
	private boolean isAdmin;

	@Column(name = "user_isactive")
	private boolean isActive = true;

	@Column(name = "user_upsalink")
	private String upsaLink;
	
	public String getUpsaLink() {
		return upsaLink;
	}

	public void setUpsaLink(final String upsaLink) {
		this.upsaLink = upsaLink;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(final boolean admin) {
		isAdmin = admin;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(final boolean active) {
		isActive = active;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if ((id == null) || (other.id == null)) {
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
}
