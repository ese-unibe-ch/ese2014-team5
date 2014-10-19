package org.sample.controller.service;

import org.sample.controller.pojos.SignupForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidUserException;
import org.sample.model.Ad;
import org.sample.controller.pojos.AdCreationForm;

public interface SampleService {

    public SignupForm saveFrom(SignupForm signupForm) throws InvalidUserException;
    public SignupUser saveUser(SignupUser signupUser);
    public Long saveFromAd(AdCreationForm adForm) throws InvalidAdException;
    
    public Ad getAd(Long id);
}
 
