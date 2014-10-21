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
public class User {

    @Id
    @GeneratedValue
    private Long id;

    
    private String firstName;
    private String lastName;
    private String password;
    private String email;

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
    
    public String getpassword() {
    return password;    
    }
    
    public void setpassword(String password) {
    this.password = password;    
    }

}
