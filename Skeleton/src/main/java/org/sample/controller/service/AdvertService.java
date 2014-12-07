package org.sample.controller.service;

import java.util.List;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.BookmarkForm;
import org.sample.controller.pojos.InvitationForm;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidDateParseException;
import org.sample.exceptions.InvalidSearchException;
import org.sample.model.Advert;
import org.sample.model.Bookmark;
import org.sample.model.Enquiry;
import org.sample.model.Invitation;
import org.sample.model.Notifies;
import org.sample.model.User;
import org.sample.model.Enquiry.InvitationStatus;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service provides helper methods to get gathered information from the DB and to store information.
 * Specialised for Adverts
 * @author severin.zumbrunn, ramona.imhof, florentina.ziegler, ricardo.visini
 *
 */
@Transactional
public interface AdvertService {
	
	 /**
     * Stores a given ad in the database. Creates a new ad.
     * @param adForm
     * @return ID - the id of the stored object.
     * @throws InvalidAdException - Is thrown when the ad doesn't have necessary information
     */
    public Long saveFromAd(AdCreateForm adForm) throws InvalidAdException;
    
    /**
     * Updates and given add
     * @param updateForm
     * @param id 
     */    
    public void updateAd(AdCreateForm updateForm, long id);
    
    
    /**
     * Retrieves an ad from the database.
     * @param id
     * @return Advert - the advert returned from the database / Null, when there is no advert with the given id
     */
    public Advert getAd(Long id);
    
    /**
     * Retrieves all ads created by given user from the database.
     * @param user - the user to look for ads
     * @return Iterable<Adverts> - All ads created by the given user
     */
    public Object findAdsForUser(User user);
    

	/**
	 * Finds the ad and all notifications, invitations, etc. by ad id and removes them from the database
	 * @param id
	 */
	public void deleteAd(Long id);
	
	/**
	 * Finds a picture and removes it from the database
	 * @param picid
	 * @param adid
	 */
	public void deletePic(Long picid, Long adid);

}