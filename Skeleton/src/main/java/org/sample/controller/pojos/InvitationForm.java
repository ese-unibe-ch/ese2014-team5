package org.sample.controller.pojos;

import java.util.Date;
import java.util.Set;

import org.sample.model.User;

public class InvitationForm 
{
	private Long advertId;
    private Long userFromId;
    private Set<User> users;
    private Date fromDate;
    private String duration;
    private String textOfInvitation;

    public Long getAdvertId() {
		return advertId;
	}
	public void setAdvertId(Long advert) {
		this.advertId = advert;
	}
	public Long getUserFromId() {
		return userFromId;
	}
	public void setUserFromId(Long userFrom) {
		this.userFromId = userFrom;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getTextOfInvitation() {
		return textOfInvitation;
	}
	public void setTextOfInvitation(String textOfInvitation) {
		this.textOfInvitation = textOfInvitation;
	}


	
	
}