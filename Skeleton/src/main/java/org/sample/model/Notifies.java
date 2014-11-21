package org.sample.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;



@Entity
public class Notifies {
	@Id
	@GeneratedValue
	private Long id;
	//private boolean read;
	@Column(name = "seen")
	private Integer seen;
	
	public enum Type {
		BOOKMARK, MESSAGE, ENQUIRY, SEARCHMATCH, INVITATION
	}
	
	@Enumerated(EnumType.ORDINAL)
	private Type notetype;
	


	private String text;
	private Date date;
	@OneToOne
	private org.sample.model.User toUser;
	@OneToOne
	private org.sample.model.User fromUser;
	@OneToOne
	private Bookmark bookmark;
	@OneToOne
	private Search search;
	@OneToOne
	private Advert ad;
	@OneToOne
	private Invitation invitation;
	
	
	
	public Invitation getInvitation() {
		return invitation;
	}
	public void setInvitation(Invitation invitation) {
		this.invitation = invitation;
	}
	public Integer getSeen() {
		return seen;
	}
	public void setSeen(Integer seen) {
		this.seen = seen;
	}
	
	
	public Type getNotetype() {
		return notetype;
	}
	public void setNotetype(Type notetype) {
		this.notetype = notetype;
	}
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
	

	
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public User getToUser() {
		return toUser;
	}
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	/*public boolean getRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
	}
*/
	
}