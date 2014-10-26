package org.sample.controller.service;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidAdException;
import org.sample.model.Advert;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SampleService {

    public SignupUser saveUser(SignupUser signupUser);
    public Long saveFromAd(AdCreateForm adForm) throws InvalidAdException;
    
    public Advert getAd(Long id);
    
    /*The get methods*/
	public Object findAds(String string);
	public void updateUser(SignupUser profileUpdateForm);
	public Object getLoggedInUser();
}
 
