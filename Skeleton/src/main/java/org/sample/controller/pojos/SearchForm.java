package org.sample.controller.pojos;

import java.util.Date;


/**
 * Form which retrieves the data of the Search parameter from the Controller and the Jsp
 * 
 */

public class SearchForm {


    private String search;
    private String fromSize;
    private String toSize;
    private String fromPrice;
    private String toPrice;
    private String nearCity;
    private boolean favorites;
    private Long userId;
    
    /*Part for extended search*/
    private String fromDate;
    private String toDate;
    private String numberOfPeople;

    
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getFromSize() {
		return fromSize;
	}
	public void setFromSize(String fromSize) {
		this.fromSize = fromSize;
	}
	public String getToSize() {
		return toSize;
	}
	public void setToSize(String toSize) {
		this.toSize = toSize;
	}
	public String getFromPrice() {
		return fromPrice;
	}
	public void setFromPrice(String fromPrice) {
		this.fromPrice = fromPrice;
	}
	public String getToPrice() {
		return toPrice;
	}
	public void setToPrice(String toPrice) {
		this.toPrice = toPrice;
	}
	public String getNearCity() {
		return nearCity;
	}
	public void setNearCity(String nearCity) {
		this.nearCity = nearCity;
	}
        
        public String getNumberOfPeople() {
		return numberOfPeople;
	}
	public void setNumberOfPeople(String numberOfPeople) {
		this.numberOfPeople =numberOfPeople;
	}
	public boolean isFavorites() {
		return favorites;
	}
	public void setFavorites(boolean favorites) {
		this.favorites = favorites;
	}
        
        public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
        
        public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
   
}
