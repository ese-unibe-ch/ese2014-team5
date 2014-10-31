package org.sample.controller.service;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidSearchException;
import org.sample.model.Advert;
import org.sample.model.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SampleService {

    public SignupUser saveUser(SignupUser signupUser);
    public Long saveFromAd(AdCreateForm adForm) throws InvalidAdException;
    public Long saveFromSearch(SearchForm searchForm) throws InvalidSearchException;
    
    public Advert getAd(Long id);
    
    /*The get methods*/
    public Object findAdsForUser(User user);
	public Object findAds(SearchForm Form);
	public void updateUser(SignupUser profileUpdateForm);
	public User getLoggedInUser();
	public void removeSearch(Long value);

}
 
