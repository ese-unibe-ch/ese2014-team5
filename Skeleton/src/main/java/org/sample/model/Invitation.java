/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.sample.model.Notifies.Type;

/**
 *
 * @author Anubis
 */
@Entity
public class Invitation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Advert advert;

    @OneToOne
    private User userFrom;
    
    @OneToOne
    private User userTo;
    
    private Long groupId;
    private int rating;

    private Date fromDate;
    private Date toDate;

    public enum InvitationStatus {UNKNOWN,ACCEPTED,CANCELLED}

    @Enumerated(EnumType.ORDINAL)
	private InvitationStatus status;
    
    private String textOfInvitation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }
    
    public User getUserTo() {
		return userTo;
	}

	public void setUserTo(User userTo) {
		this.userTo = userTo;
	}

	public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public InvitationStatus getStatus() {
		return status;
	}

	public void setStatus(InvitationStatus status) {
		this.status = status;
	}

	public String getTextOfInvitation() {
        return textOfInvitation;
    }

    public void setTextOfInvitation(String textOfInvitation) {
        this.textOfInvitation = textOfInvitation;
    }

}
