package org.sample.controller.pojos;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class AdCreateForm {


    //private Long id;
    private String roomDesc;
    private String peopleDesc;
    private String title;
    private String roomSize;
    private Point coods;
    private String fromDate;
    private String toDate;
    
    private String street;
    private String city;
    private String plz;
    
    private List<MultipartFile> files;
    private List<String> filenames;
    
    
	public String getRoomDesc() {
		return roomDesc;
	}
	public void setRoomDesc(String roomDescription) {
		this.roomDesc = roomDescription;
	}
	public String getPeopleDesc() {
		return peopleDesc;
	}
	public void setPeopleDesc(String peopleDescription) {
		this.peopleDesc = peopleDescription;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRoomSize() {
		return roomSize;
	}
	public void setRoomSize(String size) {
		this.roomSize = size;
	}
	public Point getCoods() {
		return coods;
	}
	public void setCoods(Point coods) {
		this.coods = coods;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String from) {
		this.fromDate = from;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String to) {
		this.toDate = to;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	public List<String> getFilenames() {
		return filenames;
	}
	public void setFilenames(List<String> filenames) {
		this.filenames = filenames;
	}

	public void addFile(String file)
	{
		if(filenames==null)
		{
			filenames = new ArrayList<String>();
		}
		this.filenames.add(file);
	}
   
}
