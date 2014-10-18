/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.controller.service;

import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidUserException;
import org.sample.model.AddUserAccount;
import org.sample.model.dao.UserAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */

@Service
public class UserRegistrationImpl {
    
    @Autowired UserAccountDao UserAccountDao;
    
    @Transactional
    public SignupUser saveUser(SignupUser signupUser) throws InvalidUserException {
        
        AddUserAccount User = new AddUserAccount();
        User.setFirstName(signupUser.getFirstName());
        User.setLastName(signupUser.getLastName());
        User.setEmail(signupUser.getEmail());
        
        String password = signupUser.getpassword();
        String passwordRepeat = signupUser.getpasswordRepeat();
        if(password.equals(passwordRepeat)){
                    User.setpassword(signupUser.getpassword()); 
        }
        //TODO, password should not be saved readable in database. 
        //Hashfunction is needed or something similar
        //Would mean user password replaced by double, but still read in as String in signupUser
        
        User = UserAccountDao.save(User);
        
        signupUser.setId(User.getId());
        
        return signupUser;
    }
}
