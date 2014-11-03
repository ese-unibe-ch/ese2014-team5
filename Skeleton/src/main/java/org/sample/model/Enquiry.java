/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package org.sample.model;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
//import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
/**
*
*/
@Entity
public class Enquiry {
	    
	@Id
	@GeneratedValue
	private Long id;
	/*Title is for inserting name of logged in person who writes*/
	
	private String enquiryTitle;

	private String enquiryText;
	
	/*At least to receiver and person who wrote it...*/
	@OneToOne
	private User userFrom;
	@OneToOne
	private User userTo;
	
	/*It is fused with one advert*/
	@OneToOne
	private Advert advert;
	
	
	public Long getId() {
	return id;
	}
	public void setId(Long id) {
	this.id = id;
	}
	public String getEnquiryTitle() {
	return enquiryTitle;
	}
	
	public void setEnquiryTitle(String enquiryTitle) {
	this.enquiryTitle = enquiryTitle;
	}
	
	public String getEnquiryText() {
	return enquiryText;
	}
	
	public void setEnquiryText(String enquiryText) {
	this.enquiryText = enquiryText;
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


}