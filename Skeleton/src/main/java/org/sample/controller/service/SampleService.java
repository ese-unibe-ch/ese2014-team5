package org.sample.controller.service;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidAdException;
import org.sample.model.Advert;

public interface SampleService {

    public SignupUser saveUser(SignupUser signupUser);
    public Long saveFromAd(AdCreateForm adForm) throws InvalidAdException;
    
    public Advert getAd(Long id);
    
    /*The get methods*/
    public Object getUser(Long id);
    public Object getUserByFirstNameAndLastName(String fname, String lname);
	public Object findAds(String string);
}
 
