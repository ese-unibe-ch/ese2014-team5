package org.sample.controller.pojos;


/**
 * Form which retrieves the data of the bookmark from the Controller and the Jsp
 * 
 */

public class BookmarkForm 
{
	private String username;
	private String adNumber;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAdNumber() {
		return adNumber;
	}
	public void setAdNumber(String adNumber) {
		this.adNumber = adNumber;
	}
	
}