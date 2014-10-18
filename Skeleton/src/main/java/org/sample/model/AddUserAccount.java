/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * This class loads the data to the database
 */
@Entity
public class AddUserAccount {

    @Id
    @GeneratedValue
    private Long id;

    
    private String FirstName;
    private String LastName;
    private String password;
    private String Email;

        public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
     
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

}
