package org.sample.controller.service;

import org.sample.controller.pojos.BookmarkForm;
import org.sample.model.Bookmark;
import org.sample.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service provides helper methods to get gathered information from the DB and to store information.
 * @author severin.zumbrunn, ramona.imhof, florentina.ziegler, ricardo.visini
 *
 */
@Transactional
public interface BookmarkService {
	
	/**
	 * Retrieves the current logged in user from spring Security
	 * @return the logged in user
	 */
	public User getLoggedInUser();
	
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

    /**
     * This method set the bookmark as readed
     * @param adid 
     */
	public void setReadBookmarkNote(String adid);

}