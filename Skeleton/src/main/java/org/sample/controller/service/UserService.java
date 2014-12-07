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
 * @author severin.zumbrunn, ramona.imhof, florentina.ziegler, ricardo.visini
 *
 */
@Transactional
public interface UserService {

	/**
	 * Stores a given user in the database. Always creates a new User.
	 * @param signupUser - the user to store in the DB (has no ID)
	 * @return SignupUser - the exact user-object stored in the DB (has an ID)
	 */
    public SignupUser saveUser(SignupUser signupUser);
	
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
     * Finds the notifications of a given user
     * @param user
     * @return 
     */
	public Object findNotificationsForUser(User user);

	/**
	 * Sets a notification as read.
	 * @param noteid
	 */
	public void setRead(String noteid);

}
 
