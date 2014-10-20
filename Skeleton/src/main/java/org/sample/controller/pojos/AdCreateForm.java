package org.sample.controller.pojos;

import java.awt.Point;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class AdCreateForm {


    //private Long id;
    private String roomDescription;
    private String peopleDescription;
    private String title;
    private String size;
    private Point coods;
    private String from;
    private String to;
    
    private String street;
    private String city;
    private String plz;
    
    private List<MultipartFile> files;
    private List<String> filenames;
    
    
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
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Point getCoods() {
		return coods;
	}
	public void setCoods(Point coods) {
		this.coods = coods;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
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
		this.filenames.add(file);
	}
   
}
