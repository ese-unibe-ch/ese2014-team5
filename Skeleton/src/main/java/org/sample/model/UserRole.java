package org.sample.model;

import javax.persistence.Entity;

@Entity
public class UserRole {
	private Integer userRoleId;
	private org.sample.model.User user;
	private String role;
	
	public UserRole(){}
	
	public Integer getUserRoleId() {
		return userRoleId;
	}
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	public org.sample.model.User getUser() {
		return user;
	}
	public void setUser(org.sample.model.User user) {
		this.user = user;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
