package org.sample.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Advert {

    @Id
    @GeneratedValue
    private Long id;

    private String roomDesc;
    private String peopleDesc;
    private String title;
    private int roomSize;
    private Point coods;
    private Date fromDate;
    private Date toDate;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Set<Picture> pictures;
    
    @OneToOne
    private Address address; 
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRoomSize() {
		return roomSize;
	}

	public void setRoomSize(int size) {
		this.roomSize = size;
	}

	public Point getCoods() {
		return coods;
	}

	public void setCoods(Point coods) {
		this.coods = coods;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date from) {
		this.fromDate = from;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date to) {
		this.toDate = to;
	}

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}
	
	public void addPicture(Picture picture) {
		
		if(pictures==null)
		{
			pictures = new HashSet<Picture>();
		}
		this.pictures.add(picture);
	}

	public String getRoomDesc() {
		return roomDesc;
	}

	public void setRoomDesc(String roomDesc) {
		this.roomDesc = roomDesc;
	}

	public String getPeopleDesc() {
		return peopleDesc;
	}

	public void setPeopleDesc(String peopleDesc) {
		this.peopleDesc = peopleDesc;
	}

    
	
	
}
