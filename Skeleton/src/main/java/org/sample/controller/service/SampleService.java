package org.sample.controller.service;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.BookmarkForm;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidSearchException;
import org.sample.model.Advert;
import org.sample.model.Bookmark;
import org.sample.model.Enquiry;
import org.sample.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service provides helper methods to get gathered information from the DB and to store information.
 * @author severin.zumbrunn, ramona.imhof, florentina.ziegler, ricardo.visini
 *
 */
@Transactional
public interface SampleService {

	/**
	 * Stores a given user in the database. Always creates a new User.
	 * @param signupUser - the user to store in the DB (has no ID)
	 * @return SignupUser - the exact user-object stored in the DB (has an ID)
	 */
    public SignupUser saveUser(SignupUser signupUser);
    
    /**
     * Stores a given ad in the database. Creates a new ad.
     * @param adForm
     * @return ID - the id of the stored object.
     * @throws InvalidAdException - Is thrown when the ad doesn't have necessary information
     */
    public Long saveFromAd(AdCreateForm adForm) throws InvalidAdException;
    
    /**
     * Stores a given search in the database. Creates a new search.
     * @param searchForm
     * @param saveToProfile
     * @return ID - the id of the stored object
     * @throws InvalidSearchException - Is thrown when the search doesn't have necessary information.
     */
    public Long saveFromSearch(SearchForm searchForm, boolean saveToProfile) throws InvalidSearchException;
    
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
     * Retrieves all ads matching the given search
     * @param Form - the search to look for ads
     * @return Iterable<Adverts> - All ads matching the given search
     */
	public Object findAds(SearchForm Form);
	
	/**
	 * Updates a given user with new data
	 * @param profileUpdateForm
	 */
	public void updateUser(SignupUser profileUpdateForm);
	
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
	 * Bookmarks an ad specified in the bookmarkForm parameter
	 * @param bookmarkForm
	 * @return the id of the bookmark entry
	 */
	public Long bookmark(BookmarkForm bookmarkForm);
	
	/**
	 * Looks in the DB whether a given ad is bookmarked by a given user
	 * @param id - the id of the ad to check
	 * @param user - the user to check for
	 * @return - true: if a bookmark exists for this ad and user, false: if there is none
	 */
	public boolean checkBookmarked(Long id, User user);
	
	/**
	 * Retrieves a list of bookmarked ads for a given user
	 * @param user
	 * @return Iterable<Adverts> - List of bookmarked ads
	 */
	public Object findBookmarkedAdsForUser(User user);
	
	/**
	 * Removes a bookmark in the db
	 * @param adid
	 * @param username
	 */
	public void deleteBookmark(String adid, String username);
    
    /**
     * Updates and given add
     * @param updateForm
     * @param id 
     */    
    public void updateAd(AdCreateForm updateForm, long id);

        /**
         * Finds the notifications of a given user
         * @param user
         * @return 
         */
	public Object findNotificationsForUser(User user);
	
	/**
	 * Creates a new text notification, this is used for system messages to the user
	 * @param user - the user to whom to display the message
	 * @param text - the text you want to display to the user
	 */
	public void createNotification(User user, String text);
	
	/**
	 * Sets a notification as read.
	 * @param noteid
	 */
	public void setRead(String noteid);
	
	/**
	 * According to the selected search (i.e. filter) in users' profiles, this creates notifications 
	 * as soon as new (matching) ads are created.
	 * 
	 * @param id The id of the advert that causes new notifications for saved searches.
	 */
	public void createNewSearchNotifications(Long id);
	
	/**
	 * Creates an enquiry for a given ad with given text
	 * @param enquirytext
	 * @param adid
	 * @return the Enquiry object entered to the database
	 */
	public Object sendEnquiry(String enquirytext, String adid);
	
	/**
	 * Creates a new enquiry notification, to the user created the ad
	 * @param enq
	 * @return true: if notification was generated successfully
	 */
	public boolean createNotificationEnquiry(Enquiry enq);
	
	/**
	 * Checks if enquiry was already sent for given ad (id) and user
	 * @param id
	 * @param loggedInUser
	 * @return true: if enquiry was already sent, false: if no enquiry was sent yet
	 */
	public boolean checkSentEnquiry(Long id, User loggedInUser);

	/** 
	 * Retrieves all bookmarks for ad id
	 * @param id
	 * @return
	 */
	public Object findBookmarksForAd(Long id);

	/**
	 * sends notifications to users who have bookmarked the given ad
	 * @param findBookmarksForAd
	 */
	public void sendNotificationsForBookmarks(Object bookmarks);
	
        /**
         * Creates a bookmark
         * @param mark
         * @return 
         */
	public boolean createNotificationBookmark(Bookmark mark);

	public Object findSentEnquiriesForUser(User loggedInUser);
	
}
 
