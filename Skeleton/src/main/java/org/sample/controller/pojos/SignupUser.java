package org.sample.controller.pojos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

/**
 * Form which retrieves the data of the user Registration from the Controller and the Jsp
 * 
 */

public class SignupUser {
    
    private Long id;
    private String FirstName;
    private String LastName;
    private String password;
    private String passwordRepeat;
    private String bio;
    private String hobbies;
    private int age;
    private String profession;
    private String quote;

    private MultipartFile file;
    private String filenames;
    
    public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getFilenames() {
		return filenames;
	}
	public void setFilenames(String filenames) {
		this.filenames = filenames;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordRepeat() {
		return passwordRepeat;
	}
	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
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

	
   @NotNull
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", 
    message = "Must be valid email address")
    private String Email;
   
    public String getFirstName() {
    return FirstName;
    }
    public void setFirstName(String FirstName) {
    this.FirstName = FirstName;    
    }
    
    public String getLastName() {
    return LastName;
    }
    public void setLastName(String LastName) {
    this.LastName = LastName;    
    }
    
    public String getEmail(){
    return Email;    
    }
    
    public void setEmail(String Email) {
    this.Email = Email;     
    }
    
    public String getpassword() {
    return password;    
    }
    
    public void setpassword(String password) {
    this.password = password;    
    }
    
    public String getpasswordRepeat() {
    return passwordRepeat;    
    }
    
     public void setpasswordRepeat(String passwordRepeat) {
    this.passwordRepeat = passwordRepeat;    
    }
    
      public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
