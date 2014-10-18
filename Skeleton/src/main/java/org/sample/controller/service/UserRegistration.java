/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.controller.service;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidUserException;


public interface UserRegistration {
    
    public SignupUser saveUser(SignupUser signupUser) throws InvalidUserException;

}
