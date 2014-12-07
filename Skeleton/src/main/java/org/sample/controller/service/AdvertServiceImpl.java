package org.sample.controller.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletContext;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.exceptions.InvalidAdException;
import org.sample.model.Address;
import org.sample.model.Advert;
import org.sample.model.Bookmark;
import org.sample.model.Enquiry;
import org.sample.model.Invitation;
import org.sample.model.Notifies;
import org.sample.model.Picture;
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
public class AdvertServiceImpl implements AdvertService {

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
     * Checks the adForm and saves the ad to the database
     *
     * @param adForm
     * @return the id of the new ad
     * @throws InvalidAdException
     */
    @Transactional
    public Long saveFromAd(AdCreateForm adForm) throws InvalidAdException {

        String street = adForm.getStreet();
        String city = adForm.getCity();
        String plz = adForm.getPlz();

        String title = adForm.getTitle();
        String roomDesc = adForm.getRoomDesc();
        String peopleDesc = adForm.getPeopleDesc();
        String roomSize = adForm.getRoomSize();
        String fromDate = adForm.getFromDate();
        String numberOfPeople = adForm.getNumberOfPeople();

        SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");
        Date todayDate = new Date();
        todayDate.setTime(todayDate.getTime() - 24 * 60 * 60 * 1000);
        Date fromDate2;

        if (StringUtils.isEmpty(title)) {
            throw new InvalidAdException("Title must not be empty" + title);
        } else if (title.length() < 4) {
            throw new InvalidAdException("Please enter a meaningfull Title ");
        }

        if (StringUtils.isEmpty(roomDesc)) {
            throw new InvalidAdException("Room description must not be empty");
        } else if (roomDesc.length() < 10) {
            throw new InvalidAdException("Please enter more information in your Room Description");
        }

        if (StringUtils.isEmpty(peopleDesc)) {
            throw new InvalidAdException("People description must not be empty");
        } else if (peopleDesc.length() < 10) {
            throw new InvalidAdException("Please enter more information in your People Description");
        }

        if (StringUtils.isEmpty(numberOfPeople)) {
            throw new InvalidAdException("Number of people in the WG must be entered");
        }

        if (StringUtils.isEmpty(roomSize) || !isInteger(roomSize)) {
            throw new InvalidAdException("Please enter a valid Room size");
        }

        if (!fromDate.equals("")) {
            try {
                fromDate2 = dateFormater.parse(fromDate);
            } catch (ParseException e1) {
                throw new InvalidAdException("Please enter the date correctly MM/dd/yyyy");
            }
            if (todayDate.getTime() > fromDate2.getTime()) {
                throw new InvalidAdException("Please enter a future date or today");
            }
        }

        if (StringUtils.isEmpty(street)) {
            throw new InvalidAdException("Street must not be empty");
        }

        if (StringUtils.isEmpty(city)) {
            throw new InvalidAdException("City must not be empty");
        }

        if (StringUtils.isEmpty(plz) || !isInteger(plz) || (plz.length() < 4) || (plz.length() > 5)) {
            throw new InvalidAdException("Please enter a valid postcode");
        }

        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setPlz(adForm.getPlz());

        Advert ad = new Advert();
        ad.setAddress(address);
        ad.setTitle(adForm.getTitle());
        ad.setPeopleDesc(adForm.getPeopleDesc());
        ad.setRoomDesc(adForm.getRoomDesc());
        ad.setFusedSearch(adForm.getTitle() + " " + adForm.getRoomDesc() + " " + adForm.getPeopleDesc());
        ad.setRoomPrice(Integer.parseInt(adForm.getRoomPrice()));
        ad.setRoomSize(Integer.parseInt(adForm.getRoomSize()));
        ad.setNumberOfPeople(Integer.parseInt(adForm.getNumberOfPeople()));
        ad.setUser(userDao.findByUsername(adForm.getUsername()));

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date dateFrom = null;
        Date dateTo = null;
        try {
            if (adForm.getFromDate() != null) {
                dateFrom = formatter.parse(adForm.getFromDate());
            } else {
                dateFrom = todayDate;
            }
            if (adForm.getToDate() != null) {
                dateTo = formatter.parse(adForm.getToDate());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            dateFrom = new Date();
        }
        ad.setFromDate(dateFrom);
        if (adForm.getToDate() != null) {
            ad.setToDate(dateTo);
        }

        if (!adForm.getFilenames().isEmpty()) {
            for (String file : adForm.getFilenames()) {
                Picture pic = new Picture();
                pic.setUrl(file);

                ad.addPicture(pic);
                pic = pictureDao.save(pic);
            }
        } else {
            Picture pic = new Picture();
            pic.setUrl("../web/images/icon/home.png");
            pic = pictureDao.save(pic);
            ad.addPicture(pic);
        }

        address = addDao.save(address);
        ad = adDao.save(ad);

        return ad.getId();
    }

    /**
     * Takes the updateForm and the id of the user and changes his information
     * on the database
     *
     * @param updateForm
     * @param id
     * @throws InvalidAdException
     */
    @Transactional
    public void updateAd(AdCreateForm updateForm, long id) throws InvalidAdException {
        String street = updateForm.getStreet();
        String city = updateForm.getCity();
        String plz = updateForm.getPlz();

        String title = updateForm.getTitle();
        String roomDesc = updateForm.getRoomDesc();
        String peopleDesc = updateForm.getPeopleDesc();
        String roomSize = updateForm.getRoomSize();
        String fromDate = updateForm.getFromDate();
        String numberOfPeople = updateForm.getNumberOfPeople();

        SimpleDateFormat dateFormater = new SimpleDateFormat("MM/dd/yyyy");

        if (StringUtils.isEmpty(title)) {
            throw new InvalidAdException("Title must not be empty" + title);
        } else if (title.length() < 4) {
            throw new InvalidAdException("Please enter a meaningfull Title ");
        }

        if (StringUtils.isEmpty(roomDesc)) {
            throw new InvalidAdException("Room description must not be empty");
        } else if (roomDesc.length() < 10) {
            throw new InvalidAdException("Please enter more information in your Room Description");
        }

        if (StringUtils.isEmpty(peopleDesc)) {
            throw new InvalidAdException("People description must not be empty");
        } else if (peopleDesc.length() < 10) {
            throw new InvalidAdException("Please enter more information in your People Description");
        }

        if (StringUtils.isEmpty(numberOfPeople)) {
            throw new InvalidAdException("Number of people in the WG must be entered");
        }

        if (StringUtils.isEmpty(roomSize) || !isInteger(roomSize)) {
            throw new InvalidAdException("Please enter a valid Room size");
        }

        if (StringUtils.isEmpty(fromDate)) {
            throw new InvalidAdException("Date must not be empty");
        }
        if (fromDate.length() != 10) {
            throw new InvalidAdException("Please enter the date correctly MM/dd/yyyy");
        }
        try {
            @SuppressWarnings("unused")
			Date fromDate_sec = null;
            fromDate_sec = dateFormater.parse(fromDate);
        } catch (ParseException e1) {
            throw new InvalidAdException("Please enter the date correctly MM/dd/yyyy");
        }

        if (StringUtils.isEmpty(street)) {
            throw new InvalidAdException("Street must not be empty");
        }

        if (StringUtils.isEmpty(city)) {
            throw new InvalidAdException("City must not be empty");
        }

        if (StringUtils.isEmpty(plz) || !isInteger(plz) || (plz.length() < 4) || (plz.length() > 5)) {
            throw new InvalidAdException("Please enter a valid postcode");
        }

        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setPlz(updateForm.getPlz());
        address = addDao.save(address);

        Advert ad = getAd(id);
        if (ad == null) {
            throw new InvalidAdException("AD ID IS INCORRECT!");
        }
        ad.setAddress(address);
        ad.setTitle(updateForm.getTitle());
        ad.setPeopleDesc(updateForm.getPeopleDesc());
        ad.setRoomDesc(updateForm.getRoomDesc());
        ad.setFusedSearch(updateForm.getTitle() + " " + updateForm.getRoomDesc() + " " + updateForm.getPeopleDesc());
        ad.setRoomPrice(Integer.parseInt(updateForm.getRoomPrice()));
        ad.setRoomSize(Integer.parseInt(updateForm.getRoomSize()));
        ad.setNumberOfPeople(Integer.parseInt(updateForm.getNumberOfPeople()));

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date dateFrom = null;
        Date dateTo = null;
        if (updateForm.getFromDate() != "") {
            try {
                dateFrom = formatter.parse(updateForm.getFromDate());
                if (updateForm.getToDate() != "") {
                    dateTo = formatter.parse(updateForm.getToDate());
                }
            } catch (ParseException e) {
                dateFrom = new Date();
            }
        }

        ad.setFromDate(dateFrom);
        if (updateForm.getToDate() != "") {
            ad.setToDate(dateTo);
        }
        if (updateForm.getFilenames() != null) {
            for (String file : updateForm.getFilenames()) {
                Picture pic = new Picture();
                pic.setUrl(file);

                ad.addPicture(pic);

                pic = pictureDao.save(pic);
            }
        }
        ad = adDao.save(ad);

    }

    /**
     * Retrieves an ad from the database.
     *
     * @param id
     * @return Advert - the advert returned from the database / Null, when there
     * is no advert with the given id
     */
    public Advert getAd(Long id) {

        return adDao.findOne(id);
    }

    /**
     * Retrieves all ads created by given user from the database.
     *
     * @param user - the user to look for ads
     * @return Iterable<Adverts> - All ads created by the given user
     */
    public Iterable<Advert> findAdsForUser(org.sample.model.User user) {
        return adDao.findByUserId(user.getId());
    }

    /**
     * finds all dependencies of given ad id and removes them
     */
	public void deleteAd(Long id) {
		Advert ad = adDao.findById(id);
		Iterable<Notifies> notes = notifiesDao.findByAd(ad);
		notifiesDao.deleteInBatch(notes);
		Iterable<Enquiry> enqs = enquiryDao.findByAdvert(ad);
		enquiryDao.deleteInBatch(enqs);
		Iterable<Invitation> invs = invitationDao.findByAdvert(ad);
		invitationDao.deleteInBatch(invs);
		Iterable<Bookmark> bms = bookmarkDao.findByAd(ad);
		bookmarkDao.deleteInBatch(bms);
		adDao.delete(ad);
	}
	
	/**
	 * deletes given picture from ad from db
	 */
	public void deletePic(Long picid,Long adid) {
		Advert ad = adDao.findById(adid);
		Set<Picture> pics = ad.getPictures();
		Picture picToRemove = null;
		for(Picture p : pics)
		{
			if(p.getId()==picid)
			{
				picToRemove = p;
			}
		}
		if(picToRemove!=null)
		{
			pics.remove(picToRemove);
			ad.setPictures(pics);
			adDao.save(ad);
			pictureDao.delete(picToRemove);
		}
	}

}
