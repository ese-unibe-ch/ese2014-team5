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
 * Specialised for Searches
 * @author severin.zumbrunn, ramona.imhof, florentina.ziegler, ricardo.visini
 *
 */
@Transactional
public interface SearchService {

    /**
     * Stores a given search in the database. Creates a new search.
     * @param searchForm
     * @param saveToProfile
     * @return ID - the id of the stored object
     * @throws InvalidSearchException - Is thrown when the search doesn't have necessary information.
     */
    public Long saveFromSearch(SearchForm searchForm, boolean saveToProfile) throws InvalidSearchException;
    
    
    /**
     * Retrieves all ads matching the given search
     * @param Form - the search to look for ads
     * @return Iterable<Adverts> - All ads matching the given search
     */
	public Object findAds(SearchForm Form);
	
	
	/**
	 * Retrieves the current logged in user from spring Security
	 * @return the logged in user
	 */
	public User getLoggedInUser();
	
	
	/**
	 * Deletes a search with given id
	 * @param value - the id of the search to delete
	 */
	public void removeSearch(Long value);
	
	
	/**
	 * According to the selected search (i.e. filter) in users' profiles, this creates notifications 
	 * as soon as new (matching) ads are created.
	 * 
	 * @param id The id of the advert that causes new notifications for saved searches.
	 */
	public void createNewSearchNotifications(Long id);

}