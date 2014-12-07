package org.sample.controller.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;

import org.sample.controller.pojos.BookmarkForm;
import org.sample.model.Advert;
import org.sample.model.Bookmark;
import org.sample.model.Notifies;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author severin.zumbrunn, ramona.imhof, florentina.ziegler, ricardo.visini
 *
 *
 */
@Service
@Transactional
public class BookmarkServiceImpl implements BookmarkService {
    
    @Autowired
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
    * Bookmarks an ad specified in the bookmarkForm parameter
     *
     * @param bookmarkForm
     * @return the id of the bookmark entry
     */
    public Long bookmark(BookmarkForm bookmarkForm) {

        Bookmark bookmark = new Bookmark();
        bookmark.setAd(adDao.findById((long) Integer.parseInt(bookmarkForm.getAdNumber())));
        bookmark.setUser(userDao.findByUsername(bookmarkForm.getUsername()));

        return bookmarkDao.save(bookmark).getId();
    }

    public boolean checkBookmarked(Long id, org.sample.model.User user) {

        return (bookmarkDao.findByAdAndUser(adDao.findById(id), user) != null) ? true : false;
    }

    /**
     * Retrieves a list of bookmarked ads for a given user
     *
     * @param user
     * @return Iterable<Adverts> - List of bookmarked ads
     */
    public Object findBookmarkedAdsForUser(org.sample.model.User user) {

        Iterable<Bookmark> bookmarks = bookmarkDao.findByUser(user);
        ArrayList<Advert> ads = new ArrayList<Advert>();
        for (Bookmark bm : bookmarks) {
            ads.add(bm.getAd());
        }
        return ads;
    }

    /**
     * Removes a bookmark in the db
     *
     * @param adid
     * @param username
     */
    public void deleteBookmark(String adid, String username) {
        bookmarkDao.delete(bookmarkDao.findByAdAndUser(adDao.findById(Long.parseLong(adid)), userDao.findByUsername(username)));

    }


    /**
     * Creates a bookmark
     *
     * @param mark
     * @return
     */
    public boolean createNotificationBookmark(Bookmark mark) {

        Notifies note = new Notifies();
        note.setToUser(mark.getUser());
        note.setFromUser(null);
        note.setAd(mark.getAd());
        note.setBookmark(mark);
        note.setDate(new Date());
        note.setNotetype(Notifies.Type.BOOKMARK);
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
     * Retrieves all bookmarks for ad id
     *
     * @param id
     * @return
     */
    public Object findBookmarksForAd(Long id) {

        Iterable<Bookmark> bookmarks = bookmarkDao.findByAd(adDao.findById(id));
        Iterator<Bookmark> it = bookmarks.iterator();
        ArrayList<Bookmark> marks = new ArrayList<Bookmark>();
        if (!it.hasNext()) {
            return null;
        } else {
            while (it.hasNext()) {
                marks.add((Bookmark) it.next());
            }
            return marks;
        }

    }

    /**
     * sends notifications to users who have bookmarked the given ad
     *
     * @param findBookmarksForAd
     */
    @SuppressWarnings("unchecked")
	public void sendNotificationsForBookmarks(Object bookmarks) {
    	assert(bookmarks instanceof List);
        if (bookmarks != null) {
            for (Bookmark mark : (List<Bookmark>) bookmarks) {
                createNotificationBookmark(mark);
            }
        }
    }

    /**
     * This method set the bookmark as readed
     *
     * @param adid
     */
    public void setReadBookmarkNote(String adid) {

        Bookmark bm = bookmarkDao.findByAdAndUser(adDao.findById(Long.parseLong(adid)), getLoggedInUser());
        List<Notifies> notes = notifiesDao.findByToUserAndBookmark(getLoggedInUser(), bm);
        for (Notifies note : notes) {
            note.setSeen(1);
            notifiesDao.save(note);
        }
    }

}
