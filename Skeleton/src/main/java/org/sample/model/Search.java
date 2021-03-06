package org.sample.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Search {

    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name="user_id")
    private User user;

	private String freetext;
	private String priceFrom;
	private String priceTo;
	private String sizeFrom;
	private String sizeTo;
	private String area;
	private String peopleAmount;
	private Date fromDate;
    private Date toDate;

    
    public String getFreetext() {
		return freetext;
	}

	public void setFreetext(String freetext) {
		this.freetext = freetext;
	}

	public String getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(String priceFrom) {
		this.priceFrom = priceFrom;
	}

	public String getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(String priceTo) {
		this.priceTo = priceTo;
	}

	public String getSizeFrom() {
		return sizeFrom;
	}

	public void setSizeFrom(String sizeFrom) {
		this.sizeFrom = sizeFrom;
	}

	public String getSizeTo() {
		return sizeTo;
	}

	public void setSizeTo(String sizeTo) {
		this.sizeTo = sizeTo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPeopleAmount() {
		return peopleAmount;
	}

	public void setPeopleAmount(String peopleAmount) {
		this.peopleAmount = peopleAmount;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}
