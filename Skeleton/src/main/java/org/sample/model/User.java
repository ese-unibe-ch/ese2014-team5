package org.sample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 * This class loads the data to the database
 * 
 * @field selectedSearch is 0 if the user doesn't want notifications about defined search filters, 
 * otherwise the id of the {@link Search} whose parameters are used for notifications.
 */
@Entity
public class User
{

    @Id
    @GeneratedValue
    private Long id;

    
    private String firstName;
    private String lastName;
    @NotNull
    private String password;
    @NotNull
    private String email;
    private String username;
    private boolean enabled;
    private Integer selectedSearch;
    @OneToOne
	private UserRole userRole;

    
	public User(){}
	
	public UserRole getUserRole()
	{
		return this.userRole;
	}
	
	public void setUserRole(UserRole role)
	{
		this.userRole = role;
	}
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
     
    public String getFirstName() {
    return firstName;
    }
    public void setFirstName(String FirstName) {
    this.firstName = FirstName;    
    }
    
    public String getLastName() {
    return lastName;
    }
    public void setLastName(String LastName) {
    this.lastName = LastName;    
    }
    
    public String getEmail(){
    return email;    
    }
    
    public void setEmail(String Email) {
    this.email = Email;     
    }
    
    public String getPassword() {
    return password;    
    }
    
    public void setPassword(String password) {
    this.password = password;    
    }

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getSelectedSearch() {
		return selectedSearch;
	}

	public void setSelectedSearch(Integer selectedSearch) {
		this.selectedSearch = selectedSearch;
	}

}
