package org.sample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserRole {
	@Id
	@GeneratedValue
	private Integer userRoleId;	
	
	private Integer role;
	
	public UserRole(){}
	
	public Integer getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
}
