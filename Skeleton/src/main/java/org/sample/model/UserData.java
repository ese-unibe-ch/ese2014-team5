package org.sample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
public class UserData
{

    @Id
    @GeneratedValue
    private Long id;

    
    @Size(max = 800)
    private String bio;
    private String hobbies;
    @OneToOne
    private Picture picture;
    private int age;
    private String profession;
    private String quote;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	public Picture getPicture() {
		return picture;
	}
	public void setPicture(Picture picture) {
		this.picture = picture;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
    
    
    
}