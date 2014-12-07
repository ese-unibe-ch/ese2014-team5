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
 * Specialised for Enquiries
 * @author severin.zumbrunn, ramona.imhof, florentina.ziegler, ricardo.visini
 *
 */
@Transactional
public interface EnquiryService {
	
	
	/**
	 * Retrieves the current logged in user from spring Security
	 * @return the logged in user
	 */
	public User getLoggedInUser();
	
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
     * This finds the enquiries a user received
     * @param loggedInUser the user which is logged in
     * @return 
     */
	public Object findSentEnquiriesForUser(User loggedInUser);
	
    /**
     * This method finds the enquiries which are send for one advert
     * @param ad
     * @return 
     */
	public Object findEnquiriesForAd(Advert ad);

    /**
     * This method finds the notfications of type enquiry for one advert
     * @param ad
     * @return 
     */
	public List<Notifies> findNotificationsEnquiryForAd(Advert ad);

    /**
     * This method set an enquiry note as readed
     * @param id 
     */
	public void setReadEnquiryNoteForAdId(String id);

    /**
     * This method generates with the invForm the invitation on database level
     * @param invForm
     * @throws InvalidDateParseException 
     */
	public void createInvitation(InvitationForm invForm) throws InvalidDateParseException;

    /**
     * This method finds the invitations for one advert
     * @param ad
     * @return 
     */
	public List<Invitation> findInvitationsForAd(Advert ad);

    /**
     * This method deletes an invitation, it also send a note about that
     * to inform the people which were invited before.
     * @param id 
     */
	public void cancelInvitation(Long id);

    /**
     * This method allows sets the status of the invitation to accepted
     * @param id 
     */
	public void acceptInvitationForEnquiryId(Long id);

    /**
     * This method sets the status of the invitation to not accepted
     * @param id 
     */
	public void cancelInvitationForEnquiryId(Long id);

    /**
     * Is setting a rank for an enquiry with a given id.
     * @param id
     * @param rank 
     */
	public void setRatingForEnquiry(Long id, int rank);

    /**
     * Is setting all enquiry notification to status readed
     * @param userid 
     */
	public void setEnquiryNotificationsReadForUserId(Long userid);

    /**
     * Is finding all invitation which are not cancelled for one advert
     * @param ad
     * @return 
     */
	public List<Invitation> findNotCancelledInvitationsForAd(Advert ad);
	
	/**
	 * Creates a new text notification, this is used for system messages to the user
	 * @param user - the user to whom to display the message
	 * @param text - the text you want to display to the user
	 */
	public void createNotification(User user, String text);
	

}