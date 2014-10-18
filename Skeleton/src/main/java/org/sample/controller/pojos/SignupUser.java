/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.controller.pojos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * 
 */
public class SignupUser {
    
    private Long id;
    private String FirstName;
    private String LastName;
    private String password;
    private String passwordRepeat;

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
