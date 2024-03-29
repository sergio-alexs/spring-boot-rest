package com.devs4j.users.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity that represents a relationship between User and Role in the database.
 *
 * @author jroldan
 * @version 1.0
 * @category Entity
 * @since 22/12/26
 * @upgrade 23/01/26
 */
@Entity
@Table(name = "users_in_roles")
public class UserInRoleEntity implements Serializable {

	private static final long serialVersionUID = -6426077101670141136L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private UserEntity user;

	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private RoleEntity role;

	public UserInRoleEntity() {
	}

	public UserInRoleEntity(UserEntity user, RoleEntity role) {
		super();
		this.user = user;
		this.role = role;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return this.user;
	}

	public void setUser(final UserEntity user) {
		this.user = user;
	}

	public RoleEntity getRole() {
		return this.role;
	}

	public void setRole(final RoleEntity role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
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
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final UserInRoleEntity other = (UserInRoleEntity) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "UserInRoleEntity [id=" + id + ", user=" + user + ", role=" + role + "]";
	}

}
