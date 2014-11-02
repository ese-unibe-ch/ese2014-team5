package org.sample.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;



@Entity
public class Notification {
	@Id
	@GeneratedValue
	private Long id;
	
	public enum Type {
	    BOOKMARK, SEARCHMATCH, INTERESTED, MESSAGE
	}
	
	private Type type;
	private String text;
	private Date date;
	@OneToOne
	private User to;
	@OneToOne
	private org.sample.model.User from;
	@OneToOne
	private Bookmark bookmark;
	@OneToOne
	private Search search;
	@OneToOne
	private Advert ad;
	private boolean read;
	
	public Bookmark getBookmark() {
		return bookmark;
	}
	public void setBookmark(Bookmark bookmark) {
		this.bookmark = bookmark;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	public Advert getAd() {
		return ad;
	}
	public void setAd(Advert ad) {
		this.ad = ad;
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getFrom() {
		return from;
	}
	public void setFrom(User from) {
		this.from = from;
	}
	public User getTo() {
		return to;
	}
	public void setTo(User to) {
		this.to = to;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getTypeString() {
		String str = "";
		switch(type)
		{
			case BOOKMARK:
				str = "Bookmarked Ad has changed";
				break;
			case SEARCHMATCH:
				str = "A new Ad matching your preferences has been uploaded.";
				break;
			case INTERESTED:
				str = "New Enquiry for your Ad";
				break;
			case MESSAGE:
				str = text;
				break;
		}
		return str;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
	
}