package org.sample.model;

import java.awt.Point;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Ad {

    @Id
    @GeneratedValue
    private Long id;

    private String roomDescription;
    private String peopleDescription;
    private String title;
    private int size;
    private Point coods;
    private Date from;
    private Date to;
    
    @OneToMany
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

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public String getPeopleDescription() {
		return peopleDescription;
	}

	public void setPeopleDescription(String peopleDescription) {
		this.peopleDescription = peopleDescription;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Point getCoods() {
		return coods;
	}

	public void setCoods(Point coods) {
		this.coods = coods;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}
	
	public void addPicture(Picture picture) {
		
		this.pictures.add(picture);
	}

    
	
	
}
