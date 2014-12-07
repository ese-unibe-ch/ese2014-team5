package org.sample.controller.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;

import org.sample.controller.pojos.SearchForm;
import org.sample.exceptions.InvalidSearchException;
import org.sample.model.Advert;
import org.sample.model.Notifies;
import org.sample.model.Search;
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
import org.springframework.util.StringUtils;

/**
 *
 * @author severin.zumbrunn, ramona.imhof, florentina.ziegler, ricardo.visini
 *
 *
 */
@Service
@Transactional
public class SearchServiceImpl implements SearchService {

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
     * Checks of the entered String s can be converted to an Integer
     *
     * @param s
     * @return
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Takes the saveForm and checks the content. Saves the content of the
     * search String
     *
     * @param searchForm
     * @param saveToProfile
     * @return
     */
    @Transactional
    public Long saveFromSearch(SearchForm searchForm, boolean saveToProfile) {
        String freetext = searchForm.getSearch();
        String priceFrom = searchForm.getFromPrice();
        String priceTo = searchForm.getToPrice();
        String sizeFrom = searchForm.getFromSize();
        String sizeTo = searchForm.getToSize();
        String area = searchForm.getNearCity();
        String peopleAmount = searchForm.getNumberOfPeople();
        String fromDate = searchForm.getFromDate();
        String toDate = searchForm.getToDate();

        if (!(!StringUtils.isEmpty(freetext) || !priceFrom.equals("0") || !priceTo.equals("0")
                || !sizeFrom.equals("0") || !sizeTo.equals("0") || !StringUtils.isEmpty(area)
                || !StringUtils.isEmpty(peopleAmount) || fromDate != null || toDate != null)) {
            throw new InvalidSearchException("Search is not being saved because no filters are set.");
        }

        Search search = new Search();
        search.setFreetext(freetext);
        search.setPriceFrom(priceFrom);
        search.setPriceTo(priceTo);
        search.setSizeFrom(sizeFrom);
        search.setSizeTo(sizeTo);
        search.setArea(area);
        search.setPeopleAmount(peopleAmount);
        SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");
        Date insertFromdate = null;
        try {
            insertFromdate = dateFormater.parse(fromDate);
        } catch (ParseException ex) {
            //Logger.getLogger(SampleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date insertTodate = null;
        try {
            insertTodate = dateFormater.parse(toDate);
        } catch (ParseException ex) {
            //Logger.getLogger(SampleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        search.setFromDate(insertFromdate);
        search.setToDate(insertTodate);
        if (getLoggedInUser() != null && getLoggedInUser().getUserRole().getRole() == 1 && saveToProfile == true) {
            search.setUser(userDao.findOne(searchForm.getUserId()));
        }

        search = searchDao.save(search);

        return search.getId();
    }

    /**
     * This function takes the search form and checks if the content is correct
     * If yes then it commits a search. And retrieves the output The search is
     * done by SQL-Querries
     *
     * @param form
     * @return
     */
    @Transactional
    public Iterable<Advert> findAds(SearchForm form) {

        if (form.getFromPrice() == (null)) {
            form.setFromPrice("0");
        }
        if (form.getToPrice() == (null)) {
            form.setToPrice("0");
        }
        int priceMin = 0;
        int priceMax = 0;
        try {
            priceMin = Integer.parseInt(form.getFromPrice());
            priceMax = Integer.parseInt(form.getToPrice());

            if (priceMax < priceMin) {
                int placeholder = priceMax;
                priceMax = priceMin;
                priceMin = placeholder;
            }
        } catch (NumberFormatException e) {
            priceMax = 9999999;
            priceMin = 0;
        }

        if (form.getFromSize() == null) {
            form.setFromSize("0");
        }
        if (form.getToSize() == null) {
            form.setToSize("0");
        }
        int roomSizeMin = 0;
        int roomSizeMax = 0;
        try {
            roomSizeMin = Integer.parseInt(form.getFromSize());
            roomSizeMax = Integer.parseInt(form.getToSize());

            if (roomSizeMax < roomSizeMin) {
                int placeholder = roomSizeMax;
                roomSizeMax = roomSizeMin;
                roomSizeMin = placeholder;
            }

        } catch (NumberFormatException e) {
            roomSizeMin = 0;
            roomSizeMax = 9999999;
        }

        String town = form.getNearCity();
        if (town == null || town.length() == 0) {
            town = "";

        }

        String textSearch = form.getSearch();
        if (textSearch == null || textSearch.length() == 0) {
            textSearch = "";
        }

        Iterable<org.sample.model.Advert> ads = null;
        boolean noDateRangeUp = false;
        boolean noDateRangeDown = false;
        int people = 0;
        if (form.getNumberOfPeople() == null || form.getNumberOfPeople().equals("") || form.getNumberOfPeople().length() == 0) {
            people = 99;
        } else {
            people = Integer.parseInt(form.getNumberOfPeople());
        }

        Date dateFrom = null;
        Date dateTo = null;
        SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");

        if (form.getFromDate() != null && form.getFromDate() != "") {
            try {
                dateFrom = dateFormater.parse(form.getFromDate());
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            try {
                dateFrom = dateFormater.parse("01/01/1980");
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            noDateRangeDown = true;
        }

        if (form.getToDate() != null && form.getToDate() != "") {
            try {
                dateTo = dateFormater.parse(form.getToDate());

            } catch (Exception ex) {

            }
        } else {
            try {
                dateTo = dateFormater.parse("01/01/2100");
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            noDateRangeUp = true;
        }

        if (dateFrom.compareTo(dateTo) > 0) {
            /*Turn the dates if they are not in timeline*/
            Date placeholder = dateTo;
            dateTo = dateFrom;
            dateFrom = placeholder;
        }

        if (noDateRangeDown == false && noDateRangeUp == false) {
            ads = adDao.findByFromDateBeforeAndToDateAfterAndNumberOfPeopleLessThanEqualAndRoomPriceBetweenAndRoomSizeBetweenAndAddressCityContainingAndFusedSearchContaining(dateFrom, dateTo, people, priceMin, priceMax, roomSizeMin, roomSizeMax, town, textSearch);
        } else if (noDateRangeDown == true && noDateRangeUp == false) {
            ads = adDao.findByToDateAfterAndNumberOfPeopleLessThanEqualAndRoomPriceBetweenAndRoomSizeBetweenAndAddressCityContainingAndFusedSearchContaining(dateTo, people, priceMin, priceMax, roomSizeMin, roomSizeMax, town, textSearch);
        } else if (noDateRangeDown == false && noDateRangeUp == true) {
            ads = adDao.findByFromDateBeforeAndToDateAfterAndNumberOfPeopleLessThanEqualAndRoomPriceBetweenAndRoomSizeBetweenAndAddressCityContainingAndFusedSearchContaining(dateFrom, dateFrom, people, priceMin, priceMax, roomSizeMin, roomSizeMax, town, textSearch);
        } else if (noDateRangeDown == true && noDateRangeUp == true) {
            ads = adDao.findByroomPriceBetweenAndRoomSizeBetweenAndAddressCityContainingAndFusedSearchContaining(priceMin, priceMax, roomSizeMin, roomSizeMax, town, textSearch);

        }

        return ads;
    }

    /**
     * Retrieves the current logged in user from spring Security
     *
     * @return the logged in user
     */
    public org.sample.model.User getLoggedInUser() {
        return (org.sample.model.User) userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * Deletes a search with given id
     *
     * @param value - the id of the search to delete
     */
    public void removeSearch(Long searchId) {
        Search search = searchDao.findOne(searchId);
        search.setUser(null);
        searchDao.save(search);
    }


    /**
     * According to the selected search (i.e. filter) in users' profiles, this
     * creates notifications as soon as new (matching) ads are created.
     *
     * @param id The id of the advert that causes new notifications for saved
     * searches.
     */
    public void createNewSearchNotifications(Long id) {
        Advert advert = adDao.findOne(id);
        List<org.sample.model.User> possibleUsersForSearchNotification = userDao.findByselectedSearchGreaterThanEqual(Long.valueOf(1));

        for (org.sample.model.User user : possibleUsersForSearchNotification) {
            Search search = searchDao.findOne(user.getSelectedSearch());
            if ((advert.getTitle().contains(search.getFreetext()) || advert.getRoomDesc().contains(search.getFreetext()))
                    && (search.getPriceFrom() == "" || advert.getRoomPrice() >= Integer.parseInt(search.getPriceFrom()))
                    && (search.getPriceTo() == "" || advert.getRoomPrice() <= Integer.parseInt(search.getPriceTo()))
                    && (search.getSizeFrom() == "" || advert.getRoomSize() >= Integer.parseInt(search.getSizeFrom()))
                    && (search.getSizeTo() == "" || advert.getRoomSize() <= Integer.parseInt(search.getSizeTo()))
                    && (search.getArea() == "" || advert.getAddress().getCity().equals(search.getArea()))
                    && (search.getPeopleAmount() == "" || advert.getnumberOfPeople() == Integer.parseInt(search.getPeopleAmount()))
                    && (search.getFromDate() == null || advert.getFromDate().equals(search.getFromDate()) || advert.getFromDate().after(search.getFromDate()))
                    && (search.getToDate() == null || advert.getToDate().equals(search.getToDate()) || advert.getToDate().before(search.getToDate()))) {

                Notifies newNotification = new Notifies();
                newNotification.setSearch(search);
                newNotification.setAd(advert);
                newNotification.setToUser(user);
                newNotification.setDate(new Date());
                newNotification.setSeen(0);
                notifiesDao.save(newNotification);

                try {
                    email.GenerateAnEmail(newNotification);
                } catch (MessagingException ex) {
                    Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

}
