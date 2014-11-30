package org.sample.controller.pojos;

import java.util.Date;
import java.util.Set;

import org.sample.model.Enquiry;
import org.sample.model.User;

public class InvitationForm 
{
	private Long advertId;
    private Long userFromId;
    private String selected_enquiries;
    private String fromDate;
    public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
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
	
	public String getSelected_enquiries() {
		return selected_enquiries;
	}
	public void setSelected_enquiries(String selected_enquiries) {
		this.selected_enquiries = selected_enquiries;
	}
	
	public String getFromDate() {
		return fromDate;
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