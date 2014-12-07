package org.sample.controller.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.pojos.BookmarkForm;
import org.sample.controller.pojos.InvitationForm;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.pojos.SignupUser;
import org.sample.exceptions.InvalidAdException;
import org.sample.exceptions.InvalidDateParseException;
import org.sample.exceptions.InvalidSearchException;
import org.sample.exceptions.InvalidUserException;
import org.sample.model.Address;
import org.sample.model.Advert;
import org.sample.model.Bookmark;
import org.sample.model.Enquiry;
import org.sample.model.Enquiry.InvitationStatus;
import org.sample.model.Invitation;
import org.sample.model.Notifies;
import org.sample.model.Notifies.Type;
import org.sample.model.Picture;
import org.sample.model.Search;
import org.sample.model.UserData;
import org.sample.model.UserRole;
import org.sample.model.dao.AdDao;
import org.sample.model.dao.AddressDao;
import org.sample.model.dao.BookmarkDao;
import org.sample.model.dao.EnquiryDao;
import org.sample.model.dao.InvitationDao;
import org.sample.model.dao.NotifiesDao;
import org.sample.model.dao.PictureDao;
import org.sample.model.dao.SearchDao;
import org.sample.model.dao.UserDao;
import org.sample.model.dao.UserDataDao;
import org.sample.model.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author severin.zumbrunn, ramona.imhof, florentina.ziegler, ricardo.visini
 *
 *
 */
@Service
@Transactional
public class EnquiryServiceImpl implements EnquiryService {

	ServletContext servletContext;
    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    AddressDao addDao;
    @Autowired
    UserDataDao userDataDao;
    @Autowired
    AdDao adDao;
    @Autowired
    PictureDao pictureDao;
    @Autowired
    SearchDao searchDao;

    @Autowired
    BookmarkDao bookmarkDao;
    @Autowired
    NotifiesDao notifiesDao;
    @Autowired
    InvitationDao invitationDao;

    @Autowired
    EnquiryDao enquiryDao;

    @Autowired
    ServletContext context;

    EmailSender email = new EmailSender();
    

    /**
     * Retrieves the current logged in user from spring Security
     *
     * @return the logged in user
     */
    public org.sample.model.User getLoggedInUser() {
        return (org.sample.model.User) userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    
    
    /**
     * Creates an enquiry for a given ad with given text
     *
     * @param enquirytext
     * @param adid
     * @return the Enquiry object entered to the database
     */
    public Object sendEnquiry(String enquirytext, String adid) {
        Enquiry enquiry = new Enquiry();
        Advert ad = adDao.findById(Long.parseLong(adid));
        enquiry.setEnquiryText(enquirytext);
        //enquiry.setEnquiryTitle("Enquiry from User "+ this.getLoggedInUser().getUsername() );
        enquiry.setAdvert(ad);
        enquiry.setUserFrom(this.getLoggedInUser());
        enquiry.setUserTo(ad.getUser());
        return enquiryDao.save(enquiry);
    }

    /**
     * Creates a new enquiry notification, to the user created the ad
     *
     * @param enq
     * @return true: if notification was generated successfully
     */
    public boolean createNotificationEnquiry(Enquiry enq) {

        Notifies note = new Notifies();
        note.setText(enq.getEnquiryText());
        note.setToUser(enq.getUserTo());
        note.setFromUser(enq.getUserFrom());
        note.setAd(enq.getAdvert());
        note.setDate(new Date());
        note.setNotetype(Notifies.Type.ENQUIRY);
        note.setSeen(0);
        note = notifiesDao.save(note);

        try {
            email.GenerateAnEmail(note);
        } catch (MessagingException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (note != null) ? true : false;
    }

    /**
     * Checks if enquiry was already sent for given ad (id) and user
     *
     * @param id
     * @param loggedInUser
     * @return true: if enquiry was already sent, false: if no enquiry was sent
     * yet
     */
    public boolean checkSentEnquiry(Long id, org.sample.model.User loggedInUser) {

        Enquiry enq = enquiryDao.findByAdvertAndUserFrom(adDao.findById(id), loggedInUser);

        if (enq != null) {
            return true;
        }
        return false;
    }

    /**
     * This finds the enquiries a user received
     *
     * @param loggedInUser the user which is logged in
     * @return
     */
    public Object findSentEnquiriesForUser(org.sample.model.User loggedInUser) {

        return enquiryDao.findByUserFrom(loggedInUser);
    }
    
    /**
     * This method finds the enquiries which are send for one advert
     *
     * @param ad
     * @return
     */
    public Object findEnquiriesForAd(Advert ad) {

        return enquiryDao.findByAdvert(ad);
    }

    /**
     * This method finds the notfications of type enquiry for one advert
     *
     * @param ad
     * @return
     */
    public List<Notifies> findNotificationsEnquiryForAd(Advert ad) {

        return notifiesDao.findByAdAndNotetype(ad, Type.ENQUIRY);
    }

    /**
     * This method set an enquiry note as readed
     *
     * @param id
     */
    public void setReadEnquiryNoteForAdId(String id) {
        List<Notifies> notes = notifiesDao.findByAdAndNotetype(adDao.findById(Long.parseLong(id)), Type.ENQUIRY);
        for (Notifies note : notes) {
            note.setSeen(1);
            notifiesDao.save(note);
        }
    }


    /**
     * This method generates with the invForm the invitation on database level
     *
     * @param invForm
     * @throws InvalidDateParseException
     */
    public void createInvitation(InvitationForm invForm) throws InvalidDateParseException {
        Invitation inv = new Invitation();
        Advert ad = adDao.findById(invForm.getAdvertId());

        inv.setAdvert(ad);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            inv.setFromDate(formatter.parse(invForm.getFromDate()));
        } catch (ParseException e) {
            throw new InvalidDateParseException("no parse possible");
        }
        inv.setTextOfInvitation(invForm.getTextOfInvitation());
        inv.setToDate(calculateToDate(invForm));

        inv = invitationDao.save(inv);

        for (String id : invForm.getSelected_enquiries().split(",")) {

            Enquiry enq = enquiryDao.findById(Long.parseLong(id));
            enq.setStatus(InvitationStatus.UNKNOWN);
            enq.setRating(1);
            enq.setInvitation(inv);
            enq = enquiryDao.save(enq);
            createNotificationInvitation(enq, inv);
        }
    }

    /**
     * Creates an notification for an invitation, starts also the class to send
     * an email of this notification
     *
     * @param enq
     * @param inv
     * @return
     */
    public boolean createNotificationInvitation(Enquiry enq, Invitation inv) {

        Notifies note = new Notifies();
        note.setText("You are invited to visit the room " + enq.getAdvert().getTitle() + ", on " + inv.getFromDate() + "<br/><br/> " + inv.getTextOfInvitation());
        note.setToUser(enq.getUserFrom());
        note.setFromUser(enq.getUserTo());
        note.setAd(enq.getAdvert());
        note.setDate(new Date());
        note.setNotetype(Notifies.Type.INVITATION);
        note.setInvitation(inv);
        note.setSeen(0);
        note = notifiesDao.save(note);

        try {
            /*Place to directly send an email to the user as notification*/
            email.GenerateAnEmail(note);
        } catch (MessagingException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (note != null) ? true : false;
    }

    /**
     * Calculates the toDate of the invitation, generates a date of format:
     * "yyyy-MM-dd HH:mm"
     *
     * @param invForm
     * @return
     * @throws InvalidDateParseException
     */
    public Date calculateToDate(InvitationForm invForm) throws InvalidDateParseException {

        Date durationDate = null;
        Date toDate = new Date();
        Date fromDate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        try {
            durationDate = formatter.parse(invForm.getDuration());
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            fromDate = formatter.parse(invForm.getFromDate());
        } catch (ParseException e) {
            throw new InvalidDateParseException("no parse possible");
        }
        toDate.setTime(fromDate.getTime() + durationDate.getTime());
        return toDate;

    }

    /**
     * This method finds the invitations for one advert
     *
     * @param ad
     * @return
     */
    public List<Invitation> findInvitationsForAd(Advert ad) {
        return (List<Invitation>) invitationDao.findByAdvert(ad);
    }

    /**
     * Is finding all invitation which are not cancelled for one advert
     *
     * @param ad
     * @return
     */
    public List<Invitation> findNotCancelledInvitationsForAd(Advert ad) {
        List<Invitation> invs = (List<Invitation>) invitationDao.findByAdvert(ad);
        ArrayList<Invitation> notCInvs = new ArrayList<Invitation>();
        for (Invitation inv : invs) {
            if (!inv.getCancelled()) {
                notCInvs.add(inv);
            }
        }
        return notCInvs;
    }

    /**
     * This method deletes an invitation, it also send a note about that to
     * inform the people which were invited before.
     *
     * @param id
     */
    public void cancelInvitation(Long id) {
        Invitation inv = invitationDao.findById(id);
        for (Enquiry enq : enquiryDao.findByInvitation(inv)) {
            createNotification(enq.getUserFrom(), "The invitation for advert '" + enq.getAdvert().getTitle() + "' was cancelled.");
            enq.setInvitation(null);
            enq.setStatus(InvitationStatus.UNKNOWN);
            enq = enquiryDao.save(enq);
        }
        inv.setCancelled(true);
        inv = invitationDao.save(inv);
    }

    /**
     * Is setting all enquiry notification to status readed
     *
     * @param userid
     */
    public void setEnquiryNotificationsReadForUserId(Long userid) {
        List<Notifies> notes = (List<Notifies>) notifiesDao.findByToUser(userDao.findById(userid));
        for (Notifies note : notes) {
            if (note.getNotetype().equals(Notifies.Type.INVITATION)) {
                note.setSeen(1);
                note = notifiesDao.save(note);
            }
        }
    }

    /**
     * This method allows sets the status of the invitation to accepted
     *
     * @param id
     */
    public void acceptInvitationForEnquiryId(Long id) {
        Enquiry enq = enquiryDao.findById(id);
        enq.setStatus(InvitationStatus.ACCEPTED);
        enq = enquiryDao.save(enq);
        createNotificationEnquiry(enq);
    }

    /**
     * This method sets the status of the invitation to not accepted
     *
     * @param id
     */
    public void cancelInvitationForEnquiryId(Long id) {
        Enquiry enq = enquiryDao.findById(id);
        enq.setStatus(InvitationStatus.CANCELLED);
        enq = enquiryDao.save(enq);
        createNotificationEnquiry(enq);
    }

    /**
     * Is setting a rank for an enquiry with a given id.
     *
     * @param id
     * @param rank
     */
    public void setRatingForEnquiry(Long id, int rank) {
        Enquiry enq = enquiryDao.findById(id);
        enq.setRating(rank);
        enq = enquiryDao.save(enq);
    }


    /**
     * Creates a new text notification, this is used for system messages to the
     * user
     *
     * @param user - the user to whom to display the message
     * @param text - the text you want to display to the user
     */
    public void createNotification(org.sample.model.User user, String text) {

        Notifies note = new Notifies();
        note.setText(text);
        note.setToUser(user);
        note.setDate(new Date());
        note.setNotetype(Notifies.Type.MESSAGE);
        note.setSeen(0);
        notifiesDao.save(note);

        try {
            /*Place to directly send an email to the user as notification*/
            email.GenerateAnEmail(note);
        } catch (MessagingException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}