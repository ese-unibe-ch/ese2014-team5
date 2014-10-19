package org.sample.controller.service;

import org.sample.controller.pojos.SignupForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidUserException;
import org.sample.controller.pojos.AdcreationForm;

public interface SampleService {

    public SignupForm saveFrom(SignupForm signupForm) throws InvalidUserException;
    

    public SignupUser saveUser(SignupUser signupUser);
    public AdcreationForm saveFrom(AdcreationForm adForm) throws InvalidAdException;
}
 
