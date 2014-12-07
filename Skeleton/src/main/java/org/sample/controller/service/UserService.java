package org.sample.controller.service;

import org.sample.controller.pojos.SignupUser;
import org.sample.model.User;
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
 
